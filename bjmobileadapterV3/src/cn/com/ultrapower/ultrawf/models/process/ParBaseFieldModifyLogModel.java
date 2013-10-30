package cn.com.ultrapower.ultrawf.models.process;

import cn.com.ultrapower.ultrawf.share.UnionConditionForSql;

public class ParBaseFieldModifyLogModel {

	
	private int IsArchive=0;
	
	public int getIsArchive() {
			return IsArchive;
		}
		public void setIsArchive(int isArchive) {
			IsArchive = isArchive;
		}
	
//		属性设置区域--Begin--
		private  String Base_Field_ModifyLog_ID;
		private  String Base_Field_ModifyLog_BaseID;
		private  String Base_Field_ModifyLog_BaseSchema;
		private  String Base_Field_ModifyLog_ProcessID;
		private  String Base_Field_ModifyLog_ProcessType;
		private  String Base_Field_ModifyLog_field_ID;
		private  String Base_Field_ModifyLog_FieldDBName;
		private  String Base_Field_ModifyLog_Base_field_ShowName;
		private  String Base_Field_ModifyLog_Date;
		private  String Base_Field_ModifyLog_Dealer;
		private  String Base_Field_ModifyLog_DealerID;
		private  String Base_Field_ModifyLog_OldValue;
		private  String Base_Field_ModifyLog_NewValue;
//		1 Base_Field_ModifyLog_ID 本记录的唯一标识，创建是自动形成，无业务含义
		public String getBase_Field_ModifyLog_ID()
		{
		   return Base_Field_ModifyLog_ID;
		}
		public void   setBase_Field_ModifyLog_ID(String p_Base_Field_ModifyLog_ID)
		{
		   Base_Field_ModifyLog_ID=p_Base_Field_ModifyLog_ID;
		}
//		700021001 Base_Field_ModifyLog_BaseID 指向主工单记录的指针
		public String getBase_Field_ModifyLog_BaseID()
		{
		   return Base_Field_ModifyLog_BaseID;
		}
		public void   setBase_Field_ModifyLog_BaseID(String p_Base_Field_ModifyLog_BaseID)
		{
		   Base_Field_ModifyLog_BaseID=p_Base_Field_ModifyLog_BaseID;
		}
//		700021002 Base_Field_ModifyLog_BaseSchema 指向主工单记录的指针
		public String getBase_Field_ModifyLog_BaseSchema()
		{
		   return Base_Field_ModifyLog_BaseSchema;
		}
		public void   setBase_Field_ModifyLog_BaseSchema(String p_Base_Field_ModifyLog_BaseSchema)
		{
		   Base_Field_ModifyLog_BaseSchema=p_Base_Field_ModifyLog_BaseSchema;
		}
//		700021013 Base_Field_ModifyLog_ProcessID 指向主工单处理过程记录的指针
		public String getBase_Field_ModifyLog_ProcessID()
		{
		   return Base_Field_ModifyLog_ProcessID;
		}
		public void   setBase_Field_ModifyLog_ProcessID(String p_Base_Field_ModifyLog_ProcessID)
		{
		   Base_Field_ModifyLog_ProcessID=p_Base_Field_ModifyLog_ProcessID;
		}
//		700021011 Base_Field_ModifyLog_ProcessType 修改字段的环节的环节类型
		public String getBase_Field_ModifyLog_ProcessType()
		{
		   return Base_Field_ModifyLog_ProcessType;
		}
		public void   setBase_Field_ModifyLog_ProcessType(String p_Base_Field_ModifyLog_ProcessType)
		{
		   Base_Field_ModifyLog_ProcessType=p_Base_Field_ModifyLog_ProcessType;
		}
//		700021004 Base_Field_ModifyLog_field_ID 字段ID
		public String getBase_Field_ModifyLog_field_ID()
		{
		   return Base_Field_ModifyLog_field_ID;
		}
		public void   setBase_Field_ModifyLog_field_ID(String p_Base_Field_ModifyLog_field_ID)
		{
		   Base_Field_ModifyLog_field_ID=p_Base_Field_ModifyLog_field_ID;
		}
//		700021006 Base_Field_ModifyLog_FieldDBName 字段数据名
		public String getBase_Field_ModifyLog_FieldDBName()
		{
		   return Base_Field_ModifyLog_FieldDBName;
		}
		public void   setBase_Field_ModifyLog_FieldDBName(String p_Base_Field_ModifyLog_FieldDBName)
		{
		   Base_Field_ModifyLog_FieldDBName=p_Base_Field_ModifyLog_FieldDBName;
		}
//		700021012 Base_Field_ModifyLog_Base_field_ShowName 字段显示名
		public String getBase_Field_ModifyLog_Base_field_ShowName()
		{
		   return Base_Field_ModifyLog_Base_field_ShowName;
		}
		public void   setBase_Field_ModifyLog_Base_field_ShowName(String p_Base_Field_ModifyLog_Base_field_ShowName)
		{
		   Base_Field_ModifyLog_Base_field_ShowName=p_Base_Field_ModifyLog_Base_field_ShowName;
		}
//		700021005 Base_Field_ModifyLog_Date 修改时间
		public String getBase_Field_ModifyLog_Date()
		{
		   return Base_Field_ModifyLog_Date;
		}
		public void   setBase_Field_ModifyLog_Date(String p_Base_Field_ModifyLog_Date)
		{
		   Base_Field_ModifyLog_Date=p_Base_Field_ModifyLog_Date;
		}
//		700021007 Base_Field_ModifyLog_Dealer 修改人
		public String getBase_Field_ModifyLog_Dealer()
		{
		   return Base_Field_ModifyLog_Dealer;
		}
		public void   setBase_Field_ModifyLog_Dealer(String p_Base_Field_ModifyLog_Dealer)
		{
		   Base_Field_ModifyLog_Dealer=p_Base_Field_ModifyLog_Dealer;
		}
//		700021008 Base_Field_ModifyLog_DealerID 修改人登录名
		public String getBase_Field_ModifyLog_DealerID()
		{
		   return Base_Field_ModifyLog_DealerID;
		}
		public void   setBase_Field_ModifyLog_DealerID(String p_Base_Field_ModifyLog_DealerID)
		{
		   Base_Field_ModifyLog_DealerID=p_Base_Field_ModifyLog_DealerID;
		}
//		700021009 Base_Field_ModifyLog_OldValue 旧值
		public String getBase_Field_ModifyLog_OldValue()
		{
		   return Base_Field_ModifyLog_OldValue;
		}
		public void   setBase_Field_ModifyLog_OldValue(String p_Base_Field_ModifyLog_OldValue)
		{
		   Base_Field_ModifyLog_OldValue=p_Base_Field_ModifyLog_OldValue;
		}
//		700021010 Base_Field_ModifyLog_NewValue 新值
		public String getBase_Field_ModifyLog_NewValue()
		{
		   return Base_Field_ModifyLog_NewValue;
		}
		public void   setBase_Field_ModifyLog_NewValue(String p_Base_Field_ModifyLog_NewValue)
		{
		   Base_Field_ModifyLog_NewValue=p_Base_Field_ModifyLog_NewValue;
		}
//		属性设置区域--End--

		
		
