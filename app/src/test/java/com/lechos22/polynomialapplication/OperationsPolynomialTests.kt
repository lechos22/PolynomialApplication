package com.lechos22.polynomialapplication

import org.junit.Assert
import org.junit.Test

class OperationsPolynomialTests {
    @Test
    fun plusTest() {
        assert(
            Polynomial(Pair(1, AccurateNumber.ONE)) + Polynomial(Pair(1, AccurateNumber.ONE))
                == Polynomial(Pair(1, AccurateNumber(2.0)))
        )
        assert(
            Polynomial(Pair(1, AccurateNumber.ONE)) + Polynomial(Pair(2, AccurateNumber.ONE))
                == Polynomial(Pair(1, AccurateNumber.ONE), Pair(2, AccurateNumber.ONE))
        )
    }

    @Test
    fun unaryMinusTest() {
        assert(
            -Polynomial(Pair(1, AccurateNumber.ONE))
                == Polynomial(Pair(1, -AccurateNumber.ONE))
        )
    }

    @Test
    fun minusTest() {
        assert(
            (Polynomial(Pair(1, AccurateNumber.ONE)) - Polynomial(Pair(1, AccurateNumber.ONE)))
                .isZero()
        )
        assert(
            Polynomial(Pair(1, AccurateNumber(2.0))) - Polynomial(Pair(1, AccurateNumber.ONE))
                == Polynomial(Pair(1, AccurateNumber.ONE))
        )
        assert(
            Polynomial(Pair(1, AccurateNumber.ONE)) - Polynomial(Pair(2, AccurateNumber.ONE))
                == Polynomial(Pair(1, AccurateNumber.ONE), Pair(2, -AccurateNumber.ONE))
        )
    }

    @Test
    fun timesScalarTest() {
        assert(
            Polynomial(Pair(1, AccurateNumber.ONE)) * AccurateNumber(5)
                == Polynomial(Pair(1, AccurateNumber(5)))
        )
    }

    @Test
    fun timesPolynomialTest() {
        Assert.assertEquals(
            Polynomial(Pair(0, AccurateNumber.ONE)),
            Polynomial(Pair(0, AccurateNumber.ONE))
                * Polynomial(Pair(0, AccurateNumber.ONE))
        )
        Assert.assertEquals(
            Polynomial(Pair(2, AccurateNumber.ONE)),
            Polynomial(Pair(1, AccurateNumber.ONE))
                * Polynomial(Pair(1, AccurateNumber.ONE))
        )
        Assert.assertEquals(
            Polynomial.fromString("2x^3+17x^2+22x+7"),
            Polynomial.fromString("2x^2+3x+1") * Polynomial.fromString("x+7")
        )
    }

    @Test
    fun divScalarTest() {
        Assert.assertEquals(
            Polynomial(Pair(1, AccurateNumber(1, 5))),
            Polynomial(Pair(1, AccurateNumber.ONE)) / AccurateNumber(5)
        )
    }

    @Test
    fun divRemTest() {
        Assert.assertEquals(
            Pair(
                Polynomial(
                    Pair(1, AccurateNumber.ONE)
                ),
                Polynomial(
                    Pair(1, AccurateNumber.ONE),
                    Pair(0, AccurateNumber.ONE)
                )
            ),
            Polynomial.fromString("x^3+2x+1")
                .divRem(Polynomial.fromString("x^2+1"))
        )
        Assert.assertEquals(
            Pair(
                Polynomial.fromString("x^2-7x+49"),
                Polynomial.fromString("-342")
            ),
            Polynomial.fromString("x^3+1")
                .divRem(Polynomial.fromString("x+7"))
        )
        Assert.assertEquals(
            Pair(
                Polynomial(Pair(3, AccurateNumber(1, 7)), Pair(0, AccurateNumber(1, 7))),
                Polynomial()
            ),
            Polynomial.fromString("x^3+1")
                .divRem(Polynomial.fromString("7"))
        )
    }
}