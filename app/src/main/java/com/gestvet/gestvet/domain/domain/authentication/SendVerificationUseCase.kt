package com.gestvet.gestvet.domain.authentication

import com.gestvet.gestvet.data.network.AuthenticationService
import javax.inject.Inject

class SendVerificationUseCase @Inject constructor(
    private val authenticationService: AuthenticationService
) {

    suspend operator fun invoke() = authenticationService.sendVerificationEmail()

}