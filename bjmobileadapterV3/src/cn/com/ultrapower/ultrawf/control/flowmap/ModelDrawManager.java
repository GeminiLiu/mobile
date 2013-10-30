package cn.com.ultrapower.ultrawf.control.flowmap;

import java.util.*;

import cn.com.ultrapower.ultrawf.models.design.GroupUserInterfaceTmp;
import cn.com.ultrapower.ultrawf.models.design.RoleUserHandler;
import cn.com.ultrapower.ultrawf.models.design.UserModel;
import cn.com.ultrapower.ultrawf.models.flowmap.*;
import cn.com.ultrapower.ultrawf.share.*;
import cn.com.ultrapower.ultrawf.share.flowmap.*;
import cn.com.ultrapower.system.util.FormatTime;

/**
 * 画环节的类
 * 
 * @author BigMouse
 */
public class ModelDrawManager
{
	// 画图的样式集合
	private ProcessStatusList pStatusList = null;

	/**
	 * 起始的标识
	 */
	private final String startModelTemplite = "<v:oval FillColor=\"#EEEEEE\" style=\"position:absolute; left:$LEFT$; top:$TOP$; width:20; height:20\" />";
	
	private final String startArrowTemplite = "<v:PolyLine style=\"position:absolute\" filled=\"false\" Points=\"$STARTPOS$ $MIDDLEPOS$ $ENDPOS$\" strokeweight=\"$WIDTH$pt\"><v:stroke EndArrow=\"Block\" /></v:PolyLine><div style=\"position:absolute; top:$NOTETOP$; left:$NOTELEFT$; font-size:12px;\"><font title=\"$ALTCONTENT$\">$NOTECONTENT$</font></div>";

	/**
	 * 环节的样式模板
	 */
	private final String tplModelTemplite = "<v:Rect id=\"$PHASEID$\" $FREEJS$ onmouseover=\"showProcessInfo('$JSPHASEID$')\" onclick=\"InforBar.showProcessInfo('$JSBPHASEID$')\" onmouseout=\"hideProcessInfo()\" strokeweight=\"$W$\" style=\"width:$WIDTH$; height:$HEIGHT$; cursor:$CUR$; position:absolute; top:$TOP$; left:$LEFT$;\" filled=\"t\" fillcolor=\"$COLOR$\"><v:TextBox inset=\"0,0,0,0\" style=\"text-align:center; font-size:11px;\"><div style=\"padding-top:2px;\">$TITLE$</div></v:TextBox><v:fill type=\"gradient\" colors2=\"White\" /><v:shadow on=\"t\" type=\"perspective\" color=\"black\" opacity=\".2\" obscured=\"t\" offset=\"1.8pt,1.8pt\" /></v:Rect>";

	private final String modelTemplite = "<v:RoundRect id=\"$PHASEID$\" $FREEJS$ onmouseover=\"showProcessInfo('$JSPHASEID$')\" onclick=\"InforBar.showProcessInfo('$JSBPHASEID$')\" onmouseout=\"hideProcessInfo()\" strokeweight=\"$W$\" style=\"width:$WIDTH$; height:$HEIGHT$; cursor:$CUR$; position:absolute; top:$TOP$; left:$LEFT$;\" filled=\"t\" fillcolor=\"$COLOR$\" arcsize=\"0.15\"><v:TextBox inset=\"0,0,0,0\" style=\"text-align:center; font-size:11px;\"><div style=\"padding-top:2px;\">$TITLE$</div></v:TextBox><v:fill type=\"gradient\" colors2=\"White\" /><v:shadow on=\"t\" type=\"perspective\" color=\"black\" opacity=\".2\" obscured=\"t\" offset=\"1.8pt,1.8pt\" /></v:RoundRect>";
	
	/**
	 * IN的箭头的样式模板
	 */
	private final String inArrowTemplite = "<v:PolyLine style=\"position:absolute\" filled=\"false\" Points=\"$STARTPOS$ $MIDDLEPOS$ $ENDPOS$\" strokeweight=\"$WIDTH$pt\"><v:stroke EndArrow=\"Block\"  dashstyle=\"$ARROWTYPE$\" /></v:PolyLine><div style=\"position:absolute; top:$NOTETOP$; left:$NOTELEFT$; font-size:12px;\"><font title=\"$ALTCONTENT$\">$NOTECONTENT$</font></div>";

