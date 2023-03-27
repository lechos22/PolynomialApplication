package com.example.polynomialapplication

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.polynomialapplication.ui.theme.PolynomialApplicationTheme

@Composable
fun MyButton(
    icon: ImageVector?,
    text: String,
    onClick: () -> Unit
) =
    Button(
        onClick = onClick,
        contentPadding = PaddingValues(
            start = 20.dp,
            top = 12.dp,
            end = 20.dp,
            bottom = 12.dp
        ),
        modifier = Modifier.fillMaxWidth(0.3F)
    ) {
        icon?.let {
            Icon(
                it,
                contentDescription = it.name,
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
        }
        Text(text)
    }

@Preview(showBackground = false)
@Composable
fun MyButtonPreview() =
    PolynomialApplicationTheme {
        MyButton(
            icon = null,
            text = "Button w/o icon"
        ) {
            println("click")
        }
    }

@Preview(showBackground = false)
@Composable
fun MyButtonIconPreview() =
    PolynomialApplicationTheme {
        MyButton(
            icon = Icons.Filled.Check,
            text = "Button with icon"
        ) {
            println("click")
        }
    }
