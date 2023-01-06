package com.marinj.shoppingwarfare.feature.category.list.presentation

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.MainCoroutineRule
import com.marinj.shoppingwarfare.core.fixture.FakeNavigator
import com.marinj.shoppingwarfare.core.mapper.FailureToStringMapper
import com.marinj.shoppingwarfare.core.navigation.NavigationEvent.Destination
import com.marinj.shoppingwarfare.feature.category.detail.presentation.CATEGORY_NAME
import com.marinj.shoppingwarfare.feature.category.detail.presentation.navigation.CategoryDetailDestination.createCategoryDetailRoute
import com.marinj.shoppingwarfare.feature.category.list.domain.model.Category
import com.marinj.shoppingwarfare.feature.category.list.presentation.model.CategoryEvent.GetCategories
import com.marinj.shoppingwarfare.feature.category.list.presentation.model.CategoryEvent.NavigateToCategoryDetail
import com.marinj.shoppingwarfare.feature.category.list.presentation.model.CategoryViewEffect.Error
import com.marinj.shoppingwarfare.feature.category.list.presentation.model.UiCategory
import com.marinj.shoppingwarfare.feature.category.list.presentation.viewmodel.CategoryViewModel
import com.marinj.shoppingwarfare.fixtures.category.FakeFailureObserveCategories
import com.marinj.shoppingwarfare.fixtures.category.FakeSuccessDeleteCategory
import com.marinj.shoppingwarfare.fixtures.category.FakeSuccessObserveCategories
import com.marinj.shoppingwarfare.fixtures.category.FakeSuccessUndoCategoryDeletion
import com.marinj.shoppingwarfare.fixtures.category.buildCategory
import com.marinj.shoppingwarfare.fixtures.category.buildUiCategory
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class CategoryViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private val navigator = FakeNavigator

    @Test
    fun `onEvent SHOULD update categories WHEN GetCategories is provided and ObserveCategories emits categories`() = runTest {
        val categories = buildCategoryList()
        val expectedResult = buildUiCategoryList()
        val sut = CategoryViewModel(
            FakeSuccessObserveCategories(categories),
            FakeSuccessDeleteCategory(),
            FakeSuccessUndoCategoryDeletion(),
            FailureToStringMapper(),
            navigator,
        )

        sut.onEvent(GetCategories)

        sut.viewState.test {
            val updatedViewState = awaitItem()
            assertThat(updatedViewState.categories).isEqualTo(expectedResult)
            assertThat(updatedViewState.isLoading).isFalse()
        }
    }

    @Test
    fun `onEvent SHOULD update viewEffect WHEN GetCategories is provided and ObserveCategories throws exception`() = runTest {
        val sut = CategoryViewModel(
            FakeFailureObserveCategories(),
            FakeSuccessDeleteCategory(),
            FakeSuccessUndoCategoryDeletion(),
            FailureToStringMapper(),
            navigator,
        )

        sut.onEvent(GetCategories)

        sut.viewEffect.test {
            assertThat(awaitItem()).isEqualTo(Error("Failed to fetch Categories, try again later."))
        }
    }

    @Test
    fun `onEvent SHOULD trigger navigation WHEN NavigateToCreateCategory is provided with DeleteCategory when DeleteCategory is provided`() =
        runTest {
            val sut = CategoryViewModel(
                FakeFailureObserveCategories(),
                FakeSuccessDeleteCategory(),
                FakeSuccessUndoCategoryDeletion(),
                FailureToStringMapper(),
                navigator,
            )
            val expectedEvent = Destination(
                createCategoryDetailRoute(categoryId = ID, categoryName = CATEGORY_NAME),
            )

            navigator.receivedEvents.test {
                sut.onEvent(
                    NavigateToCategoryDetail(
                        categoryId = ID,
                        categoryName = CATEGORY_NAME,
                    ),
                )

                assertThat(awaitItem()).isEqualTo(expectedEvent)
            }
        }

//    @Test
//    fun `onEvent SHOULD update viewEffect WHEN DeleteCategory is provided with DeleteCategory when DeleteCategory is provided and deleteCategory returns Right`() =
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

    private fun buildUiCategoryList(): List<UiCategory> = listOf(
        buildUiCategory(
            providedCategoryId = ID,
            providedTitle = TITLE,
            providedBackgroundColor = BACKGROUND_COLOR,
            providedTitleColor = TITLE_COLOR,
        ),
    )

    private fun buildCategoryList(): List<Category> = listOf(
        buildCategory(
            providedId = ID,
            providedTitle = TITLE,
            providedBackgroundColor = BACKGROUND_COLOR,
            providedTitleColor = TITLE_COLOR,
        ),
    )
}

private const val ID = "id"
private const val TITLE = "title"
private const val BACKGROUND_COLOR = 123
private const val TITLE_COLOR = 312
