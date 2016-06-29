package com.fanxl.rxandroid;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by fanxl2 on 2016/6/28.
 */
public class RxBus {

	private static volatile RxBus rxBus = null;
	private Subject<Object, Object> bus;

	private RxBus(){
		bus = new SerializedSubject<>(PublishSubject.create());
	}

	public static RxBus getDefault(){
		if (rxBus==null){
			synchronized (RxBus.class){
				if (rxBus==null){
					rxBus = new RxBus();
				}
			}
		}
		return rxBus;
	}

	public void post(Object object){
		bus.onNext(object);
	}

	public <T> Observable<T> toObserverable(Class<T> eventType) {
		// ofType = filter + cast
		return bus.ofType(eventType);
	}

}
