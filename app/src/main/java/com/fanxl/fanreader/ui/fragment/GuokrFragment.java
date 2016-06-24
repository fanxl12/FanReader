package com.fanxl.fanreader.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.fanxl.fanreader.R;
import com.fanxl.fanreader.bean.guokr.GuokrHotItem;
import com.fanxl.fanreader.presenter.IGuokrPresenter;
import com.fanxl.fanreader.presenter.impl.IGuokrPresenterImpl;
import com.fanxl.fanreader.ui.adapter.GuokrAdapter;
import com.fanxl.fanreader.ui.iView.IGuokrFragment;
import com.fanxl.fanreader.ui.view.DividerItemDecoration;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by fanxl2 on 2016/6/23.
 */
public class GuokrFragment extends BaseFragment implements IGuokrFragment, SwipeRefreshLayout.OnRefreshListener {

	private static final String TAG = "GuokrFragment";

	@BindView(R.id.swipe_target)
	RecyclerView swipeTarget;
	@BindView(R.id.swipeToLoadLayout)
	SwipeRefreshLayout swipeRefreshLayout;
	@BindView(R.id.progressBar)
	ProgressBar progressBar;

	private Unbinder mUnbinder;
	private IGuokrPresenter mGuokrPresenter;
	private int currentOffset;
	private boolean loading = false;
	private ArrayList<GuokrHotItem> guokrHotItems = new ArrayList<>();
	private GuokrAdapter guokrAdapter;
	private LinearLayoutManager mLinearLayoutManager;
	int pastVisiblesItems, visibleItemCount, totalItemCount;


	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_common, container, false);
		mUnbinder = ButterKnife.bind(this, view);
		return view;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initData();
		initView();
	}

	private void initView() {
		swipeRefreshLayout.setOnRefreshListener(this);
		mLinearLayoutManager = new LinearLayoutManager(getActivity());
		setSwipeRefreshLayoutColor(swipeRefreshLayout);
		swipeTarget.setLayoutManager(mLinearLayoutManager);
		swipeTarget.setHasFixedSize(true);
		swipeTarget.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST));
		swipeTarget.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

				if (dy>0){
					visibleItemCount = mLinearLayoutManager.getChildCount();
					totalItemCount = mLinearLayoutManager.getItemCount();
					pastVisiblesItems = mLinearLayoutManager.findFirstVisibleItemPosition();
					if (!loading && (visibleItemCount + pastVisiblesItems) >= totalItemCount) {
						loading = true;
						onLoadMore();
					}
				}
			}
		});


		guokrAdapter = new GuokrAdapter(guokrHotItems, getActivity());
		swipeTarget.setAdapter(guokrAdapter);
		onRefresh();
	}

	private void initData() {
		mGuokrPresenter = new IGuokrPresenterImpl(this);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		mGuokrPresenter.unsubcrible();
		mUnbinder.unbind();
	}

	@Override
	public void updateList(ArrayList<GuokrHotItem> guokrHotItems) {
		currentOffset++;
		Log.i(TAG, "updateList: "+guokrHotItems.size());
		this.guokrHotItems.addAll(guokrHotItems);
		guokrAdapter.notifyDataSetChanged();
	}

	@Override
	public void showProgressDialog() {
		if (progressBar != null)
			progressBar.setVisibility(View.VISIBLE);
	}

	@Override
	public void hidProgressDialog() {
		if (swipeRefreshLayout != null) {//不加可能会崩溃
			swipeRefreshLayout.setRefreshing(false);
			loading = false;
			progressBar.setVisibility(View.INVISIBLE);
		}
	}

	@Override
	public void showError(String error) {
		Log.i(TAG, "showError: "+error);
		Snackbar.make(swipeTarget, getString(R.string.common_loading_error) + error, Snackbar.LENGTH_SHORT).setAction(getString(R.string.comon_retry), new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mGuokrPresenter.getGuokrHot(currentOffset);
			}
		}).show();
	}

	@Override
	public void onRefresh() {
		currentOffset = 0;
		guokrHotItems.clear();
		//2016-04-05修复Inconsistency detected. Invalid view holder adapter positionViewHolder
		guokrAdapter.notifyDataSetChanged();
		mGuokrPresenter.getGuokrHot(currentOffset);
	}

	public void onLoadMore() {
		mGuokrPresenter.getGuokrHot(currentOffset);
	}
}
