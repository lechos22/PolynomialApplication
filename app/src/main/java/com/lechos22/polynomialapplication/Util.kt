package com.lechos22.polynomialapplication

import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun divisors(x: BigFract): Set<BigFract> =
    (1..x.sqrt().toInt())
        .filter { x % it.toDouble() == 0.0 }
        .map(Int::toDouble)
        .map(::BigFract)
        .flatMap { listOf(it, x/it) }
        .toSet()

fun plusMinus(xs: Set<BigFract>): List<BigFract> = xs.flatMap { listOf(it, -it) }
