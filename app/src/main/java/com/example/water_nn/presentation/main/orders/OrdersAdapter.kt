package com.example.water_nn.presentation.main.orders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.water_nn.R
import com.example.water_nn.data.database.entity.Order
import com.example.water_nn.databinding.ItemCompletedOrderBinding
import com.example.water_nn.domain.models.DeliveryDay
import com.example.water_nn.presentation.lists.RecyclerAdapter

private val diffCallback = object : DiffUtil.ItemCallback<Order>() {
    override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem.equals(newItem)
    }
}

class OrdersAdapter(
    private val onItemClickListener: (String) -> Unit
) : RecyclerAdapter<Order, OrdersAdapter.ViewHolder>(diffCallback) {

    inner class ViewHolder(private val binding: ItemCompletedOrderBinding) :
        RecyclerAdapter.ViewHolder<Order>(binding) {

        override fun bind(item: Order, position: Int) {
            binding.root.setOnClickListener { onItemClickListener(item.id.toString()) }
            binding.orderAddress.text = binding.root.context.resources.getString(
                R.string.ORDERS_ADDRESS_DELIVERY_TEXT,
                item.street,
                item.building,
                item.apartment,
                item.floor
            )
            binding.orderPhone.text = item.phoneNumber
            binding.orderDate.text =
                when (item.deliveryDay) {
                    DeliveryDay.TODAY -> "Сегодня"      //убрать
                    DeliveryDay.TOMORROW -> "Завтра"    //убрать
                }
            binding.orderBottleQuantity.text = binding.root.context.resources.getString(
                R.string.ORDERS_BOTTLE_QTY_TEXT,
                item.quantityWater
            )
            binding.price.text = binding.root.context.resources.getString(
                R.string.ORDERS_TOTAL_PRICE_TEXT,
                item.totalPrice
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCompletedOrderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}