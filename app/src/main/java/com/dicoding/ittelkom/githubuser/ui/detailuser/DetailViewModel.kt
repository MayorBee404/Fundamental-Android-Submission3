package com.dicoding.ittelkom.githubuser.ui.detailuser

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.ittelkom.githubuser.data.Repository
import com.dicoding.ittelkom.githubuser.model.UserResponse
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    val repository = Repository(application)

    suspend fun getDetailUser(username: String) = repository.getDetailUser(username)

    fun insertFavoriteUser(user: UserResponse) = viewModelScope.launch {
        repository.insertFavoriteUser(user)
    }

    fun deleteFavoriteUser(user: UserResponse) = viewModelScope.launch {
        repository.deleteFavoriteUser(user)
    }
}
