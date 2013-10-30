package cn.com.ultrapower.ultrawf.models.config;

public class ParTplDealLinkModel {


//	属性设置区域--Begin--
	private  String LinkID;//
	private  String LinkType;//
	private  String BaseTplID;//
	private  String BaseTplSchema;//
	private  String StartPhase;//
	private  String EndPhase;//
	private  String Desc;//
	private  String Flag00IsAvail;//
	private  String Flag21Required;//
	private  String LinkNo;//
//	1 LinkID 
	public String getLinkID()
	{
	   return LinkID;
	}
	public void   setLinkID(String p_LinkID)
	{
	   LinkID=p_LinkID;
	}
//	700020044 LinkType 
	public String getLinkType()
	{
	   return LinkType;
	}
	public void   setLinkType(String p_LinkType)
	{
	   LinkType=p_LinkType;
	}
//	700020501 BaseTplID 
	public String getBaseTplID()
	{
	   return BaseTplID;
	}
	public void   setBaseTplID(String p_BaseTplID)
	{
	   BaseTplID=p_BaseTplID;
	}
//	700020502 BaseTplSchema 
	public String getBaseTplSchema()
	{
	   return BaseTplSchema;
	}
	public void   setBaseTplSchema(String p_BaseTplSchema)
	{
	   BaseTplSchema=p_BaseTplSchema;
	}
//	700020503 StartPhase 
	public String getStartPhase()
	{
	   return StartPhase;
	}
	public void   setStartPhase(String p_StartPhase)
	{
	   StartPhase=p_StartPhase;
	}
//	700020504 EndPhase 
	public String getEndPhase()
	{
	   return EndPhase;
	}
	public void   setEndPhase(String p_EndPhase)
	{
	   EndPhase=p_EndPhase;
	}
//	700020505 Desc 
	public String getDesc()
	{
	   return Desc;
	}
	public void   setDesc(String p_Desc)
	{
	   Desc=p_Desc;
	}
//	700020506 Flag00IsAvail 
	public String getFlag00IsAvail()
	{
	   return Flag00IsAvail;
	}
	public void   setFlag00IsAvail(String p_Flag00IsAvail)
	{
	   Flag00IsAvail=p_Flag00IsAvail;
	}
//	700020507 Flag21Required 
	public String getFlag21Required()
	{
	   return Flag21Required;
	}
	public void   setFlag21Required(String p_Flag21Required)
	{
	   Flag21Required=p_Flag21Required;
	}
//	700020510 LinkNo 
	public String getLinkNo()
	{
	   return LinkNo;
	}
	public void   setLinkNo(String p_LinkNo)
	{
	   LinkNo=p_LinkNo;
	}
//	属性设置区域--End--	
	
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

}
