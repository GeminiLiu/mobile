package com.ultrapower.mobile.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.ultrapower.biz.ItSysWsFacade;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.mobile.common.constants.Constants;
import com.ultrapower.mobile.common.enums.UserOnlineEnum;
import com.ultrapower.mobile.common.udp.UDPClient;
import com.ultrapower.mobile.common.utils.EncryptUtil;
import com.ultrapower.mobile.common.utils.WSUtil;
import com.ultrapower.mobile.common.utils.XMLParamParser;
import com.ultrapower.mobile.model.UserInfo;
import com.ultrapower.mobile.model.UserOnline;
import com.ultrapower.mobile.model.xml.FieldInfo;
import com.ultrapower.mobile.model.xml.XmlInfo;
import com.ultrapower.mobile.service.BizFacade;
import com.ultrapower.mobile.service.UserManagerService;
import com.ultrapower.mobile.service.UserOnlineManagerService;

@Transactional
public class BizFacadeImpl implements BizFacade {

	private static Logger log = LoggerFactory.getLogger(BizFacadeImpl.class);
	
	private UserManagerService userManagerService;
	
	private UserOnlineManagerService userOnlineManagerService;
	
	/**
	 * 心跳通知
	 * @param userName 登录名
	 * @param password 密码
	 * @param simId sim卡号
	 * @param itSysCode 业务系统标识
	 * @param ip ip地址
	 * @param port 端口号
	 */
	public void heartbeatNotice(String userName, String password, String simId, String itSysCode, String ip, int port) {
		long now = TimeUtils.getCurrentTime();
		UserOnline userOnline = userOnlineManagerService.getUserOnline(userName, password, itSysCode);
		if (userOnline != null) {
			log.info("更新心跳！userName=" + userName + ",simId=" + simId + ",itSysName=" + itSysCode + ",ip=" + ip + ":" + port);
			userOnline.setHeartBeatTime(now);
			userOnline.setSimId(simId);
			userOnline.setOnlineIpAddress(ip);
			userOnline.setPort(port);
		} else {
			log.info("用户登录初始化心跳！userName=" + userName + ",simId=" + simId + ",itSysName=" + itSysCode + ",ip=" + ip + ":" + port);
			userOnline = new UserOnline(itSysCode,simId,userName,password,port); 
			userOnline.setOnlineIpAddress(ip);
			userOnline.setIsOnline(UserOnlineEnum.ONLINE.getVal());
			userOnline.setLoginTime(now);
			userOnline.setHeartBeatTime(now);
			userOnlineManagerService.saveOrUpdate(userOnline);
		}
	}

