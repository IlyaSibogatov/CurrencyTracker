package com.example.myapplication.fragments.converter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.koin.java.KoinJavaComponent.getKoin

class ConverterViewModelFactory :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ConverterViewModel(getKoin().get()) as T
    }
}