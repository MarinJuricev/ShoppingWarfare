package com.marinj.shoppingwarfare.core.viewmodel

import androidx.compose.runtime.Composable
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.R
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent.CartTopBar
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent.CategoryDetailTopBar
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent.CategoryTopBar
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent.CreateCategoryTopBar
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent.HistoryTopBar
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent.UserTopBar
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarViewModel
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarViewState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import kotlin.time.ExperimentalTime

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
            val expectedResult = TopBarViewState(
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
            val expectedResult = TopBarViewState(
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
            val expectedResult = TopBarViewState(
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
            val expectedResult = TopBarViewState(
                isVisible = false
            )

            sut.viewState.test {
                assertThat(awaitItem()).isEqualTo(expectedResult)
            }
        }

    @Test
    fun `onEvent should update viewState to match historyTopBar when HistoryTopBar is provided`() =
        runBlockingTest {
            val event = HistoryTopBar()

            sut.onEvent(event)
            val expectedResult = TopBarViewState(
                isVisible = false
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
            val expectedResult = TopBarViewState(
                isVisible = false
            )

            sut.viewState.test {
                assertThat(awaitItem()).isEqualTo(expectedResult)
            }
        }
}
