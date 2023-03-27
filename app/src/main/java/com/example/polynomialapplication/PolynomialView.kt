package com.example.polynomialapplication

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.polynomialapplication.ui.theme.PolynomialApplicationTheme

@Composable
fun PolynomialView(polynomial: Polynomial) =
    Box(modifier = Modifier.padding(vertical = 7.dp, horizontal = 2.dp)) {
        Text(
            text = polynomial.roundedString(6)
        )
    }

@Preview(showBackground = false)
@Composable
fun PolynomialPreview() =
    PolynomialApplicationTheme {
        PolynomialView(
            Polynomial(
                Pair(2, 1.0),
                Pair(1, 1.0),
                Pair(0, 1.0)
            )
        )
    }
