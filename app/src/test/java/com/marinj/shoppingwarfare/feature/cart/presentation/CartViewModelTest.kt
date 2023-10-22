package com.marinj.shoppingwarfare.feature.cart.presentation

import app.cash.turbine.test
import com.marinj.shoppingwarfare.MainCoroutineRule
import com.marinj.shoppingwarfare.core.mapper.FailureToStringMapper
import com.marinj.shoppingwarfare.feature.cart.FakeFailureCheckoutCart
import com.marinj.shoppingwarfare.feature.cart.FakeFailureDeleteCartItem
import com.marinj.shoppingwarfare.feature.cart.FakeFailureObserveCartItems
import com.marinj.shoppingwarfare.feature.cart.FakeFailureUpdateCartItemIsInBasket
import com.marinj.shoppingwarfare.feature.cart.FakeFailureUpdateCartItemQuantity
import com.marinj.shoppingwarfare.feature.cart.FakeSuccessCheckoutCart
import com.marinj.shoppingwarfare.feature.cart.FakeSuccessDeleteCartItem
import com.marinj.shoppingwarfare.feature.cart.FakeSuccessObserveCartItems
import com.marinj.shoppingwarfare.feature.cart.FakeSuccessUpdateCartItemIsInBasket
import com.marinj.shoppingwarfare.feature.cart.FakeSuccessUpdateCartItemQuantity
import com.marinj.shoppingwarfare.feature.cart.buildUiCartItemContent
import com.marinj.shoppingwarfare.feature.cart.buildUiCartItemHeader
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.CheckoutCart
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
import com.marinj.shoppingwarfare.feature.cart.presentation.viewmodel.CartViewModel
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class CartViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @Test
    fun `SHOULD update cartData WHEN OnGetCartItems is provided and emits cartData`() =
        runTest {
            val uiCartItems = listOf(
                buildUiCartItemHeader(),
                buildUiCartItemContent(),
            )
            val sut = buildSut()

            sut.onEvent(OnGetCartItems)

            sut.viewState.test {
                val updatedViewState = awaitItem()
                updatedViewState.uiCartItems shouldBe uiCartItems
                updatedViewState.isLoading.shouldBeFalse()
            }
        }

    @Test
    fun `SHOULD update viewEffect with Error WHEN OnGetCartItems is provided and throws an exception`() =
        runTest {
            val sut = buildSut(providedObserveCartItems = FakeFailureObserveCartItems)

            sut.viewState.test {
                val initialViewState = awaitItem()
                initialViewState.uiCartItems.shouldBeEmpty()
                initialViewState.isLoading.shouldBeTrue()

                sut.onEvent(OnGetCartItems)

                awaitItem().isLoading.shouldBeFalse()
            }

            sut.viewEffect.test {
                awaitItem() shouldBe Error("Failed to fetch cart items, please try again later.")
            }
        }

    @Test
    fun `SHOULD update viewEffect with CartItemDeleted WHEN DeleteCartItem is provided and deleteCartItem returns Right`() =
        runTest {
            val uiCartItem = buildUiCartItemContent(
                providedId = ID,
                providedName = NAME,
            )
            val sut = buildSut()

            sut.onEvent(CartEvent.DeleteCartItem(uiCartItem))

            sut.viewEffect.test {
                awaitItem() shouldBe CartViewItemDeleted(NAME)
            }
        }

    @Test
    fun `SHOULD update viewEffect with Error WHEN DeleteCartItem is provided and deleteCartItem returns Left`() =
        runTest {
            val uiCartItem = buildUiCartItemContent(
                providedId = ID,
                providedName = NAME,
            )
            val sut = buildSut(providedDeleteCartItem = FakeFailureDeleteCartItem)
            sut.onEvent(CartEvent.DeleteCartItem(uiCartItem))

            sut.viewEffect.test {
                awaitItem() shouldBe Error("Failed to delete $NAME, please try again later.")
            }
        }

    @Test
    fun `SHOULD not update viewEffect WHEN CartItemQuantityChanged is provided and updateCartItemQuantity returns Right`() =
        runTest {
            val uiCartItem = buildUiCartItemContent(
                providedId = ID,
                providedName = NAME,
            )
            val newQuantity = 5
            val sut = buildSut()

            sut.onEvent(
                CartEvent.CartItemQuantityChanged(
                    cartItemToUpdate = uiCartItem,
                    newQuantity = newQuantity,
                ),
            )

            sut.viewEffect.test {
                expectNoEvents()
            }
        }

    @Test
    fun `SHOULD update viewEffect with Error WHEN CartItemQuantityChanged is provided and updateCartItemQuantity returns Left`() =
        runTest {
            val uiCartItem = buildUiCartItemContent(
                providedId = ID,
                providedName = NAME,
            )
            val newQuantity = 5
            val sut = buildSut(providedUpdateCartItemQuantity = FakeFailureUpdateCartItemQuantity)

            sut.onEvent(
                CartEvent.CartItemQuantityChanged(
                    cartItemToUpdate = uiCartItem,
                    newQuantity = newQuantity,
                ),
            )

            sut.viewEffect.test {
                awaitItem() shouldBe Error("Failed to update $NAME, please try again later")
            }
        }

    @Test
    fun `SHOULD update viewState receiptStatus to Error WHEN ReceiptCaptureError is provided`() =
        runTest {
            val sut = buildSut()
            sut.onEvent(CartEvent.ReceiptCaptureError)

            sut.viewState.test {
                awaitItem().receiptStatus shouldBe ReceiptStatus.Error
            }
        }

    @Test
    fun `SHOULD update viewState receiptStatus with result from validateReceiptPath WHEN ReceiptCaptureSuccess is provided`() =
        runTest {
            val receiptPath = "receiptPath"
            val expectedResult = ReceiptStatus.Taken(receiptPath)
            val sut = buildSut()

            sut.onEvent(CartEvent.ReceiptCaptureSuccess(receiptPath))

            sut.viewState.test {
                awaitItem().receiptStatus shouldBe expectedResult
            }
        }

    @Test
    fun `SHOULD update viewEffect WHEN CheckoutClicked is provided and checkoutCart returns Right`() =
        runTest {
            val sut = buildSut()

            sut.onEvent(CartEvent.CheckoutClicked)

            sut.viewEffect.test {
                awaitItem() shouldBe CartViewCheckoutCompleted
            }
        }

    @Test
    fun `SHOULD update viewEffect WHEN CheckoutClicked is provided and checkoutCart returns Left`() =
        runTest {
            val sut = buildSut(providedCheckoutCart = FakeFailureCheckoutCart)

            sut.onEvent(CartEvent.CheckoutClicked)

            sut.viewEffect.test {
                awaitItem() shouldBe Error("Unknown Error Occurred, please try again later")
            }
        }

    @Test
    fun `SHOULD update viewState WHEN CartNameUpdated is provided`() = runTest {
        val newCartName = "newCartName"
        val sut = buildSut()

        sut.onEvent(CartEvent.CartNameUpdated(newCartName))

        sut.viewState.test {
            awaitItem().cartName shouldBe newCartName
        }
    }

    @Test
    fun `SHOULD not update viewEffect when ItemAddedToBasket is provided and updateCartItemIsInBasket returns Right`() =
        runTest {
            val uiCartItem = buildUiCartItemContent(
                providedId = ID,
                providedName = NAME,
                providedIsInBasket = IS_IN_BASKET,
            )
            val sut = buildSut()
            sut.onEvent(
                CartEvent.ItemAddedToBasket(
                    cartItem = uiCartItem,
                ),
            )

            sut.viewEffect.test {
                expectNoEvents()
            }
        }

    @Test
    fun `SHOULD update viewEffect with Error when ItemAddedToBasket is provided and updateCartItemIsInBasket returns Left`() =
        runTest {
            val uiCartItem = buildUiCartItemContent(
                providedId = ID,
                providedName = NAME,
                providedIsInBasket = IS_IN_BASKET,
            )
            val sut = buildSut(providedUpdateCartItemIsInBasket = FakeFailureUpdateCartItemIsInBasket)

            sut.onEvent(
                CartEvent.ItemAddedToBasket(
                    cartItem = uiCartItem,
                ),
            )

            sut.viewEffect.test {
                awaitItem() shouldBe Error("Failed to update $NAME, please try again later")
            }
        }

    @Test
    fun `SHOULD update viewState when CartTabPositionUpdated is provided`() = runTest {
        val newCartTabPosition = 1
        val sut = buildSut()

        sut.onEvent(CartEvent.CartTabPositionUpdated(newCartTabPosition))

        sut.viewState.test {
            awaitItem().selectedTabPosition shouldBe newCartTabPosition
        }
    }

    private fun buildSut(
        providedObserveCartItems: ObserveCartItems = FakeSuccessObserveCartItems(),
        providedDeleteCartItem: DeleteCartItem = FakeSuccessDeleteCartItem,
        providedUpdateCartItemQuantity: UpdateCartItemQuantity = FakeSuccessUpdateCartItemQuantity,
        providedUpdateCartItemIsInBasket: UpdateCartItemIsInBasket = FakeSuccessUpdateCartItemIsInBasket,
        providedCheckoutCart: CheckoutCart = FakeSuccessCheckoutCart,
        providedValidateReceiptPath: ValidateReceiptPath = ValidateReceiptPath(),
        providedFailureToStringMapper: FailureToStringMapper = FailureToStringMapper(),
        providedCartItemToUiCartItemMapper: CartItemToUiCartItemMapper = CartItemToUiCartItemMapper(),
        providedUiCartItemToCartItemMapper: UiCartItemToCartItemMapper = UiCartItemToCartItemMapper(),
    ) = CartViewModel(
        observeCartItems = providedObserveCartItems,
        deleteCartItem = providedDeleteCartItem,
        updateCartItemQuantity = providedUpdateCartItemQuantity,
        updateCartItemIsInBasket = providedUpdateCartItemIsInBasket,
        checkoutCart = providedCheckoutCart,
        validateReceiptPath = providedValidateReceiptPath,
        failureToStringMapper = providedFailureToStringMapper,
        cartItemToUiCartItemMapper = providedCartItemToUiCartItemMapper,
        uiCartItemToCartItemMapper = providedUiCartItemToCartItemMapper,
    )
}

private const val ID = "id"
private const val NAME = "name"
private const val IS_IN_BASKET = true
