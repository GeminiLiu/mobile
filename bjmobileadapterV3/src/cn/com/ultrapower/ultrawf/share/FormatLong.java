package cn.com.ultrapower.ultrawf.share;

public class FormatLong {
	
	
	public static long FormatStringToLong(String p_String)
	{
		long intRe=0;
		if(p_String!=null)
		{	if(!p_String.trim().equals(""))
			{
				try{
				intRe=Integer.parseInt(p_String);
				}catch(Exception ex)
				{
					
				}
			}
		}
		return intRe;
	}

}
