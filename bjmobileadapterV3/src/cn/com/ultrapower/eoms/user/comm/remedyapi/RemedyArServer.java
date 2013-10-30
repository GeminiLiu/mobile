package cn.com.ultrapower.eoms.user.comm.remedyapi;

import com.remedy.arsys.api.ARServerUser;
import com.remedy.arsys.api.NameID;
import com.remedy.arsys.api.Util;

public class RemedyArServer
{
	private	static MyServerUser 	m_ARServerUser=null;
	
	public static ARServerUser getArServerUser(String strARName,String strARPassWord,String strARServer,int intARServerPort){
		try {
			if(m_ARServerUser==null || !m_ARServerUser.isLogin()){
				m_ARServerUser = null;
				m_ARServerUser = new MyServerUser(strARName,strARPassWord,null,strARServer,intARServerPort);
				Util.ARSetServerPort(m_ARServerUser, new NameID(strARServer), intARServerPort, 0) ;
				m_ARServerUser.login();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return m_ARServerUser;
	}
		
	public static void releaseArServerUser(){
		m_ARServerUser.logout();
	}
}