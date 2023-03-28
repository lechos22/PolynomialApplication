package com.lechos22.polynomialapplication.real

import kotlin.math.pow

object NumberFactorization {
    // All prime components of int32 should be contained in this range
    private val shortPrimes = mutableSetOf<Int>().run {
        (2..65521).forEach { n ->
            if (none { n % it == 0 }) add(n)
        }
        this
    }

    private fun primesOf(n: Int) =
        shortPrimes
            .filter { n % it == 0 }
            .associateWith { n / it }

    fun gcd(n: Int, m: Int): Int {
        val mPrimes = primesOf(m)
        return primesOf(n).entries
            .filter { (k, _) -> mPrimes.containsKey(k) }
            .associate { (k, v) -> Pair(k, Integer.min(v, mPrimes[k]!!)) }
            .map { (k, v) -> k.toDouble().pow(v).toInt() }
            .fold(1, Int::times)
    }
}