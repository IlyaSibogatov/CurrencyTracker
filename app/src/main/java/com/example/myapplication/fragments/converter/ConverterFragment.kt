package com.example.myapplication.fragments.converter

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentConverterBinding
import com.example.myapplication.utils.MyUtils.hideKeyboard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.getKoin

class ConverterFragment : Fragment(R.layout.fragment_converter) {

    private lateinit var viewModel: ConverterViewModel
    private lateinit var controller: NavController

    private var _binding: FragmentConverterBinding? = null
    private val binding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controller = Navigation.findNavController(view)
        _binding = FragmentConverterBinding.bind(view)

        initViewModel()
        setupSpinner()

        binding.apply {

            btnBack.setOnClickListener {
                controller.popBackStack()
            }

            btnConvert.setOnClickListener {
                requireView().hideKeyboard()
                if (viewModel.equalsSymbols()) {
                    Toast.makeText(
                        getKoin().get(),
                        context!!.getString(R.string.same_currencies_selected),
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (enterCount.text.isEmpty()) {
                    Toast.makeText(
                        getKoin().get(),
                        context!!.getString(R.string.null_enter_value),
                        Toast.LENGTH_SHORT
                    ).show()
                    result.text = CLEAR_RESULT
                } else {
                    result.text = viewModel.getResult(enterCount.text.toString())
                }
            }
        }
    }

    private fun setupSpinner() {
        ArrayAdapter(
            getKoin().get(),
            android.R.layout.simple_spinner_item,
            viewModel.symbols
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.apply {
                lifecycleScope.launch(Dispatchers.Main) {
                    binding.apply {
                        chooseTo.adapter = adapter
                        chooseFrom.adapter = adapter
                    }
                }
            }
        }

        binding.apply {
            chooseFrom.onItemSelectedListener = (object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.setRate(FIRST_RATE, position)
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            })
            chooseTo.onItemSelectedListener = (object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.setRate(SECOND_RATE, position)
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            })
        }
    }

    private fun initViewModel() {
        val viewModelFactory = ConverterViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[ConverterViewModel::class.java]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val CLEAR_RESULT = ""
        private const val FIRST_RATE = 1
        private const val SECOND_RATE = 2
    }
}