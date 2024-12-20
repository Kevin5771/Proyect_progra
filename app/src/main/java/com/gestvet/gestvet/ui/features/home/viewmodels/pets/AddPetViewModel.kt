package com.gestvet.gestvet.ui.features.home.viewmodels.pets

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gestvet.gestvet.data.network.PetService.Companion.PETS_TAG
import com.gestvet.gestvet.domain.pets.AddPetUseCase
import com.gestvet.gestvet.ui.features.home.models.PetModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPetViewModel @Inject constructor(
    private val addPetUseCase: AddPetUseCase,
    state: SavedStateHandle
) : ViewModel() {

    private val ownerId = state.getStateFlow("ownerId", 0L)

    private val _showDatePicker = MutableStateFlow(false)
    val showDatePicker: StateFlow<Boolean> get() = _showDatePicker
    fun setShowDatePicker(value: Boolean) {
        _showDatePicker.value = value
    }

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> get() = _name
    fun setName(name: String) {
        _name.value = name
    }

    private val _birthDate = MutableStateFlow("")
    val birthDate: StateFlow<String> get() = _birthDate
    fun setBirthDate(date: String) {
        _birthDate.value = date
    }

    private val _breed = MutableStateFlow("")
    val breed: StateFlow<String> get() = _breed
    fun setBreed(breed: String) {
        _breed.value = breed
    }

    private val _chipNumber = MutableStateFlow("")
    val chipNumber: StateFlow<String> get() = _chipNumber
    fun setChipNumber(chip: String) {
        _chipNumber.value = chip
    }

    private val _passport = MutableStateFlow("")
    val passport: StateFlow<String> get() = _passport
    fun setPassport(passport: String) {
        _passport.value = passport
    }

    private val _color = MutableStateFlow("")
    val color: StateFlow<String> get() = _color
    fun setColor(color: String) {
        _color.value = color
    }

    private val _neutered = MutableStateFlow(false)
    val neutered: StateFlow<Boolean> get() = _neutered
    fun setNeutered(value: Boolean) {
        _neutered.value = value
    }

    private val validData = name.combine(breed) { name, breed ->
        return@combine name.length > 2 && breed.length > 3
    }
    private val validDocuments = chipNumber.combine(passport) { chip, passport ->
        return@combine chip.isNotEmpty() && passport.isNotEmpty()
    }

    val buttonEnabled = validData.combine(validDocuments) { data, documents ->
        return@combine data && documents
    }

    fun addPet() {
        try {
            viewModelScope.launch {
                val pet = PetModel(
                    owner = ownerId.value,
                    name = name.value,
                    birthDate = birthDate.value,
                    breed = breed.value,
                    color = color.value,
                    chipNumber = chipNumber.value,
                    passportNumeber = passport.value,
                    neutered = neutered.value
                )
                addPetUseCase.invoke(pet)
            }
        } catch (error: Throwable) {
            Log.e(PETS_TAG, "Error al guardar los datos: ${error.message}")
        }
    }


}