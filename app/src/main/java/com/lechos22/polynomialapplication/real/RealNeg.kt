package com.lechos22.polynomialapplication.real

class RealNeg(val x: Real) : Real {
    override fun invoke() = -x()
    override fun toString() = "(- $x)"
}