package com.lechos22.polynomialapplication

import android.os.Build
import androidx.annotation.RequiresApi
import org.apache.commons.numbers.fraction.BigFraction
import java.math.BigDecimal
import java.math.BigInteger
import java.math.MathContext

class AccurateNumber {
    val bigFraction: BigFraction
    val absoluteValue: AccurateNumber
        get() = AccurateNumber(bigFraction.abs())
    constructor(bigFraction: BigFraction) {
        this.bigFraction = bigFraction
    }
    constructor(num: Int, den: Int = 1) {
        this.bigFraction = BigFraction.of(num, den)
    }
    constructor(double: Double) {
        this.bigFraction = BigFraction.from(double)
    }
    constructor(double: Double, maxDenominator: Int) {
        this.bigFraction = BigFraction.from(double, maxDenominator)
    }
    constructor(string: String) {
        this.bigFraction = BigFraction.parse(string)
    }
    operator fun plus(other: AccurateNumber) = AccurateNumber(bigFraction.add(other.bigFraction))
    operator fun minus(other: AccurateNumber) = AccurateNumber(bigFraction.subtract(other.bigFraction))
    operator fun unaryMinus() = AccurateNumber(bigFraction.negate())
    operator fun times(other: AccurateNumber) = AccurateNumber(bigFraction.multiply(other.bigFraction))
    operator fun div(other: AccurateNumber) = AccurateNumber(bigFraction.divide(other.bigFraction))
    operator fun rem(other: Double) = bigFraction.toDouble() % other
    operator fun compareTo(other: AccurateNumber) = bigFraction.compareTo(other.bigFraction)
    override fun equals(other: Any?) =
        if(other is AccurateNumber) bigFraction == other.bigFraction
        else bigFraction == other
    fun pow(other: Int) = AccurateNumber(bigFraction.pow(other))
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun sqrt(): BigDecimal = bigFraction.bigDecimalValue().sqrt(MathContext.UNLIMITED)
    override fun hashCode() = bigFraction.hashCode()
    fun isZero() = bigFraction.signum() == 0
    fun isRound(): Boolean = bigFraction.numerator.mod(bigFraction.denominator) == BigInteger.ZERO
    override fun toString() = bigFraction.toDouble().toString()

    companion object {
        val ZERO = AccurateNumber(BigFraction.ZERO)
        val ONE = AccurateNumber(BigFraction.ONE)
    }
}