	/**
	 * OUT的箭头的样式模板
	 */
	private final String outArrowTemplite = "<v:Line style=\"position:absolute\" from=\"$LINESTARTPOS$\" to=\"$LINEENDPOS$\" strokeweight=\"$LINEWIDTH$pt\"></v:Line><v:Line style=\"position:absolute\" from=\"$STARTPOS$\" to=\"$ENDPOS$\" strokeweight=\"$WIDTH$pt\"><v:stroke StartArrow=\"Block\" dashstyle=\"$ARROWTYPE$\" /></v:Line><div style=\"position:absolute; top:$NOTETOP$; left:$NOTELEFT$; font-size:12px;\"><font title=\"$ALTCONTENT$\">$NOTECONTENT$</font></div>";

	/**
	 * 画ERROR的样式模板
	 */
	private final String errorString = "<v:Line style=\"position:absolute\" StrokeColor=\"#FF0000\" filled=\"false\" from=\"$LEFTSTARTPOS$\" to=\"$LEFTENDPOS$\" strokeweight=\"2pt\"></v:Line><v:Line style=\"position:absolute\" StrokeColor=\"#FF0000\" filled=\"false\" from=\"$RIGHTSTARTPOS$\" to=\"$RIGHTENDPOS$\" strokeweight=\"2pt\"></v:Line>";
	
	/**
	 * 重载的构造函数，设置流程图的样式
	 * 
	 * @param psList
	 */
	protected ModelDrawManager(ProcessStatusList psList)
	{
		pStatusList = psList;
	}

