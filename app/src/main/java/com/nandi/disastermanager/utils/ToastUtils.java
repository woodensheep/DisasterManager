package com.nandi.disastermanager.utils;

import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nandi.disastermanager.R;

/**
 * Created by qingsong on 2017/4/13.
 * Toast工具类
 */

public class ToastUtils {

    private final static boolean isShow = true;

    private ToastUtils() {
        throw new UnsupportedOperationException("ToastUtils不能被实例化");
    }

    public static void showShort(Context context, CharSequence text) {
        Toast mToast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
        mToast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 100);
        LinearLayout toastView = (LinearLayout) mToast.getView();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        TextView tv = new TextView(context);
        tv.setTextSize(30);
        tv.setTextColor(Color.RED);
        toastView.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, 25);
        tv.setLayoutParams(params);
        mToast.setView(toastView);
        toastView.addView(tv);
        tv.setText(text);
        mToast.show();
    }


    public static void showLong(Context context, CharSequence text) {
        if (isShow) Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }
}
