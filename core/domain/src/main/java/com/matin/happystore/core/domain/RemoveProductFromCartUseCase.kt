package com.matin.happystore.core.domain

import com.matin.data.HappyStoreRepository
import com.matin.happystore.core.model.InCartProduct
import javax.inject.Inject

class RemoveProductFromCartUseCase
    @Inject
    constructor(private val repository: HappyStoreRepository) {
        suspend operator fun invoke(inCartProduct: InCartProduct) {
            repository.removeFromCart(inCartProduct)
        }
    }
