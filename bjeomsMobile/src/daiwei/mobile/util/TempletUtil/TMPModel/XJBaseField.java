package daiwei.mobile.util.TempletUtil.TMPModel;

import java.util.List;

/**
 * 巡检工单详情页 1
 * @author 都 3/26
 *
 */
public class XJBaseField {
	
	private String objectId;
	private List<XJFieldGroup> xjFieldGroup;    
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}	
	public List<XJFieldGroup> getXjFieldGroup() {
		return xjFieldGroup;
	}
	public void setXjFieldGroup(List<XJFieldGroup> xjFieldGroup) {
		this.xjFieldGroup = xjFieldGroup;
	}

}
