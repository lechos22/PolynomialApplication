package com.lechos22.polynomialapplication

import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope

class PolynomialListSaver : Saver<List<Polynomial>, String> {
    override fun SaverScope.save(value: List<Polynomial>): String =
        value.map { it.serialize() + "|" }
            .fold("", String::plus)
            .substringBeforeLast("|")
    override fun restore(value: String): List<Polynomial> =
        value.split("|")
            .map(Polynomial.Companion::deserialize)
}
