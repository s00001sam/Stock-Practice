package com.sam.stockassignment.repo.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

abstract class ApiUseCase<out Type, in Params> {
    abstract suspend fun getApiData(req: Params): Type?
    fun getFlow(req: Params): Flow<Type?> {
        return flow {
            val response = getApiData(req)
            emit(response)
        }.flowOn(Dispatchers.IO)
    }
}