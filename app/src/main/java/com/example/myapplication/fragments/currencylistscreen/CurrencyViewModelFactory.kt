package com.example.myapplication.fragments.currencylistscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.koin.java.KoinJavaComponent.getKoin

class CurrencyViewModelFactory :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CurrencyViewModel(getKoin().get()) as T
    }
}