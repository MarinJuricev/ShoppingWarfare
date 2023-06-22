package com.marinj.shoppingwarfare.feature.history.detail.domain.usecase

import arrow.core.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.history.list.domain.model.HistoryItem
import com.marinj.shoppingwarfare.feature.history.list.domain.repository.HistoryRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class GetHistoryItemByIdImpl @Inject constructor(
    private val historyRepository: HistoryRepository,
) : GetHistoryItemById {

    override suspend operator fun invoke(
        historyItemId: String,
    ): Either<Failure, HistoryItem> =
        historyRepository.getHistoryItemById(historyItemId)
}
