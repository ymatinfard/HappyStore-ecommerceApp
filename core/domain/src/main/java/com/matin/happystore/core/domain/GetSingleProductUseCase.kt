package com.matin.happystore.core.domain

import com.matin.data.HappyStoreRepository
import com.matin.happystore.core.common.Result
import com.matin.happystore.core.model.Product

import javax.inject.Inject

class GetSingleProductUseCase @Inject constructor(private val repository: HappyStoreRepository) {

    suspend operator fun invoke(id: Int): Result<Product> {
        return repository.getSingleProduct(id)
    }
}