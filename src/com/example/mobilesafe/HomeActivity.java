package com.example.mobilesafe;

import com.example.mobilesafe.utils.MD5Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.LocalSocketAddress.Namespace;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends Activity {
	
	protected static final String TAG = "HomeActivity";
	private GridView gd_home_parent;
	private MyAdapter adapter;
	private SharedPreferences sp;
	private EditText et_setup_pwd;
	private EditText et_setup_confirm;
	private Button ok;
	private Button cancel;
	private AlertDialog ad = null ;
	private static String[] names = {
		"�ֻ�����","ͨѶ��ʿ","�������","���̹���","����ͳ��","�ֻ�ɱ��","��������","�߼�����","��������"
	};
	private static int[] ids={R.drawable.safe,R.drawable.callmsgsafe,R.drawable.app,
		R.drawable.taskmanager,R.drawable.netmanager,R.drawable.trojan,R.drawable.sysoptimize
		,R.drawable.atools,R.drawable.settings};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		gd_home_parent = (GridView)findViewById(R.id.gd_home_parent);
		adapter = new MyAdapter();
		gd_home_parent.setAdapter(adapter);
		gd_home_parent.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				switch (position) {
				case 8://������������
					Intent intent = new Intent(HomeActivity.this, SettingActivity.class);
					startActivity(intent);
					break;
				case 0://�����ֻ�����ҳ��
					showLostFindDialog();
					break;
				default:
					break;
				}
			}
		});
	}
	protected void showLostFindDialog() {
		// TODO Auto-generated method stub
		//�ж��Ƿ����ù�����
		if(isSetupPwd()){
			//�Ѿ��������룬�����Ի���
			showEnterDialog();
		}
		else {
			//û���������롣��������
			showSetupPwdDialog();
		}
	}
	
	//��������Ի���
	private void showSetupPwdDialog() {
		
		// TODO  Auto-generated method stub
		AlertDialog.Builder builder = new Builder(this);
		//�Զ���һ�������ļ�
		View view = View.inflate(this, R.layout.dialog_setup_password, null);
		et_setup_pwd = (EditText) view.findViewById(R.id.et_setup_pwd);
		et_setup_confirm = (EditText) view.findViewById(R.id.et_setup_confirm);
		ok = (Button) view.findViewById(R.id.ok);
		cancel = (Button) view.findViewById(R.id.cancel);
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//ȡ���Ի���
				ad.dismiss();
			}
		});
		ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//ȡ������
				String password = et_setup_pwd.getText().toString().trim();
				String password_confrim = et_setup_confirm.getText().toString().trim();
				if(TextUtils.isEmpty(password)||TextUtils.isEmpty(password_confrim)){
					Toast.makeText(HomeActivity.this,"����Ϊ��",2000).show();
					return;
				}
				//�ж�һ�±���
				if(password.equals(password_confrim)){
					//���һ�£��������룬ȥ���Ի��򣬽����ֻ�����ҳ��
					Editor editor = sp.edit();
					editor.putString("password",MD5Utils.md5Password(password));//MD5����
					editor.commit();
					ad.dismiss();
					Log.i(TAG, "���һ�£��������룬ȥ���Ի��򣬽����ֻ�����ҳ��");
					Intent intent = new Intent(HomeActivity.this,LostFindActivity.class);
					startActivity(intent);
				}
				else {
					Toast.makeText(HomeActivity.this,"���벻һ��",2000).show();
					return;
				}
			}
		});
		builder.setView(view);
		ad = builder.show();
	}
	//��������Ի���
	private void showEnterDialog() {
		// TODO  Auto-generated method stub
				AlertDialog.Builder builder = new Builder(this);
				//�Զ���һ�������ļ�
				View view = View.inflate(this, R.layout.dialog_enter_password, null);
				et_setup_pwd = (EditText) view.findViewById(R.id.et_setup_pwd);
				ok = (Button) view.findViewById(R.id.ok);
				cancel = (Button) view.findViewById(R.id.cancel);
				cancel.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						//ȡ���Ի���
						ad.dismiss();
					}
				});
				ok.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						//ȡ�����ܺ������
						String password = MD5Utils.md5Password(et_setup_pwd.getText().toString().trim());
						String savePassword = sp.getString("password", "");
						if(TextUtils.isEmpty(password)){
							Toast.makeText(HomeActivity.this,"����Ϊ��",2000).show();
							return;
						}
						//�ж������Ƿ���ȷ
						if(password.equals(savePassword)){
							//����������ȷ��ȥ���Ի��򣬽����ֻ�����ҳ��
							ad.dismiss();
							Log.i(TAG, "������ȷ��ȥ���Ի��򣬽����ֻ�����ҳ��");
							Intent intent = new Intent(HomeActivity.this,LostFindActivity.class);
							startActivity(intent);
						}
						else {
							et_setup_pwd.setText("");
							Toast.makeText(HomeActivity.this,"�������",2000).show();
							return;
						}
					}
				});
				builder.setView(view);
				ad = builder.show();
		
	}
	/**
	 * �ж��Ƿ����ù�����
	 * @return
	 */
	private boolean isSetupPwd(){
		String password = sp.getString("password", null);
		return !TextUtils.isEmpty(password);
		
	}
	private class MyAdapter extends BaseAdapter
	{

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View view = View.inflate(HomeActivity.this, R.layout.list_item_home, null);
			ImageView iv = (ImageView) view.findViewById(R.id.iv_item);
			TextView tv = (TextView) view.findViewById(R.id.tv_item);
			
			tv.setText(names[position]);
			iv.setImageResource(ids[position]);
			return view;
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return names.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

	}
}
