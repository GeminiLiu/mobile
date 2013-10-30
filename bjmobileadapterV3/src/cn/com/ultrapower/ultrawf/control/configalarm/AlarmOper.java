package cn.com.ultrapower.ultrawf.control.configalarm;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import cn.com.ultrapower.system.database.GetDataBase;
import cn.com.ultrapower.system.database.IDataBase;
import cn.com.ultrapower.system.remedyop.RemedyDBOp;
import cn.com.ultrapower.system.table.Row;
import cn.com.ultrapower.system.table.RowSet;
import cn.com.ultrapower.system.table.Table;
import cn.com.ultrapower.ultrawf.control.configalarm.models.TypeModel;

public class AlarmOper {
	
	public RowSet getBaseItem(String Schema,String strSign){
		RowSet rowSet=null;
		String strSQL="";
		if(strSign.equals("cfgBaseItems")){
			strSQL="";
		}else if(strSign.equals("cfgLogicType")){
			strSQL="";
		}else if(strSign.equals("cfgResponsElevel")){
			strSQL="";
		}
		IDataBase iDataBase=GetDataBase.createDataBase();
		Table table=new Table(iDataBase,"");
		RemedyDBOp RemedyDBOp=new RemedyDBOp();
		String tblName=RemedyDBOp.GetRemedyTableName(Schema);
		strSQL+="from "+tblName;
		rowSet=table.executeQuery(strSQL, null, 0, 0, 0);	
		return rowSet;
	}
	
	//获取工单类型信息
	public List getBaseType(String Schema,String strSQL){
		List list=new ArrayList();
		try{
			IDataBase iDataBase=GetDataBase.createDataBase();
			Table table=new Table(iDataBase,"");
			RemedyDBOp RemedyDBOp=new RemedyDBOp();
			String tblName=RemedyDBOp.GetRemedyTableName(Schema);
			strSQL+="from "+tblName;
			RowSet rowSet=table.executeQuery(strSQL, null, 0, 0, 0);	
			if(rowSet!=null){
				Row row=null;
				for(int i=0;i<rowSet.length();i++){
					 row=rowSet.get(i);
					 System.out.println(row.getString("TypeName")+"  "+row.getString("TypeValue"));
					 list.add(new TypeModel(row.getString("TypeName"),row.getString("TypeValue")));
				}
					
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return list;
	}
	
	//根据条件查询工单信息
	public List<TypeModel> getNetType(String Schema,String strSQL){
		List<TypeModel> list=new ArrayList<TypeModel>();
		try{
			IDataBase iDataBase=GetDataBase.createDataBase();
			Table table=new Table(iDataBase,"");
			RemedyDBOp RemedyDBOp=new RemedyDBOp();
			String tblName=RemedyDBOp.GetRemedyTableName(Schema);
			strSQL = StringUtils.replace(strSQL, "{table}", tblName);
			RowSet rowSet=table.executeQuery(strSQL, null, 0, 0, 0);	
			if(rowSet!=null){
				Row row=null;
				for(int i=0;i<rowSet.length();i++){
					 row=rowSet.get(i);
					 list.add(new TypeModel(row.getString("TypeName"),row.getString("TypeName")));
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return list;
	}
}
