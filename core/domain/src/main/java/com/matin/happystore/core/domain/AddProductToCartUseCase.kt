package com.matin.happystore.core.domain

import com.matin.data.HappyStoreRepository
import com.matin.happystore.core.model.InCartProduct
import com.matin.happystore.core.model.Product
import javax.inject.Inject

class AddProductToCartUseCase
    @Inject
    constructor(private val repository: HappyStoreRepository) {
        suspend operator fun invoke(
            id: Int,
            quantity: Int,
        ) {
            repository.addToCart(InCartProduct(Product(id), quantity))
        }
    }
