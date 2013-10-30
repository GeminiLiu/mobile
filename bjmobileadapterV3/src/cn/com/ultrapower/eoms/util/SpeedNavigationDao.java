package cn.com.ultrapower.eoms.util;

import java.util.ArrayList;

import cn.com.ultrapower.eoms.common.basedao.Navigation;

public class SpeedNavigationDao {
	/**
	 * @author zhouy
	 * @param args
	 */
    Navigation navigation = null;
	public ArrayList getNavigationes(String navigationId){
		ArrayList list = new ArrayList();
		navigation = new Navigation();
		navigation.setTitle("中国移动EOMS系统");
		navigation.setUrl("http://localhost:8080/");
		list.add(navigation);
		navigation=null;
		navigation = new Navigation();
		navigation.setTitle("河北移动EOMS系统");
		navigation.setUrl("http://localhost:8080/");
		list.add(navigation);
		return list;
	}
}
