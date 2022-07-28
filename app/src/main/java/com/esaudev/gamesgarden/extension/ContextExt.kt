package com.esaudev.gamesgarden.extension

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent

fun Context.openChromeTab(url: String) {
    val customTab = CustomTabsIntent.Builder().build()
    customTab.launchUrl(this, Uri.parse(url))
}