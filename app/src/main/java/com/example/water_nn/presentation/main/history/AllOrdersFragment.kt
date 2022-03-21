package com.example.water_nn.presentation.main.history

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.water_nn.R
import com.example.water_nn.databinding.FragmentAllOrdersBinding
import com.example.water_nn.presentation.main.Contract
import org.koin.androidx.viewmodel.ext.android.viewModel

class AllOrdersFragment : Fragment(R.layout.fragment_all_orders) {

    private val binding by viewBinding(FragmentAllOrdersBinding::bind)

    private val allOrdersViewModel: Contract.IAllOrdersViewModel by viewModel<AllOrdersViewModel>()
    private val adapter = ordersAdapterDelegates {
        val args = NewOrderFragmentArgs(
            id = it.toString()
        ).toBundle()
        findNavController().navigate(R.id.action_allOrdersFragment_to_newOrderFragment, args)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.allOrdersRecyclerView.adapter = adapter

        allOrdersViewModel.getAllOrders()

        allOrdersViewModel.orderList.observe(viewLifecycleOwner) { orders ->
            adapter.items = orders.map {
                ItemOrder.CompletedOrder(it)
            }
            adapter.notifyDataSetChanged()

            if (orders.isEmpty()) {
                binding.orderIcon.isVisible = true
                binding.notOrdersYet.isVisible = true
            } else {
                binding.orderIcon.isVisible = false
                binding.notOrdersYet.isVisible = false
            }
        }

        binding.createNewOrderButton.setOnClickListener {
            val args = NewOrderFragmentArgs(id = "").toBundle()
            findNavController().navigate(R.id.action_allOrdersFragment_to_newOrderFragment, args)
        }

        val itemTouchHelperCallback =
            object :
                ItemTouchHelper.SimpleCallback(
                    0, ItemTouchHelper.LEFT //or ItemTouchHelper.RIGHT
                ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean = false

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    allOrdersViewModel.deleteOrder((adapter.items[viewHolder.bindingAdapterPosition] as ItemOrder.CompletedOrder).order)
                }
            }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.allOrdersRecyclerView)

    }
}