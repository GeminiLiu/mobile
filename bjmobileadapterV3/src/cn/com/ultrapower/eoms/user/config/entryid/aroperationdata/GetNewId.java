package cn.com.ultrapower.eoms.user.config.entryid.aroperationdata;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.config.entryid.aroperationdata.ArEntryid;
import cn.com.ultrapower.eoms.user.config.entryid.hibernate.dbmanage.EntryidSearch;
import cn.com.ultrapower.eoms.user.comm.function.Function;
public class GetNewId {

	/**
	 * Date 2006-10-13
	 * author 
	 * @param 
	 * @param 
	 * @return void 
	 */
	static final Logger logger = (Logger) Logger.getLogger(GetNewId.class);

	public synchronized String getnewid(String TableID,String IDName){
		try
		{
			String value=Function.getNewID(TableID,IDName);
			return value;
		}
		catch(Exception e)
		{
			 Calendar CD 	= Calendar.getInstance();
			 long datetimeid	= CD.getTimeInMillis();
			 logger.error("360 GetNewId.getnewid error:"+e.toString());
			 return String.valueOf(datetimeid);
		}
	} 

}
