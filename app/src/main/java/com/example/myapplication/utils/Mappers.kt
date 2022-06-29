package com.example.myapplication.utils

import com.example.myapplication.api.Currency
import com.example.myapplication.api.ListCurrency
import com.example.myapplication.db.CurrencyEntity

class Mappers {

    fun toDao(currency: Currency) = CurrencyEntity(
        id = currency.id,
        symbol = currency.symbol,
        currencySymbol = currency.currencySymbol,
        type = currency.type,
        rateUsd = currency.rateUsd
    )

    fun toApi(currency: Currency) = Currency(
        id = currency.id,
        symbol = currency.symbol,
        currencySymbol = currency.currencySymbol,
        type = currency.type,
        rateUsd = currency.rateUsd
    )

    fun toApiList(list: ListCurrency) = list.data.map {
        toApi(it)
    }

    fun toDaoList(api: ListCurrency) = api.data.map {
        toDao(it)
    }
}