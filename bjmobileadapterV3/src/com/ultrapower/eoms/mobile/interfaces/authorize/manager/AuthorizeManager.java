package com.ultrapower.eoms.mobile.interfaces.authorize.manager;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import cn.com.ultrapower.eoms.user.rolemanage.people.hibernate.po.SysPeoplepo;

import com.ultrapower.eoms.common.dao.impl.HibernateDaoImpl;
import com.ultrapower.eoms.mobile.dao.UserLoginDAO;
import com.ultrapower.eoms.mobile.interfaces.authorize.model.AuthorizeInputModel;
import com.ultrapower.eoms.mobile.interfaces.authorize.model.AuthorizeOutputModel;
import com.ultrapower.eoms.mobile.interfaces.template.model.MobileTemplate;
import com.ultrapower.eoms.mobile.service.InterfaceService;
import com.ultrapower.eoms.mobile.util.CryptUtils;

public class AuthorizeManager implements InterfaceService
{
	private HibernateDaoImpl hibernateDao;
	
	public String call(String xml, String fileList)
	{
		AuthorizeInputModel input = new AuthorizeInputModel();
		String outputXml;

		try
		{
			input.buildModel(xml);
			AuthorizeOutputModel output = handle(input);
			outputXml = output.buildXml();
		}
		catch (Exception e)
		{
			outputXml = AuthorizeOutputModel.buildExceptionXml();
			e.printStackTrace();
		}
		return outputXml;
	} 

	private AuthorizeOutputModel handle(AuthorizeInputModel inputModel)
	{
		UserLoginDAO  uld = new UserLoginDAO();
		Vector vec = null;
		vec = uld.findLoginUser(inputModel.getUserName());
		boolean checkFlag = false;
		AuthorizeOutputModel output = new AuthorizeOutputModel();
		SysPeoplepo  sp = null;
		if(vec.size() > 0)
		{
			sp = (SysPeoplepo)vec.get(0);
			String compass = sp.getC630000002();
			CryptUtils crypt = CryptUtils.getInstance();
			String pwd = crypt.decode(inputModel.getPassword());
			if(compass!=null && compass.equals(pwd)){
				checkFlag = true;
			}
		}
		if(checkFlag)
		{
			output.setLoginResult(1);
			output.setUserName(inputModel.getUserName());
		}else{
			output.setLoginResult(0);
		}

		List<MobileTemplate> templateList = hibernateDao.getAll(MobileTemplate.class);
		
		Map<String, Map<String, String[]>> templates = new LinkedHashMap<String, Map<String,String[]>>();
		if(templateList != null)
		{
			for(MobileTemplate template : templateList)
			{
				if(!templates.containsKey(template.getType()))
				{
					Map<String, String[]> categoryMap = new LinkedHashMap<String, String[]>();
					templates.put(template.getType(), categoryMap);
				}
					
				templates.get(template.getType()).put(template.getCategory(), new String[] {template.getCategoryName(), template.getVersion()});
			}
		}
		
		output.setTemplates(templates);
		return output;
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
