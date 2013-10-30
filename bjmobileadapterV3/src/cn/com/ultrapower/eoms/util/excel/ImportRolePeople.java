package cn.com.ultrapower.eoms.util.excel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.ultrapower.ultrawf.control.design.UserManager;
import cn.com.ultrapower.ultrawf.share.FormatString;
import cn.com.ultrapower.system.database.GetDataBase;
import cn.com.ultrapower.system.database.IDataBase;
import cn.com.ultrapower.system.remedyop.RemedyDBOp;

/**
 * 导入角色人员
 * @author Administrator
 *
 */
public class ImportRolePeople {
	
	public String importData(HttpServletRequest request, HttpServletResponse response){
		StringBuffer sb = new StringBuffer();
		ResultSet rs =null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		try {
			RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
			String groupTable=m_RemedyDBOp.GetRemedyTableName("Group");
			String getGroupSql = "select c106 from " + groupTable + " where c8 =?";
			//取出excel的各行各单元格的信息，置入Content对象中
			Content content = (Content) ControllCenter.executeImortExcel(
					request, response);
			
			if (content != null && content.getContent() != null
					&& !content.getContent().isEmpty()) {
				Iterator iterator = content.getContent().iterator();
				//依次取出Content对象中每一行的信息
				while (iterator.hasNext()) {
					Row row = (Row)iterator.next();
					List<Cell> arrayList = row.getRow();
					String role = arrayList.get(0).getCaption().trim();
					String users = arrayList.get(1).getCaption();
					// 查询具体数据
					conn = m_dbConsole.getConn();
					pstmt= conn.prepareStatement(getGroupSql);
					pstmt.setString(1, role);
					rs = pstmt.executeQuery();
					if(rs.next()){//如果存在这样的组
						String groupID = FormatString.CheckNullString(rs.getString(1)).trim();
						List<String> addUsers = getUserLoginNameByUserNames(users.split(","));
						UserManager uManager = new UserManager();
						uManager.addUserGroup(addUsers, groupID);
						sb.append("<br>");
						sb.append(addUsers.toString());
						sb.append("=============>");
						sb.append(role);
					}else{
						sb.append("<br>没有找到");
						sb.append(role);
					}
					if (rs != null)
						rs.close();
					if (pstmt != null)
						pstmt.close();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}
			m_dbConsole.closeConn();
		}return sb.toString();
	}
	
	
	private List<String> getUserLoginNameByUserNames(String [] userFullNames){
		String [] userLoginNames = new String[userFullNames.length];
		for(int i = 0 ; i < userFullNames.length ; i ++){
			userLoginNames[i] = getUserLoginNameByUserName(userFullNames[i]);
		}
		return Arrays.asList(userLoginNames);
	}
	
	
	private String getUserLoginNameByUserName(String userfullName){
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		String strTblName=m_RemedyDBOp.GetRemedyTableName("UltraProcess:SysUser");
		String sql = "select t.c630000001 from " + strTblName + " t where t.c630000003 = '" + userfullName + "'";
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		Statement stm=null;
		ResultSet resultSet =null;
		String userLoginName = "";
		try {
			stm = m_dbConsole.GetStatement();
			resultSet = m_dbConsole.executeResultSet(stm,sql);
			if(resultSet.next()){
				userLoginName = resultSet.getString(1);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}
			try {
				if (stm != null)
					stm.close();
			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}
			m_dbConsole.closeConn();
		}
		return userLoginName;
	}
}
