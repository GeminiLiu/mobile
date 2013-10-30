package cn.com.ultrapower.ultrawf.share;

public class UnionConditionForSql {
	
	 
	/**
	 * 生成查询Sql的查询条件	 * @param p_TblPrefix　表别名：可以不为空
	 * @param p_strFiled　表字段名
	 * @param p_strFiledValue 字段值
	 * @return
	 */
	public static String getStringFiledSql(String p_TblPrefix,String p_strFiled,String p_strFiledValue)
	{
		if(p_TblPrefix==null)
			p_TblPrefix="";
		if(!p_TblPrefix.trim().equals(""))
			p_TblPrefix+=".";
		String strFiled=p_TblPrefix+p_strFiled;
		String keyWord;
		String sqlWhere="";
		if(p_strFiledValue==null)
			return "";
		
		if(!p_strFiledValue.equals(""))
		{
			String[] SplAry=p_strFiledValue.split(":");
			if(SplAry.length>=1)
			{
				keyWord=SplAry[0];
				keyWord=keyWord.trim().toUpperCase();
			}
			else
				keyWord="";
			
			//NULL:表示查询条件为某字段为NULL或''
			if(keyWord.equals("NULL"))
			{
				sqlWhere=" and ("+strFiled+"='' or "+strFiled+" is null) ";
			}
			//NOTNULL:表示查询条件为某字段不为NULL或''
			else if(keyWord.equals("NOTNULL"))
			{
				//sqlWhere=" and ("+strFiled+" is not null) ";
				sqlWhere=" and ("+strFiled+">'0') ";
			}
			//表示
			else if(keyWord.equals("LIKEFILD"))
			{
				sqlWhere=" and "+p_strFiledValue;
				sqlWhere=sqlWhere.replaceAll("LIKEFILD:","");
				sqlWhere=sqlWhere.replaceAll("DB_FIELD_NAME",strFiled);
				
			}
			//表示某字段的like查询
			else if(keyWord.equals("LIKE"))
			{
				sqlWhere=p_strFiledValue;
				sqlWhere=sqlWhere.replaceAll("LIKE:","");
				if(!sqlWhere.trim().equals(""))
					sqlWhere=" and "+strFiled+" like '%"+sqlWhere+"%'";
				//System.out.println("sqlWhere:"+sqlWhere);
			}
			else if(keyWord.equals("NOT"))
			{
				//sqlWhere=p_strFiledValue;
				String strValue=p_strFiledValue;
				strValue=strValue.replaceAll("NOT:","");
				if(strValue.trim().equals(""))
					return "";
				String[] valAry=strValue.split(",");
				for(int j=0;j<valAry.length;j++)
				{
					if(!valAry[j].trim().equals(""))
					{
						if(sqlWhere.trim().equals(""))
						{
							if(valAry[j].equals("NULL"))
							{
								sqlWhere=strFiled+">'0'";
							}else
							{
								sqlWhere=strFiled+"<>'"+valAry[j]+"'";
							}							
						}
						else
						{
							if(valAry[j].equals("NULL"))
							{
								sqlWhere+=" and "+strFiled+">'0'";
							}else
							{
								sqlWhere+=" and "+strFiled+"<>'"+valAry[j]+"'";
							}							
						}
					}//if(!valAry[j].trim().equals(""))
					
				}//for(int j=0;j<valAry.length;j++)
				
				sqlWhere=" and ("+sqlWhere+") ";
				/*
				if(!sqlWhere.trim().equals(""))
				{
					String[] valAry=strValue.split(",");
					
					if(sqlWhere.trim().toUpperCase().equals("NULL"))
						sqlWhere=" and "+strFiled+">'0'";
					else
						sqlWhere=" and "+strFiled+"<>'"+sqlWhere+"'";
				}
				*/
			}
			else if(keyWord.equals("ISNULL"))
			{
				sqlWhere=p_strFiledValue;
				sqlWhere=sqlWhere.replaceAll("ISNULL:","");
				//if(!sqlWhere.trim().equals(""))
				//{
					sqlWhere=" and "+strFiled+" is null ";
				//}				
			}
			//或条件			else if(keyWord.equals("OR"))
			{	
				String strValue=p_strFiledValue;
				strValue=strValue.replaceAll("OR:","");
				if(strValue.trim().equals(""))
					return "";
				String[] valAry=strValue.split(",");
				
				for(int j=0;j<valAry.length;j++)
				{
					if(!valAry[j].trim().equals(""))
					{
						if(sqlWhere.trim().equals(""))
						{
							if(valAry[j].equals("NULL"))
							{
								sqlWhere=strFiled+" is null";
							}else
							{
							    sqlWhere=strFiled+"='"+valAry[j]+"'";
							}
							
						}
						else{
							if(valAry[j].equals("NULL"))
							{
								sqlWhere+=" or "+strFiled+" is null";
							}else
							{
								sqlWhere+=" or "+strFiled+"='"+valAry[j]+"'";
							}
						}
					}//if(!valAry[j].trim().equals(""))
				}//for(int j=0;j<valAry.length;j++)
				sqlWhere=" and ("+sqlWhere+") ";
			}
			else if(keyWord.equals("IN"))
			{
				String strValue=p_strFiledValue;
				strValue=strValue.replaceAll("IN:","");	
				if(strValue.trim().equals(""))
					return "";
				String[] valAry=strValue.split(",");
				
				if(valAry.length==1)
				{
					
					sqlWhere=" and "+strFiled+"='"+valAry[0]+"'";
				}
				else
				{
					for(int j=0;j<valAry.length;j++)
					{
						if(!valAry[j].trim().equals(""))
						{
							if(sqlWhere.trim().equals(""))
								sqlWhere="'"+valAry[j]+"'";	
							else
								sqlWhere+=" ,'"+valAry[j]+"'";
						}
					}
					sqlWhere=" and "+strFiled+" in ("+sqlWhere+") ";
				}
								
				
			}
			else if(keyWord.equals(">="))
			{
				sqlWhere=p_strFiledValue;
				sqlWhere=sqlWhere.replaceAll(">=:","");
				if(!sqlWhere.trim().equals(""))
					sqlWhere=" and "+strFiled+" >='"+sqlWhere+"'";				
			}
			else if(keyWord.equals(">"))
			{
				sqlWhere=p_strFiledValue;
				sqlWhere=sqlWhere.replaceAll(">:","");
				if(!sqlWhere.trim().equals(""))
					sqlWhere=" and "+strFiled+" >'"+sqlWhere+"'";				
			}			
			else if(keyWord.equals("<="))
			{
				sqlWhere=p_strFiledValue;
				sqlWhere=sqlWhere.replaceAll("<=:","");
				if(!sqlWhere.trim().equals(""))
					sqlWhere=" and "+strFiled+"<='"+sqlWhere+"'";				
			}
			else if(keyWord.equals("<"))
			{
				sqlWhere=p_strFiledValue;
				sqlWhere=sqlWhere.replaceAll("<:","");
				if(!sqlWhere.trim().equals(""))
					sqlWhere=" and "+strFiled+"<'"+sqlWhere+"'";				
			}
			else if(keyWord.equals("="))
			{
				sqlWhere=p_strFiledValue;
				sqlWhere=sqlWhere.replaceAll("=:","");
				if(!sqlWhere.trim().equals(""))
					sqlWhere=" and "+strFiled+"='"+sqlWhere+"'";				
			}			
			else if(keyWord.equals("SQLIN"))
			{
				//IN 的sql语句
				sqlWhere=p_strFiledValue;
				sqlWhere=sqlWhere.replaceAll("SQLIN:","");
				if(!sqlWhere.trim().equals(""))
					sqlWhere=" and "+strFiled+" in ("+sqlWhere+")";	
			}			
			else if(keyWord.equals("ORLIKE"))
			{
				String strValue=p_strFiledValue;
				strValue=strValue.replaceAll("ORLIKE:","");
				if(strValue.trim().equals(""))
					return "";
				String[] valAry=strValue.split(",");
				
				for(int j=0;j<valAry.length;j++)
				{
					if(!valAry[j].trim().equals(""))
					{
						if(sqlWhere.trim().equals(""))
						{
							if(valAry[j].equals("NULL"))
							{
								sqlWhere=strFiled+" is null";
							}else
							{
							    sqlWhere=strFiled+" like '%"+valAry[j]+"%'";
							}
							
						}
						else{
							if(valAry[j].equals("NULL"))
							{
								sqlWhere+=" or "+strFiled+" is null";
							}else
							{
								sqlWhere+=" or "+strFiled+" like '%"+valAry[j]+"%'";
							}
						}
					}//if(!valAry[j].trim().equals(""))
				}//for(int j=0;j<valAry.length;j++)
				sqlWhere=" and ("+sqlWhere+") ";				
			}	
			
			else
			{
				sqlWhere=" and "+strFiled+"='"+p_strFiledValue+"'";
				
			}//if(keyWord.equals("NULL"))
			
		}//if(!p_strFiledValue.equals(""))
		
		return sqlWhere;
	}
	/**
	 * 生成查询Sql的查询条件
	 * @param p_TblPrefix
	 * @param p_strFiled
	 * @param p_lngFiledValue
	 * @return
	 */
	public static String getLongTimeFiedSql(String p_TblPrefix,String p_strFiled,long p_lngFiledValue)
	{
		if(p_TblPrefix==null)
			p_TblPrefix="";
		if(!p_TblPrefix.trim().equals(""))
			p_TblPrefix+=".";	
		String sqlWhere="";
		//时间的0 为默认值
		if(p_lngFiledValue==0)
			return "";
		sqlWhere=" and "+p_TblPrefix+p_strFiled+"='"+String.valueOf(p_lngFiledValue)+"'";
		return sqlWhere;		
	}
	/**
	 * 生成查询Sql的查询条件
	 * @param p_TblPrefix
	 * @param p_strFiled
	 * @param p_intFiledValue
	 * @return
	 */
	public static String getIntFiedSql(String p_TblPrefix,String p_strFiled,int p_intFiledValue)
	{
		/*if(p_TblPrefix==null)
			p_TblPrefix="";
		if(!p_TblPrefix.trim().equals(""))
			p_TblPrefix+=".";	*/
		String sqlWhere="";
		//整形的999 为默认值，即没进行赋值		if(p_intFiledValue==999)
			sqlWhere="";
		else
			sqlWhere=getStringFiledSql(p_TblPrefix,p_strFiled,String.valueOf(p_intFiledValue));
		//sqlWhere=" and "+p_TblPrefix+p_strFiled+"='"+String.valueOf(p_intFiledValue)+"'";
		return sqlWhere;		
	}	
	
	

}
