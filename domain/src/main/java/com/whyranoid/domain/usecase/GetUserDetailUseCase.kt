package com.whyranoid.domain.usecase

import com.whyranoid.domain.model.user.UserDetail
import com.whyranoid.domain.repository.UserRepository

class GetUserDetailUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(uid: Long): Result<UserDetail> {
        return userRepository.getUserDetail(uid)
    }
}
