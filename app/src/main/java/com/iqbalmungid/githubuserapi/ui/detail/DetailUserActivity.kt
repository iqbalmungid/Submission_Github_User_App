package com.iqbalmungid.githubuserapi.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.iqbalmungid.githubuserapi.databinding.ActivityDetailUserBinding
import com.iqbalmungid.githubuserapi.ui.fragmentfollow.FollowAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val uname = intent.getStringExtra(EX_UNAME)
        val id = intent.getIntExtra(EX_ID, 0)
        val avatar = intent.getStringExtra(AVATAR)
        val link = intent.getStringExtra(LINK)
        val bundle = Bundle()
        bundle.putString(EX_UNAME, uname)

        viewModel = ViewModelProvider(this).get(
            DetailUserViewModel::class.java
        )
        viewModel.setUserDetail(uname.toString())
        viewModel.getUserDetail().observe(this, {
            if (it != null) {
                binding.apply {
                    txtName.text = it.name; if (it.name == null) {
                    txtName.text = "-"
                }
                    txtUname.text = it.login
                    txtRepo.text = "Repository : ${it.public_repos}"
                    txtLoc.text = "Location : ${it.location}"; if (it.location == null) {
                    txtLoc.text = "Location : - "
                }
                    txtComp.text = "Company : ${it.company}"; if (it.company == null) {
                    txtComp.text = "Company : - "
                }
                    txtFollowers.text = " ${it.followers} Followers"
                    txtFollowing.text = "${it.following} Following"
                    Glide.with(this@DetailUserActivity)
                        .load(it.avatar_url)
                        .centerCrop()
                        .into(imgPp)
                }
            }
        })

        var checked = false
        CoroutineScope(Dispatchers.IO).launch {
            val i = viewModel.checkUser(id)
            withContext(Dispatchers.Main) {
                if (i != null) {
                    if (i > 0) {
                        binding.btnFav.isChecked = true
                        checked = true
                    } else {
                        binding.btnFav.isChecked = false
                        checked = false
                    }
                }
            }
        }

        binding.btnFav.setOnClickListener {
            checked = !checked
            if (checked) {
                viewModel.addToFavorite(uname.toString(), id, avatar.toString(), link.toString())
            } else {
                viewModel.removeFromFavorite(id)
            }
            binding.btnFav.isChecked = checked
        }

        val followAdapter = FollowAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = followAdapter
            tab.setupWithViewPager(viewPager)
        }

        binding.back.setOnClickListener {
            onBackPressed()
        }
    }

    companion object {
        const val EX_UNAME = "ex_uname"
        const val EX_ID = "ex_id"
        const val AVATAR = "avatar"
        const val LINK = "link"
    }
}