package com.fanxl.fanreader.presenter.impl;

import com.fanxl.fanreader.api.zhihu.ZhihuRequest;
import com.fanxl.fanreader.bean.UpdateItem;
import com.fanxl.fanreader.presenter.IMainPresenter;
import com.fanxl.fanreader.ui.iView.IMain;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by fanxl2 on 2016/6/23.
 */
public class MainPresenterImpl extends BasePresenterImpl implements IMainPresenter {

	private IMain iMain;

	public MainPresenterImpl(IMain iMain){
		if (iMain==null){
			throw new IllegalArgumentException("iMain can not be null");
		}
		this.iMain=iMain;
	}

	@Override
	public void checkUpdate() {
		Subscription s = ZhihuRequest.getZhihuApi().getUpdateInfo()
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Observer<UpdateItem>() {
					@Override
					public void onCompleted() {

					}

					@Override
					public void onError(Throwable e) {

					}

					@Override
					public void onNext(UpdateItem updateItem) {
						iMain.showUpdate(updateItem);
					}
				});
		addSubscription(s);
	}
}
