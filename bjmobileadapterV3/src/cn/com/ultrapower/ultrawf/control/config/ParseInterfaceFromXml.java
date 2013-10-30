package cn.com.ultrapower.ultrawf.control.config;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.*;

import cn.com.ultrapower.ultrawf.control.config.OrganModel;

public class ParseInterfaceFromXml {
	
	private	int intOrganbegin = 0;
	public 	ParseInterfaceFromXml(){}
	 
	public String ParseInterfaceOrgan(String _strXml,String _title,String _ifOpen,String _objName)
	{
        try{
			SAXBuilder builder = new SAXBuilder();
			StringReader m_StringReader = new StringReader(_strXml);
			Document doc = builder.build(m_StringReader);
			doc.getRootElement();
			
			return getOrganConfigContant(doc,_title,_ifOpen,_objName);
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * ������֯��
	 * @param _ifOpen
	 * @param _objName
	 * @return
	 * @throws JDOMException 
	 */
	public String getOrganConfigContant(Document doc,String _title,String _ifOpen,String _objName)
	{
		StringBuffer scriptStr = new StringBuffer();
		List headgroups = null;
	    Element element = null;
		element = doc.getRootElement();//treeInfo
		headgroups = element.getChildren(_objName);//group
		scriptStr.append(_objName+" = new dTree('"+_objName+"');\n");
		scriptStr.append(_objName+".add(0,-1,'"+_title+"',null,'','main');\n");
			
		for(int i=0;i<headgroups.size();i++){
			element = (Element)headgroups.get(i);
			scriptStr.append(this.getOrganGroup(element,"0",_ifOpen,_objName));
	    }	
		scriptStr.append("  document.write("+_objName+");");
		scriptStr.append(_objName+"."+_ifOpen+"All();");
		System.out.print("-------------------"+scriptStr.toString()+"-------------------");
		return scriptStr.toString();
	}

	/**
	 * �õ���֯���Model
	 * @param element property��element
	 * @param _flag
	 * @return
	 */
	private OrganModel getOrganModel(Element element,String _flag){
		OrganModel m_OrganModel = new OrganModel();
		if("user".equals(_flag)){
			m_OrganModel.setUserId(element.getChildText("userID")==null?"":element.getChildText("userID"));
			m_OrganModel.setUserLoginName(element.getChildText("loginname")==null?"":element.getChildText("loginname"));
			m_OrganModel.setUserName(element.getChildText("username")==null?"":element.getChildText("username"));			
		}else if("group".equals(_flag)){
			m_OrganModel.setGroupId(element.getChildText("groupid")==null?"":element.getChildText("groupid"));
			m_OrganModel.setGroupName(element.getChildText("groupname")==null?"":element.getChildText("groupname"));			
		}
		return m_OrganModel;		
	}
	
	/**
	 * �õ�group���Ե�javascript
	 * @param element
	 * @param _index �Ƿ���ʾ�Ӳ˵�

	 * @return
	 */
	private String getOrganGroup(Element element,String _parentId,String _ifOpen,String _objName){
		intOrganbegin++;
		StringBuffer scriptStr = new StringBuffer();
		
		OrganModel treeModel = null;
		List first = element.getChildren();//group�е���Ϣ	
		treeModel = this.getOrganModel(element.getChild("property"),"group");	
	    //String script = "OnClickGroupNode(\""+treeModel.getGroupId()+"\",\""+treeModel.getGroupName()+"\")";
		//String script = " selectOrgan()";
	 	//String strGroupNode = checkBoxString+"<span class=\"itemstyle\" id=\"Span_"+treeModel.getGroupId()+"\" onclick=OnClickCheckBox("+treeModel.getGroupId()+",\"Span_"+treeModel.getGroupId()+"\");>";
	 	String strGroupNode 	= "";
	 	String checkBoxString 	= "";
		checkBoxString = "<input type=\"checkbox\" class=\"inputCheckBox\" name=\"group\" id=\""+treeModel.getGroupId()+"\"" +
							" value=\""+"GroupID"+"#"+treeModel.getGroupId()+"\">";
	 	strGroupNode = strGroupNode + "<span class=\"itemstyle\" id=\"Span_"+treeModel.getGroupId()+"\" onclick=OnClickCheckBox("+treeModel.getGroupId()+",\"Span_"+treeModel.getGroupId()+"\");>";
	 	strGroupNode = strGroupNode + checkBoxString + treeModel.getGroupName();
	 	strGroupNode = strGroupNode + "</span>";
		scriptStr.append(_objName+".add("+treeModel.getGroupId()+","+_parentId+",'"+strGroupNode+"','','','main','','','false','"+intOrganbegin+"');\n");
		_parentId = treeModel.getGroupId();
		
		for(int i=0;i<first.size();i++){
			element = (Element)first.get(i);
			if(element.getName().equals("user")){
		    	scriptStr.append(this.getOrganUser(element,_parentId,_ifOpen,_objName));
         	}else if(element.getName().equals("group")){  
      	    	scriptStr.append(this.getOrganGroup(element,_parentId,_ifOpen,_objName));  
            }
		}
		
		return scriptStr.toString();
	}	

	/**
	 * �õ�user���Ե�javascript
	 * @param element
	 * @return
	 */
	private String getOrganUser(Element element,String _parentId,String _ifOpen,String _objName){
		StringBuffer scriptStr = new StringBuffer();			
		OrganModel treeModel = null;
	 	treeModel = this.getOrganModel(element.getChild("property"),"user");
		//String script = "OnClickUserNode(\""+treeModel.getUserLoginName()+"\",\""+treeModel.getUserName()+"\")";
		//String script = " selectOrgan()";
		//class="itemstyle" onclick="changeTextColor(this)" id="'+nodeId+'"
	 	//String strUserNode = checkBoxString+"<span class=\"itemstyle\" id=\"Span_"+treeModel.getUserId()+"\" onclick=OnClickCheckBox("+treeModel.getUserId()+",\"Span_"+treeModel.getUserId()+"\");><span>"+treeModel.getUserName()+"</span></span>";
//	 	String strUserNode = checkBoxString+"<span class=\"itemstyle\" onclick=\"checks("+treeModel.getUserId()+",Span_"+treeModel.getUserId()+");"+script+"\"><span>"+treeModel.getUserName()+"</span></span>";
	 	String strUserNode = "";
	 	String checkBoxString = "";
		checkBoxString = "<input type=\"checkbox\" class=\"inputCheckBox\" name=\"group\" id=\""+treeModel.getUserId()+"\"" +
				" value=\""+"UserID"+"#"+treeModel.getUserLoginName()+"\">";		
		strUserNode = strUserNode + "<span class=\"itemstyle\" id=\"Span_"+treeModel.getUserId()+"\" onclick=OnClickCheckBox("+treeModel.getUserId()+",\"Span_"+treeModel.getUserId()+"\");>";
		strUserNode = strUserNode + checkBoxString + treeModel.getUserName();
		strUserNode = strUserNode + "</span>";	
		scriptStr.append(_objName+".add("+treeModel.getUserId()+","+_parentId+",'"+strUserNode+"','','','main','','','"+_ifOpen+"');\n"); 
		return scriptStr.toString();
	}	

}
