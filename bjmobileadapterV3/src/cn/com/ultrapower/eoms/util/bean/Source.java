package cn.com.ultrapower.eoms.util.bean;
/**
 * 将找个放入session中,是和source相关的一些属性.
 * @author LiamgYang
 * 2006-12-15
 */
public class Source {
	//资源的id
	private String sourceId ;
	//模块的id
	private String ModuleId;
	
	public String getModuleId() {
		return ModuleId;
	}

	public void setModuleId(String moduleId) {
		ModuleId = moduleId;
	}


	public String getSourceId() {
		return sourceId;
	}
	

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
}
