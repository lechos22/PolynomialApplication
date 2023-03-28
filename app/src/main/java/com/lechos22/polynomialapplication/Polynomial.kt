package com.lechos22.polynomialapplication

import java.util.*

open class Polynomial : Cloneable {
    private var coefficients: SortedMap<Int, AccurateNumber>

    constructor(coefficients: Map<Int, AccurateNumber>) {
        this.coefficients = coefficients
            .filter { (_, a) -> !a.isZero() }
            .toSortedMap(reverseOrder())
    }

    constructor(vararg coefficients: Pair<Int, AccurateNumber>) {
        this.coefficients = coefficients
            .toMap()
            .filter { (_, a) -> !a.isZero() }
            .toSortedMap(reverseOrder())
    }

    constructor(coefficients: List<Pair<Int, AccurateNumber>>) {
        this.coefficients = coefficients
            .toMap()
            .filter { (_, a) -> !a.isZero() }
            .toSortedMap(reverseOrder())
    }

    override fun equals(other: Any?): Boolean =
        (other is Polynomial)
            && coefficients.size == other.coefficients.size
            && coefficients.all { (n, a) -> other.coefficients[n] == a }

    fun degree(): Int = if (coefficients.size == 0) 0 else coefficients.firstKey()
    fun isZero(): Boolean = coefficients.size == 0

    operator fun invoke(x: AccurateNumber): AccurateNumber =
        coefficients.entries.fold(AccurateNumber.ZERO) { acc, (n, a) ->
            acc + x.pow(n) * a
        }

    public override fun clone() = Polynomial(coefficients)
    operator fun unaryPlus(): Polynomial = this.clone()
    operator fun plus(other: Polynomial): Polynomial {
        val acc = mutableMapOf<Int, AccurateNumber>()
        coefficients.forEach { (n, a) ->
            acc[n] = a
        }
        other.coefficients.forEach { (n, a) ->
            acc[n] = (acc[n] ?: AccurateNumber.ZERO) + a
        }
        return Polynomial(acc)
    }

    operator fun plusAssign(other: Polynomial) {
        other.coefficients.forEach { (n, a) ->
            coefficients[n] = (coefficients[n] ?: AccurateNumber.ZERO) + a
            if (coefficients[n]!!.isZero())
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
            coefficients[n] = (coefficients[n] ?: AccurateNumber.ZERO) - a
            if (coefficients[n]!!.isZero())
                coefficients.remove(n)
        }
    }

    operator fun times(x: AccurateNumber) =
        Polynomial(
            coefficients.mapValues { (_, a) -> a * x }
        )

    operator fun times(other: Polynomial): Polynomial {
        val acc = mutableMapOf<Int, AccurateNumber>()
        coefficients.forEach { (n, a) ->
            other.coefficients.forEach { (m, b) ->
                acc[n + m] = (acc[n + m] ?: AccurateNumber.ZERO) + a * b
            }
        }
        return Polynomial(acc)
    }

    operator fun div(x: AccurateNumber) =
        Polynomial(
            coefficients.mapValues { (_, a) -> a / x }
        )

