package com.dicoding.ittelkom.githubuser.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.ittelkom.githubuser.data.local.UserDao
import com.dicoding.ittelkom.githubuser.data.local.UserDatabase
import com.dicoding.ittelkom.githubuser.data.network.ApiConfig
import com.dicoding.ittelkom.githubuser.data.network.ApiServices
import com.dicoding.ittelkom.githubuser.model.SearchUserResponse
import com.dicoding.ittelkom.githubuser.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository (private val application: Application) {
    private val retrofit : ApiServices
    private val dao : UserDao
    val database : UserDatabase = UserDatabase.getInstance(application)

    init{
        retrofit = ApiConfig.getApiService()
        dao = database.userDao()
    }
    fun searchUser(query: String): LiveData<Resource<List<UserResponse>>> {
        val listUser = MutableLiveData<Resource<List<UserResponse>>>()

        listUser.postValue(Resource.Loading())
        retrofit.searchUsers(query).enqueue(object : Callback<SearchUserResponse> {
            override fun onResponse(
                call: Call<SearchUserResponse>,
                response: Response<SearchUserResponse>
            ) {
                val list = response.body()?.items
                if (list.isNullOrEmpty())
                    listUser.postValue(Resource.Error(null))
                else
                    listUser.postValue(Resource.Success(list))
            }

            override fun onFailure(call: Call<SearchUserResponse>, t: Throwable) {
                listUser.postValue(Resource.Error(t.message))
            }

        })
        return listUser
    }

    suspend fun getDetailUser(username: String): LiveData<Resource<UserResponse>> {

        val user = MutableLiveData<Resource<UserResponse>>()

        if (dao.getFavoriteDetailUser(username) != null) {
            user.postValue(Resource.Success(dao.getFavoriteDetailUser(username)))
        } else {
            retrofit.getDetailUser(username).enqueue(object : Callback<UserResponse> {
                override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                    val result = response.body()
                    user.postValue(Resource.Success(result))
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {

                }
            })
        }

        return user
    }


    suspend fun getFavoriteList(): LiveData<Resource<List<UserResponse>>> {

        val listFavorite = MutableLiveData<Resource<List<UserResponse>>>()
        listFavorite.postValue(Resource.Loading())

        if (dao.getFavoriteListUser().isNullOrEmpty())
            listFavorite.postValue(Resource.Error(null))
        else
            listFavorite.postValue(Resource.Success(dao.getFavoriteListUser()))

        return listFavorite
    }

    suspend fun insertFavoriteUser(user: UserResponse) = dao.insertFavoriteUser(user)

    suspend fun deleteFavoriteUser(user: UserResponse) = dao.deleteFavoriteUser(user)

}