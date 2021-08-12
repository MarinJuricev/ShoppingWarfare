package com.marinj.shoppingwarfare.feature.category.data.mapper

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.core.mapper.Mapper
import com.marinj.shoppingwarfare.feature.category.data.model.LocalCategory
import com.marinj.shoppingwarfare.feature.category.domain.model.Category
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

private const val TITLE = "title"
private const val BACKGROUND_COLOR = 1

@ExperimentalCoroutinesApi
class DomainToLocalCategoryMapperTest {

    private lateinit var sut: Mapper<LocalCategory, Category>

    @Before
    fun setUp() {
        sut = DomainToLocalCategoryMapper()
    }

    @Test
    fun `map should return a valid LocalCategory instance`() = runBlockingTest {
        val category = mockk<Category>()
        every { category.title } answers { TITLE }
        every { category.backgroundColor } answers { BACKGROUND_COLOR }

        val actualResult = sut.map(category)
        val expectedResult = LocalCategory(
            title = TITLE,
            backgroundColor = BACKGROUND_COLOR
        )

        assertThat(actualResult).isEqualTo(expectedResult)
    }
}
