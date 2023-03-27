package com.lechos22.polynomialapplication

import org.junit.Assert
import org.junit.Test

class OperationsPolynomialTests {
    @Test
    fun plusTest() {
        assert(
            Polynomial(Pair(1, 1.0)) + Polynomial(Pair(1, 1.0))
                == Polynomial(Pair(1, 2.0))
        )
        assert(
            Polynomial(Pair(1, 1.0)) + Polynomial(Pair(2, 1.0))
                == Polynomial(Pair(1, 1.0), Pair(2, 1.0))
        )
    }

    @Test
    fun unaryMinusTest() {
        assert(
            -Polynomial(Pair(1, 1.0))
                == Polynomial(Pair(1, -1.0))
        )
    }

    @Test
    fun minusTest() {
        assert(
            (Polynomial(Pair(1, 1.0)) - Polynomial(Pair(1, 1.0)))
                .isZero()
        )
        assert(
            Polynomial(Pair(1, 2.0)) - Polynomial(Pair(1, 1.0))
                == Polynomial(Pair(1, 1.0))
        )
        assert(
            Polynomial(Pair(1, 1.0)) - Polynomial(Pair(2, 1.0))
                == Polynomial(Pair(1, 1.0), Pair(2, -1.0))
        )
    }

    @Test
    fun timesDoubleTest() {
        assert(
            Polynomial(Pair(1, 1.0)) * 5.0
                == Polynomial(Pair(1, 5.0))
        )
    }

    @Test
    fun timesPolynomialTest() {
        Assert.assertEquals(
            Polynomial(Pair(0, 1.0)),
            Polynomial(Pair(0, 1.0))
                * Polynomial(Pair(0, 1.0))
        )
        Assert.assertEquals(
            Polynomial(Pair(2, 1.0)),
            Polynomial(Pair(1, 1.0))
                * Polynomial(Pair(1, 1.0))
        )
        Assert.assertEquals(
            Polynomial.fromString("2x^3+17x^2+22x+7"),
            Polynomial.fromString("2x^2+3x+1") * Polynomial.fromString("x+7")
        )
    }

    @Test
    fun divDoubleTest() {
        Assert.assertEquals(
            Polynomial(Pair(1, 0.2)),
            Polynomial(Pair(1, 1.0)) / 5.0
        )
    }

    @Test
    fun divRemTest() {
        Assert.assertEquals(
            Pair(
                Polynomial(
                    Pair(1, 1.0)
                ),
                Polynomial(
                    Pair(1, 1.0),
                    Pair(0, 1.0)
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
                Polynomial(Pair(3, 1.0 / 7.0), Pair(0, 1.0 / 7.0)),
                Polynomial()
            ),
            Polynomial.fromString("x^3+1")
                .divRem(Polynomial.fromString("7"))
        )
    }
}