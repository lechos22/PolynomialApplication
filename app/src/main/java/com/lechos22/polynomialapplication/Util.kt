package com.lechos22.polynomialapplication

import kotlin.math.roundToLong
import kotlin.math.sqrt

fun divisors(x: Double): Set<Double> =
    (1..sqrt(x).roundToLong())
        .filter { x % it.toDouble() == 0.0 }
        .map(Long::toDouble)
        .flatMap { listOf(it, x/it) }
        .toSet()

fun plusMinus(xs: Collection<Double>): List<Double> = xs.flatMap { listOf(it, -it) }
