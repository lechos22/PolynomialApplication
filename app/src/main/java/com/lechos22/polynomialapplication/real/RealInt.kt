package com.lechos22.polynomialapplication.real

class RealInt(val x: Int): Real {
    override fun invoke() = x.toDouble()
    operator fun div(other: RealInt) = RealFraction(this, other)
    override fun toString(): String = x.toString()
}
