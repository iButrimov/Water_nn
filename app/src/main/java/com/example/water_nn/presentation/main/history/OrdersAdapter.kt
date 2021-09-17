package com.example.water_nn.presentation.main.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.water_nn.data.database.entity.Order
import com.example.water_nn.databinding.ItemCompletedOrderBinding

class OrdersAdapter : RecyclerView.Adapter<OrdersAdapter.OrderViewHolder>() {

    var list: List<Order> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        return OrderViewHolder(parent)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = list[position]
        holder.bind(order)
    }

    override fun getItemCount(): Int {
        return list.size
    }

//    override fun getItemViewType(position: Int): Int {
//        return when(list[position]) {
//            is Order -> COMPLETED_ORDER_ITEM_TYPE
//            is CurrentOrder -> COMPLETED_ORDER_ITEM_TYPE
//        }
//    }

    class OrderViewHolder(private val binding: ItemCompletedOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        constructor(parent: ViewGroup) : this(
            ItemCompletedOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

        fun bind(order: Order) {
            binding.apply {
                orderAddress.text = order.address
                orderDate.text = order.deliveryDay.name
                orderBottleQuantity.text = "${order.quantityWater} поз."
                price.text = "${order.totalPrice} ₽"
            }
        }
    }

    fun getOrderById (position: Int): Order {
        return list[position]
    }

//    companion object {
//        const val CURRENT_ORDER_ITEM_TYPE = 1
//        const val COMPLETED_ORDER_ITEM_TYPE = 2
//    }
}