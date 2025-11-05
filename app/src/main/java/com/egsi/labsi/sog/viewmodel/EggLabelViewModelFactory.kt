package com.egsi.labsi.sog.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class EggLabelViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EggLabelViewModel::class.java)) {
            return EggLabelViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
