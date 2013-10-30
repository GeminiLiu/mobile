package com.ultrapower.eoms.common.core.component.tree.manager;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import com.ultrapower.eoms.common.core.component.tree.model.DtreeBean;
import com.ultrapower.workflow.client.WorkFlowServiceClient;
import com.ultrapower.workflow.configuration.sort.model.WfSort;

public class FormTypeTreeImpl extends TreeManager {
	
	/**
	 * 获得工单类别树的XML串
	 * @return
	 */
	public String getTreeXml()
	{
		List<DtreeBean> dtreeList = createDtree();
		return this.apposeDhtmlXtreeXml(dtreeList);
	}
	public List<DtreeBean> createDtree()
	{
		List<DtreeBean> lst = new ArrayList<DtreeBean>();
		try {
			//采用流程提供的远程方式获取工单类别
			List<WfSort> allWfSort = WorkFlowServiceClient.clientInstance().getSortService().getAllWfSort();
			int allWfSortLen = 0;
			if(allWfSort!=null)
				allWfSortLen = allWfSort.size();
			DtreeBean menuDtree = null;
			for(int i=0;i<allWfSortLen;i++){
				WfSort wfSort = allWfSort.get(i);
				menuDtree = new DtreeBean();
				menuDtree.setId(wfSort.getCode());;
				menuDtree.setText(wfSort.getName());
				menuDtree.setIm0("folderClosed.gif");
				menuDtree.setIm1("folderClosed.gif");
				menuDtree.setIm2("folderClosed.gif");
				lst.add(menuDtree);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}

//		StringBuffer sb = new StringBuffer();
//		sb.append("select dt.dtcode dtcode, dt.dtname dtname, dti.divalue divalue,dti.diname diname");
//		sb.append(" from bs_t_sm_dictype dt, bs_t_sm_dicitem dti");
//		sb.append(" where dti.dtcode='formtype' and dt.dtcode = dti.dtcode");
//		QueryAdapter qa = new QueryAdapter();
//		DataTable datatable = qa.executeQuery(sb.toString(), null, 0, 0, 2);
//		int datatableLen = 0;
//		if(datatable!=null)
//		{
//			datatableLen = datatable.length();
//		}
//		DataRow dataRow;
//		DtreeBean menuDtree = null;
//		for(int row=0;row<datatableLen;row++){
//			dataRow = datatable.getDataRow(row);
//			menuDtree = new DtreeBean();
//			menuDtree.setId(dataRow.getString("divalue"));
//			menuDtree.setText(dataRow.getString("diname"));
//			menuDtree.setIm0("folderClosed.gif");
//			menuDtree.setIm1("folderClosed.gif");
//			menuDtree.setIm2("folderClosed.gif");
//			lst.add(menuDtree);
//		}
		return lst;
	}
}
