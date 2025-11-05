package com.egsi.labsi.sog

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.webkit.ValueCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import com.egsi.labsi.sog.ioegje.EggLabelGlobalLayoutUtil
import com.egsi.labsi.sog.ioegje.eggLabelSetupSystemBars
import com.egsi.labsi.sog.ioegje.presentation.app.EggLabelApplication
import com.egsi.labsi.sog.ioegje.presentation.pushhandler.EggLabelPushHandler
import org.koin.android.ext.android.inject

class EggLabelActivity : AppCompatActivity() {

    private val eggLabelPushHandler by inject<EggLabelPushHandler>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        eggLabelSetupSystemBars()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContentView(R.layout.activity_egg_label)

        val eggLabelRootView = findViewById<View>(android.R.id.content)
        EggLabelGlobalLayoutUtil().eggLabelAssistActivity(this)
        ViewCompat.setOnApplyWindowInsetsListener(eggLabelRootView) { eggLabelView, eggLabelInsets ->
            val eggLabelSystemBars = eggLabelInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            val eggLabelDisplayCutout = eggLabelInsets.getInsets(WindowInsetsCompat.Type.displayCutout())
            val eggLabelIme = eggLabelInsets.getInsets(WindowInsetsCompat.Type.ime())


            val eggLabelTopPadding = maxOf(eggLabelSystemBars.top, eggLabelDisplayCutout.top)
            val eggLabelLeftPadding = maxOf(eggLabelSystemBars.left, eggLabelDisplayCutout.left)
            val eggLabelRightPadding = maxOf(eggLabelSystemBars.right, eggLabelDisplayCutout.right)
            window.setSoftInputMode(EggLabelApplication.eggLabelInputMode)

            if (window.attributes.softInputMode == WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN) {
                Log.d(EggLabelApplication.EGG_LABEL_MAIN_TAG, "ADJUST PUN")
                val eggLabelBottomInset = maxOf(eggLabelSystemBars.bottom, eggLabelDisplayCutout.bottom)

                eggLabelView.setPadding(eggLabelLeftPadding, eggLabelTopPadding, eggLabelRightPadding, 0)

                eggLabelView.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                    bottomMargin = eggLabelBottomInset
                }
            } else {
                Log.d(EggLabelApplication.EGG_LABEL_MAIN_TAG, "ADJUST RESIZE")

                val eggLabelBottomInset = maxOf(eggLabelSystemBars.bottom, eggLabelDisplayCutout.bottom, eggLabelIme.bottom)

                eggLabelView.setPadding(eggLabelLeftPadding, eggLabelTopPadding, eggLabelRightPadding, 0)

                eggLabelView.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                    bottomMargin = eggLabelBottomInset
                }
            }



            WindowInsetsCompat.CONSUMED
        }
        Log.d(EggLabelApplication.EGG_LABEL_MAIN_TAG, "Activity onCreate()")
        eggLabelPushHandler.eggLabelHandlePush(intent.extras)
    }
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            eggLabelSetupSystemBars()
        }
    }

    override fun onResume() {
        super.onResume()
        eggLabelSetupSystemBars()
    }
}