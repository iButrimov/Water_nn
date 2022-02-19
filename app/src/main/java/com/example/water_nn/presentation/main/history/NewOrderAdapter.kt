package com.example.water_nn.presentation.main.history

import androidx.core.widget.doAfterTextChanged
import com.example.water_nn.databinding.*
import com.example.water_nn.domain.models.DeliveryDay
import com.example.water_nn.domain.models.DeliveryTime
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

private fun orderUserInfoAdapterDelegate(
    onNameChangedListener: (String) -> Unit,
    onPhoneChangedListener: (String) -> Unit,
) =
    adapterDelegateViewBinding<ItemOrderCard.OrderUserInfo, ItemOrderCard?, ItemOrderUserInfoBinding>(
        { layoutInflater, root ->
            ItemOrderUserInfoBinding.inflate(
                layoutInflater,
                root,
                false
            )
        }) {
        binding.name.doAfterTextChanged {
            onNameChangedListener(it.toString())
        }
        binding.phoneNumber.doAfterTextChanged {
            onPhoneChangedListener(it.toString())
        }

        bind {
            binding.apply {
                name.setText(item.name)
                phoneNumber.setText(item.phone)
            }
        }
    }

private fun orderAddressInfoAdapterDelegate(
    onStreetChangedListener: (String) -> Unit,
    onBuildingChangedListener: (String) -> Unit,
    onFloorChangedListener: (String) -> Unit,
    onApartmentChangedListener: (String) -> Unit
) =
    adapterDelegateViewBinding<ItemOrderCard.OrderAddressInfo, ItemOrderCard?, ItemOrderAddressInfoBinding>(
        { layoutInflater, root ->
            ItemOrderAddressInfoBinding.inflate(
                layoutInflater,
                root,
                false
            )
        }) {
        binding.street.doAfterTextChanged {
            onStreetChangedListener(it.toString())
        }
        binding.buildingNumber.doAfterTextChanged {
            onBuildingChangedListener(it.toString())
        }
        binding.floorNumber.doAfterTextChanged {
            onFloorChangedListener(it.toString())
        }
        binding.apartmentNumber.doAfterTextChanged {
            onApartmentChangedListener(it.toString())
        }

        bind {
            binding.apply {
                street.setText(item.street)
                buildingNumber.setText(item.building)
                floorNumber.setText(item.floor)
                apartmentNumber.setText(item.apartment)
            }
        }
    }

private fun orderWaterInfoAdapterDelegate(
    minusFullBottleClickListener: () -> Unit,
    plusFullBottleClickListener: () -> Unit,
    minusEmptyBottleClickListener: () -> Unit,
    plusEmptyBottleClickListener: () -> Unit,
) =
    adapterDelegateViewBinding<ItemOrderCard.OrderWaterInfo, ItemOrderCard?, ItemOrderWaterInfoBinding>(
        { layoutInflater, root ->
            ItemOrderWaterInfoBinding.inflate(
                layoutInflater,
                root,
                false
            )
        }) {
        binding.minusFullBottleButton.setOnClickListener {
            minusFullBottleClickListener()
        }
        binding.plusFullBottleButton.setOnClickListener {
            plusFullBottleClickListener()
        }
        binding.minusEmptyBottleButton.setOnClickListener {
            minusEmptyBottleClickListener()
        }
        binding.plusEmptyBottleButton.setOnClickListener {
            plusEmptyBottleClickListener()
        }

        bind {
            binding.apply {
                qtyFullBottle.text = item.qtyFull.toString()
                qtyEmptyBottle.text = item.qtyEmpty.toString()
                priceFullBottle.text = "${item.priceFull.toInt()} ₽"
                priceEmptyBottle.text = "${item.priceEmpty.toInt()} ₽"
            }
        }
    }

