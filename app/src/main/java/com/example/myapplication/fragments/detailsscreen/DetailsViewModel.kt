package com.example.myapplication.fragments.detailsscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.repositories.CurrencyRepository
import com.example.myapplication.db.CurrencyEntity

class DetailsViewModel(
    id: String,
    currencyRepository: CurrencyRepository
) : ViewModel() {

    val currency: LiveData<CurrencyEntity> = currencyRepository.getCurrencyFromDaoById(id)
}