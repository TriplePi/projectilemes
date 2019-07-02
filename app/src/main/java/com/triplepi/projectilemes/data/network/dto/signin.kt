package com.triplepi.projectilemes.data.network.dto

import com.google.gson.annotations.SerializedName

class UserDataDto(
    @SerializedName("username") val userName: String,
    val password: String,
    val jwt: String,
    val name: String,
    val id: Long
)

class TokenDto(
    token: String
)