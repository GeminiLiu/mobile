package daiwei.mobile.animal;
import java.io.Serializable;

public class LoginModel implements Serializable{
	private String gdNum;//工单个数
	private String xjNum;//巡检个数
	public String getGdNum() {
		return gdNum;
	}
	public void setGdNum(String gdNum) {
		this.gdNum = gdNum;
	}
	public String getXjNum() {
		return xjNum;
	}
	public void setXjNum(String xjNum) {
		this.xjNum = xjNum;
	}
}