package com.ultrapower.eoms.mobile.dao;

import java.util.Vector;

import cn.com.ultrapower.eoms.common.basedao.GeneralDAO;
import cn.com.ultrapower.eoms.common.basedao.GeneralException;
import cn.com.ultrapower.eoms.util.ArLogModule;
import cn.com.ultrapower.eoms.util.Log;

public class UserLoginDAO extends GeneralDAO{
	
/**
 * 返回登录人员在数据库中的信息
 * @param loginName
 * @return vector
 */
public Vector findLoginUser(String loginName){
	    
		StringBuffer stringbuffer=new StringBuffer();	
		String sqlstr =" from SysPeoplepo sp where sp.c630000001='"+loginName+"'";		
		stringbuffer.append(sqlstr);				
		try {
			Vector vec=GeneralDAO.loadObjects(stringbuffer.toString());
			if(null!=vec || !"".equals(vec))
			return vec;
			return null;
		}
		 catch (GeneralException e) {
			// TODO Auto-genorated catch block
			e.printStackTrace();
			Log.logger.error(ArLogModule.LOGIN+"登录错误");
			return null;
		}
	}
}
