package com.dicoding.ittelkom.githubuser.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Entity(tableName = "user")
@Parcelize
data class UserResponse(

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean? = false,

    @field:SerializedName("login")
    @ColumnInfo(name = "username")
    val username: String? = "",

    @field:SerializedName("type")
    @ColumnInfo(name = "type")
    val type: String? = "",

    @field:SerializedName("company")
    @ColumnInfo(name = "company")
    val company: String? = "",

    @field:SerializedName("id")
    @ColumnInfo(name = "id")
    @PrimaryKey
    val id: Int? = 0,

    @field:SerializedName("public_repos")
    @ColumnInfo(name = "publicRepos")
    val publicRepos: Int? = 0,

    @field:SerializedName("followers")
    @ColumnInfo(name = "followers")
    val followers: Int? = 0,

    @field:SerializedName("avatar_url")
    @ColumnInfo(name = "avatarUrl")
    val avatarUrl: String? = "",

    @field:SerializedName("following")
    @ColumnInfo(name = "following")
    val following: Int? = 0,

    @field:SerializedName("name")
    @ColumnInfo(name = "name")
    val name: String? = "",

    @field:SerializedName("location")
    @ColumnInfo(name = "location")
    val location: String? = "",

    ) : Parcelable
