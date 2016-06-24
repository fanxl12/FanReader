package com.fanxl.fanreader.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.fanxl.fanreader.R;

import java.io.File;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by fanxl2 on 2016/6/22.
 */
public class TestActivity extends BaseActivity {

	private static final String TAG = "TestActivity";
	private Subscriber<String> subscriber;
	//被观察者
	private Observable<String> observable;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);
		ob();
		beiOb();
	}

	public void dingyue(View v){
		Action1<String> onNextAction = new Action1<String>() {
			@Override
			public void call(String s) {
				Log.i(TAG, "onNextAction call: "+s);
			}
		};

		Action1<Throwable> onErrorAction = new Action1<Throwable>() {
			@Override
			public void call(Throwable throwable) {

			}
		};

		Action0 onCompletedAction = new Action0() {
			@Override
			public void call() {
				Log.i(TAG, "onCompletedAction call: ");
			}
		};

		// 自动创建 Subscriber ，并使用 onNextAction 来定义 onNext()
		observable.subscribe(onNextAction);
		// 自动创建 Subscriber ，并使用 onNextAction 和 onErrorAction 来定义 onNext() 和 onError()
		observable.subscribe(onNextAction, onErrorAction);
		// 自动创建 Subscriber ，并使用 onNextAction、 onErrorAction 和 onCompletedAction 来定义 onNext()、 onError() 和 onCompleted()
		observable.subscribe(onNextAction, onErrorAction, onCompletedAction);


	}

	private void beiOb(){
		//被观察者
		observable = Observable.create(new Observable.OnSubscribe<String>() {
			@Override
			public void call(Subscriber<? super String> subscriber) {
				subscriber.onNext("12");
				subscriber.onNext("13");
				subscriber.onNext("14");
				subscriber.onNext("15");
				subscriber.onCompleted();
			}
		});

		observable.subscribe(subscriber);
	}

	private void ob(){
		//观察者
		Observer<String> observer = new Observer<String>() {
			@Override
			public void onCompleted() {
				Log.i(TAG, "onCompleted: ");
			}

			@Override
			public void onError(Throwable e) {
				Log.i(TAG, "onError: ");
			}

			@Override
			public void onNext(String s) {
				Log.i(TAG, "onNext: "+s);
			}
		};

		subscriber = new Subscriber<String>() {
			@Override
			public void onCompleted() {
				Log.i(TAG, "subscriber onCompleted: ");
			}

			@Override
			public void onError(Throwable e) {
				Log.i(TAG, "subscriber onError: ");
			}

			@Override
			public void onNext(String s) {
				Log.i(TAG, "subscriber onNext: "+s);
			}
		};
	}

	private void test1(){
		final File file = new File("");
		final ImageView image = new ImageView(this);
		Observable.create(new Observable.OnSubscribe<Bitmap>() {
			@Override
			public void call(Subscriber<? super Bitmap> subscriber) {

			}
		}).subscribe(new Observer<Bitmap>() {
			@Override
			public void onCompleted() {

			}

			@Override
			public void onError(Throwable e) {

			}

			@Override
			public void onNext(Bitmap bitmap) {
				image.setImageBitmap(bitmap);
			}
		});
	}

}
