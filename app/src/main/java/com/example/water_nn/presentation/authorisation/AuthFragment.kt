package com.example.water_nn.presentation.authorisation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.water_nn.R
import com.example.water_nn.databinding.FragmentAuthBinding
import com.example.water_nn.databinding.UserInfoBinding
import com.example.water_nn.domain.models.AuthData
import com.example.water_nn.domain.models.AuthValidationStatus
import com.example.water_nn.presentation.main.Contract
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthFragment : Fragment(R.layout.fragment_auth) {

    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!
    private lateinit var userInfoBinding: UserInfoBinding

    private val authViewModel: Contract.IAuthViewModel by viewModel<AuthViewModel>()
    private lateinit var authData: AuthData

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        userInfoBinding = UserInfoBinding.bind(binding.root)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()

        binding.registerBtn.setOnClickListener {
            authData = AuthData(
                name = userInfoBinding.nameField.editText?.text.toString(),
                phoneNumber = userInfoBinding.phoneNumberField.editText?.text.toString(),
            )

            authViewModel.createUser(authData)
        }
    }

    private fun observeViewModel() {
        authViewModel.validationAuthStatusList.observe(viewLifecycleOwner) {
            if (it.contains(AuthValidationStatus.NAME_FIELD_IS_EMPTY)) {
                userInfoBinding.nameField.editText?.error =
                    resources.getString(R.string.NAME_FIELD_SHOULD_BE_FILLED_TEXT)
            }
            if (it.contains(AuthValidationStatus.PHONE_NUMBER_FIELD_IS_EMPTY)) {
                userInfoBinding.phoneNumberField.editText?.error =
                    resources.getString(R.string.PHONE_FIELD_SHOULD_BE_FILLED_TEXT)
            }

            if (it.contains(AuthValidationStatus.SUCCESS)) {
                findNavController().navigate(R.id.action_authFragment_to_mainActivity)
                requireActivity().finish()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}