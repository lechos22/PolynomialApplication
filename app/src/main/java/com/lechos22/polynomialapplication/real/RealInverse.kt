package com.lechos22.polynomialapplication.real

class RealInverse(val x: Real) : Real {
    override fun invoke(): Double = 1 / x()
    override fun toString() = "(/ $x)"
}