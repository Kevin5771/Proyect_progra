package com.gestvet.gestvet.domain.authentication

import com.gestvet.gestvet.data.network.AuthenticationService
import com.gestvet.gestvet.data.network.UserService
import com.gestvet.gestvet.ui.features.login.model.User
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(
    private val authenticationService: AuthenticationService,
    private val userService: UserService
) {

    suspend operator fun invoke(user: User): Boolean {
        val accountCreated =
            authenticationService.createAccount(user.email ?: "", user.password ?: "") != null

        return if (accountCreated) {
            userService.createUser(user)
        } else {
            false
        }
    }

}