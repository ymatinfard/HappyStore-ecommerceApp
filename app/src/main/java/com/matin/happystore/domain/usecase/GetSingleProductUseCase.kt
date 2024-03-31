package com.matin.happystore.domain.usecase

import com.matin.happystore.domain.HappyStoreRepository
import com.matin.happystore.domain.model.Product
import com.matin.happystore.domain.util.Result
import javax.inject.Inject

class GetSingleProductUseCase @Inject constructor(private val repository: HappyStoreRepository) {

    suspend operator fun invoke(id: Int): Result<Product> {
        return repository.getSingleProduct(id)
    }
}