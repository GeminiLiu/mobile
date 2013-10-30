package cn.com.ultrapower.eoms.user.sla.aroperationdata;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.comm.remedyapi.ArEdit;

public class ActionOp {
    static final Logger logger = (Logger) Logger.getLogger(SlaDefineOp.class);
	GetFormTableName getformtablename = new GetFormTableName();
	String     username               = getformtablename.GetFormName("user");
	String     password               = getformtablename.GetFormName("password");
	String     ipname                 = getformtablename.GetFormName("driverurl");
	int        port                   = Integer.parseInt(getformtablename.GetFormName("serverport"));
    public boolean delRow(String tablename, String id)
    {
    	boolean flag    = false;
    	if(tablename==null)
		{
			logger.info("表名为空");
			return false;
		}
		if(id==null)
		{
			logger.info("id为空");
			return false;
		}
        try
        {
        	ArEdit aredit   = new ArEdit(username,password,ipname,port);
        	//	ArEdit aredit   = new ArEdit("Demo","","192.168.10.201",8888);
        	flag            = aredit.ArDelete(tablename,id);
        }catch(Exception e)
        {
        	logger.error("[111]ActionOp:delRow:删除动作时发生异常"+e.getMessage());
        	return false;
        }
    	return flag;
    }
    
    public static void main(String[] args)
    {
    	ActionOp op = new ActionOp();
    	op.delRow("fasd","fdsa");
    }
}
