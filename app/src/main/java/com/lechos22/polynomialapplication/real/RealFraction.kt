package com.lechos22.polynomialapplication.real

class RealFraction(val nom: Real, val denom: Real) : Real {
    override fun invoke(): Double = nom() / denom()

    override fun toString() = "(/ $nom $denom)"
}