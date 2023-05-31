package com.marinj.shoppingwarfare.feature.cart.presentation

import app.cash.turbine.test
import arrow.core.left
import arrow.core.right
import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.MainCoroutineRule
import com.marinj.shoppingwarfare.core.mapper.FailureToStringMapper
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.CheckoutCartImpl
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.DeleteCartItem
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.ObserveCartItems
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.UpdateCartItemIsInBasket
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.UpdateCartItemQuantity
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.ValidateReceiptPath
import com.marinj.shoppingwarfare.feature.cart.presentation.mapper.CartItemToUiCartItemMapper
import com.marinj.shoppingwarfare.feature.cart.presentation.mapper.UiCartItemToCartItemMapper
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartEvent
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartEvent.OnGetCartItems
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartViewEffect.CartViewCheckoutCompleted
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartViewEffect.CartViewItemDeleted
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartViewEffect.Error
import com.marinj.shoppingwarfare.feature.cart.presentation.model.ReceiptStatus
import com.marinj.shoppingwarfare.feature.cart.presentation.model.UiCartItem
import com.marinj.shoppingwarfare.feature.cart.presentation.viewmodel.CartViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

private const val ID = "id"
private const val NAME = "name"
private const val ERROR_MESSAGE = "errorMessage"
private const val IS_IN_BASKET = true

class CartViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private val observeCartItems: ObserveCartItems = mockk()
    private val deleteCartItem: DeleteCartItem = mockk()
    private val updateCartItemQuantity: UpdateCartItemQuantity = mockk()
    private val checkoutCart: CheckoutCartImpl = mockk()
    private val validateReceiptPath: ValidateReceiptPath = mockk()
    private val cartItemToUiCartItemMapper: CartItemToUiCartItemMapper = mockk()
    private val uiCartItemToCartItemMapper: UiCartItemToCartItemMapper = mockk()
    private val failureToStringMapper: FailureToStringMapper = mockk()
    private val updateCartItemIsInBasket: UpdateCartItemIsInBasket = mockk()

    private lateinit var sut: CartViewModel

    @Before
    fun setUp() {
        sut = CartViewModel(
            observeCartItems = observeCartItems,
            deleteCartItem = deleteCartItem,
            updateCartItemQuantity = updateCartItemQuantity,
            updateCartItemIsInBasket = updateCartItemIsInBasket,
            checkoutCart = checkoutCart,
            validateReceiptPath = validateReceiptPath,
            cartItemToUiCartItemMapper = cartItemToUiCartItemMapper,
            uiCartItemToCartItemMapper = uiCartItemToCartItemMapper,
            failureToStringMapper = failureToStringMapper,
        )
    }

    @Test
    fun `SHOULD update cartData when OnGetCartItems is provided and emits cartData`() =
        runTest {
            val cartItem = mockk<CartItem>()
            val uiCartItem = mockk<UiCartItem>()
            val uiCartItems = listOf(uiCartItem)
            val cartItems = listOf(cartItem)
            val cartItemsFlow = flow {
                emit(cartItems)
            }
            coEvery {
                observeCartItems()
            } coAnswers { cartItemsFlow }
            coEvery {
                cartItemToUiCartItemMapper.map(cartItems)
            } coAnswers { uiCartItems }

            sut.viewState.test {
                val initialViewState = awaitItem()
                assertThat(initialViewState.uiCartItems).isEmpty()
                assertThat(initialViewState.isLoading).isTrue()

                sut.onEvent(OnGetCartItems)

                val updatedViewState = awaitItem()
                assertThat(updatedViewState.uiCartItems).isEqualTo(uiCartItems)
                assertThat(updatedViewState.isLoading).isFalse()
            }
        }

    @Test
    fun `SHOULD update viewEffect with Error when OnGetCartItems is provided and throws an exception`() =
        runTest {
            val cartItemsFlow = flow<List<CartItem>> {
                throw Exception()
            }
            coEvery {
                observeCartItems()
            } coAnswers { cartItemsFlow }

            sut.viewState.test {
                val initialViewState = awaitItem()
                assertThat(initialViewState.uiCartItems).isEmpty()
                assertThat(initialViewState.isLoading).isTrue()

                sut.onEvent(OnGetCartItems)

                assertThat(awaitItem().isLoading).isFalse()
            }

            sut.viewEffect.test {
                assertThat(awaitItem()).isEqualTo(Error("Failed to fetch cart items, please try again later."))
            }
        }

    @Test
    fun `SHOULD update viewEffect with CartItemDeleted when DeleteCartItem is provided and deleteCartItem returns Right`() =
        runTest {
            val cartItem = mockk<UiCartItem.Content>().apply {
                every { id } answers { ID }
                every { name } answers { NAME }
            }
            coEvery {
                deleteCartItem(cartItemId = ID)
            } coAnswers { Unit.right() }

            sut.onEvent(CartEvent.DeleteCartItem(cartItem))

            sut.viewEffect.test {
                assertThat(awaitItem()).isEqualTo(CartViewItemDeleted(NAME))
            }
        }

    @Test
    fun `SHOULD update viewEffect with Error when DeleteCartItem is provided and deleteCartItem returns Left`() =
        runTest {
            val cartItem = mockk<UiCartItem.Content>().apply {
                every { id } answers { ID }
                every { name } answers { NAME }
            }
            val failure = Failure.Unknown.left()
            coEvery {
                deleteCartItem(cartItemId = ID)
            } coAnswers { failure }

            sut.onEvent(CartEvent.DeleteCartItem(cartItem))

            sut.viewEffect.test {
                assertThat(awaitItem()).isEqualTo(Error("Failed to delete $NAME, please try again later."))
            }
        }

    @Test
    fun `SHOULD not update viewEffect when CartItemQuantityChanged is provided and updateCartItemQuantity returns Right`() =
        runTest {
            val cartItem = mockk<UiCartItem.Content>().apply {
                every { id } answers { ID }
                every { name } answers { NAME }
            }
            val newQuantity = 5
            coEvery {
                updateCartItemQuantity(
                    cartItemId = ID,
                    newQuantity = newQuantity,
                )
            } coAnswers { Unit.right() }

            sut.onEvent(
                CartEvent.CartItemQuantityChanged(
                    cartItemToUpdate = cartItem,
                    newQuantity = newQuantity,
                ),
            )

            sut.viewEffect.test {
                expectNoEvents()
            }
        }

    @Test
    fun `SHOULD update viewEffect with Error when CartItemQuantityChanged is provided and updateCartItemQuantity returns Left`() =
        runTest {
            val cartItem = mockk<UiCartItem.Content>().apply {
                every { id } answers { ID }
                every { name } answers { NAME }
            }
            val newQuantity = 5
            coEvery {
                updateCartItemQuantity(
                    cartItemId = ID,
                    newQuantity = newQuantity,
                )
            } coAnswers { Failure.Unknown.left() }

            sut.onEvent(
                CartEvent.CartItemQuantityChanged(
                    cartItemToUpdate = cartItem,
                    newQuantity = newQuantity,
                ),
            )

            sut.viewEffect.test {
                assertThat(awaitItem()).isEqualTo(Error("Failed to update $NAME, please try again later"))
            }
        }

    @Test
    fun `SHOULD update viewState receiptStatus to Error when ReceiptCaptureError is provided`() =
        runTest {
            sut.onEvent(CartEvent.ReceiptCaptureError)

            sut.viewState.test {
                assertThat(awaitItem().receiptStatus).isEqualTo(ReceiptStatus.Error)
            }
        }

    @Test
    fun `SHOULD update viewState receiptStatus with result from validateReceiptPath when ReceiptCaptureSuccess is provided`() =
        runTest {
            val receiptPath = "receiptPath"
            val expectedResult = ReceiptStatus.Taken(receiptPath)
            every { validateReceiptPath(receiptPath) } answers { expectedResult }

            sut.onEvent(CartEvent.ReceiptCaptureSuccess(receiptPath))

            sut.viewState.test {
                assertThat(awaitItem().receiptStatus).isEqualTo(expectedResult)
            }
        }

    @Test
    fun `SHOULD update viewEffect when CheckoutClicked is provided and checkoutCart returns Right`() =
        runTest {
            val cartItems = mockk<List<CartItem>>()
            every {
                uiCartItemToCartItemMapper.map(sut.viewState.value.uiCartItems)
            } answers { cartItems }
            coEvery {
                checkoutCart(
                    cartItems = cartItems,
                    receiptPath = sut.viewState.value.receiptStatus.receiptPath,
                    cartName = sut.viewState.value.cartName,
                )
            } coAnswers { Unit.right() }

            sut.onEvent(CartEvent.CheckoutClicked)

            sut.viewEffect.test {
                assertThat(awaitItem()).isEqualTo(CartViewCheckoutCompleted)
            }
        }

    @Test
    fun `SHOULD update viewEffect when CheckoutClicked is provided and checkoutCart returns Left`() =
        runTest {
            val checkoutFailure = Failure.Unknown
            val cartItems = mockk<List<CartItem>>()
            every {
                uiCartItemToCartItemMapper.map(sut.viewState.value.uiCartItems)
            } answers { cartItems }
            coEvery {
                checkoutCart(
                    cartItems = cartItems,
                    receiptPath = sut.viewState.value.receiptStatus.receiptPath,
                    cartName = sut.viewState.value.cartName,
                )
            } coAnswers { checkoutFailure.left() }
            every {
                failureToStringMapper.map(checkoutFailure)
            } answers { ERROR_MESSAGE }

            sut.onEvent(CartEvent.CheckoutClicked)

            sut.viewEffect.test {
                assertThat(awaitItem()).isEqualTo(Error(ERROR_MESSAGE))
            }
        }

    @Test
    fun `SHOULD update viewState when CartNameUpdated is provided`() = runTest {
        val newCartName = "newCartName"

        sut.onEvent(CartEvent.CartNameUpdated(newCartName))

        sut.viewState.test {
            assertThat(awaitItem().cartName).isEqualTo(newCartName)
        }
    }

    @Test
    fun `SHOULD not update viewEffect when ItemAddedToBasket is provided and updateCartItemIsInBasket returns Right`() =
        runTest {
            val cartItem = mockk<UiCartItem.Content>().apply {
                every { id } answers { ID }
                every { isInBasket } answers { IS_IN_BASKET }
            }
            coEvery {
                updateCartItemIsInBasket(
                    cartItemId = ID,
                    updatedIsInBasket = !IS_IN_BASKET,
                )
            } coAnswers { Unit.right() }

            sut.onEvent(
                CartEvent.ItemAddedToBasket(
                    cartItem = cartItem,
                ),
            )

            sut.viewEffect.test {
                expectNoEvents()
            }
        }

    @Test
    fun `SHOULD update viewEffect with Error when ItemAddedToBasket is provided and updateCartItemIsInBasket returns Left`() =
        runTest {
            val cartItem = mockk<UiCartItem.Content>().apply {
                every { id } answers { ID }
                every { name } answers { NAME }
                every { isInBasket } answers { IS_IN_BASKET }
            }
            coEvery {
                updateCartItemIsInBasket(
                    cartItemId = ID,
                    updatedIsInBasket = !IS_IN_BASKET,
                )
            } coAnswers { Failure.Unknown.left() }

            sut.onEvent(
                CartEvent.ItemAddedToBasket(
                    cartItem = cartItem,
                ),
            )

            sut.viewEffect.test {
                assertThat(awaitItem()).isEqualTo(Error("Failed to update $NAME, please try again later"))
            }
        }

    @Test
    fun `SHOULD update viewState when CartTabPositionUpdated is provided`() = runTest {
        val newCartTabPosition = 1

        sut.onEvent(CartEvent.CartTabPositionUpdated(newCartTabPosition))

        sut.viewState.test {
            assertThat(awaitItem().selectedTabPosition).isEqualTo(newCartTabPosition)
        }
    }
}
