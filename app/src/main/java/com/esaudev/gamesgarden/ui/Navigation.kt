package com.esaudev.gamesgarden.ui

import android.content.Context
import android.content.Intent

fun Intent.goToSearch(context: Context): Intent {
    action = "action.SearchActivity.open"
    setPackage(context.packageName)
    return this
}