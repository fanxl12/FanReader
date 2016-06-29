package com.fanxl.fanreader.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.fanxl.fanreader.R;

/**
 * Created by fanxl2 on 2016/6/22.
 */
public class StatusBarUtil {

	private static final int DEFAULT_STATUS_BAR_ALPHA = 112;

	/**
	 * 设置状态栏纯色 不加半透明效果
	 *
	 * @param activity 需要设置的 activity
	 * @param color    状态栏颜色值
	 */
	public static void setColorNoTranslucent(Activity activity, int color) {
		setColor(activity, color, 0);
	}

	/**
	 * 设置状态栏颜色
	 *
	 * @param activity 需要设置的 activity
	 * @param color    状态栏颜色值
	 */
	public static void setColor(Activity activity, int color) {
		setColor(activity, color, DEFAULT_STATUS_BAR_ALPHA);
	}

	/**
	 * 设置状态栏全透明
	 *
	 * @param activity 需要设置的activity
	 */
	public static void setTransparent(Activity activity) {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
			return;
		}
		transparentStatusBar(activity);
		setRootView(activity);
	}

	/**
	 * 设置状态栏颜色
	 *
	 * @param activity       需要设置的activity
	 * @param color          状态栏颜色值
	 * @param statusBarAlpha 状态栏透明度
	 */
	public static void setColor(Activity activity, int color, int statusBarAlpha) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
			activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			activity.getWindow().setStatusBarColor(calculateStatusColor(color, statusBarAlpha));
		}else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
			activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			// 生成一个状态栏大小的矩形
			View statusView = createStatusBarView(activity, color, statusBarAlpha);
			// 添加 statusView 到布局中
			//2016-06-05修复状态栏与滑动返回冲突
			ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView().findViewById(android.R.id.content);
			decorView.addView(statusView);
			setRootView(activity);
		}
	}

	private static final String TAG = "StatusBarUtil";

	public static void transparent(Activity activity, Toolbar toolbar){

		transparentStatusBar(activity);

//		int statusBarHeight = getStatusBarHeight(activity);

//		Log.i(TAG, "statusBarHeight: "+statusBarHeight);

		//测量actionBarHeight
		TypedValue tv = new TypedValue();
		int actionBarHeight = 0;
		if (activity.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)){
			actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, activity.getResources().getDisplayMetrics());
		}

		Log.i(TAG, "actionBarHeight: "+actionBarHeight);

		//根据版本的不同，重新计算高度
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			int padingTopPx = activity.getResources().getDimensionPixelOffset(R.dimen.toolbar_padding_top);
			Log.i(TAG, "padingTopPx: "+padingTopPx);
			actionBarHeight += padingTopPx;
