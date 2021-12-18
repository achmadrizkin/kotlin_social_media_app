package com.example.kotlin_social_media_app.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_social_media_app.model.explore.ExploreList
import com.example.kotlin_social_media_app.model.like.Like
import com.example.kotlin_social_media_app.model.like.LikeList
import com.example.kotlin_social_media_app.repository.LikeRepo
import com.example.kotlin_social_media_app.repository.PostDetailsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PostDetailsActivityViewModel @Inject constructor(private val repository: PostDetailsRepo, private val likeRepository: LikeRepo): ViewModel() {
    var exploreList: MutableLiveData<ExploreList>
    var likeLiveData: MutableLiveData<Like>
    var likeListLiveData: MutableLiveData<LikeList>

    init {
        exploreList = MutableLiveData()
        likeLiveData = MutableLiveData()
        likeListLiveData = MutableLiveData()
    }

    fun getExploreObservable(): MutableLiveData<ExploreList> {
        return exploreList
    }

    fun getExploreListOfData(user: String) {
        repository.getExploreOrderByLikeFromApiCall(user, exploreList)
    }

    fun postLikeToUserPostObservable(): MutableLiveData<Like> {
        return likeLiveData
    }

    fun postLikeToUserPostOfData(like: Like) {
        likeRepository.postLikeToUserPostFromApiCallFromApiCall(like, likeLiveData)
    }

    fun getLikeByUserObservable(): MutableLiveData<LikeList> {
        return likeListLiveData
    }

    fun getLikeByUserOfData(id: String, email: String) {
        likeRepository.getLikeByUserFromApiCall(id, email, likeListLiveData)
    }
}