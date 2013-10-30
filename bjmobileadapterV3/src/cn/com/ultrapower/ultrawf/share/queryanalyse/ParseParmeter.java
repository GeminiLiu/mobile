package cn.com.ultrapower.ultrawf.share.queryanalyse;
import java.util.*;

import cn.com.ultrapower.ultrawf.share.FormatString;
/**
 * 
 * @author 徐发球
 * @date 2007-11-10
 */
public class ParseParmeter {

	
	List parFiledInfoList=null;
	private String SelectFiled=null;
	private String WhereSql=null;
	private ParseParmeter SubParseParmeter=null;
	
	 
	public ParseParmeter getSubParseParmeter() {
		return SubParseParmeter;
	}
	public void setSubParseParmeter(ParseParmeter subParseParmeter) {
		SubParseParmeter = subParseParmeter;
	}
	public String getSelectFiled()
	{
		if(SelectFiled==null)
			SelectFiled="";
		return SelectFiled;
	}
	public String getWhereSql()
	{
		//String whereSql="";
		if(WhereSql==null)
			WhereSql="";
		return WhereSql;
	}
	
	String[] parValueAry=null;
	public String[] getParmeterValue()
	{
		
		return parValueAry;
	}
	
	
	
	public ParseParmeter(List p_FiledInfoList)
	{
		parFiledInfoList=p_FiledInfoList;
		ParseFiled();
	}
	
