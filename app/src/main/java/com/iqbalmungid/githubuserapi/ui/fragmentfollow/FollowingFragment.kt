package com.iqbalmungid.githubuserapi.ui.fragmentfollow

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.iqbalmungid.githubuserapi.R
import com.iqbalmungid.githubuserapi.databinding.FragmentFollowBinding
import com.iqbalmungid.githubuserapi.ui.detail.DetailUserActivity
import com.iqbalmungid.githubuserapi.ui.main.ListUserAdapter

class FollowingFragment: Fragment(R.layout.fragment_follow) {
    private var bind : FragmentFollowBinding? = null
    private val binding get() = bind!!
    private lateinit var followingViewModel: FollowingViewModel
    private lateinit var adapter: ListUserAdapter
    private lateinit var uname: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind = FragmentFollowBinding.bind(view)

        val args = arguments
        uname = args?.getString(DetailUserActivity.EX_UNAME).toString()

        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            if (requireActivity().applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
                rvUsers.layoutManager = GridLayoutManager(activity, 2)
            } else {
                rvUsers.layoutManager = LinearLayoutManager(activity)
            }
            rvUsers.setHasFixedSize(true)
            rvUsers.adapter = adapter
        }
        load(true)
        followingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowingViewModel::class.java)
        followingViewModel.setFollowing(uname)
        followingViewModel.getFollowing().observe(viewLifecycleOwner, {
            if (it!=null){
                adapter.setList(it)
                load(false)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bind = null
    }

    private fun load(state: Boolean){
        if (state){
            binding.progress.visibility = View.VISIBLE
        }else {
            binding.progress.visibility = View.GONE
        }
    }
}