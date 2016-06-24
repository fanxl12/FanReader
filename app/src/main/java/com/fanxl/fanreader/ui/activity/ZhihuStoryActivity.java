package com.fanxl.fanreader.ui.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.fanxl.fanreader.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fanxl2 on 2016/6/24.
 */
public class ZhihuStoryActivity extends BaseActivity {

	public static final int TYPE_ZHIHU = 1;
	public static final int TYPE_GUOKR = 2;

	@BindView(R.id.toolbar)
	Toolbar toolbar;
	@BindView(R.id.wv_zhihu)
	WebView wvZhihu;
	@BindView(R.id.iv_zhihu_story)
	ImageView ivZhihuStory;
	@BindView(R.id.ctl)
	CollapsingToolbarLayout ctl;
	@BindView(R.id.nest)
	NestedScrollView nest;
	@BindView(R.id.fabButton)
	FloatingActionButton fabButton;

	private int type;
	private String id;
	private String title;
	private String url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zhihu_story);
		ButterKnife.bind(this);

		initData();
		initView();
		getData();
	}

	private void getData() {

	}

	private void initView() {
		WebSettings settings = wvZhihu.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		settings.setLoadWithOverviewMode(true);
		settings.setBuiltInZoomControls(true);
		//settings.setUseWideViewPort(true);造成文字太小
		settings.setDomStorageEnabled(true);
		settings.setDatabaseEnabled(true);
		settings.setAppCachePath(getCacheDir().getAbsolutePath() + "/webViewCache");
		settings.setAppCacheEnabled(true);
		settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
		wvZhihu.setWebChromeClient(new WebChromeClient());
//		ctl.setContentScrimColor(vibrantColor);
//		ctl.setBackgroundColor(vibrantColor);
//		ctl.setStatusBarScrimColor(vibrantColor);
	}

	private void initData() {
		type = getIntent().getIntExtra("type", 0);
		id = getIntent().getStringExtra("id");
		title = getIntent().getStringExtra("title");
	}

}
