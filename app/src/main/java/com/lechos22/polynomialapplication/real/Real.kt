package com.lechos22.polynomialapplication.real

interface Real {
    operator fun invoke(): Double
    operator fun plus(other: Real) = RealSum(this, other)
    operator fun unaryMinus() = RealNeg(this)
    operator fun minus(other: Real) = RealSum(this, -other)
    operator fun div(other: Real) = RealFraction(this, other)
    operator fun times(other: Real) = RealProduct(this, other)
}
