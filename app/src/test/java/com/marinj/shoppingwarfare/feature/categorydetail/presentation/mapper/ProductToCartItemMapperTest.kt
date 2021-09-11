package com.marinj.shoppingwarfare.feature.categorydetail.presentation.mapper

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.core.mapper.Mapper
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import com.marinj.shoppingwarfare.feature.categorydetail.domain.model.Product
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

private const val ID = "id"
private const val NAME = "name"

@ExperimentalCoroutinesApi
class ProductToCartItemMapperTest {

    private val uuidGenerator = { ID }

    private lateinit var sut: Mapper<CartItem, Product>

    @Before
    fun setUp() {
        sut = ProductToCartItemMapper(
            uuidGenerator,
        )
    }

    @Test
    fun `map should map id`() = runBlockingTest {
        val product = mockk<Product>(relaxed = true)

        val actualResult = sut.map(product)

        assertThat(actualResult.id).isEqualTo(ID)
    }

    @Test
    fun `map should map name`() = runBlockingTest {
        val product = mockk<Product>(relaxed = true).apply {
            every { name } answers { NAME }
        }

        val actualResult = sut.map(product)

        assertThat(actualResult.name).isEqualTo(NAME)
    }

    @Test
    fun `map should map quantity`() = runBlockingTest {
        val product = mockk<Product>(relaxed = true)

        val actualResult = sut.map(product)

        assertThat(actualResult.quantity).isEqualTo(CartItem.DEFAULT_QUANTITY)
    }
}
