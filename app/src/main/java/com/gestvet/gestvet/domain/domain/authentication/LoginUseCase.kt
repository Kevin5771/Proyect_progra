package com.gestvet.gestvet.domain.authentication

import com.gestvet.gestvet.data.network.AuthenticationService
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authenticationService: AuthenticationService
) {

    suspend operator fun invoke(email: String, password: String, onResult: (Throwable?)->Unit) =
        authenticationService.login(email,password) {
            onResult(it)
        }

}