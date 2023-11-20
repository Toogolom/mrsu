package com.example.mrsu.retrofit

data class AccessToken(
    val access_token: String,
    val token_type: String,
    val expires_in: Long
)