package daiwei.mobile.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import eoms.mobile.R;
import daiwei.mobile.animal.Rotate3D;


public class XjZfzLineActivity extends BaseActivity {

	private ImageView point;
	private ImageButton imageButton1;
	private ImageButton imageButton2;
	private ImageButton imageButton3;
	private ImageButton imageButton4;
	private ImageButton imageButton5;
	private LayoutInflater inflater;
	private LinearLayout ll_map;
	private LinearLayout ll_list;
	private LinearLayout ll_button;
//	private View bg;
//	private View frame;
	
	private float degree = (float) 0.0;
	private int mCenterX ;
	private int mCenterY ;
	
	@Override
	public void getTvtf() {
		 this.tl =  new String[]{"井0704","井0703","井0702","井0701",
					"杆0704","杆0703","杆0702","杆0701",
					"石0704","石0703","石0702","石0701"};
		 this.vl = new String[]{"点坐标:388,321\n是否关键点:否","点坐标:388,321\n是否关键点:否","点坐标:388,321\n是否关键点:否","点坐标:388,321\n是否关键点:否",
				   "点坐标:388,321\n是否关键点:否","点坐标:388,321\n是否关键点:否","点坐标:388,321\n是否关键点:否","点坐标:388,321\n是否关键点:否",
				   "点坐标:388,321\n是否关键点:否","点坐标:388,321\n是否关键点:否","点坐标:388,321\n是否关键点:否","点坐标:388,321\n是否关键点:否"};
		 this.tp = new int[]{4,4,4,4,
				4,4,4,4,
				4,4,4,4};
		 this.fr = new int[]{99,99,99,99,
				99,99,99,99,
				99,99,99,99};

		
		this.titleText="线路巡检";
		this.gdType="线段：浑南-本溪(高速)";
		this.gdId="";
		this.detailId=R.layout.xj_detail_line_bg;
//	View parent=findViewById(R.id.ll_friends);
//	RelativeLayout rlchild=(RelativeLayout) findViewById(R.id.gd_bottom);
	}

