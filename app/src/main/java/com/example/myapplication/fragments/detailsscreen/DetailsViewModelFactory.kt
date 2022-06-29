package com.example.myapplication.fragments.detailsscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.koin.java.KoinJavaComponent.getKoin

class DetailsViewModelFactory(private val id: String) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailsViewModel(
            id,
            getKoin().get()) as T
    }
}
