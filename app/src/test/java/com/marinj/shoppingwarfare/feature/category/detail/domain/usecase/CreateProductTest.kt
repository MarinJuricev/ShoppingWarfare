package com.marinj.shoppingwarfare.feature.category.detail.domain.usecase

import com.marinj.shoppingwarfare.feature.category.detail.domain.repository.ProductRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.Before

class CreateProductTest {

    private val uuidGenerator: () -> String = mockk()
    private val productRepository: ProductRepository = mockk()

    private lateinit var sut: CreateProduct

    @Before
    fun setUp() {
        every { uuidGenerator() } answers { UUID }

        sut = CreateProduct(
            uuidGenerator,
            productRepository,
        )
    }
}

private const val UUID = "id"
