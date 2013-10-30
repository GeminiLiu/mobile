package cn.com.ultrapower.eoms.util.utilinterface;

import java.util.*;

import cn.com.ultrapower.eoms.user.userinterface.bean.PortaletInfo;

public class Portal {

	/**
	 * 通过传入的HashMap得到生成属性菜单的字符串。
	 * @param hm
	 * @return
	 */
	public String getPortalTree(HashMap hm,String userId){
		StringBuffer sb = new StringBuffer();
		Iterator it=hm.keySet().iterator();
		List list = null;
		while(it.hasNext()){ 
			int i = 1;
			String key=(String)it.next();
			list = (List)hm.get(key);
			if (list != null) {
				for (Iterator ite = list.iterator(); ite.hasNext();) {
					PortaletInfo portaletinfo = (PortaletInfo) ite.next();
					if(i == 1){
						sb.append("t=outlookbar.addtitle('");
						sb.append(portaletinfo.getSource1cnname());
						sb.append("');");
					}
					sb.append("\n");
					sb.append("outlookbar.additem('");
					sb.append(portaletinfo.getSourcecnname());
					sb.append("',t,'");
					if(portaletinfo.getUrlvalue().lastIndexOf("ftp") == -1){
						sb.append("sessionSetTree.jsp?url=");
					}
					sb.append(portaletinfo.getUrlvalue());
					if(portaletinfo.getUrlvalue().lastIndexOf("ftp") == -1){
						sb.append("&sourceId=");
						sb.append(portaletinfo.getSourceid());
						sb.append("&userId=");
						sb.append(userId);
						sb.append("&moduleId=");
						sb.append(portaletinfo.getModuleid());
					}
					
					sb.append("');");
					sb.append("\n");
					i++;
				}
			}
		} 
		System.out.print(sb.toString());
		return sb.toString();
	}
}
