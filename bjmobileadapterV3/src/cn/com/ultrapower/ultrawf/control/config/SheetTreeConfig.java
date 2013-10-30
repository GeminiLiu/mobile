package cn.com.ultrapower.ultrawf.control.config;

import java.util.*;

import cn.com.ultrapower.ultrawf.control.design.ProcessRoleManager;
import cn.com.ultrapower.ultrawf.control.design.TplDesignManager;
import cn.com.ultrapower.ultrawf.models.config.BaseCategoryClassModel;
import cn.com.ultrapower.ultrawf.models.config.BaseCategoryModel;
import cn.com.ultrapower.ultrawf.models.design.ProcessRoleModel;
import cn.com.ultrapower.ultrawf.models.process.TplBaseModel;
import cn.com.ultrapower.system.database.GetDataBase;
import cn.com.ultrapower.system.sqlquery.ReBuildSQL;
import cn.com.ultrapower.system.sqlquery.parameter.RDParameter;
import cn.com.ultrapower.system.table.RowSet;
import cn.com.ultrapower.system.table.Table;
 
public class SheetTreeConfig
{
	private static String strSheetTree = "";
	
	public static String getStrSheetTree()
	{
		if(strSheetTree.equals(""))
		{
			setSheetTree();
		}
		return strSheetTree;
	}

	public static void setStrSheetTree(String strSheetTree)
	{
		SheetTreeConfig.strSheetTree = strSheetTree;
	}

