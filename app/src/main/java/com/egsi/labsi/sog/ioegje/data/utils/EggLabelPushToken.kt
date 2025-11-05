package com.egsi.labsi.sog.ioegje.data.utils

import android.util.Log
import com.egsi.labsi.sog.ioegje.presentation.app.EggLabelApplication
import com.google.firebase.messaging.FirebaseMessaging
import java.lang.Exception
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class EggLabelPushToken {

    suspend fun eggLabelGetToken(): String = suspendCoroutine { continuation ->
        try {
            FirebaseMessaging.getInstance().token.addOnCompleteListener {
                if (!it.isSuccessful) {
                    continuation.resume(it.result)
                    Log.d(EggLabelApplication.EGG_LABEL_MAIN_TAG, "Token error: ${it.exception}")
                } else {
                    continuation.resume(it.result)
                }
            }
        } catch (e: Exception) {
            Log.d(EggLabelApplication.EGG_LABEL_MAIN_TAG, "FirebaseMessagingPushToken = null")
            continuation.resume("")
        }
    }


}