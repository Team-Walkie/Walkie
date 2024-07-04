package com.whyranoid.domain.usecase

import com.whyranoid.domain.model.user.User
import com.whyranoid.domain.repository.UserRepository

class GetUserUseCase(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(uid: Long): Result<User> {
        return kotlin.runCatching {
            userRepository.getUser(uid).getOrThrow()
        }
    }
}
