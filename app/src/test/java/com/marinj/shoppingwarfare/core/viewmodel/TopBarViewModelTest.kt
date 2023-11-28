package com.marinj.shoppingwarfare.core.viewmodel

import androidx.compose.runtime.Composable
import app.cash.turbine.test
import com.marinj.shoppingwarfare.MainCoroutineRule
import com.marinj.shoppingwarfare.R
import com.marinj.shoppingwarfare.core.viewmodel.topbar.NoSearchBarTopBarViewState
import com.marinj.shoppingwarfare.core.viewmodel.topbar.SearchTopBarViewState
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent.CartTopBar
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent.CategoryTopBar
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent.CreateCategoryTopBar
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent.HistoryDetailTopBar
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent.HistoryTopBar
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent.ProductTopBar
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent.UserTopBar
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarViewModel
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainCoroutineRule::class)
class TopBarViewModelTest {

    private val sut = TopBarViewModel()

    @Test
    fun `onEvent SHOULD update viewState to match categoryTopBar when CategoryTopBar is provided`() =
        runTest {
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
                onActionClick = onActionClicked,
            )

            sut.viewState.test {
                awaitItem() shouldBe expectedResult
            }
        }

    @Test
    fun `onEvent SHOULD update viewState to match createCategoryTopBar when CreateCategoryTopBar is provided`() =
        runTest {
            val navigationIcon: @Composable () -> Unit = {}
            val event = CreateCategoryTopBar(
                navigationIcon = navigationIcon,
            )

            sut.onEvent(event)
            val expectedResult = NoSearchBarTopBarViewState(
                title = R.string.category,
                subTitle = R.string.create_category,
                navigationIcon = navigationIcon,
            )

            sut.viewState.test {
                awaitItem() shouldBe expectedResult
            }
        }

    @Test
    fun `onEvent SHOULD update viewState to match categoryDetailTopBar when CategoryDetailTopBar is provided`() =
        runTest {
            val icon: @Composable () -> Unit = {}
            val navigationIcon: @Composable () -> Unit = {}
            val onActionClicked = {}
            val event = ProductTopBar(
                onActionClick = onActionClicked,
                icon = icon,
                navigationIcon = navigationIcon,
            )

            sut.onEvent(event)
            val expectedResult = NoSearchBarTopBarViewState(
                title = R.string.category,
                subTitle = R.string.category_detail,
                icon = icon,
                onActionClick = onActionClicked,
                navigationIcon = navigationIcon,
            )

            sut.viewState.test {
                awaitItem() shouldBe expectedResult
            }
        }

    @Test
    fun `onEvent SHOULD update viewState to match cartTopBar when CartTopBar is provided`() =
        runTest {
            val event = CartTopBar()

            sut.onEvent(event)
            val expectedResult = NoSearchBarTopBarViewState(
                isTopBarVisible = false,
            )

            sut.viewState.test {
                awaitItem() shouldBe expectedResult
            }
        }

    @Test
    fun `onEvent SHOULD update viewState to match historyTopBar when HistoryTopBar is provided`() =
        runTest {
            val onTextChange: (String) -> Unit = {}
            val onActionClick: () -> Unit = {}
            val searchTextUpdated = { SEARCH_TEXT }
            val event = HistoryTopBar(
                searchTextUpdated = searchTextUpdated,
                onTextChange = onTextChange,
                onActionClick = onActionClick,
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
                awaitItem() shouldBe expectedResult
            }
        }

    @Test
    fun `onEvent SHOULD update viewState to match userTopBar when UserTopBar is provided`() =
        runTest {
            val event = UserTopBar()

            sut.onEvent(event)
            val expectedResult = NoSearchBarTopBarViewState(
                isTopBarVisible = false,
            )

            sut.viewState.test {
                awaitItem() shouldBe expectedResult
            }
        }

    @Test
    fun `onEvent SHOULD update viewState to match historyDetailTopBar when HistoryDetailTopBar is provided`() =
        runTest {
            val navigationIcon: @Composable () -> Unit = {}
            val event = HistoryDetailTopBar(
                navigationIcon = navigationIcon,
            )

            sut.onEvent(event)
            val expectedResult = NoSearchBarTopBarViewState(
                title = R.string.history,
                subTitle = R.string.history_detail,
                navigationIcon = navigationIcon,
            )

            sut.viewState.test {
                awaitItem() shouldBe expectedResult
            }
        }
}

private const val SEARCH_TEXT = "search"
