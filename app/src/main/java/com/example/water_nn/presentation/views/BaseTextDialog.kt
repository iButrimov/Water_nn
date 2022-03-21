package com.example.water_nn.presentation.views

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.KeyEvent
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.water_nn.R
import com.example.water_nn.getDialogListener

abstract class BaseTextDialog : DialogFragment() {
    companion object {
        const val DEFAULT_DIALOG_ID: Int = 0

        val TITLE_TEXT_KEY = BaseTextDialog::class.java.name + ".title_text.KEY"
        val MESSAGE_TEXT_KEY = BaseTextDialog::class.java.name + ".message_text.KEY"
        val ICON_KEY = BaseTextDialog::class.java.name + ".icon.KEY"
        val DIALOG_ID = BaseTextDialog::class.java.name + ".icon.DIALOG_ID"
    }

    protected open val positiveButtonText: String? = null
    protected open val negativeButtonText: String? = null
    protected open val neutralButtonText: String? = null

    private val messageText: String by lazy { arguments?.getString(MESSAGE_TEXT_KEY) ?: "" }
    private val titleText: String? by lazy {
        arguments?.takeIf { it.containsKey(TITLE_TEXT_KEY) }?.getString(TITLE_TEXT_KEY)
    }
    private val icon: Int? by lazy {
        arguments?.takeIf { it.containsKey(ICON_KEY) }?.getInt(ICON_KEY)
    }
    private val header: TextView by lazy {
        LayoutInflater.from(activity).inflate(R.layout.dialog_header, null, false) as TextView
    }
    private val view: TextView by lazy {
        (LayoutInflater.from(activity).inflate(R.layout.dialog_message, null, false) as TextView)
            .apply { movementMethod = ScrollingMovementMethod() }
    }

    var onPositiveClickListener: (() -> Unit)? = null
    var onNegativeClickListener: (() -> Unit)? = null
    var onNeutralClickListener: (() -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return AlertDialog.Builder(activity as Context)
            .apply {
                if (titleText != null) {
                    setMessage(messageText)
                    header.text = titleText
                    icon?.let { left ->
                        header.setCompoundDrawablesWithIntrinsicBounds(left, 0, 0, 0)
                    }
                    setCustomTitle(header)
                } else {
                    icon?.let { left ->
                        view.setCompoundDrawablesWithIntrinsicBounds(left, 0, 0, 0)
                    }
                    view.text = messageText
                    setView(view)
                }
                positiveButtonText?.let {
                    setPositiveButton(it) { _, _ ->
                        onPositiveClickListener?.invoke()
                    }
                }
                negativeButtonText?.let {
                    setNegativeButton(it) { _, _ ->
                        onNegativeClickListener?.invoke()
                    }
                }
                neutralButtonText?.let {
                    setNeutralButton(it) { _, _ ->
                        onNeutralClickListener?.invoke()
                    }
                }
            }
            .create()
            .apply {
                setCanceledOnTouchOutside(false)
                setOnKeyListener { _, keyCode, _ -> keyCode == KeyEvent.KEYCODE_BACK }
            }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        getDialogListener<OnDismissListener>()?.onDialogDismissed(
            requireArguments().getInt(
                DIALOG_ID,
                DEFAULT_DIALOG_ID
            )
        )
    }

    interface OnDismissListener {
        fun onDialogDismissed(dialogId: Int)
    }
}