package com.dicoding.ittelkom.githubuser.ui.follow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.ittelkom.githubuser.databinding.FollowerFragmentBinding
import com.dicoding.ittelkom.githubuser.model.UserResponse
import com.dicoding.ittelkom.githubuser.data.Resource
import com.dicoding.ittelkom.githubuser.data.ViewStateCallBack
import com.dicoding.ittelkom.githubuser.ui.adapter.ListUserAdapter


class FollowerFragment : Fragment(), ViewStateCallBack<List<UserResponse>> {

    private var _followerBinding: FollowerFragmentBinding? = null
    private lateinit var viewModel: FollowerViewModel
    private lateinit var userAdapter: ListUserAdapter
    private var username: String? = null
    private val followerBinding get() = _followerBinding!!

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
        _followerBinding = FollowerFragmentBinding.inflate(inflater, container, false)
        return followerBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[FollowerViewModel::class.java]
        userAdapter = ListUserAdapter()
        followerBinding.rvViewFollowers.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
        viewModel.getUserFollowers(username.toString()).observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> onFailed(it.message)
                is Resource.Loading -> onLoading()
                is Resource.Success -> it.data?.let { it1 -> onSuccess(it1) }
            }
        }
    }

    override fun onSuccess(data: List<UserResponse>) {
        userAdapter.setAllData(data)
        followerBinding.apply {
            tvMessage.visibility = invisible
            progressbarFollowers.visibility = invisible
            rvViewFollowers.visibility = visible
        }
    }

    override fun onLoading() {
        followerBinding.apply {
            tvMessage.visibility = invisible
            progressbarFollowers.visibility = visible
            rvViewFollowers.visibility = invisible

        }
    }

    override fun onFailed(message: String?) {
        followerBinding.apply {
            if (message == null) {
                tvMessage.text = "$username Tidak ada yang Mengikuti"
                tvMessage.visibility = visible
            } else {
                tvMessage.text = message
                tvMessage.visibility = visible
            }
            progressbarFollowers.visibility = invisible
            rvViewFollowers.visibility = invisible
        }
    }

    companion object {
        fun getInstance(username: String): Fragment {
            return FollowerFragment().apply {
                arguments = Bundle().apply {
                    putString("USERNAME", username)
                }
            }
        }
    }
}