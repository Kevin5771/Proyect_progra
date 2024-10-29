package com.gestvet.gestvet.domain.appointments

import com.gestvet.gestvet.data.network.AppointmentsService
import com.gestvet.gestvet.ui.features.home.models.AppointmentModel
import javax.inject.Inject

class AddAppointmentUseCase @Inject constructor(private val appointmentsService: AppointmentsService) {
    suspend operator fun invoke(appointment: AppointmentModel) = appointmentsService.addAppointment(appointment)
}