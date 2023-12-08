package com.engin.cointrack.core.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import android.widget.ProgressBar

object ProgressDialog {

    private var dialog: Dialog? = null
    fun show(context: Context) {
        try {
            if (dialog != null) return
            dialog = Dialog(context)
            dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog?.setContentView(ProgressBar(context))
           /* dialog?.window?.setLayout(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))*/
            dialog?.setCancelable(false)
            dialog?.show()
        }catch (e : Exception){ }
    }

    fun dismiss() {
        try {
            dialog?.dismiss()
            dialog = null
        }catch (e:Exception){}

    }
}