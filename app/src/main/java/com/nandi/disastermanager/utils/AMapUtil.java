package com.nandi.disastermanager.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.net.URISyntaxException;


/**
 * Created by qingsong on 2017/9/8.
 */

public class AMapUtil {
    private static Intent intent;


    /**
     * 启动高德App进行导航
     *
     * @param sourceApplication 终点名称
     * @param lat 必填 纬度
     * @param lon 必填 经度
     *  dev 必填 是否偏移(0:lat 和 lon 是已经加密后的,不需要国测加密; 1:需要国测加密)
     * @param style 必填 导航方式(0 速度快; 1 费用少; 2 路程短; 3 不走高速；4 躲避拥堵；5 不走高速且避免收费；6 不走高速且躲避拥堵；7 躲避收费和拥堵；8 不走高速躲避收费和拥堵))
     */
    public static  void goToGaoDe(Context context, String sourceApplication , String lat , String lon  , String style){
        if(isInstallByRead("com.baidu.BaiduMap")) {//传入指定应用包名
            StringBuffer stringBuffer = new StringBuffer("androidamap://navi?sourceApplication=")
                    .append(sourceApplication);
            stringBuffer.append("&lat=").append(lat)
                    .append("&lon=").append(lon)
                    .append("&dev=").append(1)
                    .append("&style=").append(style);

            intent = new Intent("android.intent.action.VIEW", Uri.parse(stringBuffer.toString()));
            intent.setPackage("com.autonavi.minimap");
            context.startActivity(intent);
        }else{
            Toast.makeText(context, "您尚未安装高德地图", Toast.LENGTH_LONG).show();
            Uri uri = Uri.parse("market://details?id=com.autonavi.minimap");
            intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        }
    }

    /**
     *
     * @param context
     * @param sourceApplication   终点名称
     * @param lat 终点纬度
     * @param lon 经度
     */
    public  static void goToBaidu(Context context, String sourceApplication, String lat , String lon ){
        if(isInstallByRead("com.baidu.BaiduMap")){//传入指定应用包名
            try {
                //"origin=latlng:"+"34.264642646862,108.95108518068&" +   //起点  此处不传值默认选择当前位置

                intent = Intent.getIntent("intent://map/direction?" +
                        //"origin=latlng:"+"34.264642646862,108.95108518068&" +   //起点  此处不传值默认选择当前位置
                        "destination=latlng:"+lat+","+lon+"|name:"+sourceApplication+        //终点
                        "&mode=driving&" +          //导航路线方式
                        "&src=慧医#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
                context.startActivity(intent); //启动调用
            } catch (URISyntaxException e) {
                Log.e("intent", e.getMessage());
            }
        }else{//未安装
            //market为路径，id为包名
            //显示手机上所有的market商店
            Toast.makeText(context, "您尚未安装百度地图", Toast.LENGTH_LONG).show();
            Uri uri = Uri.parse("market://details?id=com.baidu.BaiduMap");
            intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        }
    }
    /**
     *
     * @param context
     * @param sourceApplication   终点名称
     * @param lat 终点纬度
     * @param lon 经度
     */
    public  static void goToGoogle(Context context, String sourceApplication, String lat , String lon ){
        if (isInstallByRead("com.google.android.apps.maps")) {
            Uri gmmIntentUri = Uri.parse("google.navigation:q="+lat+","+lon +", + China+sourceApplication");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            context.startActivity(mapIntent);
        }else {
            Toast.makeText(context, "您尚未安装谷歌地图", Toast.LENGTH_LONG).show();

            Uri uri = Uri.parse("market://details?id=com.google.android.apps.maps");
            intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);  }
    }

    /**
     * 根据包名检测某个APP是否安装
     *
     * @param packageName 包名
     * @return true 安装 false 没有安装
     */
    public static boolean isInstallByRead(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }


}
