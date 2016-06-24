package com.fanxl.fanreader;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

/**
 * Created by fanxl2 on 2016/6/22.
 */
public class MicroApplication extends Application {

	public static MicroApplication microApplication = null;

	@Override
	public void onCreate() {
		super.onCreate();
		microApplication = this;
		Stetho.initializeWithDefaults(this);
	}

	public static Context getContext(){
		return microApplication;
	}

}
