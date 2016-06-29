package com.fanxl.fanreader.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.fanxl.fanreader.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fanxl2 on 2016/6/27.
 */
public class TestToolBarOne extends AppCompatActivity {

	@BindView(R.id.toolbar)
	Toolbar toolbar;
	@BindView(R.id.iv_zhihu_story)
	ImageView ivZhihuStory;
	@BindView(R.id.ctl)
	CollapsingToolbarLayout ctl;
	@BindView(R.id.nest)
	NestedScrollView nest;
	@BindView(R.id.fabButton)
	FloatingActionButton fabButton;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_tool_one);
		ButterKnife.bind(this);

		toolbar.setTitle("这个我测试");
		setSupportActionBar(toolbar);

		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
		}

		ivZhihuStory.setBackgroundResource(R.drawable.bg);
	}
}
