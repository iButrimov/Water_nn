package com.example.water_nn.presentation.main.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.water_nn.R
import com.example.water_nn.databinding.FragmentOrderBinding
import com.example.water_nn.presentation.main.Contract
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderFragment : Fragment(R.layout.fragment_order) {

    private var _binding: FragmentOrderBinding? = null
    private val binding get() = _binding!!

    private val orderViewModel: Contract.IOrderViewModel by viewModel<OrderViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        orderViewModel.getAllOrders()

        orderViewModel.orderList.observe(viewLifecycleOwner) {
            //adapter.notifyDataSetChanged()
        }
    }
}