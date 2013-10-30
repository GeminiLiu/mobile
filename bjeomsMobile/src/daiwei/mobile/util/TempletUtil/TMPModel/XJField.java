package daiwei.mobile.util.TempletUtil.TMPModel;

import java.io.Serializable;
/**
 * 巡检实体类3
 * @author 都 3/26
 *
 */
public class XJField implements Serializable{
private String taskContendId;
private String maintainContent;
private String qualityStandard;
private String writeWay;
private String dataItem;
private String defaultValue;
private String detaValue;
public String getTaskContendId() {
	return taskContendId;
}
public void setTaskContendId(String taskContendId) {
	this.taskContendId = taskContendId;
}
public String getMaintainContent() {
	return maintainContent;
}
public void setMaintainContent(String maintainContent) {
	this.maintainContent = maintainContent;
}
public String getQualityStandard() {
	return qualityStandard;
}
public void setQualityStandard(String qualityStandard) {
	this.qualityStandard = qualityStandard;
}
public String getWriteWay() {
	return writeWay;
}
public void setWriteWay(String writeWay) {
	this.writeWay = writeWay;
}
public String getDataItem() {
	return dataItem;
}
public void setDataItem(String dataItem) {
	this.dataItem = dataItem;
}
public String getDefaultValue() {
	return defaultValue;
}
public void setDefaultValue(String defaultValue) {
	this.defaultValue = defaultValue;
}
public String getDetaValue() {
	return detaValue;
}
public void setDetaValue(String detaValue) {
	this.detaValue = detaValue;
}

}
