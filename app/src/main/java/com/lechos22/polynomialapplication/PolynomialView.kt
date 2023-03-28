package com.lechos22.polynomialapplication

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lechos22.polynomialapplication.ui.theme.PolynomialApplicationTheme

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun PolynomialView(polynomial: Polynomial) =
    Box(modifier = Modifier.padding(vertical = 7.dp, horizontal = 2.dp)) {
        Text(
            text = polynomial.roundedString()
        )
    }

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
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
