package cn.com.ultrapower.ultrawf.control.config;

import java.util.*;

import cn.com.ultrapower.ultrawf.models.process.*;

public class BaseFieldManage {
	
	public BaseFieldManage(){		
	}

	public List<FieldModel> getFieldList(String schema){
		
		Field m_Field = new Field();
		return m_Field.getFieldList(schema);
	}
}
