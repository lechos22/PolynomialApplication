package com.lechos22.polynomialapplication.real

class RealInt(val x: Int): Real {
    override fun invoke() = x.toDouble()
    override fun toString(): String = x.toString()
}
