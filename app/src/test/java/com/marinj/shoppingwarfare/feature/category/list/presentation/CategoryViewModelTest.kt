package com.marinj.shoppingwarfare.feature.category.list.presentation

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.MainCoroutineRule
import com.marinj.shoppingwarfare.core.mapper.FailureToStringMapper
import com.marinj.shoppingwarfare.core.navigation.Navigator
import com.marinj.shoppingwarfare.feature.category.list.presentation.model.CategoryEvent.GetCategories
import com.marinj.shoppingwarfare.feature.category.list.presentation.viewmodel.CategoryViewModel
import com.marinj.shoppingwarfare.fixtures.category.FakeSuccessDeleteCategory
import com.marinj.shoppingwarfare.fixtures.category.FakeSuccessObserveCategories
import com.marinj.shoppingwarfare.fixtures.category.FakeSuccessUndoCategoryDeletion
import com.marinj.shoppingwarfare.fixtures.category.buildCategory
import com.marinj.shoppingwarfare.fixtures.category.buildUiCategory
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class CategoryViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private val navigator: Navigator = mockk()

    @Test
    fun `onEvent SHOULD update categories WHEN GetCategories is provided and ObserveCategories emits categories`() = runTest {
        val categories = listOf(
            buildCategory(
                providedId = ID,
                providedTitle = TITLE,
                providedBackgroundColor = BACKGROUND_COLOR,
                providedTitleColor = TITLE_COLOR,
            ),
        )
        val sut = CategoryViewModel(
            FakeSuccessObserveCategories(categories),
            FakeSuccessDeleteCategory(),
            FakeSuccessUndoCategoryDeletion(),
            FailureToStringMapper(),
            navigator,
        )
        val expectedResult = listOf(
            buildUiCategory(
                providedCategoryId = ID,
                providedTitle = TITLE,
                providedBackgroundColor = BACKGROUND_COLOR,
                providedTitleColor = TITLE_COLOR,
            ),
        )

        sut.onEvent(GetCategories)

        sut.viewState.test {
            val updatedViewState = awaitItem()
            assertThat(updatedViewState.categories).isEqualTo(expectedResult)
            assertThat(updatedViewState.isLoading).isFalse()
        }
    }

//    @Test
//    fun `should update categoryViewEffect with Error when GetCategories is provided and emits an exception`() =
//        runTest {
//            val categoriesFlow = flow<List<Category>> {
//                throw Exception()
//            }
//            coEvery {
//                observeCategories()
//            } coAnswers { categoriesFlow }
//
//            sut.viewState.test {
//                val initialViewState = awaitItem()
//                assertThat(initialViewState.categories).isEmpty()
//                assertThat(initialViewState.isLoading).isTrue()
//
//                sut.onEvent(GetCategories)
//
//                val updatedViewState = awaitItem()
//                assertThat(updatedViewState.isLoading).isFalse()
//            }
//
//            sut.viewEffect.test {
//                assertThat(awaitItem()).isEqualTo(CategoryViewEffect.Error("Failed to fetch Categories, try again later."))
//            }
//        }
//
//    @Test
//    fun `should update categoryViewEffect with DeleteCategory when DeleteCategory is provided and deleteCategory returns Right`() =
//        runTest {
//            val uiCategory = mockk<UiCategory>().apply {
//                every { id } answers { ID }
//            }
//            coEvery {
//                deleteCategory(ID)
//            } coAnswers { Unit.buildRight() }
//
//            sut.onEvent(DeleteCategory(uiCategory))
//
//            sut.viewEffect.test {
//                assertThat(awaitItem()).isEqualTo(CategoryViewEffect.DeleteCategoryView(uiCategory))
//            }
//        }
//
//    @Test
//    fun `should update categoryViewEffect with Error when DeleteCategory is provided and deleteCategory returns Left`() =
//        runTest {
//            val uiCategory = mockk<UiCategory>().apply {
//                every { id } answers { ID }
//            }
//            coEvery {
//                deleteCategory(ID)
//            } coAnswers { Unknown.buildLeft() }
//
//            sut.onEvent(DeleteCategory(uiCategory))
//
//            sut.viewEffect.test {
//                assertThat(awaitItem()).isEqualTo(CategoryViewEffect.Error("Error while deleting category."))
//            }
//        }
//
//    @Test
//    fun `should trigger Navigator emitDestination with CategoryDetailAction when NavigateToCategoryDetail is provided`() {
//        coEvery {
//            navigator.emitDestination(
//                Destination(CategoryDetailDestination.createCategoryDetailRoute(ID, CATEGORY_NAME)),
//            )
//        } returns Unit
//        sut.onEvent(NavigateToCategoryDetail(ID, CATEGORY_NAME))
//
//        coVerify {
//            navigator.emitDestination(
//                Destination(CategoryDetailDestination.createCategoryDetailRoute(ID, CATEGORY_NAME)),
//            )
//        }
//    }
//
//    @Test
//    fun `should trigger Navigator emitAction with CreateCategoryAction when NavigateToCreateCategory is provided`() {
//        sut.onEvent(NavigateToCreateCategory)
//
//        coVerify {
//            navigator.emitDestination(Destination(CreateCategoryDestination.route()))
//        }
//    }
//
//    @Test
//    fun `should update categoryViewEffect with Error when UndoCategoryDeletion is provided and deleteCategory returns Left`() =
//        runTest {
//            val uiCategory = mockk<UiCategory>()
//            val category = mockk<Category>()
//            coEvery {
//                undoCategoryDeletion(category)
//            } coAnswers { Unknown.buildLeft() }
//
//            sut.onEvent(UndoCategoryDeletion(uiCategory))
//
//            sut.viewEffect.test {
//                assertThat(awaitItem()).isEqualTo(CategoryViewEffect.Error("Couldn't undo category deletion."))
//            }
//        }
//
//    @Test
//    fun `should update not categoryViewEffect with Error when UndoCategoryDeletion is provided and deleteCategory returns Right`() =
//        runTest {
//            val uiCategory = mockk<UiCategory>()
//            val category = mockk<Category>()
//            coEvery {
//                undoCategoryDeletion(category)
//            } coAnswers { Unit.buildRight() }
//
//            sut.onEvent(UndoCategoryDeletion(uiCategory))
//
//            sut.viewEffect.test {
//                expectNoEvents()
//            }
//        }
}

private const val ID = "id"
private const val TITLE = "title"
private const val BACKGROUND_COLOR = 123
private const val TITLE_COLOR = 312
