package com.dicoding.ittelkom.githubuser.data.local

import androidx.room.*
import com.dicoding.ittelkom.githubuser.model.UserResponse

@Dao
interface UserDao {
    @Query("SELECT * FROM user ORDER BY username ASC")
    suspend fun getFavoriteListUser(): List<UserResponse>

    @Query("SELECT * FROM user WHERE username = :username")
    suspend fun getFavoriteDetailUser(username: String): UserResponse?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteUser(user: UserResponse)

    @Delete
    suspend fun deleteFavoriteUser(user: UserResponse)
}