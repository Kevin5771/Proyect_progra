package com.gestvet.gestvet.ui.features.home.viewmodels.clients

import androidx.lifecycle.ViewModel
import com.gestvet.gestvet.domain.clients.GetClientsUseCase
import com.gestvet.gestvet.ui.features.home.models.ClientsModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class ClientViewModel @Inject constructor(
    getClientsUseCase: GetClientsUseCase
): ViewModel() {

    private val _clients: Flow<List<ClientsModel>> = getClientsUseCase.invoke().map {
        it.toObjects(ClientsModel::class.java)
    }

    val clients: Flow<List<ClientsModel>> get() = _clients

}