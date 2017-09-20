package com.nandi.disastermanager.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

/**
 * Created by qingsong on 2017/9/19.
 */

public class AppUtils {

    public static String getVerCode(Context context) {
        int versionNumber = -1;
        try {
            versionNumber = context.getPackageManager().getPackageInfo("com.nandi.disastermanager", 0).versionCode;
            System.out.println("当前版本" + versionNumber);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("version", e.getMessage());
        }
        return versionNumber + "";
    }
}
