package com.dicoding.ittelkom.githubuser.ui.favorite

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.ittelkom.githubuser.data.Resource
import com.dicoding.ittelkom.githubuser.data.ViewStateCallBack
import com.dicoding.ittelkom.githubuser.databinding.ActivityFavoriteBinding
import com.dicoding.ittelkom.githubuser.model.UserResponse
import com.dicoding.ittelkom.githubuser.ui.adapter.ListUserAdapter
import com.dicoding.ittelkom.githubuser.ui.listuser.ListUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity(), ViewStateCallBack<List<UserResponse>> {
    private lateinit var favoriteBinding: ActivityFavoriteBinding
    private val viewModel: FavoriteViewModel by viewModels()
    private lateinit var userAdapter: ListUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoriteBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(favoriteBinding.root)

        userAdapter = ListUserAdapter()
        favoriteBinding.rvFavorite.apply {
            adapter = userAdapter
            layoutManager =
                LinearLayoutManager(this@FavoriteActivity, LinearLayoutManager.VERTICAL, false)
        }

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getFavoriteList().observe(this@FavoriteActivity) {
                sequenceOf(
                    when (it) {
                        is Resource.Error -> onFailed(it.message)
                        is Resource.Loading -> onLoading()
                        is Resource.Success -> it.data?.let { it1 -> onSuccess(it1) }
                    })
            }
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                elevation = 0f
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val intent = Intent(this@FavoriteActivity,ListUser::class.java)
        startActivity(intent)
        true
        return super.onSupportNavigateUp()
    }

    override fun onSuccess(data: List<UserResponse>) {
        favoriteBinding.apply {
            favoriteProgressBar.visibility = invisible
        }
        userAdapter.setAllData(data)
    }

    override fun onLoading() {
        favoriteBinding.apply {
            favoriteProgressBar.visibility = visible
        }
    }

    override fun onFailed(message: String?) {
        if (message == null) {
            favoriteBinding.apply {
                favoriteProgressBar.visibility = invisible
                rvFavorite.visibility = invisible
                tvFavoriteError.text = "Tidak ada Favorite yang di Tambahkan"
            }
        }
    }

    override fun onResume() {
        super.onResume()
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getFavoriteList().observe(this@FavoriteActivity) {
                sequenceOf(
                    when (it) {
                        is Resource.Error -> onFailed(it.message)
                        is Resource.Loading -> onLoading()
                        is Resource.Success -> it.data?.let { it1 -> onSuccess(it1) }
                    })
            }
        }
    }
}