package com.example.water_nn.presentation.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.water_nn.R
import com.example.water_nn.databinding.FragmentSplashBinding
import com.example.water_nn.presentation.main.Contract
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : Fragment(R.layout.fragment_splash) {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!
    private lateinit var splashAnim: Animation
    private lateinit var logoImage: ImageView
    private lateinit var upperElementImage: ImageView
    private lateinit var lowerElementImage: ImageView

    private val splashViewModel: Contract.ISplashViewModel by viewModel<SplashViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)

        logoImage = binding.imageLogo
        upperElementImage = binding.imageUpperElement
        lowerElementImage = binding.imageLowerElement
        splashAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.splash_anim)

        logoImage.startAnimation(splashAnim)
        upperElementImage.startAnimation(splashAnim)
        lowerElementImage.startAnimation(splashAnim)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {

            splashViewModel.checkUserCreated()

            delay(1000)
            binding.progress.isVisible = true
            delay(2000)
            binding.progress.isVisible = false

            observeIsUserCreated()
        }
    }

    private fun observeIsUserCreated() {
        splashViewModel.isUserCreated.observe(viewLifecycleOwner) {
            if (it == true) {
                findNavController().navigate(R.id.action_splashFragment_to_mainActivity)
                activity?.finish()
            } else {
                findNavController().navigate(R.id.action_splashFragment_to_authActivity)
                activity?.finish()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}