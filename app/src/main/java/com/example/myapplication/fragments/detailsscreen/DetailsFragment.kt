package com.example.myapplication.fragments.detailsscreen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentDetailsScreenBinding
import kotlinx.android.synthetic.main.fragment_details_screen.*
import java.text.DecimalFormat

class DetailsFragment : Fragment(R.layout.fragment_details_screen) {

    private lateinit var viewModel: DetailsViewModel
    private lateinit var controller: NavController

    private var _binding: FragmentDetailsScreenBinding? = null
    private val binding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controller = Navigation.findNavController(view)
        _binding = FragmentDetailsScreenBinding.bind(view)

        val id = arguments?.getString(ARGUMENT_ID) ?: INVALID_ID
        initViewModel(id as String)

        binding.apply {
            btn_back.setOnClickListener {
                controller.popBackStack()
            }
        }

        viewModel.currency.observe(viewLifecycleOwner){
            binding.itemIntegratedName.text = it.symbol
            if (it.currencySymbol.isNullOrEmpty()){
                binding.itemCharCode.visibility = View.GONE
                binding.itemIntegratedCharCode.visibility = View.GONE
            } else {
                binding.itemCharCode.visibility = View.VISIBLE
                binding.itemIntegratedCharCode.visibility = View.VISIBLE
                binding.itemIntegratedCharCode.text = it.currencySymbol
            }
            binding.itemIntegratedType.text = it.type
            binding.itemIntegratedValue.text = decimalFormat.format(it.rateUsd.toFloat())
        }
    }

    private fun initViewModel(id: String) {
        val viewModelFactory = DetailsViewModelFactory(id)
        viewModel = ViewModelProvider(this, viewModelFactory).get(DetailsViewModel::class.java)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        val decimalFormat = DecimalFormat("#.#####")
        private const val ARGUMENT_ID = "ARGUMENT_ID"
        private const val INVALID_ID = -1
    }
}