package daiwei.mobile.util.FolderUtil;

import java.util.Map;

public class Item {
/*	class 中心下Item {
		//<userdata name="text"><![CDATA[飞毛腿铁岭中心]]></userdata>
		private Map<String,String> userdate ;
		private String test ;
		private String id ;
		private String open ;
		...
		
	}*/
	/*<item text="VVVCCC23"
			id="402862813d444396013d4451ae14000bci3d71755782eb4c9bb4df71f3870286bf"
			open="" im0="" im1="" im2="" child="1" disabled="" checked=""
			nocheckbox="">
			<userdata name="text"><![CDATA[VVVCCC23]]></userdata>
			<userdata name="type"><![CDATA[center]]></userdata>
			<userdata name="id"><![CDATA[402862813d444396013d4451ae14000b]]></userdata>
		</item>*/
	
	private Map<String,String> userdate;
	private String test;
	private String id;
	private String type;
	private String item;
	public Map<String, String> getUserdate() {
		return userdate;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public void setUserdate(Map<String, String> userdate) {
		this.userdate = userdate;
	}
	public String getTest() {
		return test;
	}
	public void setTest(String test) {
		this.test = test;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

}
