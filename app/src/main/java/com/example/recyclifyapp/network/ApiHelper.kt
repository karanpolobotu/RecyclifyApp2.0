package com.example.recyclifyapp.network

class ApiHelper(private val apiService: ApiService) {

    suspend fun getProduct(productStr: String) = apiService.getProduct(productStr)
}