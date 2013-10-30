package daiwei.mobile.animal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class ViewRotate implements OnClickListener{
	private ImageView imageview;
	private View bg;
//	private DisplayNextView displayNextView;
	private Context context;
	private View convertView;
	private LayoutInflater mInflater;
	private boolean now_zhengfan;
	private View container_bg;
	
	public ViewRotate(Context context, View convertView,
			LayoutInflater mInflater){
		this.context = context;
		this.convertView = convertView;
		this.mInflater = mInflater;
		now_zhengfan = true;
//		AnimationUtils.loadAnimation(context, R.anim.my_alpha_action);

	}
	private void init() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
