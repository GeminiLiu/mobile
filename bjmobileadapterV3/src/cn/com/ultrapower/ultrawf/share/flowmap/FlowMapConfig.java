package cn.com.ultrapower.ultrawf.share.flowmap;

import java.util.*;
import java.io.*;

import org.jdom.*;
import org.jdom.input.*;

import cn.com.ultrapower.ultrawf.share.constants.Constants;

import java.lang.reflect.*;

/**
 * 对FlowMapConfig.xml读取的配置类
 * 
 * @author BigMouse
 */
public class FlowMapConfig
{
	// 配置文件的地址
	private File file = new File(Constants.sysPath + File.separator + "conf" + File.separator + "UltraFlowMapConfig.xml");

	/**
	 * 根据传入参数获得对Process表操作的类的完整路径
	 * 
	 * @param processtype：环节的类型，Deal或者Auditing
	 * @return 对Process表操作的类的完整路径名
	 * @throws Exception
	 */
	public String getProcessManagerClassName(String processtype) throws Exception
	{
		// 读取xml配置文件，并获取根节点
		SAXBuilder bu = new SAXBuilder();
		Document doc = bu.build(file);
		Element rootElement = doc.getRootElement();

		// 获取环节类型的节点
		Element ptypeElements = rootElement.getChild("ProcessType");
		List ptypeList = ptypeElements.getChildren("PType");

		// 根据传入参数获得对Process表操作的类的完整路径
		for (Iterator it = ptypeList.iterator(); it.hasNext();)
		{
			Element ptypeElement = (Element) it.next();
			if (ptypeElement.getAttributeValue("type").equals(processtype))
			{
				return ptypeElement.getChildText("ControlClass");
			}
		}
		return null;
	}
	
	/**
	 * 根据传入参数获得对Link表操作的类的完整路径
	 * 
	 * @param processtype：流转的类型，Deal或者Auditing
	 * @return 对Link表操作的类的完整路径名
	 * @throws Exception
	 */
	public String getLinkManagerClassName(String linktype) throws Exception
	{
		SAXBuilder bu = new SAXBuilder();
		Document doc = bu.build(file);
		Element rootElement = doc.getRootElement();

		// 获取流转类型的节点

		Element ptypeElements = rootElement.getChild("LinkType");
		List ptypeList = ptypeElements.getChildren("LType");

		// 根据传入参数获得对Link表操作的类的完整路径
		for (Iterator it = ptypeList.iterator(); it.hasNext();)
		{
			Element ptypeElement = (Element) it.next();
			if (ptypeElement.getAttributeValue("type").equals(linktype))
			{
				return ptypeElement.getChildText("ControlClass");
			}
		}
		return null;
	}
	
	/**
	 * 根据传入参数获得对Process表操作的类的完整路径
	 * 
	 * @param processtype：环节的类型，Deal或者Auditing
	 * @return 对Process表操作的类的完整路径名
	 * @throws Exception
	 */
	public String getProcessClassName(String processtype) throws Exception
	{
		// 读取xml配置文件，并获取根节点
		SAXBuilder bu = new SAXBuilder();
		Document doc = bu.build(file);
		Element rootElement = doc.getRootElement();

		// 获取环节类型的节点
		Element ptypeElements = rootElement.getChild("ProcessType");
		List ptypeList = ptypeElements.getChildren("PType");

		// 根据传入参数获得对Process表操作的类的完整路径
		for (Iterator it = ptypeList.iterator(); it.hasNext();)
		{
			Element ptypeElement = (Element) it.next();
			if (ptypeElement.getAttributeValue("type").equals(processtype))
			{
				return ptypeElement.getChildText("ModelClass");
			}
		}
		return null;
	}
	
	/**
	 * 根据传入参数获得对Link表操作的类的完整路径
	 * 
	 * @param processtype：流转的类型，Deal或者Auditing
	 * @return 对Link表操作的类的完整路径名

	 * @throws Exception
	 */
	public String getLinkClassName(String linktype) throws Exception
	{
		// 读取xml配置文件，并获取根节点

		SAXBuilder bu = new SAXBuilder();
		Document doc = bu.build(file);
		Element rootElement = doc.getRootElement();

		// 获取环节类型的节点

		Element ptypeElements = rootElement.getChild("LinkType");
		List ptypeList = ptypeElements.getChildren("LType");

		// 根据传入参数获得对Process表操作的类的完整路径
		for (Iterator it = ptypeList.iterator(); it.hasNext();)
		{
			Element ptypeElement = (Element) it.next();
			if (ptypeElement.getAttributeValue("type").equals(linktype))
			{
				return ptypeElement.getChildText("ModelClass");
			}
		}
		return null;
	}

