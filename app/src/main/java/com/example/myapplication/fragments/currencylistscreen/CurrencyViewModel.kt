package com.example.myapplication.fragments.currencylistscreen

import android.widget.Toast
import androidx.lifecycle.*
import com.example.myapplication.api.ListCurrency
import com.example.myapplication.db.CurrencyEntity
import com.example.myapplication.di.DependencyStorage
import com.example.myapplication.repositories.CurrencyRepository
import com.example.myapplication.utils.MyUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.getKoin
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

enum class SortOrder {
    ASC,
    DESC,
    SEARCH
}

enum class ShowedType {
    ALL,
    CRYPTO,
    FIAT
}

class CurrencyViewModel(
    private val currencyRepository: CurrencyRepository
) : ViewModel() {

    private val sortOrderLiveData = MutableLiveData(SortOrder.ASC)
    private val showTypeLiveData = MutableLiveData(ShowedType.ALL)
    var oldShowType = ShowedType.ALL
    var oldSortOrder = SortOrder.ASC
    var query: String = EMPTY_QUERY

    fun setShowType(showedType: ShowedType) {
        oldShowType = showedType
        showTypeLiveData.value = showedType
    }

    fun setSortOrder(sortOrder: SortOrder) {
        if (sortOrder != SortOrder.SEARCH) {
            oldSortOrder = sortOrder
        }
        sortOrderLiveData.value = sortOrder
    }

    val currencies: LiveData<List<CurrencyEntity>> = sortOrderLiveData.switchMap {
        when (it) {
            SortOrder.ASC -> {
                showTypeLiveData.switchMap {
                    when (it) {
                        ShowedType.ALL -> currencyRepository.getCurrenciesAsc()
                        ShowedType.CRYPTO -> currencyRepository.getCurrencyByTypeAsc(TYPE_CRYPTO)
                        ShowedType.FIAT -> currencyRepository.getCurrencyByTypeAsc(TYPE_FIAT)
                    }
                }
            }
            SortOrder.DESC -> {
                showTypeLiveData.switchMap {
                    when (it) {
                        ShowedType.ALL -> currencyRepository.getCurrenciesDesc()
                        ShowedType.CRYPTO -> currencyRepository.getCurrencyByTypeDesc(TYPE_CRYPTO)
                        ShowedType.FIAT -> currencyRepository.getCurrencyByTypeDesc(TYPE_FIAT)
                    }
                }
            }
            SortOrder.SEARCH -> currencyRepository.searchCurrency(query)
        }
    }

    init {
        getApiRequest()
    }

    fun getApiRequest() {
        if (MyUtils.isInternetAvailable(getKoin().get())) {
            viewModelScope.launch(Dispatchers.IO) {
                currencyRepository.getCurrenciesFromApi(object : Callback<ListCurrency> {

                    override fun onResponse(
                        call: Call<ListCurrency>,
                        response: Response<ListCurrency>
                    ) {
                        response.body()?.let { responseBody ->
                            if (responseBody.data.isNotEmpty()) {
                                viewModelScope.launch(Dispatchers.IO) {
                                    currencyRepository.insertCurrencyToDao(
                                        DependencyStorage.Mappers.mappers.toDaoList(
                                            responseBody
                                        )
                                    )
                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<ListCurrency>, t: Throwable) {
                        Toast.makeText(
                            getKoin().get(),
                            FAILURE_MESSAGE,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
            }
        } else {
            Toast.makeText(
                getKoin().get(),
                NO_INTERNET_CONNECTION_MESSAGE,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    companion object {
        private const val NO_INTERNET_CONNECTION_MESSAGE = "No internet connection"
        private const val FAILURE_MESSAGE = "Something went wrong"
        private const val EMPTY_QUERY = ""
        private const val TYPE_CRYPTO = "crypto"
        private const val TYPE_FIAT = "fiat"
    }
}