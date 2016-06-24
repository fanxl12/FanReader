package com.fanxl.fanreader.utils;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by fanxl2 on 2016/6/23.
 */
public class RxBus {

	private static RxBus ourInstance = new RxBus();

	public static RxBus getInstance() {
		return ourInstance;
	}

	private RxBus() {
		bus = new SerializedSubject<>(PublishSubject.create());
	}

	private final Subject<Object, Object> bus;

	public void send(Object o) {
		bus.onNext(o);
	}

	public <T> Observable<T> toObservable(Class<T> eventClass) {
		return bus.ofType(eventClass);
	}

}
