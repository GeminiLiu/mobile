package cn.com.ultrapower.ultrawf.share;

public class FormatInt {

	public static int FormatStringToInt(String p_String)
	{
		int intRe=0;
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
