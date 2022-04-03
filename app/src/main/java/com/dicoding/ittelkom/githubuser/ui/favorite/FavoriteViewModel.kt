package com.dicoding.ittelkom.githubuser.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.dicoding.ittelkom.githubuser.data.Repository
import java.lang.Appendable

class FavoriteViewModel(application:Application):AndroidViewModel(application) {
    private val repository = Repository(application)

    suspend fun getFavoriteList() = repository.getFavoriteList()
}