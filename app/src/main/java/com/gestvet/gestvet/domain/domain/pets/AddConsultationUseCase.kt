package com.gestvet.gestvet.domain.pets

import com.gestvet.gestvet.data.network.PetService
import com.gestvet.gestvet.ui.features.home.models.ConsultationModel
import javax.inject.Inject

class AddConsultationUseCase @Inject constructor(private val petService: PetService) {

    suspend operator fun invoke(consultation: ConsultationModel) =
        petService.addConsultation(consultation)

}