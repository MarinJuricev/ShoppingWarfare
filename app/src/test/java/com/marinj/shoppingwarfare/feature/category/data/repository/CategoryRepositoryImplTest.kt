package com.marinj.shoppingwarfare.feature.category.data.repository

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.core.mapper.Mapper
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.buildLeft
import com.marinj.shoppingwarfare.core.result.buildRight
import com.marinj.shoppingwarfare.feature.category.data.datasource.CategoryDao
import com.marinj.shoppingwarfare.feature.category.data.model.LocalCategory
import com.marinj.shoppingwarfare.feature.category.domain.model.Category
import com.marinj.shoppingwarfare.feature.category.domain.repository.CategoryRepository
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
class CategoryRepositoryImplTest {

    private val categoryDao: CategoryDao = mockk()
    private val localToDomainCategoryMapper: Mapper<Category, LocalCategory> = mockk()
    private val domainToLocalCategoryMapper: Mapper<LocalCategory, Category> = mockk()

    private lateinit var sut: CategoryRepository

    @Before
    fun setUp() {
        sut = CategoryRepositoryImpl(
            categoryDao,
            localToDomainCategoryMapper,
            domainToLocalCategoryMapper,
        )
    }

    @Test
    fun `observeCategories should return categories`() = runBlockingTest {
        val category = mockk<Category>()
        val categoryList = listOf(category)
        val localCategory = mockk<LocalCategory>()
        val localCategoryList = listOf(localCategory)
        coEvery {
            localToDomainCategoryMapper.map(localCategory)
        } coAnswers { category }
        coEvery {
            categoryDao.getCategories()
        } coAnswers { flow { emit(localCategoryList) } }

        sut.observeCategories().test {
            assertThat(awaitItem()).isEqualTo(categoryList)
            awaitComplete()
        }
    }

    @Test
    fun `upsertCategory should return LeftFailure when categoryDao returns 0L`() = runBlockingTest {
        val category = mockk<Category>()
        val localCategory = mockk<LocalCategory>()
        val daoResult = 0L
        coEvery {
            domainToLocalCategoryMapper.map(category)
        } coAnswers { localCategory }
        coEvery {
            categoryDao.upsertCategory(localCategory)
        } coAnswers { daoResult }

        val actualResult = sut.upsertCategory(category)
        val expectedResult = Failure.ErrorMessage("Error while adding new category").buildLeft()

        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `upsertCategory should return RightUnit when categoryDao returns everything but 0L`() =
        runBlockingTest {
            val category = mockk<Category>()
            val localCategory = mockk<LocalCategory>()
            val daoResult = 1L
            coEvery {
                domainToLocalCategoryMapper.map(category)
            } coAnswers { localCategory }
            coEvery {
                categoryDao.upsertCategory(localCategory)
            } coAnswers { daoResult }

            val actualResult = sut.upsertCategory(category)
            val expectedResult = Unit.buildRight()

            assertThat(actualResult).isEqualTo(expectedResult)
        }

    @Test
    fun `deleteCategoryById should return RightUnit`() =
        runBlockingTest {
            val categoryId = "1"
            coEvery {
                categoryDao.deleteCategoryById(categoryId)
            } coAnswers { Unit }

            val actualResult = sut.deleteCategoryById(categoryId)
            val expectedResult = Unit.buildRight()

            assertThat(actualResult).isEqualTo(expectedResult)
        }
}
