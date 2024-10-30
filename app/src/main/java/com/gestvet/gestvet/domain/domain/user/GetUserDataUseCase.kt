package com.gestvet.gestvet.domain.user

import com.gestvet.gestvet.data.network.UserService
import javax.inject.Inject

class GetUserDataUseCase @Inject constructor(
    private val userService: UserService
) {

    suspend operator fun invoke() = userService.getUserData()

}