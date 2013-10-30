package cn.com.ultrapower.ultrawf.models.flowmap;

/**
 * 流转Link信息的实体类
 * 
 * @author BigMouse
 */
public class LinkInfo
{
	private String linkID;
	
	private String startPhase;
	
	private String endPhase;
	
	private String linkType;
	
	private int flagRequired;

	public int getFlagRequired()
	{
		return flagRequired;
	}

	public void setFlagRequired(int flagRequired)
	{
		this.flagRequired = flagRequired;
	}

	public String getEndPhase()
	{
		return endPhase;
	}

	public void setEndPhase(String endPhase)
	{
		this.endPhase = endPhase;
	}

	public String getLinkID()
	{
		return linkID;
	}

	public void setLinkID(String linkID)
	{
		this.linkID = linkID;
	}

	public String getLinkType()
	{
		return linkType;
	}

	public void setLinkType(String linkType)
	{
		this.linkType = linkType;
	}

	public String getStartPhase()
	{
		return startPhase;
	}

	public void setStartPhase(String startPhase)
	{
		this.startPhase = startPhase;
	}
	
	
}
