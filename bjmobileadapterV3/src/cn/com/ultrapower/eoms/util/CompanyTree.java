package cn.com.ultrapower.eoms.util;

import java.util.Iterator;
import java.util.List;

import cn.com.ultrapower.eoms.user.userinterface.UserGroupInterface;
import cn.com.ultrapower.eoms.user.userinterface.bean.ElementInfoBean;
import cn.com.ultrapower.eoms.user.userinterface.bean.UserGroupPram;

public class CompanyTree {
	public String getTopCompanyNode(String userLoginName, String userCompanyId) {
		try {
			// 根据用户登陆名，及单位标识，查询出顶级的公司List。
			UserGroupPram usergrouppram = new UserGroupPram();
			// 用户登陆名。
			usergrouppram.setUserLoginName(userLoginName);
			// 组的父ID：当查义顶级公司节点时，此参数为空。
			usergrouppram.setGroupParentid("");
			// 公司标识，1：代表查询当前登陆用户所在的公司，0：代表查询所有公司。
			usergrouppram.setUser_CPID("1");
			UserGroupInterface usergroupinterface = new UserGroupInterface();
			List list = usergroupinterface.getGroupUserList(usergrouppram);
			StringBuffer sbf = new StringBuffer();
			sbf.append("var tree = new WebFXTree(\"\");");
			sbf.append("try{");
			if (list != null) {
				for (Iterator it = list.iterator(); it.hasNext();) {
					// 返回值List中是一个Bean信息。
					ElementInfoBean elementinfo = (ElementInfoBean) it.next();
					// ID,是组时为组ID，是用户是为用户ID。
					String id = elementinfo.getElementid();
					// 名称：是组是为组名称，是用户是为用户名称。
					String name = elementinfo.getElementname();
					// 标识，1：用户，0：组。
					String flag = elementinfo.getElementflag();
					if(flag.equals("0")){
						sbf.append("tree.add(new WebFXLoadTreeItem(\""+name+"\",\"companySelectOtherTree.jsp?gid="+id+"\"));");
					}else if(flag.equals("1")){
						sbf.append("tree.add(new WebFXLoadTreeItem(\""+name+"\",\"companySelectOtherTree.jsp?gid="+id+"\",\"\",\"\",\"\",\"\",\"\",\""+id+"\"));");
					}
					//System.out.println("ID:" + id + ",NAME:" + name + ",flag:"+ flag);
							
				}
				sbf.append("}catch(e){}");
				sbf.append("document.write(tree);");
				Log.logger.info(sbf.toString());
				return sbf.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getCompanyNode(String fatherCompanyId) {
		try {
			StringBuffer sbf = new StringBuffer();
//			根据组的父ID，查询出子组信息List及用此组ID下的用户信息List
			UserGroupPram usergrouppram = new UserGroupPram();
			//用户登陆名。
			usergrouppram.setUserLoginName("");
			//组的父ID：当查义顶级公司节点时，此参数为空。
			usergrouppram.setGroupParentid(fatherCompanyId);
			//公司标识，1：代表查询当前登陆用户所在的公司，0：代表查询所有公司。
			usergrouppram.setUser_CPID("");
			UserGroupInterface usergroupinterface = new UserGroupInterface();
			List list = usergroupinterface.getGroupUserList(usergrouppram);
			if(list!=null)
			{
				for(Iterator it = list.iterator();it.hasNext();)
				{
					//返回值List中是一个Bean信息。
					ElementInfoBean elementinfo = (ElementInfoBean)it.next();
					//ID,是组时为组ID，是用户是为用户ID。
					String id   = elementinfo.getElementid();
					//名称：是组是为组名称，是用户是为用户名称。
					String name = elementinfo.getElementname();
					//标识，1：用户，0：组。
					String flag = elementinfo.getElementflag();
					//用户的ID
					String userId = elementinfo.getUserid();
					Log.logger.info(userId);
					userId = StringUtil.dropZero(userId);
					Log.logger.info(userId);
					if(flag.equals("0")){
						//sbf.append("tree.add(new WebFXLoadTreeItem(\""+name+"\",\"companySelectOtherTree.jsp?gid="+id+"\"));");
						sbf.append("<tree src=\"companySelectOtherTree.jsp?gid="+id+"\" text=\""+name+"\" />");
					}else if(flag.equals("1")){
						//sbf.append("tree.add(new WebFXLoadTreeItem(\""+name+"\",\"companySelectOtherTree.jsp?gid="+id+"\",\"\",\"\",\"\",\"\",\"\",\""+name+":"+id+":"+id+"\"));");
						sbf.append("<tree text=\""+name+"\"   funpram=\""+userId+":"+name+":"+userId+"\"/>");
					}
					//System.out.println("ID:"+id+",NAME:"+name+",flag:"+flag);
				}
				Log.logger.info(sbf.toString());
				return sbf.toString();
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	public static void main(String[]args){
		CompanyTree a = new CompanyTree();
		a.getTopCompanyNode("Demo","1");
	}
}
