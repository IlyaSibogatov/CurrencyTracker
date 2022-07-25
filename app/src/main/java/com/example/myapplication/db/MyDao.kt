package com.example.myapplication.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCurrencies(currencies: List<CurrencyEntity>)

    @Query("SELECT * FROM currencies ORDER BY symbol ASC")
    fun getCurrenciesAsc(): LiveData<List<CurrencyEntity>>

    @Query("SELECT * FROM currencies ORDER BY symbol DESC")
    fun getCurrenciesDesc(): LiveData<List<CurrencyEntity>>

    @Query("SELECT * FROM currencies WHERE type=:type ORDER BY symbol ASC")
    fun getCurrencyByTypeAsc(type: String): LiveData<List<CurrencyEntity>>

    @Query("SELECT * FROM currencies WHERE type=:type ORDER BY symbol DESC")
    fun getCurrencyByTypeDesc(type: String): LiveData<List<CurrencyEntity>>

    @Query("SELECT * FROM currencies WHERE id=:id")
    fun getCurrencyById(id: String): LiveData<CurrencyEntity>

    @Query("SELECT * FROM currencies WHERE id LIKE '%' || :query || '%' OR symbol LIKE '%' || :query || '%'")

    fun searchCurrency(query: String): LiveData<List<CurrencyEntity>>

    @Query("SELECT * FROM currencies ORDER BY symbol ASC")
    fun getAll(): List<CurrencyEntity>
}