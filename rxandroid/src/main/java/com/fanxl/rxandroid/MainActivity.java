package com.fanxl.rxandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity {

	private CompositeSubscription sb = new CompositeSubscription();
	private static final String TAG = "MainActivity";

	@BindView(R.id.tv_name)
	TextView tv_name;

	@BindView(R.id.et_input)
	EditText et_input;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
		Subscription s = RxBus.getDefault().toObserverable(OneEvent.class)
				.subscribe(new Action1<OneEvent>() {
					@Override
					public void call(OneEvent oneEvent) {
						Log.i(TAG, "call: "+oneEvent.getMsg());
					}
				});
		sb.add(s);
		et_input.setHint("123");
	}



	@OnClick(R.id.bt_push)
	void push(){
		RxBus.getDefault().post(new OneEvent("hello, rxjava"));
	}

	@OnClick(R.id.bt_commit)
	void commit(){

	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (sb!=null && !sb.isUnsubscribed()){
			sb.unsubscribe();
		}
	}
}
