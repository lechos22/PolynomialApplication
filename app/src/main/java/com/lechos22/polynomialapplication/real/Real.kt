package com.lechos22.polynomialapplication.real

interface Real {
    operator fun invoke(): Double
    operator fun plus(other: Real) = RealSum(this, other)
    operator fun unaryMinus() =
        if(this is RealNeg) this.x
        else RealNeg(this)
    operator fun minus(other: Real) = RealSum(this, -other)
    fun inverse() =
        if(this is RealInverse) this.x
        else RealInverse(this)
    operator fun div(other: Real) = RealProduct(this, other.inverse())
    operator fun times(other: Real) = RealProduct(this, other)
    fun root(other: Real) = RealRoot(this, other)
}
