package com.gestvet.gestvet.domain.pets

import com.gestvet.gestvet.data.network.PetService
import javax.inject.Inject

class GetPetsByOwnerUseCase @Inject constructor(private val petService: PetService) {

    operator fun invoke(ownerId: Long) = petService.getPetsByOwner(ownerId)

}