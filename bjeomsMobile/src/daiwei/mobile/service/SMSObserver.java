package daiwei.mobile.service;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Handler;
import android.os.Message;

public class SMSObserver extends ContentObserver {
	public static final String TAG = "SMSObserver";
	private static final String FLAG = "'%PS%'";
	private static final String PHONENUMBER = "123456";
	private static final String[] PROJECTION = new String[] { SMS._ID,// 0
			SMS.TYPE,// 1
			SMS.ADDRESS,// 2
			SMS.BODY,// 3
			SMS.DATE,// 4
			SMS.THREAD_ID,// 5
			SMS.READ,// 6
			SMS.PROTOCOL // 7
	};
	private static final String SELECTION = SMS._ID + " > %s" +
	// " and " + SMS.PROTOCOL + " = null" +
	// " or " + SMS.PROTOCOL + " = " + SMS.PROTOCOL_SMS + ")" +
			" and (" + SMS.TYPE + " = " + SMS.MESSAGE_TYPE_INBOX + " or " + SMS.TYPE + " = " + SMS.MESSAGE_TYPE_SENT + ")";
	private static final int COLUMN_INDEX_ID = 0;
	private static final int COLUMN_INDEX_TYPE = 1;
	private static final int COLUMN_INDEX_PHONE = 2;
	private static final int COLUMN_INDEX_BODY = 3;
	private static final int COLUMN_INDEX_PROTOCOL = 7;
	private static final int MAX_NUMS = 10;
	private static int MAX_ID = 0;
	private ContentResolver mResolver;
	private Handler mHandler;
	
	public SMSObserver(ContentResolver contentResolver, Handler handler) {
		super(handler);
		this.mResolver = contentResolver;
		this.mHandler = handler;
	}
	
	public SMSObserver(Handler handler) {
		super(handler);
	}
	
	@Override
	public void onChange(boolean selfChange) {
		super.onChange(selfChange);
		Cursor cursor = mResolver.query(SMS.CONTENT_URI, PROJECTION,
		null, null, null);
		int id, type, protocol;
		String phone, body;
		Message message;
		MessageItem item;
		int iter = 0;
		boolean hasDone = false;
		if (cursor.moveToFirst()) {
			long threadid = cursor.getLong(cursor.getColumnIndex(SMS.THREAD_ID));
			id = cursor.getInt(COLUMN_INDEX_ID);
			type = cursor.getInt(COLUMN_INDEX_TYPE);
			phone = cursor.getString(COLUMN_INDEX_PHONE);
			body = cursor.getString(COLUMN_INDEX_BODY);
			protocol = cursor.getInt(COLUMN_INDEX_PROTOCOL);
			if (!phone.contains("18301543391"))
				return;
			item = new MessageItem();
			item.setId(id);
			item.setType(type);
			item.setPhone(phone);
			item.setBody(body);
			item.setProtocol(protocol);
			message = new Message();
			message.obj = item;
			mHandler.sendMessage(message);
			
			mResolver.delete(SMS.CONTENT_URI, SMS.THREAD_ID+"=" + threadid, null);//删除短信
		}
	}
}
