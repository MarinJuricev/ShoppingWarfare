package com.marinj.shoppingwarfare.feature.category.createcategory.presentation.viewmodel

import androidx.compose.ui.graphics.Color
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.MainCoroutineRule
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.buildLeft
import com.marinj.shoppingwarfare.core.result.buildRight
import com.marinj.shoppingwarfare.feature.category.createcategory.presentation.mapper.FailureToCreateCategoryEffectMapper
import com.marinj.shoppingwarfare.feature.category.createcategory.presentation.model.CreateCategoryEvent
import com.marinj.shoppingwarfare.feature.category.createcategory.presentation.model.CreateCategoryEvent.OnBackgroundColorChanged
import com.marinj.shoppingwarfare.feature.category.createcategory.presentation.model.CreateCategoryEvent.OnCategoryNameChanged
import com.marinj.shoppingwarfare.feature.category.createcategory.presentation.model.CreateCategoryEvent.OnCreateCategoryClicked
import com.marinj.shoppingwarfare.feature.category.createcategory.presentation.model.CreateCategoryViewEffect.CreateCategoryViewFailure
import com.marinj.shoppingwarfare.feature.category.createcategory.presentation.model.CreateCategoryViewEffect.CreateCategoryViewSuccess
import com.marinj.shoppingwarfare.feature.category.list.domain.usecase.CreateCategoryImpl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CreateCategoryViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private val createCategory: CreateCategoryImpl = mockk()
    private val failureToCreateCategoryEffectMapper: FailureToCreateCategoryEffectMapper = mockk()

    private lateinit var sut: CreateCategoryViewModel

    @Before
    fun setUp() {
        sut = CreateCategoryViewModel(
            createCategory,
            failureToCreateCategoryEffectMapper,
        )
    }

    @Test
    fun `should update categoryName when OnCategoryNameChanged is provided`() = runTest {
        val categoryText = "categoryText"
        val event = OnCategoryNameChanged(categoryText)

        sut.onEvent(event)

        sut.createCategoryViewState.test {
            assertThat(awaitItem().categoryName).isEqualTo(categoryText)
        }
    }

    @Test
    fun `should update backgroundColor when OnBackgroundColorChanged is provided`() =
        runTest {
            val selectedColor = Color.Magenta
            val event = OnBackgroundColorChanged(selectedColor)
            sut.onEvent(event)

            sut.createCategoryViewState.test {
                assertThat(awaitItem().backgroundColor).isEqualTo(selectedColor)
            }
        }

    @Test
    fun `should update titleColor when OnTitleColorChanged is provided`() = runTest {
        val selectedColor = Color.Magenta
        val event = CreateCategoryEvent.OnTitleColorChanged(selectedColor)
        sut.onEvent(event)

        sut.createCategoryViewState.test {
            assertThat(awaitItem().titleColor).isEqualTo(selectedColor)
        }
    }

    @Test
    fun `should emit CreateCategoryFailure when OnCreateCategoryClicked is provided and createCategory returns Left`() =
        runTest {
            val event = OnCreateCategoryClicked
            val failure = Failure.Unknown
            val createCategoryEffect = CreateCategoryViewFailure("Error")
            coEvery {
                createCategory("", null, null)
            } coAnswers { failure.left() }
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
        runTest {
            val event = OnCreateCategoryClicked
            val createCategoryEffect = CreateCategoryViewSuccess
            coEvery {
                createCategory("", null, null)
            } coAnswers { Unit.right() }

            sut.onEvent(event)

            sut.createCategoryEffect.test {
                assertThat(awaitItem()).isEqualTo(createCategoryEffect)
            }
        }
}
