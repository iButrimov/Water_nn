package com.example.water_nn.presentation.main.mainscreen.address

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.water_nn.R
import com.example.water_nn.databinding.FragmentAddressDeliveryBottomSheetBinding
import com.example.water_nn.presentation.BaseBottomSheetFragment
import com.example.water_nn.presentation.OneShot
import com.example.water_nn.presentation.views.onClick
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.water_nn.presentation.main.mainscreen.address.AddressState as State
import com.example.water_nn.presentation.main.mainscreen.address.AddressViewModel as ViewModel
import com.example.water_nn.presentation.main.mainscreen.address.AddressEvent as Event

class AddressBottomSheetFragment : BaseBottomSheetFragment<ViewModel, State, Event>() {

    override val viewModel: ViewModel by viewModel()
    override val binding: FragmentAddressDeliveryBottomSheetBinding by viewBinding(
        FragmentAddressDeliveryBottomSheetBinding::bind
    )

    private val adapter = AddressDeliveryAdapter {
        sendEvent(Event.AddressSelected(it))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.setOnShowListener {
            (it as BottomSheetDialog).behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        return inflater.inflate(R.layout.fragment_address_delivery_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rv.adapter = adapter
        sendEvent(Event.AddressListRequest)

        binding.addNewAddressButton.onClick(::addNewAddress)
        binding.confirmAddressButton.onClick(::confirmSelectedAddress)
    }

    override fun renderOneShot(oneShot: OneShot) {
        when (oneShot) {
            is AddressOneShot.CreateNewAddress -> navigateToMap()
            else -> super.renderOneShot(oneShot)
        }
    }

    override fun renderView(state: State) {
        super.renderView(state)

        adapter.setListData(state.addressList)
    }

    override fun getTheme(): Int {
        return R.style.BottomSheetDialogTheme
    }

    private fun addNewAddress() {
        val streetName = binding.addNewEditText.text.toString()
        sendEvent(Event.AddNewAddress(streetName))
        binding.addNewEditText.text.clear()
    }

    private fun confirmSelectedAddress() {
        sendEvent(Event.ConfirmSelectedAddress)
        dismiss()
    }

    private fun navigateToMap() {
        //Navigate to map
        dismiss()
    }
}