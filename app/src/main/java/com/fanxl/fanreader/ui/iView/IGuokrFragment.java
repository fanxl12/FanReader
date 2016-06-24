package com.fanxl.fanreader.ui.iView;

import com.fanxl.fanreader.bean.guokr.GuokrHotItem;

import java.util.ArrayList;

public interface IGuokrFragment extends IBaseFragment {
	void updateList(ArrayList<GuokrHotItem> guokrHotItems);
}