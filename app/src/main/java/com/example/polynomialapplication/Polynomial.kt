package com.example.polynomialapplication

import java.util.SortedMap
import kotlin.math.pow
import kotlin.math.roundToLong

open class Polynomial : Cloneable {
    private var coefficients: SortedMap<Int, Double>

    constructor(coefficients: Map<Int, Double>) {
        this.coefficients = coefficients
            .filter { (_, a) -> a != 0.0 }
            .toSortedMap(reverseOrder())
    }

    constructor(vararg coefficients: Pair<Int, Double>) {
        this.coefficients = coefficients
            .toMap()
            .filter { (_, a) -> a != 0.0 }
            .toSortedMap(reverseOrder())
    }

    constructor(coefficients: List<Pair<Int, Double>>) {
        this.coefficients = coefficients
            .toMap()
            .filter { (_, a) -> a != 0.0 }
            .toSortedMap(reverseOrder())
    }

    override fun equals(other: Any?): Boolean =
        (other is Polynomial)
            && coefficients.size == other.coefficients.size
            && coefficients.all { (n, a) -> other.coefficients[n] == a }

    fun degree(): Int = if (coefficients.size == 0) 0 else coefficients.firstKey()
    fun isZero(): Boolean = coefficients.size == 0

    operator fun invoke(x: Double): Double =
        coefficients.entries.fold(0.0) { acc, (n, a) ->
            acc + x.pow(n) * a
        }

    public override fun clone() = Polynomial(coefficients)
    operator fun unaryPlus(): Polynomial = this.clone()
    operator fun plus(other: Polynomial): Polynomial {
        val acc = mutableMapOf<Int, Double>()
        coefficients.forEach { (n, a) ->
            acc[n] = a
        }
        other.coefficients.forEach { (n, a) ->
            acc[n] = (acc[n] ?: 0.0) + a
        }
        return Polynomial(acc)
    }

    operator fun plusAssign(other: Polynomial) {
        other.coefficients.forEach { (n, a) ->
            coefficients[n] = (coefficients[n] ?: 0.0) + a
            if (coefficients[n] == 0.0)
                coefficients.remove(n)
        }
    }

    operator fun unaryMinus(): Polynomial =
        Polynomial(
            coefficients.mapValues { (_, a) -> -a }
        )

    operator fun minus(other: Polynomial): Polynomial = this + -other
    operator fun minusAssign(other: Polynomial) {
        other.coefficients.forEach { (n, a) ->
            coefficients[n] = (coefficients[n] ?: 0.0) - a
            if (coefficients[n] == 0.0)
                coefficients.remove(n)
        }
    }

    operator fun times(x: Double) =
        Polynomial(
            coefficients.mapValues { (_, a) -> a * x }
        )

    operator fun times(other: Polynomial): Polynomial {
        val acc = mutableMapOf<Int, Double>()
        coefficients.forEach { (n, a) ->
            other.coefficients.forEach { (m, b) ->
                acc[n + m] = (acc[n + m] ?: 0.0) + a * b
            }
        }
        return Polynomial(acc)
    }

    operator fun div(x: Double) =
        Polynomial(
            coefficients.mapValues { (_, a) -> a / x }
        )

    open fun divRem(other: Polynomial): Pair<Polynomial, Polynomial> {
        if (other.coefficients.none { (_, a) ->
                a > 0.0
            }) throw ArithmeticException("Division by zero")
        else if (this.coefficients.none { (_, a) ->
                a > 0.0
            }) return Pair(Polynomial(), Polynomial())
        val acc = Polynomial()
        val remainder = this.clone()
        val otherDegree = other.degree()
        while (!remainder.isZero() && remainder.degree() >= otherDegree) {
            val quotient = Polynomial(
                Pair(
                    remainder.degree() - otherDegree,
                    remainder.coefficients[remainder.degree()]!! / other.coefficients[otherDegree]!!
                )
            )
            acc += quotient
            remainder -= other * quotient
        }
        return Pair(acc, remainder)
    }

    operator fun div(other: Polynomial): Polynomial = this.divRem(other).first

    operator fun rem(other: Polynomial): Polynomial = this.divRem(other).second

    override fun hashCode(): Int = coefficients.hashCode()

    open fun roundedString(decimals: Int? = null): String {
        val builder = StringBuilder()
        coefficients.forEach { (n, a) ->
            builder.append(" + ")
            if (a != 1.0 || n == 0)
                builder.append(decimals?.let {
                    val factor = 10.0.pow(it)
                    (a * factor).roundToLong().toDouble() / factor
                } ?: a)
            if (n == 1)
                builder.append("x")
            else if (n != 0)
                builder.append("x^", n)
        }
        return if (builder.isNotEmpty())
            builder.substring(3)
                .replace("+ -", "- ")
                .replace(Regex(".0x"), "x")
                .replace(Regex(".0 "), " ")
                .replace(Regex(".0$"), "")
                .replace("-1x", "-x")
        else
            "0"
    }

    override fun toString(): String = roundedString(null)

    open fun serialize(): String {
        val builder = StringBuilder()
        coefficients.forEach { (n, a) ->
            builder.append(";", n, ",", a)
        }
        return if (builder.isNotEmpty())
            builder.substring(1)
        else
            ""
    }

    open fun zeroesReduction(): List<Polynomial> =
        (1..this.coefficients.lastKey()).map { linear(1.0, 0.0) } +
            Polynomial(
                this.coefficients
                    .mapKeys { (m, _) -> m - this.coefficients.lastKey() }
            )

    open fun dropUnit(): List<Polynomial> =
        if(degree() == 0 && coefficients[0] == 1.0) emptyList()
        else listOf(this)

    open fun factorize(): List<Polynomial> =
        zeroesReduction()
            .flatMap(Polynomial::dropUnit)
            .ifEmpty { listOf(constant(1.0)) }

    companion object {
        fun constant(a: Double) = Polynomial(Pair(0, a))
        fun linear(a: Double, b: Double) = Polynomial(Pair(1, a), Pair(0, b))
        fun quadratic(a: Double, b: Double, c: Double) =
            Polynomial(Pair(2, a), Pair(1, b), Pair(0, c))
        fun fromString(string: String): Polynomial =
            Polynomial(string
                .replace(" ", "")
                .replace("*", "")
                .replace("-", "+-")
                .replace("++", "+")
                .replace("-x", "-1x")
                .split("+")
                .map {
                    val trimmed = it.trim()
                    if (trimmed == "")
                        Pair(-1, 0.0)
                    else if (trimmed.matches(Regex("-?[\\d.]+")))
                        Pair(0, trimmed.toDouble())
                    else if (trimmed.matches(Regex("-?[\\d.]*x\\^\\d+"))) {
                        val params = trimmed.split("x^")
                        Pair(params[1].toInt(), params[0].toDoubleOrNull() ?: 1.0)
                    } else if (trimmed.matches(Regex("-?[\\d.]*x")))
                        Pair(
                            1,
                            trimmed.substring(0, trimmed.length - 1).toDoubleOrNull() ?: 1.0
                        )
                    else
                        throw IllegalArgumentException("Trying to parse an invalid polynomial string")
                }
            )

        fun deserialize(string: String): Polynomial =
            Polynomial(string.split(";").map {
                val params = it.split(",")
                val n = params[0].toInt()
                val a = params[1].toDouble()
                Pair(n, a)
            })
    }
}