package com.example.kotlin_api_consume.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kotlin_api_consume.viewmodel.ProductViewModel

@Composable
fun ProductScreen(viewModel: ProductViewModel = viewModel()) {
    val products = viewModel.products.collectAsState().value

    if (products.isEmpty()) {
        Text("No hay productos", modifier = Modifier.padding(16.dp))
    } else {
        LazyColumn(
            contentPadding = PaddingValues(16.dp)
        ) {
            items(products) { product ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = product.name)
                        Text(text = "Price: $${product.price}")
                        Text(text = "Stock: ${product.stock}")
                    }
                }
            }
        }
    }

}
