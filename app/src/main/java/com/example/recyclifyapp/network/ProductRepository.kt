package com.example.recyclifyapp.network

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun getProductInfo(productStr: String) = apiHelper.getProduct(productStr)
}