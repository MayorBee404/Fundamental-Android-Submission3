package com.dicoding.ittelkom.githubuser.ui.listuser

import android.app.PendingIntent.getActivity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.ittelkom.githubuser.R
import com.dicoding.ittelkom.githubuser.databinding.ListUserBinding
import com.dicoding.ittelkom.githubuser.model.UserResponse
import com.dicoding.ittelkom.githubuser.data.Resource
import com.dicoding.ittelkom.githubuser.data.ViewStateCallBack
import com.dicoding.ittelkom.githubuser.ui.adapter.ListUserAdapter
import com.dicoding.ittelkom.githubuser.ui.favorite.FavoriteActivity
import com.dicoding.ittelkom.githubuser.ui.setting.SettingActivity


class ListUser : AppCompatActivity(), ViewStateCallBack<List<UserResponse>> {

    private lateinit var listUserBinding: ListUserBinding
    private lateinit var userQuery: String
    private val viewModel by viewModels<ListUserModel>()
    private lateinit var userAdapter: ListUserAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listUserBinding = ListUserBinding.inflate(layoutInflater)
        setContentView(listUserBinding.root)

        userAdapter = ListUserAdapter()
        listUserBinding.includeMainSearch.rvListUser.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(this@ListUser, LinearLayoutManager.VERTICAL, false)

        }

        listUserBinding.searchView.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    userQuery = query.toString()
                    clearFocus()
                    viewModel.searchUser(userQuery).observe(this@ListUser) {
                        when (it) {
                            is Resource.Error -> onFailed(it.message)
                            is Resource.Loading -> onLoading()
                            is Resource.Success -> it.data?.let { it1 -> onSuccess(it1) }
                        }
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.custom_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_settings -> {
                val intent = Intent(this@ListUser, SettingActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.nav_favorite ->{
                val intent = Intent(this@ListUser,FavoriteActivity::class.java)
                startActivity(intent)
                true
            }

        }
        return super.onOptionsItemSelected(item)
    }


    override fun onSuccess(data: List<UserResponse>) {
        userAdapter.setAllData(data)
        listUserBinding.includeMainSearch.apply {
            ivSearchIcon.visibility = invisible
            tvMessage.visibility = invisible
            mainProgressBar.visibility = invisible
            rvListUser.visibility = visible
        }
    }

    override fun onLoading() {
        listUserBinding.includeMainSearch.apply {
            ivSearchIcon.visibility = invisible
            tvMessage.visibility = invisible
            mainProgressBar.visibility = visible
            rvListUser.visibility = invisible
        }
    }

    override fun onFailed(message: String?) {
        listUserBinding.includeMainSearch.apply {
            if (message == null) {
                ivSearchIcon.apply {
                    setImageResource(R.drawable.ic_search_on)
                    visibility = visible
                }
                tvMessage.apply {
                    text = resources.getString(R.string.user_not_found)
                    visibility = visible
                }
            } else {
                ivSearchIcon.apply {
                    setImageResource(R.drawable.ic_baseline_search_24)
                    visibility = visible
                }
                tvMessage.apply {
                    text = message
                    visibility = visible
                }
            }
            mainProgressBar.visibility = invisible
            rvListUser.visibility = invisible
        }
    }


}