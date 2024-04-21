package com.matin.data

import com.matin.data.model.toDomain
import com.matin.data.model.toEntity
import com.matin.happystore.core.common.Result
import com.matin.happystore.core.database.model.CartDao
import com.matin.happystore.core.database.model.ProductEntity
import com.matin.happystore.core.database.model.ProductsDao
import com.matin.happystore.core.model.InCartProduct
import com.matin.happystore.core.model.Product
import com.matin.happystore.core.network.HappyStoreApi
import com.matin.happystore.core.network.model.NetworkProduct
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HappyStoreRepositoryImpl
    @Inject
    constructor(
        private val api: HappyStoreApi,
        private val productsDao: ProductsDao,
        private val cartDao: CartDao,
    ) : HappyStoreRepository {
        override suspend fun getProducts(): Flow<List<Product>> = productsDao.getProducts().map { it.map( ProductEntity::toDomain ) }

        override suspend fun sync() =
            withContext(Dispatchers.IO) {
                val serverProducts = api.getAllProducts().map(NetworkProduct::toEntity)
                val cachedProducts = productsDao.getProducts().first()

                val deletedProductsFromServer = cachedProducts - serverProducts.toSet()

                productsDao.deleteProducts(deletedProductsFromServer.map(ProductEntity::id))
                productsDao.upsertProducts(serverProducts)
            }

        override suspend fun getSingleProduct(id: Int): Result<Product> = TODO()

        override suspend fun getInCartProductIds(): Flow<List<Int>> = cartDao.getInCartProductIds()

        override suspend fun addToCart(inCartProduct: InCartProduct) {
            cartDao.addToCart(inCartProduct.toEntity())
        }

        override suspend fun removeFromCart(inCartProduct: InCartProduct) {
            cartDao.removeFromCart(inCartProduct.toEntity())
        }

        override suspend fun getInCartProductsFullDetail(): Flow<List<InCartProduct>> =
            cartDao.getInCartProductsFullDetail().map { inCartProductFullList ->
                inCartProductFullList.map { it.toDomain() }
            }

        override suspend fun updateProductQuantity(inCartProduct: InCartProduct) {
            cartDao.updateQuantity(inCartProduct.toEntity())
        }
    }
