package com.gestvet.gestvet.domain.authentication

import com.gestvet.gestvet.data.network.AuthenticationService
import javax.inject.Inject

class VerifyEmailUseCase @Inject constructor(
    private val authenticationService: AuthenticationService
) {

    operator fun invoke() = authenticationService.isEmailVerified

}