package daiwei.mobile.activity;


import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import eoms.mobile.R;
import android.app.Activity;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MicActivity extends Activity {
	private Button buttonStart; // 开始按钮
	private Button buttonStop; // 停止按钮
	private String filePath;//文件路径
	private String baseId;//工单号
	private String baseSN;//声音名
	private String picPathChild;//工单文件夹
	private TextView textViewLuYinState;
	private boolean isMic; // 是否在录音 true 是 false否
	private MediaRecorder mediaRecorder;
	private TextView textViewLuYinFileName;
	private TextView textViewLuYinTime;
	private Handler stepTimeHandler;
	private Runnable mTicker;
	private long startTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mic_activity);
		// 初始化组件
		initView();
		// 初始化数据
		initData();
		// 设置组件
		setView();
		// 设置事件
		setEvent();
	}


	private void initView() {
		// 开始
		buttonStart = (Button) findViewById(R.id.button_start);
		// 停止
		buttonStop = (Button) findViewById(R.id.button_stop);
		textViewLuYinState = (TextView) findViewById(R.id.tv_mic_activity_state);
		textViewLuYinFileName = (TextView) findViewById(R.id.tv_mic_activity_fileName);
		textViewLuYinTime = (TextView) findViewById(R.id.textview_mic_activity_time);
	}
	private void initData() {
		Boolean sdState=Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
		if(!sdState){
			Toast.makeText(MicActivity.this, "请插入SD卡以便存储录音",
					Toast.LENGTH_LONG).show();
			return;
		}
		// 要保存的文件的路径filePath
		File sd = Environment.getExternalStorageDirectory();
		filePath = sd + "/daiwei";
		// 实例化daiwei文件夹
		File dir = new File(filePath);
		if (!dir.exists()) {
			// 如果文件夹不存在 则创建文件夹
			dir.mkdirs();
		}
		//获取baseId
		baseId = getIntent().getStringExtra("baseId");
		baseSN = getIntent().getStringExtra("baseSN");
		if(baseId!=null){
		// 工单号对应下的文件
		picPathChild = filePath + "/" + baseId;
		File fileChild = new File(picPathChild);
		if (!fileChild.exists()) {
			//实例化工单号文件夹
			fileChild.mkdirs();
		}
	}
	}


	private void setView() {
		buttonStart.setEnabled(true);
		buttonStop.setEnabled(false);
	}
	private void setEvent() {
		// 开始按钮
		buttonStart.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				countTime();
				startAudio();
			}
		});
		// 停止按钮
		buttonStop.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				stopAudion();
				stepTimeHandler.removeCallbacks(mTicker);

			}
		});

	}
	/* *********************************************
	 * 
	 * 开始录音
	 */
	private void startAudio(){
		
		SimpleDateFormat    sDateFormat    =   new    SimpleDateFormat("yyyyMMddHHmmss");     
	    String picDate    =  baseSN+"_"+  sDateFormat.format(new    java.util.Date()) ;
		String micName = picPathChild + "/" + picDate + ".amr";// 文件名
		System.out.println("音频文件名："+micName);
		mediaRecorder = new MediaRecorder();
		mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);	// 设置录音的来源为麦克风
		mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
		mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
		mediaRecorder.setOutputFile(micName);
		try {
			mediaRecorder.prepare();
			mediaRecorder.start();
			textViewLuYinState.setText("正在录音中...");
			textViewLuYinFileName.setText(picDate + ".amr");
			buttonStart.setEnabled(false);
			buttonStop.setEnabled(true);
			isMic = true;
		} catch (Exception e) {
			
		}
	}
	/* ********************************************
	 * 
	 * 停止录制
	 */
	private void stopAudion() {
		if (null != mediaRecorder) {
			// 停止录音
			mediaRecorder.stop();
			mediaRecorder.release();
			mediaRecorder = null;
			//返回到上个界面就行了
			setResult(RESULT_OK, null);
			finish();
		
			
//			textViewLuYinState.setText("录音已停止！");
//			// 开始键能够按下
//			buttonStart.setEnabled(true);
//			buttonStop.setEnabled(false);
		}
	}
	/**
	 * ********************************************************
	 * 
	 * 当程序停止的时候
	 */
	@Override
	protected void onStop() {
		if (null != mediaRecorder && isMic) {
			// 停止录音
			mediaRecorder.stop();
			mediaRecorder.release();
			mediaRecorder = null;

			buttonStart.setEnabled(true);
			buttonStop.setEnabled(false);
		}
		super.onStop();
	}
	
	/**
	 * 启动计时线程定时更新
	 * @param time
	 * @return
	 */
	private void countTime(){
		stepTimeHandler = new Handler();
		startTime = System.currentTimeMillis();
		 mTicker = new Runnable() {
			public void run() {
				String content = showTimeCount(System
						.currentTimeMillis() - startTime);
				System.out.println("content==========" + content);
				textViewLuYinTime.setText(content);
				long now = SystemClock.uptimeMillis();
				long next = now + (1000 - now % 1000);
				stepTimeHandler.postAtTime(mTicker, next);
			}
		};
		// 启动计时线程，定时更新
		mTicker.run();
	}
	// 计时器格式
		// 时间计数器，最多只能到99小时，如需要更大小时数需要改改方法
		public String showTimeCount(long time) {
			if (time >= 360000000) {
				return "00:00:00";
			}
			String timeCount = "";
			long hourc = time / 3600000;
			String hour = "0" + hourc;
			hour = hour.substring(hour.length() - 2, hour.length());

			long minuec = (time - hourc * 3600000) / (60000);
			String minue = "0" + minuec;
			minue = minue.substring(minue.length() - 2, minue.length());

			long secc = (time - hourc * 3600000 - minuec * 60000) / 1000;
			String sec = "0" + secc;
			sec = sec.substring(sec.length() - 2, sec.length());
			timeCount = hour + ":" + minue + ":" + sec;
			return timeCount;
		}

}
