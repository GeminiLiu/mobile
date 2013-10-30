package daiwei.mobile.animal;

import java.io.Serializable;
import java.util.List;
/**
 * 派发树对象类
 * @author admin
 *
 */
public class Item implements Serializable {
	String text;//派发对象名称
	String type;//派发对象类型  team、  person
	String id;//派发对象编号
	String loginname;
	boolean nocheckbox;//1：代表不可以被选中
	List<Item> child;//子节点对象列表
	Item parentItem;//父节点对象
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	public boolean getNocheckbox() {
		return nocheckbox;
	}
	public void setNocheckbox(boolean nocheckbox) {
		this.nocheckbox = nocheckbox;
	}
	public List<Item> getChild() {
		return child;
	}
	public void setChild(List<Item> child) {
		this.child = child;
	}
	public Item getParentItem() {
		return parentItem;
	}
	public void setParentItem(Item parentItem) {
		this.parentItem = parentItem;
	}
	
}
