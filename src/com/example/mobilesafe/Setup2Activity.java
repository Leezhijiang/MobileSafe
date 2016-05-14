package com.example.mobilesafe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Setup2Activity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup2);
	}
	public void next(View v){
		Intent intent = new Intent(Setup2Activity.this,Setup3Activity.class);
		startActivity(intent);
	}
	public void pre(View v){
		Intent intent = new Intent(Setup2Activity.this,Setup1Activity.class);
		startActivity(intent);
	}
}
