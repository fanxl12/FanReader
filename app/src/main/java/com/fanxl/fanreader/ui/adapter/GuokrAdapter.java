package com.fanxl.fanreader.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fanxl.fanreader.R;
import com.fanxl.fanreader.bean.guokr.GuokrHotItem;
import com.fanxl.fanreader.ui.activity.ZhihuStoryActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fanxl2 on 2016/6/23.
 */
public class GuokrAdapter extends RecyclerView.Adapter<GuokrAdapter.GuokrViewHolder> {

	private ArrayList<GuokrHotItem> guokrHotItems;
	private Context mContext;

	public GuokrAdapter(ArrayList<GuokrHotItem> guokrHotItems, Context context) {
		this.guokrHotItems = guokrHotItems;
		this.mContext = context;
	}


	@Override
	public GuokrViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new GuokrViewHolder(LayoutInflater.from(mContext).inflate(R.layout.ithome_item, parent, false));
	}

	@Override
	public void onBindViewHolder(GuokrViewHolder holder, int position) {
		final GuokrHotItem guokrHotItem = guokrHotItems.get(holder.getAdapterPosition());

		Glide.with(mContext).load(guokrHotItem.getSmallImage()).into(holder.mIvIthome);

		holder.mTvTitle.setText(guokrHotItem.getTitle());
		holder.mTvDescription.setText(guokrHotItem.getSummary());
		holder.mTvTime.setText(guokrHotItem.getTime());
		holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, ZhihuStoryActivity.class);
				intent.putExtra("type", ZhihuStoryActivity.TYPE_GUOKR);
				intent.putExtra("id", guokrHotItem.getId());
				intent.putExtra("title", guokrHotItem.getTitle());
				mContext.startActivity(intent);
			}
		});
	}

	@Override
	public int getItemCount() {
		return guokrHotItems.size();
	}

	class GuokrViewHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.tv_title)
		TextView mTvTitle;
		@BindView(R.id.iv_ithome)
		ImageView mIvIthome;
		@BindView(R.id.tv_description)
		TextView mTvDescription;
		@BindView(R.id.tv_time)
		TextView mTvTime;
		@BindView(R.id.btn_detail)
		Button btnDetail;

		GuokrViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}

}
