package com.example.ecommerce.ui.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.ecommerce.R
import com.example.ecommerce.databinding.FragmentPaymentBinding

class PaymentFragment : Fragment() {

    private lateinit var binding:FragmentPaymentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rightArrow.setOnClickListener {
            requireView().findNavController().navigate(R.id.cardPaymentFragment)
        }
    }


}