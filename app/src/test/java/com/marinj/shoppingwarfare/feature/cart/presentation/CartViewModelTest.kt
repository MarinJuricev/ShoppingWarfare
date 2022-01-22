package com.marinj.shoppingwarfare.feature.cart.presentation

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.MainCoroutineRule
import com.marinj.shoppingwarfare.core.mapper.FailureToStringMapper
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.buildLeft
import com.marinj.shoppingwarfare.core.result.buildRight
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.CheckoutCart
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.DeleteCartItem
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.ObserveCartItems
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.UpdateCartItemQuantity
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.ValidateReceiptPath
import com.marinj.shoppingwarfare.feature.cart.presentation.mapper.CartItemsToUiCartItemsMapper
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartEvent
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartEvent.OnGetCartItems
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartViewEffect.CartViewCheckoutCompleted
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartViewEffect.CartViewItemDeleted
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartViewEffect.Error
import com.marinj.shoppingwarfare.feature.cart.presentation.model.ReceiptStatus
import com.marinj.shoppingwarfare.feature.cart.presentation.viewmodel.CartViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

private const val ID = "id"
private const val NAME = "name"
private const val ERROR_MESSAGE = "errorMessage"

@ExperimentalCoroutinesApi
class CartViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private val observeCartItems: ObserveCartItems = mockk()
    private val deleteCartItem: DeleteCartItem = mockk()
    private val updateCartItemQuantity: UpdateCartItemQuantity = mockk()
    private val checkoutCart: CheckoutCart = mockk()
    private val validateReceiptPath: ValidateReceiptPath = mockk()
    private val cartItemsToUiCartItemsMapper: CartItemsToUiCartItemsMapper = mockk()
    private val failureToStringMapper: FailureToStringMapper = mockk()

    private lateinit var sut: CartViewModel

    @Before
    fun setUp() {
        sut = CartViewModel(
            observeCartItems = observeCartItems,
            deleteCartItem = deleteCartItem,
            updateCartItemQuantity = updateCartItemQuantity,
            checkoutCart = checkoutCart,
            validateReceiptPath = validateReceiptPath,
            cartItemsToCartDataMapper = cartItemsToUiCartItemsMapper,
            failureToStringMapper = failureToStringMapper,
        )
    }

    @Test
    fun `should update cartData when OnGetCartItems is provided and emits cartData`() =
        runTest {
            val cartItem = mockk<CartItem>()
            val cartItems = listOf(cartItem)
            val cartData = mockk<Map<String, List<CartItem>>>()
            val cartItemsFlow = flow {
                emit(cartItems)
            }
            coEvery {
                observeCartItems()
            } coAnswers { cartItemsFlow }
            coEvery {
                cartItemsToUiCartItemsMapper.map(cartItems)
            } coAnswers { cartData }

            sut.viewState.test {
                val initialViewState = awaitItem()
                assertThat(initialViewState.cartData).isEmpty()
                assertThat(initialViewState.isLoading).isTrue()

                sut.onEvent(OnGetCartItems)

                val updatedViewState = awaitItem()
                assertThat(updatedViewState.cartData).isEqualTo(cartData)
                assertThat(updatedViewState.cartItems).isEqualTo(cartItems)
                assertThat(updatedViewState.isLoading).isFalse()
            }
        }

    @Test
    fun `should update viewEffect with Error when OnGetCartItems is provided and throws an exception`() =
        runTest {
            val cartItemsFlow = flow<List<CartItem>> {
                throw Exception()
            }
            coEvery {
                observeCartItems()
            } coAnswers { cartItemsFlow }

            sut.viewState.test {
                val initialViewState = awaitItem()
                assertThat(initialViewState.cartData).isEmpty()
                assertThat(initialViewState.isLoading).isTrue()

                sut.onEvent(OnGetCartItems)

                assertThat(awaitItem().isLoading).isFalse()
            }

            sut.viewEffect.test {
                assertThat(awaitItem()).isEqualTo(Error("Failed to fetch cart items, please try again later."))
            }
        }

    @Test
    fun `should update viewEffect with CartItemDeleted when DeleteCartItem is provided and deleteCartItem returns Right`() =
        runTest {
            val cartItem = mockk<CartItem>().apply {
                every { id } answers { ID }
                every { name } answers { NAME }
            }
            coEvery {
                deleteCartItem(cartItemId = ID)
            } coAnswers { Unit.buildRight() }

            sut.onEvent(CartEvent.DeleteCartItem(cartItem))

            sut.viewEffect.test {
                assertThat(awaitItem()).isEqualTo(CartViewItemDeleted(NAME))
            }
        }

    @Test
    fun `should update viewEffect with Error when DeleteCartItem is provided and deleteCartItem returns Left`() =
        runTest {
            val cartItem = mockk<CartItem>().apply {
                every { id } answers { ID }
                every { name } answers { NAME }
            }
            val failure = Failure.Unknown.buildLeft()
            coEvery {
                deleteCartItem(cartItemId = ID)
            } coAnswers { failure }

            sut.onEvent(CartEvent.DeleteCartItem(cartItem))

            sut.viewEffect.test {
                assertThat(awaitItem()).isEqualTo(Error("Failed to delete $NAME, please try again later."))
            }
        }

    @Test
    fun `should not update viewEffect when CartItemQuantityChanged is provided and updateCartItemQuantity returns Right`() =
        runTest {
            val cartItem = mockk<CartItem>().apply {
                every { name } answers { NAME }
            }
            val newQuantity = 5
            coEvery {
                updateCartItemQuantity(
                    cartItemToUpdate = cartItem,
                    newQuantity = newQuantity
                )
            } coAnswers { Unit.buildRight() }

            sut.onEvent(
                CartEvent.CartItemQuantityChanged(
                    cartItemToUpdate = cartItem,
                    newQuantity = newQuantity
                )
            )

            sut.viewEffect.test {
                expectNoEvents()
            }
        }

    @Test
    fun `should update viewEffect with Error when CartItemQuantityChanged is provided and updateCartItemQuantity returns Left`() =
        runTest {
            val cartItem = mockk<CartItem>().apply {
                every { name } answers { NAME }
            }
            val newQuantity = 5
            coEvery {
                updateCartItemQuantity(
                    cartItemToUpdate = cartItem,
                    newQuantity = newQuantity
                )
            } coAnswers { Failure.Unknown.buildLeft() }

            sut.onEvent(
                CartEvent.CartItemQuantityChanged(
                    cartItemToUpdate = cartItem,
                    newQuantity = newQuantity
                )
            )

            sut.viewEffect.test {
                assertThat(awaitItem()).isEqualTo(Error("Failed to update $NAME, please try again later"))
            }
        }

    @Test
    fun `should update viewState receiptStatus to Error when ReceiptCaptureError is provided`() =
        runTest {
            sut.onEvent(CartEvent.ReceiptCaptureError)

            sut.viewState.test {
                assertThat(awaitItem().receiptStatus).isEqualTo(ReceiptStatus.Error)
            }
        }

    @Test
    fun `should update viewState receiptStatus with result from validateReceiptPath when ReceiptCaptureSuccess is provided`() =
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
    fun `should update viewEffect when CheckoutClicked is provided and checkoutCart returns Right`() =
        runTest {
            coEvery {
                checkoutCart(
                    cartItems = sut.viewState.value.cartItems,
                    receiptPath = sut.viewState.value.receiptStatus.receiptPath,
                    cartName = sut.viewState.value.cartName,
                )
            } coAnswers { Unit.buildRight() }

            sut.onEvent(CartEvent.CheckoutClicked)

            sut.viewEffect.test {
                assertThat(awaitItem()).isEqualTo(CartViewCheckoutCompleted)
            }
        }

    @Test
    fun `should update viewEffect when CheckoutClicked is provided and checkoutCart returns Left`() =
        runTest {
            val checkoutFailure = Failure.Unknown

            coEvery {
                checkoutCart(
                    cartItems = sut.viewState.value.cartItems,
                    receiptPath = sut.viewState.value.receiptStatus.receiptPath,
                    cartName = sut.viewState.value.cartName,
                )
            } coAnswers { checkoutFailure.buildLeft() }
            every {
                failureToStringMapper.map(checkoutFailure)
            } answers { ERROR_MESSAGE }

            sut.onEvent(CartEvent.CheckoutClicked)

            sut.viewEffect.test {
                assertThat(awaitItem()).isEqualTo(Error(ERROR_MESSAGE))
            }
        }

    @Test
    fun `should update viewState when CartNameUpdated is provided`() = runTest {
        val newCartName = "newCartName"

        sut.onEvent(CartEvent.CartNameUpdated(newCartName))

        sut.viewState.test {
            assertThat(awaitItem().cartName).isEqualTo(newCartName)
        }
    }
}
