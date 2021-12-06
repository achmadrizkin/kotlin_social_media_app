package com.example.kotlin_social_media_app.repository

import androidx.lifecycle.MutableLiveData
import com.example.kotlin_social_media_app.model.product.ProductList
import com.example.kotlin_social_media_app.network.RetrofitService
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProductDetailsRepo @Inject constructor(private val retrofitService: RetrofitService) {

}