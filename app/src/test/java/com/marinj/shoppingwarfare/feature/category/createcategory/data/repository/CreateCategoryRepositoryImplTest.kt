package com.marinj.shoppingwarfare.feature.category.createcategory.data.repository

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.buildLeft
import com.marinj.shoppingwarfare.core.result.buildRight
import com.marinj.shoppingwarfare.feature.category.createcategory.domain.repository.CreateCategoryRepository
import com.marinj.shoppingwarfare.feature.category.list.data.datasource.local.CategoryDao
import com.marinj.shoppingwarfare.feature.category.list.data.mapper.DomainToLocalCategoryMapper
import com.marinj.shoppingwarfare.feature.category.list.data.model.LocalCategory
import com.marinj.shoppingwarfare.feature.category.list.data.model.toLocal
import com.marinj.shoppingwarfare.feature.category.list.domain.model.Category
import com.marinj.shoppingwarfare.fixtures.category.buildCategory
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class CreateCategoryRepositoryImplTest {

    private val categoryDao: CategoryDao = mockk()

    private lateinit var sut: CreateCategoryRepository

    @Before
    fun setUp() {
        sut = CreateCategoryRepositoryImpl(
            categoryDao,
        )
    }

    @Test
    fun `createCategory should return Left when categoryDao returns 0L`() = runTest {
        val category = buildCategory()
        val localCategory = category.toLocal()
        coEvery {
            categoryDao.upsertCategory(localCategory)
        } coAnswers { 0L }

        val actualResult = sut.createCategory(category)
        val expectedResult = Failure.ErrorMessage("Error while adding new category").buildLeft()

        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `createCategory should return Right when categoryDao returns not 0L`() = runTest {
        val category = buildCategory()
        val localCategory = category.toLocal()
        coEvery {
            categoryDao.upsertCategory(localCategory)
        } coAnswers { 1L }

        val actualResult = sut.createCategory(category)
        val expectedResult = Unit.buildRight()

        assertThat(actualResult).isEqualTo(expectedResult)
    }
}
