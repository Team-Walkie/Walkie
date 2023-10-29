package com.whyranoid.domain.usecase.broadcast

import com.whyranoid.domain.repository.GpsRepository

class RemoveGpsListener(private val gpsRepository: GpsRepository) {
    operator fun invoke() {
        gpsRepository.unRegisterReceiver()
    }
}
