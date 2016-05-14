package com.example.mobilesafe;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;

public class Setup4Activity extends Activity {
	private SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup1);
		sp = getSharedPreferences("config", MODE_PRIVATE);
	}
	public void next(View v){
		Editor editor = sp.edit();
		editor.putBoolean("configed", true);
		editor.commit();
		Intent intent = new Intent(Setup4Activity.this,Setup3Activity.class);
		startActivity(intent);
	}
	public void pre(View v){
		Intent intent = new Intent(Setup4Activity.this,LostFindActivity.class);
		startActivity(intent);
	}
}
