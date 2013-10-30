package cn.com.ultrapower.eoms.user.kpi;
public class DeleteNull
{
	 public static String  dealSpace(String str)
	    {
	    	String laststr = "";
	    	if(str==null)
	    	{
	        	str     = "";
	    		laststr = str;
	    	}
	    	else
	    	{
	    		laststr = str.trim();
	    	}
	    	return laststr;
	    }
	    public static void main(String[] args)
	    {
	    	String a = "ASD";
	    	System.out.println(DeleteNull.dealNull(a));
	    }
	    public static String dealNull(Object object)
	    {
	    	Object obj = null;
	    	if(object==null)
	    	{
	    		obj = new String("");
	    	}else
	    	{
	    		obj = object;
	    	}
	    	return (String)obj;
	    }

}
