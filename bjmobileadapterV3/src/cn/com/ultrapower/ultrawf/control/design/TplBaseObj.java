package cn.com.ultrapower.ultrawf.control.design;

import java.util.*;

import cn.com.ultrapower.ultrawf.models.process.*;

public class TplBaseObj
{
	private TplBaseModel tbModel = new TplBaseModel();
	private List processList = new ArrayList();
	private List linkList = new ArrayList();
	private List stateList = new ArrayList();
	
	private String type = "NEW";
	private List tbfsList = new ArrayList();
	private List tdapList = new ArrayList();
	private List tpList = new ArrayList();
	private List tdvList = new ArrayList();
	private List tlList = new ArrayList();
	
	
	
	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public List getTbfsList()
	{
		return tbfsList;
	}

	public void setTbfsList(List tbfsList)
	{
		this.tbfsList = tbfsList;
	}

	public List getTdapList()
	{
		return tdapList;
	}

	public void setTdapList(List tdapList)
	{
		this.tdapList = tdapList;
	}

	public List getTpList()
	{
		return tpList;
	}

	public void setTpList(List tpList)
	{
		this.tpList = tpList;
	}

	public List getTdvList()
	{
		return tdvList;
	}

	public void setTdvList(List tdvList)
	{
		this.tdvList = tdvList;
	}

	public List getTlList()
	{
		return tlList;
	}

	public void setTlList(List tlList)
	{
		this.tlList = tlList;
	}

	/**
	 * 获取模板信息
	 * @return 模板信息
	 */
	public TplBaseModel getTbModel()
	{
		return tbModel;
	}
	
	/**
	 * 设置模板信息
	 * @param tbModel 模板信息
	 */
	public void setTbModel(TplBaseModel tbModel)
	{
		this.tbModel = tbModel;
	}
	
	/**
	 * 获取环节列表
	 * @return
	 */
	public List getProcessList()
	{
		return processList;
	}
	
	/**
	 * 获取环节信息
	 * @param phaseNo 环节号
	 * @return 环节信息
	 */
	public TplProcessObj getProcessModel(String phaseNo)
	{
		for(Iterator it = processList.iterator(); it.hasNext();)
		{
			TplProcessObj tpObj = (TplProcessObj)it.next();
			if(tpObj.getProcessID().equals(phaseNo))
			{
				return tpObj;
			}
		}
		return null;
	}
	
	/**
	 * 添加环节信息
	 * @param tpObj 环节信息
	 */
	public void addProcessModel(TplProcessObj tpObj)
	{
		processList.add(tpObj);
	}
	
	/**
	 * 更新环节信息
	 * @param tpObj 环节信息
	 */
	public void setProcessMdoel(TplProcessObj tpObj)
	{
		for(int i = 0; i < processList.size(); i++)
		{
			if(((TplProcessObj)processList.get(i)).getProcessID().equals(tpObj.getProcessID()))
			{
				processList.set(i, tpObj);
			}
		}
	}
	
	/**
	 * 删除环节信息
	 * @param phaseNo 环节号
	 * @return 被删除的环节信息
	 */
	public TplProcessObj deleteProcessModel(String phaseNo)
	{
		TplProcessObj tpo = null;
		for(int i = 0; i < processList.size(); i++)
		{
			TplProcessObj tpObj = (TplProcessObj)processList.get(i);
			if(tpObj.getProcessID().equals(phaseNo))
			{
				tpo = tpObj;
				processList.remove(i);
				break;
			}
		}
		return tpo;
	}
	
	/**
	 * 获取流转箭头列表
	 * @return
	 */
	public List getLinkList()
	{
		return linkList;
	}
	
	/**
	 * 获取流转箭头信息
	 * @param phaseNo 流转箭头号
	 * @return 流转箭头信息
	 */
	public TplLinkObj getLinkModel(String phaseNo)
	{
		for(Iterator it = linkList.iterator(); it.hasNext();)
		{
			TplLinkObj tlObj = (TplLinkObj)it.next();
			if(tlObj.getLinkID().equals(phaseNo))
			{
				return tlObj;
			}
		}
		return null;
	}
	
	/**
	 * 添加流转箭头信息
	 * @param tpObj 流转箭头信息
	 */
	public void addLinkModel(TplLinkObj tlObj)
	{
		linkList.add(tlObj);
	}
	
	/**
	 * 更新流转箭头信息
	 * @param tpObj 流转箭头信息
	 */
	public void setLinkModel(TplLinkObj tlObj)
	{
		for(int i = 0; i < linkList.size(); i++)
		{
			if(((TplLinkObj)linkList.get(i)).getLinkID().equals(tlObj.getLinkID()))
			{
				linkList.set(i, tlObj);
			}
		}
	}
	
	/**
	 * 删除流转箭头信息
	 * @param phaseNo 流转箭头号
	 * @return 被删除的流转箭头信息
	 */
	public TplLinkObj deleteLinkModel(String phaseNo)
	{
		TplLinkObj tlo = null;
		for(int i = 0; i < linkList.size(); i++)
		{
			TplLinkObj tlObj = (TplLinkObj)linkList.get(i);
			if(tlObj.getLinkID().equals(phaseNo))
			{
				tlo = tlObj;
				linkList.remove(i);
				break;
			}
		}
		return tlo;
	}
	
	/**
	 * 获取状态列表
	 * @return
	 */
	public List getStateList()
	{
		return stateList;
	}
	
	/**
	 * 获取状态信息
	 * @param phaseNo 状态号
	 * @return 状态信息
	 */
	public TplStateObj getStateModel(String stateNo)
	{
		for(Iterator it = stateList.iterator(); it.hasNext();)
		{
			TplStateObj tsObj = (TplStateObj)it.next();
			if(tsObj.tbfsModel.getBaseStateCode().equals(stateNo))
			{
				return tsObj;
			}
		}
		return null;
	}
	
	/**
	 * 添加状态信息
	 * @param tpObj 状态信息
	 */
	public void addStateModel(TplStateObj tsObj)
	{
		stateList.add(tsObj);
	}
	
	/**
	 * 更新状态信息
	 * @param tpObj 状态信息
	 */
	public void setStateModel(TplStateObj tsObj)
	{
		for(int i = 0; i < stateList.size(); i++)
		{
			if(((TplStateObj)stateList.get(i)).tbfsModel.getBaseStateCode().equals(tsObj.tbfsModel.getBaseStateCode()))
			{
				stateList.set(i, tsObj);
			}
		}
	}
	
	/**
	 * 删除状态信息
	 * @param phaseNo 状态号
	 * @return 被删除的状态信息
	 */
	public TplStateObj deleteStateModel(String stateNo)
	{
		TplStateObj tso = null;
		for(int i = 0; i < stateList.size(); i++)
		{
			TplStateObj tsObj = (TplStateObj)stateList.get(i);
			if(tsObj.tbfsModel.getBaseStateCode().equals(stateNo))
			{
				tso = tsObj;
				stateList.remove(i);
				break;
			}
		}
		return tso;
	}
}