		private String OrderbyFiledNameString="";
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
		
		
		public String getWhereSql()
		{
			StringBuffer stringBuffer=new StringBuffer();
			//1	Base_Field_ModifyLog_ID	本记录的唯一标识，创建是自动形成，无业务含义
			stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C1",getBase_Field_ModifyLog_ID()));
			//700021001	Base_Field_ModifyLog_BaseID	指向主工单记录的指针
			stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C700021001",getBase_Field_ModifyLog_BaseID()));
			//700021002	Base_Field_ModifyLog_BaseSchema	指向主工单记录的指针
			stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C700021002",getBase_Field_ModifyLog_BaseSchema()));
			//700021013	Base_Field_ModifyLog_ProcessID	指向主工单处理过程记录的指针
			stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C700021013",getBase_Field_ModifyLog_ProcessID()));
			//700021011	Base_Field_ModifyLog_ProcessType	修改字段的环节的环节类型
			stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C700021011",getBase_Field_ModifyLog_ProcessType()));
			//700021004	Base_Field_ModifyLog_field_ID	字段ID
			stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C700021004",getBase_Field_ModifyLog_field_ID()));
			//700021006	Base_Field_ModifyLog_FieldDBName	字段数据名
			stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C700021006",getBase_Field_ModifyLog_FieldDBName()));
			//700021012	Base_Field_ModifyLog_Base_field_ShowName	字段显示名
			stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C700021012",getBase_Field_ModifyLog_Base_field_ShowName()));
			//700021005	Base_Field_ModifyLog_Date	修改时间
			stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C700021005",getBase_Field_ModifyLog_Date()));
			//700021007	Base_Field_ModifyLog_Dealer	修改人
			stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C700021007",getBase_Field_ModifyLog_Dealer()));
			//700021008	Base_Field_ModifyLog_DealerID	修改人登录名
			stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C700021008",getBase_Field_ModifyLog_DealerID()));
			//700021009	Base_Field_ModifyLog_OldValue	旧值
			stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C700021009",getBase_Field_ModifyLog_OldValue()));
			//700021010	Base_Field_ModifyLog_NewValue	新值
			stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C700021010",getBase_Field_ModifyLog_NewValue()));
			
			

			
			
			return stringBuffer.toString();
		}
		
}
