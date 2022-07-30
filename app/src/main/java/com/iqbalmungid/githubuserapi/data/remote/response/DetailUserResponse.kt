package com.iqbalmungid.githubuserapi.data.remote.response

data class DetailUserResponse(
    val login: String,
    val id: Int,
    val avatar_url: String,
    val name: String,
    val company: String,
    val location: String,
    val public_repos: String,
    val following: String,
    val followers: String
)
