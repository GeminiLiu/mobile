package daiwei.mobile.service;

import java.util.Map;
import android.content.Context;

public interface LoginService{
		
	public Map<String,Object> login(Context context, String username, String password);
	public boolean isTempletMapAvailable(Context context);
}

