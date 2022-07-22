package com.example.myapplication.repositories

import com.example.myapplication.api.Api
import com.example.myapplication.api.ListCurrency
import com.example.myapplication.db.CurrencyEntity
import com.example.myapplication.db.MyDao
import retrofit2.Callback

class CurrencyRepository(
    private val api: Api,
    private val myDao: MyDao
) {

    fun getCurrenciesFromApi(callback: Callback<ListCurrency>) {
        api.getCurrenciesList().enqueue(callback)
    }

    fun insertCurrencyToDao(currency: List<CurrencyEntity>) {
        myDao.insertCurrencies(currency)
    }

    fun getCurrenciesAsc() = myDao.getCurrenciesAsc()

    fun getCurrenciesDesc() = myDao.getCurrenciesDesc()

    fun getCurrencyByTypeAsc(type: String) = myDao.getCurrencyByTypeAsc(type)

    fun getCurrencyByTypeDesc(type: String) = myDao.getCurrencyByTypeDesc(type)

    fun getCurrencyFromDaoById(id: String) = myDao.getCurrencyById(id)

    fun searchCurrency(query: String) = myDao.searchCurrency(query)

    fun getCurrencyBySymbol(symbol: String) = myDao.getCurrencyBySymbol(symbol)

    fun getAll() = myDao.getAll()
}