package com.iqbalmungid.githubuserapi.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iqbalmungid.githubuserapi.data.local.entity.FavEntity
import com.iqbalmungid.githubuserapi.data.local.room.FavDao
import com.iqbalmungid.githubuserapi.data.local.room.FavDatabase
import com.iqbalmungid.githubuserapi.data.remote.api.Client
import com.iqbalmungid.githubuserapi.data.remote.response.DetailUserResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : AndroidViewModel(application) {
    val user = MutableLiveData<DetailUserResponse>()

    private var userFavDao: FavDao?
    private var userFavDb: FavDatabase?

    init {
        userFavDb = FavDatabase.getInstance(application)
        userFavDao = userFavDb?.favDao()
    }

    fun setUserDetail(username: String) {
        Client.apiInstance
            .getDetailUser(username)
            .enqueue(object : Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    if (response.isSuccessful) {
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }
            })
    }

    fun getUserDetail(): LiveData<DetailUserResponse> {
        return user
    }

    fun addToFavorite(uname: String, id: Int, avatar: String, link: String) {
        CoroutineScope(Dispatchers.IO).launch {
            var user = FavEntity(
                uname,
                id,
                avatar,
                link
            )
            userFavDao?.addToFavorite(user)
        }
    }

    fun checkUser(id: Int) = userFavDao?.checkUser(id)

    fun removeFromFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userFavDao?.removeFromFavorite(id)
        }
    }
}