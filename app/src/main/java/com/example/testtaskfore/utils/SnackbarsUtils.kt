package com.example.testtaskfore.utils

import android.view.View
import com.example.testtaskfore.R
import com.google.android.material.snackbar.Snackbar

object SnackbarsUtils {

    fun showActionSnackbar(view: View, message: Int, length: Int, action: () -> Unit) {
        Snackbar
            .make(view, message, length)
            .setAction(R.string.action_text) {
                action()
            }
            .show()
    }

    fun showSnackbar(view: View, message: Int, length: Int) {
        Snackbar
            .make(view, message, length)
            .show()
    }
}