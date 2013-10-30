package cn.com.ultrapower.ultrawf.models.process;

public class TplDealLinkModel
{
	private String linkID = "";
	private String linkBaseID = "";
	private String linkBaseSchema = "";
	private String linkPhaseNo = "";
	private String startPhaseNo = "";
	private int startPort;
	private String endPhaseNo = "";
	private int endPort;
	private int linkVerdictResult;
	private String desc = "";
	private int linkFlag00IsAvail = 2;
	private int flag21Required;
	private String linkGoLine = "";
	private String linkType = "DEAL";
	private int startPoint;
	private int endPoint;
	private int linkFlagDuplicated;
	private int linkNum;
	public String getLinkID()
	{
		return linkID;
	}
	public void setLinkID(String linkID)
	{
		this.linkID = linkID;
	}
	public String getLinkBaseID()
	{
		return linkBaseID;
	}
	public void setLinkBaseID(String linkBaseID)
	{
		this.linkBaseID = linkBaseID;
	}
	public String getLinkBaseSchema()
	{
		return linkBaseSchema;
	}
	public void setLinkBaseSchema(String linkBaseSchema)
	{
		this.linkBaseSchema = linkBaseSchema;
	}
	public String getLinkPhaseNo()
	{
		return linkPhaseNo;
	}
	public void setLinkPhaseNo(String linkPhaseNo)
	{
		this.linkPhaseNo = linkPhaseNo;
	}
	public String getStartPhaseNo()
	{
		return startPhaseNo;
	}
	public void setStartPhaseNo(String startPhaseNo)
	{
		this.startPhaseNo = startPhaseNo;
	}
	public int getStartPort()
	{
		return startPort;
	}
	public void setStartPort(int startPort)
	{
		this.startPort = startPort;
	}
	public String getEndPhaseNo()
	{
		return endPhaseNo;
	}
	public void setEndPhaseNo(String endPhaseNo)
	{
		this.endPhaseNo = endPhaseNo;
	}
	public int getEndPort()
	{
		return endPort;
	}
	public void setEndPort(int endPort)
	{
		this.endPort = endPort;
	}
	public int getLinkVerdictResult()
	{
		return linkVerdictResult;
	}
	public void setLinkVerdictResult(int linkVerdictResult)
	{
		this.linkVerdictResult = linkVerdictResult;
	}
	public String getDesc()
	{
		return desc;
	}
	public void setDesc(String desc)
	{
		this.desc = desc;
	}
	public int getLinkFlag00IsAvail()
	{
		return linkFlag00IsAvail;
	}
	public void setLinkFlag00IsAvail(int linkFlag00IsAvail)
	{
		this.linkFlag00IsAvail = linkFlag00IsAvail;
	}
	public int getFlag21Required()
	{
		return flag21Required;
	}
	public void setFlag21Required(int flag21Required)
	{
		this.flag21Required = flag21Required;
	}
	public String getLinkGoLine()
	{
		return linkGoLine;
	}
	public void setLinkGoLine(String linkGoLine)
	{
		this.linkGoLine = linkGoLine;
	}
	public String getLinkType()
	{
		return linkType;
	}
	public void setLinkType(String linkType)
	{
		this.linkType = linkType;
	}
	public int getStartPoint()
	{
		return startPoint;
	}
	public void setStartPoint(int startPoint)
	{
		this.startPoint = startPoint;
	}
	public int getEndPoint()
	{
		return endPoint;
	}
	public void setEndPoint(int endPoint)
	{
		this.endPoint = endPoint;
	}
	public int getLinkFlagDuplicated()
	{
		return linkFlagDuplicated;
	}
	public void setLinkFlagDuplicated(int linkFlagDuplicated)
	{
		this.linkFlagDuplicated = linkFlagDuplicated;
	}
	public int getLinkNum() {
		return linkNum;
	}
	public void setLinkNum(int linkNum) {
		this.linkNum = linkNum;
	}
	
}
