package com.example.recyclifyapp.network

import com.example.recyclifyapp.model.ProductInfo
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface ApiService {
    @Headers(
        "Content-Type: application/x-www-form-urlencoded"
    )
    @GET("product/{productStr}")
    suspend fun getProduct(@Path("productStr", encoded = true)productStr: String): ProductInfo
}