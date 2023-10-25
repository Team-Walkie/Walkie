package com.whyranoid.domain.usecase.broadcast

import com.whyranoid.domain.repository.GpsRepository

class AddGpsListener(private val gpsRepository: GpsRepository) {
    operator fun invoke() {
        gpsRepository.registerReceiver()
    }
}
