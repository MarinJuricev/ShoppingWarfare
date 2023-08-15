package com.marinj.shoppingwarfare.feature.category.createcategory.presentation.viewmodel

import androidx.compose.ui.graphics.Color
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.MainCoroutineRule
import com.marinj.shoppingwarfare.core.fixture.FakeNavigator
import com.marinj.shoppingwarfare.core.mapper.FailureToStringMapper
import com.marinj.shoppingwarfare.core.navigation.NavigationEvent
import com.marinj.shoppingwarfare.core.navigation.Navigator
import com.marinj.shoppingwarfare.feature.category.createcategory.presentation.model.CreateCategoryEvent
import com.marinj.shoppingwarfare.feature.category.createcategory.presentation.model.CreateCategoryEvent.OnBackgroundColorChanged
import com.marinj.shoppingwarfare.feature.category.createcategory.presentation.model.CreateCategoryEvent.OnCategoryNameChanged
import com.marinj.shoppingwarfare.feature.category.createcategory.presentation.model.CreateCategoryEvent.OnCreateCategoryClicked
import com.marinj.shoppingwarfare.feature.category.createcategory.presentation.model.CreateCategoryViewEffect.CreateCategoryViewFailure
import com.marinj.shoppingwarfare.feature.category.createcategory.presentation.model.CreateCategoryViewEffect.CreateCategoryViewSuccess
import com.marinj.shoppingwarfare.feature.category.list.domain.usecase.CreateCategory
import com.marinj.shoppingwarfare.fixtures.category.FakeFailureCreateCategory
import com.marinj.shoppingwarfare.fixtures.category.FakeSuccessCreateCategory
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class CreateCategoryViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @Test
    fun `SHOULD update categoryName WHEN OnCategoryNameChanged is provided`() = runTest {
        val categoryText = "categoryText"
        val event = OnCategoryNameChanged(categoryText)
        val sut = buildSut()

        sut.onEvent(event)

        sut.createCategoryViewState.test {
            assertThat(awaitItem().categoryName).isEqualTo(categoryText)
        }
    }

    @Test
    fun `SHOULD update backgroundColor WHEN OnBackgroundColorChanged is provided`() =
        runTest {
            val selectedColor = Color.Magenta
            val event = OnBackgroundColorChanged(selectedColor)
            val sut = buildSut()

            sut.onEvent(event)

            sut.createCategoryViewState.test {
                assertThat(awaitItem().backgroundColor).isEqualTo(selectedColor)
            }
        }

    @Test
    fun `should update titleColor when OnTitleColorChanged is provided`() = runTest {
        val selectedColor = Color.Magenta
        val event = CreateCategoryEvent.OnTitleColorChanged(selectedColor)
        val sut = buildSut()

        sut.onEvent(event)

        sut.createCategoryViewState.test {
            assertThat(awaitItem().titleColor).isEqualTo(selectedColor)
        }
    }

    @Test
    fun `should emit CreateCategoryFailure when OnCreateCategoryClicked is provided and createCategory returns Left`() =
        runTest {
            val event = OnCreateCategoryClicked
            val createCategoryEffect = CreateCategoryViewFailure("Unknown Error Occurred, please try again later")
            val sut = buildSut(
                providedCreateCategory = FakeFailureCreateCategory,
            )

            sut.onEvent(event)

            sut.createCategoryEffect.test {
                assertThat(awaitItem()).isEqualTo(createCategoryEffect)
            }
        }

    @Test
    fun `SHOULD emit CreateCategorySuccess WHEN OnCreateCategoryClicked is provided and createCategory returns Right`() =
        runTest {
            val event = OnCreateCategoryClicked
            val createCategoryEffect = CreateCategoryViewSuccess
            val sut = buildSut()

            sut.onEvent(event)

            sut.createCategoryEffect.test {
                assertThat(awaitItem()).isEqualTo(createCategoryEffect)
            }
        }

    @Test
    fun `SHOULD emit navigateUp WHEN OnBackClicked is provided`() = runTest {
        val event = CreateCategoryEvent.OnBackClicked
        val navigator = FakeNavigator
        val sut = buildSut(
            providedNavigator = navigator,
        )

        navigator.receivedEvents.test {
            sut.onEvent(event)
            assertThat(awaitItem()).isEqualTo(NavigationEvent.NavigateUp)
        }
    }
    private fun buildSut(
        providedCreateCategory: CreateCategory = FakeSuccessCreateCategory,
        providedNavigator: Navigator = FakeNavigator,
        providedFailureToStringMapper: FailureToStringMapper = FailureToStringMapper(),
    ): CreateCategoryViewModel {
        return CreateCategoryViewModel(
            createCategory = providedCreateCategory,
            navigator = providedNavigator,
            failureToStringMapper = providedFailureToStringMapper,
        )
    }
}
