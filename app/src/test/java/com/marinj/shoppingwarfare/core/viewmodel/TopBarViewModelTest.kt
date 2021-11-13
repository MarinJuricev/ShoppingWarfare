package com.marinj.shoppingwarfare.core.viewmodel

import androidx.compose.runtime.Composable
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.R
import com.marinj.shoppingwarfare.core.viewmodel.topbar.NoSearchBarTopBarViewState
import com.marinj.shoppingwarfare.core.viewmodel.topbar.SearchTopBarViewState
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent.CartTopBar
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent.CategoryDetailTopBar
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent.CategoryTopBar
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent.CreateCategoryTopBar
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent.HistoryTopBar
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent.UserTopBar
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import kotlin.time.ExperimentalTime

private const val SEARCH_TEXT = "search"

@ExperimentalCoroutinesApi
@ExperimentalTime
class TopBarViewModelTest {

    private lateinit var sut: TopBarViewModel

    @Before
    fun setUp() {
        sut = TopBarViewModel()
    }

    @Test
    fun `onEvent should update viewState to match categoryTopBar when CategoryTopBar is provided`() =
        runBlockingTest {
            val icon: @Composable () -> Unit = {}
            val onActionClicked = {}
            val event = CategoryTopBar(
                onActionClick = onActionClicked,
                icon = icon,
            )

            sut.onEvent(event)
            val expectedResult = NoSearchBarTopBarViewState(
                title = R.string.category,
                icon = icon,
                onActionClick = onActionClicked
            )

            sut.viewState.test {
                assertThat(awaitItem()).isEqualTo(expectedResult)
            }
        }

    @Test
    fun `onEvent should update viewState to match createCategoryTopBar when CreateCategoryTopBar is provided`() =
        runBlockingTest {
            val event = CreateCategoryTopBar()

            sut.onEvent(event)
            val expectedResult = NoSearchBarTopBarViewState(
                title = R.string.category,
                subTitle = R.string.create_category
            )

            sut.viewState.test {
                assertThat(awaitItem()).isEqualTo(expectedResult)
            }
        }

    @Test
    fun `onEvent should update viewState to match categoryDetailTopBar when CategoryDetailTopBar is provided`() =
        runBlockingTest {
            val icon: @Composable () -> Unit = {}
            val onActionClicked = {}
            val event = CategoryDetailTopBar(
                onActionClick = onActionClicked,
                icon = icon,
            )

            sut.onEvent(event)
            val expectedResult = NoSearchBarTopBarViewState(
                title = R.string.category,
                subTitle = R.string.category_detail,
                icon = icon,
                onActionClick = onActionClicked,
            )

            sut.viewState.test {
                assertThat(awaitItem()).isEqualTo(expectedResult)
            }
        }

    @Test
    fun `onEvent should update viewState to match cartTopBar when CartTopBar is provided`() =
        runBlockingTest {
            val event = CartTopBar()

            sut.onEvent(event)
            val expectedResult = NoSearchBarTopBarViewState(
                isTopBarVisible = false
            )

            sut.viewState.test {
                assertThat(awaitItem()).isEqualTo(expectedResult)
            }
        }

    @Test
    fun `onEvent should update viewState to match historyTopBar when HistoryTopBar is provided`() =
        runBlockingTest {
            val onTextChange: (String) -> Unit = {}
            val onActionClick: () -> Unit = {}
            val searchTextUpdated = { SEARCH_TEXT }
            val event = HistoryTopBar(
                searchTextUpdated =searchTextUpdated ,
                onTextChange = onTextChange,
                onActionClick = onActionClick
            )

            sut.onEvent(event)
            val expectedResult = SearchTopBarViewState(
                isTopBarVisible = true,
                searchText = searchTextUpdated,
                isSearchEnabled = true,
                onTextChange = onTextChange,
                onActionClick = onActionClick,
            )

            sut.viewState.test {
                assertThat(awaitItem()).isEqualTo(expectedResult)
            }
        }

    @Test
    fun `onEvent should update viewState to match userTopBar when UserTopBar is provided`() =
        runBlockingTest {
            val event = UserTopBar()

            sut.onEvent(event)
            val expectedResult = NoSearchBarTopBarViewState(
                isTopBarVisible = false
            )

            sut.viewState.test {
                assertThat(awaitItem()).isEqualTo(expectedResult)
            }
        }
}
