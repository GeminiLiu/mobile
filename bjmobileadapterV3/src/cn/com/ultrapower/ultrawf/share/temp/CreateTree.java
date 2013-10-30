package cn.com.ultrapower.ultrawf.share.temp;

import java.io.*;
import java.util.List;
import org.jdom.*;
import org.jdom.input.*;

public class CreateTree {
	int begin=0;
	private File file;
	private Document doc;
	public CreateTree(){}
	public CreateTree(String _filePath) {
		file = new File("../webapps/ROOT/WEB-INF" + File.separator + "tree.xml");
		try {
			SAXBuilder builder = new SAXBuilder();
			doc = builder.build(file);
			doc.getRootElement();
		} catch (Exception e) {
			System.err.println("XML解析初始化失败");
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param _ifOpen
	 * @param _objName
	 * @return
	 */
	public String getConfigContant(String _title,String _ifOpen,String _objName) {
		StringBuffer scriptStr = new StringBuffer();
		List headgroups = null;
	    Element element = null;
		element = doc.getRootElement();//treeInfo
		headgroups = element.getChildren(_objName);//group
		scriptStr.append(_objName+" = new dTree('"+_objName+"');\n");
		scriptStr.append(_objName+".add(0,-1,'"+_title+"',null,'','main');\n");
			
		for(int i=0;i<headgroups.size();i++){
			element = (Element)headgroups.get(i);
			scriptStr.append(this.getGroup(element,"0",_ifOpen,_objName));
	    }	
		scriptStr.append("  document.write("+_objName+");");
		scriptStr.append(_objName+"."+_ifOpen+"All();");
		return scriptStr.toString();
	}
	


	/**
	 * 得到group属性的javascript
	 * @param element
	 * @param _index 是否显示子菜单
	 * @return
	 */
	public String getGroup(Element element,String _parentId,String _ifOpen,String _objName){
		begin++;
		StringBuffer scriptStr = new StringBuffer();
		TreeModel treeModel = null;
		List first = element.getChildren();//group中的信息	
		treeModel = this.getModel(element.getChild("property"),"group");	
		String script = "alert(\""+treeModel.getGroupName()+"\");";	 	
	 	String strGroupNode = "<span onclick="+script+">"+treeModel.getGroupName()+"</span>";
		scriptStr.append(_objName+".add("+treeModel.getGroupId()+","+_parentId+",'"+strGroupNode+"','','','main','','','false','"+begin+"');\n");
		_parentId = treeModel.getGroupId();
		
		for(int i=0;i<first.size();i++){
			element = (Element)first.get(i);
			if(element.getName().equals("user")){
		    	scriptStr.append(this.getUser(element,_parentId,_ifOpen,_objName));
         	}else if(element.getName().equals("group")){  
      	    	scriptStr.append(this.getGroup(element,_parentId,_ifOpen,_objName));  
            }
		}			
		return scriptStr.toString();
	}
	
	
	/**
	 * 得到user属性的javascript
	 * @param element
	 * @return
	 */
	public String getUser(Element element,String _parentId,String _ifOpen,String _objName){
		StringBuffer scriptStr = new StringBuffer();
		TreeModel treeModel = null;
	 	treeModel = this.getModel(element.getChild("property"),"user");
	 	String script = "alert(\""+treeModel.getUserName()+"\")";
	 	String strUserNode = "<span onclick="+script+">"+treeModel.getUserName()+"</span>";
		scriptStr.append(_objName+".add("+treeModel.getUserId()+","+_parentId+",'"+strUserNode+"','','','main','','','"+_ifOpen+"');\n"); 
		return scriptStr.toString();
	}
	
	
	
	/**
	 * 
	 * @param element property层element
	 * @param _flag
	 * @return
	 */
	public TreeModel getModel(Element element,String _flag){
		TreeModel TreeModel = new TreeModel();
		if("user".equals(_flag)){
			TreeModel.setUserId(element.getChildText("userID")==null?"":element.getChildText("userID"));
			TreeModel.setUserLoginName(element.getChildText("loginname")==null?"":element.getChildText("loginname"));
			TreeModel.setUserName(element.getChildText("username")==null?"":element.getChildText("username"));			
		}else if("group".equals(_flag)){
			TreeModel.setGroupId(element.getChildText("groupid")==null?"":element.getChildText("groupid"));
			TreeModel.setGroupName(element.getChildText("groupname")==null?"":element.getChildText("groupname"));			
		}
		return TreeModel;		
	}
}
