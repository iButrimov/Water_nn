package com.example.water_nn.presentation.main.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.example.water_nn.R
import com.example.water_nn.databinding.DeliveryInfoBinding
import com.example.water_nn.databinding.FragmentProfileBinding
import com.example.water_nn.databinding.UserInfoBinding
import com.example.water_nn.domain.models.UserInformation
import com.example.water_nn.presentation.main.Contract
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var userInfoBinding: UserInfoBinding
    private lateinit var deliveryInfoBinding: DeliveryInfoBinding

    private val profileViewModel: Contract.IProfileViewModel by viewModel<ProfileViewModel>()
    private lateinit var userInformation: UserInformation

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        userInfoBinding = UserInfoBinding.bind(binding.root)
        deliveryInfoBinding = DeliveryInfoBinding.bind(binding.root)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileViewModel.getUserInfo()

        profileViewModel.userInfoLiveData.observe(viewLifecycleOwner) {
            userInfoBinding.nameField.editText?.setText(it.name)
            userInfoBinding.phoneNumberField.editText?.setText(it.phoneNumber)
            deliveryInfoBinding.addressDeliveryField.editText?.setText(it.address)
            deliveryInfoBinding.buildingNumber.editText?.setText(it.buildingNumber)
            deliveryInfoBinding.floorNumber.editText?.setText(it.floorNumber)
            deliveryInfoBinding.apartmentNumber.editText?.setText(it.apartmentNumber)
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

        deliveryInfoBinding.addressDeliveryField.editText?.doAfterTextChanged {
            profileViewModel.address = it.toString()
        }

        deliveryInfoBinding.buildingNumber.editText?.doAfterTextChanged {
            profileViewModel.buildingNumber = it.toString()
        }

        deliveryInfoBinding.floorNumber.editText?.doAfterTextChanged {
            profileViewModel.floorNumber = it.toString()
        }

        deliveryInfoBinding.apartmentNumber.editText?.doAfterTextChanged {
            profileViewModel.apartmentNumber = it.toString()
        }

        binding.saveBtn.setOnClickListener {
            userInformation = UserInformation(
                name = userInfoBinding.nameField.editText?.text.toString(),
                phoneNumber = userInfoBinding.phoneNumberField.editText?.text.toString(),
                address = deliveryInfoBinding.addressDeliveryField.editText?.text.toString(),
                buildingNumber = deliveryInfoBinding.buildingNumber.editText?.text.toString(),
                floorNumber = deliveryInfoBinding.floorNumber.editText?.text.toString(),
                apartmentNumber = deliveryInfoBinding.apartmentNumber.editText?.text.toString()
            )

            profileViewModel.saveUserInformation(userInformation)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}