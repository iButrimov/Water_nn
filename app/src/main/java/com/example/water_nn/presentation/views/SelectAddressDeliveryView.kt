package com.example.water_nn.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.water_nn.R
import com.example.water_nn.databinding.ViewSelectAddressDeliveryBinding

typealias OnAddressDeliverySelectedListener = () -> Unit

class SelectAddressDeliveryView(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val binding: ViewSelectAddressDeliveryBinding

    private var listener: OnAddressDeliverySelectedListener? = null

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.view_select_address_delivery, this, true)
        binding = ViewSelectAddressDeliveryBinding.bind(this)
        initListener()
    }

    fun setAddressDeliveryText(address: String?) {
        binding.address.text = address ?: "Адрес не указан"
    }

    private fun initListener() {
        binding.root.setOnClickListener {
            this.listener?.invoke()
        }
    }

    fun setListener(listener: OnAddressDeliverySelectedListener?) {
        this.listener = listener
    }
}