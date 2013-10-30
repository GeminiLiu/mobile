package cn.com.ultrapower.eoms.user.config.source.Bean;

public class SourceAttInfoBean {

	/**
	 * date 2006-11-13
	 * author shigang
	 * @param args
	 * @return void
	 */
	private String source_attname;//字段属性英文名
	private String source_attnamevalue;//字段值
	
	
	public String getsource_attname() {
		return source_attname;
	}
	public void setsource_attname(String source_attname) {
		this.source_attname = source_attname;
	}
	
	public String getsource_attnamevalue() {
		return source_attnamevalue;
	}
	public void setsource_attnamevalue(String source_attnamevalue) {
		this.source_attnamevalue = source_attnamevalue;
	}
}


