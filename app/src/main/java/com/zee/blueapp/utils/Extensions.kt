package com.zee.blueapp.utils

import android.content.Context
import android.widget.Toast
import com.zee.blueapp.ui.home.HomeViewModel

fun HomeViewModel.HomeEvent.ApiFailure.getErrorMsg(context: Context): String {
    return if (this.res != null) context.getString(res) else this.failureReason
}

fun Context.showToast(msg: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, msg, length).show()
}