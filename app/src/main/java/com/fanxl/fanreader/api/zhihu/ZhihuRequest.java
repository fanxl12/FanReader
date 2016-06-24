package com.fanxl.fanreader.api.zhihu;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.fanxl.fanreader.MicroApplication;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by fanxl2 on 2016/6/23.
 */
public class ZhihuRequest {

	private static int cacheSize = 10 * 1024 * 1024; // 10 MiB
	private static File httpCacheDirectory = new File(MicroApplication.getContext().getCacheDir(), "zhihuCache");
	private static Cache cache = new Cache(httpCacheDirectory, cacheSize);

	private static OkHttpClient client = new OkHttpClient.Builder()
			.addNetworkInterceptor(new StethoInterceptor())
			.cache(cache)
			.build();

	private static ZhihuApi zhihuApi = null;

	public static ZhihuApi getZhihuApi(){
		if (zhihuApi==null){
			synchronized (ZhihuRequest.class){
				if (zhihuApi==null){
					zhihuApi = new Retrofit.Builder()
							.baseUrl("http://news-at.zhihu.com")
							//添加gson转换器
							.addConverterFactory(GsonConverterFactory.create())
							//通过RxJavaCallAdapterFactory为Retrofit添加RxJava支持
							.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
							.client(client)
							.build().create(ZhihuApi.class);
				}
			}
		}
		return zhihuApi;
	}

}
