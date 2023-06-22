package com.marinj.shoppingwarfare.feature.history.list.domain.usecase

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.feature.history.FakeSuccessHistoryRepository
import com.marinj.shoppingwarfare.feature.history.buildHistoryItem
import kotlinx.coroutines.test.runTest
import org.junit.Test

class ObserveHistoryItemsTest {

    @Test
    fun `invoke SHOULD return result from historyRepository observeHistoryItems`() =
        runTest {
            val historyItems = listOf(buildHistoryItem())
            val sut = ObserveHistoryItemsImpl(
                historyRepository = FakeSuccessHistoryRepository(historyItems),
            )

            sut().test {
                assertThat(awaitItem()).isEqualTo(historyItems)
                awaitComplete()
            }
        }
}
