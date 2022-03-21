package com.example.water_nn.presentation.views

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.annotation.DrawableRes
import com.example.water_nn.R
import com.example.water_nn.presentation.common.models.DialogMessage


@SuppressLint("InflateParams")
class ErrorDialog : BaseTextDialog() {
    companion object {
        val TAG: String = ErrorDialog::class.java.name + ".dialog.TAG"

        fun createDialog(errorDialogMessage: DialogMessage): BaseTextDialog = createDialog(
            titleText = errorDialogMessage.title,
            messageText = errorDialogMessage.description
        )

        fun createDialog(
            messageText: String,
            titleText: String? = null,
            @DrawableRes iconRes: Int? = null,
            dialogId: Int? = DEFAULT_DIALOG_ID
        ): ErrorDialog = createDialog(
            titleText = titleText,
            messageText = messageText,
            iconRes = iconRes,
            dialogId = dialogId ?: DEFAULT_DIALOG_ID
        )

        private fun createDialog(
            messageText: String,
            titleText: String? = null,
            @DrawableRes iconRes: Int? = null,
            dialogId: Int
        ): ErrorDialog = ErrorDialog().apply {
            arguments = Bundle().apply {
                putString(MESSAGE_TEXT_KEY, messageText)
                titleText?.let { putString(TITLE_TEXT_KEY, it) }
                iconRes?.let { putInt(ICON_KEY, it) }
                putInt(DIALOG_ID, dialogId)
            }
        }
    }

    override val positiveButtonText: String by lazy { getString(R.string.dialog_btn_ok) }
}