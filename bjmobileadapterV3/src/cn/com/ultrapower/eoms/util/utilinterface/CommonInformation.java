package cn.com.ultrapower.eoms.util.utilinterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import cn.com.ultrapower.eoms.common.basedao.GeneralDAO;
import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.rolemanage.group.hibernate.po.SysGrouppo;
import cn.com.ultrapower.eoms.util.Log;

/**
 * 用于从基础组返回常用数据
 * 
 * @author lijupeng
 * 
 */
public class CommonInformation {

	/**
	 * 获得当前用户所在的省公司的id
	 * 
	 * @param userId
	 * @return
	 */
	public static String getProvinceId(long userId) {
		String provinceId = "";
		String sql = "from SysGrouppo groupTable,SysPeoplepo userTable where userTable.c630000029="
				+ userId + " and userTable.c630000013=groupTable.c1";
		try {
			Vector vector = (Vector) GeneralDAO.loadObjects(sql);
			if (vector != null && !vector.isEmpty()) {
				Object[] object = (Object[]) vector.get(0);
				SysGrouppo group = (SysGrouppo) object[0];
				String stringId = group.getC630000037().substring(
						group.getC630000037().indexOf(";") + 1,
						group.getC630000037().length());
				provinceId = Function.dropZero(stringId.substring(0, stringId
						.indexOf(";")));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return provinceId;
	}

	// 改写getProvinceId方法
	// 组类别0：总部 1：分公司2：代维公司3：非单位4、省公司、5、郊县公司。。。
	public static String getProvinceIdByWang(long userId) {
		// String provinceId = "";
		// String sql = "from SysGrouppo g,SysPeoplepo user where user.c1=" +
		// userId + " and user.c630000013=g.c1";
		// try {
		// Vector vector = (Vector) GeneralDAO.loadObjects(sql);
		// if(vector != null && !vector.isEmpty()){
		// Object[] object = (Object[])vector.get(0);
		// SysGrouppo group = (SysGrouppo)object[0];
		// provinceId = group.getC1();w
		// }
		// } catch (Exception e) {
		// // TODO: handle exception
		// }
		// return provinceId;

		String provinceId = "";
		String sql = "from SysGrouppo g,SysPeoplepo user where user.c1="
				+ userId + " and user.c630000013=g.c1";
		try {
			Vector vector = (Vector) GeneralDAO.loadObjects(sql);
			if (vector != null && !vector.isEmpty()) {
				Object[] object = (Object[]) vector.get(0);
				SysGrouppo group = (SysGrouppo) object[0];
				provinceId = group.getC630000027();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return provinceId;
	}

	/**
	 * <p>
	 * Description:根据传过来的公司ID获得该公司的所有人员的List
	 * <p>
	 * 
	 * @author wangwenzhuo
	 * @creattime 2007-3-1
	 * @param companyId
	 * @return List List中为SysPeoplepo
	 */
	public List getSysPeopleUnderCompany(String companyId) {

		StringBuffer sql = new StringBuffer();
		sql.append("from SysPeoplepo user where user.c630000013='");
		sql.append(companyId);
		sql.append("'");
		sql.append(" order by  nlssort(user.c630000003,'NLS_SORT=SCHINESE_PINYIN_M') ");
		List list = new ArrayList();
		try {
			list = GeneralDAO.loadObjects(sql.toString());
		} catch (Exception e) {
			Log.logger
					.error("[549]PeopleUnderGroupInterface.getPeopleUnderGroup() 根据传过来的组ID或组IntId获得组下及其下所有节点的所有人员的List失败"
							+ e.getMessage());
		}
		return list;
	}

	public static void main(String[] arg) {
		System.out.println("province id is " + getProvinceId(971));
	}
}
