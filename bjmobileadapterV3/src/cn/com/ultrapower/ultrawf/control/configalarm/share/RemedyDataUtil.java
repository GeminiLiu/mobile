package cn.com.ultrapower.ultrawf.control.configalarm.share;

import java.util.ArrayList;
import java.util.List;

import cn.com.ultrapower.system.database.GetDataBase;
import cn.com.ultrapower.system.database.IDataBase;
import cn.com.ultrapower.system.remedyop.RemedyDBOp;
import cn.com.ultrapower.system.table.Row;
import cn.com.ultrapower.system.table.RowSet;
import cn.com.ultrapower.system.table.Table;

public class RemedyDataUtil {
	
	public List<String> getConfigData(String schema,String strsql){
		List<String> list=new ArrayList<String>();
		try{
			IDataBase iDataBase = GetDataBase.createDataBase();
			Table table = new Table(iDataBase,"");
			RemedyDBOp RemedyDBOp = new RemedyDBOp();
			String tblName = RemedyDBOp.GetRemedyTableName(schema);
			strsql += "from " + tblName;
			RowSet rowSet = table.executeQuery(strsql, null, 0, 0, 0);	
			if(rowSet!=null){
				Row row=null;
				for(int i=0;i<rowSet.length();i++){
					row=rowSet.get(i);
					list.add(row.getString("configdata"));
				}		
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return list;
	}
	
}