	public static void setSheetTree()
	{
		
		String tmpStr =  getSheetString() ;
		

		StringBuffer xmlString = new StringBuffer("<?xml version=\"1.0\"?><tree id=\"0\">");
		xmlString.append("	<item id=\"WaitingDeal\" text=\"待办工单\"> ");
		xmlString.append("		<userdata name=\"url\">../manageprocess/WaitingDeal_All.jsp</userdata><userdata name=\"openmode\">left</userdata> ");
		xmlString.append("		<item id=\"MyWaitingDeal\" text=\"我待办的工单\"> ");
		xmlString.append("			<userdata name=\"url\">../manageprocess/WaitingDeal_User.jsp?type=user</userdata><userdata name=\"openmode\">left</userdata> ");
		xmlString.append(tmpStr);
		xmlString.append("		</item> ");
		xmlString.append("		<item id=\"MyDepWaitingDeal\" text=\"本部门待办的工单\"> ");
		xmlString.append("			<userdata name=\"url\">../manageprocess/WaitingDeal_User.jsp?type=group</userdata><userdata name=\"openmode\">left</userdata> ");
		xmlString.append(tmpStr);
		xmlString.append("		</item> ");
		xmlString.append("		<item id=\"MyCompWaitingDeal\" text=\"本公司待办的工单\"> ");
		xmlString.append("			<userdata name=\"url\">../manageprocess/WaitingDeal_User.jsp?type=company</userdata><userdata name=\"openmode\">left</userdata> ");
		xmlString.append(tmpStr);
		xmlString.append("		</item> ");
		xmlString.append("	</item> ");
		xmlString.append("	<item id=\"Dealed\" text=\"已办工单\"> ");
		xmlString.append("		<userdata name=\"url\">../manageprocess/Dealed_Deal.jsp?type=all</userdata><userdata name=\"openmode\">left</userdata> ");
		xmlString.append("		<item id=\"MyCreated\" text=\"我建立的工单\"> ");
		xmlString.append("			<userdata name=\"url\">../manageprocess/Dealed_Deal.jsp?type=create</userdata><userdata name=\"openmode\">left</userdata> ");
		xmlString.append(tmpStr);
		xmlString.append("		</item> ");
		xmlString.append("		<item id=\"MyDealed\" text=\"我处理的工单\"> ");
		xmlString.append("			<userdata name=\"url\">../manageprocess/Dealed_Deal.jsp?type=deal</userdata><userdata name=\"openmode\">left</userdata> ");
		xmlString.append(tmpStr);
		xmlString.append("		</item> ");
		xmlString.append("	</item> ");
		xmlString.append("	<item id=\"MyDepDealed\" text=\"本部门的工单\"> ");
		xmlString.append("		<userdata name=\"url\">../manageprocess/Dealed_MyGroup.jsp?type=group</userdata><userdata name=\"openmode\">left</userdata> ");
		xmlString.append("	</item> ");
		xmlString.append("	<item id=\"MyCompDealed\" text=\"本公司的工单\"> ");
		xmlString.append("		<userdata name=\"url\">../manageprocess/Dealed_MyGroup.jsp?type=company</userdata><userdata name=\"openmode\">left</userdata> ");
		xmlString.append("	</item> ");
//		xmlString.append("<item id=\"BaseTypeManage\" text=\"工单分类管理\">");
//		xmlString.append("<userdata name=\"url\">../manageflow/BaseCategoryList.jsp</userdata><userdata name=\"openmode\">left</userdata>");
		xmlString.append(tmpStr);

		xmlString.append("</tree>");
		
		strSheetTree = xmlString.toString();
	}
	private static String getSheetString(){
		BaseCategoryClassManage bccm = new BaseCategoryClassManage();
		BaseCategoryManage bcm = new BaseCategoryManage();
		ProcessRoleManager prManager = new ProcessRoleManager();
		TplDesignManager tdManager = new TplDesignManager();
		
		List<BaseCategoryClassModel> bccList = bccm.getBaseCategoryClassList();
		List<BaseCategoryModel> bcList = bcm.getAllList();
		List<ProcessRoleModel> prList = prManager.getProcessRoleList();
		List<TplBaseModel> tbList = tdManager.getTplBaseList();
		
		StringBuffer xmlString = new StringBuffer(2000);
		for(Iterator<BaseCategoryClassModel> it_class = bccList.iterator(); it_class.hasNext();)
		{
			BaseCategoryClassModel bccModel = it_class.next();
			xmlString.append("<item id=\"class_" + bccModel.getBaseCategoryClassID() + "\" text=\"" + bccModel.getBaseCategoryClassName() + "\">");
			xmlString.append("<userdata name=\"url\">../manageflow/BaseCategoryList.jsp?clsasname=" + bccModel.getBaseCategoryClassCode() + "</userdata><userdata name=\"openmode\">left</userdata>");
			for(Iterator<BaseCategoryModel> it_cate = bcList.iterator(); it_cate.hasNext();)
			{
				BaseCategoryModel bcModel = it_cate.next();
				if(bcModel.getBaseCategoryClassCode() == bccModel.getBaseCategoryClassCode())
				{
					xmlString.append("<item id=\"cate_" + bcModel.getBaseCategorySchama() + "\" text=\"" + bcModel.getBaseCategoryName() + "\">");
					xmlString.append("<userdata name=\"url\">#</userdata><userdata name=\"openmode\">left</userdata>");
					xmlString.append("</item>");
				}
			}
			xmlString.append("</item>");
		}
		
		return xmlString.toString();
	}
	 /**
	  * 得到树XML字符串

	  * @param _schema
	  * @param _userLoginName
	  * @param _parentId
	  * @return
	  */
	 public static String getXMLRole(String xx, String jspName, String searchText){
		 StringBuffer html = new StringBuffer();
		 RowSet rowSet = getRowSet(xx, jspName);
		 for(int i=0;i<rowSet.length();i++){
			 if(rowSet.get(i).getString("COL").indexOf("电路调度工单")!=-1){
				 String tree = rowSet.get(i).getString("COL");
				 tree = tree.substring(0, tree.indexOf("url:"));
				 tree += "url:../../common/boco/boco_dldd.jsp\";";
//				 tree += "url:http://www.baidu.com\";";
				 html.append(tree);
			 }else{
				 html.append(rowSet.get(i).getString("COL") + "\n");
			 }
		 }
		 return html.toString();		 
	 }
	 public static RowSet getRowSet(String xx, String jspName)
		{
			RDParameter rdp = new RDParameter();
			ReBuildSQL reBuildSQL = new ReBuildSQL("SheetTree.Sheetquery.tree", rdp, "");
			String sql = reBuildSQL.getReBuildSQL();
			sql = sql.replaceAll("#xx#", "'"+xx+"'");
			if(jspName!=null && !"".equals(jspName)){
				sql = sql.replaceAll("#data#", "url:"+jspName+"!!baseschema='||C650000002||'");
				sql = sql.replaceAll("#data_class#", "url:"+jspName+"!!baseclass='||to_char(C650000002)||'");
			}
			Table table = new Table(GetDataBase.createDataBase(),"");
			RowSet rowSet = table.executeQuery(sql, null, 0, 0, 0);
			return rowSet;
		}
	 
}
