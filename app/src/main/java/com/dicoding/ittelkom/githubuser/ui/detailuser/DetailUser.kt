package com.dicoding.ittelkom.githubuser.ui.detailuser

import android.content.Intent.EXTRA_USER
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.ittelkom.githubuser.R
import com.dicoding.ittelkom.githubuser.data.Resource
import com.dicoding.ittelkom.githubuser.data.ViewStateCallBack
import com.dicoding.ittelkom.githubuser.databinding.DetailUserBinding
import com.dicoding.ittelkom.githubuser.model.UserResponse
import com.dicoding.ittelkom.githubuser.ui.adapter.FollowPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DetailUser : AppCompatActivity(), ViewStateCallBack<UserResponse?> {


    private lateinit var detailUserBinding: DetailUserBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailUserBinding = DetailUserBinding.inflate(layoutInflater)
        setContentView(detailUserBinding.root)
        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            elevation = 0f
        }

        val username = intent.getStringExtra(EXTRA_USER)

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getDetailUser(username.toString()).observe(this@DetailUser) {
                when (it) {
                    is Resource.Error -> onFailed(it.message)
                    is Resource.Loading -> onLoading()
                    is Resource.Success -> onSuccess(it.data)
                }
            }
        }

        val pageAdapter = FollowPagerAdapter(this, username.toString())

        detailUserBinding.apply {
            viewPager.adapter = pageAdapter
            TabLayoutMediator(tabs, viewPager) { tabs, position ->
                tabs.text = resources.getString(tabTitles[position])
            }.attach()

            detailProgressBar.visibility = visible
            viewPager.visibility = invisible
            detailRepository.visibility = invisible
            detailFollowers.visibility = invisible
            detailFollowing.visibility = invisible
            detailUsername.visibility = invisible
            detailName.visibility = invisible
            detailCompany.visibility = invisible
            detailLocation.visibility = invisible
            fabFavorite.visibility = invisible
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }


    override fun onSuccess(data: UserResponse?) {
        detailUserBinding.apply {
            detailRepository.text = "Repository : ${data?.publicRepos.toString()}"
            detailFollowers.text = "Followers : ${data?.followers.toString()}"
            detailFollowing.text = "Following : ${data?.following.toString()}"
            detailUsername.text = data?.username
            detailName.text = data?.name
            detailCompany.text = data?.company
            detailLocation.text = data?.location
            detailRepository.visibility = visible
            detailFollowers.visibility = visible
            detailFollowing.visibility = visible
            detailUsername.visibility = visible
            detailName.visibility = visible
            detailCompany.visibility = visible
            detailLocation.visibility = visible
            detailProgressBar.visibility = invisible
            tabs.visibility = visible
            viewPager.visibility = visible

            detailAvatar.visibility = visible

            Glide.with(this@DetailUser)
                .load(data?.avatarUrl)
                .apply(RequestOptions().override(80, 80))
                .into(detailAvatar)

            supportActionBar?.title = data?.username
            fabFavorite.visibility = View.VISIBLE

            if (data?.isFavorite == true)
                fabFavorite.setImageDrawable(resources.getDrawable(R.drawable.ic_favorite))
            else
                fabFavorite.setImageDrawable(resources.getDrawable(R.drawable.ic_unfavorite))


            fabFavorite.setOnClickListener {
                if (data?.isFavorite == true) {
                    data.isFavorite = false
                    viewModel.deleteFavoriteUser(data)
                    fabFavorite.setImageDrawable(resources.getDrawable(R.drawable.ic_unfavorite))
                } else {
                    data?.isFavorite = true
                    data?.let { it1 -> viewModel.insertFavoriteUser(it1) }
                    fabFavorite.setImageDrawable(resources.getDrawable(R.drawable.ic_favorite))
                }
            }

        }
    }

    override fun onLoading() {
        detailUserBinding.apply {
            detailProgressBar.visibility = visible

        }

    }

    override fun onFailed(message: String?) {
        detailUserBinding.apply {
            fabFavorite.visibility = visible
        }

    }

    companion object {

        private val tabTitles = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }

}