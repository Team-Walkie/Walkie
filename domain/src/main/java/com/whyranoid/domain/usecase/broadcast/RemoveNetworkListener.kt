package com.whyranoid.domain.usecase.broadcast

import com.whyranoid.domain.repository.NetworkRepository

class RemoveNetworkListener(private val networkRepository: NetworkRepository) {
    operator fun invoke() {
        networkRepository.removeNetworkConnectionCallback()
    }
}
