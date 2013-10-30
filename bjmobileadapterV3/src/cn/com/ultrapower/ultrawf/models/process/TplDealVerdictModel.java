package cn.com.ultrapower.ultrawf.models.process;

import java.util.ArrayList;
import java.util.List;

public class TplDealVerdictModel
{
	private String verdictID = "";
	private long createDate = 0l;
	private String verdictBaseID = "";
	private String verdictBaseSchema = "";
	private String baseTplStateCode = "";
	private String baseTplStateName = "";
	private String verdictPhaseNo = "";
	private String verdictCondition = "";
	private String verdictDesc = "";
	private int verdictFlag00IsAvail = 2;
	private String verdictGoLine = "";
	private String verdictType = "DEAL";
	private int verdictFlagDuplicated;
	private int verdictPosX;
	private int verdictPosY;
	private List duplicatedList = new ArrayList();
	
	public String getVerdictID()
	{
		return verdictID;
	}
	public void setVerdictID(String verdictID)
	{
		this.verdictID = verdictID;
	}
	public String getVerdictBaseID()
	{
		return verdictBaseID;
	}
	public void setVerdictBaseID(String verdictBaseID)
	{
		this.verdictBaseID = verdictBaseID;
	}
	public String getVerdictBaseSchema()
	{
		return verdictBaseSchema;
	}
	public void setVerdictBaseSchema(String verdictBaseSchema)
	{
		this.verdictBaseSchema = verdictBaseSchema;
	}
	public String getBaseTplStateName()
	{
		return baseTplStateName;
	}
	public void setBaseTplStateName(String baseTplStateName)
	{
		this.baseTplStateName = baseTplStateName;
	}
	public String getVerdictPhaseNo()
	{
		return verdictPhaseNo;
	}
	public void setVerdictPhaseNo(String verdictPhaseNo)
	{
		this.verdictPhaseNo = verdictPhaseNo;
	}
	public String getVerdictCondition()
	{
		return verdictCondition;
	}
	public void setVerdictCondition(String verdictCondition)
	{
		this.verdictCondition = verdictCondition;
	}
	public String getVerdictDesc()
	{
		return verdictDesc;
	}
	public void setVerdictDesc(String verdictDesc)
	{
		this.verdictDesc = verdictDesc;
	}
	public String getBaseTplStateCode()
	{
		return baseTplStateCode;
	}
	public void setBaseTplStateCode(String baseTplStateCode)
	{
		this.baseTplStateCode = baseTplStateCode;
	}
	public int getVerdictFlag00IsAvail()
	{
		return verdictFlag00IsAvail;
	}
	public void setVerdictFlag00IsAvail(int verdictFlag00IsAvail)
	{
		this.verdictFlag00IsAvail = verdictFlag00IsAvail;
	}
	public String getVerdictGoLine()
	{
		return verdictGoLine;
	}
	public void setVerdictGoLine(String verdictGoLine)
	{
		this.verdictGoLine = verdictGoLine;
	}
	public String getVerdictType()
	{
		return verdictType;
	}
	public void setVerdictType(String verdictType)
	{
		this.verdictType = verdictType;
	}
	public int getVerdictFlagDuplicated()
	{
		return verdictFlagDuplicated;
	}
	public void setVerdictFlagDuplicated(int verdictFlagDuplicated)
	{
		this.verdictFlagDuplicated = verdictFlagDuplicated;
	}
	public int getVerdictPosX()
	{
		return verdictPosX;
	}
	public void setVerdictPosX(int verdictPosX)
	{
		this.verdictPosX = verdictPosX;
	}
	public int getVerdictPosY()
	{
		return verdictPosY;
	}
	public void setVerdictPosY(int verdictPosY)
	{
		this.verdictPosY = verdictPosY;
	}
	public List getDuplicatedList()
	{
		return duplicatedList;
	}
	public void setDuplicatedList(List duplicatedList)
	{
		this.duplicatedList = duplicatedList;
	}
	public void setDuplicatedVerdictModel(TplDealVerdictModel tdvModel)
	{
		if(tdvModel.getVerdictFlagDuplicated() == 1)
		{
			if(tdvModel.getCreateDate() > this.createDate)
			{
				this.createDate = tdvModel.getCreateDate();
				this.verdictFlag00IsAvail = tdvModel.getVerdictFlag00IsAvail();
			}
		}
	}
	public long getCreateDate()
	{
		return createDate;
	}
	public void setCreateDate(long createDate)
	{
		this.createDate = createDate;
	}
}
