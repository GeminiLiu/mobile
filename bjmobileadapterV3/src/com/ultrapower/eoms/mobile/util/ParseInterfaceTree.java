/*package cn.com.ultrapower.ultrawf.control.config;

import java.util.List;
import cn.com.ultrapower.eoms.user.userinterface.RoleGrandInterface;
import cn.com.ultrapower.eoms.user.userinterface.bean.ElementInfoBean;
import cn.com.ultrapower.eoms.user.userinterface.bean.SendScopePram;


public class ParseInterfaceTree {

	*//**
	 * 得到树html字符串
	 * @param _schema
	 * @param _userLoginName
	 * @return
	 *//*
	 public String getHtml(String _schema,String _userLoginName,String strHeadTable,String _Sendscopetype,String _customerflag){
		 int begin = 0;
		 StringBuffer html = new StringBuffer();
	 
		 html.append("var tree = new WebFXTree(\""+strHeadTable+"\");\n");
		 //基础组接口调用
		 RoleGrandInterface _roleGrandInterface = new RoleGrandInterface();
		 SendScopePram sendscopepram = new SendScopePram();
		 sendscopepram.setcustomerflag(_customerflag);
		 sendscopepram.setSendscopetype(_Sendscopetype);
		 sendscopepram.setSourceName(_schema);
		 sendscopepram.setUserLoginName(_userLoginName);
         //通过登陆人信息得到可操作组信息List
		 List elements = _roleGrandInterface.getGroupUserTree(sendscopepram);
		 String strUserNode = "";
	 	 String checkBoxString = "";
	 	 if(!_Sendscopetype.equals("user_role")){
			 for(int i=0;i<elements.size();i++){
				begin++;
			    strUserNode = "";
			    checkBoxString = "";	
				ElementInfoBean elementinfo = (ElementInfoBean)elements.get(i);
				//封装树html信息
			    if(elementinfo.getElementflag().equals("0")){
			    	checkBoxString = "<input type='checkbox' class='inputCheckBox' name='group' id='"+elementinfo.getElementid()+"'" +
					" value='"+"GroupID"+"#"+elementinfo.getElementid()+ "'>";		
			        strUserNode = strUserNode + "<span class='itemstyle' id='Span_"+elementinfo.getElementid()+"' onclick='OnClickCheckBox(\\\""+elementinfo.getElementid()+"\\\",\\\"Span_"+elementinfo.getElementid()+"\\\")';>";
			        strUserNode = strUserNode + checkBoxString + elementinfo.getElementname();
			        strUserNode = strUserNode + "</span>";	
			    	html.append("tree.add(new WebFXLoadTreeItem(\""+elementinfo.getElementname()+"\",\"OrganTree_True_LoadXML.jsp?baseschema=" + _schema + "&username=" + _userLoginName + "&sendscopetype=" + _Sendscopetype + "&customerflag=" + _customerflag + "&id="+elementinfo.getElementid()+"\",\"\",\"\",\"\",\"\",\"\",\""+strUserNode+"\",\"\",\"0\",\""+begin+"\"));");
				}else{
					checkBoxString = "<input type='checkbox' class='inputCheckBox' name='group' id='"+elementinfo.getElementid()+"'" +
					" value='"+"UserID"+"#"+elementinfo.getElementid() + "'>";		
			        strUserNode = strUserNode + "<span class='itemstyle' id='Span_"+elementinfo.getElementid()+"' onclick='OnClickCheckBox(\\\""+elementinfo.getElementid()+"\\\",\\\"Span_"+elementinfo.getElementid()+"\\\")';>";
			        strUserNode = strUserNode + checkBoxString + elementinfo.getElementname();
			        strUserNode = strUserNode + "</span>";	
			    	html.append("tree.add(new WebFXTreeItem(\""+elementinfo.getElementname()+"\",\"\",\"\",\"\",\"\",\"\",\""+strUserNode+"\",\"\",\"1\",\""+begin+"\"))");
				}

			 }		 

	 	 }
	 	 else{
	 		for(int i=0;i<elements.size();i++){
				begin++;
			    strUserNode = "";
			    checkBoxString = "";	
				ElementInfoBean elementinfo = (ElementInfoBean)elements.get(i);
				//封装树html信息
			    if(elementinfo.getElementflag().equals("0")){
			    	checkBoxString = "";		
			        strUserNode = strUserNode + "<span class='itemstyle' id='Span_"+elementinfo.getElementid()+"' onclick='OnClickCheckBox(\\\""+elementinfo.getElementid()+"\\\",\\\"Span_"+elementinfo.getElementid()+"\\\")';>";
			        strUserNode = strUserNode + checkBoxString + elementinfo.getElementname();
			        strUserNode = strUserNode + "</span>";	
			    	html.append("tree.add(new WebFXLoadTreeItem(\""+elementinfo.getElementname()+"\",\"OrganTree_True_LoadXML.jsp?baseschema=" + _schema + "&username=" + _userLoginName + "&sendscopetype=" + _Sendscopetype + "&customerflag=" + _customerflag + "&id="+elementinfo.getElementid()+"\",\"\",\"\",\"\",\"\",\"\",\""+strUserNode+"\",\"\",\"0\",\""+begin+"\"));");
				}else{
					checkBoxString = "<input type='checkbox' class='inputCheckBox' name='group' id='"+elementinfo.getElementid()+"'" +
					" value='"+"UserID"+"#"+elementinfo.getElementid() + "'>";		
			        strUserNode = strUserNode + "<span class='itemstyle' id='Span_"+elementinfo.getElementid()+"' onclick='OnClickCheckBox(\\\""+elementinfo.getElementid()+"\\\",\\\"Span_"+elementinfo.getElementid()+"\\\")';>";
			        strUserNode = strUserNode + checkBoxString + elementinfo.getElementname();
			        strUserNode = strUserNode + "</span>";	
			    	html.append("tree.add(new WebFXTreeItem(\""+elementinfo.getElementname()+"\",\"\",\"\",\"\",\"\",\"\",\""+strUserNode+"\",\"\",\"1\",\""+begin+"\"))");
				}
			 }
	 	 }
		 html.append(" document.write(tree);\n");	 
		 return html.toString();		 
	 }
	 
	 *//**
	  * 得到树XML字符串
	  * @param _schema
	  * @param _userLoginName
	  * @param _parentId
	  * @return
	  *//*
	 public String getXML(String _schema,String _userLoginName,String _parentId,String _Sendscopetype,String _customerflag){
		 StringBuffer html = new StringBuffer();
		 html.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		 html.append("<tree>");
		 //基础组接口调用
		 RoleGrandInterface _roleGrandInterface = new RoleGrandInterface();
		 SendScopePram sendscopepram = new SendScopePram();
		 sendscopepram.setcustomerflag(_customerflag);
		 sendscopepram.setSendscopetype(_Sendscopetype);
		 sendscopepram.setSourceName(_schema);
		 sendscopepram.setUserLoginName(_userLoginName);
		 sendscopepram.setNodeParentid(_parentId);
		 //通过登陆人信息得到可操作组信息List
		 List elements = _roleGrandInterface.getGroupUserTree(sendscopepram);
		 String strUserNode = "";
	 	 String checkBoxString = "";	
	 	 if(!_Sendscopetype.equals("user_role")){
		 	 if (elements.size()>0)
		 	 {
			 		String strUserNode_0 = "";
			 		//strUserNode_0 = strUserNode_0 + "||span class='itemstyle' id='Span_Select_all_"+_parentId+"' onclick='';%%";
			 		strUserNode_0 = strUserNode_0 + "||a class='itemstyle' onclick='AllSelectedChildNodes(@@"+_parentId+"@@)'%%选择全部||##a%%";
			 		//strUserNode_0 = strUserNode_0 + "||##span%%";			 
				//html.append("<tree text=\"选择本节点全部\" src=\"\" funpram=\""+strUserNode_0+"\" target=\"\" ifPerson=\"0\"  />");
					String strUserNode_1 = "";
					//strUserNode_1 = strUserNode_1 + "||span class='itemstyle' id='Span_Cancel_all_"+_parentId+"' onclick='';%%";
					strUserNode_1 = strUserNode_1 + "||a class='itemstyle' onclick='ClearSelectedChildNodes(@@"+_parentId+"@@)'%%取消全部";
					//strUserNode_1 = strUserNode_1 + "||##span%%";		
				html.append("<tree text=\"选择本节点全部,取消本节点全部\" src=\"\" funpram=\""+strUserNode_0 + "  " + strUserNode_1+" \" target=\"\" ifPerson=\"0\"  />");
		 	 }
			 for(int i=0;i<elements.size();i++){
				 
				  strUserNode = "";
			 	  checkBoxString = "";	
				  ElementInfoBean elementinfo = (ElementInfoBean)elements.get(i);
	              //封装树html信息
				  if(elementinfo.getElementflag().equals("0")){		
				    	checkBoxString = "||input type='checkbox' class='inputCheckBox' name='group' id='"+_parentId+"_"+elementinfo.getElementid()+"'" +
						" value='"+"GroupID"+"#"+elementinfo.getElementid() + "'%%";		
				        strUserNode = strUserNode + "||span class='itemstyle' id='Span_"+_parentId+"_"+elementinfo.getElementid()+"' onclick='OnClickCheckBox(@@"+_parentId+"_"+elementinfo.getElementid()+"@@,@@Span_"+_parentId+"_"+elementinfo.getElementid()+"@@)';%%";
				        strUserNode = strUserNode + checkBoxString + elementinfo.getElementname();
				        strUserNode = strUserNode + "||##span%%";	
			        	html.append("<tree text=\""+elementinfo.getElementname()+"\" src=\"OrganTree_True_LoadXML.jsp?baseschema=" + _schema + "&amp;username=" + _userLoginName + "&amp;sendscopetype=" + _Sendscopetype + "&amp;customerflag=" + _customerflag + "&amp;id="+elementinfo.getElementid()+"\" funpram=\""+strUserNode+"\" target=\"\" ifPerson=\"0\"  />");
				  }else{
				    	checkBoxString = "||input type='checkbox' class='inputCheckBox' name='group' id='"+_parentId+"_"+elementinfo.getElementid()+"'" +
						" value='"+"UserID"+"#"+elementinfo.getElementid() + "'%%";		
				        strUserNode = strUserNode + "||span class='itemstyle' id='Span_"+_parentId+"_"+elementinfo.getElementid()+"' onclick='OnClickCheckBox(@@"+_parentId+"_"+elementinfo.getElementid()+"@@,@@Span_"+_parentId+"_"+elementinfo.getElementid()+"@@)';%%";
				        strUserNode = strUserNode + checkBoxString + elementinfo.getElementname();
				        strUserNode = strUserNode + "||##span%%";	
				    	html.append("<tree text=\""+elementinfo.getElementname()+"\" funpram=\""+strUserNode+"\" ifPerson=\"1\" />");
				  }
			  }
	 	 }
	 	 else{
	 		 if (elements.size()>0)
		 	 {
			 		String strUserNode_0 = "";
			 		//strUserNode_0 = strUserNode_0 + "||span class='itemstyle' id='Span_Select_all_"+_parentId+"' onclick='';%%";
			 		strUserNode_0 = strUserNode_0 + "||a class='itemstyle' onclick='AllSelectedChildNodes(@@"+_parentId+"@@)'%%选择全部||##a%%";
			 		//strUserNode_0 = strUserNode_0 + "||##span%%";			 
				//html.append("<tree text=\"选择本节点全部\" src=\"\" funpram=\""+strUserNode_0+"\" target=\"\" ifPerson=\"0\"  />");
					String strUserNode_1 = "";
					//strUserNode_1 = strUserNode_1 + "||span class='itemstyle' id='Span_Cancel_all_"+_parentId+"' onclick='';%%";
					strUserNode_1 = strUserNode_1 + "||a class='itemstyle' onclick='ClearSelectedChildNodes(@@"+_parentId+"@@)'%%取消全部";
					//strUserNode_1 = strUserNode_1 + "||##span%%";		
				html.append("<tree text=\"选择本节点全部,取消本节点全部\" src=\"\" funpram=\""+strUserNode_0 + "  " + strUserNode_1+" \" target=\"\" ifPerson=\"0\"  />");
		 	 }
			 for(int i=0;i<elements.size();i++){
				 
				  strUserNode = "";
			 	  checkBoxString = "";	
				  ElementInfoBean elementinfo = (ElementInfoBean)elements.get(i);
	              //封装树html信息
				  if(elementinfo.getElementflag().equals("0")){		
				    	checkBoxString = "";		
				        strUserNode = strUserNode + "||span class='itemstyle' id='Span_"+_parentId+"_"+elementinfo.getElementid()+"' onclick='OnClickCheckBox(@@"+_parentId+"_"+elementinfo.getElementid()+"@@,@@Span_"+_parentId+"_"+elementinfo.getElementid()+"@@)';%%";
				        strUserNode = strUserNode + checkBoxString + elementinfo.getElementname();
				        strUserNode = strUserNode + "||##span%%";	
			        	html.append("<tree text=\""+elementinfo.getElementname()+"\" src=\"OrganTree_True_LoadXML.jsp?baseschema=" + _schema + "&amp;username=" + _userLoginName + "&amp;sendscopetype=" + _Sendscopetype + "&amp;customerflag=" + _customerflag + "&amp;id="+elementinfo.getElementid()+"\" funpram=\""+strUserNode+"\" target=\"\" ifPerson=\"0\"  />");
				  }else{
				    	checkBoxString = "||input type='checkbox' class='inputCheckBox' name='group' id='"+_parentId+"_"+elementinfo.getElementid()+"'" +
						" value='"+"UserID"+"#"+elementinfo.getUserid() + "'%%";		
				        strUserNode = strUserNode + "||span class='itemstyle' id='Span_"+_parentId+"_"+elementinfo.getElementid()+"' onclick='OnClickCheckBox(@@"+_parentId+"_"+elementinfo.getElementid()+"@@,@@Span_"+_parentId+"_"+elementinfo.getElementid()+"@@)';%%";
				        strUserNode = strUserNode + checkBoxString + elementinfo.getElementname();
				        strUserNode = strUserNode + "||##span%%";	
				    	html.append("<tree text=\""+elementinfo.getElementname()+"\" funpram=\""+strUserNode+"\" ifPerson=\"1\" />");
				  }
			  }
	 	 }
		 html.append("</tree>");
		 System.out.println(html.toString());
		 return html.toString();		 
	 }
}
*/

