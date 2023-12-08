package com.engin.cointrack.core.ui.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.engin.cointrack.databinding.LayoutEmptyScreenBinding

class EmptyView : ConstraintLayout {

    private val binding = LayoutEmptyScreenBinding.inflate(
        LayoutInflater.from(context),
        this
    )

    //region constructors
    constructor(context: Context) : super(context)

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(
        context,
        attrs
    )

    constructor(
        context: Context,
        attrs: AttributeSet?, defStyleAttr: Int
    ) : super(
        context,
        attrs,
        defStyleAttr
    )
    //endregion

    fun setText(message : String){
        binding.textView.text = message
    }

    fun hideLottie(){
        binding.animationView.visibility = View.GONE
    }


}