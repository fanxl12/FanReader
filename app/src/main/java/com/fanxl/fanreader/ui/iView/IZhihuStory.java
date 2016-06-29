package com.fanxl.fanreader.ui.iView;

import com.fanxl.fanreader.bean.guokr.GuokrArticle;
import com.fanxl.fanreader.bean.zhihu.ZhihuStory;

/**
 * Created by fanxl2 on 2016/6/27.
 */
public interface IZhihuStory  {

	void showError(String error);

	void showZhihuStory(ZhihuStory zhihuStory);

	void showGuokrArticle(GuokrArticle guokrArticle);

}
