package daiwei.mobile.util.TempletUtil.TMPModelXJ;

import java.util.HashMap;
/**
 * 巡检模版实体类
 * @author admin
 *
 */
public class RecordInfo{
	private String distance;
	private String objectId;
	private String patrolObject;
	private HashMap<String,Content> contents;
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	public String getPatrolObject() {
		return patrolObject;
	}
	public void setPatrolObject(String patrolObject) {
		this.patrolObject = patrolObject;
	}
	public HashMap<String, Content> getContents() {
		return contents;
	}
	public void setContents(HashMap<String, Content> contents) {
		this.contents = contents;
	}
}