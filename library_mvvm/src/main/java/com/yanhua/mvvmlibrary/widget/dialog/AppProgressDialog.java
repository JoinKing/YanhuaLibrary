package com.yanhua.mvvmlibrary.widget.dialog;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.yanhua.mvvmlibrary.R;


public class AppProgressDialog extends ProgressDialog {
	private String message;
	private TextView messageTextView;
	private Activity activity;
	public AppProgressDialog(Activity activity) {
		super(activity, R.style.bottom_dialog);
		message = "正在加载...";
		this.activity = activity;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_progress_dialog);
		messageTextView =  findViewById(R.id.define_progress_msg);
		messageTextView.setText(message);
	}

	@Override
	public void show() {
		WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
		layoutParams.gravity = Gravity.CENTER;
		layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
		layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
		getWindow().getDecorView().setPadding(0, 0, 0, 0);
		getWindow().setAttributes(layoutParams);
		super.show();
		if (messageTextView != null) {
			messageTextView.setText(message);
		}
	}

}
