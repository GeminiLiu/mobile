package cn.com.ultrapower.ultrawf.control.flowmap;

import java.util.*;

import cn.com.ultrapower.ultrawf.share.flowmap.*;

/**
 * 画流程图的类
 * 
 * @author BigMouse
 */
public class ProcessDrawManager
{
	/**
	 * 一个完整的流程图的ProcessList
	 */
	private ProcessList pList;

	/**
	 * 当前流程图的编号
	 */
	private int rowNum = 0;

	private StringBuffer strFlowMap = new StringBuffer();

	private ModelDrawManager mDrawManager;

	/**
	 * 画流程图入口
	 * 
	 * @param processList：ProcessList的List
	 * @throws Exception
	 */
	public String draw(List processList) throws Exception
	{
		init();

		// 获取流程图的List
		for (Iterator it = processList.iterator(); it.hasNext();)
		{
			pList = (ProcessList) it.next();
			drawProcessList();
			rowNum++;
		}
		return strFlowMap.toString();
	}

	/**
	 * 初始化加载客户端的信息
	 */
	private void init()
	{
		strFlowMap.append("");
	}

	// /**
	// * 画一个完整的流程图
	// */
	// private void drawProcessList() throws Exception
	// {
	// // 获取画流程图的样式
	// FlowMapConfig fmConfig = new FlowMapConfig();
	// ProcessStatusList processsStatusMap = fmConfig.getProcessStatusList();
	//
	// // 创建画环节的对象
	// mDrawManager = new ModelDrawManager(processsStatusMap);
	//
	// // 建立栈，并向栈中压入起始环节
	// ProcessStack processStack = new ProcessStack();
	// processStack.push(getProcessModel(pList.getBeginPhaseNo()));
	//
	// // 类似非递归前序遍历
	// while (!processStack.empty())
	// {
	// // 获取栈顶的ProcessModel
	// ProcessModel pModel = (ProcessModel) processStack.pop();
	//
	// // 将前一环节号是取出的ProcessModel的环节号的环节压入栈中
	// List nextProcessList = getProcessModel(pModel.getPhaseNo());
	// int cellNum = processStack.push(nextProcessList);
	//
	// // 画取出的环节
	// strFlowMap.append(mDrawManager.draw(pModel, rowNum, cellNum, 1));
	//
	// if (nextProcessList.size() == 0)
	// {
	// rowNum++;
	// }
	// }
	// }

	/**
	 * 画一个完整的流程图
	 */
	private void drawProcessList() throws Exception
	{
		// 获取画流程图的样式
		FlowMapConfig fmConfig = new FlowMapConfig();
		ProcessStatusList processsStatusMap = fmConfig.getProcessStatusList();

		// 创建画环节的对象
		mDrawManager = new ModelDrawManager(processsStatusMap);

		List startModelList = getProcessModel(pList.getBeginPhaseNo());
		
		int row = 0;
		for (int i = 0; i < startModelList.size(); i++)
		{
			row = drawModel((ProcessModel) getProcessModel(pList.getBeginPhaseNo()).get(i), i + 1 + rowNum, 1, i + row);
		}
	}

	private int drawModel(ProcessModel pModel, int rowNum, int cellNum, int rowCount)
	{
		int rows = 0;
		int x = cellNum;
		int y = rowNum;
		if (cellNum == 1 && rowCount != 0)
		{
			this.rowNum++;
		}
		strFlowMap.append(mDrawManager.draw(pModel, rowNum, cellNum, rowCount));
		x++;

		List nextProcessList = getProcessModel(pModel.getPhaseNo());
		for (int i = 0; i < nextProcessList.size(); i++)
		{
			if (i != 0)
			{
				rows++;
				y++;
			}
			int num = drawModel((ProcessModel) nextProcessList.get(i), y, x, rows);
			rows += num;
			y += num;
		}
		this.rowNum += rows;
		return rows;
	}

