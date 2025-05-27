package com.example.kotlin_api_consume.repository

import com.example.kotlin_api_consume.models.Product
import com.example.kotlin_api_consume.retrofit.RetrofitInstance
class ProductRepository {
    suspend fun getProducts(): List<Product> {
        return RetrofitInstance.api.getProducts()
    }
}
