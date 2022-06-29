package com.example.myapplication.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currencies")
data class CurrencyEntity(
    @PrimaryKey
    @ColumnInfo(name = "id") var id: String,
    @ColumnInfo(name = "symbol") var symbol: String,
    @ColumnInfo(name = "currency_symbol") val currencySymbol: String?,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "rate_usd") val rateUsd: String
)