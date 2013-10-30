package cn.com.ultrapower.eoms.user.config.source.Bean;

public class SourceActionConfigBean {

	/**
	 * date 2006-11-10
	 * author shigang
	 * @param args
	 * @return void
	 */
	private String Id;
	private String sourceaction_typeflag;
	private String sourceaction_fieldValue;
	private String sourceaction_numValue;
	private String sourceaction_orderBy;
	
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getSourceaction_fieldValue() {
		return sourceaction_fieldValue;
	}
	public void setSourceaction_fieldValue(String sourceaction_fieldValue) {
		this.sourceaction_fieldValue = sourceaction_fieldValue;
	}
	public String getSourceaction_numValue() {
		return sourceaction_numValue;
	}
	public void setSourceaction_numValue(String sourceaction_numValue) {
		this.sourceaction_numValue = sourceaction_numValue;
	}
	public String getSourceaction_orderBy() {
		return sourceaction_orderBy;
	}
	public void setSourceaction_orderBy(String sourceaction_orderBy) {
		this.sourceaction_orderBy = sourceaction_orderBy;
	}
	public String getSourceaction_typeflag() {
		return sourceaction_typeflag;
	}
	public void setSourceaction_typeflag(String sourceaction_typeflag) {
		this.sourceaction_typeflag = sourceaction_typeflag;
	}

	

}