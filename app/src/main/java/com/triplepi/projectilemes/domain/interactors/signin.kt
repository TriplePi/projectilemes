package com.triplepi.projectilemes.domain.interactors

class SignInUseCase(
    private val username: String,
    private val password: String
) : UseCase<Boolean>() {

    override fun run(): Boolean {

        return false
    }
}