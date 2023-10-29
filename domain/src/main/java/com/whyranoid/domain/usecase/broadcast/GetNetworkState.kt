package com.whyranoid.domain.usecase.broadcast

import com.whyranoid.domain.repository.NetworkRepository
import kotlinx.coroutines.flow.StateFlow

class GetNetworkState(private val networkRepository: NetworkRepository) {
    operator fun invoke(): StateFlow<Boolean> {
        return networkRepository.getNetworkConnectionState()
    }
}
