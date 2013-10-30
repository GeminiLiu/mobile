package cn.com.ultrapower.eoms.user.rolemanage.people.hibernate.dbmanage;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.rolemanage.password.hibernate.po.Passwordmanage;

public class OaValidation {
	public String showCheckBox(long oamanage,long id)
	{
		try{
			String str = "";
			if(oamanage==1){
				str = "<td><input type='checkbox' align='center' class='checkbox' id='startupEles' name='startupEles' value='"+id+"'></td>";
			}
			else{
				str = "<td><input type='checkbox' align='center' class='checkbox' id='startupEles' name='startupEles' value='"+id+"' checked='true'></td>";
			}
			return str;
		}
		catch(Exception e){
			return null;
		}
	}
}
