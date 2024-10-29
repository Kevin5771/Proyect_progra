package com.gestvet.gestvet.domain.authentication

import com.gestvet.gestvet.data.network.AuthenticationService
import javax.inject.Inject

class DeleteAccountUseCase @Inject constructor(private val authenticationService: AuthenticationService) {

    suspend operator fun invoke(successDelete: () -> Boolean): Boolean {
        return if (authenticationService.deleteAccount()) {
            successDelete()
        } else {
            false
        }
    }

}