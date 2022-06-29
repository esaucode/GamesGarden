package com.esaudev.gamesgarden.util

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import android.webkit.URLUtil
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import com.esaudev.gamesgarden.R
import com.esaudev.gamesgarden.ui.components.WebViewFragment

object CustomTabsHelper {

    private const val TAG = "CustomTabsHelper"
    const val CHROME_PACKAGE = "com.android.chrome"
    private const val BETA_PACKAGE = "com.chrome.beta"
    private const val DEV_PACKAGE = "com.chrome.dev"
    private const val LOCAL_PACKAGE = "com.google.android.apps.chrome"
    private const val ACTION_CUSTOM_TABS_CONNECTION =
        "android.support.customtabs.action.CustomTabsService"
    private var sPackageNameToUse: String? = null

    /**
     * Goes through all apps that handle VIEW intents and have a warmup service. Picks
     * the one chosen by the user if there is one, otherwise makes a best effort to return a
     * valid package name.
     *
     * This is *not* threadsafe.
     *
     * @param context [Context] to use for accessing [PackageManager].
     * @return The package name recommended to use for connecting to custom tabs related components.
     */
    fun getPackageNameToUse(context: Context): String? {
        if (sPackageNameToUse != null) return sPackageNameToUse
        val pm = context.packageManager
        // Get default VIEW intent handler.
        val activityIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.example.com"))
        val defaultViewHandlerInfo = pm.resolveActivity(activityIntent, 0)
        var defaultViewHandlerPackageName: String? = null
        if (defaultViewHandlerInfo != null) {
            defaultViewHandlerPackageName = defaultViewHandlerInfo.activityInfo.packageName
        }

        // Get all apps that can handle VIEW intents.
        val resolvedActivityList = pm.queryIntentActivities(activityIntent, 0)
        val packagesSupportingCustomTabs: MutableList<String> = ArrayList()
        for (info in resolvedActivityList) {
            val serviceIntent = Intent()
            serviceIntent.action = ACTION_CUSTOM_TABS_CONNECTION
            serviceIntent.setPackage(info.activityInfo.packageName)
            if (pm.resolveService(serviceIntent, 0) != null) {
                packagesSupportingCustomTabs.add(info.activityInfo.packageName)
            }
        }

        // Now packagesSupportingCustomTabs contains all apps that can handle both VIEW intents
        // and service calls.
        if (packagesSupportingCustomTabs.isEmpty()) {
            sPackageNameToUse = ""
        } else if (packagesSupportingCustomTabs.size == 1) {
            sPackageNameToUse = packagesSupportingCustomTabs[0]
        } else if (!TextUtils.isEmpty(defaultViewHandlerPackageName)
            && !hasSpecializedHandlerIntents(context, activityIntent)
            && packagesSupportingCustomTabs.contains(defaultViewHandlerPackageName)
        ) {
            sPackageNameToUse = defaultViewHandlerPackageName
        } else if (packagesSupportingCustomTabs.contains(CHROME_PACKAGE)) {
            sPackageNameToUse = CHROME_PACKAGE
        } else if (packagesSupportingCustomTabs.contains(BETA_PACKAGE)) {
            sPackageNameToUse = BETA_PACKAGE
        } else if (packagesSupportingCustomTabs.contains(DEV_PACKAGE)) {
            sPackageNameToUse = DEV_PACKAGE
        } else if (packagesSupportingCustomTabs.contains(LOCAL_PACKAGE)) {
            sPackageNameToUse = LOCAL_PACKAGE
        }
        return sPackageNameToUse
    }

    /**
     * Used to check whether there is a specialized handler for a given intent.
     * @param intent The intent to check with.
     * @return Whether there is a specialized handler for the given intent.
     */
    private fun hasSpecializedHandlerIntents(context: Context, intent: Intent): Boolean {
        try {
            val pm = context.packageManager
            val handlers = pm.queryIntentActivities(
                intent,
                PackageManager.GET_RESOLVED_FILTER
            )
            if (handlers.isNullOrEmpty()) {
                return false
            }
            for (resolveInfo in handlers) {
                val filter = resolveInfo.filter ?: continue
                if (filter.countDataAuthorities() == 0 || filter.countDataPaths() == 0) continue
                if (resolveInfo.activityInfo == null) continue
                return true
            }
        } catch (e: RuntimeException) {
            Log.e(TAG, "Runtime exception while getting specialized handlers")
        }
        return false
    }

    val packages: Array<String>
        get() = arrayOf("", CHROME_PACKAGE, BETA_PACKAGE, DEV_PACKAGE, LOCAL_PACKAGE)


    fun getCustomTabsBuilder(context: Context): CustomTabsIntent.Builder {
        return CustomTabsIntent.Builder()
            .setColorSchemeParams(
                CustomTabsIntent.COLOR_SCHEME_LIGHT,
                getCustomTabsParams(context)
            )
            .setShowTitle(true)
            .setShareState(CustomTabsIntent.SHARE_STATE_OFF)
            .setExitAnimations(
                context, android.R.anim.slide_in_left,
                android.R.anim.slide_out_right
            )
    }

    fun getCustomTabsParams(context: Context): CustomTabColorSchemeParams {
        return CustomTabColorSchemeParams.Builder()
            .setNavigationBarColor(ContextCompat.getColor(context, R.color.black))
            .setToolbarColor(
                ContextCompat.getColor(context, R.color.black)
            )
            .setSecondaryToolbarColor(
                ContextCompat.getColor(context, R.color.black)
            )
            .build()
    }

    fun isValidURL(url: String): Boolean {
        return URLUtil.isValidUrl(url)
    }

    fun openNativeWebView(pageURL: String, pageName: String, context: Context) {
        try {
            if (isValidURL(pageURL))
                WebViewFragment.newInstance(pageURL, pageName)
                    .show(
                        (context as AppCompatActivity).supportFragmentManager,
                        WebViewFragment.TAG
                    )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}