	/**
	 * 通过前一环节号，获取ProcessModel
	 * 
	 * @param prevPhaseNo：环节号
	 * @return ProcessModel
	 */
	private List getProcessModel(String phaseNo)
	{
		List pModelList = new ArrayList();
		for (Iterator it = pList.getProcessList().iterator(); it.hasNext();)
		{
			ProcessModel pModel = (ProcessModel) it.next();
			if(pModel.getPrevPhaseNo().equals(phaseNo))
			{
				boolean isAdd = false;
				for (int i = 0; i < pModelList.size(); i++)
				{
					ProcessModel processModel = (ProcessModel) pModelList.get(i);
					if(processModel.getFlagPredefined() < pModel.getFlagPredefined())
					{
						pModelList.add(i, pModel);
						isAdd = true;
						break;
					}
					else if(processModel.getFlagPredefined() == pModel.getFlagPredefined() &&processModel.getStDate() > pModel.getStDate())
					{
						pModelList.add(i, pModel);
						isAdd = true;
						break;
					}
				}
				if (!isAdd)
				{
					pModelList.add(pModel);
				}
			}
		}
		return pModelList;
	}
	//
	// /**
	// * 封装遍历时所需要的栈
	// *
	// * @author BigMouse
	// */
	// class ProcessStack
	// {
	// Stack processStack = new Stack();
	//
	// ProcessNumStack pNumStack = new ProcessNumStack();
	//
	// ProcessStack()
	// {
	// processStack.clear();
	// }
	//
	// /**
	// * 向遍历需要的栈中压入环节实体对象
	// *
	// * @param processModelList：环节实体对象
	// * @return 环节所处的位置
	// */
	// int push(List processModelList)
	// {
	// for (int i = processModelList.size() - 1; i >= 0; i--)
	// {
	// processStack.push((ProcessModel) processModelList.get(i));
	// }
	// return pNumStack.push(processModelList.size());
	// }
	//
	// /**
	// * 从遍历需要的栈顶取出环节实体对象
	// *
	// * @return 环节实体对象
	// * @throws Exception
	// */
	// ProcessModel pop() throws Exception
	// {
	// pNumStack.reduceNum(1);
	// return (ProcessModel) processStack.pop();
	// }
	//
	// /**
	// * 遍历需要的栈是否非空
	// *
	// * @return true为空，false不为空
	// */
	// boolean empty()
	// {
	// return processStack.empty();
	// }
	// }
	//
	// /**
	// * 遍历环节的辅助类
	// *
	// * @author BigMouse
	// */
	// class ProcessNumStack
	// {
	// /**
	// * 遍历环节的辅助的栈
	// */
	// Stack processNumStack = new Stack();
	//
	// /**
	// * 构造函数，清空遍历环节的辅助的栈
	// */
	// ProcessNumStack()
	// {
	// processNumStack.clear();
	// }
	//
	// /**
	// * 向复制栈中压入环节的数量
	// *
	// * @param num：环节的数量
	// * @return 环节所在的列
	// */
	// int push(int num)
	// {
	// int processNumStackSize = processNumStack.size();
	// // 将数量压入栈
	// processNumStack.push(new Integer(num));
	//
	// // 判断栈顶的数量是否为0，如果为0，则出栈
	// while (!processNumStack.empty() &&
	// Integer.parseInt(processNumStack.peek().toString()) == 0)
	// {
	// processNumStack.pop();
	// }
	//
	// // 返回栈中的数量，也就是环节所在的列
	// return processNumStackSize;
	// }
	//
	// /**
	// * 从栈中取出环节的数量
	// *
	// * @return 环节的数量
	// */
	// int pop()
	// {
	// return Integer.parseInt(processNumStack.pop().toString());
	// }
	//
	// /**
	// * 减少栈顶数值
	// *
	// * @param num：减少的数量
	// * @throws Exception
	// */
	// void reduceNum(int num) throws Exception
	// {
	// // 获取栈顶的数值
	// int oldnum = Integer.parseInt(processNumStack.peek().toString());
	//
	// // 判断减少后的之是否小于0，是则报错，否则将减过的数值再次压入栈中
	// int newnum = oldnum - num;
	// if (newnum < 0)
	// {
	// newnum = oldnum;
	// throw new Exception("Not big enough.");
	// }
	// else
	// {
	// pop();
	// processNumStack.push(new Integer(newnum));
	// }
	// }
	// }
}
