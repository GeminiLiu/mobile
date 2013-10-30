package cn.com.ultrapower.eoms.user.rolemanage.password.hibernate.dbmanage;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.rolemanage.password.hibernate.po.Passwordmanage;
import cn.com.ultrapower.eoms.user.rolemanage.people.aroperationdata.People;
import cn.com.ultrapower.eoms.user.rolemanage.people.bean.PeopleInfo;
import cn.com.ultrapower.eoms.user.rolemanage.people.hibernate.dbmanage.GetUserInfoList;
import cn.com.ultrapower.eoms.user.rolemanage.people.hibernate.po.SysPeoplepo;

/**
 * <p>Description:根据sox法案对用户登陆进行密码验证<p>
 * @author wanwenzhuo
 * @CreatTime 2006-12-25
 */
public class PasswordValidation {
	
	static final Logger logger = (Logger) Logger.getLogger(PasswordValidation.class);
	
	GetFormTableName tablename	= new GetFormTableName();
	//密码过期时间段
	private long expireTimeZone			= Long.parseLong(tablename.GetFormName("passwordvalidate"));
	//当前系统时间
	private long currentTime			= System.currentTimeMillis()/1000;
	//连续登陆时间段
	private long durationLoginTimeZone	= Long.parseLong(tablename.GetFormName("durationlogintime"));
	//禁止登陆时间段
	private long noLoginTimeZone		= Long.parseLong(tablename.GetFormName("nologintime"));
	//设定的连续登陆次数
	private int setUpLoginTimes			= Integer.parseInt(tablename.GetFormName("connnum"));
	//新密码不得与之重复的最后修改密码的个数
	private int setUpLastPasswordNum	= Integer.parseInt(tablename.GetFormName("setuplastpasswordnum"));
	//全局对象变量
	PasswordFind passwordFind			= new PasswordFind();
	//用户验证信息全局变量
	String backStr						= "";
	
