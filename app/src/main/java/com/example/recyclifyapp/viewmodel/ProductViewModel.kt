package com.example.recyclifyapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclifyapp.model.ProductInfo
import com.example.recyclifyapp.model.State
import com.example.recyclifyapp.network.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductViewModel(private val productRepository: MainRepository) : ViewModel(){
    private val _mainLiveData = MutableLiveData<State<ProductInfo>>()
    val productLiveData: LiveData<State<ProductInfo>> get() = _mainLiveData



    fun getProduct(productStr:String) {
        _mainLiveData.value = State.loading()
        viewModelScope.launch(Dispatchers.IO) {
            val mainResponse = productRepository.getProductInfo(productStr)
            withContext(Main) {

                mainResponse.let { mainData ->
                    if (mainData.status == 1) {
                        _mainLiveData.value = State.success(mainData)
                    } else {
                        _mainLiveData.value = State.error("No data found")
                    }
                } ?: run {
                    _mainLiveData.value = State.error("Something went wrong")
                }


            }
        }
    }
}