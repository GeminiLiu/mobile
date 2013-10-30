package daiwei.mobile.activity;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import eoms.mobile.R;


public class XjMessageActivity extends BaseActivity {

	ArrayList<Integer> MultiChoiceID = new ArrayList<Integer>();
	final String[] mItems = { "基站机房", "BTS", "NodeB", "RRU", "铁塔", "天馈线" };
	private AlertDialog.Builder builder;
	int mSingleChoiceID = -1;
	private ImageButton imageButton1;
	private ImageButton imageButton2;

	@Override
	public void getTvtf() {
		this.tl = new String[] { "基站机房", "站址经度", "站址纬度", "所属城市", "所属县区",
				"站点类型", "应急类型", "覆盖类型", "地理特征", "蜂窝类型", "所属代维公司", "所属代维中心",
				"所属代维组", "BTS", "OMC中网元名称", "所属BSC", "通信系统标识", "基站等级", "设备型号",
				"设备供应商", "NodeB", "NodeB名称", "NodeB频段", "NodeB频段", "OMC中网元名称",
				"所属RNC", "设备供应商", "RRU", "OMC中网元名称", "设备型号", "设备供应商", "铁塔",
				"平台数量", "已用平台数量", "铁塔类型", "塔身高（米）", "铁塔厂家", "产权", "建成时间",
				"天馈线", "天线名称", "总俯仰角", "天线类型", "挂高", "设备型号", "设备厂家", "方位角",
				"电子下倾角", "机械下倾角", "水平半功率角", "天面", "馈线数量", "是否智能天线", "智能天线阵元数目",
				"天线增益", "智能天线版本", "智能天线权值标准化标", "天线间距或半径", "天线尺寸", "天线频段",
				"馈线长度", "馈线厂家", "馈线总损耗（dB）", "馈线类型" };
		this.vl = new String[] { "", "1", "2", "3", "4", "5", "6", "7", "8",
				"9", "10", "11", "12", "", "", "", "", "", "", "", "", "", "",
				"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
				"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
				"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
				"", "", "", };
		this.tp = new int[] { 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1,
				1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1,
				1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
				1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
		this.fr = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1,
				1, 1, 1, 1, 0, 2, 2, 2, 2, 2, 2, 0, 3, 3, 3, 0, 4, 4, 4, 4, 4,
				4, 4, 0, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5,
				5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5 };

		this.titleText = "资源信息";
		this.gdType = "铁岭.昌图.检察院基站";
		this.gdId = "";
		this.detailId = R.layout.xj_detail_resource_bg;
	}

	@Override
	public void afterOnCreate() {
		imageButton1 = (ImageButton) findViewById(R.id.imageButton1);
		imageButton2 = (ImageButton) findViewById(R.id.imageButton2);
		builder = new AlertDialog.Builder(getApplicationContext());

		imageButton2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CreatDialog(0);// 单选

			}
		});

		imageButton1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CreatDialog(1);// 多选
			}
		});
		/*
		 * RelativeLayout lay = (RelativeLayout)findViewById(R.id.gd_bottom);
		 * lay.setVisibility(View.GONE);
		 */
	}

	private void showDialog(String str) {
		new AlertDialog.Builder(XjMessageActivity.this).setMessage(str).show();
	}

	public void CreatDialog(int id) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				XjMessageActivity.this);
		switch (id) {
			case 0:// 单选
				mSingleChoiceID = -1;
				builder.setIcon(R.drawable.scanningicon);
				builder.setTitle("资源信息条形码扫描");
				builder.setSingleChoiceItems(mItems, 0,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								mSingleChoiceID = whichButton;
								showDialog("你选择的id为" + whichButton + " , "
										+ mItems[whichButton]);
							}
						});
				builder.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								if (mSingleChoiceID > 0) {
									showDialog("你选择的是" + mSingleChoiceID);
								}
							}
						});
				builder.setNegativeButton("取消",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
	
							}
						});
	
				break;

		case 1:
			MultiChoiceID.clear();
			builder.setIcon(R.drawable.checkicon);
			builder.setTitle("资源信息校对");
			builder.setMultiChoiceItems(mItems, new boolean[] { false, false,
					false, false, false, false },
					new DialogInterface.OnMultiChoiceClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton, boolean isChecked) {
							if (isChecked) {
								MultiChoiceID.add(whichButton);
								showDialog("你选择的id为" + whichButton + " , "
										+ mItems[whichButton]);
							} else {
								MultiChoiceID.remove(whichButton);
							}

						}
					});
			builder.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							String str = "";
							int size = MultiChoiceID.size();
							for (int i = 0; i < size; i++) {
								str += mItems[MultiChoiceID.get(i)] + ", ";
							}
							showDialog("你选择的是" + str);
						}
					});
			builder.setNegativeButton("取消",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {

						}
					});
			break;
		}
		builder.create().show();
	}
}