	/**
	 * 画环节
	 * 
	 * @param pModel：环节实体对象
	 * @param rowNum：所在的行数
	 * @param cellNum：所在的列数
	 * @param rowCount：所处兄弟节点索引
	 * @return画环节的字符串
	 */
	protected StringBuffer draw(ProcessModel pModel, int rowNum, int cellNum, int rowCount)
	{
		ProcessInfo activeProcess = null;

		// 判断环节的状态
		List duplicatedProcessList = pModel.getDuplicatedProcess();
		if (duplicatedProcessList.isEmpty())
		{
			activeProcess = pModel.getProcess();
		}
		else
		{
			activeProcess = (ProcessInfo) duplicatedProcessList.get(duplicatedProcessList.size() - 1);
		}

		// 打印环节的信息（测试）
		System.out.println("环节：" + pModel.getPhaseNo() + "  状态为：" + activeProcess.getProcessStatus() + "  画在：" + rowNum + "行 " + cellNum + "列");

		ProcessStatusModel pStatusModel = pStatusList.getProcessStatusModel(activeProcess.getProcessStatus());

		// 判断在配置文件中是否有这个环节
		if (pStatusModel == null)
		{
			System.out.println("没有此环节：" + activeProcess.getProcessStatus());
			pStatusModel = new ProcessStatusModel();
			pStatusModel.setColor("#CCCCCC");
			pStatusModel.setColor1("#FFFFFF");
			pStatusModel.setInfoText(activeProcess.getProcessStatus());
			pStatusModel.setStatusName(activeProcess.getProcessStatus());
			pStatusModel.setArrowType("BLOCK");
			pStatusModel.setArrowWay("IN");
		}

		// 计算环节起始点所在的坐标
		int modelX;
		int modelY;
		modelX = pStatusList.getStartX() + (cellNum - 1) * (pStatusList.getModelWidth() + pStatusList.getArrowLength());
		modelY = pStatusList.getStartY() + (rowNum - 1) * (pStatusList.getModelHeight() + pStatusList.getRowHeight());

		//if(!pModel.getBeginPhaseNo().equals("BEGIN") && (pModel.getBeginPhaseNo().equals(pModel.getPhaseNo()) || pModel.getProcess().getFlagType() == 3))
		if(1==2)
		{
			modelX += pStatusList.getArrowLength() + 20;
		}
		
		// 替换模板中的元素
		StringBuffer templite = new StringBuffer();
		if(pModel.getFlagPredefined() == 0)
		{
			templite = new StringBuffer(modelTemplite);
		}
		else if(pModel.getFlagPredefined() == 1)
		{
			templite = new StringBuffer(tplModelTemplite);
		}
		templite.replace(templite.indexOf("$PHASEID$"), templite.indexOf("$PHASEID$") + 9, pModel.getPhaseNo());
		templite.replace(templite.indexOf("$JSPHASEID$"), templite.indexOf("$JSPHASEID$") + 11, pModel.getPhaseNo());
		templite.replace(templite.indexOf("$JSBPHASEID$"), templite.indexOf("$JSBPHASEID$") + 12, pModel.getPhaseNo());
		templite.replace(templite.indexOf("$COLOR$"), templite.indexOf("$COLOR$") + 7, pStatusModel.getColor());
		templite.replace(templite.indexOf("$WIDTH$"), templite.indexOf("$WIDTH$") + 7, String.valueOf(pStatusList.getModelWidth()));
		templite.replace(templite.indexOf("$HEIGHT$"), templite.indexOf("$HEIGHT$") + 8, String.valueOf(pStatusList.getModelHeight()));
		templite.replace(templite.indexOf("$TOP$"), templite.indexOf("$TOP$") + 5, String.valueOf(modelY));
		templite.replace(templite.indexOf("$LEFT$"), templite.indexOf("$LEFT$") + 6, String.valueOf(modelX));

		// ******************************
		// 环节中显示的内容
		String titleString = "";
		String more = "";
		if(pModel.getHasFreeProcess())
		{
			more = "<v:Rect fillcolor=\"red\" style=\"width:5; height:5; position:absolute; top:0; left:0;\"></v:Rect>";
			more += "<v:Rect fillcolor=\"red\" style=\"width:5; height:5; position:absolute; top:43; left:0;\"></v:Rect>";
			more += "<v:Rect fillcolor=\"red\" style=\"width:5; height:5; position:absolute; top:43; left:113;\"></v:Rect>";
			more += "<v:Rect fillcolor=\"red\" style=\"width:5; height:5; position:absolute; top:0; left:113;\"></v:Rect>";
			//more = more + "<v:Rect style=\"width:5;height:5; position:absolute; cursor:hand; top:0; left:113;\" filled=\"t\" fillcolor=\"RED\"></v:Rect>";
			//more = more + "<v:Rect style=\"width:5;height:5; position:absolute; cursor:hand; top:43; left:0;\" filled=\"t\" fillcolor=\"RED\"></v:Rect>";
			//more = more + "<v:Rect style=\"width:5;height:5; position:absolute; cursor:hand; top:43; left:113;\" filled=\"t\" fillcolor=\"RED\"></v:Rect>";
			templite.replace(templite.indexOf("$FREEJS$"), templite.indexOf("$FREEJS$") + 8, "ondblclick=\"showFreeFlowMap('" + pModel.getPhaseNo() + "')\"");
			templite.replace(templite.indexOf("$W$"), templite.indexOf("$W$") + 3, "1.2");
			templite.replace(templite.indexOf("$CUR$"), templite.indexOf("$CUR$") + 5, "hand");
		}
		else
		{
			templite.replace(templite.indexOf("$FREEJS$"), templite.indexOf("$FREEJS$") + 8, "");
			templite.replace(templite.indexOf("$W$"), templite.indexOf("$W$") + 3, "1");
			templite.replace(templite.indexOf("$CUR$"), templite.indexOf("$CUR$") + 5, "default");
		}
		
		if(pModel.getProcess().getDealer() == null)
		{
			if(pModel.getProcess().getAssginee() == null)
			{
				titleString += "<span style=\"font-weight:bold;\">" + activeProcess.getProcessStatus() + "/" + pModel.getProcess().getGroup() + "</span>" + more + "<br />" + FormatTime.formatIntToDateString(activeProcess.getEdDate());
			}
			else
			{
				titleString += "<span style=\"font-weight:bold;\">" + activeProcess.getProcessStatus() + "/" + pModel.getProcess().getAssginee() + "</span>" + more + "<br />" + FormatTime.formatIntToDateString(activeProcess.getEdDate());
			}
		}
		else
		{
			titleString += "<span style=\"font-weight:bold;\">" + activeProcess.getProcessStatus() + "/" + pModel.getProcess().getDealer() + "</span>" + more + "<br />" + FormatTime.formatIntToDateString(activeProcess.getEdDate());
		}
		templite.replace(templite.indexOf("$TITLE$"), templite.indexOf("$TITLE$") + 7, titleString);
		// ******************************

		// ******************************
		// 画环节详细信息
		StringBuffer processInfo = new StringBuffer();
		processInfo.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
		processInfo.append("<Process>");

		// 画主环节信息
		processInfo.append(drawProcessInfo(pModel.getProcess()));

		//
		for (Iterator it = duplicatedProcessList.iterator(); it.hasNext();)
		{
			// 画复制品环节信息
			processInfo.append(drawProcessInfo((ProcessInfo) it.next()));
		}
		processInfo.append("<SubLogs>");
		processInfo.append("</SubLogs>");
		processInfo.append("</Process>");

		// 生成js，添加环节详细信息
		templite.append("<script>addPhaseLog(\"" + pModel.getPhaseNo() + "\", \"" + processInfo.toString().replace('"', '\'') + "\")</script>");
		// ******************************

		// 判断是否为起始环节，如果不是，则画其左侧的箭头
		if (!pModel.getPrevPhaseNo().equals(pModel.getBeginPhaseNo()))
		{
			// 向左和向右的箭头的上下间距
			int arrowSpace = 5;

			// 画IN的箭头，判断是否为兄弟节点中的第一行，如果是则箭头没有折线，否则箭头有折线
			templite.append(inArrowTemplite);
			int x1 = 0;
			int y1 = 0;
			int x2 = 0;
			int y2 = 0;
			int x3 = 0;
			int y3 = 0;
			int x4 = 0;
			int y4 = 0;
			if (rowCount > 0)
			{
				templite.replace(templite.indexOf("$STARTPOS$"), templite.indexOf("$STARTPOS$") + 10, String.valueOf(modelX - pStatusList.getArrowLength() + 10) + "," + String.valueOf(modelY + pStatusList.getModelHeight() / 2 - (pStatusList.getModelHeight() + pStatusList.getRowHeight()) * rowCount - arrowSpace));
				x1 = modelX - pStatusList.getArrowLength() + 10;
				y1 = modelY + pStatusList.getModelHeight() / 2 - (pStatusList.getModelHeight() + pStatusList.getRowHeight()) * rowCount - arrowSpace;
			}
			else
			{
				templite.replace(templite.indexOf("$STARTPOS$"), templite.indexOf("$STARTPOS$") + 10, String.valueOf(modelX - pStatusList.getArrowLength()) + "," + String.valueOf(modelY + pStatusList.getModelHeight() / 2 - arrowSpace));
				x1 = modelX - pStatusList.getArrowLength();
				y1 = modelY + pStatusList.getModelHeight() / 2 - arrowSpace;
			}
			templite.replace(templite.indexOf("$MIDDLEPOS$"), templite.indexOf("$MIDDLEPOS$") + 11, String.valueOf(modelX - pStatusList.getArrowLength() + 10) + "," + String.valueOf(modelY + pStatusList.getModelHeight() / 2 - arrowSpace));
			x2 = x3 = modelX - pStatusList.getArrowLength() + 10;
			y2 = y3 = modelY + pStatusList.getModelHeight() / 2 - arrowSpace;
			templite.replace(templite.indexOf("$ENDPOS$"), templite.indexOf("$ENDPOS$") + 8, String.valueOf(modelX) + "," + String.valueOf(modelY + pStatusList.getModelHeight() / 2 - arrowSpace));
			x4 = modelX;
			y4 = modelY + pStatusList.getModelHeight() / 2 - arrowSpace;
			if(pModel.getFlagPredefined() == 1)
			{
				templite.replace(templite.indexOf("$WIDTH$"), templite.indexOf("$WIDTH$") + 7, String.valueOf(pStatusList.getArrowwidth() + ".5"));
			}
			else
			{
				templite.replace(templite.indexOf("$WIDTH$"), templite.indexOf("$WIDTH$") + 7, String.valueOf(pStatusList.getArrowwidth()));
			}
			if(pStatusModel.getArrowType().equals("ERROR") && pStatusModel.getArrowWay().equals("IN"))
			{
				String errorStr = "";
				errorStr = getErrorString(modelX, modelY, pStatusModel.getArrowWay());
				templite.replace(templite.indexOf("$ARROWTYPE$"), templite.indexOf("$ARROWTYPE$") + 11, "Block");
				templite.append(errorStr);
			}
			else if(pStatusModel.getArrowType().equals("DASHED") && pStatusModel.getArrowWay().equals("IN"))
			{
				templite.replace(templite.indexOf("$ARROWTYPE$"), templite.indexOf("$ARROWTYPE$") + 11, "LongDash");
			}
			else
			{
				templite.replace(templite.indexOf("$ARROWTYPE$"), templite.indexOf("$ARROWTYPE$") + 11, "Block");
			}

			// ******************************
			// 画箭头的文字
			templite.replace(templite.indexOf("$NOTETOP$"), templite.indexOf("$NOTETOP$") + 9, String.valueOf(modelY + pStatusList.getModelHeight() / 2 - 22));
			templite.replace(templite.indexOf("$NOTELEFT$"), templite.indexOf("$NOTELEFT$") + 10, String.valueOf(modelX - pStatusList.getArrowLength() + 17));
			if (pModel.getProcess().getDesc() != null)
			{
				String altcontent = "";
				if(pModel.getProcess().getStDate() > 0)
				{
					altcontent = FormatTime.formatIntToDateString(pModel.getProcess().getStDate()) + "，" + pModel.getProcess().getDesc().substring(0, pModel.getProcess().getDesc().length() - 1);
				}
				templite.replace(templite.indexOf("$ALTCONTENT$"), templite.indexOf("$ALTCONTENT$") + 12, altcontent);
				if (pModel.getProcess().getDesc().length() > 7)
				{
					templite.replace(templite.indexOf("$NOTECONTENT$"), templite.indexOf("$NOTECONTENT$") + 13, pModel.getProcess().getDesc().substring(0, 6) + "...");
				}
				else
				{
					templite.replace(templite.indexOf("$NOTECONTENT$"), templite.indexOf("$NOTECONTENT$") + 13, pModel.getProcess().getDesc().substring(0, pModel.getProcess().getDesc().length() - 1));
				}
			}
			else
			{
				templite.replace(templite.indexOf("$NOTECONTENT$"), templite.indexOf("$NOTECONTENT$") + 13, "");
			}
			System.out.println("IN的箭头的内容：" + pModel.getProcess().getDesc());
			
			// ******************************
			templite.append("<script>PreviewBar.drawLink('" + pModel.getPhaseNo() + "in', 'IN', " + x1 + ", " + y1 + ", " + x2 + ", " + y2 + ", " + x3 + ", " + y3 + ", " + x4 + ", " + y4 + ");</script>");
			// 画OUT的箭头
			if (pStatusModel.getArrowWay().equals("OUT"))
			{
				templite.append(outArrowTemplite);
				templite.replace(templite.indexOf("$LINESTARTPOS$"), templite.indexOf("$LINESTARTPOS$") + 14, String.valueOf(modelX - pStatusList.getArrowLength() + 10) + "," + String.valueOf(modelY + pStatusList.getModelHeight() / 2 - arrowSpace));
				templite.replace(templite.indexOf("$LINEENDPOS$"), templite.indexOf("$LINEENDPOS$") + 12, String.valueOf(modelX - pStatusList.getArrowLength() + 10) + "," + String.valueOf(modelY + pStatusList.getModelHeight() / 2 + arrowSpace));
				templite.replace(templite.indexOf("$LINEWIDTH$"), templite.indexOf("$LINEWIDTH$") + 11, String.valueOf(pStatusList.getArrowwidth()));
				templite.replace(templite.indexOf("$STARTPOS$"), templite.indexOf("$STARTPOS$") + 10, String.valueOf(modelX - pStatusList.getArrowLength() + 10) + "," + String.valueOf(modelY + pStatusList.getModelHeight() / 2 + arrowSpace));
				templite.replace(templite.indexOf("$ENDPOS$"), templite.indexOf("$ENDPOS$") + 8, String.valueOf(modelX) + "," + String.valueOf(modelY + pStatusList.getModelHeight() / 2 + arrowSpace));
				templite.replace(templite.indexOf("$WIDTH$"), templite.indexOf("$WIDTH$") + 7, String.valueOf(pStatusList.getArrowwidth()));
				x1 = modelX - pStatusList.getArrowLength() + 10;
				y1 = modelY + pStatusList.getModelHeight() / 2 + arrowSpace;
				x2 = modelX;
				y2 = modelY + pStatusList.getModelHeight() / 2 + arrowSpace;
				templite.append("<script>PreviewBar.drawLink('" + pModel.getPhaseNo() + "out', 'OUT', " + x1 + ", " + y1 + ", " + x2 + ", " + y2 + ", " + x3 + ", " + y3 + ", " + x4 + ", " + y4 + ");</script>");
				if(pStatusModel.getArrowType().equals("BLOCK"))
				{
					templite.replace(templite.indexOf("$ARROWTYPE$"), templite.indexOf("$ARROWTYPE$") + 11, "Block");
				}
				else if(pStatusModel.getArrowType().equals("ERROR"))
				{
					String errorStr = "";
					errorStr = getErrorString(modelX, modelY, pStatusModel.getArrowWay());
					templite.replace(templite.indexOf("$ARROWTYPE$"), templite.indexOf("$ARROWTYPE$") + 11, "Block");
					templite.append(errorStr);
				}
				else if(pStatusModel.getArrowType().equals("DASHED"))
				{
					templite.replace(templite.indexOf("$ARROWTYPE$"), templite.indexOf("$ARROWTYPE$") + 11, "LongDash");
				}
				
				// ******************************
				// 画箭头的文字
				templite.replace(templite.indexOf("$NOTETOP$"), templite.indexOf("$NOTETOP$") + 9, String.valueOf(modelY + pStatusList.getModelHeight() / 2 + 10));
				templite.replace(templite.indexOf("$NOTELEFT$"), templite.indexOf("$NOTELEFT$") + 10, String.valueOf(modelX - pStatusList.getArrowLength() + 17));
				templite.replace(templite.indexOf("$ALTCONTENT$"), templite.indexOf("$ALTCONTENT$") + 12, FormatTime.formatIntToDateString(activeProcess.getEdDate()) + "，" + activeProcess.getProcessStatus());
				templite.replace(templite.indexOf("$NOTECONTENT$"), templite.indexOf("$NOTECONTENT$") + 13, activeProcess.getProcessStatus());
				System.out.println("OUT的箭头的内容：" + activeProcess.getProcessStatus());
				// ******************************
			}
		}
		//else if(!pModel.getBeginPhaseNo().equals("BEGIN") && !pModel.getHasFreeProcess() || !pModel.getBeginPhaseNo().equals("BEGIN") && pModel.getProcess().getFlagType() == 3 || pModel.getBeginPhaseNo().equals("BEGIN"))
		else if(1== 2)
		{
			//画起始标识
			if (rowCount > 0)
			{
				templite.append(startArrowTemplite);
				templite.replace(templite.indexOf("$STARTPOS$"), templite.indexOf("$STARTPOS$") + 10, String.valueOf(modelX - pStatusList.getArrowLength() + 10) + "," + String.valueOf(modelY + pStatusList.getModelHeight() / 2 - (pStatusList.getModelHeight() + pStatusList.getRowHeight()) * rowCount));
			}
			else
			{
				templite.append(startModelTemplite);
				templite.replace(templite.indexOf("$TOP$"), templite.indexOf("$TOP$") + 5, String.valueOf(modelY + pStatusList.getModelHeight() / 2 - 10));
				templite.replace(templite.indexOf("$LEFT$"), templite.indexOf("$LEFT$") + 6, String.valueOf(modelX - pStatusList.getArrowLength() - 20));
				templite.append(startArrowTemplite);
				templite.replace(templite.indexOf("$STARTPOS$"), templite.indexOf("$STARTPOS$") + 10, String.valueOf(modelX - pStatusList.getArrowLength()) + "," + String.valueOf(modelY + pStatusList.getModelHeight() / 2));
			}
			templite.replace(templite.indexOf("$MIDDLEPOS$"), templite.indexOf("$MIDDLEPOS$") + 11, String.valueOf(modelX - pStatusList.getArrowLength() + 10) + "," + String.valueOf(modelY + pStatusList.getModelHeight() / 2));
			templite.replace(templite.indexOf("$ENDPOS$"), templite.indexOf("$ENDPOS$") + 8, String.valueOf(modelX) + "," + String.valueOf(modelY + pStatusList.getModelHeight() / 2));
			templite.replace(templite.indexOf("$WIDTH$"), templite.indexOf("$WIDTH$") + 7, String.valueOf(pStatusList.getArrowwidth()));

			// ******************************
			// 画箭头的文字
			templite.replace(templite.indexOf("$NOTETOP$"), templite.indexOf("$NOTETOP$") + 9, String.valueOf(modelY + pStatusList.getModelHeight() / 2 - 17));
			templite.replace(templite.indexOf("$NOTELEFT$"), templite.indexOf("$NOTELEFT$") + 10, String.valueOf(modelX - pStatusList.getArrowLength() + 17));
			if (pModel.getProcess().getDesc() != null)
			{
				templite.replace(templite.indexOf("$ALTCONTENT$"), templite.indexOf("$ALTCONTENT$") + 12, pModel.getProcess().getDesc());
				if (pModel.getProcess().getDesc().length() > 7)
				{
					templite.replace(templite.indexOf("$NOTECONTENT$"), templite.indexOf("$NOTECONTENT$") + 13, pModel.getProcess().getDesc().substring(0, 6) + "...");
				}
				else
				{
					templite.replace(templite.indexOf("$NOTECONTENT$"), templite.indexOf("$NOTECONTENT$") + 13, pModel.getProcess().getDesc().substring(0, pModel.getProcess().getDesc().length() - 1));
				}
			}
			else
			{
				templite.replace(templite.indexOf("$NOTECONTENT$"), templite.indexOf("$NOTECONTENT$") + 13, "");
			}
		}

		templite.append("<script>PreviewBar.drawProcess('" + pModel.getPhaseNo() + "', " + modelX + ", " + modelY + ", '" + pStatusModel.getColor() + "');</script>");
		return templite;
	}

