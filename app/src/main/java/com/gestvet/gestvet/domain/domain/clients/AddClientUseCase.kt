package com.gestvet.gestvet.domain.clients

import com.gestvet.gestvet.data.network.ClientService
import com.gestvet.gestvet.ui.features.home.models.ClientsModel
import javax.inject.Inject

class AddClientUseCase @Inject constructor(private val clientService: ClientService) {
    suspend operator fun invoke(clientsModel: ClientsModel) = clientService.addClient(clientsModel)
}