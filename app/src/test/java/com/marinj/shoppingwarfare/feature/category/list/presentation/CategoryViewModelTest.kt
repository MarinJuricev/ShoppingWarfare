package com.marinj.shoppingwarfare.feature.category.list.presentation

import app.cash.turbine.test
import com.marinj.shoppingwarfare.MainCoroutineRule
import com.marinj.shoppingwarfare.core.fixture.FakeNavigator
import com.marinj.shoppingwarfare.core.mapper.FailureToStringMapper
import com.marinj.shoppingwarfare.core.navigation.NavigationEvent.Destination
import com.marinj.shoppingwarfare.feature.category.detail.presentation.navigation.CATEGORY_NAME_PARAM
import com.marinj.shoppingwarfare.feature.category.detail.presentation.navigation.ProductDestination.createCategoryDetailRoute
import com.marinj.shoppingwarfare.feature.category.list.domain.model.Category
import com.marinj.shoppingwarfare.feature.category.list.presentation.model.CategoryEvent.DeleteCategory
import com.marinj.shoppingwarfare.feature.category.list.presentation.model.CategoryEvent.GetCategories
import com.marinj.shoppingwarfare.feature.category.list.presentation.model.CategoryEvent.NavigateToCategoryDetail
import com.marinj.shoppingwarfare.feature.category.list.presentation.model.CategoryEvent.UndoCategoryDeletion
import com.marinj.shoppingwarfare.feature.category.list.presentation.model.CategoryViewEffect.DeleteCategoryView
import com.marinj.shoppingwarfare.feature.category.list.presentation.model.CategoryViewEffect.Error
import com.marinj.shoppingwarfare.feature.category.list.presentation.model.UiCategory
import com.marinj.shoppingwarfare.feature.category.list.presentation.viewmodel.CategoryViewModel
import com.marinj.shoppingwarfare.fixtures.category.FakeFailureDeleteCategory
import com.marinj.shoppingwarfare.fixtures.category.FakeFailureObserveCategories
import com.marinj.shoppingwarfare.fixtures.category.FakeFailureUndoCategoryDeletion
import com.marinj.shoppingwarfare.fixtures.category.FakeSuccessDeleteCategory
import com.marinj.shoppingwarfare.fixtures.category.FakeSuccessObserveCategories
import com.marinj.shoppingwarfare.fixtures.category.FakeSuccessUndoCategoryDeletion
import com.marinj.shoppingwarfare.fixtures.category.buildCategory
import com.marinj.shoppingwarfare.fixtures.category.buildUiCategory
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.shouldBe
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
            FakeSuccessDeleteCategory,
            FakeSuccessUndoCategoryDeletion,
            FailureToStringMapper(),
            navigator,
        )

        sut.onEvent(GetCategories)

        sut.viewState.test {
            val updatedViewState = awaitItem()
            updatedViewState.categories shouldBe expectedResult
            updatedViewState.isLoading.shouldBeFalse()
        }
    }

    @Test
    fun `onEvent SHOULD update viewEffect WHEN GetCategories is provided and ObserveCategories throws exception`() = runTest {
        val sut = CategoryViewModel(
            FakeFailureObserveCategories,
            FakeSuccessDeleteCategory,
            FakeSuccessUndoCategoryDeletion,
            FailureToStringMapper(),
            navigator,
        )

        sut.onEvent(GetCategories)

        sut.viewEffect.test {
            awaitItem() shouldBe Error("Failed to fetch Categories, try again later.")
        }
    }

    @Test
    fun `onEvent SHOULD trigger navigation WHEN NavigateToCreateCategory is provided with DeleteCategory when DeleteCategory is provided`() =
        runTest {
            val sut = CategoryViewModel(
                FakeFailureObserveCategories,
                FakeSuccessDeleteCategory,
                FakeSuccessUndoCategoryDeletion,
                FailureToStringMapper(),
                navigator,
            )
            val expectedEvent = Destination(
                createCategoryDetailRoute(categoryId = ID, categoryName = CATEGORY_NAME_PARAM),
            )

            navigator.receivedEvents.test {
                sut.onEvent(
                    NavigateToCategoryDetail(
                        categoryId = ID,
                        categoryName = CATEGORY_NAME_PARAM,
                    ),
                )

                awaitItem() shouldBe expectedEvent
            }
        }

    @Test
    fun `onEvent SHOULD update viewEffect WHEN DeleteCategory is provided with DeleteCategory when DeleteCategory is provided and deleteCategory returns Right`() =
        runTest {
            val sut = CategoryViewModel(
                FakeFailureObserveCategories,
                FakeSuccessDeleteCategory,
                FakeSuccessUndoCategoryDeletion,
                FailureToStringMapper(),
                navigator,
            )
            val uiCategory = buildUiCategory()

            sut.onEvent(DeleteCategory(uiCategory))

            sut.viewEffect.test {
                awaitItem() shouldBe DeleteCategoryView(uiCategory)
            }
        }

    @Test
    fun `onEvent SHOULD update viewEffect WHEN DeleteCategory is provided with DeleteCategory when DeleteCategory is provided and deleteCategory returns Left`() =
        runTest {
            val sut = CategoryViewModel(
                FakeFailureObserveCategories,
                FakeFailureDeleteCategory,
                FakeSuccessUndoCategoryDeletion,
                FailureToStringMapper(),
                navigator,
            )
            val uiCategory = buildUiCategory()

            sut.onEvent(DeleteCategory(uiCategory))

            sut.viewEffect.test {
                awaitItem() shouldBe Error("Error while deleting category.")
            }
        }

    @Test
    fun `onEvent SHOULD update viewEffect WHEN UndoCategoryDeletion is provided and toDomain returns Left`() =
        runTest {
            val sut = CategoryViewModel(
                FakeFailureObserveCategories,
                FakeFailureDeleteCategory,
                FakeSuccessUndoCategoryDeletion,
                FailureToStringMapper(),
                navigator,
            )
            val uiCategory = buildUiCategory(providedTitle = "")

            sut.onEvent(UndoCategoryDeletion(uiCategory))

            sut.viewEffect.test {
                awaitItem() shouldBe Error("title can not be null or empty")
            }
        }

    @Test
    fun `onEvent SHOULD update viewEffect WHEN UndoCategoryDeletion is provided and undoCategoryDeletion returns Left`() =
        runTest {
            val sut = CategoryViewModel(
                FakeFailureObserveCategories,
                FakeFailureDeleteCategory,
                FakeFailureUndoCategoryDeletion,
                FailureToStringMapper(),
                navigator,
            )
            val uiCategory = buildUiCategory(providedTitle = TITLE)

            sut.onEvent(UndoCategoryDeletion(uiCategory))

            sut.viewEffect.test {
                awaitItem() shouldBe Error("Couldn't undo category deletion.")
            }
        }

    @Test
    fun `onEvent SHOULD not update viewEffect WHEN UndoCategoryDeletion is provided and undoCategoryDeletion returns Right`() =
        runTest {
            val sut = CategoryViewModel(
                FakeFailureObserveCategories,
                FakeFailureDeleteCategory,
                FakeSuccessUndoCategoryDeletion,
                FailureToStringMapper(),
                navigator,
            )
            val uiCategory = buildUiCategory(providedTitle = TITLE)

            sut.onEvent(UndoCategoryDeletion(uiCategory))

            sut.viewEffect.test {
                expectNoEvents()
            }
        }

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
            providedCategoryId = ID,
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
