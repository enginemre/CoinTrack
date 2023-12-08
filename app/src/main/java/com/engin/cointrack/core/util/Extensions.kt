package com.engin.cointrack.core.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

fun EditText.hideKeyboard(): Boolean {
    return (context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
        .hideSoftInputFromWindow(windowToken, 0)
}

fun Context.handleLoading(isLoading: Boolean) {
    if (isLoading)
        ProgressDialog.show(this)
    else
        ProgressDialog.dismiss()
}

fun showSnackBar(rootView:View,message: String,actionText : String? = null,action : (() -> Unit)? =null) {
    Snackbar.make(rootView, message, Snackbar.LENGTH_LONG)
        .setAction(actionText) {
            action?.invoke()
        }
        .setDuration(Snackbar.LENGTH_LONG)
        .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_FADE)
        .setActionTextColor(
            ContextCompat.getColor(
                rootView.context,
                android.R.color.holo_red_dark
            )
        )
        .show()
}
