package com.gestvet.gestvet.domain.appointments

import com.gestvet.gestvet.data.network.AppointmentsService
import javax.inject.Inject

class DeleteAppointmentUseCase @Inject constructor(private val appointmentsService: AppointmentsService) {
    suspend operator fun invoke(id: Long) = appointmentsService.deleteAppointment(id)
}