package com.example.polynomialapplication

import org.junit.Assert
import org.junit.Test

class BasicPolynomialTests {
    @Test
    fun isZeroTest() {
        assert(Polynomial().isZero())
        assert(
            Polynomial(
                Pair(1, 0.0)
            ).isZero()
        )
    }

    @Test
    fun degreeTest() {
        assert(Polynomial().degree() == 0)
        assert(Polynomial(
            Pair(1, 2.0)
        ).degree() == 1)
        assert(Polynomial(
            Pair(3, 3.0),
            Pair(5, 4.0)
        ).degree() == 5)
        assert(Polynomial(
            Pair(6, 5.0),
            Pair(2, 6.0)
        ).degree() == 6)
    }

    @Test
    fun equalsTest() {
        assert(
            Polynomial(
                Pair(1, 1.0)
            ) == Polynomial(
                Pair(1, 1.0)
            )
        )
    }

    @Test
    fun notEqualsTest() {
        assert(
            Polynomial(
                Pair(2, 1.0)
            ) != Polynomial(
                Pair(1, 1.0)
            )
        )
        assert(
            Polynomial(
                Pair(1, 2.0)
            ) != Polynomial(
                Pair(1, 1.0)
            )
        )
        assert(
            Polynomial(Pair(1, 2.0)) != Polynomial()
        )
        assert(
            Polynomial() != Polynomial(Pair(1, 2.0))
        )
    }

    @Test
    fun cloneTest() {
        val polynomial = Polynomial()
        val polynomial2 = polynomial.clone()
        Assert.assertEquals(polynomial, polynomial2)
        polynomial2 += Polynomial(Pair(1, 1.0))
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
                Pair(0, 1.0)
            ).toString()
        )
        Assert.assertEquals(
            "x^2",
            Polynomial(
                Pair(2, 1.0)
            ).toString()
        )
        Assert.assertEquals(
            "x",
            Polynomial(
                Pair(1, 1.0)
            ).toString()
        )
        Assert.assertEquals(
            "x + 1",
            Polynomial(
                Pair(1, 1.0),
                Pair(0, 1.0)
            ).toString()
        )
    }

    @Test
    fun serializeTest() {
        Assert.assertEquals(
            "1,5.0;0,1.3",
            Polynomial(
                Pair(1, 5.0),
                Pair(0, 1.3)
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