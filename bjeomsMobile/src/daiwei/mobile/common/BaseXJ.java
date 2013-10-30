package daiwei.mobile.common;

import java.util.ArrayList;
import java.util.List;

import eoms.mobile.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

public abstract class BaseXJ{
public View act;
public String[] tl = {};// 标题
public String[] vl = {};// 内容
public int[] tp = {};// 标题类型 0：常规信息 1：主题 2:spinner模板 3:editText模板 4：开关 5:无主题文本
public int[] fr = {};// frame 99直接挂在主模版上
public String titleText = "";//工单主题
public String gdType = "";//工单类型  --故障工单，通用工单，发电工单，投诉工单
public String gdId = "";//任务ID
public int detailId = R.layout.gd_detail_bg;
// 0 frame
public void onCreate(View v) {
	this.act = v;
	getTvtf();
	afterOnCreate();
	BuildActivity();
}
public abstract void getTvtf();
public abstract void afterOnCreate();
private void BuildActivity() {
	
		List<LinearLayout> frames = new ArrayList<LinearLayout>();

		LinearLayout lyt = (LinearLayout) act.findViewById(R.id.gd_frame_lv1);
		LayoutInflater inflater = (LayoutInflater) act.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		TextView tvTitle = (TextView) act.findViewById(R.id.tv_title);
		tvTitle.setText(titleText);
		
		
		if(!gdType.equals("")){
		TextView deal_tv1 = (TextView) act.findViewById(R.id.deal_tv1);
		deal_tv1.setText(gdType);
		
		TextView deal_content = (TextView)act.findViewById(R.id.deal_content);
		deal_content.setText(gdId);
		
		if(this.gdId.equals("")){
			deal_content.setVisibility(View.GONE);
		}
		
		
		}
		
		if(this.detailId == R.layout.yh_detail_bg)
			frames.add(lyt);
		
		
		
		for (int i = 0; i < tl.length; i++) {
			if (tp[i] == 0) {
				View view1 = inflater.inflate(R.layout.gd_detail_item_lv, null,
						true);
				lyt.addView(view1);
				TextView tx = (TextView) view1.findViewById(R.id.detail_tv2);
				tx.setText(tl[i]);
				
				if(vl[i].length() >0 ){
					ImageView tv3 = (ImageView) view1.findViewById(R.id.detail_tv3);
					tv3.setBackgroundResource(R.drawable.normal_all);
					if(vl[i].equals("(有异常)"))
						tv3.setBackgroundResource(R.drawable.enormal);
				}
				
				final ImageView add1 = (ImageView) view1.findViewById(R.id.add1);
				final LinearLayout MessageN = (LinearLayout) view1.findViewById(R.id.ll_normalMessage);//内容
				
				MessageN.setVisibility(View.GONE);
				add1.setImageResource(R.drawable.add);
				
				RelativeLayout normalMessage = (RelativeLayout) view1.findViewById(R.id.ll_normalMessage1);	//标题加号
				normalMessage.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (MessageN.getVisibility() == View.GONE) {
							MessageN.setVisibility(View.VISIBLE);
							add1.setImageResource(R.drawable.decrease);
						} else {
							MessageN.setVisibility(View.GONE);
							add1.setImageResource(R.drawable.add);
						}
					}
				});
				frames.add((LinearLayout) view1.findViewById(R.id.ll_normalMessage));
			} else if (tp[i] == 1) {
				View view1 = inflater.inflate(R.layout.gd_detail_item_text,
						null, true);
				TextView tvtl = (TextView) view1.findViewById(R.id.tv_tl);
				tvtl.setText(tl[i]);
				TextView tvvl = (TextView) view1.findViewById(R.id.tv_vl);
				tvvl.setText(vl[i]);
				
				if(fr[i] == 99)
					lyt.addView(view1);
				else
					frames.get(fr[i]).addView(view1);
			} else if (tp[i] == 2) {
				View view1 = inflater.inflate(R.layout.spinner, null, true);
				TextView tvtl = (TextView) view1.findViewById(R.id.tv_spinner);
				tvtl.setText(tl[i]);

				spinnerList((Spinner) view1.findViewById(R.id.spinner_reason),
						vl[i].split(","));
				frames.get(fr[i]).addView(view1);
			} else if (tp[i] == 3) {
				View view1 = inflater.inflate(R.layout.edittext, null, true);
				TextView tvtl = (TextView) view1.findViewById(R.id.tv_editText);
				tvtl.setText(tl[i]);
				EditText tvvl = (EditText) view1.findViewById(R.id.et_editText);
				tvvl.setHint(vl[i]);
				frames.get(fr[i]).addView(view1);
			} else if (tp[i] == 4) {
				View view1 = inflater.inflate(R.layout.switchon, null, true);
				TextView tvtl = (TextView) view1.findViewById(R.id.tv_switch);
				tvtl.setText(tl[i]);
				TextView tvvl = (TextView) view1.findViewById(R.id.tv_switch1);
				tvvl.setText(vl[i]);
				
				LinearLayout ll_content=(LinearLayout) view1.findViewById(R.id.ll_content);
				
				final ImageButton button = (ImageButton) view1.findViewById(R.id.xj_button_on);	
				ll_content.setOnClickListener(new OnClickListener() {
					boolean flag = true;
					@Override
					public void onClick(View v) {
						if(flag){
							button.setImageResource(R.drawable.switch_enormal);
							flag = false;
							AlertDialog.Builder builder=new AlertDialog.Builder(act.getContext());
//							builder.setIcon(R.drawable.bottom_receiveon);
//							builder.setTitle("签收");
							builder.setMessage("是否存在隐患需要上报?");
							builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									dialog.dismiss();
//									Intent intent=new Intent(getApplicationContext(),YhAddActivity.class);
//									startActivity(intent);
									
								}
							}).setNegativeButton("否", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									dialog.dismiss();
								}
							}).show();

							
						}else{
							button.setImageResource(R.drawable.switch_normal);
							flag = true;
						}
					}
				});
				button.setOnClickListener(new OnClickListener() {
					boolean flag = true;
					@Override
					public void onClick(View v) {
						if(flag){
							button.setImageResource(R.drawable.switch_enormal);
							flag = false;
							AlertDialog.Builder builder=new AlertDialog.Builder(act.getContext());
//							builder.setIcon(R.drawable.bottom_receiveon);
//							builder.setTitle("签收");
							builder.setMessage("是否存在隐患需要上报?");
							builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									dialog.dismiss();
//									Intent intent=new Intent(getApplicationContext(),YhAddActivity.class);
//									startActivity(intent);
									
								}
							}).setNegativeButton("否", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									dialog.dismiss();
								}
							}).show();
						}else{
							button.setImageResource(R.drawable.switch_normal);
							flag = true;
						}
					}
				});
				frames.get(fr[i]).addView(view1);
			} else if (tp[i] == 5) {
				View view1 = inflater.inflate(R.layout.gd_detail_textview,
						null, true);
				TextView tvtl = (TextView) view1.findViewById(R.id.tv_vl);
				tvtl.setText(vl[i]);
				if(fr[i] == 99)
					lyt.addView(view1);
				else
					frames.get(fr[i]).addView(view1);
			}
		}
	}

	protected void spinnerList(Spinner spn, String[] str) {
		ArrayAdapter<String> choicesAdapter = new ArrayAdapter<String>(act.getContext(),
				R.layout.myspinner, str);
		choicesAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spn.setPrompt("请选择");
		spn.setAdapter(choicesAdapter);
	}
}