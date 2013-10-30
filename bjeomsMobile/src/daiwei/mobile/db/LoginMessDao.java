package daiwei.mobile.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class LoginMessDao {
	private DBHelper helper;
	public LoginMessDao(Context context) {
		helper = new DBHelper(context);
	}



	/**
	 * 增加信息
	 * @param id
	 * @param type
	 * @param version
	 * @param updatetime
	 */
	//insert into mydb (id,type,version,updatetime) values (1,'PS','WPQQQQQQQ','2013.03.08')
	public void add(String id,String type,String version,String updatetime){
		if(find(type)){//增加前查询有没有这条数据 如果有，不增加
			return;
		}
	
		SQLiteDatabase db = helper.getWritableDatabase();
		if(db.isOpen()){
			//db.execSQL("insert into person (name,phone) values ('zhangsan','120')")
			db.execSQL("insert into person (id,type,version,updatetime) values (?,?,?,?)", new Object[]{id,type,version,updatetime});
			db.close();
		}
		
	}
	/**
	 * 查询信息
	 * @param type
	 * @return boolean
	 */
	//select * from mydb where type='PS'
	public boolean find(String type){
		boolean result = false;
		SQLiteDatabase db =  helper.getReadableDatabase();
		if(db.isOpen()){
			Cursor  curosr = db.rawQuery("select * from mydb where type=?", new String[]{type}); 
			if(curosr.moveToFirst()){
				result = true;
			}
			curosr.close(); 
			db.close();
		}
		return result;
	}
	/*//更新
	//update person set phone='119' where name='zhangsan' and phone='120'
	public void update(String name, String phone,String newphone){
		SQLiteDatabase db = helper.getWritableDatabase();
		if(db.isOpen()){
			db.execSQL("update person set phone=? where name=? and phone=?", new Object[]{newphone,name,phone});
			db.close();
		}
	}
	
	//delete from person where name='zhangsan'
	public void delete(String name){
		SQLiteDatabase db = helper.getWritableDatabase();
		if(db.isOpen()){
			db.execSQL("delete from person where name=?", new Object[]{name});
			db.close();
		}
	}*/
	/**
	 * 查询所有数据
	 * 封装到list集合中
	 * @return
	 */
	public List<LoginMess> findAll(){
		List<LoginMess> messes = new ArrayList<LoginMess>();
		SQLiteDatabase db =  helper.getReadableDatabase();
		if(db.isOpen()){
		   Cursor cursor =	db.rawQuery("select * from mydb", null);
			while(cursor.moveToNext()){
				String id = cursor.getString( cursor.getColumnIndex("id"));
				String type = cursor.getString( cursor.getColumnIndex("type"));
				String version = cursor.getString( cursor.getColumnIndex("version"));
				String updatetime=cursor.getString(cursor.getColumnIndex("updatetime"));
				LoginMess mess=new LoginMess(id, type,version,updatetime);
				messes.add(mess);
			}
			cursor.close();
			db.close();
		}
		return messes;
	}
	
}