package com.ultrapower.eoms.mobile.util;

import java.util.List;

import com.ultrapower.eoms.mobile.interfaces.assigntree.model.AssignTreeInputModel;
//import cn.com.ultrapower.eoms.user.userinterface.RoleGrandInterface;
import cn.com.ultrapower.eoms.user.userinterface.bean.ElementInfoBean;
import cn.com.ultrapower.eoms.user.userinterface.bean.SendScopePram;
import cn.com.ultrapower.ultrawf.share.FormatString;


public class ParseInterfaceTree {

	 
	 /**
	  * 得到树XML字符串

	  * @param _schema
	  * @param _userLoginName
	  * @param _parentId
	  * @return
	  */
	 public String getXML(String _schema,String _userLoginName,String _parentId,String _Sendscopetype,String _customerflag,AssignTreeInputModel inputModel){
		 int multi = inputModel.getMultiSelect();
		 String selectObjs = inputModel.getSelectObjs();
		 String teamSelectFlag = FormatString.CheckNullString(selectObjs).contains("team")?"0":"1";
		 String personSelectFlag = FormatString.CheckNullString(selectObjs).contains("person")?"0":"1";
		 StringBuffer html = new StringBuffer();
		 html.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		 html.append("<tree>");
		 //基础组接口调用

		 RoleGrandInterface _roleGrandInterface = new RoleGrandInterface();
		 SendScopePram sendscopepram = new SendScopePram();
		 sendscopepram.setcustomerflag(_customerflag);
		 sendscopepram.setSendscopetype(_Sendscopetype);
		 sendscopepram.setSourceName(_schema);
		 sendscopepram.setUserLoginName(_userLoginName);
		 sendscopepram.setNodeParentid(_parentId);
		 
		 List elements = _roleGrandInterface.getGroupUserTree(sendscopepram);
		 String strUserNode = "";
	 	 String checkBoxString = "";	
		 for(int i=0;i<elements.size();i++){
			 
			  strUserNode = "";
		 	  checkBoxString = "";	
			  ElementInfoBean elementinfo = (ElementInfoBean)elements.get(i);
              //封装树html信息
			  if(elementinfo.getElementflag().equals("0")){		
				  //组或者部门
			    	checkBoxString = teamSelectFlag;		
		        	html.append("<item text=\""+elementinfo.getElementname()+"\" nocheckbox=\""+checkBoxString+"\" id=\""+elementinfo.getElementid()+"\" open=\"\" child=\""+elementinfo.getHasuser()+"\" im0=\"dwgroup.png\" im1=\"dwgroup.png\" im2=\"dwgroup.png\" >");
		        	html.append(" <userdata name=\"text\">"+elementinfo.getElementname()+"</userdata>");
		        	html.append(" <userdata name=\"type\">team</userdata>");
		        	html.append(" <userdata name=\"id\">"+elementinfo.getElementid()+"</userdata>");
		        	html.append("</item>");
			  }else{
				  //人员
				  	checkBoxString = personSelectFlag;
				  	html.append("<item text=\""+elementinfo.getElementname()+"\" nocheckbox=\""+checkBoxString+"\" id=\""+elementinfo.getElementid()+"\" open=\"\" child=\""+elementinfo.getHasuser()+"\" im0=\"dwperson.png\" im1=\"dwperson.png\" im2=\"dwperson.png\" >");
		        	html.append(" <userdata name=\"text\">"+elementinfo.getElementname()+"</userdata>");
		        	html.append(" <userdata name=\"type\">person</userdata>");
		        	html.append(" <userdata name=\"id\">"+elementinfo.getElementid()+"</userdata>");
		        	html.append(" <userdata name=\"loginname\">"+elementinfo.getElementid()+"</userdata>");
		        	html.append("</item>");
			  }
		  }
		 html.append("</tree>");
		 System.out.println(html.toString());
		 return html.toString();		 
	 }
}
