package com.fanxl.fanreader.api.zhihu;

import com.fanxl.fanreader.bean.UpdateItem;
import com.fanxl.fanreader.bean.zhihu.ZhihuDaily;
import com.fanxl.fanreader.bean.zhihu.ZhihuStory;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by fanxl2 on 2016/6/23.
 */
public interface ZhihuApi {

	@GET("/api/4/news/latest")
	Observable<ZhihuDaily> getLastDaily();

	@GET("http://caiyao.name/releases/MrUpdate.json")
	Observable<UpdateItem> getUpdateInfo();

	@GET("/api/4/news/{id}")
	Observable<ZhihuStory> getZhihuStory(@Path("id") String id);



}
