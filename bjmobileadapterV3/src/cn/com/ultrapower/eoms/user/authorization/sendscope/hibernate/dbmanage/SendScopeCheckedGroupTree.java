package cn.com.ultrapower.eoms.user.authorization.sendscope.hibernate.dbmanage;

import java.util.Iterator;
import java.util.List;
import org.hibernate.Session;
import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.authorization.role.hibernate.dbmanage.GetRoleUserTree;
import cn.com.ultrapower.eoms.user.authorization.role.hibernate.dbmanage.GetRoleUserTreeCheckBox;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;
import cn.com.ultrapower.eoms.user.rolemanage.group.hibernate.po.SysGrouppo;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.comm.function.Function;

public class SendScopeCheckedGroupTree {
	static final Logger logger = (Logger) Logger
			.getLogger(SendScopeCheckedGroupTree.class);

	static Session session = null;

	private String strjstree = "";

	private String tmpstrjstree = "";

	Function str = new Function();

	GetFormTableName tablename = new GetFormTableName();

	String Group_Type1 = tablename.GetFormName("Group_Type");

	public StringBuffer showTree() {

		StringBuffer strjs = new StringBuffer();
		strjs
				.append("<script language='JavaScript'>d = new dTree('d');d.add(0,-1,'',null,'','main');</script>");
		strjs.append("<script language='JavaScript'>");
		strjs.append(intfind());
		strjs.append("</script>");
		strjs.append("<script>document.write(d);</script>");
		return strjs;
	}

	public String intfind() {
		tmpstrjstree = "";
		String groupParentID = "";
		String Group_Type = "";
		String Group_Name = "";
		String groupID = "";
		try {
			String query = "from SysGrouppo";

			HibernateDAO session = new HibernateDAO();
			List l1 = session.queryObject(query);
			Iterator it = l1.iterator();

			while (it.hasNext()) {
				SysGrouppo sysgroup = (SysGrouppo) it.next();
				groupParentID = str.nullString(sysgroup.getC630000020());
				// Group_Type = str.nullString(sysgroup.getC630000021());
				Group_Name = str.nullString(sysgroup.getC630000018());
				groupID = str.nullString(sysgroup.getC1().toString());

				// if(Group_Type==Group_Type1||Group_Type.equals(Group_Type1)){
				tmpstrjstree = "d.add(" + groupID + "," + groupParentID + ",\""
						+ Group_Name
						+ "<input type='checkbox' name='Group_Name' value='"
						+ groupID + "'>"
						+ "</font>\",\"\",\"\",\"main\",\"\",\"\",\"false\");";

				strjstree = strjstree + tmpstrjstree;
				// }

			}
			logger.info("strjstree:" + strjstree);
			return strjstree.toString();
		} catch (Exception e) {
			logger.info("----请查看c21是否为空----" + e);
			e.printStackTrace(System.err);
			return null;
		}
	}
}
