package com.gestvet.gestvet.domain.pets

import com.gestvet.gestvet.data.network.PetService
import com.gestvet.gestvet.ui.features.home.models.PetModel
import javax.inject.Inject

class AddPetUseCase @Inject constructor(private val petService: PetService) {
    suspend operator fun invoke(petModel: PetModel) = petService.addPet(petModel)
}