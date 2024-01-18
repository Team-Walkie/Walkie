package com.whyranoid.domain.usecase

import com.whyranoid.domain.repository.AccountRepository

class RequestLoginUseCase(
    private val accountRepository: AccountRepository,
) {

    suspend operator fun invoke(authorId: String, name: String): Result<Long> {
        return accountRepository.signIn(authorId, name)
    }
}