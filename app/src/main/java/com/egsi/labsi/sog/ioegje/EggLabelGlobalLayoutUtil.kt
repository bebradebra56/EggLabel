package com.egsi.labsi.sog.ioegje

import android.app.Activity
import android.graphics.Rect
import android.view.View
import android.widget.FrameLayout
import com.egsi.labsi.sog.ioegje.presentation.app.EggLabelApplication

class EggLabelGlobalLayoutUtil {

    private var eggLabelMChildOfContent: View? = null
    private var eggLabelUsableHeightPrevious = 0

    fun eggLabelAssistActivity(activity: Activity) {
        val content = activity.findViewById<FrameLayout>(android.R.id.content)
        eggLabelMChildOfContent = content.getChildAt(0)

        eggLabelMChildOfContent?.viewTreeObserver?.addOnGlobalLayoutListener {
            possiblyResizeChildOfContent(activity)
        }
    }

    private fun possiblyResizeChildOfContent(activity: Activity) {
        val eggLabelUsableHeightNow = eggLabelComputeUsableHeight()
        if (eggLabelUsableHeightNow != eggLabelUsableHeightPrevious) {
            val eggLabelUsableHeightSansKeyboard = eggLabelMChildOfContent?.rootView?.height ?: 0
            val eggLabelHeightDifference = eggLabelUsableHeightSansKeyboard - eggLabelUsableHeightNow

            if (eggLabelHeightDifference > (eggLabelUsableHeightSansKeyboard / 4)) {
                activity.window.setSoftInputMode(EggLabelApplication.eggLabelInputMode)
            } else {
                activity.window.setSoftInputMode(EggLabelApplication.eggLabelInputMode)
            }
//            mChildOfContent?.requestLayout()
            eggLabelUsableHeightPrevious = eggLabelUsableHeightNow
        }
    }

    private fun eggLabelComputeUsableHeight(): Int {
        val r = Rect()
        eggLabelMChildOfContent?.getWindowVisibleDisplayFrame(r)
        return r.bottom - r.top  // Visible height без status bar
    }
}