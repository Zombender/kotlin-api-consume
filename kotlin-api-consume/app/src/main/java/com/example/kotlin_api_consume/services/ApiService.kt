package com.example.kotlin_api_consume.services
import retrofit2.http.GET
import com.example.kotlin_api_consume.models.Product

interface ApiService {
    @GET("products/")
    suspend fun getProducts(): List<Product>
}