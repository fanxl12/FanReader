package com.fanxl.fanreader.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fanxl.fanreader.R;

/**
 * Created by fanxl2 on 2016/6/23.
 */
public class WeixinFragment extends BaseFragment {


	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_common, container, false);
		return view;
	}
}
