package com.fanxl.fanreader.presenter;

/**
 * Created by fanxl2 on 2016/6/23.
 */
public interface IGuokrPresenter extends BasePresenter {

	void getGuokrHot(int offset);
	void getGuokrHotFromCache(int offset);

}