	/**
	 * 用户登录
	 * @param userName 登录名
	 * @param password 密码
	 * @param simId sim卡号
	 * @param itSysCode 业务系统标识
	 * @param ip ip地址
	 * @param port 端口号
	 */
	public String login(String userName, String password, String simId,
			String itSysCode, String ip, int port) {
		log.info("用户登录！userName=" + userName + ",simId=" + simId + ",itSysCode=" + itSysCode + ",ip=" + ip + ":" + port);
		String realPwd = EncryptUtil.decode(password);
		XMLParamParser wsParams = XMLParamParser.createInstance();
		wsParams.addAttr(Constants.INF_TYPE, "" ,Constants.INF_TYPE_WS_USERAUTHENTICATION);
		wsParams.addAttr(Constants.USERNAME, "" ,userName);
		wsParams.addAttr(Constants.PASSWORD, "" ,realPwd);
		
		ItSysWsFacade itSysWsFacade = WSUtil.getItSysWsFacade(itSysCode);
		FieldInfo res = new FieldInfo(Constants.ISSUCCESS, "是否成功" ,Boolean.toString(false));
		String wsReslut = null;
		String userFullName = "";
		try {
			wsReslut = itSysWsFacade.invoke(wsParams.getXmlContent());
			XmlInfo xml = XMLParamParser.convert(wsReslut);
			String issucc = xml.getContent(Constants.ISSUCCESS);
			if ("true".equalsIgnoreCase(issucc)) {
				userFullName = xml.getContent(Constants.USERFULLNAME);
				UserInfo user = userManagerService.getUser(userName, itSysCode);
				if (user != null) {
					user.setSimId(simId);
					user.setPassword(password);
					user.setUserFullName(userFullName);
				} else {
					user = new UserInfo(itSysCode, simId, userName, password);
					user.setUserFullName(userFullName);
					userManagerService.saveOrUpdate(user);
				}
				res = new FieldInfo(Constants.ISSUCCESS, "是否成功" ,Boolean.toString(true));
				log.info("用户登录成功！userName=" + userName);
			} else {
				log.info("用户登录失败！userName=" + userName);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		
		XMLParamParser udpParams = XMLParamParser.createInstance();
		udpParams.addAttr(Constants.INF_TYPE, "接口类型" ,Constants.INF_TYPE_TCP_REUSERINIT);
		udpParams.addAttr(res);
		udpParams.addAttr(Constants.ITSYSNAME, "系统名称" ,itSysCode);
		udpParams.addAttr(Constants.USERNAME, "用户登录名" ,userName);
		udpParams.addAttr(Constants.USERFULLNAME, "用户全名" ,userFullName);
		return udpParams.getXmlContent();
	}
	
	/**
	 * 待办推送，向android客户端推送待办工单的xml
	 * @param userName 登录名
	 * @param itSysName 业务系统标识
	 * @param xml xml参数
	 */
	public void sendWaitDeal(String userName, String itSysName, String xml) {
		UserOnline userOnline = userOnlineManagerService.getUserOnline(userName, itSysName);
		if (userOnline != null) {
			String ip = userOnline.getOnlineIpAddress();
			int port = userOnline.getPort();
			log.info("手机终端在线，开始发送待办,userName=" + userName + ",itSysName=" + itSysName + ",ip=" + ip + ":" + port);
			UDPClient udpClient = new UDPClient(ip, port);
			XMLParamParser parser = XMLParamParser.parseXML(xml);
			parser.removeField(Constants.INF_TYPE);
			parser.addAttr(Constants.INF_TYPE, "接口类型" , Constants.INF_TYPE_UDP_PUSHWAITING);
			udpClient.send(parser.getXmlContent());
		} else {
			log.info("手机终端不在线，不推送待办信息!userName=" + userName);
		}
	}
	
	/**
	 * 手机客户端主动获取待办列表
	 * @param itSysCode 业务系统标识
	 * @param userName 登录名
	 * @param password 密码
	 */
	private void pullUserWaitList(String itSysCode, String userName, String password) {
		XMLParamParser wsParams = XMLParamParser.createInstance();
		wsParams.addAttr(Constants.INF_TYPE, "" ,Constants.INF_TYPE_WS_PULLUSERWAITINGLIST);
		wsParams.addAttr(Constants.USERNAME, "" ,userName);
		wsParams.addAttr(Constants.PASSWORD, "" ,password);
		log.info("手机客户端主动获取待办信息！userName=" + userName);
		try {
			ItSysWsFacade itSysWsFacade = WSUtil.getItSysWsFacade(itSysCode);
			itSysWsFacade.invoke(wsParams.getXmlContent());
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 向手机端推送其他信息
	 * @param itSysCode 业务系统标识
	 * @param userName 登录名
	 * @param xmlInfo xml参数
	 */
	public void pushNotice(String itSysCode, String userName, XmlInfo xmlInfo) {
		XMLParamParser paraXml = XMLParamParser.createInstance();
		paraXml.addAttr(Constants.INF_TYPE, "接口类型", Constants.INF_TYPE_UDP_PUSHNOTICE);
		paraXml.addAttr(Constants.ITSYSNAME, "业务系统名称", itSysCode);
		paraXml.addAttr(Constants.PUSHNOTICEDETAILS, "通知内容", xmlInfo.getContent(Constants.PUSHNOTICEDETAILS));
		List<UserOnline> onlineUsers = userOnlineManagerService.getOnlineUsers(itSysCode);
		if (CollectionUtils.isNotEmpty(onlineUsers)) {
			log.info("开始发送通知！itSysName=" + itSysCode);
			for (int i = 0; i < onlineUsers.size(); i++) {
				UserOnline userOnline = onlineUsers.get(i);
				String ip = userOnline.getOnlineIpAddress();
				int port = userOnline.getPort();
				UDPClient udpClient = new UDPClient(ip, port);
				udpClient.send(paraXml.getXmlContent());
			}
		}
	}

	/**
	 * 方法调用入口
	 */
	public String invoke(String paramXml, String ip, int port) throws Exception {
		if (StringUtils.isBlank(paramXml)) {
			throw new Exception("调用服务参数不能为空！");
		}
		String rtn = null;
		XmlInfo xmlInfo = XMLParamParser.convert(paramXml);
		String infType = xmlInfo.getContent(Constants.INF_TYPE);
		String userName = xmlInfo.getContent(Constants.USERNAME);
		String password = xmlInfo.getContent(Constants.PASSWORD);
		String simId = xmlInfo.getContent(Constants.SIMID);
		String itSysCode = xmlInfo.getContent(Constants.ITSYSNAME);
		if (StringUtils.isBlank(itSysCode)) {
			throw new Exception("业务系统名称不能为空！");
		}
		log.info("接口调用 userName=" + userName + ",simId=" + simId + ",infType=" + infType + ",itSysName=" + itSysCode + ",ip=" + ip + ":" + port);
		if (Constants.INF_TYPE_TCP_USERINIT.equals(infType)) {
			rtn = this.login(userName, password, simId, itSysCode, ip, port);
		} else if (Constants.INF_TYPE_USERONLINE.equals(infType)) {
			this.heartbeatNotice(userName, password, simId, itSysCode, ip, port);
		} else if (Constants.INF_TYPE_WS_PUSHWAITING.equals(infType)) {
			this.sendWaitDeal(userName, itSysCode, paramXml);
		} else if (Constants.INF_TYPE_UDP_GETUSERWAITINGLIST.equals(infType)) {
			this.pullUserWaitList(itSysCode, userName, password);
		} else if (Constants.INF_TYPE_WS_PUSHNOTICE.equals(infType)) {
			this.pushNotice(itSysCode, userName, xmlInfo);
		}  
		return rtn;
	}
	

	public UserManagerService getUserManagerService() {
		return userManagerService;
	}

	public void setUserManagerService(UserManagerService userManagerService) {
		this.userManagerService = userManagerService;
	}

	public UserOnlineManagerService getUserOnlineManagerService() {
		return userOnlineManagerService;
	}

	public void setUserOnlineManagerService(
			UserOnlineManagerService userOnlineManagerService) {
		this.userOnlineManagerService = userOnlineManagerService;
	}
}
