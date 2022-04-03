package com.dicoding.ittelkom.githubuser.data
import android.view.View
interface ViewStateCallBack<T> {
    fun onSuccess(data: T)
    fun onLoading()
    fun onFailed(message: String?)

    val invisible: Int
        get() = View.INVISIBLE

    val visible: Int
        get() = View.VISIBLE
}