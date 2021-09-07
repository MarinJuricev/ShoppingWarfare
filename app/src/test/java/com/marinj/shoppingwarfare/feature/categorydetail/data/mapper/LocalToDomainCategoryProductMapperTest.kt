package com.marinj.shoppingwarfare.feature.categorydetail.data.mapper

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.core.mapper.Mapper
import com.marinj.shoppingwarfare.feature.categorydetail.data.model.LocalCategoryProduct
import com.marinj.shoppingwarfare.feature.categorydetail.domain.model.CategoryProduct
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

private const val ID = "id"
private const val NAME = "name"

@ExperimentalCoroutinesApi
class LocalToDomainCategoryProductMapperTest {

    private lateinit var sut: Mapper<CategoryProduct, LocalCategoryProduct>

    @Before
    fun setUp() {
        sut = LocalToDomainCategoryItemMapper()
    }

    @Test
    fun `map should map id`() = runBlockingTest {
        val localCategoryItem = mockk<LocalCategoryProduct>(relaxed = true).apply {
            every { categoryProductId } answers { ID }
        }

        val actualResult = sut.map(localCategoryItem)

        assertThat(actualResult.id).isEqualTo(ID)
    }

    @Test
    fun `map should map name`() = runBlockingTest {
        val localCategoryItem = mockk<LocalCategoryProduct>(relaxed = true).apply {
            every { name } answers { NAME }
        }

        val actualResult = sut.map(localCategoryItem)

        assertThat(actualResult.name).isEqualTo(NAME)
    }
}
