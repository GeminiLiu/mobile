package cn.com.ultrapower.eoms.user.config.source.Bean;


/**
 * <p>Description:封装资源属性值到JavaBean中<p>
 * @author wangwenzhuo
 * @CreatTime 2006-10-28
 */
public class SourceAttributeValueBean {
	
	private Long sourceattvalueId;
    private Long sourceattvalueAttid;
    private String sourceattvalueBelongrow;
    private String sourceattvalueValue;
    
	public Long getSourceattvalueAttid() 
	{
		return sourceattvalueAttid;
	}
	
	public void setSourceattvalueAttid(Long sourceattvalueAttid)
	{
		this.sourceattvalueAttid = sourceattvalueAttid;
	}
	
	public String getSourceattvalueBelongrow() 
	{
		return sourceattvalueBelongrow;
	}
	
	public void setSourceattvalueBelongrow(String sourceattvalueBelongrow) 
	{
		this.sourceattvalueBelongrow = sourceattvalueBelongrow;
	}
	
	public Long getSourceattvalueId() 
	{
		return sourceattvalueId;
	}
	
	public void setSourceattvalueId(Long sourceattvalueId) 
	{
		this.sourceattvalueId = sourceattvalueId;
	}
	
	public String getSourceattvalueValue() 
	{
		return sourceattvalueValue;
	}
	
	public void setSourceattvalueValue(String sourceattvalueValue)
	{
		this.sourceattvalueValue = sourceattvalueValue;
	}
	
}
