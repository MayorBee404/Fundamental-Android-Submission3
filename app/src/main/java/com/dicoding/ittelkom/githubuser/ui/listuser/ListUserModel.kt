package com.dicoding.ittelkom.githubuser.ui.listuser
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.ittelkom.githubuser.data.Repository
import com.dicoding.ittelkom.githubuser.model.SearchUserResponse
import com.dicoding.ittelkom.githubuser.model.UserResponse
import com.dicoding.ittelkom.githubuser.data.network.ApiConfig
import com.dicoding.ittelkom.githubuser.data.Resource

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListUserModel (application : Application):AndroidViewModel(application) {
    private val repository = Repository(application)

    fun searchUser(query: String) = repository.searchUser(query)

}