package com.example.water_nn.presentation.main.mainscreen

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.water_nn.R
import com.example.water_nn.databinding.FragmentMainScreenBinding
import com.example.water_nn.presentation.BaseFragment
import com.example.water_nn.presentation.main.ToolbarElementControl
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.water_nn.presentation.main.mainscreen.MainScreenEvent as Event
import com.example.water_nn.presentation.main.mainscreen.MainScreenState as State
import com.example.water_nn.presentation.main.mainscreen.MainScreenViewModel as ViewModel

class MainScreenFragment : BaseFragment<ViewModel, State, Event>(R.layout.fragment_main_screen) {

    override val viewModel: ViewModel by viewModel()
    override val binding: FragmentMainScreenBinding by viewBinding(FragmentMainScreenBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

    }

    override fun onResume() {
        super.onResume()
        (activity as ToolbarElementControl).setAddressDeliveryView()
    }

    override fun onStop() {
        super.onStop()
        (activity as ToolbarElementControl).removeAddressDeliveryView()
    }
}