package com.example.water_nn.presentation.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.water_nn.R
import com.example.water_nn.databinding.FragmentSplashBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment(R.layout.fragment_splash) {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            delay(1000)
            binding.progress.isVisible = true
            delay(2000)
            binding.progress.isVisible = false

//            if (user.isEmpty){
//                findNavController().navigate(R.id.action_splashFragment_to_authActivity)
//                activity?.finish()
//            } else {
//                findNavController().navigate(R.id.action_splashFragment_to_mainActivity)
//                activity?.finish()
//            }

            findNavController().navigate(R.id.action_splashFragment_to_authActivity)
        }
    }
}