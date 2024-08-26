package com.whyranoid.domain.usecase.running

import com.whyranoid.domain.repository.AccountRepository
import com.whyranoid.domain.repository.RunningRepository
import kotlinx.coroutines.flow.first

class RunningFinishUseCase(
    private val accountRepository: AccountRepository,
    private val runningRepository: RunningRepository,
) {
    suspend operator fun invoke(): Result<Unit> {
        accountRepository.walkieId.first()?.let { id ->
            return runningRepository.finishRunning(id)
        }
        return Result.failure(Exception("ID 정보 없음"))
    }
}
