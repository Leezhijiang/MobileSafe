package com.example.mobilesafe;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.security.auth.PrivateCredentialPermission;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.mobilesafe.utils.StreamTools;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.media.audiofx.EnvironmentalReverb;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;
import android.widget.Toast;

public class SplashActivity extends Activity {


	protected static final String TAG = "SplashActivity";
	protected static final int ENTER_HOME = 0;
	protected static final int SHOW_UPDATE_DIALOG = 1;
	protected static final int JSON_ERROR = 2;
	protected static final int URL_ERROR = 3;
	protected static final int NETWORK_ERROE = 4;
	private TextView tv_splash_version ;
	private String description;//�°汾������Ϣ
	private String apkurl;//�°汾���ص�ַ
	private TextView tv_splash_info;//�����°汾����
	private SharedPreferences sp ;
	@Override

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		tv_splash_version = (TextView)findViewById(R.id.tv_splash_version);
		tv_splash_info = (TextView)findViewById(R.id.tv_splash_info);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		tv_splash_version.setText("�汾�ţ�"+getVersionName());
		AlphaAnimation aa = new AlphaAnimation(0.2f, 1.0f);
		aa.setDuration(500);
		boolean update = sp.getBoolean("update", true);
		if(update){
			checkUpdate();
		}
		else {
			handler.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					enterHome();
				}
			}, 2000);
		}
		//�������
		findViewById(R.id.rl_splash_root).setAnimation(aa);
		
	}
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case SHOW_UPDATE_DIALOG://��ʾ�����Ի���
				Log.i(TAG, "��ʾ�����Ի���");
				showUpdateDialog();
				break;
			case ENTER_HOME://����������
				Log.i(TAG, "����������");
				enterHome();
				break;
			case JSON_ERROR://json��������
				Log.i(TAG, "json��������");
				enterHome();
				break;
			case URL_ERROR://URL����
				Log.i(TAG, "URL����");
				enterHome();
				break;
			case NETWORK_ERROE://�������
				Log.i(TAG, "�������");
				enterHome();
				break;
			default:
				break;
			}
		}

		

	};
	/**
	 * ����������
	 */
	protected void enterHome() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this,HomeActivity.class);
		startActivity(intent);
		//�رյ�ǰҳ��
		finish();
		
	}
	/**
	 * ���������Ի���
	 */
	protected void showUpdateDialog() {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("��ʾ����")
		.setMessage(description)
/*		.setCancelable(false)*/
		.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				// TODO Auto-generated method stub
				enterHome();
				dialog.dismiss();
			}
		})
		.setPositiveButton("��������", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				//����APK�����滻��װ
				if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
					//���SD������
					FinalHttp finalHttp = new FinalHttp();
					finalHttp.download(apkurl, Environment.getExternalStorageDirectory().getAbsolutePath()+"/mobilesafe2.0.apk",
							new AjaxCallBack<File>() {

								@Override
								public void onStart() {
									// TODO Auto-generated method stub
									tv_splash_info.setVisibility(View.VISIBLE);
									super.onStart();
								}

								@Override
								public void onFailure(Throwable t, int errorNo,
										String strMsg) {
									// TODO Auto-generated method stub
									t.printStackTrace();
									Log.i(TAG, "afinal����ʧ��");
									Toast.makeText(getApplicationContext(), "����ʧ��", 2000).show();
									super.onFailure(t, errorNo, strMsg);
								}

								@Override
								public void onLoading(long count, long current) {
									// TODO Auto-generated method stub
									super.onLoading(count, current);
									int progress =(int)(current*100/count);
									tv_splash_info.setText("���ؽ���:"+progress+"%");
								}

								@Override
								public void onSuccess(File t) {
									// TODO Auto-generated method stub
									super.onSuccess(t);
									installAPK(t);
								}
								/**
								 * ��װapk
								 * @param t
								 */

								private void installAPK(File t) {
									// TODO Auto-generated method stub
									Intent intent = new Intent();
									intent.setAction("android.intent.action.VIEW");
									intent.addCategory("android.intent.category.DEFAULT");
									intent.setDataAndType(Uri.fromFile(t),"application/vnd.android.package-archive");
									startActivity(intent);
								}
					});
				}else{
					Toast.makeText(getApplicationContext(), "û��sd�����밲װ���ٳ���", 2000).show();
					return;
				}
			}
		})
		.setNegativeButton("�´���˵", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				enterHome();
			}
		}).show();
	}
	/**
	 * ����°汾������о�����
	 */
	private void checkUpdate(){
		Log.i(TAG, "�������");
		final Message mes = Message.obtain();
		new Thread(new Runnable() {
			long startTime = System.currentTimeMillis();
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					
					URL url = new URL(getString(R.string.serverurl));
					HttpURLConnection conn= (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("GET");
					conn.setConnectTimeout(4000);
					int code = conn.getResponseCode();
					if(code ==200){
						//�����ɹ�
						InputStream is = conn.getInputStream();
						//����ת���ַ���
						String result = StreamTools.readFromStream(is);
						Log.i(TAG, "�����ɹ���"+result);
						//json����
						JSONObject obj = new JSONObject(result);
						//�õ��������İ汾��Ϣ
						String version = (String) obj.get("version");
						description = (String) obj.get("description");
						apkurl = (String) obj.get("akpurl");

						//У���Ƿ����°汾
						if(getVersionName().equals(version)){
							//�汾һ��û���°汾,������ҳ��
							mes.what=ENTER_HOME;
						}
						else{
							//�����°棬�����Ի���
							mes.what=SHOW_UPDATE_DIALOG;
						}
					}

				} 
				catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					mes.what=JSON_ERROR;
				}
				catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					mes.what=URL_ERROR;
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					mes.what=NETWORK_ERROE;
					e.printStackTrace();
				}
				finally{
					long endTime = System.currentTimeMillis();
					//���̻߳���ʱ��
					long dTime = endTime-startTime;
					if(dTime<2000){
						try {
							Thread.sleep(2000-dTime);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					handler.sendMessage(mes);
				}
			}
		}).start();
	}
	/**
	 * �õ�Ӧ�õİ汾��
	 */
	private String getVersionName(){
		//���������ֻ�APK
		PackageManager pm = getPackageManager();
		//�õ��ƶ�APK�Ĺ����嵥�ļ�
		try {
			PackageInfo info = pm.getPackageInfo(getPackageName(), 0);
			return info.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}

	}
}