	/**
	 * 获取xml中配置的环节状态集合对象
	 * 
	 * @return xml中配置的环节状态集合对象
	 * @throws Exception
	 */
	public ProcessStatusList getProcessStatusList() throws Exception
	{
		// 读取xml配置文件，并获取根节点
		SAXBuilder bu = new SAXBuilder();
		Document doc = bu.build(file);
		Element rootElement = doc.getRootElement();

		ProcessStatusList psList = new ProcessStatusList();

		// 获取环节状态的节点
		Element ptypeElements = rootElement.getChild("ProcessStatus");

		// 获取流程图的整体样式封装到环节状态集合类中
		psList.setStartX(Integer.parseInt(ptypeElements.getAttributeValue("startX")));
		psList.setStartY(Integer.parseInt(ptypeElements.getAttributeValue("startY")));
		psList.setModelWidth(Integer.parseInt(ptypeElements.getAttributeValue("modelWidth")));
		psList.setModelHeight(Integer.parseInt(ptypeElements.getAttributeValue("modelHeight")));
		psList.setArrowLength(Integer.parseInt(ptypeElements.getAttributeValue("arrowLength")));
		psList.setArrowwidth(Integer.parseInt(ptypeElements.getAttributeValue("arrowWidth")));
		psList.setRowHeight(Integer.parseInt(ptypeElements.getAttributeValue("rowHeight")));

		// 获取环节状态集合封装到环节状态实体类中并填入到List
		List ptypeList = ptypeElements.getChildren("PStatus");
		List pStatusList = new ArrayList();
		for (Iterator it = ptypeList.iterator(); it.hasNext();)
		{
			Element ptypeElement = (Element) it.next();
			ProcessStatusModel psModel = new ProcessStatusModel();
			psModel.setStatusName(ptypeElement.getAttributeValue("status"));
			psModel.setColor(ptypeElement.getChild("Color").getText());
			psModel.setColor1(ptypeElement.getChild("Color1").getText());
			psModel.setInfoText(ptypeElement.getChild("InfoText").getText());
			psModel.setArrowWay(ptypeElement.getChild("ArrowWay").getText());
			psModel.setArrowType(ptypeElement.getChild("ArrowType").getText());
			pStatusList.add(psModel);
		}
		psList.setProcessStatusModelList(pStatusList);
		return psList;
	}

	/**
	 * 根据环节的类型，获取xml中配置的相应环节的字段信息实体类的Map
	 * 
	 * @param tablename：环节的类型，Deal或者Auditing
	 * @return 环节的字段信息实体类的List
	 * @throws Exception
	 */
	public Map getFieldMap(String tablename) throws Exception
	{
		// 读取xml配置文件，并获取根节点
		SAXBuilder bu = new SAXBuilder();
		Document doc = bu.build(file);
		Element rootElement = doc.getRootElement();

		// 获取字段信息的节点
		Element fieldReflectElements = rootElement.getChild("FieldsReflect");
		List fieldModelElements = fieldReflectElements.getChildren("FieldModel");

		// 根据环节的类型，获取xml中配置的相应环节的字段信息的节点集合
		List fieldList = new ArrayList();
		for (Iterator it = fieldModelElements.iterator(); it.hasNext();)
		{
			Element fieldElement = (Element) it.next();
			if (fieldElement.getAttributeValue("fieldType").equals(tablename))
			{
				fieldList = fieldElement.getChildren("Field");
			}
		}

		// 获取字段信息封装到字段信息实体类中并填入到Map
		Map fieldElementMap = new HashMap();
		for (Iterator it = fieldList.iterator(); it.hasNext();)
		{
			Element fieldElement = (Element) it.next();
			FieldModel fModel = new FieldModel();
			fModel.setFieldName(fieldElement.getAttributeValue("fieldName"));
			Field field = FieldType.class.getField(fieldElement.getAttributeValue("dataType"));
			fModel.setFieldType(field.getInt(new FieldType()));
			fModel.setFieldModel(fieldElement.getText());
			fieldElementMap.put(fieldElement.getAttributeValue("fieldName"), fModel);
		}
		return fieldElementMap;
	}

	/**
	 * 根据环节的类型，获取xml中配置的相应环节的字段信息实体类的List
	 * 
	 * @param tablename：环节的类型，Deal或者Auditing
	 * @return 环节的字段信息实体类的List
	 * @throws Exception
	 */
	public List getFieldList(String tablename) throws Exception
	{
		// 读取xml配置文件，并获取根节点
		SAXBuilder bu = new SAXBuilder();
		Document doc = bu.build(file);
		Element rootElement = doc.getRootElement();

		// 获取字段信息的节点
		Element fieldReflectElements = rootElement.getChild("FieldsReflect");
		List fieldModelElements = fieldReflectElements.getChildren("FieldModel");

		// 根据环节的类型，获取xml中配置的相应环节的字段信息的节点集合
		List fieldList = new ArrayList();
		for (Iterator it = fieldModelElements.iterator(); it.hasNext();)
		{
			Element fieldElement = (Element) it.next();
			if (fieldElement.getAttributeValue("fieldType").equals(tablename))
			{
				fieldList = fieldElement.getChildren("Field");
			}
		}

		// 获取字段信息封装到字段信息实体类中并加入到List
		List fieldElementList = new ArrayList();
		for (Iterator it = fieldList.iterator(); it.hasNext();)
		{
			Element fieldElement = (Element) it.next();
			FieldModel fModel = new FieldModel();
			fModel.setFieldName(fieldElement.getAttributeValue("fieldName"));
			Field field = FieldType.class.getField(fieldElement.getAttributeValue("dataType"));
			fModel.setFieldType(field.getInt(new FieldType()));
			fModel.setFieldModel(fieldElement.getText());
			fieldElementList.add(fModel);
		}
		return fieldElementList;
	}
}
