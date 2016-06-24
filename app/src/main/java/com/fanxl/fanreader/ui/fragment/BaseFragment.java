package com.fanxl.fanreader.ui.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;

import com.fanxl.fanreader.R;
import com.fanxl.fanreader.utils.SharePreferenceUtil;

/**
 * Created by fanxl2 on 2016/6/23.
 */
public class BaseFragment extends Fragment {

	public void setSwipeRefreshLayoutColor(SwipeRefreshLayout swipeRefreshLayout){
		swipeRefreshLayout.setColorSchemeColors(getActivity().getSharedPreferences(SharePreferenceUtil.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE).getInt(SharePreferenceUtil.VIBRANT, ContextCompat.getColor(getActivity(), R.color.colorAccent)));
	}
}
