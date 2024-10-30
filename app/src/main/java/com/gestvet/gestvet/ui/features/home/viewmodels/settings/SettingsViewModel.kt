package com.gestvet.gestvet.ui.features.home.viewmodels.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gestvet.gestvet.data.network.AuthenticationService
import com.gestvet.gestvet.domain.user.GetUserDataUseCase
import com.gestvet.gestvet.ui.features.login.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val authenticationService: AuthenticationService,
    private val getUserDataUseCase: GetUserDataUseCase

    ) : ViewModel() {

    private val _currentUser = MutableStateFlow(User())
    val currentUser: StateFlow<User> get() = _currentUser

    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> get() = _showDialog
    fun setShowDialog(value: Boolean) {
        _showDialog.value = value
    }

    init {
        viewModelScope.launch {
            getUserDataUseCase.invoke().collect {
                _currentUser.value = it.toObject(User::class.java)!!
            }
        }
    }

    fun logOut() {
        authenticationService.logOut()
    }

    fun deleteAccount(successDelete: () -> Unit, failureDelete: () -> Unit) =
        viewModelScope.launch(Dispatchers.IO){
            if (authenticationService.deleteAccount()) {
                successDelete()
            } else {
                failureDelete()
            }
        }

    fun sendRecoveryEmail(successSent: () -> Unit, failureSent: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO){
            val recovery = authenticationService.recoverPassword(currentUser.value.email!!)
            if (recovery) {
                successSent()
            } else {
                failureSent()
            }
        }
    }
}