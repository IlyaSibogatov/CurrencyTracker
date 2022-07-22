package com.example.myapplication.fragments.detailsscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.db.CurrencyEntity
import com.example.myapplication.repositories.CurrencyRepository

class DetailsViewModel(
    id: String,
    private val currencyRepository: CurrencyRepository
) : ViewModel() {

    val currency: LiveData<CurrencyEntity> = currencyRepository.getCurrencyFromDaoById(id)
    val ru: LiveData<CurrencyEntity> = currencyRepository.getCurrencyFromDaoById(RU)
    val eu: LiveData<CurrencyEntity> = currencyRepository.getCurrencyFromDaoById(EU)

    private var firstRate = 0F

    fun setRates(rate: Float) {
        firstRate = rate
    }

    fun getRates(secondRate: Float): Float {
        return (firstRate / secondRate)
    }

    companion object {
        const val RU = "russian-ruble"
        const val EU = "euro"
    }
}