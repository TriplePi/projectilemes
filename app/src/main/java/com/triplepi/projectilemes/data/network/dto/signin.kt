package com.triplepi.projectilemes.data.network.dto

import com.google.gson.annotations.SerializedName

class UserDataDto(
    @SerializedName("username") val userName: String,
    val password: String
)

class TokenDto(
    token: String
)