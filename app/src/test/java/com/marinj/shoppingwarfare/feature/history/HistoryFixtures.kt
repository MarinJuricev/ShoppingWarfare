package com.marinj.shoppingwarfare.feature.history

import arrow.core.Either
import arrow.core.right
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.history.list.domain.model.HistoryCartItem
import com.marinj.shoppingwarfare.feature.history.list.domain.model.HistoryItem
import com.marinj.shoppingwarfare.feature.history.list.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

fun buildHistoryItem(
    providedId: String = ID,
    providedReceiptPath: String? = RECEIPT_PATH,
    providedTimeStamp: Long = TIME_STAMP,
    providedCartName: String = CART_NAME,
    providedHistoryCartItems: List<HistoryCartItem> = listOf(buildHistoryCartItem()),
) = HistoryItem(
    id = providedId,
    receiptPath = providedReceiptPath,
    timestamp = providedTimeStamp,
    cartName = providedCartName,
    historyCartItems = providedHistoryCartItems,
)

fun buildHistoryCartItem(
    providedId: String = HISTORY_CART_ITEM_ID,
    providedCategoryName: String = CATEGORY_NAME,
    providedName: String = NAME,
    quantity: Int = QUANTITY,
) = HistoryCartItem(
    id = "congue",
    categoryName = "Pansy Quinn",
    name = "Elaine Bright",
    quantity = 9210,
)

class FakeSuccessHistoryRepository(
    private val historyItemsToReturn: List<HistoryItem> = listOf(),
) : HistoryRepository {
    override fun observeHistoryItems(): Flow<List<HistoryItem>> = flowOf(historyItemsToReturn)

    override suspend fun upsertHistoryItem(historyItem: HistoryItem): Either<Failure, Unit> = Unit.right()

    override suspend fun getHistoryItemById(id: String): Either<Failure, HistoryItem> = buildHistoryItem().right()

    override suspend fun dropHistory(): Either<Failure, Unit> = Unit.right()

}

private const val ID = "id"
private const val RECEIPT_PATH = "receiptPath"
private const val CART_NAME = "cartName"
private const val TIME_STAMP = 100L
private const val HISTORY_CART_ITEM_ID = "historyCartItemId"
private const val CATEGORY_NAME = "categoryName"
private const val NAME = "name"
private const val QUANTITY = 1