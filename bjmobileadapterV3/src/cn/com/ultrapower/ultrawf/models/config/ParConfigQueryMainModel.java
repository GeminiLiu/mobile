package cn.com.ultrapower.ultrawf.models.config;

import cn.com.ultrapower.ultrawf.share.UnionConditionForSql;

public class ParConfigQueryMainModel {

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
	
	public String getWhereSql(String p_TblAliasName)
	{
		StringBuffer sqlString = new StringBuffer();
		
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"Code",getCode()));
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"Confdesc",getConfdesc()));
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"Colcount",getColcount()));
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"Lablepercent",getLablepercent()));
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"Inputpercent",getInputpercent()));
		
		
		return sqlString.toString();

	}
	
	//返回排序字符串
	public String getOrderbyFiledNameString() 
	{
		String strRe="";
		if(!OrderbyFiledNameString.trim().equals(""))
		{
			//如果升序
			if(OrderByType==0)
				strRe=" order by "+OrderbyFiledNameString;
			else
			{
				String[] strAry =OrderbyFiledNameString.split(",");
				
				for(int index=0;index<strAry.length;index++)
				{	if(strRe.trim().equals(""))
						strRe+= strAry[index]+" desc";
					else
						strRe+=","+ strAry[index]+" desc";
				}
				strRe=" order by "+strRe;
			}//if(OrderByType==0)
		}
		return (strRe);
	}	
	//用于排序字段的名称
	private String OrderbyFiledNameString="";
	//排序类型 0升序　否则为降序
	private int OrderByType=0;
	/**
	 * 设置排序字段
	 * @param p_strOrderByFiledNameString
	 * @param p_intOrderByType 0 升序 否则为降序
	 */
	public void setOrderbyFiledNameString(String p_strOrderByFiledNameString,int p_intOrderByType) {
		OrderbyFiledNameString = p_strOrderByFiledNameString;
		OrderByType=p_intOrderByType;
	}	
	public void setOrderbyFiledNameString(String p_strOrderByFiledNameString) {
		OrderbyFiledNameString = p_strOrderByFiledNameString;
		OrderByType=0;
	}		
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
