package com.ultrapower.eoms.mobile.interfaces.template.manager;

import java.io.File;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import com.ultrapower.eoms.common.dao.impl.HibernateDaoImpl;
import com.ultrapower.eoms.mobile.interfaces.template.model.MobileTemplate;
import com.ultrapower.eoms.mobile.interfaces.template.model.TemplateInputModel;
import com.ultrapower.eoms.mobile.service.InterfaceService;

public class TemplateManager implements InterfaceService
{
	private HibernateDaoImpl hibernateDao;
	
	public String call(String xml, String fileList)
	{
		TemplateInputModel input = new TemplateInputModel();
		String outputXml;

		try
		{
			input.buildModel(xml);
			outputXml = handle(input);
			outputXml = outputXml.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + '\n', "");
		}
		catch (Exception e)
		{
			outputXml = "<opDetail><baseInfo><isLegal>0</isLegal></baseInfo></opDetail>";
			e.printStackTrace();
		}
		return outputXml;
	}
	
	private String handle(TemplateInputModel inputModel)
	{
		String outputXml = "";
		String templateBasePath = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "mobiletemplate/";
		
		List<MobileTemplate> templateList = hibernateDao.find("from MobileTemplate where type=? and category=? order by sort asc", new Object[] {inputModel.getCategory(), inputModel.getVersion()});
		MobileTemplate template = null;
		if(templateList != null && templateList.size()>0)
		{
			template = templateList.get(0);
			String templateFilePath = templateBasePath + inputModel.getCategory() + File.separator + template.getVersion() + ".xml";
			SAXReader reader = new SAXReader();
			Document doc;
			try
			{
				doc = reader.read("file://" + templateFilePath);
				outputXml = doc.asXML();
			}
			catch (DocumentException e)
			{
				e.printStackTrace();
			}
		}
		
		return outputXml;
	}

	public HibernateDaoImpl getHibernateDao()
	{
		return hibernateDao;
	}

	public void setHibernateDao(HibernateDaoImpl hibernateDao)
	{
		this.hibernateDao = hibernateDao;
	}

}
