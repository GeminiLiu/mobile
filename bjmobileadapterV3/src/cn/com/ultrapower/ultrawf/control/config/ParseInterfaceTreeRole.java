package cn.com.ultrapower.ultrawf.control.config;

import java.util.Random;

import cn.com.ultrapower.ultrawf.control.design.ProcessRoleManager;
import cn.com.ultrapower.system.database.GetDataBase;
import cn.com.ultrapower.system.sqlquery.ReBuildSQL;
import cn.com.ultrapower.system.sqlquery.parameter.RDParameter;
import cn.com.ultrapower.system.table.Row;
import cn.com.ultrapower.system.table.RowSet;
import cn.com.ultrapower.system.table.Table;


public class ParseInterfaceTreeRole {

	/**
	 * 得到树html字符串
	 * @param _schema
	 * @param _userLoginName
	 * @return
	 */
	 public String getHtml(String baseID, String roleID, String searchText){
		int begin = 1;
		StringBuffer html = new StringBuffer();
		String strHeadTable = null;
		strHeadTable =  "roletree";		
		html.append("var tree = new WebFXTree(\""+strHeadTable+"\");\n");
		ProcessRoleManager prManager = new ProcessRoleManager();
		RowSet rowSet = prManager.getChildRoleRowSet(baseID, roleID);
		String strUserNode = "";
	 	if(rowSet.length() > 0)
		{
			begin++;
		    strUserNode = "";
		    String pRoleName = rowSet.get(0).getString("ParentRoleName");
			//封装树html信息
	        strUserNode = strUserNode + "<span class='itemstyle' id='parentRole'>";
	        strUserNode = strUserNode + pRoleName;
	        strUserNode = strUserNode + "</span>";	
	        html.append("var item_root = new WebFXLoadTreeItem(\"pRoleName\",\"OrganTree_Role_LoadXML.jsp?type=role&baseid=" + baseID + "&roleid=" + roleID + "&id=parentRole&st=" + searchText + "\",\"\",\"\",\"\",\"\",\"\",\""+strUserNode+"\",\"\",\"0\",\""+begin+"\");");
	    	html.append("tree.add(item_root);");
	        //html.append("item_root.expandChildren();");
		}		 
		html.append(" document.getElementById('divTree').innerHTML =tree;\n");	 
		return html.toString();		 
	 }
	 
	 /**
	  * 得到树XML字符串
	  * @param _schema
	  * @param _userLoginName
	  * @param _parentId
	  * @return
	  */
	 public String getXMLRole(String baseID, String roleID, String searchText){
		 StringBuffer html = new StringBuffer();
		 html.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		 html.append("<tree>");
		 
		 ProcessRoleManager prManager = new ProcessRoleManager();
		 RowSet rowSet = prManager.getChildRoleRowSet(baseID, roleID);
		 
		 String strUserNode = "";
		 String checkBoxString = "";
		 for(int i = 0; i < rowSet.length(); i++)
		 {
			strUserNode = "";
		 	checkBoxString = "";	
			Row row = rowSet.get(i);
			String roleName = row.getString("RoleName");
			if(searchText.equals("") || roleName.indexOf(searchText) > -1)
			{
	            //封装树html信息
		    	checkBoxString = "||input type='checkbox' class='inputCheckBox' name='group' id='" + row.getString("RoleID") + "' value='GroupID#" + row.getString("RoleID") + "'%%";		
		        strUserNode = strUserNode + "||span class='itemstyle' id='Span_"+ row.getString("RoleID") + "' onclick='OnClickCheckBox(@@"+row.getString("RoleID")+"@@,@@Span_"+row.getString("RoleID")+"@@)'%%";
		        strUserNode = strUserNode + checkBoxString + row.getString("RoleName");
		        strUserNode = strUserNode + "||##span%%";	
	        	html.append("<tree text=\"" + row.getString("RoleName") + "\" src=\"OrganTree_Role_LoadXML.jsp?id=" + row.getString("RoleID") + "\" funpram=\""+strUserNode+"\" target=\"\" ifPerson=\"0\"  />");
			}
		 }
		 html.append("</tree>");
		 //System.out.println(html.toString());
		 return html.toString();		 
	 }
	 
	 /**
	  * 得到树XML字符串

	  * @param _schema
	  * @param _userLoginName
	  * @param _parentId
	  * @return
	  */
	 public String getXMLUser(String groupID){
		 StringBuffer html = new StringBuffer();
		 html.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		 html.append("<tree>");
		 
		 RDParameter rdp = new RDParameter();
		 rdp.addIndirectPar("groupID", groupID, 4);
		 ReBuildSQL reBuildSQL = new ReBuildSQL("rolequery.ChildRole.UserTree", rdp, "");
		 String sql = reBuildSQL.getReBuildSQL();
		 Table table = new Table(GetDataBase.createDataBase(),"");
		 RowSet rowSet = table.executeQuery(sql, null, 0, 0, 0);
		 
		 String strUserNode = "";
		 String checkBoxString = "";
		 for(int i = 0; i < rowSet.length(); i++)
		 {
			strUserNode = "";
		 	checkBoxString = "";	
			Row row = rowSet.get(i);
			//封装树html信息
			Random ran = new Random(System.currentTimeMillis());
			String tempID = String.valueOf(ran.nextInt(3));
	    	checkBoxString = "||input type='checkbox' class='inputCheckBox' name='user' id='" + tempID + row.getString("LoginName") + "' value='UserID#"+row.getString("LoginName") + "'%%";		
	        strUserNode = strUserNode + "||span class='itemstyle' id='Span_" + tempID + row.getString("LoginName") + "' onclick='OnClickCheckBox(@@" + tempID+row.getString("LoginName")+"@@,@@Span_" + tempID + row.getString("LoginName")+"@@)'%%";
	        strUserNode = strUserNode + checkBoxString + row.getString("FullName");
	        strUserNode = strUserNode + "||##span%%";	
	    	html.append("<tree text=\"" + row.getString("FullName") + "\" funpram=\""+strUserNode+"\" ifPerson=\"1\" />");
		 }
		 html.append("</tree>");
		 //System.out.println(html.toString());
		 return html.toString();		 
	 }
}
