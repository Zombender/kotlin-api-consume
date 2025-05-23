package com.example.kotlin_api_consume.services
import retrofit2.http.GET

interface ApiService {
    @GET("/api/users")
    suspend fun getUser(){
        Call<UserResponde> getUsers();
    }

}