package com.ultrapower.eoms.mobile.model;

import cn.com.ultrapower.ultrawf.share.FormatString;

public class BaseOwnFieldModel
{
	//工单类型
	private String baseSchema;
	//字段英文名
	private String fieldCode;
	//字段中文名
	private String fieldName;
	//字段类型2,4,6,7
	private String fieldType;
	//字段归属，系统字段、主字段、动作字段
	private int fieldCategory;
	//字段所属的容器名称
	private String fieldContainer;
	//占用行数
	private int rows;
	//是否为后台逻辑字段
	private int hiddenField;
	//固定选择字段字典
	private String collectDic;
	//使用表作为数据源的字段字典
	private String tableDic;
	//外界计算出来的dic名称
	private String dicName;
	//外界计算出的回复模板名称
	private String templateName;
	//显示顺序
	private int order;
	//是否允许上传录音
	private int uploadRec;
	//是否允许上传照片
	private int uploadPic;
	
	private String tree_assignType;
	//派发树_是否可以多选
	private int tree_multiSelect;
	//派发树_是否显示公司
	private int tree_showCorp;
	//派发树_是否显示中心
	private int tree_showCenter;
	//派发树_是否显示驻点
	private int tree_showStation;
	//派发树_是否显示小组
	private int tree_showTeam;
	//派发树_是否显示人员
	private int tree_showPerson;
	//派发树_可选择的对象
	private String tree_selectObject;
	
	//可编辑动作
	private String editActions;
	
	public TemplateFieldModel toTemplateField()
	{
		TemplateFieldModel fieldModel = new TemplateFieldModel();
		fieldModel.setCode(fieldCode);
		if(hiddenField == 1)
		{
			fieldModel.setType("HIDDEN");
		}
		else if((dicName != null && !dicName.equals("")) || fieldType.equals("6"))
		{
			fieldModel.setType("SELECT");
			fieldModel.setDic(FormatString.CheckNullString(dicName));
		}
		else if(fieldType.equals("2"))
			fieldModel.setType("INTEGER");
		else if(fieldType.equals("4"))
		{
			if(rows > 1)
				fieldModel.setType("TEXTAREA");
			else
				fieldModel.setType("STRING");
		}
		else if(fieldType.equals("7"))
			fieldModel.setType("TIME");
		else if(fieldType.equals("1000"))
			fieldModel.setType("TREE");
		else if(fieldType.equals("10"))
		{
			fieldModel.setType("FLOAT");
		}
		else
		{
			fieldModel.setType("STRING");
		}
		fieldModel.setName(fieldName);
		fieldModel.setRow(rows);
		
		fieldModel.setAssignType(tree_assignType);
		fieldModel.setCorp(tree_showCorp);
		fieldModel.setCenter(tree_showCenter);
		fieldModel.setStation(tree_showStation);
		fieldModel.setTeam(tree_showTeam);
		fieldModel.setPerson(tree_showPerson);
		fieldModel.setSelect(tree_selectObject);
		fieldModel.setMulti(tree_multiSelect);
		
		fieldModel.setTemplate(templateName);
		return fieldModel;
	}

	public String getBaseSchema()
	{
		return baseSchema;
	}

	public void setBaseSchema(String baseSchema)
	{
		this.baseSchema = baseSchema;
	}

	public String getFieldCode()
	{
		return fieldCode;
	}

	public void setFieldCode(String fieldCode)
	{
		this.fieldCode = fieldCode;
	}

	public String getFieldName()
	{
		return fieldName;
	}

	public void setFieldName(String fieldName)
	{
		this.fieldName = fieldName;
	}

	public String getFieldType()
	{
		return fieldType;
	}

	public void setFieldType(String fieldType)
	{
		this.fieldType = fieldType;
	}

	public int getFieldCategory()
	{
		return fieldCategory;
	}

	public void setFieldCategory(int fieldCategory)
	{
		this.fieldCategory = fieldCategory;
	}

	public String getFieldContainer()
	{
		return fieldContainer;
	}

	public void setFieldContainer(String fieldContainer)
	{
		this.fieldContainer = fieldContainer;
	}

	public String getTemplateName()
	{
		return templateName;
	}

	public void setTemplateName(String templateName)
	{
		this.templateName = templateName;
	}

	public int getRows()
	{
		return rows;
	}

	public void setRows(int rows)
	{
		this.rows = rows;
	}

	public int getHiddenField()
	{
		return hiddenField;
	}

	public void setHiddenField(int hiddenField)
	{
		this.hiddenField = hiddenField;
	}

	public String getCollectDic()
	{
		return collectDic;
	}

	public void setCollectDic(String collectDic)
	{
		this.collectDic = collectDic;
	}

	public String getTableDic()
	{
		return tableDic;
	}

	public void setTableDic(String tableDic)
	{
		this.tableDic = tableDic;
	}

	public String getDicName()
	{
		return dicName;
	}

	public void setDicName(String dicName)
	{
		this.dicName = dicName;
	}

	public int getOrder()
	{
		return order;
	}

	public void setOrder(int order)
	{
		this.order = order;
	}

	public int getUploadRec()
	{
		return uploadRec;
	}

	public void setUploadRec(int uploadRec)
	{
		this.uploadRec = uploadRec;
	}

	public int getUploadPic()
	{
		return uploadPic;
	}

	public void setUploadPic(int uploadPic)
	{
		this.uploadPic = uploadPic;
	}

	public String getTree_assignType()
	{
		return tree_assignType;
	}

	public void setTree_assignType(String tree_assignType)
	{
		this.tree_assignType = tree_assignType;
	}

	public int getTree_multiSelect()
	{
		return tree_multiSelect;
	}

	public void setTree_multiSelect(int tree_multiSelect)
	{
		this.tree_multiSelect = tree_multiSelect;
	}

	public int getTree_showCorp()
	{
		return tree_showCorp;
	}

	public void setTree_showCorp(int tree_showCorp)
	{
		this.tree_showCorp = tree_showCorp;
	}

	public int getTree_showCenter()
	{
		return tree_showCenter;
	}

	public void setTree_showCenter(int tree_showCenter)
	{
		this.tree_showCenter = tree_showCenter;
	}

	public int getTree_showStation()
	{
		return tree_showStation;
	}

	public void setTree_showStation(int tree_showStation)
	{
		this.tree_showStation = tree_showStation;
	}

	public int getTree_showTeam()
	{
		return tree_showTeam;
	}

	public void setTree_showTeam(int tree_showTeam)
	{
		this.tree_showTeam = tree_showTeam;
	}

	public int getTree_showPerson()
	{
		return tree_showPerson;
	}

	public void setTree_showPerson(int tree_showPerson)
	{
		this.tree_showPerson = tree_showPerson;
	}

	public String getTree_selectObject()
	{
		return tree_selectObject;
	}

	public void setTree_selectObject(String tree_selectObject)
	{
		this.tree_selectObject = tree_selectObject;
	}

	public String getEditActions()
	{
		return editActions;
	}

	public void setEditActions(String editActions)
	{
		this.editActions = editActions;
	}
}
