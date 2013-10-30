package cn.com.ultrapower.ultrawf.share;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import cn.com.ultrapower.ultrawf.share.OperationLogFile;
import cn.com.ultrapower.ultrawf.share.FormatTime;

public class XMLAnalyse {

	private String m_FilePath="";
	private String m_FileName="";
	Element rootElement=null;
	public XMLAnalyse(String p_FilePath)
	{
		Document doc = null;
		m_FilePath=FormatString.CheckNullString(p_FilePath);		
		try
		{	
			File file = null;
			file = new File(m_FilePath);
			SAXBuilder builder = new SAXBuilder();
	
				doc = builder.build(file);
				doc.getRootElement();
				rootElement = doc.getRootElement();
		}catch(Exception ex)
		{  
			OperationLogFile.writeErrLog("\r\n错误发生时间:"+FormatTime.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
			OperationLogFile.writeErrLog("\r\n错误发生位置:XMLAnalyse public XMLAnalyse(String p_FilePath)");
			OperationLogFile.writeErrLog("\r\n错误描述:"+ex.getMessage());
			ex.printStackTrace();
			ex.printStackTrace();
		}
		
	}
	
	/**
	 * 返回某给节点值	 * @param p_Catalog
	 * @return
	 */
	public String getValue(String p_Catalog)
	{
		p_Catalog=FormatString.CheckNullString(p_Catalog);
		Element element = rootElement;
		String tags[] = p_Catalog.split("#");
		try
		{
			for (int i = 0; i < tags.length; i++) {
				element = element.getChild(tags[i]);
				if(element==null)
					break;
			}
		}catch(Exception ex)
		{
			OperationLogFile.writeErrLog("\r\n错误发生时间:"+FormatTime.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
			OperationLogFile.writeErrLog("\r\n错误发生位置:XMLAnalyse public String getValue(String p_Catalog)");
			OperationLogFile.writeErrLog("\r\n错误描述:"+ex.getMessage());
			ex.printStackTrace();
		}
		// 根据标识得到配置信息
		String text = "";
		if(element!=null)
			text=element.getText();
		
		return text;
	}
	
	
	public String getValue(Element p_element,String p_Catalog)
	{
		p_Catalog=FormatString.CheckNullString(p_Catalog);
		Element element = p_element;
		try
		{
			String tags[] = p_Catalog.split("#");
			for (int i = 0; i < tags.length; i++) {
				element = element.getChild(tags[i]);
				if(element==null)
					break;				
			}
		}catch(Exception ex)
		{
			OperationLogFile.writeErrLog("\r\n错误发生时间:"+FormatTime.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
			OperationLogFile.writeErrLog("\r\n错误发生位置:XMLAnalyse public String getValue(Element p_element,String p_Catalog)");
			OperationLogFile.writeErrLog("\r\n错误描述:"+ex.getMessage());
			ex.printStackTrace();
			
		}
		
		// 根据标识得到配置信息
		String text = "";
		if(element!=null)
			text=element.getText();
		return text;		
	}
	
	/**
	 * 查找返回某个节点的值 
	 * 如：<interface><book><id>001</id><name>书名</name></book><book><id>002</id><name>书名2</name></book></interface>
	 * 要想返回002的书名则 getValue("","id","002","name"); 如果有多层则 getValue("public#class","id","002","name");
	 * @param p_Catalog 
	 * @param p_fromTags
	 * @param p_formValue
	 * @param p_tarTags
	 * @return
	 */
	public String getValue(String p_Catalog,String p_fromTags,String p_formValue,String p_tarTags)
	{
		p_Catalog=FormatString.CheckNullString(p_Catalog);
		p_fromTags=FormatString.CheckNullString(p_fromTags);
		p_tarTags=FormatString.CheckNullString(p_tarTags);
		
		if(p_fromTags.equals("")||p_formValue.equals(""))
			return "";
		if(rootElement==null)
			return "";
		
		Element element = null;		
		String strReValue="";
		element=rootElement;
		
		String tags[] = p_Catalog.split("#");
		for (int i = 0; i < tags.length; i++) {
			if(element!=null)
				element = element.getChild(tags[i]);
			
		}	
		Element chlElement;
		String m_fromTagsValue;
		if(element==null)
			strReValue="";
		else
		{
			List m_list=element.getChildren();
			int rowCount=0;
			if(m_list!=null)
				rowCount=m_list.size();
			for(int row=0;row<rowCount;row++)
			{
				chlElement=(Element)m_list.get(row);
				Element tmpElement=chlElement.getChild(p_fromTags);
				if(tmpElement!=null)
				{
					m_fromTagsValue=FormatString.CheckNullString(tmpElement.getText());
					if(m_fromTagsValue.equals(p_formValue))
					{
						strReValue=FormatString.CheckNullString(chlElement.getChild(p_tarTags).getText());
						break;
					}
				}
			}
		}
		return strReValue;
	}
	
	/**
	 * 
	 * @param p_Catalog
	 * @return
	 */
	public List getElementList(String p_Catalog)
	{
		p_Catalog=FormatString.CheckNullString(p_Catalog).trim();
		List m_list=null;
		if(!p_Catalog.equals(""))
		{
			p_Catalog=FormatString.CheckNullString(p_Catalog);
			Element element=rootElement;
			
			String tags[] = p_Catalog.split("#");
			int len=tags.length;
			for (int i = 0; i < len; i++) {
					element = element.getChild(tags[i]);
			}
			try{
			m_list=element.getChildren();
			}catch(Exception ex)
			{
				ex.printStackTrace();
			}

		}
		else
		{
			m_list=rootElement.getChildren();
			//m_list.add(rootElement);
		}
		return m_list;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		XMLAnalyse m_XMLAnalyse=new XMLAnalyse("E:\\WorkSpace\\USDInterface\\WEB-INF\\conf\\KFComplainSheet.xml");
		String stroptname=m_XMLAnalyse.getValue("OperateUserLoginName");
		String value=m_XMLAnalyse.getValue("optDic#optTypes","id","001","name");
		System.out.println(value);
	}

}
