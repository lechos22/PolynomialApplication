package com.lechos22.polynomialapplication.real

class RealSum(vararg xs: Real) : Real {
    val xs: List<Real> = xs.flatMap { if(it is RealSum) it.xs else listOf(it) }
    override fun invoke() = xs.sumOf(Real::invoke)
    override fun toString() = "(+ ${xs.joinToString(" ")})"
}