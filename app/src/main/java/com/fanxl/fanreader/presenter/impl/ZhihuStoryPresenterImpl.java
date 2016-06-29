package com.fanxl.fanreader.presenter.impl;

import com.fanxl.fanreader.api.guokr.GuokrRequest;
import com.fanxl.fanreader.api.zhihu.ZhihuRequest;
import com.fanxl.fanreader.bean.guokr.GuokrArticle;
import com.fanxl.fanreader.bean.zhihu.ZhihuStory;
import com.fanxl.fanreader.presenter.IZhihuStoryPresenter;
import com.fanxl.fanreader.ui.iView.IZhihuStory;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by fanxl2 on 2016/6/27.
 */
public class ZhihuStoryPresenterImpl extends BasePresenterImpl implements IZhihuStoryPresenter {

	private IZhihuStory iZhihuStory;

	public ZhihuStoryPresenterImpl(IZhihuStory iZhihuStory){
		if (iZhihuStory==null){
			throw new IllegalArgumentException("iZhihuStory can not be null");
		}
		this.iZhihuStory=iZhihuStory;
	}

	@Override
	public void getZhiHuStory(String id) {
		Subscription s = ZhihuRequest.getZhihuApi().getZhihuStory(id)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Observer<ZhihuStory>() {
					@Override
					public void onCompleted() {

					}

					@Override
					public void onError(Throwable e) {
						iZhihuStory.showError(e.getMessage());
					}

					@Override
					public void onNext(ZhihuStory zhihuStory) {
						iZhihuStory.showZhihuStory(zhihuStory);
					}
				});
		addSubscription(s);
	}

	@Override
	public void getGuokrArticle(String id) {
		Subscription s = GuokrRequest.getGuokrApi().getGuokrArticle(id)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Observer<GuokrArticle>() {
					@Override
					public void onCompleted() {

					}

					@Override
					public void onError(Throwable e) {
						iZhihuStory.showError(e.getMessage());
					}

					@Override
					public void onNext(GuokrArticle guokrArticle) {
						iZhihuStory.showGuokrArticle(guokrArticle);
					}
				});
		addSubscription(s);
	}
}
