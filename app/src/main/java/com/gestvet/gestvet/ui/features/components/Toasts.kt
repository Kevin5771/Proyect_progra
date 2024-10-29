package com.gestvet.gestvet.ui.features.components

import android.content.Context
import android.widget.Toast
import com.gestvet.gestvet.R

fun savedToast(context: Context) {
    Toast.makeText(context, R.string.savedChanges, Toast.LENGTH_SHORT).show()
}