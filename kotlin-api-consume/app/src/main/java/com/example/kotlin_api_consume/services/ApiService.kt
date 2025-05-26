package com.example.api_conn.data
import retrofit2.http.GET

interface ApiService {
    @GET("products/")
    suspend fun getProducts(): List<Product>
}