package com.example.kotlin_social_media_app.repository

import androidx.lifecycle.MutableLiveData
import com.example.kotlin_social_media_app.model.product.ProductList
import com.example.kotlin_social_media_app.model.user.UserList
import com.example.kotlin_social_media_app.network.RetrofitService
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ShopRepository @Inject constructor(private val retrofitService: RetrofitService) {
    fun getProductListFromApiCall(userList: MutableLiveData<ProductList>) {
        retrofitService.getAllProductsList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getProductListObserverRx(userList))
    }

    fun searchProductByNameFromApiCall(name_product: String, userList: MutableLiveData<ProductList>) {
        retrofitService.searchProductByName(name_product)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(searchProductByNameObserverRx(userList))
    }

    private fun getProductListObserverRx(userList: MutableLiveData<ProductList>): Observer<ProductList> {
        return object : Observer<ProductList> {
            override fun onSubscribe(d: Disposable) {
                // start progress indicator
            }

            override fun onNext(t: ProductList) {
                userList.postValue(t)
            }

            override fun onError(e: Throwable) {
                userList.postValue(null)
            }

            override fun onComplete() {
                // end progress indicator
            }
        }
    }

    private fun searchProductByNameObserverRx(userList: MutableLiveData<ProductList>): Observer<ProductList> {
        return object : Observer<ProductList> {
            override fun onSubscribe(d: Disposable) {
                // start progress indicator
            }

            override fun onNext(t: ProductList) {
                userList.postValue(t)
            }

            override fun onError(e: Throwable) {
                userList.postValue(null)
            }

            override fun onComplete() {
                // end progress indicator
            }
        }
    }

}