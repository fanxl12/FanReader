package com.fanxl.fanreader.api.guokr;

import com.fanxl.fanreader.bean.guokr.GuokrArticle;
import com.fanxl.fanreader.bean.guokr.GuokrHot;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by fanxl2 on 2016/6/23.
 */
public interface GuokrApi {

	@GET("http://apis.guokr.com/minisite/article.json?retrieve_type=by_minisite")
	Observable<GuokrHot> getGuokrHot(@Query("offset") int offset);

	@GET("http://apis.guokr.com/minisite/article/{id}.json")
	Observable<GuokrArticle> getGuokrArticle(@Path("id") String id);

}
