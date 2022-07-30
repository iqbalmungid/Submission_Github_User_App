package com.iqbalmungid.githubuserapi.ui.favorite

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.iqbalmungid.githubuserapi.data.local.entity.FavEntity
import com.iqbalmungid.githubuserapi.data.remote.response.User
import com.iqbalmungid.githubuserapi.databinding.ActivityFavoriteBinding
import com.iqbalmungid.githubuserapi.ui.detail.DetailUserActivity
import com.iqbalmungid.githubuserapi.ui.main.ListUserAdapter

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: ListUserAdapter
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.title = "Favorite User"

        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()
        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                Intent(this@FavoriteActivity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EX_UNAME, data.login)
                    it.putExtra(DetailUserActivity.EX_ID, data.id)
                    it.putExtra(DetailUserActivity.AVATAR, data.avatar_url)
                    it.putExtra(DetailUserActivity.LINK, data.html_url)
                    startActivity(it)
                }
                Toast.makeText(applicationContext, data.login, Toast.LENGTH_SHORT).show()
            }
        })

        binding.apply {
            rvUsers.setHasFixedSize(true)
            if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
                rvUsers.layoutManager = GridLayoutManager(this@FavoriteActivity, 2)
            } else {
                rvUsers.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            }
            rvUsers.adapter = adapter
        }

        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        viewModel.getFavoriteUser()?.observe(this, {
            if (it != null){
                val list = List(it)
                adapter.setList(list)
            }
        })
    }

    private fun List(users: List<FavEntity>): ArrayList<User> {
        val listUsers = ArrayList<User>()
        for (user in users){
            val userData = User(
                user.login,
                user.id,
                user.avatar_url,
                user.html_url
            )
            listUsers.add(userData)
        }
        return listUsers
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFavoriteUser()?.observe(this, {
            if (it != null){
                val list = List(it)
                adapter.setList(list)
            }
        })
    }
}