	@Override
	public void afterOnCreate() {
		point = (ImageView) findViewById(R.id.iv_point);
		imageButton1 = (ImageButton) findViewById(R.id.imageButton1);
		imageButton2 = (ImageButton) findViewById(R.id.imageButton2);
		imageButton3 = (ImageButton) findViewById(R.id.imageButton3);
		imageButton4 = (ImageButton) findViewById(R.id.imageButton4);
		imageButton5 = (ImageButton) findViewById(R.id.imageButton5);
		ll_list = (LinearLayout) findViewById(R.id.ll_sv);
		ll_map = (LinearLayout) findViewById(R.id.ll_sv1);//地图
//		bg = (ViewGroup)findViewById(R.id.container);
//		frame = findViewById(R.id.framelayout);
		
		inflater = LayoutInflater.from(this);
		
		android.util.DisplayMetrics dm = new android.util.DisplayMetrics();
		dm = getResources().getDisplayMetrics();
		mCenterX = dm.widthPixels / 2;
		mCenterY = dm.heightPixels / 2;
//		point.setOnClickListener(new OnClickListener() {
//			boolean flag = true;
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
////				Intent intent=new Intent(getApplicationContext(),MapActivity.class);
////				startActivity(intent);
//				/*if(ll_list.getVisibility()==View.VISIBLE){
//					applyRotation(0, 0, 90);
//					
//					ll_map.setVisibility(View.VISIBLE);//展示地图
//					ll_list.setVisibility(View.GONE);
//					point.setImageResource(R.drawable.icon_list);
//				}else{
//					ll_map.setVisibility(View.GONE);//隐藏地图
//					ll_list.setVisibility(View.VISIBLE);
//					point.setImageResource(R.drawable.icon_map);
//				}*/
//		
//				if(flag){
//					Rotate3D rotate3d = new Rotate3D(degree , -90 , 0, mCenterX, mCenterY);
//					Rotate3D rotate3d3 = new Rotate3D( 90 + degree,0,0, mCenterX, mCenterY);
//				
//				rotate3d.setDuration(300);
//				rotate3d3.setDuration(300);
//				
//				ll_list.startAnimation(rotate3d);
//				ll_map.startAnimation(rotate3d3);
//				
//				ll_map.setVisibility(View.VISIBLE);//展示地图
//				ll_list.setVisibility(View.GONE);
//				point.setImageResource(R.drawable.icon_list);
//	
//				flag = false;
//				}else{
//				
//					Rotate3D rotate3d = new Rotate3D(degree , 90 , 0, mCenterX, mCenterY);
//					Rotate3D rotate3d3 = new Rotate3D( - 90 + degree,0,0, mCenterX, mCenterY);
//					
//					rotate3d.setDuration(300);
//					rotate3d3.setDuration(300);
//					
//					ll_list.startAnimation(rotate3d3);
//					ll_map.startAnimation(rotate3d);
//					
//					ll_map.setVisibility(View.GONE);//隐藏地图
//					ll_list.setVisibility(View.VISIBLE);
//					point.setImageResource(R.drawable.icon_map);
//			
//					flag = true;	
//				}
//				
//			}
//		});
//	ll_map.setOnClickListener(new OnClickListener() {
//		
//		private ImageButton normal;
//
//		@Override
//		public void onClick(View v) {
//			// TODO Auto-generated method stub
//			final View dialogview=inflater.inflate(R.layout.xjmapdialog, null);
//			normal = (ImageButton) dialogview.findViewById(R.id.xj_button_on);
//			ll_button=(LinearLayout) dialogview.findViewById(R.id.ll_button);
//			ll_button.setOnClickListener(new OnClickListener(
//					) {
//				Boolean b=true;
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					if(b){
//					normal.setImageResource(R.drawable.switch_enormal);
//					b=false;
//					}
//					else{
//						normal.setImageResource(R.drawable.switch_normal);
//						b=true;
//					}
// 
//				}
//			});
//			normal.setOnClickListener(new OnClickListener(
//					) {
//				Boolean b=true;
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					if(b){
//					normal.setImageResource(R.drawable.switch_enormal);
//					b=false;
//					}
//					else{
//						normal.setImageResource(R.drawable.switch_normal);
//						b=true;
//					}
// 
//				}
//			});
//			
//			AlertDialog.Builder builder=new AlertDialog.Builder(XjZfzLineActivity.this);
//			
//			builder.setView(dialogview);
//			builder.setTitle("当前位置及线路巡检项填写");
//			builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//				
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					
//				
//					dialog.dismiss();
//			
//				}
//			}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
//				
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					dialog.dismiss();
//				}
//			}).show();
//			
//		}
//	});
//	imageButton1.setOnClickListener(new OnClickListener() {
//		
//		@Override
//		public void onClick(View v) {
//			changeColor();
//			imageButton1.setBackgroundResource(R.drawable.startxjon);
//			
//		}
//	});
//	imageButton2.setOnClickListener(new OnClickListener() {	
//		
//		@Override
//		public void onClick(View v) {
//			changeColor();
//			imageButton2.setBackgroundResource(R.drawable.endxjon);
//			
//		}
//	});
//	imageButton3.setOnClickListener(new OnClickListener() {	
//		
//		@Override
//		public void onClick(View v) {
//			changeColor();
//			imageButton3.setBackgroundResource(R.drawable.stopxjon);
//			
//		}
//	});
//	imageButton4.setOnClickListener(new OnClickListener() {	
//	
//	@Override
//	public void onClick(View v) {
//		changeColor();
//		imageButton4.setBackgroundResource(R.drawable.xj_detail_cameraon);
//		
//		Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//		startActivityForResult(intentFromCapture, 1);
//		
//	}
//});
//	imageButton5.setOnClickListener(new OnClickListener() {	
//	
//	@Override
//	public void onClick(View v) {
//		changeColor();
//		imageButton5.setBackgroundResource(R.drawable.xj_detail_threeon);
//		
//	}
//});
	}
	public void changeColor() {
		imageButton1.setBackgroundResource(R.drawable.startxj);
		imageButton2.setBackgroundResource(R.drawable.endxj);
		imageButton3.setBackgroundResource(R.drawable.stopxj);
		imageButton4.setBackgroundResource(R.drawable.xj_detail_camera);
		imageButton5.setBackgroundResource(R.drawable.xj_detail_three);
	}

/*	
private void applyRotation(int position,float start,float end){
	float centerX=bg.getWidth()/2.0f;
	float centerY=bg.getHeight()/2.0f;
	Rotate3d1 rotation=new Rotate3d1(start, end, centerX, centerY, 310.0f, false);
	rotation.setDuration(500);
	rotation.setFillAfter(false);
	rotation.setInterpolator(new AccelerateInterpolator());
//	rotation.setAnimationListener(new DisplayNextView(position, true));
	bg.startAnimation(rotation);
//	AlphaAnimation alphaAnim = new AlphaAnimation(0, 1);

}*/
	
}