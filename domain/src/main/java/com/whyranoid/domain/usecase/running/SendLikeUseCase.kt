package com.whyranoid.domain.usecase.running

import com.whyranoid.domain.repository.AccountRepository
import com.whyranoid.domain.repository.RunningRepository
import kotlinx.coroutines.flow.first

class SendLikeUseCase(
    private val accountRepository: AccountRepository,
    private val runningRepository: RunningRepository,
) {
    suspend operator fun invoke(receiverId: Long): Result<Unit> {
        return kotlin.runCatching {
            val uid = requireNotNull(accountRepository.walkieId.first())
            runningRepository.sendLike(uid, receiverId).onSuccess {
                return Result.success(Unit)
            }.onFailure {
                return Result.failure(Exception("좋아요 누름 실패"))
            }
        }
    }
}
