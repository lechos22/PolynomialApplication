package com.lechos22.polynomialapplication

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lechos22.polynomialapplication.ui.theme.PolynomialApplicationTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PolynomialInput(onInput: (String) -> Unit) {
    var text by remember { mutableStateOf("") }
    FlowColumn {
        OutlinedTextField(
            value = text,
            onValueChange = { text = it.filter { c ->
                c.isDigit() || c in "x+-^. "
            } },
            label = { Text("Your polynomial") },
            modifier = Modifier.fillMaxWidth(1F)
        )
        FlowRow(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth(1F)
                .padding(top = 5.dp)
        ) {
            MyButton(
                icon = Icons.Filled.Add,
                text = "Append"
            ) {
                onInput(text)
            }
        }

    }
}

@Preview(showBackground = false)
@Composable
fun PolynomialInputPreview() =
    PolynomialApplicationTheme {
        PolynomialInput {
            println(it)
        }
    }
