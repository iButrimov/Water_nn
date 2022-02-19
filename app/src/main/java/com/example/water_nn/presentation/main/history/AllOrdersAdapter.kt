package com.example.water_nn.presentation.main.history

import com.example.water_nn.databinding.ItemCompletedOrderBinding
import com.example.water_nn.domain.models.DeliveryDay
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

private fun completedOrderAdapterDelegate(orderOnClickedListener: (Int) -> Unit) =
    adapterDelegateViewBinding<ItemOrder.CompletedOrder, ItemOrder?, ItemCompletedOrderBinding>(
        { layoutInflater, root ->
            ItemCompletedOrderBinding.inflate(
                layoutInflater,
                root,
                false
            )
        }) {
        binding.root.setOnClickListener {
            orderOnClickedListener(item.order.id)
        }

        bind {
            binding.apply {
                orderDate.text =
                    when (item.order.deliveryDay) {
                        DeliveryDay.TODAY -> "Сегодня"      //убрать
                        DeliveryDay.TOMORROW -> "Завтра"    //убрать
                    }
                orderAddress.text =
                            "ул. ${item.order.street}, " +
                            "д.${item.order.building}, " +
                            "кв.${item.order.apartment}, " +
                            "эт.${item.order.floor}"
                orderPhone.text = item.order.phoneNumber
                orderBottleQuantity.text = "${item.order.quantityWater} поз."
                price.text = "${item.order.totalPrice} ₽"
            }
        }
    }

fun ordersAdapterDelegates(orderOnClickedListener: (Int) -> Unit) = ListDelegationAdapter(
    completedOrderAdapterDelegate(orderOnClickedListener)
)