    open fun divRem(other: Polynomial): Pair<Polynomial, Polynomial> {
        if (other.coefficients.none { (_, a) -> !a.isZero() })
            throw ArithmeticException("Division by zero")
        else if (this.coefficients.none { (_, a) -> !a.isZero() })
            return Pair(Polynomial(), Polynomial())
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

    open fun roundedString(): String {
        val builder = StringBuilder()
        coefficients.forEach { (n, a) ->
            builder.append(" + ")
            if (a != AccurateNumber.ONE || n == 0)
                builder.append(a.bigFraction.bigDecimalValue().toEngineeringString())
            if (n == 1)
                builder.append("x")
            else if (n != 0)
                builder.append("x^", n)
        }
        return if (builder.isNotEmpty())
            builder.substring(3)
                .replace("+ -", "- ")
                .replace(Regex("\\.0x"), "x")
                .replace(Regex("\\.0 "), " ")
                .replace(Regex("\\.0$"), "")
                .replace("-1x", "-x")
        else
            "0"
    }

    override fun toString(): String = roundedString()

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

    open fun zeroesDeduction(): List<Polynomial> =
        (1..this.coefficients.lastKey()).map { linear(AccurateNumber.ONE, AccurateNumber.ZERO) } +
            Polynomial(
                this.coefficients
                    .mapKeys { (m, _) -> m - this.coefficients.lastKey() }
            )

    open fun rationalRootsDeduction(): List<Polynomial> =
        if(coefficients.isNotEmpty() && coefficients.all { it.value.isRound() }) {
            val ps = divisors(coefficients[coefficients.lastKey()]!!.absoluteValue)
            val qs = plusMinus(divisors(coefficients[degree()]!!.absoluteValue))
            var acc = this.clone()
            qs.flatMap { q ->
                ps.map { p -> p / q }
                    .filter { this@Polynomial(it).isZero() }
                    .flatMap {
                        val divisor = linear(AccurateNumber.ONE, -it)
                        var divRem = acc.divRem(divisor)
                        var count = 0
                        while(divRem.second.isZero()) {
                            acc = divRem.first
                            count++
                            divRem = acc.divRem(divisor)
                        }
                        (1..count).map{ divisor }
                    }
            } + if(!acc.isZero()) listOf(acc) else emptyList()
        } else listOf(this)

    open fun factorize(): List<Polynomial> =
        if(isZero()) listOf(this)
        else
            mutableListOf(this)
                .run {
                    addAll(removeLast().zeroesDeduction())
                    addAll(removeLast().rationalRootsDeduction())
                    this
                }
                .filter { it.degree() != 0 || it.coefficients[0] != AccurateNumber(1.0) }
                .ifEmpty { listOf(constant(AccurateNumber.ONE)) }

    companion object {
        fun constant(a: AccurateNumber) = Polynomial(Pair(0, a))
        fun linear(a: AccurateNumber, b: AccurateNumber) = Polynomial(Pair(1, a), Pair(0, b))
        fun quadratic(a: AccurateNumber, b: AccurateNumber, c: AccurateNumber) =
            Polynomial(Pair(2, a), Pair(1, b), Pair(0, c))
        /* Input format definition in BNF
            <polynomial> ::=
               <polynomial_word> <opt_ws> <sign> <opt_ws> <polynomial>
             | <polynomial_word>
            <polynomial_word> ::=
               <float> "x" <opt_ws> "^" <opt_ws> <uint>
             | "x" <opt_ws> "^" <opt_ws> <uint>
             | <float> "x" | "x" | <float>
            <opt_ws> ::= <whitespace> | E
            <whitespace> ::=
               " " | "\n" | "\t"
             | <whitespace> <whitespace>
            <float> ::= <int> | <int> "." <uint>
            <int> ::= <uint> | <sign> <uint>
            <uint> ::= [0-9]+
            <sign> ::= "+" | "-"
         */
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
                        Pair(-1, AccurateNumber.ZERO)
                    else if (trimmed.matches(Regex("-?[\\d.]+")))
                        Pair(0, AccurateNumber(trimmed.toDouble()))
                    else if (trimmed.matches(Regex("-?[\\d.]*x\\^\\d+"))) {
                        val params = trimmed.split("x^")
                        Pair(params[1].toInt(), AccurateNumber(params[0].toDoubleOrNull() ?: 1.0, 1000))
                    } else if (trimmed.matches(Regex("-?[\\d.]*x")))
                        Pair(
                            1,
                            AccurateNumber(trimmed.substring(0, trimmed.length - 1).toDoubleOrNull() ?: 1.0, 1000)
                        )
                    else
                        throw IllegalArgumentException("Trying to parse an invalid polynomial string")
                }
            )

        fun deserialize(string: String): Polynomial =
            Polynomial(string.split(";").map {
                val params = it.split(",")
                val n = params[0].toInt()
                val a = AccurateNumber(params[1].toDouble())
                Pair(n, a)
            })
    }
}
