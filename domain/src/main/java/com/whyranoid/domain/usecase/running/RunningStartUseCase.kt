package com.whyranoid.domain.usecase.running

import com.whyranoid.domain.repository.AccountRepository
import com.whyranoid.domain.repository.RunningRepository
import kotlinx.coroutines.flow.first

class RunningStartUseCase(
    private val accountRepository: AccountRepository,
    private val runningRepository: RunningRepository,
) {
    suspend operator fun invoke(): Result<Long> {
        accountRepository.uId.first()?.let { id ->
            return runningRepository.startRunning(id)
        }
        return Result.failure(Exception("ID 정보 없음"))
    }
}
