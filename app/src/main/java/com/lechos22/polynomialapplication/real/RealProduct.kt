package com.lechos22.polynomialapplication.real

class RealProduct(vararg xs: Real) : Real {
    val xs: List<Real> = xs.flatMap { if(it is RealProduct) it.xs else listOf(it) }
    override fun invoke() = xs.map(Real::invoke).fold(1.0, Double::times)
    override fun toString() = "(* ${xs.joinToString(" ")})"
}