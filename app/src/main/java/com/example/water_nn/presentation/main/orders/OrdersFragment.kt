package com.example.water_nn.presentation.main.orders

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.water_nn.R
import com.example.water_nn.databinding.FragmentOrdersBinding
import com.example.water_nn.presentation.BaseFragment
import com.example.water_nn.presentation.OneShot
import com.example.water_nn.presentation.main.history.NewOrderFragmentArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.water_nn.presentation.main.orders.OrdersEvent as Event
import com.example.water_nn.presentation.main.orders.OrdersState as State
import com.example.water_nn.presentation.main.orders.OrdersViewModel as ViewModel

class OrdersFragment : BaseFragment<ViewModel, State, Event>(
    R.layout.fragment_orders
) {

    override val viewModel: ViewModel by viewModel()
    override val binding: FragmentOrdersBinding by viewBinding(FragmentOrdersBinding::bind)

    private val adapter = OrdersAdapter {
        sendEvent((Event.OrderItemClicked(it)))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rv.adapter = adapter
        binding.rv.itemAnimator = null
        sendEvent(Event.OrdersListRequest)

        binding.createNewOrderButton.setOnClickListener {
            val args = NewOrderFragmentArgs(id = "").toBundle()
            findNavController().navigate(R.id.action_ordersFragment_to_newOrderFragment, args)
        }
    }

    override fun renderView(state: State) {
        super.renderView(state)

        adapter.setListData(state.orders)

        with(binding) {
            notOrdersYetIcon.isVisible = state.haveNotOrders
            notOrdersYetText.isVisible = state.haveNotOrders
        }
    }

    override fun renderOneShot(oneShot: OneShot) {
        when (oneShot) {
            is OrderOneShot.ShowOrderOneShot -> showOrderInfo(oneShot.id)
        }
        super.renderOneShot(oneShot)
    }

    private fun showOrderInfo(id: String) {
        val args = NewOrderFragmentArgs(id).toBundle()
        findNavController().navigate(R.id.action_ordersFragment_to_newOrderFragment, args)
    }
}