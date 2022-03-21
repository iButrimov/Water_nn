package com.example.water_nn.presentation.views

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText

fun View.setVisibility(isVisible: Boolean, falseVisibility: Int = View.GONE) =
    if (isVisible) this.visibility = View.VISIBLE else
        this.visibility = falseVisibility

fun View.onClick(callback: () -> Unit) {
    setOnClickListener { callback() }
}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit): TextWatcher {
    val textWatcher = object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    }
    this.addTextChangedListener(textWatcher)
    return textWatcher
}