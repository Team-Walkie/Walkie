package com.whyranoid.domain.usecase

import com.whyranoid.domain.repository.AccountRepository

class SignOutUseCase(private val accountRepository: AccountRepository) {
    suspend operator fun invoke() {
        accountRepository.singOut()
    }
}
