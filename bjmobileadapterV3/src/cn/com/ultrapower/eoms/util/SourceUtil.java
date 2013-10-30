package cn.com.ultrapower.eoms.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.com.ultrapower.eoms.common.basedao.GeneralDAO;

public class SourceUtil extends GeneralDAO {
	
	
	
	/**
	 * 通过资源英文名找到对应的资源ID出现异常
	 * @param sourceName 资源中文名
	 * @return 资源的ID
	 * @author LY
	 */
	public int getSourceIdBySourceEnName(String sourceName){
		Session session;
		Transaction tx;
		Connection conn = null;
		Statement st  = null;
		ResultSet rs = null;
		int sourceId = 1;
		try {
			session = GeneralDAO.currentSession();
			tx = session.beginTransaction();
			conn = session.connection();
			try {
				st = conn.createStatement();
				StringBuffer sb = new StringBuffer();
				sb.append("select source_id from sourceConfig where source_name = '");
				sb.append(sourceName);
				sb.append("'");
				Log.logger.info(sb.toString());
				rs = st.executeQuery(sb.toString());
				tx.commit();
				while(rs.next()){
					sourceId = rs.getInt("source_id");
				}
			} catch (SQLException e){
				e.printStackTrace();
			}
		} catch (HibernateException e) {
			Log.logger.error("通过资源英文名找到对应的资源ID出现异常");
			e.printStackTrace();
		}finally{
			try {
				if(rs != null){
					rs.close();
				}
				if(st != null){
					st.close();
				}
				if(conn != null){
					conn.close();
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return sourceId;
	}
	
	public static void main(String[]args){
		SourceUtil a = new SourceUtil();
		System.out.println(a.getSourceIdBySourceEnName("xinjianzhishiyi"));
	}
}
