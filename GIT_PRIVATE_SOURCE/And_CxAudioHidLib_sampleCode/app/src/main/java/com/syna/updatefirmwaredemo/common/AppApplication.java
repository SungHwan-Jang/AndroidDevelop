package com.syna.updatefirmwaredemo.common;

import java.util.LinkedList;
import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;

@TargetApi(Build.VERSION_CODES.KITKAT)
public class AppApplication extends Application {
	private static AppApplication instance;
	public static Context mAppContext;
	private List<Activity> activityList = new LinkedList<Activity>();

	@Override
	public void onCreate() {
	    mAppContext = this.getApplicationContext();
        super.onCreate();
	}  

	public AppApplication() {
	}

	public static AppApplication getInstance() {
		if (null == instance) {
			instance = new AppApplication();
		}
		return instance;
	}

	public void addActivity(Activity activity) {
		if (!activityList.contains(activity))
			activityList.add(activity);
	}

	public void exit() {
		for (Activity activity : activityList) {
			activity.finish();
		}
		System.exit(0);
	}
}
