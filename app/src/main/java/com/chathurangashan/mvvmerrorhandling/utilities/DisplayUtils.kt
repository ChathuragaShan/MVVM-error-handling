package com.chathurangashan.mvvmerrorhandling.utilities

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics

object DisplayUtils {
    fun dpFromPx(context: Context, px: Float): Float {
        return px / context.resources.displayMetrics.density
    }

    fun pxFromDp(context: Context, dp: Float): Float {
        return dp * context.resources.displayMetrics.density
    }

    fun isSafeToShowDialog(activity: Activity): Boolean {
        return activity.hasWindowFocus()
    }

    fun gcd(a: Long, b: Long): Long {
        return if (b == 0L) a else gcd(b, a % b)
    }

    fun asFraction(a: Long, b: Long): Pair<Long,Long> {
        val gcd = gcd(a, b)
        return Pair((a / gcd) , b / gcd)
    }


    fun getDisplayMetrics(activity: Activity): DisplayMetrics {
        val outMetrics = DisplayMetrics()

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            val display = activity.display
            display?.getRealMetrics(outMetrics)
        } else {
            @Suppress("DEPRECATION")
            val display = activity.windowManager.defaultDisplay
            @Suppress("DEPRECATION")
            display.getMetrics(outMetrics)
        }

        return outMetrics
    }

}