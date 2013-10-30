/*
 * Created on 2004-3-11
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package cn.com.ultrapower.eoms.common.basedao;

/**
 * @author lianhuigang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class GeneralException extends Exception
{

	/**
	 * 
	 */
	public GeneralException()
	{
		super();
	}

	/**
	 * @param arg0
	 */
	public GeneralException(String arg0)
	{
		super(arg0);
	}

	

	/**
	 * @param arg0
	 */
	public GeneralException(Throwable arg0)
	{
		super(arg0.toString());
	}

}
