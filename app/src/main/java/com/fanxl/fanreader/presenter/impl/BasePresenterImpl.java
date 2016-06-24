package com.fanxl.fanreader.presenter.impl;

import com.fanxl.fanreader.presenter.BasePresenter;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by fanxl2 on 2016/6/23.
 */
public class BasePresenterImpl implements BasePresenter {

	private CompositeSubscription mCompositeSubscription;

	protected void addSubscription(Subscription s){
		if (mCompositeSubscription==null){
			mCompositeSubscription = new CompositeSubscription();
		}
		mCompositeSubscription.add(s);
	}

	@Override
	public void unsubcrible() {
		if (mCompositeSubscription!=null){
			mCompositeSubscription.unsubscribe();
		}
	}
}
