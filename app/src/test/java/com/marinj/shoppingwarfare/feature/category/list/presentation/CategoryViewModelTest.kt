package com.marinj.shoppingwarfare.feature.category.list.presentation

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.MainCoroutineRule
import com.marinj.shoppingwarfare.core.navigation.NavigationEvent.Destination
import com.marinj.shoppingwarfare.core.navigation.Navigator
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.buildLeft
import com.marinj.shoppingwarfare.core.result.buildRight
import com.marinj.shoppingwarfare.feature.category.createcategory.presentation.navigation.CreateCategoryDestination
import com.marinj.shoppingwarfare.feature.category.detail.presentation.navigation.CategoryDetailDestination
import com.marinj.shoppingwarfare.feature.category.list.domain.model.Category
import com.marinj.shoppingwarfare.feature.category.list.domain.usecase.DeleteCategory
import com.marinj.shoppingwarfare.feature.category.list.domain.usecase.ObserveCategories
import com.marinj.shoppingwarfare.feature.category.list.domain.usecase.UndoCategoryDeletion
import com.marinj.shoppingwarfare.feature.category.list.presentation.mapper.CategoryToUiCategoryMapper
import com.marinj.shoppingwarfare.feature.category.list.presentation.mapper.UiCategoryToCategoryMapper
import com.marinj.shoppingwarfare.feature.category.list.presentation.model.CategoryViewEffect
import com.marinj.shoppingwarfare.feature.category.list.presentation.model.CategoryEvent
import com.marinj.shoppingwarfare.feature.category.list.presentation.model.CategoryEvent.NavigateToCreateCategory
import com.marinj.shoppingwarfare.feature.category.list.presentation.model.UiCategory
import com.marinj.shoppingwarfare.feature.category.list.presentation.viewmodel.CategoryViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

private const val ID = "id"
private const val CATEGORY_NAME = "fruits"

