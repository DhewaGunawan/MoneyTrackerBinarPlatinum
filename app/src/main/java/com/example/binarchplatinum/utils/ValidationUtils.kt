package com.example.binarchplatinum.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.lang.ref.WeakReference
import java.text.DecimalFormat
import java.text.NumberFormat

fun TextInputEditText.isNotNullOrEmpty(errorString: String): Boolean {
    val textInputLayout = this.parent.parent as TextInputLayout
    textInputLayout.errorIconDrawable = null
    this.onChange { textInputLayout.error = null }
    return if (this.text.toString().trim().isEmpty()) {
        textInputLayout.error = errorString
        false
    } else {
        true
    }
}

fun EditText.isNotNullOrEmpty(errorString: String): Boolean {
    val textInputLayout = this.parent.parent as TextInputLayout
    textInputLayout.errorIconDrawable = null
    this.onChange { textInputLayout.error = null }
    return if (this.text.toString().trim().isBlank()) {
        textInputLayout.error = errorString
        false
    } else {
        true
    }
}

fun EditText.onChange(cb: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            cb(s.toString())
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}

fun EditText.addCurrencyFormatter(currencyText: String) {
    this.addTextChangedListener(object: MyTextWatcher{
        val editTextWeakReference: WeakReference<EditText> = WeakReference<EditText>(this@addCurrencyFormatter)
        override fun afterTextChanged(editable: Editable?) {
            val editText = editTextWeakReference.get() ?: return
            val s = editable.toString()
            editText.removeTextChangedListener(this)
            val cleanString = s.replace("[Rp,. ]".toRegex(), "")
            val newCurrent = currencyText + cleanString.monetize()
            editText.setText(newCurrent)
            editText.setSelection(newCurrent.length)
            editText.addTextChangedListener(this)
        }
    })
}

fun String.monetize(): String = if (this.isEmpty()) "0" else DecimalFormat("#,###").format(this.replace("[^\\d]".toRegex(),"").toLong())

interface MyTextWatcher: TextWatcher {
    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
}


