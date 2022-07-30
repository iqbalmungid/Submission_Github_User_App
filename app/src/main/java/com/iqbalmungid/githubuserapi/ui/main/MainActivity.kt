package com.iqbalmungid.githubuserapi.ui.main

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.iqbalmungid.githubuserapi.R
import com.iqbalmungid.githubuserapi.data.local.datastore.SettingPreferences
import com.iqbalmungid.githubuserapi.data.remote.response.User
import com.iqbalmungid.githubuserapi.databinding.ActivityMainBinding
import com.iqbalmungid.githubuserapi.ui.detail.DetailUserActivity
import com.iqbalmungid.githubuserapi.ui.favorite.FavoriteActivity
import com.iqbalmungid.githubuserapi.ui.settings.SettingsActivity
import com.iqbalmungid.githubuserapi.ui.settings.SettingsViewModel
import com.iqbalmungid.githubuserapi.ui.settings.SettingsViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewMainModel: MainActivityViewModel
    private lateinit var adapter: ListUserAdapter
    private lateinit var settings: SettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()
        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                Intent(this@MainActivity, DetailUserActivity::class.java).also {
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
            if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
                rvUsers.layoutManager = GridLayoutManager(this@MainActivity, 2)
            } else {
                rvUsers.layoutManager = LinearLayoutManager(this@MainActivity)
            }
            rvUsers.setHasFixedSize(true)
            rvUsers.adapter = adapter

            btnSearch.setOnClickListener {
                search()
            }
            editQuery.setOnKeyListener { view, i, keyEvent ->
                if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER){
                    search()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }

        viewMainModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainActivityViewModel::class.java)
        viewMainModel.getSearchUsers().observe(this, {
            if (it != null){
                adapter.setList(it)
                load(false)
            }
        })

        val pref = SettingPreferences.getInstance(dataStore)
        settings = ViewModelProvider(this, SettingsViewModelFactory(pref)).get(SettingsViewModel::class.java)
        settings.getThemeSettings().observe(this,{
                isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        })
    }

    private fun search(){
        binding.apply {
            val query = editQuery.text.toString()
            if (query.isEmpty()) return
            load(true)
            viewMainModel.setSearchUsers(query)
        }
    }

    private fun load(state: Boolean){
        if (state){
            binding.progress.visibility = View.VISIBLE
        }else {
            binding.progress.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.fav_menu -> {
                Intent(this, FavoriteActivity::class.java).also {
                    startActivity(it)
                }
            }

            R.id.settings -> {
                Intent(this, SettingsActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}