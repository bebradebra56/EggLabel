package com.egsi.labsi.sog.ioegje.presentation.ui.load

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.egsi.labsi.sog.ioegje.data.shar.EggLabelSharedPreference
import com.egsi.labsi.sog.ioegje.data.utils.EggLabelSystemService
import com.egsi.labsi.sog.ioegje.domain.usecases.EggLabelGetAllUseCase
import com.egsi.labsi.sog.ioegje.presentation.app.EggLabelAppsFlyerState
import com.egsi.labsi.sog.ioegje.presentation.app.EggLabelApplication
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EggLabelLoadViewModel(
    private val eggLabelGetAllUseCase: EggLabelGetAllUseCase,
    private val eggLabelSharedPreference: EggLabelSharedPreference,
    private val eggLabelSystemService: EggLabelSystemService
) : ViewModel() {

    private val _eggLabelHomeScreenState: MutableStateFlow<EggLabelHomeScreenState> =
        MutableStateFlow(EggLabelHomeScreenState.EggLabelLoading)
    val eggLabelHomeScreenState = _eggLabelHomeScreenState.asStateFlow()

    private var eggLabelGetApps = false


    init {
        viewModelScope.launch {
            when (eggLabelSharedPreference.eggLabelAppState) {
                0 -> {
                    if (eggLabelSystemService.eggLabelIsOnline()) {
                        EggLabelApplication.eggLabelConversionFlow.collect {
                            when(it) {
                                EggLabelAppsFlyerState.EggLabelDefault -> {}
                                EggLabelAppsFlyerState.EggLabelError -> {
                                    eggLabelSharedPreference.eggLabelAppState = 2
                                    _eggLabelHomeScreenState.value =
                                        EggLabelHomeScreenState.EggLabelError
                                    eggLabelGetApps = true
                                }
                                is EggLabelAppsFlyerState.EggLabelSuccess -> {
                                    if (!eggLabelGetApps) {
                                        eggLabelGetData(it.eggLabelData)
                                        eggLabelGetApps = true
                                    }
                                }
                            }
                        }
                    } else {
                        _eggLabelHomeScreenState.value =
                            EggLabelHomeScreenState.EggLabelNotInternet
                    }
                }
                1 -> {
                    if (eggLabelSystemService.eggLabelIsOnline()) {
                        if (EggLabelApplication.EGG_LABEL_FB_LI != null) {
                            _eggLabelHomeScreenState.value =
                                EggLabelHomeScreenState.EggLabelSuccess(
                                    EggLabelApplication.EGG_LABEL_FB_LI.toString()
                                )
                        } else if (System.currentTimeMillis() / 1000 > eggLabelSharedPreference.eggLabelExpired) {
                            Log.d(EggLabelApplication.EGG_LABEL_MAIN_TAG, "Current time more then expired, repeat request")
                            EggLabelApplication.eggLabelConversionFlow.collect {
                                when(it) {
                                    EggLabelAppsFlyerState.EggLabelDefault -> {}
                                    EggLabelAppsFlyerState.EggLabelError -> {
                                        _eggLabelHomeScreenState.value =
                                            EggLabelHomeScreenState.EggLabelSuccess(
                                                eggLabelSharedPreference.eggLabelSavedUrl
                                            )
                                        eggLabelGetApps = true
                                    }
                                    is EggLabelAppsFlyerState.EggLabelSuccess -> {
                                        if (!eggLabelGetApps) {
                                            eggLabelGetData(it.eggLabelData)
                                            eggLabelGetApps = true
                                        }
                                    }
                                }
                            }
                        } else {
                            Log.d(EggLabelApplication.EGG_LABEL_MAIN_TAG, "Current time less then expired, use saved url")
                            _eggLabelHomeScreenState.value =
                                EggLabelHomeScreenState.EggLabelSuccess(
                                    eggLabelSharedPreference.eggLabelSavedUrl
                                )
                        }
                    } else {
                        _eggLabelHomeScreenState.value =
                            EggLabelHomeScreenState.EggLabelNotInternet
                    }
                }
                2 -> {
                    _eggLabelHomeScreenState.value =
                        EggLabelHomeScreenState.EggLabelError
                }
            }
        }
    }


    private suspend fun eggLabelGetData(conversation: MutableMap<String, Any>?) {
        val eggLabelData = eggLabelGetAllUseCase.invoke(conversation)
        if (eggLabelSharedPreference.eggLabelAppState == 0) {
            if (eggLabelData == null) {
                eggLabelSharedPreference.eggLabelAppState = 2
                _eggLabelHomeScreenState.value =
                    EggLabelHomeScreenState.EggLabelError
            } else {
                eggLabelSharedPreference.eggLabelAppState = 1
                eggLabelSharedPreference.apply {
                    eggLabelExpired = eggLabelData.eggLabelExpires
                    eggLabelSavedUrl = eggLabelData.eggLabelUrl
                }
                _eggLabelHomeScreenState.value =
                    EggLabelHomeScreenState.EggLabelSuccess(eggLabelData.eggLabelUrl)
            }
        } else  {
            if (eggLabelData == null) {
                _eggLabelHomeScreenState.value =
                    EggLabelHomeScreenState.EggLabelSuccess(eggLabelSharedPreference.eggLabelSavedUrl)
            } else {
                eggLabelSharedPreference.apply {
                    eggLabelExpired = eggLabelData.eggLabelExpires
                    eggLabelSavedUrl = eggLabelData.eggLabelUrl
                }
                _eggLabelHomeScreenState.value =
                    EggLabelHomeScreenState.EggLabelSuccess(eggLabelData.eggLabelUrl)
            }
        }
    }


    sealed class EggLabelHomeScreenState {
        data object EggLabelLoading : EggLabelHomeScreenState()
        data object EggLabelError : EggLabelHomeScreenState()
        data class EggLabelSuccess(val data: String) : EggLabelHomeScreenState()
        data object EggLabelNotInternet: EggLabelHomeScreenState()
    }
}