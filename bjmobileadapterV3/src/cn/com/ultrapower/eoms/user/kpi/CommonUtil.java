package cn.com.ultrapower.eoms.user.kpi;

import java.util.UUID;

public class CommonUtil
{

	/**
	 * 日期 2007-6-14
	 * @author xuquanxing 
	 * @param args void
	 */
	public static void main(String[] args)
	{
		System.out.println(CommonUtil.isSpecial("adc","adc,fdsfd"));

	}
	/**
	 * 返回32位的UUID
	 * 
	 * @return UUID字符串
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	/*public static boolean isSpecial(String  field,String fields)
	{
		String[] fie   = fields.split(",");
		boolean  isspe = false;
		for(int i=0; i<fie.length; i++)
		{
			if(field.equalsIgnoreCase(fie[i]))
			{
				isspe = true;
			}
		}
		return isspe;
	}
	*/
	public static boolean isSpecial(String src,String oth)
	{
		boolean result = false;
		if(src==null||oth==null)
		{
			result = false;
		}
		String[] sour = src.split(",");
		String[] match= oth.split(",");
		for(int i=0; i<sour.length; i++)
		{
			for(int j=0; j<match.length; j++)
			{
				if(sour[i].equalsIgnoreCase(match[j]))
				{
					result = true;
				}
			}
		}
		return result;
	}

}
