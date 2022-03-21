package com.example.water_nn

import android.content.Context
import androidx.fragment.app.Fragment
import com.example.water_nn.domain.common.exception.*
import com.example.water_nn.presentation.common.models.ErrorDialogMessage
import com.example.water_nn.presentation.common.models.toErrorDialogMessage

fun Context.getExceptionMessages(exception: Exception): ErrorDialogMessage = when (exception) {
    is ServerException -> exception.toErrorDialogMessage(resources)
    is ConnectionException -> exception.toErrorDialogMessage(resources)
    // Add custom exception above this line
    is MessageException -> exception.toErrorDialogMessage(resources)
    is MessageIdException -> exception.toErrorDialogMessage(resources)
    is CodeException -> exception.toErrorDialogMessage(resources)
    else -> ErrorDialogMessage.getDefault(resources)
}

fun Fragment.getExceptionMessages(exception: Exception): ErrorDialogMessage =
    requireContext().getExceptionMessages(exception)

inline fun <reified T> Fragment.getDialogListener(): T? {
    return parentFragment as? T ?: activity as? T
}