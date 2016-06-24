package com.fanxl.fanreader.ui.iView;

public interface IBaseFragment  {
	void showProgressDialog();

    void hidProgressDialog();

    void showError(String error);
}