package com.fanxl.fanreader.ui.activity;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.fanxl.fanreader.utils.Config;
import com.fanxl.fanreader.utils.SharePreferenceUtil;
import com.fanxl.fanreader.utils.StatusBarUtil;

/**
 * Created by fanxl2 on 2016/6/22.
 */
public class BaseActivity extends AppCompatActivity {


	public int setToolBar(FloatingActionButton floatingActionButton, Toolbar toolbar, boolean isChangeToolbar,
						   boolean isChangeStatusBar, DrawerLayout drawerLayout){
		int vibrantColor = getSharedPreferences(SharePreferenceUtil.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
				.getInt(SharePreferenceUtil.VIBRANT, Color.BLUE);

		int mutedColor = getSharedPreferences(SharePreferenceUtil.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
				.getInt(SharePreferenceUtil.MUTED, Color.BLUE);

		if (Config.isNight) {
			vibrantColor = Color.BLACK;
		}
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//			if (SharePreferenceUtil.isChangeNavColor(this))
//				getWindow().setNavigationBarColor(vibrantColor);
//			else
//				getWindow().setNavigationBarColor(Color.BLACK);
//		}

		StatusBarUtil.transparent(this, toolbar);

		if (floatingActionButton != null)
			floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(mutedColor));
		if (isChangeToolbar)
			toolbar.setBackgroundColor(vibrantColor);
		if (isChangeStatusBar) {
			if (SharePreferenceUtil.isImmersiveMode(this))
				StatusBarUtil.setColorNoTranslucent(this, vibrantColor);
			else
				StatusBarUtil.setColor(this, vibrantColor);
		}
		if (drawerLayout != null) {
			if (SharePreferenceUtil.isImmersiveMode(this))
				StatusBarUtil.setColorNoTranslucentForDrawerLayout(this, drawerLayout, vibrantColor);
			else
				StatusBarUtil.setColorForDrawerLayout(this, drawerLayout, vibrantColor);
		}
		return vibrantColor;

	}



}
