package cn.com.ultrapower.ultrawf.share;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import cn.com.ultrapower.system.database.*;

public class AttacheInfo {

	/**
	 * @see 根据BaseID，BaseSchema，attacheID获取到附件的列表，
	 * @param BaseID
	 * @param BaseSchema
	 * @param attacheID
	 */
		public static List getAttacheInfoList(String BaseID,String BaseSchema,String attacheID){
			/**
			 * 设计：
			 * 	根据一个sql获取到这些信息
			 * 	压入到一个HashMap的List
			 * 
			 */
			
			String Sql = "select C650000008 as filename,to_char(TO_DATE('19700101','yyyymmdd') + C650000007/86400 + 1/3),'yyyy-mm-dd HH24:mi:ss') as uptime," +
					"C650000005 as uper,C650000009 as detail from t85 att " +
					"where att.c650000001 = ? and att.c650000002 =? and att.c650000014 = ?";
			IDataBase db = GetDataBase.createDataBase();
			
	   		java.sql.PreparedStatement pstmt= null;
	   		ResultSet rs = null;
	   		List list = new ArrayList();
	   		try{
	   			pstmt = db.getConn().prepareStatement(Sql);
	   			pstmt.setString( 1,BaseID);
	   			pstmt.setString( 2,BaseSchema);
	   			pstmt.setString( 3,attacheID);
	   			rs = pstmt.executeQuery();
	   			
	   			while(rs.next()){
	   				HashMap map = new HashMap();
	   				map.put("filename",rs.getString("filename"));
	   				map.put("uptime",rs.getString("uptime"));
	   				map.put("uper",rs.getString("uper"));
	   				map.put("detail",rs.getString("detail"));
	   				list.add(map);
	   			}
	   			rs.close();
	   			pstmt.close();
	   		}catch(Exception e){
	   			e.printStackTrace();
	   		}finally{
	   			try{
	   				if(rs!=null){
	   					rs.close();
	   				}
	   			}catch(Exception e_rs){
	   				e_rs.printStackTrace();
	   		   	}
	   			try{
	   				if(pstmt!=null){
	   					pstmt.close();
	   				}
	   			}catch(Exception e_st){
	   				e_st.printStackTrace();
	   		   	}
	   			try{
	   				if(db!=null){
	   					db.closeConn();
	   				}
	   			}catch(Exception e_con){
	   				e_con.printStackTrace();
	   		   	}
	   		}
	   		return list;
		}
}
