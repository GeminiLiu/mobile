package cn.com.ultrapower.ultrawf.share.queryanalyse;

import cn.com.ultrapower.ultrawf.share.FormatString;

public class PariseUntil {

	public  ParFiledInfo setDisplayFieldInfo(String fieldID,String fieldName)
	{
		ParFiledInfo m_ParFiledInfo=new ParFiledInfo();
		m_ParFiledInfo.setFieldID(fieldID);
		m_ParFiledInfo.setFieldName(fieldName);
		m_ParFiledInfo.setOptionType(ParFiledOptionType.isDisplay);
		
		return m_ParFiledInfo;
	}	
	public  ParFiledInfo setQueryFieldInfo(String fielID,String value)
	{
		value=FormatString.CheckNullString(value);
		if(value.equals(""))
			return null ;
		String[] SplAry=value.split(":");
		String keyWord="";
		if(SplAry.length>=1)
		{
			keyWord=FormatString.CheckNullString(SplAry[0]);
			keyWord=keyWord.trim().toUpperCase();
		}
		else
			keyWord="";
		if(!keyWord.equals(""))
			value=value.replaceAll(keyWord+":","");
		if(value.equals(""))
		{
			if(!keyWord.equals("ISNULL") )
			{	 
				if(!keyWord.equals("ISNOTNULL") )
					return null;
			}
		}
		
		ParFiledInfo parFiledInfo=new ParFiledInfo();
		parFiledInfo.setFieldID(fielID);
		parFiledInfo.setFiledValue(value);
		parFiledInfo.setOptionType(ParFiledOptionType.isParmeter);
		if(!keyWord.equals(""))
		{
			if(keyWord.equals("ISNULL"))
			{
				//parFiledInfo.setFiledValue("NULL");
				parFiledInfo.setCompareLogic(ParCompareLogic.isNull);
			}
			else if(keyWord.equals("ISNOTNULL"))
			{
				//parFiledInfo.setCompareLogic(ParCompareLogic.isLike);
				//parFiledInfo.setFiledValue("NULL");
				parFiledInfo.setCompareLogic(ParCompareLogic.isNotNull );
			}
			else if(keyWord.equals("LIKE"))
				parFiledInfo.setCompareLogic(ParCompareLogic.isLike);
			else if(keyWord.equals("LEFTLIKE"))
				parFiledInfo.setCompareLogic(ParCompareLogic.isLeftLike);
			else if(keyWord.equals("RIGHTIKE"))
				parFiledInfo.setCompareLogic(ParCompareLogic.isRightLike);
			else if(keyWord.equals("NOT")||keyWord.equals("<>"))
				parFiledInfo.setCompareLogic(ParCompareLogic.isNotEqualto);
			else if(keyWord.equals("OR"))
				parFiledInfo.setCompareLogic(ParCompareLogic.isOR); 
			else if(keyWord.equals("IN"))
				parFiledInfo.setCompareLogic(ParCompareLogic.isOR);
			else if(keyWord.equals(">="))
				parFiledInfo.setCompareLogic(ParCompareLogic.isMorethanorEqualto);			
			else if(keyWord.equals(">"))
				parFiledInfo.setCompareLogic(ParCompareLogic.isMorethan);	
			else if(keyWord.equals("<="))
				parFiledInfo.setCompareLogic(ParCompareLogic.isLessthanorEqualto);
			else if(keyWord.equals("<"))
				parFiledInfo.setCompareLogic(ParCompareLogic.isLessthan);	
			else if(keyWord.equals("="))
				parFiledInfo.setCompareLogic(ParCompareLogic.isEquals);	
			else if(keyWord.equals("ORLIKE"))
				parFiledInfo.setCompareLogic(ParCompareLogic.isORLike);	
			
		}
		
		return parFiledInfo;
		
		
	}	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String value="ISNULL";
		if(!value.equals("ISNULL")||!value.equals("ISNOTNULL"))
		{
			System.out.println(value);
		}
	}

}
