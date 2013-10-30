package daiwei.mobile.view;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import eoms.mobile.R;
import daiwei.mobile.activity.JsMessResultActivity;



public class MessSearchMain {
	private Context context;
	public View ms;
	private MyViewGroup mg;
	private Spinner spinner1;
	private Spinner spinner2;
	private Spinner spinner3;
	private Spinner spinner4;
	private ImageButton imageButton1;
	
	public MessSearchMain(final Context context,MyViewGroup mg){
		this.context=context;
		this.mg=mg;
		ms=LayoutInflater.from(context).inflate(R.layout.jssearch, null);
		ms.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(MyViewGroup.showright){
					return true;
				}else{
					return false;
				}
			}
		});
		spinner1 = (Spinner) ms.findViewById(R.id.Spinner01);
		spinner2 = (Spinner) ms.findViewById(R.id.Spinner02);
		spinner3 = (Spinner) ms.findViewById(R.id.Spinner03);
		spinner4 = (Spinner) ms.findViewById(R.id.Spinner04);
		
		LinearLayout  ll=(LinearLayout) ms.findViewById(R.id.footsearch2);
		imageButton1 = (ImageButton) ms.findViewById(R.id.imageButton1);
		imageButton1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				imageButton1.setBackgroundResource(R.drawable.bottom_searchon);
				//显示资源检索结果
				Intent intent=new Intent(context,JsMessResultActivity.class);
				context.startActivity(intent);
				
				
			}
		});
		ll.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(context,JsMessResultActivity.class);
				context.startActivity(intent);
			}
		});
		spinnerList(spinner1, new String[]{"空间资源","传输线路资源","基站设备及配套资源","集客及家客资源","铁塔及天馈资源","直放站视分资源"});
		spinnerList(spinner2, new String[]{"区域","站点","机房"});
		spinnerList(spinner3, new String[]{"全部","附近500米","附近1000米","附近2000米"});
		spinnerList(spinner4, new String[]{"资源","任务"});
	}
	
	private void spinnerList(Spinner spn, String[] str) {
		ArrayAdapter<String> choicesAdapter=new ArrayAdapter<String>(this.context,R.layout.myspinner,str);
		choicesAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spn.setPrompt("请选择");
		spn.setAdapter(choicesAdapter);
	}
	
	
}
