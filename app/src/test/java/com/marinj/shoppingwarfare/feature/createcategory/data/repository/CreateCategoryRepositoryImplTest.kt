package com.marinj.shoppingwarfare.feature.createcategory.data.repository

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.core.mapper.Mapper
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.buildLeft
import com.marinj.shoppingwarfare.core.result.buildRight
import com.marinj.shoppingwarfare.feature.category.data.datasource.CategoryDao
import com.marinj.shoppingwarfare.feature.category.data.model.LocalCategory
import com.marinj.shoppingwarfare.feature.category.domain.model.Category
import com.marinj.shoppingwarfare.feature.createcategory.domain.repository.CreateCategoryRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CreateCategoryRepositoryImplTest {

    private val categoryDao: CategoryDao = mockk()
    private val domainToLocalCategoryMapper: Mapper<LocalCategory, Category> = mockk()

    private lateinit var sut: CreateCategoryRepository

    @Before
    fun setUp() {
        sut = CreateCategoryRepositoryImpl(
            categoryDao,
            domainToLocalCategoryMapper,
        )
    }

    @Test
    fun `createCategory should return Left when categoryDao returns 0L`() = runBlockingTest {
        val category = mockk<Category>()
        val localCategory = mockk<LocalCategory>()
        coEvery {
            domainToLocalCategoryMapper.map(category)
        } coAnswers { localCategory }
        coEvery {
            categoryDao.upsertCategory(localCategory)
        } coAnswers { 0L }

        val actualResult = sut.createCategory(category)
        val expectedResult = Failure.ErrorMessage("Error while adding new category").buildLeft()

        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `createCategory should return Right when categoryDao returns not 0L`() = runBlockingTest {
        val category = mockk<Category>()
        val localCategory = mockk<LocalCategory>()
        coEvery {
            domainToLocalCategoryMapper.map(category)
        } coAnswers { localCategory }
        coEvery {
            categoryDao.upsertCategory(localCategory)
        } coAnswers { 1L }

        val actualResult = sut.createCategory(category)
        val expectedResult = Unit.buildRight()

        assertThat(actualResult).isEqualTo(expectedResult)
    }
}