@ExperimentalTime
@ExperimentalCoroutinesApi
class CategoryViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private val observeCategories: ObserveCategories = mockk()
    private val deleteCategory: DeleteCategory = mockk()
    private val undoCategoryDeletion: UndoCategoryDeletion = mockk()
    private val categoryToUiCategoryMapper: CategoryToUiCategoryMapper = mockk()
    private val uiCategoryToCategoryMapper: UiCategoryToCategoryMapper = mockk()
    private val navigator: Navigator = mockk()

    private lateinit var sut: CategoryViewModel

    @Before
    fun setUp() {
        sut = CategoryViewModel(
            observeCategories,
            deleteCategory,
            undoCategoryDeletion,
            categoryToUiCategoryMapper,
            uiCategoryToCategoryMapper,
            navigator,
        )
    }

    @Test
    fun `should update categories in categoryViewState when GetCategories is provided and emits categories`() =
        runBlockingTest {
            val category = mockk<Category>()
            val uiCategory = mockk<UiCategory>()
            val listOfCategory = listOf(category)
            val listOfUiCategory = listOf(uiCategory)
            val categoriesFlow = flow {
                emit(listOfCategory)
            }
            coEvery {
                observeCategories()
            } coAnswers { categoriesFlow }
            coEvery {
                categoryToUiCategoryMapper.map(category)
            } coAnswers { uiCategory }

            sut.categoryViewState.test {
                val initialViewState = awaitItem()
                assertThat(initialViewState.categories).isEmpty()
                assertThat(initialViewState.isLoading).isTrue()

                sut.onEvent(CategoryEvent.GetCategories)

                val updatedViewState = awaitItem()
                assertThat(updatedViewState.categories).isEqualTo(listOfUiCategory)
                assertThat(updatedViewState.isLoading).isFalse()
            }
        }

    @Test
    fun `should update categoryViewEffect with Error when GetCategories is provided and emits an exception`() =
        runBlockingTest {
            val categoriesFlow = flow<List<Category>> {
                throw Exception()
            }
            coEvery {
                observeCategories()
            } coAnswers { categoriesFlow }

            sut.categoryViewState.test {
                val initialViewState = awaitItem()
                assertThat(initialViewState.categories).isEmpty()
                assertThat(initialViewState.isLoading).isTrue()

                sut.onEvent(CategoryEvent.GetCategories)

                val updatedViewState = awaitItem()
                assertThat(updatedViewState.isLoading).isFalse()
            }

            sut.categoryEffect.test {
                assertThat(awaitItem()).isEqualTo(CategoryViewEffect.Error("Failed to fetch Categories, try again later."))
            }
        }

    @Test
    fun `should update categoryViewEffect with DeleteCategory when DeleteCategory is provided and deleteCategory returns Right`() =
        runBlockingTest {
            val uiCategory = mockk<UiCategory>().apply {
                every { id } answers { ID }
            }
            coEvery {
                deleteCategory(ID)
            } coAnswers { Unit.buildRight() }

            sut.onEvent(CategoryEvent.DeleteCategory(uiCategory))

            sut.categoryEffect.test {
                assertThat(awaitItem()).isEqualTo(CategoryViewEffect.DeleteCategoryView(uiCategory))
            }
        }

    @Test
    fun `should update categoryViewEffect with Error when DeleteCategory is provided and deleteCategory returns Left`() =
        runBlockingTest {
            val uiCategory = mockk<UiCategory>().apply {
                every { id } answers { ID }
            }
            coEvery {
                deleteCategory(ID)
            } coAnswers { Failure.Unknown.buildLeft() }

            sut.onEvent(CategoryEvent.DeleteCategory(uiCategory))

            sut.categoryEffect.test {
                assertThat(awaitItem()).isEqualTo(CategoryViewEffect.Error("Error while deleting category."))
            }
        }

    @Test
    fun `should trigger Navigator emitDestination with CategoryDetailAction when NavigateToCategoryDetail is provided`() {
        coEvery {
            navigator.emitDestination(
                Destination(CategoryDetailDestination.createCategoryDetailRoute(ID, CATEGORY_NAME))
            )
        } returns Unit
        sut.onEvent(CategoryEvent.NavigateToCategoryDetail(ID, CATEGORY_NAME))

        coVerify {
            navigator.emitDestination(
                Destination(CategoryDetailDestination.createCategoryDetailRoute(ID, CATEGORY_NAME))
            )
        }
    }

    @Test
    fun `should trigger Navigator emitAction with CreateCategoryAction when NavigateToCreateCategory is provided`() {
        sut.onEvent(NavigateToCreateCategory)

        coVerify {
            navigator.emitDestination(Destination(CreateCategoryDestination.route()))
        }
    }

    @Test
    fun `should update categoryViewEffect with Error when UndoCategoryDeletion is provided and deleteCategory returns Left`() =
        runBlockingTest {
            val uiCategory = mockk<UiCategory>()
            val category = mockk<Category>()
            coEvery {
                uiCategoryToCategoryMapper.map(uiCategory)
            } coAnswers { category }
            coEvery {
                undoCategoryDeletion(category)
            } coAnswers { Failure.Unknown.buildLeft() }

            sut.onEvent(CategoryEvent.UndoCategoryDeletion(uiCategory))

            sut.categoryEffect.test {
                assertThat(awaitItem()).isEqualTo(CategoryViewEffect.Error("Couldn't undo category deletion."))
            }
        }

    @Test
    fun `should update not categoryViewEffect with Error when UndoCategoryDeletion is provided and deleteCategory returns Right`() =
        runBlockingTest {
            val uiCategory = mockk<UiCategory>()
            val category = mockk<Category>()
            coEvery {
                uiCategoryToCategoryMapper.map(uiCategory)
            } coAnswers { category }
            coEvery {
                undoCategoryDeletion(category)
            } coAnswers { Unit.buildRight() }

            sut.onEvent(CategoryEvent.UndoCategoryDeletion(uiCategory))

            sut.categoryEffect.test {
                expectNoEvents()
            }
        }
}