	public String validatePassword(String loginName,String password,String pash)
	{
		PasswordOp passwordOp	= new PasswordOp();
		try
		{
			//获得用户密码相应信息(用户密码表是否有该用户)
			Passwordmanage passwordmanage	= passwordFind.findPasswordInfoByName(loginName);
			if(passwordmanage==null)
			{
				//若同步用户密码信息表成功
				if(isHavePasswordInfo(loginName))
				{
					passwordmanage	= passwordFind.findPasswordInfoByName(loginName);
//					String isSucceed = validatePassword(loginName,password);
//					if(isSucceed.equals("success"))
//					{
//						backStr = "error005";
//						return backStr;
//					}
					if(passwordmanage==null)
					{
						logger.error("用户不存在！！！");
						backStr = "error005";
						return backStr;
					}
				}
				else
				{
					logger.error("同步密码表失败！！！");
					backStr = "error005";
					return backStr; 
				}
			}
		
			GetFormTableName tablename	= new GetFormTableName();
			String ip			= tablename.GetFormName("ip");
			long oa = passwordmanage.getIpcontrol_oaflag().longValue();
			if(oa!=0||!pash.equals(ip)){
			    logger.error("不能通过oa外网访问！！！");
				backStr = "error006";
				return backStr; 
			}
			
			//禁止登陆时间点
			long noLoginTime				= Function.tranTolong(passwordmanage.getNologintime());
			//最后修改密码时间点
			long lastModifyTime				= Function.tranTolong(passwordmanage.getLastmodifytime());
			//开始登陆时间点

			long startLoginTime				= Function.tranTolong(passwordmanage.getStartlogintime());
			//连续登陆次数
			long connNum					= Function.tranTolong(passwordmanage.getConnnum());
			
			//过期时间,若小于零则过期
			long expireTime					= lastModifyTime + expireTimeZone - currentTime;
			//禁止登陆时间,若大于零则禁止登陆
			long noLoginZone				= noLoginTime + noLoginTimeZone - currentTime;
			//若第一次用户名密码错误,则出现持续时间内登陆时间的限制
			//大于零则证明这是该用户第n次登陆(n!=1)
			//小于零则证明这是该用户超出这个时间限制后的第一次登陆
			long durationLoginTime			= startLoginTime + durationLoginTimeZone - currentTime;
		
			//若用户名密码验证通过			
			if(passwordFind.validatePasswordFromPeopleTable(loginName,password))
			{
				//若为Demo用户
				if(loginName.toLowerCase().equals("demo"))
				{
					backStr = "success";
					return backStr;
				}
				else
				{
					//验证逻辑开始
					//禁止登陆时间若大于零则禁止登陆
					if(noLoginZone>0)
					{
						logger.info("[518]PasswordValidation.validatePassword() 密码重复尝试超过规定次数,该账号暂时无法使用");
						backStr = "error001";
						return backStr;
					}
					else
					{
						//若密码没有过期
						if(expireTime>0)
						{
							//该用户上一次成功登陆系统后的第一次登陆
							if(startLoginTime==0)
							{
								backStr = "success";
								return backStr;
							}
							else
							{
								connNum			= 0;
								startLoginTime	= 0;
								noLoginTime		= 0;
								passwordmanage.setConnnum(new Long(connNum));
								passwordmanage.setStartlogintime(new Long(startLoginTime));
								passwordmanage.setNologintime(new Long(noLoginTime));
								passwordmanage.setIpcontrol_oaflag(passwordmanage.getIpcontrol_oaflag());
								passwordOp.modifyPasswordInfo(passwordmanage);
								
								backStr = "success";
								return backStr;
							}
						}
						//若密码过期
						else
						{
							logger.info("[518]PasswordValidation.validatePassword() 用户密码过期,请联系管理员");
							backStr = "error002";
							return backStr;
						}
					}
				}
			}
			//若用户密码不符
			else
			{
				//若为Demo用户
				if(loginName.toLowerCase().equals("demo"))
				{
					logger.info("[518]PasswordValidation.validatePassword() 用户密码信息不符");
					backStr = "error003";
					return backStr;
				}
				else
				{
					//若登陆次数没有超过限定次数
					if(connNum<setUpLoginTimes)
					{
						//将连续登陆次数自增
						connNum++;
						//该用户上一次成功登陆系统后的第一次登陆,将开始时间登陆点设为当前系统时间
						if(startLoginTime == 0)
						{
							startLoginTime = currentTime;
						}
						else
						{
							//若持续登陆时间大于零,则当前用户为连续第n次登陆(n!=1)
							if(durationLoginTime>0)
							{
							}
							//将当前系统时间设为开始登陆时间
							else
							{
								connNum = 1;
								startLoginTime = currentTime;
								
							}
						}
						
						passwordmanage.setConnnum(new Long(connNum));
						passwordmanage.setStartlogintime(new Long(startLoginTime));
						passwordOp.modifyPasswordInfo(passwordmanage);
						
						logger.info("[518]PasswordValidation.validatePassword() 用户密码信息不符");
						backStr = "error003";
						return backStr;
					}
					else
					{
						//若持续登陆时间大于零,则当前用户为连续第n次登陆(n!=1)
						if(durationLoginTime>0)
						{
							noLoginTime = currentTime;
							passwordmanage.setNologintime(new Long(noLoginTime));
							logger.info("[518]PasswordValidation.validatePassword() 密码重复尝试超过规定次数,该账号被暂停");
							backStr = "error004";
						}
						//将当前系统时间设为开始登陆时间
						else
						{
							connNum = 1;
							startLoginTime = currentTime;
							passwordmanage.setConnnum(new Long(connNum));
							passwordmanage.setStartlogintime(new Long(startLoginTime));
							logger.info("[518]PasswordValidation.validatePassword() 用户密码信息不符");
							backStr = "error003";
						}
						passwordOp.modifyPasswordInfo(passwordmanage);
						
						return backStr;
					}
				}
			}
		}
		catch(Exception e)
		{
			
			
			if(backStr.equals(""))
			{
				backStr = "error005";
			}
			return backStr;
		}
	}
	
