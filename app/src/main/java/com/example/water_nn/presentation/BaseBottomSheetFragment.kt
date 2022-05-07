package com.example.water_nn.presentation

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.example.water_nn.R
import com.example.water_nn.domain.common.exception.AuthException
import com.example.water_nn.domain.common.exception.ConnectionException
import com.example.water_nn.domain.common.exception.MessageException
import com.example.water_nn.domain.common.exception.ServerException
import com.example.water_nn.getExceptionMessages
import com.example.water_nn.presentation.authorisation.AuthActivity
import com.example.water_nn.presentation.common.models.ErrorDialogMessage
import com.example.water_nn.presentation.views.ErrorDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class BaseBottomSheetFragment<TViewModel, TState, TEvent> :
    BottomSheetDialogFragment where TViewModel : BaseViewModel<TState, TEvent> {

    constructor() : super()

    protected abstract val viewModel: TViewModel

    protected abstract val binding: ViewBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        subscribeOnViewChanges()
    }

    protected fun subscribeOnViewChanges() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { renderView(it) }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.oneShotFlow.collect { renderOneShot(it) }
            }
        }
    }

    protected open fun sendEvent(event: TEvent) {
        viewModel.sendEvent(event)
    }

    @CallSuper
    protected open fun renderView(state: TState) {
        Timber.d("[Render new State]: $state")
    }

    @CallSuper
    protected open fun renderOneShot(oneShot: OneShot) {
        Timber.d("[One Shot]: $oneShot")
        when (oneShot) {
            is OneShotError -> handleError(oneShot)
        }
    }

    protected open fun handleError(error: OneShotError) {
        Timber.d("[One Shot]: handleError ${error.exception}")
        when (error.exception) {
            is AuthException -> startRelogin()
            is ConnectionException -> showErrorOneLine(error.exception)
            is ServerException -> showErrorDialog(error.exception)
            is MessageException -> showErrorDialog(error.exception).also {
                Timber.e(error.exception.cause)
            }
            else -> showErrorOneLine(error.exception).also {
                Timber.e(error.exception)
            }
        }
    }

    protected open fun showErrorDialog(exception: Exception) {
        val message: ErrorDialogMessage = getExceptionMessages(exception)
        ErrorDialog.createDialog(message).show(childFragmentManager, ErrorDialog.TAG)
    }

    protected open fun showErrorOneLine(exception: Exception) {
        if (view == null) return
        val message = getExceptionMessages(exception)
        showErrorOneLine(message.description)
    }

    protected open fun showErrorOneLine(text: String) {
        Snackbar
            .make(requireView(), "", Snackbar.LENGTH_LONG)
            .setText(text)
            .show()
    }

    override fun getTheme(): Int {
        return R.style.BodyMaterialAlertDialog
    }

    private fun startRelogin() {
        startActivity(AuthActivity.createStartIntent(requireContext()))
    }
}