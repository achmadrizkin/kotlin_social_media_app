package com.example.kotlin_social_media_app.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_social_media_app.model.comment.Comment
import com.example.kotlin_social_media_app.model.comment.CommentList
import com.example.kotlin_social_media_app.model.explore.ExploreList
import com.example.kotlin_social_media_app.repository.CommentRepo
import com.example.kotlin_social_media_app.repository.PostDetailsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CommentActivityViewModel @Inject constructor(private val repository: CommentRepo): ViewModel() {
    var commentList: MutableLiveData<CommentList>
    lateinit var postComment: MutableLiveData<Comment>

    init {
        commentList = MutableLiveData()
        postComment = MutableLiveData()
    }

    fun getCommentObservable(): MutableLiveData<CommentList> {
        return commentList
    }

    fun postCommentObservable(): MutableLiveData<Comment> {
        return postComment
    }

    fun getCommentListOfData(postUser: String, toId: String) {
        repository.getCommentByToUserAndDescAndOrderByCreateAtFromApiCall(postUser, toId, commentList)
    }

    fun postCommentOfData(comment: Comment) {
        repository.postCommentFromApiCall(comment, postComment)
    }
}