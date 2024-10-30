package com.gestvet.gestvet.domain.clients

import com.gestvet.gestvet.data.network.ClientService
import javax.inject.Inject

class DeleteClientUseCase @Inject constructor(
    private val clientService: ClientService
) {
    suspend fun deleteClient(id: Long) = clientService.deleteClient(id)
}