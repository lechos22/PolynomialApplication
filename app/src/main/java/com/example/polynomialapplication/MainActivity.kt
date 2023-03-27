package com.example.polynomialapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.polynomialapplication.ui.theme.PolynomialApplicationTheme

class MainActivity : ComponentActivity() {
    private fun onArithmeticException(e: ArithmeticException){
        Toast.makeText(this, "ArithmeticException: ${e.message}", Toast.LENGTH_SHORT).show()
        System.err.println("ArithmeticException: ${e.message}")
    }
    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    fun Calculator(modifier: (Modifier) -> Modifier){
        var polynomialList by rememberSaveable(
            stateSaver = PolynomialListSaver(),
            init = { mutableStateOf(listOf()) }
        )
        FlowColumn(
            modifier = Modifier.padding(10.dp).run(modifier)
        ) {
            FlowRow(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth(1F)
                    .fillMaxHeight(0.2F)
            ) {
                PolynomialInput {
                    polynomialList = polynomialList + listOf(
                        if (it.trim() == "")
                            Polynomial()
                        else
                            Polynomial.fromString(it)
                    )
                }
            }
            FlowRow(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth(1F)
                    .fillMaxHeight(0.8F)
                    .padding(5.dp)
            ) {
                if(polynomialList.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(0.5F)
                    ){
                        itemsIndexed(polynomialList) { _, item ->
                            PolynomialView(item)
                        }
                    }
                    FlowColumn(Modifier.fillMaxWidth(0.5F)) {
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
                                try {
                                    polynomialList = polynomialList.subList(0, size - 2) +
                                        (polynomialList[size - 2] / polynomialList[size - 1])
                                } catch (e: ArithmeticException) {
                                    onArithmeticException(e)
                                }
                            }
                            MyButton(icon = null, text = "Remainder") {
                                try {
                                    polynomialList = polynomialList.subList(0, size - 2) +
                                        (polynomialList[size - 2] % polynomialList[size - 1])
                                } catch (e: ArithmeticException) {
                                    onArithmeticException(e)
                                }
                            }
                            MyButton(icon = null, text = "Divide with remainder") {
                                try {
                                    polynomialList =
                                        polynomialList.subList(0, size - 2) +
                                            polynomialList[size - 2]
                                                .divRem(polynomialList[size - 1])
                                                .toList()
                                } catch (e: java.lang.ArithmeticException) {
                                    onArithmeticException(e)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PolynomialApplicationTheme {
                Scaffold(topBar = { CenterAlignedTopAppBar(title = {
                    Text("Polynomial calculator")
                }) }) {
                    Calculator { it.padding(top = 50.dp) }
                }
            }
        }
    }
}
