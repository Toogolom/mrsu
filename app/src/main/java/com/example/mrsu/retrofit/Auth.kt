package com.example.mrsu.retrofit

data class Auth(
    val grant_type:String,
    val client_id: String,
    val client_secret: String,
    val username : String,
    val password : String
)
