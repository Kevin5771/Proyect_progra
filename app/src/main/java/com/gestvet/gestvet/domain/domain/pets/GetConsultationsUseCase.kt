package com.gestvet.gestvet.domain.pets

import com.gestvet.gestvet.data.network.PetService
import javax.inject.Inject

class GetConsultationsUseCase @Inject constructor(private val petService: PetService) {

    operator fun invoke(petId: Long) = petService.getConsultByPet(petId)

}