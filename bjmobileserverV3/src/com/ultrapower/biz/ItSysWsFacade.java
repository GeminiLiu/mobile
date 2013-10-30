package com.ultrapower.biz;

import javax.jws.WebService;

@WebService
public interface ItSysWsFacade {

	public String invoke(String params) throws Exception;
}
