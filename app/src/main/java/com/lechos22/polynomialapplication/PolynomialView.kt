package com.lechos22.polynomialapplication

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.lechos22.polynomialapplication.ui.theme.PolynomialApplicationTheme
import org.apache.commons.numbers.fraction.BigFraction
import java.math.BigInteger

@Composable
fun FractionView(fraction: BigFraction, baseFontSize: TextUnit){
    if(fraction.denominator.signum() != fraction.denominator.signum())
        Text("-", fontSize = baseFontSize)
    if(fraction.denominator.abs() == BigInteger.ONE)
        Text(fraction.numerator.abs().toString(), Modifier.padding(2.dp), fontSize = baseFontSize)
    else {
        val color = MaterialTheme.colorScheme.onBackground
        Column(
            Modifier
                .padding(4.dp)
                .drawBehind {
                    val strokeWidth = Stroke.DefaultMiter
                    val y = (size.height - strokeWidth) / 2
                    drawLine(
                        color = color,
                        start = Offset(0f, y),
                        end = Offset(size.width, y),
                        strokeWidth = strokeWidth
                    )
                }
        ) {
            Text(fraction.numerator.abs().toString(), fontSize = baseFontSize / 2)
            Text(fraction.denominator.abs().toString(), fontSize = baseFontSize / 2)
        }
    }
}

@Composable
fun PolynomialView(polynomial: Polynomial) {
    val baseFontSize = MaterialTheme.typography.bodySmall.fontSize * 2
    LazyRow(modifier = Modifier.padding(vertical = 7.dp, horizontal = 2.dp)) {
        if (polynomial.isZero()) {
            item { Text("0") }
        } else {
            val firstKey = polynomial.coefficients.firstKey()
            val superscript = SpanStyle(
                baselineShift = BaselineShift.Superscript,
                fontSize = baseFontSize / 2, // font size of subscript
            )
            polynomial.coefficients.forEach { (k, v) ->
                if (k != firstKey) {
                    if (v.bigFraction.signum() == -1)
                        item { Text("-", Modifier.padding(2.dp), fontSize = baseFontSize) }
                    else
                        item { Text("+", Modifier.padding(2.dp), fontSize = baseFontSize) }
                }
                if (v != AccurateNumber.ONE || k == 0)
                    item { FractionView(fraction = v.bigFraction.abs(), baseFontSize) }
                if (k == 1) {
                    item { Text("x", Modifier.padding(2.dp), fontSize = baseFontSize)}
                } else if (k != 0) {
                    item {
                        Text(buildAnnotatedString {
                            append("x")
                            withStyle(superscript) {
                                append(k.toString())
                            }
                        }, Modifier.padding(2.dp), fontSize = baseFontSize)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
fun PolynomialPreview() =
    PolynomialApplicationTheme {
        PolynomialView(
            Polynomial(
                Pair(2, AccurateNumber.ONE),
                Pair(1, AccurateNumber.ONE),
                Pair(0, AccurateNumber.ONE)
            )
        )
    }