	/**
	 * 画环节详细信息
	 * 
	 * @param pInfo：环节数据
	 * @return 环节详细信息
	 */
	private StringBuffer drawProcessInfo(ProcessInfo pInfo)
	{
		StringBuffer templite = new StringBuffer();

		// 环节信息
		templite.append("<ProcessInfo>");

		templite.append("<Info>");
		
		templite.append("<Status>" + pInfo.getProcessStatus() + "</Status>");

		// 内容
		if (pInfo.getDesc() != null)
		{
			templite.append("<Desc>" + pInfo.getDesc() + "</Desc>");
		}
		else
		{
			templite.append("<Desc> </Desc>");
		}

		// 执行人
		if (pInfo.getDealer() != null)
		{
			templite.append("<Dealer>" + pInfo.getDealer() + "</Dealer>");
			templite.append("<PreDealer></PreDealer>");
		}
		else
		{
			if(pInfo.getAssginee() != null)
			{
				templite.append("<Dealer>" + pInfo.getAssginee() + "</Dealer>");
				templite.append("<PreDealer></PreDealer>");
			}
			else if(pInfo.getGroup() != null)
			{
				List<UserModel> userList = new ArrayList<UserModel>();
				if(pInfo.getGroupID().length() > 6)
				{
					RoleUserHandler ruHandler = new RoleUserHandler();
					userList = ruHandler.getUsers(pInfo.getGroupID());
				}
				else
				{
					GroupUserInterfaceTmp guit = new GroupUserInterfaceTmp();
					userList = guit.getUserList(pInfo.getGroupID());
				}
				
				templite.append("<Dealer> </Dealer>");
				templite.append("<PreDealer>");
				for(Iterator<UserModel> it = userList.iterator(); it.hasNext();)
				{
					templite.append(it.next().getUserName() + ";");
				}

				templite.append("</PreDealer>");
			}
			else
			{
				templite.append("<Dealer> </Dealer>");
				templite.append("<PreDealer></PreDealer>");
			}
		}

		// 开始时间
		try
		{
			templite.append("<StDate>" + FormatTime.formatIntToDateString(pInfo.getStDate()) + "</StDate>");
		}
		catch (Exception e)
		{
			templite.append("<StDate> </StDate>");
		}

		// 处理时间
		try
		{
			templite.append("<DealDate>" + FormatTime.formatIntToDateString(pInfo.getBgDate()) + "</DealDate>");
		}
		catch (Exception e)
		{
			templite.append("<DealDate> </DealDate>");
		}

		// 完成时间
		try
		{
			templite.append("<FinishDate>" + FormatTime.formatIntToDateString(pInfo.getEdDate()) + "</FinishDate>");
		}
		catch (Exception e)
		{
			templite.append("<FinishDate> </FinishDate>");
		}
		templite.append("</Info>");

		// 日志信息
		templite.append("<Log>");

		// 遍历日志信息
		List pLogList = pInfo.getProcessLogs();
		for (Iterator it = pLogList.iterator(); it.hasNext();)
		{
			ProcessLogInfo pLogInfo = (ProcessLogInfo) it.next();
			templite.append("<LogInfo>");

			// Act
			templite.append("<Act>");
			templite.append(pLogInfo.getAct());
			templite.append("</Act>");

			// LogUser
			templite.append("<LogUser>");
			templite.append(pLogInfo.getLogUser());
			templite.append("</LogUser>");

			// StDate
			templite.append("<StDate>");
			templite.append(FormatTime.formatIntToDateString(pLogInfo.getStDate()));
			templite.append("</StDate>");

			// Result
			templite.append("<Result>");
			templite.append(pLogInfo.getResult().replace('\n', ' ').replace('\r', ' '));
			templite.append("</Result>");
			templite.append("</LogInfo>");
		}
		templite.append("</Log>");
		templite.append("</ProcessInfo>");
		return templite;
	}
	
