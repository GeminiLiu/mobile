package daiwei.mobile.util.TempletUtil;


import eoms.mobile.R;
import daiwei.mobile.common.XMLUtil;
import daiwei.mobile.util.TempletUtil.TMPModel.Caetgory;
import daiwei.mobile.util.TempletUtil.TMPModel.Templet;
import daiwei.mobile.util.TempletUtil.TMPModel.TempletModel;


public class TempletService {

	// 收缩副标题
	private int detailItemLv = R.layout.gd_detail_item_lv;

	// 基础上下样式
	private int detailItemText = R.layout.gd_detail_item_text;

	// 模板管理(版本更新等)
	public TempletService() {
		
	}

	// 加载工单模板
	public void TempletLoad(String tmpType,String TempletXML) {
		XMLUtil xmlUtil = new XMLUtil(TempletXML);
		Templet tmp = new Templet();
        
        if(xmlUtil.getIsLegal()){
		// 解析数据字典
        	if(!tmpType.equals("basestation")){
		tmp.setDicDefine(xmlUtil.getDicDefine());
        	}
		// 解析字段
		tmp.setBaseFields(xmlUtil.getBaseField());
		// 解析按钮
		tmp.setActionButtons(xmlUtil.getActionButton());
		// 解析回复模板
		try {
		tmp.setTemplatestore(xmlUtil.gettemplatestore());
		}catch(Exception e) {
			System.out.println(TempletXML);
			e.printStackTrace();
		}
		TempletModel.TempletMap.put(tmpType, tmp);
         }

	}

	
	public void TempletLoadXJ(String tmpType,String TempletXML) {
		XMLUtil xmlUtil = new XMLUtil(TempletXML);
		TempletModel.TempletMapXJ.put(tmpType, xmlUtil.analycXML());
	}
	// 通用模板解析
	public void TempletAnalytic(String TempletID) {
		Templet tmp = TempletModel.TempletMap.get("TempletModel.TempletMap");

	}

	//
	public void CaetgoryLoad() {
		Caetgory cty = new Caetgory();

		cty.setId(1);
		cty.setCode("WF4:EL_TTM_TTH");
		TempletModel.CaetgoryByCode.put("WF4:EL_AM_PS", cty);
		TempletModel.CaetgoryById.put("1", cty);

		cty = new Caetgory();
		cty.setId(2);
		cty.setCode("WF4:EL_TTM_CCH");
		TempletModel.CaetgoryByCode.put("WF4:EL_TTM_CCH", cty);
		TempletModel.CaetgoryById.put("2", cty);

		cty = new Caetgory();
		cty.setId(3);
		cty.setCode("WF4:EL_UVS_TSK");
		TempletModel.CaetgoryByCode.put("WF4:EL_UVS_TSK", cty);
		TempletModel.CaetgoryById.put("3", cty);
	}
}