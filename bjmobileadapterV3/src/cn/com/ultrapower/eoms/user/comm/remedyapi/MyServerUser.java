package cn.com.ultrapower.eoms.user.comm.remedyapi;

import com.remedy.arsys.api.ARServerUser;

public class MyServerUser extends ARServerUser {
	
	public MyServerUser(){
		super();
	}
	
	public MyServerUser(String s, String s1, String s2, String s3){
		super(s, s1, s2, s3);
	}
	
	public MyServerUser(String s, String s1, String s2, String s3, long l){
		super(s, s1, s2, s3, l);
	}
	
	public MyServerUser(String s, String s1, String s2, String s3, String s4, long l){
		super(s, s1, s2, s3, s4, l);
	}
	
	public MyServerUser(String s, String s1, String s2, String s3, int i){
		super(s, s1, s2, s3, i);
	}
	
	public boolean isLogin(){
		return isProxyInContext();
	}
}
