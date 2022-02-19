package com.example.water_nn.presentation.main.profile

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.water_nn.R
import com.example.water_nn.databinding.DeliveryInfoBinding
import com.example.water_nn.databinding.FragmentProfileBinding
import com.example.water_nn.databinding.UserInfoBinding
import com.example.water_nn.domain.models.UserInformation
import com.example.water_nn.presentation.main.Contract
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val binding by viewBinding(FragmentProfileBinding::bind)
    private val userInfoBinding by viewBinding(UserInfoBinding::bind)
    private val deliveryInfoBinding by viewBinding(DeliveryInfoBinding::bind)

    private val profileViewModel: Contract.IProfileViewModel by viewModel<ProfileViewModel>()
    private lateinit var userInformation: UserInformation

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileViewModel.getUserInfo()

        profileViewModel.userInfoLiveData.observe(viewLifecycleOwner) {
            userInfoBinding.nameField.editText?.setText(it.name)
            userInfoBinding.phoneNumberField.editText?.setText(it.phoneNumber)
            deliveryInfoBinding.streetField.editText?.setText(it.address)
            deliveryInfoBinding.buildingNumberField.editText?.setText(it.buildingNumber)
            deliveryInfoBinding.floorNumberField.editText?.setText(it.floorNumber)
            deliveryInfoBinding.apartmentNumberField.editText?.setText(it.apartmentNumber)
        }

        profileViewModel.btnIsActive.observe(viewLifecycleOwner) {
            binding.saveBtn.isEnabled = it
        }

        userInfoBinding.nameField.editText?.doAfterTextChanged {
            profileViewModel.name = it.toString()
        }

        userInfoBinding.phoneNumberField.editText?.doAfterTextChanged {
            profileViewModel.phoneNumber = it.toString()
        }

        deliveryInfoBinding.streetField.editText?.doAfterTextChanged {
            profileViewModel.address = it.toString()
        }

        deliveryInfoBinding.buildingNumberField.editText?.doAfterTextChanged {
            profileViewModel.buildingNumber = it.toString()
        }

        deliveryInfoBinding.floorNumberField.editText?.doAfterTextChanged {
            profileViewModel.floorNumber = it.toString()
        }

        deliveryInfoBinding.apartmentNumberField.editText?.doAfterTextChanged {
            profileViewModel.apartmentNumber = it.toString()
        }

        binding.saveBtn.setOnClickListener {
            userInformation = UserInformation(
                name = userInfoBinding.nameField.editText?.text.toString(),
                phoneNumber = userInfoBinding.phoneNumberField.editText?.text.toString(),
                address = deliveryInfoBinding.streetField.editText?.text.toString(),
                buildingNumber = deliveryInfoBinding.buildingNumberField.editText?.text.toString(),
                floorNumber = deliveryInfoBinding.floorNumberField.editText?.text.toString(),
                apartmentNumber = deliveryInfoBinding.apartmentNumberField.editText?.text.toString()
            )

            profileViewModel.saveUserInformation(userInformation)
        }
    }
}