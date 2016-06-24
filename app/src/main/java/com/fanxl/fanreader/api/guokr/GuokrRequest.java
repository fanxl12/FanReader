package com.fanxl.fanreader.api.guokr;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.fanxl.fanreader.MicroApplication;
import com.fanxl.fanreader.utils.NetWorkUtil;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by fanxl2 on 2016/6/23.
 */
public class GuokrRequest {

	private static GuokrApi guokrApi = null;

	private static final String CACHE_CONTROL = "Cache-Control";

	private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
		@Override
		public Response intercept(Chain chain) throws IOException {
			Response originalResponse = chain.proceed(chain.request());
			if (NetWorkUtil.isNetWorkAvailable(MicroApplication.getContext())) {
				int maxAge = 60; // 在线缓存在1分钟内可读取
				return originalResponse.newBuilder()
						.removeHeader("Pragma")
						.removeHeader(CACHE_CONTROL)
						.header(CACHE_CONTROL, "public, max-age=" + maxAge)
						.build();
			}else {
				int maxStale = 60 * 60 * 24 * 28; // 离线时缓存保存4周
				return originalResponse.newBuilder()
						.removeHeader("Pragma")
						.removeHeader(CACHE_CONTROL)
						.header(CACHE_CONTROL, "public, only-if-cached, max-stale=" + maxStale)
						.build();
			}
		}
	};

	private static File httpCacheDirectory = new File(MicroApplication.getContext().getCacheDir(), "guokrCache");
	private static int cacheSize = 10 * 1024 * 1024; // 10 MiB
	private static Cache cache = new Cache(httpCacheDirectory, cacheSize);

	private static OkHttpClient client = new OkHttpClient.Builder()
			.addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
			.addNetworkInterceptor(new StethoInterceptor())
			.addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)//不添加离线缓存无效
			.cache(cache)
			.build();


	public static GuokrApi getGuokrApi() {
		synchronized (GuokrRequest.class){
			if (guokrApi==null){
				guokrApi = new Retrofit.Builder()
						.baseUrl("http://www.guokr.com")
						.client(client)
						.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
						.addConverterFactory(GsonConverterFactory.create())
						.build().create(GuokrApi.class);
			}
			return guokrApi;
		}
	}

}
