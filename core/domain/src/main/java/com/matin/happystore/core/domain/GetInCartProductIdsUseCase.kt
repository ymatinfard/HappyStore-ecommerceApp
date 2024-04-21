package com.matin.happystore.core.domain

import com.matin.data.HappyStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetInCartProductIdsUseCase
    @Inject
    constructor(private val repository: HappyStoreRepository) {
        suspend operator fun invoke(): Flow<List<Int>> {
            //     val inCartProductIds = repository.getInCartProducts().first().map { it.id }

//        return repository.getProducts().map { allProducts ->
//
//            val inCartProducts = allProducts.filter {
//                it.id in inCartProductIds
//            }
//            inCartProducts
//        }

            return repository.getInCartProductIds()
        }
    }
