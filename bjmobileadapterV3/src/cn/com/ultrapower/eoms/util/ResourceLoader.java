package cn.com.ultrapower.eoms.util;

import java.util.ArrayList;

import cn.com.ultrapower.eoms.util.bean.ResourceBean;

/**
 * 本类用于返回资源的XML对象，并解析成可以调用的数据
 * @author lijupeng
 *
 */
public class ResourceLoader {
	    ResourceBean resource = null;
	public ArrayList getDesktopResourceList(String resourceId){
		ArrayList list = new ArrayList();
		resource = new ResourceBean();
		resource.setResourceTitle("通用工单起草");
		resource.setResourceUrl("http://localhost:8080/");
		list.add(resource);
		resource=null;
		resource = new ResourceBean();
		resource.setResourceTitle("故障工单起草");
		resource.setResourceUrl("http://localhost:8080/");
		list.add(resource);
		return list;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