	/**
	 * 画ERROR的字符串
	 * @param modelX：环节的起始横坐标
	 * @param modelY：环节的起始纵坐标
	 * @param arrowWay：环节箭头的方向
	 * @return ERROR的字符串
	 */
	private String getErrorString(int modelX, int modelY, String arrowWay)
	{
		StringBuffer errorStr = new StringBuffer(errorString);
		if(arrowWay.equals("IN"))
		{
			errorStr.replace(errorStr.indexOf("$LEFTSTARTPOS$"), errorStr.indexOf("$LEFTSTARTPOS$") + 14, String.valueOf(modelX - pStatusList.getArrowLength() / 2 - 5) + "," + String.valueOf(modelY + pStatusList.getModelHeight() / 2 - 10));
			errorStr.replace(errorStr.indexOf("$LEFTENDPOS$"), errorStr.indexOf("$LEFTENDPOS$") + 12, String.valueOf(modelX - pStatusList.getArrowLength() / 2 + 5) + "," + String.valueOf(modelY + pStatusList.getModelHeight() / 2));
			errorStr.replace(errorStr.indexOf("$RIGHTSTARTPOS$"), errorStr.indexOf("$RIGHTSTARTPOS$") + 15, String.valueOf(modelX - pStatusList.getArrowLength() / 2 + 5) + "," + String.valueOf(modelY + pStatusList.getModelHeight() / 2 - 10));
			errorStr.replace(errorStr.indexOf("$RIGHTENDPOS$"), errorStr.indexOf("$RIGHTENDPOS$") + 13, String.valueOf(modelX - pStatusList.getArrowLength() / 2 - 5) + "," + String.valueOf(modelY + pStatusList.getModelHeight() / 2));
		}
		else
		{
			errorStr.replace(errorStr.indexOf("$LEFTSTARTPOS$"), errorStr.indexOf("$LEFTSTARTPOS$") + 14, String.valueOf(modelX - pStatusList.getArrowLength() / 2 - 5) + "," + String.valueOf(modelY + pStatusList.getModelHeight() / 2 + 10));
			errorStr.replace(errorStr.indexOf("$LEFTENDPOS$"), errorStr.indexOf("$LEFTENDPOS$") + 12, String.valueOf(modelX - pStatusList.getArrowLength() / 2 + 5) + "," + String.valueOf(modelY + pStatusList.getModelHeight() / 2));
			errorStr.replace(errorStr.indexOf("$RIGHTSTARTPOS$"), errorStr.indexOf("$RIGHTSTARTPOS$") + 15, String.valueOf(modelX - pStatusList.getArrowLength() / 2 + 5) + "," + String.valueOf(modelY + pStatusList.getModelHeight() / 2 + 10));
			errorStr.replace(errorStr.indexOf("$RIGHTENDPOS$"), errorStr.indexOf("$RIGHTENDPOS$") + 13, String.valueOf(modelX - pStatusList.getArrowLength() / 2 - 5) + "," + String.valueOf(modelY + pStatusList.getModelHeight() / 2));
		}
		return errorStr.toString();
	}
}
