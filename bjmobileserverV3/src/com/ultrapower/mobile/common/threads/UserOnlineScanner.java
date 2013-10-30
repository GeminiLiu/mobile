package com.ultrapower.mobile.common.threads;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.util.WebApplicationManager;
import com.ultrapower.mobile.common.constants.Constants;
import com.ultrapower.mobile.common.enums.UserOnlineEnum;
import com.ultrapower.mobile.model.UserOnline;
import com.ultrapower.mobile.service.UserOnlineManagerService;


public class UserOnlineScanner extends Thread {
	
	private static Logger log = LoggerFactory.getLogger(UserOnlineScanner.class);
	
	public UserOnlineScanner() {
		
	}
	
	@Override
	public void run() {
		log.info("开始扫描在线用户是否超时！");
		UserOnlineManagerService userOnlineManagerService = (UserOnlineManagerService) WebApplicationManager.getBean("userOnlineManagerService");
		while (true) {
			List<UserOnline> onlineUsers = userOnlineManagerService.getOnlineUsers();
			log.info("当前用户在线个数：" + onlineUsers.size());
			for (int i = 0; i < onlineUsers.size(); i++) {
				UserOnline userOnline = onlineUsers.get(i);
				String userName = userOnline.getUserName();
				String itSysName = userOnline.getItSysName();
				long loginTime = userOnline.getLoginTime();
				String simId = userOnline.getSimId();
				long heartBeatTime = userOnline.getHeartBeatTime();
				String onlineIpAddress = userOnline.getOnlineIpAddress();
				int port = userOnline.getPort();
				long currentTime = TimeUtils.getCurrentTime();
				if ((currentTime - heartBeatTime) > Constants.USERONLINE_TIMEOUT) {
					userOnline.setIsOnline(UserOnlineEnum.OFFLINE.getVal());
					userOnlineManagerService.saveOrUpdate(userOnline);
					log.info("用户超时，状态状态置为离线！ userName=" + userName
							+ ",loginTime="
							+ TimeUtils.formatIntToDateString(loginTime)
							+ ",heartBeatTime="
							+ TimeUtils.formatIntToDateString(heartBeatTime)
							+ ",ItSysName=" + itSysName + ",simId=" + simId
							+ ",ip=" + onlineIpAddress + ":" + port);
				}
			}
			try {
				TimeUnit.SECONDS.sleep(Constants.USERONLINE_SCAN_INTERVAL);
			} catch (InterruptedException e) {
				e.printStackTrace();
				log.error(e.getMessage(), e);
			}
		}
	}
}