//			actionBarHeight += statusBarHeight;
		}

		//重新设置toolbar的高度
		ViewGroup.LayoutParams layoutParams = toolbar.getLayoutParams();
		layoutParams.height = actionBarHeight;
		toolbar.setLayoutParams(layoutParams);
	}

	/**
	 * 使状态栏透明
	 */
	public static void transparentStatusBar(Activity activity) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
			activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
		} else {
			activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		}
	}

	/**
	 * 设置根布局参数
	 */
	private static void setRootView(Activity activity) {
		ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
		rootView.setFitsSystemWindows(true);
		rootView.setClipToPadding(true);
	}

	/**
	 * 生成一个和状态栏大小相同的半透明矩形条
	 *
	 * @param activity 需要设置的activity
	 * @param color    状态栏颜色值
	 * @param alpha    透明值
	 * @return 状态栏矩形条
	 */
	private static View createStatusBarView(Activity activity, int color, int alpha) {
		// 绘制一个和状态栏一样高的矩形
		View statusBarView = new View(activity);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				getStatusBarHeight(activity));
		statusBarView.setLayoutParams(params);
		statusBarView.setBackgroundColor(calculateStatusColor(color, alpha));
		return statusBarView;
	}

	/**
	 * 获取状态栏高度
	 *
	 * @param context context
	 * @return 状态栏高度
	 */
	private static int getStatusBarHeight(Context context) {
		// 获得状态栏高度
		int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
		return context.getResources().getDimensionPixelSize(resourceId);
	}

	/**
	 * 计算状态栏颜色
	 *
	 * @param color color值
	 * @param alpha alpha值
	 * @return 最终的状态栏颜色
	 */
	private static int calculateStatusColor(int color, int alpha) {
		float a = 1 - alpha / 255f;
		int red = color >> 16 & 0xff;
		int green = color >> 8 & 0xff;
		int blue = color & 0xff;
		red = (int) (red * a + 0.5);
		green = (int) (green * a + 0.5);
		blue = (int) (blue * a + 0.5);
		return 0xff << 24 | red << 16 | green << 8 | blue;
	}

	/**
	 * 为DrawerLayout 布局设置状态栏颜色,纯色
	 *
	 * @param activity     需要设置的activity
	 * @param drawerLayout DrawerLayout
	 * @param color        状态栏颜色值
	 */
	public static void setColorNoTranslucentForDrawerLayout(Activity activity, DrawerLayout drawerLayout, int color) {
		setColorForDrawerLayout(activity, drawerLayout, color, 0);
	}

	/**
	 * 为DrawerLayout 布局设置状态栏变色
	 *
	 * @param activity       需要设置的activity
	 * @param drawerLayout   DrawerLayout
	 * @param color          状态栏颜色值
	 * @param statusBarAlpha 状态栏透明度
	 */
	public static void setColorForDrawerLayout(Activity activity, DrawerLayout drawerLayout, int color, int statusBarAlpha) {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
			return;
		}
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
		} else {
			activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		}
		// 生成一个状态栏大小的矩形
		View statusBarView = createStatusBarView(activity, color);
		// 添加 statusBarView 到布局中
		ViewGroup contentLayout = (ViewGroup) drawerLayout.getChildAt(0);
		contentLayout.addView(statusBarView, 0);
		// 内容布局不是 LinearLayout 时,设置padding top
		if (!(contentLayout instanceof LinearLayout) && contentLayout.getChildAt(1) != null) {
			contentLayout.getChildAt(1).setPadding(0, getStatusBarHeight(activity), 0, 0);
		}
		// 设置属性
		ViewGroup drawer = (ViewGroup) drawerLayout.getChildAt(1);
		drawerLayout.setFitsSystemWindows(false);
		contentLayout.setFitsSystemWindows(false);
		contentLayout.setClipToPadding(true);
		drawer.setFitsSystemWindows(false);

		addTranslucentView(activity, statusBarAlpha);
	}

	/**
	 * 生成一个和状态栏大小相同的彩色矩形条
	 *
	 * @param activity 需要设置的 activity
	 * @param color    状态栏颜色值
	 * @return 状态栏矩形条
	 */
	private static View createStatusBarView(Activity activity, int color) {
		// 绘制一个和状态栏一样高的矩形
		View statusBarView = new View(activity);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				getStatusBarHeight(activity));
		statusBarView.setLayoutParams(params);
		statusBarView.setBackgroundColor(color);
		return statusBarView;
	}

	/**
	 * 添加半透明矩形条
	 *
	 * @param activity       需要设置的 activity
	 * @param statusBarAlpha 透明值
	 */
	private static void addTranslucentView(Activity activity, int statusBarAlpha) {
		ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
		// 移除半透明矩形,以免叠加
		if (contentView.getChildCount() > 1) {
			contentView.removeViewAt(1);
		}
		contentView.addView(createTranslucentStatusBarView(activity, statusBarAlpha));
	}

	/**
	 * 创建半透明矩形 View
	 *
	 * @param alpha 透明值
	 * @return 半透明 View
	 */
	private static View createTranslucentStatusBarView(Activity activity, int alpha) {
		// 绘制一个和状态栏一样高的矩形
		View statusBarView = new View(activity);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				getStatusBarHeight(activity));
		statusBarView.setLayoutParams(params);
		statusBarView.setBackgroundColor(Color.argb(alpha, 0, 0, 0));
		return statusBarView;
	}

	/**
	 * 为DrawerLayout 布局设置状态栏变色
	 *
	 * @param activity     需要设置的activity
	 * @param drawerLayout DrawerLayout
	 * @param color        状态栏颜色值
	 */
	public static void setColorForDrawerLayout(Activity activity, DrawerLayout drawerLayout, int color) {
		setColorForDrawerLayout(activity, drawerLayout, color, DEFAULT_STATUS_BAR_ALPHA);
	}

}