	private void ParseFiled()
	{
	
		int listCount=0;
		if(parFiledInfoList!=null)
			listCount=parFiledInfoList.size();
		StringBuffer strSelect=new StringBuffer();
		StringBuffer strWhere=new StringBuffer();
		//String[] strValueAry=new String[listCount];
		
		
		//Hashtable hashWhere=new Hashtable();
		Hashtable hashValue=new Hashtable();
		
		boolean isSelect=false;
		int optionType; 
		int compareLogic;
		int aryIndex=0;
		try{
			
			for(int i=0;i<listCount;i++)
			{
				
				ParFiledInfo m_ParFiledInfo=(ParFiledInfo)parFiledInfoList.get(i);
				optionType=m_ParFiledInfo.getOptionType();
				String fieldID=m_ParFiledInfo.getFieldID();
				if (optionType== ParFiledOptionType.isDisplay || optionType== ParFiledOptionType.isDisplayAndParmeter )
				{
					
					if(isSelect)
						strSelect.append(","+fieldID+" as "+m_ParFiledInfo.getFieldName());
					else
						strSelect.append(fieldID+" as "+m_ParFiledInfo.getFieldName());
					isSelect=true;
				} 
				if (optionType == ParFiledOptionType.isParmeter || optionType == ParFiledOptionType.isDisplayAndParmeter)
				{	
					compareLogic=m_ParFiledInfo.getCompareLogic();
					String value=m_ParFiledInfo.getFiledValue().trim();
					if(!value.equals("")||compareLogic==ParCompareLogic.isNull|| compareLogic==ParCompareLogic.isNotNull )
					{
						
						if(compareLogic==ParCompareLogic.isLike)
						{
							if(m_ParFiledInfo.getGroupLogic()==GroupLogic.isLeftParenthesis)
							{
								strWhere.append(m_ParFiledInfo.getRelateLogic()+" ("+fieldID+" like ?");
							}
							else if(m_ParFiledInfo.getGroupLogic()==GroupLogic.isRightParenthesis)
							{
								strWhere.append(m_ParFiledInfo.getRelateLogic()+fieldID+" like ? )");
							}
							else
								strWhere.append(m_ParFiledInfo.getRelateLogic()+fieldID+" like ?");						
							//strValueAry[i]="%"+value+"%";
							aryIndex++;
							hashValue.put(String.valueOf(aryIndex),"%"+value+"%");
						}
						else if(compareLogic==ParCompareLogic.isLessthan)
						{
							strWhere.append(m_ParFiledInfo.getRelateLogic()+fieldID+"<?");						
							//strValueAry[i]=value;
							aryIndex++;
							hashValue.put(String.valueOf(aryIndex),value);
						}
						else if(compareLogic==ParCompareLogic.isLessthanorEqualto)
						{
							strWhere.append(m_ParFiledInfo.getRelateLogic()+fieldID+"<=?");						
							//strValueAry[i]=value;
							aryIndex++;
							hashValue.put(String.valueOf(aryIndex),value);
						}
						else if(compareLogic==ParCompareLogic.isMorethan)
						{
							strWhere.append(m_ParFiledInfo.getRelateLogic()+fieldID+">?");						
							//strValueAry[i]=value;
							aryIndex++;
							hashValue.put(String.valueOf(aryIndex),value);
						}
						else if(compareLogic==ParCompareLogic.isMorethanorEqualto)
						{
							strWhere.append(m_ParFiledInfo.getRelateLogic()+fieldID+">=?");						
							//strValueAry[i]=value;
							aryIndex++;
							hashValue.put(String.valueOf(aryIndex),value);
						}	
						else if(compareLogic==ParCompareLogic.isOR)
						{
							String[] valAry=value.split(",");
							String sqlWhere="";
							for(int j=0;j<valAry.length;j++)
							{
								if(!valAry[j].trim().equals(""))
								{
									if(sqlWhere.trim().equals(""))
									{
										if(valAry[j].equals("NULL"))
										{
											sqlWhere=fieldID+" is null";
										}else
										{
										    sqlWhere=fieldID+"=?";
										    aryIndex++;
										    hashValue.put(String.valueOf(aryIndex),valAry[j]);
										}
										
									}
									else{
										if(valAry[j].equals("NULL"))
										{
											sqlWhere+=" or "+fieldID+" is null";
										}else
										{
											sqlWhere+=" or "+fieldID+"=?";
											aryIndex++;
											hashValue.put(String.valueOf(aryIndex),valAry[j]);
										}
									}
								}//if(!valAry[j].trim().equals(""))
							}//for(int j=0;j<valAry.length;j++)
							strWhere.append(" and ("+sqlWhere+")");						
							
						}	
						else if(compareLogic==ParCompareLogic.isORLike)
						{
							String sqlWhere="";
							String[] valAry=value.split(",");
							
							for(int j=0;j<valAry.length;j++)
							{
								if(!valAry[j].trim().equals(""))
								{
									if(sqlWhere.trim().equals(""))
									{
										if(valAry[j].equals("NULL"))
										{
											sqlWhere=fieldID+" is null";
										}else
										{
										    sqlWhere=fieldID+" like ?";
										    aryIndex++;
										    hashValue.put(String.valueOf(aryIndex),"%"+valAry[j]+"%");
										}
										
									}
									else{
										if(valAry[j].equals("NULL"))
										{
											sqlWhere+=" or "+fieldID+" is null";
										}else
										{
											sqlWhere+=" or "+fieldID+" like ?";
											aryIndex++;
											hashValue.put(String.valueOf(aryIndex),"%"+valAry[j]+"%");
										}
									}
								}//if(!valAry[j].trim().equals(""))
							}//for(int j=0;j<valAry.length;j++)
							strWhere.append(" and ("+sqlWhere+") ");								
							
						}
						else if(compareLogic==ParCompareLogic.isNotEqualto)
						{
							strWhere.append(m_ParFiledInfo.getRelateLogic()+" ("+fieldID+">? or "+fieldID+"<?)");
							aryIndex++;
							hashValue.put(String.valueOf(aryIndex),value);
							aryIndex++;
							hashValue.put(String.valueOf(aryIndex),value);
						}
						else if(compareLogic==ParCompareLogic.isEquals)
						{
							String m_value=value.toUpperCase();
							if(m_value.equals("NULL"))
								strWhere.append(m_ParFiledInfo.getRelateLogic()+fieldID+" is null ");
							if(m_value.equals("NOTNULL"))
							{
								strWhere.append(m_ParFiledInfo.getRelateLogic()+fieldID+">='0'");
							}
							else
							{
								strWhere.append(m_ParFiledInfo.getRelateLogic()+fieldID+"=?");
								aryIndex++;
								hashValue.put(String.valueOf(aryIndex),value);
							}
						}
						else if(compareLogic==ParCompareLogic.isNull)
						{
							strWhere.append(m_ParFiledInfo.getRelateLogic()+fieldID+" is null ");
						}
						else if(compareLogic==ParCompareLogic.isNotNull)
						{
							strWhere.append(m_ParFiledInfo.getRelateLogic()+fieldID+" is not null ");
						}						
						else
						{
							strWhere.append(m_ParFiledInfo.getRelateLogic()+fieldID+"=?");						
							//strValueAry[i]=value;
							aryIndex++;
							hashValue.put(String.valueOf(aryIndex),value);
						}
						
						
					}//if(!value.equals(""))					
					

				}////if (optionType == ParFiledOptionType.isDisplay)
				/*
				else if (optionType == ParFiledOptionType.isDisplayAndParmeter)
				{
					
					if(isSelect)
						strSelect.append(","+fieldID+" as "+m_ParFiledInfo.getFieldName());
					else
						strSelect.append(fieldID+" as "+m_ParFiledInfo.getFieldName());
					isSelect=true;
					String value=m_ParFiledInfo.getFiledValue().trim();
					if(!value.equals(""))
					{
						
						if(compareLogic==ParCompareLogic.isLike)
						{
							if(m_ParFiledInfo.getGroupLogic()==GroupLogic.isLeftParenthesis)
							{
								strWhere.append(m_ParFiledInfo.getRelateLogic()+" ("+fieldID+" like ?");
							}
							else if(m_ParFiledInfo.getGroupLogic()==GroupLogic.isRightParenthesis)
							{
								strWhere.append(m_ParFiledInfo.getRelateLogic()+fieldID+" like ? )");
							}
							else
								strWhere.append(m_ParFiledInfo.getRelateLogic()+fieldID+" like ?");						
							//strValueAry[i]="%"+value+"%";
							aryIndex++;
							hashValue.put(String.valueOf(aryIndex),"%"+value+"%");
						}
						else if(compareLogic==ParCompareLogic.isLessthan)
						{
							strWhere.append(m_ParFiledInfo.getRelateLogic()+fieldID+"<?");						
							//strValueAry[i]=value;
							aryIndex++;
							hashValue.put(String.valueOf(aryIndex),value);
						}
						else if(compareLogic==ParCompareLogic.isLessthanorEqualto)
						{
							strWhere.append(m_ParFiledInfo.getRelateLogic()+fieldID+"<=?");						
							//strValueAry[i]=value;
							aryIndex++;
							hashValue.put(String.valueOf(aryIndex),value);
						}
						else if(compareLogic==ParCompareLogic.isMorethan)
						{
							strWhere.append(m_ParFiledInfo.getRelateLogic()+fieldID+">?");						
							//strValueAry[i]=value;
							aryIndex++;
							hashValue.put(String.valueOf(aryIndex),value);
						}
						else if(compareLogic==ParCompareLogic.isMorethanorEqualto)
						{
							strWhere.append(m_ParFiledInfo.getRelateLogic()+fieldID+">=?");						
							//strValueAry[i]=value;
							aryIndex++;
							hashValue.put(String.valueOf(aryIndex),value);
						}	
						else if(compareLogic==ParCompareLogic.isOR)
						{
							String[] valAry=value.split(",");
							String sqlWhere="";
							for(int j=0;j<valAry.length;j++)
							{
								if(!valAry[j].trim().equals(""))
								{
									if(sqlWhere.trim().equals(""))
									{
										if(valAry[j].equals("NULL"))
										{
											sqlWhere=fieldID+" is null";
										}else
										{
										    sqlWhere=fieldID+"=?";
										    aryIndex++;
										    hashValue.put(String.valueOf(aryIndex),valAry[j]);
										}
										
									}
									else{
										if(valAry[j].equals("NULL"))
										{
											sqlWhere+=" or "+fieldID+" is null";
										}else
										{
											sqlWhere+=" or "+fieldID+"=?";
											aryIndex++;
											hashValue.put(String.valueOf(aryIndex),valAry[j]);
										}
									}
								}//if(!valAry[j].trim().equals(""))
							}//for(int j=0;j<valAry.length;j++)
							strWhere.append(" and ("+sqlWhere+")");						
							
						}						
						else
						{
							
							strWhere.append(m_ParFiledInfo.getRelateLogic()+fieldID+"=?");						
							//strValueAry[i]=value;
							aryIndex++;
							hashValue.put(String.valueOf(aryIndex),value);
						}
						
						
					}//if(!value.equals(""))	
					
				}//if (optionType == ParFiledOptionType.isDisplay)
				*/
			}//for(int i=0;i<listCount;i++)
		}catch(Exception ex)
		{
			System.out.println("Error ParseParmeter.ParseFiled: "+ex.getMessage());
			//ex.printStackTrace();
		}
		  
		SelectFiled=strSelect.toString();
		WhereSql=strWhere.toString();
		int parNum=0;
		if(hashValue!=null)
			parNum=hashValue.size();
		//参数值
		if(parNum>0) 
		{
			parValueAry=new String[parNum];
			for(int j=1;j<=parNum;j++)
			{
				parValueAry[j-1]=FormatString.FormatObjectToString(hashValue.get(String.valueOf(j)));
			}
		}//if(aryIndex<=0)
		

		
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Hashtable hsOwnFiled1=new Hashtable();
		
		try{
			hsOwnFiled1.put("ddd",null);
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
	}

}
