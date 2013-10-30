package com.ultrapower.eoms.mobile.client;

import java.io.File;
import java.util.List;

public interface MobileClientService
{
	public String invoke(String serviceCode, String inputXml, int hasPic, int hasRec, List<File> file, List<String> fileName) throws Exception;
}
