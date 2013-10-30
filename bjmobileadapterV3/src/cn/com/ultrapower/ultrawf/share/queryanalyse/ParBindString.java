package cn.com.ultrapower.ultrawf.share.queryanalyse;

public class ParBindString {
 
	private String BingSqlString="";
	private String[] BindValue;
	
	public String[] getBindValue() {
		return BindValue;
	}

	public void setBindValue(String[] bindValue) {
		BindValue = bindValue;
	}

	public String getBingSqlString() {
		if(BingSqlString==null)
			BingSqlString="";
		return BingSqlString;
	}

	public void setBingSqlString(String bingString) {
		BingSqlString = bingString;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
