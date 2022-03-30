package com.example.water_nn.presentation.main.profile

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.water_nn.R
import com.example.water_nn.databinding.FragmentProfileBinding
import com.example.water_nn.domain.models.UserInformation
import com.example.water_nn.presentation.BaseFragment
import com.example.water_nn.presentation.OneShot
import com.example.water_nn.presentation.views.afterTextChanged
import com.example.water_nn.presentation.views.hideSoftKeyboard
import com.example.water_nn.presentation.views.setVisibility
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.water_nn.presentation.main.profile.ProfileEvent as Event
import com.example.water_nn.presentation.main.profile.ProfileState as State
import com.example.water_nn.presentation.main.profile.ProfileViewModel as ViewModel

class ProfileFragment : BaseFragment<ViewModel, State, Event>(R.layout.fragment_profile) {

    override val viewModel: ViewModel by viewModel()
    override val binding: FragmentProfileBinding by viewBinding(FragmentProfileBinding::bind)

    private var lastSavedState: State? = null
    private var currentState: State = State()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        binding.name.afterTextChanged {
            sendEvent(Event.NameChanged(it))
        }

        binding.email.afterTextChanged {
            sendEvent(Event.EmailChanged(it))
        }

        binding.phone.afterTextChanged {
            sendEvent(Event.PhoneChanged(it))
        }

        sendEvent(Event.ProfileInfoRequest)
    }

    override fun renderView(state: State) {
        super.renderView(state)

        invalidateOption()

        currentState = state
        binding.loading.fullScreenLoading.setVisibility(state.isLoading)
    }

    override fun renderOneShot(oneShot: OneShot) {
        when (oneShot) {
            is ProfileOneShot.SetUserInfo -> {
                with(oneShot.userInformation) {
                    setUserInfo(this)
                    lastSavedState = State(name, email, phone)
                    currentState = State(name, email, phone)
                }
            }
            else -> super.renderOneShot(oneShot)
        }
    }

    private fun setUserInfo(userInfo: UserInformation) {
        with(binding) {
            name.setText(userInfo.name)
            email.setText(userInfo.email)
            phone.setText(userInfo.phone)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.profile_fragment_menu, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val menuSaveItem = menu.findItem(R.id.saveChanges)

        if (lastSavedState != currentState && currentState.isDataValid) {
            menuSaveItem.setIcon(R.drawable.ic_done_enabled).also {
                it.isEnabled = true
            }
        } else {
            menuSaveItem.setIcon(R.drawable.ic_done_disabled).also {
                it.isEnabled = false
            }
        }
        super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.saveChanges -> saveChanges()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveChanges() {
        requireView().hideSoftKeyboard()

        val userInformation = UserInformation(
            name = binding.name.text.toString(),
            email = binding.email.text.toString(),
            phone = binding.phone.text.toString()
        )

        sendEvent(Event.SaveUserInformation(userInformation))

        lastSavedState = State(
            userInformation.name,
            userInformation.email,
            userInformation.phone
        )

        invalidateOption()

        showMessage()
    }

    private fun showMessage() {
        Toast.makeText(requireContext(), R.string.DATA_SAVED_TOAST_MESSAGE, Toast.LENGTH_SHORT)
            .show()
    }

    private fun invalidateOption() {
        activity?.invalidateOptionsMenu()
    }
}