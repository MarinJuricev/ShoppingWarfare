package com.marinj.shoppingwarfare.feature.cart.di

import com.marinj.shoppingwarfare.feature.cart.data.remote.CartApi
import com.marinj.shoppingwarfare.feature.cart.data.remote.CartApiImpl
import com.marinj.shoppingwarfare.feature.cart.data.repository.CartRepositoryImpl
import com.marinj.shoppingwarfare.feature.cart.domain.repository.CartRepository
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.AddToCart
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.AddToCartImpl
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.CheckoutCart
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.CheckoutCartImpl
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.DeleteCartItem
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.DeleteCartItemImpl
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.ObserveCartItems
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.ObserveCartItemsCount
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.ObserveCartItemsCountImpl
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.ObserveCartItemsImpl
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.UpdateCartItemIsInBasket
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.UpdateCartItemIsInBasketImpl
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.UpdateCartItemQuantity
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.UpdateCartItemQuantityImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class CartModule {

    @Binds
    abstract fun bindCartRepository(
        repository: CartRepositoryImpl,
    ): CartRepository

    @Binds
    abstract fun bindCartApi(
        cartApiImpl: CartApiImpl,
    ): CartApi

    @Binds
    abstract fun bindAddToCart(
        addToCartImpl: AddToCartImpl,
    ): AddToCart

    @Binds
    abstract fun bindCheckoutCart(
        checkoutCartImpl: CheckoutCartImpl,
    ): CheckoutCart

    @Binds
    abstract fun bindDeleteCartItem(
        deleteCartItemImpl: DeleteCartItemImpl,
    ): DeleteCartItem

    @Binds
    abstract fun bindObserveCartItems(
        observeCartItemsImpl: ObserveCartItemsImpl,
    ): ObserveCartItems

    @Binds
    abstract fun bindObserveCartCount(
        observeCartItemsCountImpl: ObserveCartItemsCountImpl,
    ): ObserveCartItemsCount


    @Binds
    abstract fun bindUpdateCartItemsIsInBasket(
        updateCartItemIsInBasketImpl: UpdateCartItemIsInBasketImpl,
    ): UpdateCartItemIsInBasket

    @Binds
    abstract fun bindUpdateCartItemQuantity(
        updateCartItemQuantityImpl: UpdateCartItemQuantityImpl,
    ): UpdateCartItemQuantity
}
