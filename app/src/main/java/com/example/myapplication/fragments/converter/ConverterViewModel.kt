package com.example.myapplication.fragments.converter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.repositories.CurrencyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.DecimalFormat

class ConverterViewModel(
    private val currencyRepository: CurrencyRepository
) : ViewModel() {

    val symbols = arrayListOf<String>()
    private val rates = arrayListOf<Float>()

    private var firstCurrencies = 0f
    private var secondCurrencies = 0f
    private var firstSymbol = ""
    private var secondSymbol = ""
    private var result = ""

    init {
        getAll()
    }

    private fun getAll() {
        viewModelScope.launch(Dispatchers.IO) {
            val currencies = currencyRepository.getAll()
            currencies.forEach {
                symbols.add(it.symbol)
                rates.add(it.rateUsd.toFloat())
            }
        }
    }

    fun equalsSymbols(): Boolean {
        return firstSymbol == secondSymbol
    }

    fun setRate(number: Int, id: Int) {
        when (number) {
            FIRST_RATE -> {
                firstCurrencies = rates[id]
                firstSymbol = symbols[id]
            }
            SECOND_RATE -> {
                secondCurrencies = rates[id]
                secondSymbol = symbols[id]
            }
        }
    }

    fun getResult(count: String): String {
        result =
            decimalFormat.format(firstCurrencies * count.toFloat() / secondCurrencies).toString()
        return "$count $firstSymbol = $result $secondSymbol"
    }

    companion object {
        private val decimalFormat = DecimalFormat("#.#####")
        private const val FIRST_RATE = 1
        private const val SECOND_RATE = 2
    }
}