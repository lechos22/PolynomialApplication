package com.lechos22.polynomialapplication

import org.junit.Assert
import org.junit.Test

class BasicPolynomialTests {
    @Test
    fun isZeroTest() {
        assert(Polynomial().isZero())
        assert(
            Polynomial(
                Pair(1, AccurateNumber.ZERO)
            ).isZero()
        )
    }

    @Test
    fun degreeTest() {
        assert(Polynomial().degree() == 0)
        assert(
            Polynomial(
            Pair(1, AccurateNumber(2))
        ).degree() == 1)
        assert(
            Polynomial(
            Pair(3, AccurateNumber(3)),
            Pair(5, AccurateNumber(4))
        ).degree() == 5)
        assert(
            Polynomial(
            Pair(6, AccurateNumber(5)),
            Pair(2, AccurateNumber(6))
        ).degree() == 6)
    }

    @Test
    fun equalsTest() {
        assert(
            Polynomial(
                Pair(1, AccurateNumber.ONE)
            ) == Polynomial(
                Pair(1, AccurateNumber.ONE)
            )
        )
    }

    @Test
    fun notEqualsTest() {
        assert(
            Polynomial(
                Pair(2, AccurateNumber.ONE)
            ) != Polynomial(
                Pair(1, AccurateNumber.ONE)
            )
        )
        assert(
            Polynomial(
                Pair(1, AccurateNumber(2))
            ) != Polynomial(
                Pair(1, AccurateNumber.ONE)
            )
        )
        assert(
            Polynomial(Pair(1, AccurateNumber(2))) != Polynomial()
        )
        assert(
            Polynomial() != Polynomial(Pair(1, AccurateNumber(2)))
        )
    }

    @Test
    fun cloneTest() {
        val polynomial = Polynomial()
        val polynomial2 = polynomial.clone()
        Assert.assertEquals(polynomial, polynomial2)
        polynomial2 += Polynomial(Pair(1, AccurateNumber.ONE))
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
                Pair(0, AccurateNumber.ONE)
            ).toString()
        )
        Assert.assertEquals(
            "x^2",
            Polynomial(
                Pair(2, AccurateNumber.ONE)
            ).toString()
        )
        Assert.assertEquals(
            "x",
            Polynomial(
                Pair(1, AccurateNumber.ONE)
            ).toString()
        )
        Assert.assertEquals(
            "x + 1",
            Polynomial(
                Pair(1, AccurateNumber.ONE),
                Pair(0, AccurateNumber.ONE)
            ).toString()
        )
    }

    @Test
    fun serializeTest() {
        Assert.assertEquals(
            "1,5.0;0,1.3",
            Polynomial(
                Pair(1, AccurateNumber(5)),
                Pair(0, AccurateNumber(1.3))
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