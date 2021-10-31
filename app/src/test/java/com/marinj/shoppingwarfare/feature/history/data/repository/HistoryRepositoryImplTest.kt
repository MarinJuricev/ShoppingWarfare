package com.marinj.shoppingwarfare.feature.history.data.repository

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.core.result.Failure.ErrorMessage
import com.marinj.shoppingwarfare.core.result.buildLeft
import com.marinj.shoppingwarfare.core.result.buildRight
import com.marinj.shoppingwarfare.feature.history.data.datasource.HistoryDao
import com.marinj.shoppingwarfare.feature.history.data.mapper.DomainToLocalHistoryItemMapper
import com.marinj.shoppingwarfare.feature.history.data.mapper.LocalToDomainHistoryItemMapper
import com.marinj.shoppingwarfare.feature.history.data.model.LocalHistoryItem
import com.marinj.shoppingwarfare.feature.history.domain.model.HistoryItem
import com.marinj.shoppingwarfare.feature.history.domain.repository.HistoryRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class HistoryRepositoryImplTest {

    private val historyDao: HistoryDao = mockk()
    private val localToDomainHistoryItemMapper: LocalToDomainHistoryItemMapper = mockk()
    private val domainToLocalHistoryItemMapper: DomainToLocalHistoryItemMapper = mockk()

    private lateinit var sut: HistoryRepository

    @Before
    fun setUp() {
        sut = HistoryRepositoryImpl(
            historyDao,
            localToDomainHistoryItemMapper,
            domainToLocalHistoryItemMapper,
        )
    }

    @Test
    fun `observeHistoryItems should return historyItems`() = runBlockingTest {
        val historyItem = mockk<HistoryItem>()
        val historyItemList = listOf(historyItem)
        val localHistoryItem = mockk<LocalHistoryItem>()
        val localHistoryItemList = listOf(localHistoryItem)
        coEvery {
            localToDomainHistoryItemMapper.map(localHistoryItem)
        } coAnswers { historyItem }
        coEvery {
            historyDao.observeHistoryItems()
        } coAnswers { flow { emit(localHistoryItemList) } }

        sut.observeHistoryItems().test {
            assertThat(awaitItem()).isEqualTo(historyItemList)
            awaitComplete()
        }
    }

    @Test
    fun `upsertHistoryItem should return LeftFailure when historyDao returns 0L`() = runBlockingTest {
        val historyItem = mockk<HistoryItem>()
        val localHistoryItem = mockk<LocalHistoryItem>()
        val daoResult = 0L
        coEvery {
            domainToLocalHistoryItemMapper.map(historyItem)
        } coAnswers { localHistoryItem }
        coEvery {
            historyDao.upsertHistoryItem(localHistoryItem)
        } coAnswers { daoResult }

        val actualResult = sut.upsertHistoryItem(historyItem)
        val expectedResult = ErrorMessage("Error while adding new historyItem").buildLeft()

        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `upsertHistoryItem should return RightUnit when historyDao returns 1L`() = runBlockingTest {
        val historyItem = mockk<HistoryItem>()
        val localHistoryItem = mockk<LocalHistoryItem>()
        val daoResult = 1L
        coEvery {
            domainToLocalHistoryItemMapper.map(historyItem)
        } coAnswers { localHistoryItem }
        coEvery {
            historyDao.upsertHistoryItem(localHistoryItem)
        } coAnswers { daoResult }

        val actualResult = sut.upsertHistoryItem(historyItem)
        val expectedResult = Unit.buildRight()

        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `dropHistory should return RightUnit when historyDao returns LocalHistoryItem`() = runBlockingTest {
        val daoResult = Unit
        coEvery {
            historyDao.deleteHistory()
        } coAnswers { daoResult }

        val actualResult = sut.dropHistory()
        val expectedResult = daoResult.buildRight()

        assertThat(actualResult).isEqualTo(expectedResult)
    }
}
