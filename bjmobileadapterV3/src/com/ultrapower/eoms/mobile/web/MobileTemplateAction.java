package com.ultrapower.eoms.mobile.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;

import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper; 

import cn.com.ultrapower.ultrawf.share.FileOperUtil;

import com.ultrapower.eoms.common.action.BaseAction;
import com.ultrapower.eoms.common.dao.impl.HibernateDaoImpl;
import com.ultrapower.eoms.mobile.interfaces.template.model.MobileTemplate;
import com.ultrapower.eoms.mobile.service.TemplateProviderService;

public class MobileTemplateAction extends BaseAction
{
	private TemplateProviderService mobileTemplateProvider;
	private HibernateDaoImpl hibernateDao;
	private String baseSchema;
	private String versionName;
	
	public String getWorkflowTemplateXml() 
	{
		Calendar cal = Calendar.getInstance();
		String newVersionName = baseSchema.replace(":", "_") + "_"
							+ cal.get(Calendar.YEAR)
							+ cal.get(Calendar.MONTH)
							+ cal.get(Calendar.DAY_OF_MONTH)
							+ "_" + (System.currentTimeMillis()/1000);
		String xml = mobileTemplateProvider.buildWorkflowTemplate(baseSchema, newVersionName);
		List<MobileTemplate> templateList = hibernateDao.find("from MobileTemplate where (type='workflow' or type='YH' or type='BULT') and category=? order by sort asc", new Object[] {baseSchema});
		MobileTemplate template = null;
		if(templateList!=null && templateList.size()>0){
			template = templateList.get(0);
		}
		String templateFilePath = "";
		String templateBasePath = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "mobiletemplate/";
		if(template != null)
		{
			templateFilePath = templateBasePath + template.getType() + File.separator + newVersionName + ".xml";
			template.setVersion(newVersionName);
			hibernateDao.save(template);
			if(xml!=null&&xml.length()>0){
				try
				{
					FileOperUtil.newXMLFile(templateFilePath, DocumentHelper.parseText(xml));
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				catch (DocumentException e)
				{
					e.printStackTrace();
				}
			}
		}
		try
		{
			PrintWriter out = getResponse().getWriter();
			getResponse().setContentType("text/html;charset=UTF-8");
			if(!templateFilePath.equals(""))
			{
				System.out.println("模板生成完成，版本号为：" + newVersionName);
				out.print("模板生成完成，版本号为：" + newVersionName);
			}
			else
			{
				System.out.println("模板生成失败，请先在模板表中创建一条workflow或者YH类型的初始版本数据。");
			}
			out.close();
			out.flush();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public String getVersionName()
	{
		return versionName;
	}

	public void setVersionName(String versionName)
	{
		this.versionName = versionName;
	}

	public TemplateProviderService getMobileTemplateProvider()
	{
		return mobileTemplateProvider;
	}

	public void setMobileTemplateProvider(TemplateProviderService mobileTemplateProvider)
	{
		this.mobileTemplateProvider = mobileTemplateProvider;
	}

	public String getBaseSchema()
	{
		return baseSchema;
	}

	public void setBaseSchema(String baseSchema)
	{
		this.baseSchema = baseSchema;
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
