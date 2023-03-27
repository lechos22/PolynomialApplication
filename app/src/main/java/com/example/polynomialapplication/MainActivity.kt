package com.example.polynomialapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.polynomialapplication.ui.theme.PolynomialApplicationTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalLayoutApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PolynomialApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var polynomialList by rememberSaveable(
                        stateSaver = PolynomialListSaver(),
                        init = { mutableStateOf(listOf()) }
                    )
                    FlowColumn(
                        modifier = Modifier.padding(10.dp)
                    ) {
                        PolynomialInput {
                            polynomialList = polynomialList + listOf(
                                if(it.trim() == "")
                                    Polynomial()
                                else
                                    Polynomial.fromString(it)
                            )
                        }
                        FlowRow(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth(1F)
                                .padding(5.dp)
                        ) {
                            if(polynomialList.isNotEmpty()) {
                                FlowColumn(Modifier.fillMaxWidth(0.5F)) {
                                    polynomialList.map {
                                        PolynomialView(it)
                                    }
                                }
                                FlowColumn {
                                    val size = polynomialList.size
                                    MyButton(icon = null, text = "Pop") {
                                        polynomialList = polynomialList.subList(0, size - 1)
                                    }
                                    MyButton(icon = null, text = "Clone") {
                                        polynomialList = polynomialList + polynomialList.last()
                                    }
                                    MyButton(icon = null, text = "Factorize") {
                                        polynomialList = polynomialList.subList(0, size - 1) +
                                            polynomialList.last().factorize()
                                    }
                                    if(size >= 2) {
                                        MyButton(icon = null, text = "Swap") {
                                            polynomialList = polynomialList.subList(0, size - 2) +
                                                listOf(
                                                    polynomialList[size - 1],
                                                    polynomialList[size - 2]
                                                )
                                        }
                                        MyButton(icon = null, text = "Add") {
                                            polynomialList = polynomialList.subList(0, size - 2) +
                                                (polynomialList[size - 2] + polynomialList[size - 1])
                                        }
                                        MyButton(icon = null, text = "Subtract") {
                                            polynomialList = polynomialList.subList(0, size - 2) +
                                                (polynomialList[size - 2] - polynomialList[size - 1])
                                        }
                                        MyButton(icon = null, text = "Multiply") {
                                            polynomialList = polynomialList.subList(0, size - 2) +
                                                (polynomialList[size - 2] * polynomialList[size - 1])
                                        }
                                        MyButton(icon = null, text = "Divide") {
                                            polynomialList = polynomialList.subList(0, size - 2) +
                                                (polynomialList[size - 2] / polynomialList[size - 1])
                                        }
                                        MyButton(icon = null, text = "Remainder") {
                                            polynomialList = polynomialList.subList(0, size - 2) +
                                                (polynomialList[size - 2] % polynomialList[size - 1])
                                        }
                                        MyButton(icon = null, text = "Divide with remainder") {
                                            polynomialList = polynomialList.subList(0, size - 2) +
                                                polynomialList[size - 2]
                                                    .divRem(polynomialList[size - 1]).toList()
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
