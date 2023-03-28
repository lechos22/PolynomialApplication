package com.lechos22.polynomialapplication

import org.junit.Assert
import org.junit.Test

class BasicPolynomialTests {
    @Test
    fun isZeroTest() {
        assert(Polynomial().isZero())
        assert(
            Polynomial(
                Pair(1, BigFract.ZERO)
            ).isZero()
        )
    }

    @Test
    fun degreeTest() {
        assert(Polynomial().degree() == 0)
        assert(
            Polynomial(
            Pair(1, BigFract(2.0))
        ).degree() == 1)
        assert(
            Polynomial(
            Pair(3, BigFract(3.0)),
            Pair(5, BigFract(4.0))
        ).degree() == 5)
        assert(
            Polynomial(
            Pair(6, BigFract(5.0)),
            Pair(2, BigFract(6.0))
        ).degree() == 6)
    }

    @Test
    fun equalsTest() {
        assert(
            Polynomial(
                Pair(1, BigFract.ONE)
            ) == Polynomial(
                Pair(1, BigFract.ONE)
            )
        )
    }

    @Test
    fun notEqualsTest() {
        assert(
            Polynomial(
                Pair(2, BigFract.ONE)
            ) != Polynomial(
                Pair(1, BigFract.ONE)
            )
        )
        assert(
            Polynomial(
                Pair(1, BigFract(2.0))
            ) != Polynomial(
                Pair(1, BigFract.ONE)
            )
        )
        assert(
            Polynomial(Pair(1, BigFract(2.0))) != Polynomial()
        )
        assert(
            Polynomial() != Polynomial(Pair(1, BigFract(2.0)))
        )
    }

    @Test
    fun cloneTest() {
        val polynomial = Polynomial()
        val polynomial2 = polynomial.clone()
        Assert.assertEquals(polynomial, polynomial2)
        polynomial2 += Polynomial(Pair(1, BigFract.ONE))
        Assert.assertNotEquals(polynomial, polynomial2)
    }

    @Test
    fun toStringTest() {
        Assert.assertEquals(
            "0",
            Polynomial().toString()
        )
        Assert.assertEquals("1",
            Polynomial(
                Pair(0, BigFract.ONE)
            ).toString()
        )
        Assert.assertEquals(
            "x^2",
            Polynomial(
                Pair(2, BigFract.ONE)
            ).toString()
        )
        Assert.assertEquals(
            "x",
            Polynomial(
                Pair(1, BigFract.ONE)
            ).toString()
        )
        Assert.assertEquals(
            "x + 1",
            Polynomial(
                Pair(1, BigFract.ONE),
                Pair(0, BigFract.ONE)
            ).toString()
        )
    }

    @Test
    fun serializeTest() {
        Assert.assertEquals(
            "1,5.0;0,1.3",
            Polynomial(
                Pair(1, BigFract(5.0)),
                Pair(0, BigFract(1.3))
            ).serialize()
        )
    }

    @Test
    fun deserializeTest() {
        Assert.assertEquals(
            "1,5.0;0,1.3",
            Polynomial.deserialize("1,5.0;0,1.3").serialize()
        )
    }
}