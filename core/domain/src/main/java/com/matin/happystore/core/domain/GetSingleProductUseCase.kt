package com.matin.happystore.core.domain

import com.matin.data.HappyStoreRepository
import com.matin.happystore.core.model.ui.UiProduct
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSingleProductUseCase
@Inject
constructor(private val repository: HappyStoreRepository) {
    operator fun invoke(id: Int): Flow<UiProduct> {
        return combine(
            repository.getSingleProduct(id),
            repository.getInCartProductIds(),
            ::Pair
        ).map { productToCart ->
            UiProduct(
                product = productToCart.first, isInCart = productToCart
                    .second.contains(productToCart.first.id), isExpended = false, isFavorite = false
            )
        }
    }
}
