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
class LocalToDomainCategoryMapperTest {

    private lateinit var sut: Mapper<Category, LocalCategory>

    @Before
    fun setUp() {
        sut = LocalToDomainCategoryMapper()
    }

    @Test
    fun `map should return a valid Category instance`() = runBlockingTest {
        val localCategory = mockk<LocalCategory>()
        every { localCategory.title } answers { TITLE }
        every { localCategory.backgroundColor } answers { BACKGROUND_COLOR }

        val actualResult = sut.map(localCategory)
        val expectedResult = Category(
            TITLE,
            BACKGROUND_COLOR,
        )

        assertThat(actualResult).isEqualTo(expectedResult)
    }
}
