package com.iqbalmungid.githubuserapi.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.iqbalmungid.githubuserapi.data.remote.response.User
import com.iqbalmungid.githubuserapi.databinding.ItemRowUserBinding

class ListUserAdapter : RecyclerView.Adapter <ListUserAdapter.ListViewHolder>() {

    private val list = ArrayList<User>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback (onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    fun setList(user: ArrayList<User>){
        list.clear()
        list.addAll(user)
        notifyDataSetChanged()
    }

    inner class ListViewHolder(val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(user: User){
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(user)
            }
            binding.apply {
                Glide.with(itemView)
                    .load(user.avatar_url)
                    .centerCrop()
                    .into(imguserphoto)
                txtitemname.text = user.login
                txtLink.text = user.html_url
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder((view))
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickCallback{
        fun onItemClicked(data: User)
    }
}