private fun orderTimeInfoAdapterDelegate(
    onDeliveryDayClicked: (DeliveryDay) -> Unit,
    onDeliveryTimeClicked: (DeliveryTime) -> Unit
) =
    adapterDelegateViewBinding<ItemOrderCard.OrderTimeInfo, ItemOrderCard?, ItemOrderTimeInfoBinding>(
        { layoutInflater, root ->
            ItemOrderTimeInfoBinding.inflate(
                layoutInflater,
                root,
                false
            )
        }) {

        binding.chipToday.setOnClickListener {
            binding.chipToday.isChecked = true
            binding.chipTomorrow.isChecked = false
            onDeliveryDayClicked(DeliveryDay.TODAY)
        }

        binding.chipTomorrow.setOnClickListener {
            binding.chipToday.isChecked = false
            binding.chipTomorrow.isChecked = true
            onDeliveryDayClicked(DeliveryDay.TOMORROW)
        }

        binding.morning.setOnClickListener {
            onDeliveryTimeClicked(DeliveryTime.MORNING)
        }

        binding.noon.setOnClickListener {
            onDeliveryTimeClicked(DeliveryTime.NOON)
        }

        binding.afternoon.setOnClickListener {
            onDeliveryTimeClicked(DeliveryTime.AFTERNOON)
        }

        binding.evening.setOnClickListener {
            onDeliveryTimeClicked(DeliveryTime.EVENING)
        }

        bind {
            binding.apply {
                if (item.deliveryDay == DeliveryDay.TODAY) {
                    chipToday.isChecked = true
                    chipTomorrow.isChecked = false
                } else {
                    chipToday.isChecked = false
                    chipTomorrow.isChecked = true
                }

                morning.isChecked = item.deliveryTimes.contains(DeliveryTime.MORNING)
                noon.isChecked = item.deliveryTimes.contains(DeliveryTime.NOON)
                afternoon.isChecked = item.deliveryTimes.contains(DeliveryTime.AFTERNOON)
                evening.isChecked = item.deliveryTimes.contains(DeliveryTime.EVENING)
            }
        }
    }

private fun orderCommentInfoAdapterDelegate(
    onCommentChangedListener: (String) -> Unit
) =
    adapterDelegateViewBinding<ItemOrderCard.OrderCommentInfo, ItemOrderCard?, ItemOrderCommentInfoBinding>(
        { layoutInflater, root ->
            ItemOrderCommentInfoBinding.inflate(
                layoutInflater,
                root,
                false
            )
        }) {
        binding.commentField.doAfterTextChanged {
            onCommentChangedListener(it.toString())
        }

        bind {
            binding.apply {
                commentField.setText(item.comment)
            }
        }
    }

fun orderAdapterDelegates(
    onNameChangedListener: (String) -> Unit,
    onPhoneChangedListener: (String) -> Unit,
    onStreetChangedListener: (String) -> Unit,
    onBuildingChangedListener: (String) -> Unit,
    onFloorChangedListener: (String) -> Unit,
    onApartmentChangedListener: (String) -> Unit,
    minusFullBottleClickListener: () -> Unit,
    plusFullBottleClickListener: () -> Unit,
    minusEmptyBottleClickListener: () -> Unit,
    plusEmptyBottleClickListener: () -> Unit,
    onDeliveryDayClicked: (DeliveryDay) -> Unit,
    onDeliveryTimeClicked: (DeliveryTime) -> Unit,
    onCommentChangedListener: (String) -> Unit
) = ListDelegationAdapter(
    orderUserInfoAdapterDelegate(
        onNameChangedListener,
        onPhoneChangedListener
    ),
    orderAddressInfoAdapterDelegate(
        onStreetChangedListener,
        onBuildingChangedListener,
        onFloorChangedListener,
        onApartmentChangedListener
    ),
    orderTimeInfoAdapterDelegate(
        onDeliveryDayClicked,
        onDeliveryTimeClicked
    ),
    orderWaterInfoAdapterDelegate(
        minusFullBottleClickListener,
        plusFullBottleClickListener,
        minusEmptyBottleClickListener,
        plusEmptyBottleClickListener
    ),
    orderCommentInfoAdapterDelegate(
        onCommentChangedListener
    )
)

