package com.example.water_nn.presentation.main.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.water_nn.R
import com.example.water_nn.databinding.FragmentAllOrdersBinding
import com.example.water_nn.presentation.main.Contract
import org.koin.androidx.viewmodel.ext.android.viewModel

class AllOrdersFragment : Fragment(R.layout.fragment_all_orders) {

    private var _binding: FragmentAllOrdersBinding? = null
    private val binding get() = _binding!!

    private val allOrdersViewModel: Contract.IAllOrdersViewModel by viewModel<AllOrdersViewModel>()
    private val adapter = OrdersAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.allOrdersRecyclerView.adapter = adapter

        allOrdersViewModel.getAllOrders()

        allOrdersViewModel.orderList.observe(viewLifecycleOwner) {
            adapter.list = it
            adapter.notifyDataSetChanged()
        }

        binding.createNewOrderButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_newOrderFragment)
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
                    allOrdersViewModel.deleteOrder(adapter.getOrderById(viewHolder.bindingAdapterPosition))
                }
            }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.allOrdersRecyclerView)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}