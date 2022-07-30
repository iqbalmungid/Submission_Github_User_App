package com.iqbalmungid.githubuserapi.ui.fragmentfollow

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iqbalmungid.githubuserapi.data.remote.api.Client
import com.iqbalmungid.githubuserapi.data.remote.response.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel: ViewModel() {
    val following = MutableLiveData<ArrayList<User>>()

    fun setFollowing(uname: String){
        Client.apiInstance
            .getFollowingUser(uname)
            .enqueue(object : Callback<ArrayList<User>>{
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    if (response.isSuccessful){
                        following.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }

            })
    }
    fun getFollowing(): LiveData<ArrayList<User>>{
        return following
    }
}