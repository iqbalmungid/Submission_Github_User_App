package com.iqbalmungid.githubuserapi.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iqbalmungid.githubuserapi.data.remote.api.Client
import com.iqbalmungid.githubuserapi.data.remote.response.User
import com.iqbalmungid.githubuserapi.data.remote.response.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityViewModel: ViewModel() {
    val listUser = MutableLiveData<ArrayList<User>>()

    fun setSearchUsers(query: String) {
        Client.apiInstance
            .getSearchUsers(query)
            .enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful) {
                        listUser.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }

            })
    }

    fun getSearchUsers(): LiveData<ArrayList<User>> {
        return listUser
    }
}