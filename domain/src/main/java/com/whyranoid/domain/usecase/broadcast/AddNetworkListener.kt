package com.whyranoid.domain.usecase.broadcast

import com.whyranoid.domain.repository.NetworkRepository

class AddNetworkListener(private val networkRepository: NetworkRepository) {
    operator fun invoke() {
        networkRepository.addNetworkConnectionCallback()
    }
}
