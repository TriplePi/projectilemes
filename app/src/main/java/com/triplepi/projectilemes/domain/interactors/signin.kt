package com.triplepi.projectilemes.domain.interactors

import com.triplepi.projectilemes.App
import com.triplepi.projectilemes.data.network.dto.UserDataDto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

class SignInUseCase(
    private val username: String,
    private val password: String
) : UseCase<Boolean>() {

    private val api = App.INSTANCE.api

    override val dispatcher: CoroutineDispatcher
        get() = Dispatchers.IO

    override suspend fun run(): Boolean {

        val response = api.signIn(UserDataDto(username, password)).await()

        return response.isSuccessful
    }
}