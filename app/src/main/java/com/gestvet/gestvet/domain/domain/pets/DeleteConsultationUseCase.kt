package com.gestvet.gestvet.domain.pets

import com.gestvet.gestvet.data.network.PetService
import javax.inject.Inject

class DeleteConsultationUseCase @Inject constructor(private val petService: PetService) {

    suspend operator fun invoke(id: Long) = petService.deleteConsultation(id)

}