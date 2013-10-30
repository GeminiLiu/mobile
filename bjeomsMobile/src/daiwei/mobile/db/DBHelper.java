package daiwei.mobile.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import daiwei.mobile.util.AppConfigs;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "daiwei.db";
	private Context context;
	public DBHelper(Context context) {
		super(context, DB_NAME, null, AppConfigs.DB_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
//		String cache_column = ", type Text NOT NULL, xml_info Text, xml_detail Text, xml_actionbar Text";
//		db.execSQL("CREATE TABLE tb_gd_cache (_id INTEGER PRIMARY KEY AUTOINCREMENT" + cache_column + ", create_time Integer NOT NULL, update_time Integer NOT NULL, deleted Integer NOT NULL DEFAULT 0, user_id Text NOT NULL, extra1 Text, extra2 Text, extra3 Text, extra4 Text, extra5 Text)");
		StringBuffer sb = new StringBuffer("CREATE TABLE [tb_gd_cache] (");
		sb.append("[BaseID] VARCHAR2(50), ");
		sb.append("[TaskID] VARCHAR2(50), ");
		sb.append("[BaseDealOuttime] VARCHAR2(50), ");
		sb.append("[AffectBussType] VARCHAR2(50), ");
		sb.append("[BaseStatus] VARCHAR2(50), ");
		sb.append("[INC_NE_Name] VARCHAR2(50), ");
		sb.append("[SiteName] VARCHAR2(50), ");
		sb.append("[BaseSchema] VARCHAR2(100), ");
		sb.append("[BaseSummary] VARCHAR2(100), ");
		sb.append("[BaseSN] VARCHAR2(50), ");
		
		sb.append("[actionCode] VARCHAR2(50), ");
		sb.append("[actionText] VARCHAR2(50), ");
		sb.append("[serviceCode] VARCHAR2(50), ");
		sb.append("[inputXml] VARCHAR2(50), ");
		sb.append("[hasPic] VARCHAR2(50), ");
		sb.append("[hasRec] VARCHAR2(50), ");
		sb.append("[IsUpload] VARCHAR2(50), ");
		sb.append("[IsWait] VARCHAR2(50))");
	
		db.execSQL(sb.toString());
		
		sb = new StringBuffer("CREATE TABLE [tb_line_position] (");
		sb.append("[TaskID] VARCHAR2(50), ");
		sb.append("[Longitude] VARCHAR2(50), ");
		sb.append("[Latitude] VARCHAR2(50), ");
		sb.append("[LineNo] VARCHAR2(50), ");
		sb.append("[LocalTime] VARCHAR2(50),");
		sb.append("[IsUpLoad] VARCHAR2(50))");
		db.execSQL(sb.toString());	
		
		sb = new StringBuffer("CREATE TABLE [tb_line_site] (");
		sb.append("[TaskID] VARCHAR2(50), ");
		sb.append("[Longitude] VARCHAR2(50), ");
		sb.append("[Latitude] VARCHAR2(50), ");
		sb.append("[LineNo] VARCHAR2(50), ");
		sb.append("[LocalTime] VARCHAR2(50))");
		db.execSQL(sb.toString());	
	}

	public ArrayList<Map<String,String>> executeQuery(String sql,String[] selectionArgs){
		DBHelper helper = new DBHelper(context);
		SQLiteDatabase db = helper.getWritableDatabase();
		Cursor  cursor = db.rawQuery(sql, selectionArgs); 
		ArrayList<Map<String,String>> list = new ArrayList<Map<String,String>>();
		while(cursor.moveToNext()){
			int columnCount = cursor.getColumnCount();
			HashMap<String,String> map = new HashMap<String,String>();
			for(int i = 0 ; i < columnCount; i++)
				map.put(cursor.getColumnName(i), cursor.getString(i));
			list.add(map);
		}
		cursor.close(); 
		db.close();
		return list;
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//db.execSQL("CREATE TABLE tb_cache_base2 (_id INTEGER PRIMARY KEY AUTOINCREMENT)");
	}

}
