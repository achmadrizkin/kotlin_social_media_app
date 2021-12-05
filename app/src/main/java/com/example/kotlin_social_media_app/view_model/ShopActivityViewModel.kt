package com.example.kotlin_social_media_app.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_social_media_app.model.product.ProductList
import com.example.kotlin_social_media_app.repository.ShopRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShopActivityViewModel @Inject constructor(private val repository: ShopRepository): ViewModel() {
    var productList: MutableLiveData<ProductList>
    var searchProductByName: MutableLiveData<ProductList>

    init {
        productList = MutableLiveData()
        searchProductByName = MutableLiveData()
    }

    fun getAllProductListObservable(): MutableLiveData<ProductList> {
        return productList
    }

    fun searchProductByNameObservable(): MutableLiveData<ProductList> {
        return searchProductByName
    }

    fun getAllProductListOfData() {
        repository.getProductListFromApiCall(productList)
    }

    fun searchProductByNameOfData(name_product: String) {
        repository.searchProductByNameFromApiCall(name_product, searchProductByName)
    }
}