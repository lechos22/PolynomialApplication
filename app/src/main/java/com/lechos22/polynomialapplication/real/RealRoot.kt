package com.lechos22.polynomialapplication.real

import kotlin.math.pow

class RealRoot(val x: Real, val deg: Real = RealInt(2)) : Real {
    override fun invoke() = x().pow(1 / deg())
    override fun toString() = "(√ $deg $x)"
}
