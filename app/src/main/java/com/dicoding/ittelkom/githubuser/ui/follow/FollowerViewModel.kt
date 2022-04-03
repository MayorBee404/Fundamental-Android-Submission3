package com.dicoding.ittelkom.githubuser.ui.follow
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.ittelkom.githubuser.model.UserResponse
import com.dicoding.ittelkom.githubuser.data.network.ApiConfig
import com.dicoding.ittelkom.githubuser.data.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerViewModel : ViewModel() {

    private val retrofit = ApiConfig.getApiService()
    private val listUser = MutableLiveData<Resource<List<UserResponse>>>()

    fun getUserFollowers(username: String): LiveData<Resource<List<UserResponse>>> {
        listUser.postValue(Resource.Loading())
        retrofit.getUserFollowers(username).enqueue(object : Callback<List<UserResponse>> {
            override fun onResponse(call: Call<List<UserResponse>>, response: Response<List<UserResponse>>) {
                val list = response.body()
                if (list.isNullOrEmpty())
                    listUser.postValue(Resource.Error(null))
                else
                    listUser.postValue(Resource.Success(list))
            }

            override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
                listUser.postValue(Resource.Error(t.message))
            }
        })
        return listUser
    }

}