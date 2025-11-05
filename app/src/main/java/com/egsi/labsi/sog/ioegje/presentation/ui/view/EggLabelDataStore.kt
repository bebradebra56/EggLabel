package com.egsi.labsi.sog.ioegje.presentation.ui.view

import android.annotation.SuppressLint
import android.widget.FrameLayout
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class EggLabelDataStore : ViewModel(){
    val eggLabelViList: MutableList<EggLabelVi> = mutableListOf()
    var eggLabelIsFirstCreate = true
    @SuppressLint("StaticFieldLeak")
    lateinit var eggLabelContainerView: FrameLayout
    @SuppressLint("StaticFieldLeak")
    lateinit var eggLabelView: EggLabelVi

}