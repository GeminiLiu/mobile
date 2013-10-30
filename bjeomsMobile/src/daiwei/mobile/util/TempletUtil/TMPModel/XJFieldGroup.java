package daiwei.mobile.util.TempletUtil.TMPModel;

import java.io.Serializable;
/**
 * 巡检工单详情页 2
 * @author 都 3/26
 *
 */
public class XJFieldGroup implements Serializable{
private String id;
//private List<XJField> xjGroup;
private String contentId;
private String taskContentId;
private String dataValue;
public String getTaskContentId() {
	return taskContentId;
}
public void setTaskContentId(String taskContentId) {
	this.taskContentId = taskContentId;
}
public String getDataValue() {
	return dataValue;
}
public void setDataValue(String dataValue) {
	this.dataValue = dataValue;
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
//public List<XJField> getXjGroup() {
//	return xjGroup;
//}
//public void setXjGroup(List<XJField> xjGroup) {
//	this.xjGroup = xjGroup;
//}
public String getContentId() {
	return contentId;
}
public void setContentId(String contentId) {
	this.contentId = contentId;
}

}
