package com.dicoding.ittelkom.githubuser.ui.follow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.ittelkom.githubuser.databinding.FollowingFragmentBinding
import com.dicoding.ittelkom.githubuser.model.UserResponse
import com.dicoding.ittelkom.githubuser.data.Resource
import com.dicoding.ittelkom.githubuser.data.ViewStateCallBack
import com.dicoding.ittelkom.githubuser.ui.adapter.ListUserAdapter


class FollowingFragment : Fragment(), ViewStateCallBack<List<UserResponse>> {

    private var _followingBinding: FollowingFragmentBinding? = null
    private lateinit var userAdapter: ListUserAdapter
    private var username: String? = null
    private lateinit var viewModel: FollowingViewModel
    private val followingBinding get() = _followingBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString("USERNAME")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _followingBinding = FollowingFragmentBinding.inflate(inflater, container, false)
        return followingBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[FollowingViewModel::class.java]
        userAdapter = ListUserAdapter()
        followingBinding.rvViewFollowing.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        viewModel.getUserFollowing(username.toString()).observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> onFailed(it.message)
                is Resource.Loading -> onLoading()
                is Resource.Success -> it.data?.let { it1 -> onSuccess(it1) }
            }
        }
    }

    override fun onSuccess(data: List<UserResponse>) {
        userAdapter.setAllData(data)

        followingBinding.apply {
            tvMessage.visibility = invisible
            progressbarFollowers.visibility = invisible
            rvViewFollowing.visibility = visible
        }
    }

    override fun onLoading() {
        followingBinding.apply {
            tvMessage.visibility = invisible
            progressbarFollowers.visibility = visible
            rvViewFollowing.visibility = invisible
        }
    }

    override fun onFailed(message: String?) {
        followingBinding.apply {
            if (message == null) {
                tvMessage.text = "$username Tidak ada yang Di Ikuti"
                tvMessage.visibility = visible
            } else {
                tvMessage.text = message
                tvMessage.visibility = visible
            }
            progressbarFollowers.visibility = invisible
            rvViewFollowing.visibility = invisible
        }
    }

    companion object {

        fun getInstance(username: String): Fragment {
            return FollowingFragment().apply {
                arguments = Bundle().apply {
                    putString("USERNAME", username)
                }
            }
        }
    }
}
