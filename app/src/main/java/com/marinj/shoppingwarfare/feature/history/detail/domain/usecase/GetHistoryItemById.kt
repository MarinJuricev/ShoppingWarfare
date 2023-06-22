package com.marinj.shoppingwarfare.feature.history.detail.domain.usecase

import arrow.core.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.history.list.domain.model.HistoryItem

interface GetHistoryItemById {
    suspend operator fun invoke(historyItemId: String): Either<Failure, HistoryItem>
}