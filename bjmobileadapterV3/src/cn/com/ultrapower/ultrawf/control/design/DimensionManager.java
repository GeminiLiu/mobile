package cn.com.ultrapower.ultrawf.control.design;

import java.util.List;

import cn.com.ultrapower.ultrawf.models.design.*;
import cn.com.ultrapower.ultrawf.share.constants.Constants;
import cn.com.ultrapower.system.remedyop.RemedyFormOp;

public class DimensionManager
{
	public List<DimensionModel> getDimensionList(String schema)
	{
		DimensionHandler dHandler = new DimensionHandler();
		return dHandler.getDimensionList(schema);
	}
	
	public void Delete(String requestID)
	{
		RemedyFormOp RemedyOp = new RemedyFormOp(Constants.REMEDY_SERVERNAME,
			Constants.REMEDY_SERVERPORT, Constants.REMEDY_DEMONAME,
			Constants.REMEDY_DEMOPASSWORD);
		RemedyOp.RemedyLogin();
		DimensionHandler dHandler = new DimensionHandler();
		dHandler.Delete(RemedyOp, requestID);
		RemedyOp.RemedyLogout();
	}
}
