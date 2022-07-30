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

class FollowersViewModel: ViewModel() {
    val followers = MutableLiveData<ArrayList<User>>()

    fun setFollowers(uname: String){
        Client.apiInstance
            .getFollowersUser(uname)
            .enqueue(object : Callback<ArrayList<User>>{
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    if (response.isSuccessful){
                        followers.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }

            })
    }
    fun getFollowers(): LiveData<ArrayList<User>>{
        return followers
    }
}