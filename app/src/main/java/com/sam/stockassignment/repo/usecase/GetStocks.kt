package com.sam.stockassignment.repo.usecase

import com.sam.stockassignment.data.StockWholeData
import com.sam.stockassignment.repo.Repository

class GetStocks(private var repository: Repository): ApiUseCase<StockWholeData, Any?>() {
    override suspend fun getApiData(req: Any?): StockWholeData? {
        return repository.getStocks()
    }
}