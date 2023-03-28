package com.lechos22.polynomialapplication.real

import kotlin.math.pow

class RealRoot(x: Real, deg: Real = RealInt(2)) : Real {
    val x: Real
    val deg: Real
    init {
        if(x is RealRoot) {
            this.x = x.x
            this.deg = deg * x.deg
        } else {
            this.x = x
            this.deg = deg
        }
    }
    override fun invoke() = x().pow(1 / deg())
    override fun toString() = "(√ $deg $x)"
}
