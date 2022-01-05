package com.example.water_nn.presentation.main.history

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.water_nn.R
import com.example.water_nn.databinding.FragmentGratitudeScreenBinding
import kotlinx.android.synthetic.main.activity_main.*

class GratitudeScreenFragment : Fragment(R.layout.fragment_gratitude_screen) {

    private val binding by viewBinding(FragmentGratitudeScreenBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().bottom_navigation.visibility = GONE

        binding.continueBtn.setOnClickListener {
            findNavController().popBackStack(R.id.newOrderFragment, true)
            requireActivity().bottom_navigation.visibility = VISIBLE
        }
    }
}