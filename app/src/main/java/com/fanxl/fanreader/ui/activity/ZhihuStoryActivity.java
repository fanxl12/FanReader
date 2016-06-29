package com.fanxl.fanreader.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.fanxl.fanreader.R;
import com.fanxl.fanreader.bean.guokr.GuokrArticle;
import com.fanxl.fanreader.bean.zhihu.ZhihuStory;
import com.fanxl.fanreader.presenter.IZhihuStoryPresenter;
import com.fanxl.fanreader.presenter.impl.ZhihuStoryPresenterImpl;
import com.fanxl.fanreader.ui.iView.IZhihuStory;
import com.fanxl.fanreader.utils.Config;
import com.fanxl.fanreader.utils.ImageLoader;
import com.fanxl.fanreader.utils.WebUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fanxl2 on 2016/6/24.
 */
public class ZhihuStoryActivity extends BaseActivity implements IZhihuStory {

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

	private IZhihuStoryPresenter iZhihuStoryPresenter;

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
		if (type == TYPE_ZHIHU) {
			iZhihuStoryPresenter.getZhiHuStory(id);
		} else if (type == TYPE_GUOKR) {
			iZhihuStoryPresenter.getGuokrArticle(id);
		}
	}

	private void initView() {
		toolbar.setTitle(title);
		setSupportActionBar(toolbar);
		boolean isKitKat = Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT;
		int vibrantColor = setToolBar(fabButton, toolbar, false, isKitKat, null);

		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ZhihuStoryActivity.this.onBackPressed();
			}
		});

		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
		}

		fabButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				nest.smoothScrollTo(0, 0);
			}
		});

		WebSettings settings = wvZhihu.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		settings.setLoadWithOverviewMode(true);
		settings.setBuiltInZoomControls(true);
//		settings.setUseWideViewPort(true); //造成文字太小
		settings.setDomStorageEnabled(true);
		settings.setDatabaseEnabled(true);
		settings.setAppCachePath(getCacheDir().getAbsolutePath() + "/webViewCache");
		settings.setAppCacheEnabled(true);
		settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
		wvZhihu.setWebChromeClient(new WebChromeClient());
		ctl.setContentScrimColor(vibrantColor);  //展开和收缩的颜色
		ctl.setBackgroundColor(vibrantColor);    //背景颜色
		ctl.setStatusBarScrimColor(vibrantColor);
	}

	private void initData() {
		type = getIntent().getIntExtra("type", 0);
		id = getIntent().getStringExtra("id");
		title = getIntent().getStringExtra("title");
		iZhihuStoryPresenter = new ZhihuStoryPresenterImpl(this);
	}

	@Override
	protected void onDestroy() {
		//webview内存泄露
		if (wvZhihu != null) {
			((ViewGroup) wvZhihu.getParent()).removeView(wvZhihu);
			wvZhihu.destroy();
			wvZhihu = null;
		}
		iZhihuStoryPresenter.unsubcrible();
		super.onDestroy();
	}

	@Override
	public void showError(String error) {
		Snackbar.make(wvZhihu, getString(R.string.common_loading_error) + error, Snackbar.LENGTH_INDEFINITE).setAction(getString(R.string.comon_retry), new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getData();
			}
		}).show();
	}

	@Override
	public void showZhihuStory(ZhihuStory zhihuStory) {
		ImageLoader.loadImage(ZhihuStoryActivity.this,zhihuStory.getImage(),ivZhihuStory);
		url = zhihuStory.getShareUrl();
		if (TextUtils.isEmpty(zhihuStory.getBody())) {
			wvZhihu.loadUrl(zhihuStory.getShareUrl());
		} else {
			String data = WebUtil.buildHtmlWithCss(zhihuStory.getBody(), zhihuStory.getCss(), Config.isNight);
			wvZhihu.loadDataWithBaseURL(WebUtil.BASE_URL, data, WebUtil.MIME_TYPE, WebUtil.ENCODING, WebUtil.FAIL_URL);
		}
	}

	@Override
	public void showGuokrArticle(GuokrArticle guokrArticle) {
		ImageLoader.loadImage(ZhihuStoryActivity.this, guokrArticle.getResult().getSmallImage(), ivZhihuStory);
		url = guokrArticle.getResult().getUrl();
		if (TextUtils.isEmpty(guokrArticle.getResult().getContent())) {
			wvZhihu.loadUrl(guokrArticle.getResult().getUrl());
		} else {
			//解决图片显示问题,视频显示问题
			String data = WebUtil.buildHtmlForIt(guokrArticle.getResult().getContent().replaceAll("(style.*?\")>", "").replaceAll("width=\"(.*?)\"", "100%").replaceAll("height=\"(.*?)\"", "auto"), Config.isNight);
			wvZhihu.loadDataWithBaseURL(WebUtil.BASE_URL, data, WebUtil.MIME_TYPE, WebUtil.ENCODING, WebUtil.FAIL_URL);
		}
	}
}
