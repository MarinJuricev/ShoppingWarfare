package com.marinj.shoppingwarfare.feature.createcategory.presentation.viewmodel

import androidx.compose.ui.graphics.Color
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.MainCoroutineRule
import com.marinj.shoppingwarfare.core.mapper.Mapper
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.buildLeft
import com.marinj.shoppingwarfare.core.result.buildRight
import com.marinj.shoppingwarfare.feature.createcategory.domain.usecase.CreateCategory
import com.marinj.shoppingwarfare.feature.createcategory.presentation.model.CreateCategoryEffect
import com.marinj.shoppingwarfare.feature.createcategory.presentation.model.CreateCategoryEffect.CreateCategoryFailure
import com.marinj.shoppingwarfare.feature.createcategory.presentation.model.CreateCategoryEffect.CreateCategorySuccess
import com.marinj.shoppingwarfare.feature.createcategory.presentation.model.CreateCategoryEvent
import com.marinj.shoppingwarfare.feature.createcategory.presentation.model.CreateCategoryEvent.OnBackgroundColorChanged
import com.marinj.shoppingwarfare.feature.createcategory.presentation.model.CreateCategoryEvent.OnCategoryNameChanged
import com.marinj.shoppingwarfare.feature.createcategory.presentation.model.CreateCategoryEvent.OnCreateCategoryClicked
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class CreateCategoryViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private val createCategory: CreateCategory = mockk()
    private val failureToCreateCategoryEffectMapper: Mapper<CreateCategoryEffect, Failure> = mockk()

    private lateinit var sut: CreateCategoryViewModel

    @Before
    fun setUp() {
        sut = CreateCategoryViewModel(
            createCategory,
            failureToCreateCategoryEffectMapper,
        )
    }

    @Test
    fun `should update categoryName when OnCategoryNameChanged is provided`() = runBlockingTest {
        val categoryText = "categoryText"
        val event = OnCategoryNameChanged(categoryText)

        sut.onEvent(event)

        sut.createCategoryViewState.test {
            assertThat(awaitItem().categoryName).isEqualTo(categoryText)
        }
    }

    @Test
    fun `should update backgroundColor when OnBackgroundColorChanged is provided`() = runBlockingTest {
        val selectedColor = Color.Magenta
        val event = OnBackgroundColorChanged(selectedColor)
        sut.onEvent(event)

        sut.createCategoryViewState.test {
            assertThat(awaitItem().backgroundColor).isEqualTo(selectedColor)
        }
    }

    @Test
    fun `should update titleColor when OnTitleColorChanged is provided`() = runBlockingTest {
        val selectedColor = Color.Magenta
        val event = CreateCategoryEvent.OnTitleColorChanged(selectedColor)
        sut.onEvent(event)

        sut.createCategoryViewState.test {
            assertThat(awaitItem().titleColor).isEqualTo(selectedColor)
        }
    }

    @Test
    fun `should emit CreateCategoryFailure when OnCreateCategoryClicked is provided and createCategory returns Left`() =
        runBlockingTest {
            val event = OnCreateCategoryClicked
            val failure = Failure.Unknown
            val createCategoryEffect = CreateCategoryFailure("Error")
            coEvery {
                createCategory("", null, null)
            } coAnswers { failure.buildLeft() }
            coEvery {
                failureToCreateCategoryEffectMapper.map(failure)
            } coAnswers { createCategoryEffect }

            sut.onEvent(event)

            sut.createCategoryEffect.test {
                assertThat(awaitItem()).isEqualTo(createCategoryEffect)
            }
        }

    @Test
    fun `should emit CreateCategorySuccess when OnCreateCategoryClicked is provided and createCategory returns Right`() =
        runBlockingTest {
            val event = OnCreateCategoryClicked
            val createCategoryEffect = CreateCategorySuccess
            coEvery {
                createCategory("", null, null)
            } coAnswers { Unit.buildRight() }

            sut.onEvent(event)

            sut.createCategoryEffect.test {
                assertThat(awaitItem()).isEqualTo(createCategoryEffect)
            }
        }
}