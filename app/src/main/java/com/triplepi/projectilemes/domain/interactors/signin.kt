package com.triplepi.projectilemes.domain.interactors

import com.triplepi.projectilemes.App
import com.triplepi.projectilemes.data.network.dto.UserDataDto
import com.triplepi.projectilemes.data.network.dto.WorkCenterDTO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

class SignInUseCase(
    private val username: String,
    private val password: String,
    private val jwt:String,
    private val id: Long
) : UseCase<Boolean>() {

    private val api = App.INSTANCE.api

    override val dispatcher: CoroutineDispatcher
        get() = Dispatchers.IO

    override suspend fun run(): Boolean {

        val response = api.signIn(UserDataDto(username, password,jwt,"",id)).await()

        return response.isSuccessful
    }
}

class LoadUsersUseCase : UseCase<List<UserDataDto>>(){

    private val api = App.INSTANCE.api

    override suspend fun run(): List<UserDataDto> {
        val response = api.getUsers()
        return response.execute().body()!!
    }

    override val dispatcher: CoroutineDispatcher
        get() = Dispatchers.IO

}

class LoadWorkcentersUseCase:UseCase<List<WorkCenterDTO>>(){

    private val api = App.INSTANCE.api

    override suspend fun run(): List<WorkCenterDTO> {
        val response = api.getWorkCenter()
        return response.execute().body()!!
    }

    override val dispatcher: CoroutineDispatcher
        get() = Dispatchers.IO

}