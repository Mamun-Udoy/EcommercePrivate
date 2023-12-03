package com.example.ecommerce.ui.check_out_item

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.ecommerce.R
import com.example.ecommerce.databinding.FragmentCheckOutBinding


class CheckOutFragment : Fragment() {

    private lateinit var binding: FragmentCheckOutBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCheckOutBinding.inflate(inflater)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.placeOrder.setOnClickListener {
            requireView().findNavController().navigate(R.id.paymentFragment)
        }
    }


}