	/**
	 * <p>Description:根据禁止登陆时间和过期时间向客户端打印checkbox<p>
	 * @author wangwenzhuo
	 * @creattime 2006-12-26
	 * @param passwordmanage
	 * @return String
	 */
	public String showCheckBox(Passwordmanage passwordmanage)
	{
		try
		{
			//用户登陆名
			String userLoginName			= passwordmanage.getLoginname();
			//禁止登陆时间点
			long noLoginTime				= Function.tranTolong(passwordmanage.getNologintime());
			//最后修改密码时间点
			long lastModifyTime				= Function.tranTolong(passwordmanage.getLastmodifytime());
			//用户密码信息ID
			String pwdId					= String.valueOf(passwordmanage.getPwdid());
			//过期时间,若小于零则过期
			long expireTime					= 0;
			//禁止登陆时间,若大于零则禁止登陆
			long noLoginZone				= 0;
			
			//如果用户为Demo
			if(userLoginName.toLowerCase().equals("demo"))
			{
				expireTime	= 1;
				noLoginZone	 = -1;
			}
			else
			{
				expireTime	= lastModifyTime + expireTimeZone - currentTime;
				noLoginZone	= noLoginTime + noLoginTimeZone - currentTime;
			}
			
			String str						= "";
			//若禁止登陆时间大于零或者过期时间小于零
			if(expireTime<0||noLoginZone>0)
			{
				//向客户端打印checkbox
				str = "<td><input type='checkbox' align='center' class='checkbox' name='startupEles' value='"+pwdId+"'></td>";
			}
			else
			{
				str = "<td></td>";
			}
			return str;
		}
		catch(Exception e)
		{
			logger.error("[524]PasswordValidation.showCheckBox() 根据禁止登陆时间和过期时间向客户端打印checkbox失败"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * <p>Description:启动用户密码<p>
	 * @author wangwenzhuo
	 * @creattime 2006-12-26
	 * @param loginName
	 * @return boolean
	 */
	protected boolean startupPassword(String pwdId)
	{
		//根据ID查找用户密码信息
		Passwordmanage passwordManage	= passwordFind.findPasswordInfoById(pwdId);
		//实例化一个用户密码信息对象
		Passwordmanage passwordInfo		= new Passwordmanage();
		passwordInfo.setPwdid(passwordManage.getPwdid());
		passwordInfo.setLoginname(passwordManage.getLoginname());
		passwordInfo.setPassword(Function.nullString(passwordManage.getPassword()));
		passwordInfo.setLastpassword("");
		passwordInfo.setNologintime(new Long(0));
		passwordInfo.setConnnum(new Long(0));
		passwordInfo.setLastmodifytime(new Long(currentTime));
		passwordInfo.setStartlogintime(new Long(0));
		passwordInfo.setIpcontrol_oaflag(passwordManage.getIpcontrol_oaflag());//oa
		try
		{
			//修改用户密码信息
			PasswordOp passwordOp	= new PasswordOp();
			if(passwordOp.modifyPasswordInfo(passwordInfo))
			{
				logger.info("[525]PasswordValidation.forbidLoginPassword() 用户密码启动成功");
				return true;
			}
			else
			{
				logger.info("[525]PasswordValidation.forbidLoginPassword() 用户密码启动不成功");
				return false;
			}
		}
		catch (Exception e) 
		{
			logger.error("[525]PasswordValidation.forbidLoginPassword() 启动用户密码失败"+e.getMessage());
			return false;
		}
	}
	
	/**
	 * <p>Description:更新用户密码信息<p>
	 * @author wangwenzhuo
	 * @creattime 2006-12-27
	 * @param sysPeoplepo
	 * @return boolean
	 */
	public boolean updatePassword(SysPeoplepo sysPeoplepo)
	{
		//实例化一个用户信息bean
		PeopleInfo peopleInfo = new PeopleInfo();
		peopleInfo.setUserId(Function.nullString(sysPeoplepo.getC1()));
		peopleInfo.setUserFullname(Function.nullString(sysPeoplepo.getC630000003()));				
		peopleInfo.setUserBelongGroupId(Function.nullString(sysPeoplepo.getC630000036()));
		peopleInfo.setUserCpid(Function.nullString(sysPeoplepo.getC630000013()));
		peopleInfo.setUserCptype(Function.nullString(sysPeoplepo.getC630000014()));
		peopleInfo.setUserCreateuser(Function.nullString(sysPeoplepo.getC630000004()));
		peopleInfo.setUserDpid(Function.nullString(sysPeoplepo.getC630000015()));
		peopleInfo.setUserFax(Function.nullString(sysPeoplepo.getC630000010()));
		peopleInfo.setUserIntId(sysPeoplepo.getC630000029().intValue());
		peopleInfo.setUserIsmanager(Function.nullString(sysPeoplepo.getC630000006()));
		peopleInfo.setUserLicensetype(Function.nullString(sysPeoplepo.getC630000016()));
		peopleInfo.setUserLoginname(Function.nullString(sysPeoplepo.getC630000001()));
		peopleInfo.setUserMail(Function.nullString(sysPeoplepo.getC630000011()));
		peopleInfo.setUserMobie(Function.nullString(sysPeoplepo.getC630000008()));
		peopleInfo.setUserOrderby(Function.nullString(sysPeoplepo.getC630000017()));
		peopleInfo.setUserPassword(Function.nullString(sysPeoplepo.getC630000002()));
		peopleInfo.setUserPhone(Function.nullString(sysPeoplepo.getC630000009()));
		peopleInfo.setUserPosition(Function.nullString(sysPeoplepo.getC630000005()));
		peopleInfo.setUserStatus(Function.nullString(sysPeoplepo.getC630000012()));
		peopleInfo.setUserType(Function.nullString(sysPeoplepo.getC630000007()));
		peopleInfo.setTime_out(Function.nullString(sysPeoplepo.getC639900003()));
		peopleInfo.setUserSmsOrder(Function.nullString(sysPeoplepo.getC639900004()));
		peopleInfo.setUserDutyShow(Function.nullString(sysPeoplepo.getC639900005()));
		
		try
		{
	        //更新用户信息
			People people = new People();
			if(people.modifyPeoplePassword(peopleInfo,sysPeoplepo.getC1(),"personalModify"))
			{
				//更新用户密码信息
				PasswordOp passwordOp = new PasswordOp();
				return passwordOp.modifyPasswordInfo(updateLastpassInfo(sysPeoplepo));
			}
			else
			{
				logger.info("[526]PasswordValidation.updatePassword() 用户信息更新不成功");
				return false;
			}
		}
		catch(Exception e)
		{
			logger.error("[526]PasswordValidation.updatePassword() 更新用户密码信息失败"+e.getMessage());
			return false;
		}
	}
	
	/**
	 * <p>Description:更新最后修改密码值<p>
	 * @author wangwenzhuo
	 * @creattime 2006-12-27
	 * @param sysPeoplepo
	 * @return Passwordmanage
	 */
	private Passwordmanage updateLastpassInfo(SysPeoplepo sysPeoplepo)
	{
		//最终的最后修改密码
		String newlastPassword			= "";
		
		//通过loginName获取该用户的用户密码信息
		Passwordmanage passwordInfo		= passwordFind.findPasswordInfoByName(sysPeoplepo.getC630000001());
		
		//取得最后n次修改密码字段的值
		String oldlastPassword			= Function.nullString(passwordInfo.getLastpassword());
		//取得新密码
		String newPassword				= sysPeoplepo.getC630000002();
		
		//分隔最后修改密码为数组形式
		String lastPasswordLen[] = oldlastPassword.split(";");
		//若最后修改密码的次数没有超过规定次数
		if(lastPasswordLen.length<setUpLastPasswordNum)
		{
			newlastPassword = newPassword + ";" + oldlastPassword;
		}
		else
		{
			//新密码和最后修改密码(将最末一个旧密码去掉)字符串相加
			newlastPassword = newPassword + ";" + Function.splitDeformityString(oldlastPassword,";");
		}
		
		//实例化用户密码信息的po
		Passwordmanage passwordManage 	= new Passwordmanage();
		//获得最后修改密码
		passwordManage.setLastpassword(newlastPassword);
		
		//从用户信息中获得密码
		passwordManage.setPassword(newPassword);
		//从用户的用户密码信息获得其它信息
		passwordManage.setPwdid(passwordInfo.getPwdid());
		passwordManage.setLoginname(passwordInfo.getLoginname());
		passwordManage.setNologintime(Function.nullLong(passwordInfo.getNologintime()));
		passwordManage.setConnnum(Function.nullLong(passwordInfo.getConnnum()));
		passwordManage.setLastmodifytime(new Long(currentTime));
		passwordManage.setIpcontrol_oaflag(passwordInfo.getIpcontrol_oaflag());
		return passwordManage;
	}
	
	/**
	 * <p>Description:用户成功登陆时判断是否同步用户密码信息表<p>
	 * @author wangwenzhuo
	 * @creattime 2006-12-28
	 * @param loginName
	 * @param password
	 * @return boolean
	 */
	public boolean isHavePasswordInfo(String loginName)
	{
		try
		{
			Passwordmanage passwordmanage = passwordFind.findPasswordInfoByName(loginName);
			//如果用户密码信息表中没有该用户
			if(passwordmanage==null)
			{
				//根据用户信息表中的数据同步用户密码表
				GetUserInfoList getUserInfoList = new GetUserInfoList();
				SysPeoplepo sysPeoplepo			= getUserInfoList.getUserInfoName(loginName);
				if(!String.valueOf(sysPeoplepo).equals("null"))
				{
					String userPassword			= sysPeoplepo.getC630000002();
					PeopleInfo peopleInfo 		= new PeopleInfo();
					peopleInfo.setUserLoginname(loginName);
					peopleInfo.setUserPassword(userPassword);
					//同步用户密码信息表
					PasswordOp passwordOp = new PasswordOp();
					return passwordOp.insertPasswordInfo(peopleInfo);
				}
				else
				{
					logger.info("系统不存在该用户！！");
					return false;
				}
				
			}
			else
			{
				logger.info("[527]PasswordValidation.isHavePasswordInfo() 用户密码信息表中存在该用户");
				return false;
			}
		}
		catch(Exception e)
		{
			logger.error("[527]PasswordValidation.isHavePasswordInfo() 用户成功登陆时判断是否同步用户密码信息表失败"+e.getMessage());
			return false;
		}
	}
	
	/**
	 * <p>Description:判断用户是否修改过密码(判断用户是否第一次登陆该系统)<p>
	 * @author wangwenzhuo
	 * @creattime 2006-02-03
	 * @param userLoginName
	 * @return boolean
	 */
	public boolean isFixedPassword(String userLoginName)
	{
		String lastPassword = Function.nullString(passwordFind.getLastPassword(userLoginName));
		if(lastPassword.equals(""))
		{
			logger.info("[546]PasswordValidation.isFixedPassword() 用户"+userLoginName+"是第一次登陆该系统");
			return false;
		}
		else
		{
			return true;
		}
	}
	
	/**
	 * 根据登录名判断密码是否达到失效时间
	 * @author fangqun
	 * @creattime 2008-06-13
	 * @param userLoginName
	 * @return
	 */
	public String validateTimeout(String userLoginName)
	{
		GetUserInfoList getuserinfolist = new GetUserInfoList();
		SysPeoplepo po = null;
		po             = getuserinfolist.getUserInfoName(userLoginName);
		
		if(po != null)
		{
			
			String timeout = Function.nullString(po.getC639900003());
	        String nowtime = Function.DateToSecond_date(Function.NowDate());
			
	        if(timeout != null && !"".equals(timeout) && (Long.valueOf(timeout).intValue()<Long.valueOf(nowtime).intValue()) && !"Demo".equals(userLoginName))
		    {
	            return "error007";
		    }
	        else
			{
				return "true";
			} 
		}
		else
		{
			return "error003";
		}
		
	}

}