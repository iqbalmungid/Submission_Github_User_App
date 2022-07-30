package com.iqbalmungid.githubuserapi.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.iqbalmungid.githubuserapi.data.local.entity.FavEntity
import com.iqbalmungid.githubuserapi.data.local.room.FavDao
import com.iqbalmungid.githubuserapi.data.local.room.FavDatabase

class FavoriteViewModel(application: Application): AndroidViewModel(application) {
    private var userFavDao: FavDao?
    private var userFavDb: FavDatabase?

    init {
        userFavDb = FavDatabase.getInstance(application)
        userFavDao = userFavDb?.favDao()
    }

    fun getFavoriteUser(): LiveData<List<FavEntity>>? {
        return userFavDao?.getFavoriteUser()
    }

}