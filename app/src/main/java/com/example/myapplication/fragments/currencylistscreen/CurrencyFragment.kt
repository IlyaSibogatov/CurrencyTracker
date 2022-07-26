package com.example.myapplication.fragments.currencylistscreen

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.activities.SettingsActivity
import com.example.myapplication.databinding.FragmentCurrenciesScreenBinding
import com.example.myapplication.db.CurrencyEntity
import com.example.myapplication.utils.MyUtils.hideKeyboard
import kotlinx.android.synthetic.main.fragment_currencies_screen.*
import org.koin.android.ext.android.getKoin

class CurrencyFragment : Fragment(R.layout.fragment_currencies_screen),
    CurrencyAdapter.OnCurrencyClickListener {

    private lateinit var rvAdapter: CurrencyAdapter
    private lateinit var viewModel: CurrencyViewModel
    private lateinit var controller: NavController
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sharedLayoutManager: String
    private lateinit var sharedCurrencyType: String

    private var _binding: FragmentCurrenciesScreenBinding? = null
    private val binding
        get() = _binding!!

    private val observer: (List<CurrencyEntity>) -> Unit = { rvAdapter.setList(it) }
    private var currencyLiveData: LiveData<List<CurrencyEntity>>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rvAdapter = CurrencyAdapter(this)
        sharedPreferences = PreferenceManager
            .getDefaultSharedPreferences(getKoin().get())
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controller = Navigation.findNavController(view)
        _binding = FragmentCurrenciesScreenBinding.bind(view)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        initViewModel()

        binding.apply {

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                android.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    searchView.clearFocus()
                    requireView().hideKeyboard()
                    query.toString().also { viewModel.query = it }
                    viewModel.setSortOrder(SortOrder.SEARCH)
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText.isNullOrEmpty()) {
                        viewModel.setSortOrder(viewModel.oldSortOrder)
                        viewModel.setShowType(viewModel.oldShowType)
                    } else {
                        "$newText".also { viewModel.query = it }
                        viewModel.setSortOrder(SortOrder.SEARCH)
                    }
                    return false
                }
            })

            swipeToRefresh.setOnRefreshListener {
                refreshData()
                swipeToRefresh.isRefreshing = false
            }
        }

        viewModel.currencies.observe(viewLifecycleOwner) {
            rvAdapter.setList(it)
        }

        subscribeToCurrencies()
    }

    private fun subscribeToCurrencies() {
        currencyLiveData?.removeObserver(observer)
        currencyLiveData = viewModel.currencies.also {
            it.observe(viewLifecycleOwner, observer)
        }
    }

    private fun refreshData() {
        viewModel.getApiRequest()
    }

    override fun onCurrencyClick(id: String) {
        val bundle = Bundle()
        bundle.putString(ARGUMENT_ID, id)
        controller.navigate(R.id.action_currencyFragment_to_detailsFragment, bundle)
    }

    private fun initViewModel() {
        val viewModelFactory = CurrencyViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[CurrencyViewModel::class.java]
    }

    override fun onResume() {
        loadSettings()
        super.onResume()
    }

    private fun loadSettings() {
        sharedLayoutManager = sharedPreferences.getString(LIST_FORMAT_KEY, GET_LINEAR)!!
        rvNote.adapter = rvAdapter
        when (sharedLayoutManager) {
            GET_LINEAR -> rvNote.layoutManager = LinearLayoutManager(requireContext())
            GET_GRID -> rvNote.layoutManager = GridLayoutManager(requireContext(), SPAN_COUNT)
        }
        sharedCurrencyType = sharedPreferences.getString(SHOW_KEY, SHOW_ALL)!!
        when (sharedCurrencyType) {
            SHOW_ALL -> viewModel.setShowType(ShowedType.ALL)
            SHOW_CRYPTO -> viewModel.setShowType(ShowedType.CRYPTO)
            SHOW_FIAT -> viewModel.setShowType(ShowedType.FIAT)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.sort_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.asc_list -> viewModel.setSortOrder(SortOrder.ASC)
            R.id.desc_list -> viewModel.setSortOrder(SortOrder.DESC)
            R.id.settings_activity -> {
                val intent = Intent(
                    getKoin().get(),
                    SettingsActivity::class.java
                )
                activity?.finish()
                startActivity(intent)
            }
            R.id.converter -> {
                controller.navigate(R.id.action_currencyFragment_to_converterFragment)
            }
        }
        return true
    }

    companion object {
        private const val LIST_FORMAT_KEY = "list_format"
        private const val GET_LINEAR = "Linear"
        private const val GET_GRID = "Grid"
        private const val SHOW_KEY = "show"
        private const val SHOW_ALL = "All"
        private const val SHOW_CRYPTO = "Crypto"
        private const val SHOW_FIAT = "Fiat"
        private const val SPAN_COUNT = 2
        private const val ARGUMENT_ID = "ARGUMENT_ID"
    }
}