package com.whyranoid.domain.usecase.broadcast

import com.whyranoid.domain.repository.GpsRepository
import kotlinx.coroutines.flow.StateFlow

class GetGpsState(private val gpsRepository: GpsRepository) {
    operator fun invoke(): StateFlow<Boolean> {
        return gpsRepository.getGpsEnabledState()
    }
}
