package daiwei.mobile.util.TempletUtil.TMPModel;

import java.io.Serializable;

public class Field implements Serializable {
	private static final long serialVersionUID = -7060210523600424481L;
	private String id;
	private String type;//Lable
	private String row;
	private String dic;//代维管理员 代维小组
	private String text;
	
	//派发树变量
	private String assignType;
	private String corp;
	private String center;
	private String station;
	private String team;
	private String person;
	private String select;
	private String multi;
	
	//回复模板
	private String template;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRow() {
		return row;
	}
	public void setRow(String row) {
		this.row = row;
	}
	public String getDic() {
		return dic;
	}
	public void setDic(String dic) {
		this.dic = dic;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getAssignType() {
		return assignType;
	}
	public void setAssignType(String assignType) {
		this.assignType = assignType;
	}
	public String getCorp() {
		return corp;
	}
	public void setCorp(String corp) {
		this.corp = corp;
	}
	public String getCenter() {
		return center;
	}
	public void setCenter(String center) {
		this.center = center;
	}
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
	}
	public String getSelect() {
		return select;
	}
	public void setSelect(String select) {
		this.select = select;
	}
	public String getMulti() {
		return multi;
	}
	public void setMulti(String multi) {
		this.multi = multi;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
}