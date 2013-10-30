package cn.com.ultrapower.eoms.common.basedao;

public class Navigation {
	  private String title;
	  private String url;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String toString(){
		return "<"+title+","+url+">";
	}
		
	}