package com.example.mobilesafe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Setup1Activity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup1);
	}
	public void next(View v){
		//��һ���ĵ���¼�
		Intent intent = new Intent(Setup1Activity.this,Setup2Activity.class);
		startActivity(intent);
	}
}
