package com.nandi.disastermanager.http;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.nandi.disastermanager.utils.ServiceUtil;


/**
 * @author qingsong
 *  广播重复启动服务
 */
public class AlarmReceiver extends BroadcastReceiver {

	private Context mContext;

	@Override
	public void onReceive(Context context, Intent intent) {
		System.out.println("广播接收器AlarmReceiver=====onReceive()");
		this.mContext = context;
	
		// 跳转到service
		if (ServiceUtil.isConnectingToInternet(mContext)) {
			Intent i = new Intent(context, ReplaceService.class);
			System.out.println("有网络，从广播接收器启动数据更新服务");
			mContext.startService(i);
		} else {
			System.out.println("没有网络，不启动数据更新服务");
		}

	}

}
