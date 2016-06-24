package com.fanxl.fanreader.presenter.impl;

import com.fanxl.fanreader.api.guokr.GuokrRequest;
import com.fanxl.fanreader.bean.guokr.GuokrHot;
import com.fanxl.fanreader.presenter.IGuokrPresenter;
import com.fanxl.fanreader.ui.iView.IGuokrFragment;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by fanxl2 on 2016/6/23.
 */
public class IGuokrPresenterImpl extends BasePresenterImpl implements IGuokrPresenter {

	private IGuokrFragment iGuokrFragment;

	public IGuokrPresenterImpl(IGuokrFragment iGuokrFragment){
		if (iGuokrFragment==null){
			throw new IllegalArgumentException("iGuokrFragment can not be null");
		}
		this.iGuokrFragment=iGuokrFragment;

	}

	@Override
	public void getGuokrHot(int offset) {
		iGuokrFragment.showProgressDialog();
		Subscription s = GuokrRequest.getGuokrApi().getGuokrHot(offset)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Observer<GuokrHot>() {
					@Override
					public void onCompleted() {

					}

					@Override
					public void onError(Throwable e) {
						iGuokrFragment.hidProgressDialog();
						iGuokrFragment.showError(e.getMessage());
					}

					@Override
					public void onNext(GuokrHot guokrHot) {
						iGuokrFragment.hidProgressDialog();
						iGuokrFragment.updateList(guokrHot.getResult());
					}
				});
		addSubscription(s);
	}

	@Override
	public void getGuokrHotFromCache(int offset) {

	}
}
