package com.whyranoid.domain.usecase

import com.whyranoid.domain.repository.OtherUserRepository

class GetSearchedUserUseCase(
    private val otherUserRepository: OtherUserRepository,
) {
    operator fun invoke(query: String) = otherUserRepository.searchUsers(query).flow
}