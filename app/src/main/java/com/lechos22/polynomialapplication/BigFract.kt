package com.lechos22.polynomialapplication

import android.os.Build
import androidx.annotation.RequiresApi
import org.apache.commons.numbers.fraction.BigFraction
import java.math.BigDecimal
import java.math.BigInteger
import java.math.MathContext

class BigFract {
    val bigFraction: BigFraction
    val absoluteValue: BigFract
        get() = BigFract(bigFraction.abs())
    constructor(bigFraction: BigFraction) {
        this.bigFraction = bigFraction
    }
    constructor(double: Double) {
        this.bigFraction = BigFraction.from(double)
    }
    constructor(string: String) {
        this.bigFraction = BigFraction.parse(string)
    }
    operator fun plus(other: BigFract) = BigFract(bigFraction.add(other.bigFraction))
    operator fun minus(other: BigFract) = BigFract(bigFraction.subtract(other.bigFraction))
    operator fun unaryMinus() = BigFract(bigFraction.negate())
    operator fun times(other: BigFract) = BigFract(bigFraction.multiply(other.bigFraction))
    operator fun div(other: BigFract) = BigFract(bigFraction.divide(other.bigFraction))
    operator fun rem(other: Double) = bigFraction.toDouble() % other
    operator fun compareTo(other: BigFract) = bigFraction.compareTo(other.bigFraction)
    override fun equals(other: Any?) =
        if(other is BigFract) bigFraction == other.bigFraction
        else bigFraction == other
    fun pow(other: Int) = BigFract(bigFraction.pow(other))
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun sqrt(): BigDecimal = bigFraction.bigDecimalValue().sqrt(MathContext.UNLIMITED)
    override fun hashCode() = bigFraction.hashCode()
    fun isZero() = bigFraction.signum() == 0
    fun isRound(): Boolean = bigFraction.numerator.mod(bigFraction.denominator) == BigInteger.ZERO
    override fun toString() = bigFraction.toDouble().toString()

    companion object {
        val ZERO = BigFract(BigFraction.ZERO)
        val ONE = BigFract(BigFraction.ONE)
    }
}