package com.dicoding.ittelkom.githubuser.ui.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.ittelkom.githubuser.R
import com.dicoding.ittelkom.githubuser.ui.follow.FollowerFragment
import com.dicoding.ittelkom.githubuser.ui.follow.FollowingFragment


class FollowPagerAdapter(activity: AppCompatActivity, private val username: String) : FragmentStateAdapter(activity) {

    private val tabTitles = intArrayOf(
        R.string.followers,
        R.string.following
    )

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowerFragment.getInstance(username)
            1 -> fragment = FollowingFragment.getInstance(username)
        }
        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return tabTitles.size
    }
}
