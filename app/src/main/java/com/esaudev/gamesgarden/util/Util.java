package com.esaudev.gamesgarden.util;

import android.content.Context;
import android.content.pm.PackageManager;

public class Util {
    public static boolean isAppInstalled(String packageName, Context context) {
        PackageManager pm = context.getPackageManager();
        boolean installed;
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            installed = false;
        }
        return installed;
    }
}
