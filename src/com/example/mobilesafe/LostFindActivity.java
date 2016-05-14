package com.example.mobilesafe;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class LostFindActivity extends Activity {
	private SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//判断是否做过设置向导，没有就跳转设置向导
		sp = getSharedPreferences("config", MODE_PRIVATE);
		boolean configed = sp.getBoolean("configed", false);
		if(configed){
			//做过向导
			setContentView(R.layout.activity_lost_find);
		}
		else {
			//没有做过向导，跳转向导页面
			Intent intent = new Intent(LostFindActivity.this,Setup1Activity.class);
			startActivity(intent);
			//关闭当前界面
			finish();
		}
		
	}
	public void reEnterSetup(View v){
		Intent intent = new Intent(LostFindActivity.this,Setup1Activity.class);
		startActivity(intent);
	}
}
