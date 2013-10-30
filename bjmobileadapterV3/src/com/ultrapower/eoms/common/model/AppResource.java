package com.ultrapower.eoms.common.model;


/**
 * AppResource entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class AppResource extends AbstractAppResource implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public AppResource() {
	}

	/** full constructor */
	public AppResource(String resName, String url, Long category, Long log,
			Long status, Long parentId, String dnid, String memo) {
		super(resName, url, category, log, status, parentId, dnid, memo);
	}

}
