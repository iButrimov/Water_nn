package com.example.water_nn.presentation.authorisation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.water_nn.R
import com.example.water_nn.databinding.FragmentAuthBinding
import com.example.water_nn.domain.models.AuthData
import com.example.water_nn.domain.models.AuthValidationStatus
import com.example.water_nn.presentation.main.Contract
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthFragment : Fragment(R.layout.fragment_auth) {

    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!

    private val authViewModel: Contract.IAuthViewModel by viewModel<AuthViewModel>()
    private lateinit var authData: AuthData

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()

        binding.registerBtn.setOnClickListener {
            authData = AuthData(
                name = binding.nameField.editText?.text.toString(),
                address = binding.addressField.editText?.text.toString(),
                phoneNumber = binding.phoneNumberField.editText?.text.toString()
            )

            authViewModel.createUser(authData)
        }
    }

    private fun observeViewModel() {
        authViewModel.validationAuthStatusList.observe(viewLifecycleOwner) {
            if (it.contains(AuthValidationStatus.NAME_FIELD_IS_EMPTY)) {
                binding.nameField.editText?.error =
                    "Поле с именем должно быть заполнено"               //вынести в ресурсы
            }
            if (it.contains(AuthValidationStatus.ADDRESS_FIELD_IS_EMPTY)) {
                binding.addressField.editText?.error =
                    "Поле с адресом должно быть заполнено"   //вынести в ресурсы
            }
            if (it.contains(AuthValidationStatus.PHONE_NUMBER_FIELD_IS_EMPTY)) {
                binding.phoneNumberField.editText?.error =
                    "Поле с телефоном должно быть заполнено"   //вынести в ресурсы
            }

            if (it.contains(AuthValidationStatus.SUCCESS)) {
                findNavController().navigate(R.id.action_authFragment_to_mainActivity)
                requireActivity().finish()
                Toast.makeText(context, "Данные пользователя сохранены", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}