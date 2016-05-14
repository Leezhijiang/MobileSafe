package com.example.mobilesafe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Setup3Activity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup1);
	}
	public void next(View v){
		Intent intent = new Intent(Setup3Activity.this,Setup4Activity.class);
		startActivity(intent);
	}
	public void pre(View v){
		Intent intent = new Intent(Setup3Activity.this,Setup2Activity.class);
		startActivity(intent);
	}
}
