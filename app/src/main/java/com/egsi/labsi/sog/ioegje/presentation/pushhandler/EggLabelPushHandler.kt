package com.egsi.labsi.sog.ioegje.presentation.pushhandler

import android.os.Bundle
import android.util.Log
import com.egsi.labsi.sog.ioegje.presentation.app.EggLabelApplication

class EggLabelPushHandler {
    fun eggLabelHandlePush(extras: Bundle?) {
        Log.d(EggLabelApplication.EGG_LABEL_MAIN_TAG, "Extras from Push = ${extras?.keySet()}")
        if (extras != null) {
            val map = eggLabelBundleToMap(extras)
            Log.d(EggLabelApplication.EGG_LABEL_MAIN_TAG, "Map from Push = $map")
            map?.let {
                if (map.containsKey("url")) {
                    EggLabelApplication.EGG_LABEL_FB_LI = map["url"]
                    Log.d(EggLabelApplication.EGG_LABEL_MAIN_TAG, "UrlFromActivity = $map")
                }
            }
        } else {
            Log.d(EggLabelApplication.EGG_LABEL_MAIN_TAG, "Push data no!")
        }
    }

    private fun eggLabelBundleToMap(extras: Bundle): Map<String, String?>? {
        val map: MutableMap<String, String?> = HashMap()
        val ks = extras.keySet()
        val iterator: Iterator<String> = ks.iterator()
        while (iterator.hasNext()) {
            val key = iterator.next()
            map[key] = extras.getString(key)
        }
        return map
    }

}