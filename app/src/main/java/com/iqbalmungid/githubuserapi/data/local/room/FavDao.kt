package com.iqbalmungid.githubuserapi.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.iqbalmungid.githubuserapi.data.local.entity.FavEntity

@Dao
interface FavDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addToFavorite(favEntity: FavEntity)

    @Query("Select * FROM favorite_user ORDER BY login ASC")
    fun getFavoriteUser(): LiveData<List<FavEntity>>

    @Query("Select count(*) FROM favorite_user WHERE favorite_user.id = :id")
    fun checkUser(id: Int): Int

    @Query("DELETE FROM favorite_user WHERE favorite_user.id = :id")
    fun removeFromFavorite(id: Int): Int
}