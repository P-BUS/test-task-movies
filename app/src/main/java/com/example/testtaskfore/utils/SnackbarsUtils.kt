package com.example.testtaskfore.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

object SnackbarsUtils {

    fun showSnackbar(view: View, message: Int, length: Int) {
        Snackbar
            .make(view, message, length)
            .show()
    }
}