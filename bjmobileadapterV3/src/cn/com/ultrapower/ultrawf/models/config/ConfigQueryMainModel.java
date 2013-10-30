package cn.com.ultrapower.ultrawf.models.config;

public class ConfigQueryMainModel {

	private String Code;
	private String Confdesc;
	private String Colcount;
	private String Lablepercent;
	private String Inputpercent;
	
	
	public String getCode() {
		return Code;
	}


	public void setCode(String code) {
		Code = code;
	}


	public String getColcount() {
		return Colcount;
	}


	public void setColcount(String colcount) {
		Colcount = colcount;
	}


	public String getConfdesc() {
		if(Colcount==null)
			return "";
		else
			return Confdesc;
	}


	public void setConfdesc(String confdesc) {
		Confdesc = confdesc;
	}


	public String getInputpercent() {
		return Inputpercent;
	}


	public void setInputpercent(String inputpercent) {
		Inputpercent = inputpercent;
	}


	public String getLablepercent() {
		return Lablepercent;
	}


	public void setLablepercent(String lablepercent) {
		Lablepercent = lablepercent;
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
