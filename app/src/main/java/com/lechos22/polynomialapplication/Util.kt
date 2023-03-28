package com.lechos22.polynomialapplication

import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun divisors(x: AccurateNumber): Set<AccurateNumber> =
    (1..x.sqrt().toInt())
        .filter { x % it.toDouble() == 0.0 }
        .map(Int::toDouble)
        .map(::AccurateNumber)
        .flatMap { listOf(it, x/it) }
        .toSet()

fun plusMinus(xs: Set<AccurateNumber>): List<AccurateNumber> = xs.flatMap { listOf(it, -it) }
