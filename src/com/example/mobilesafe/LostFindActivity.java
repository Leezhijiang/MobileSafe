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
		
		//�ж��Ƿ����������򵼣�û�о���ת������
		sp = getSharedPreferences("config", MODE_PRIVATE);
		boolean configed = sp.getBoolean("configed", false);
		if(configed){
			//������
			setContentView(R.layout.activity_lost_find);
		}
		else {
			//û�������򵼣���ת��ҳ��
			Intent intent = new Intent(LostFindActivity.this,Setup1Activity.class);
			startActivity(intent);
			//�رյ�ǰ����
			finish();
		}
		
	}
	public void reEnterSetup(View v){
		Intent intent = new Intent(LostFindActivity.this,Setup1Activity.class);
		startActivity(intent);
	}
}
