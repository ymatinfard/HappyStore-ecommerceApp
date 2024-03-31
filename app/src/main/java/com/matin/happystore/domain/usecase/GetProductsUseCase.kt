package com.matin.happystore.domain.usecase

import com.matin.happystore.domain.HappyStoreRepository
import com.matin.happystore.domain.model.Product
import com.matin.happystore.domain.util.Result
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(private val repository: HappyStoreRepository) {

    suspend operator fun invoke(): Result<List<Product>> {
        return repository.getProducts()
    }
}