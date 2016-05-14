package com.example.mobilesafe.ui;

import com.example.mobilesafe.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SettingItemView extends RelativeLayout {
	private CheckBox cb_status;
	private TextView tv_desc;
	private TextView tv_title;
	private String desc_off;
	private String desc_on;
	
/**
 * ��ʼ�������ļ�
 * @param context
 */
	private void initView(Context context) {
		// TODO Auto-generated method stub
		View.inflate(context, R.layout.setting_item_view, SettingItemView.this);
		cb_status = (CheckBox) this.findViewById(R.id.cb_status);
		tv_desc = (TextView)this.findViewById(R.id.tv_desc);
		tv_title = (TextView)this.findViewById(R.id.tv_title);
		setDesc(desc_off); 
	}
	/**
	 * У����Ͽؼ��Ƿ��н���
	 */
	public boolean isChecked(){
		return cb_status.isChecked();
	}
	/**
	 * ������Ͽؼ���״̬
	 */
	public void setChecked(boolean checked){
		cb_status.setChecked(checked);
		if(checked){
			setDesc(desc_on);
		}else {
			setDesc(desc_off);
		}
	}
	public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initView(context);
		// TODO Auto-generated constructor stub
	}

/**
 * �����ļ�ʹ��ʱ�����
 * @param context
 * @param attrs
 */
	public SettingItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		
		initView(context);
		String title =attrs.getAttributeValue("http://schemas.android.com/apk/res/com.example.mobilesafe", "title_");
		desc_on =attrs.getAttributeValue("http://schemas.android.com/apk/res/com.example.mobilesafe", "desc_on");
		desc_off =attrs.getAttributeValue("http://schemas.android.com/apk/res/com.example.mobilesafe", "desc_off");
		tv_title.setText(title);
	}

	public SettingItemView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView(context);
	}
	/**
	 * ������Ͽؼ���������Ϣ
	 */
	public void setDesc(String text){
		tv_desc.setText(text);
	}
}
