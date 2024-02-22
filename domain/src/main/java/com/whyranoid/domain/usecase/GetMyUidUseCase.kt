package com.whyranoid.domain.usecase

import com.whyranoid.domain.repository.AccountRepository
import kotlinx.coroutines.flow.firstOrNull

class GetMyUidUseCase(
    private val accountRepository: AccountRepository,
) {
    suspend operator fun invoke(): Result<Long> {
        return runCatching { requireNotNull(accountRepository.uId.firstOrNull()) }
    }
}
