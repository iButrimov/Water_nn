package com.example.water_nn.presentation.common.models

import android.content.res.Resources
import com.example.water_nn.R
import com.example.water_nn.domain.common.exception.*

data class ErrorDialogMessage(
    override val title: String?,
    override val description: String
) : DialogMessage {
    companion object {
        fun getDefault(res: Resources) = ErrorDialogMessage(
            title = res.getString(R.string.default_error_title),
            description = res.getString(R.string.exception_unknown_error)
        )
    }
}

fun ServerException.toErrorDialogMessage(res: Resources) = ErrorDialogMessage(
    title = res.getString(R.string.default_error_title),
    description = message ?: res.getString(R.string.exception_unknown_error)
)

fun ConnectionException.toErrorDialogMessage(res: Resources) = ErrorDialogMessage(
    title = res.getString(R.string.default_error_title),
    description = message ?: res.getString(R.string.connection_exception)
)

fun MessageException.toErrorDialogMessage(res: Resources) = ErrorDialogMessage(
    title = res.getString(R.string.default_error_title),
    description = message
)

fun MessageIdException.toErrorDialogMessage(res: Resources) = ErrorDialogMessage(
    title = res.getString(R.string.default_error_title),
    description = res.getString(messageId)
)

fun CodeException.toErrorDialogMessage(res: Resources) = ErrorDialogMessage(
    title = res.getString(R.string.default_error_title),
    description = res.getString(R.string.code_error, code)
)