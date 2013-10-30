package com.ultrapower.eoms.common.model;

/**
 * AbstractAppResource entity provides the base persistence definition of the
 * AppResource entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractAppResource implements java.io.Serializable {

	// Fields

	private Long resId;
	private String resName;
	private String url;
	private Long category;
	private Long log;
	private Long status;
	private Long parentId;
	private String dnid;
	private String memo;

	// Constructors

	/** default constructor */
	public AbstractAppResource() {
	}

	/** full constructor */
	public AbstractAppResource(String resName, String url, Long category,
			Long log, Long status, Long parentId, String dnid, String memo) {
		this.resName = resName;
		this.url = url;
		this.category = category;
		this.log = log;
		this.status = status;
		this.parentId = parentId;
		this.dnid = dnid;
		this.memo = memo;
	}

	// Property accessors

	public Long getResId() {
		return this.resId;
	}

	public void setResId(Long resId) {
		this.resId = resId;
	}

	public String getResName() {
		return this.resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getCategory() {
		return this.category;
	}

	public void setCategory(Long category) {
		this.category = category;
	}

	public Long getLog() {
		return this.log;
	}

	public void setLog(Long log) {
		this.log = log;
	}

	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Long getParentId() {
		return this.parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getDnid() {
		return this.dnid;
	}

	public void setDnid(String dnid) {
		this.dnid = dnid;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}