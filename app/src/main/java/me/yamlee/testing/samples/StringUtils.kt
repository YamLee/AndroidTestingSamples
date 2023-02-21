package me.yamlee.testing.samples

import android.content.Context

object StringUtils {
    fun getApplicationName(context: Context): String {
        return context.getString(R.string.app_name)
    }
}