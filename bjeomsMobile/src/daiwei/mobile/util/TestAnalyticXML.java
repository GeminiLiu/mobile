package daiwei.mobile.util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import daiwei.mobile.animal.Item;

/**
 * 解析派发树
 * @author Administrator
 *
 */
public class TestAnalyticXML{
//	public static void main(String[] args) {
//		File file = new File("D://NewFile.xml");
//		SAXReader reader = new SAXReader();
//		try {
//			Document doc = reader.read(file);
//			Element root = doc.getRootElement();
//			Item it = new Item(); 
//			Analytic(it,root);
//			//----
//			List<Item> childList = it.getChild();
//			Item its = childList.get(0);
//			System.out.println("0: 文本:"+its.getText()+"--ID:"+its.getId());
//			//----
//			printStr(it);
//			
//		} catch (DocumentException e) {
//			e.printStackTrace();
//		}
//	}
	public static Item domParser(String in){
		SAXReader reader = new SAXReader();
		try {
//			Document doc = reader.read(in);//读流
			Document doc=DocumentHelper.parseText(in);
			Element root = doc.getRootElement();
			Item it = new Item(); 
			AnalyticOld(it,root);
			return it;
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 记录整棵派发树的值
	 * @param allItem
	 * @param parentItem
	 * @param in
	 * @return
	 */
	public static Item getAllItem(Item allItem,Item parentItem,String in){
		SAXReader reader = new SAXReader();
		try {
			Document doc=DocumentHelper.parseText(in);
			Element root = doc.getRootElement();
			Item curItem = null;
			Analytic(allItem,curItem,parentItem,root);
			return allItem;
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static void printStr (Item item){
		 System.out.print("请输入一个整数：");
		 BufferedReader strin=new BufferedReader(new InputStreamReader(System.in));
		 try {
			Integer inStr= Integer.parseInt(strin.readLine());
			if(inStr < 10)
				item = getChild(item,inStr);
			else
				item = getParent(item,inStr);
		} catch (IOException e) {
			e.printStackTrace();
		}
		printStr (item);
	}
	
	
	public static Item getChild(Item item,int str){
		List<Item> childList = item.getChild();
		if(childList.get(str).getChild() == null){
			System.out.println("已没有子节点");
			return item;
		}
		
		List<Item> items = childList.get(str).getChild();
		for(int i=0;i<items.size();i++){
			Item it = items.get(i);
			String s = it.getNocheckbox()?"-------这一级不可以选择":"";
			System.out.println(i+": 文本:"+it.getText()+"--ID:"+it.getId()+s);
		}
		return childList.get(str);
	}
	/*
	public static Item getParent(Item item,int str){
		Item ParentItem = item.getParentItem();
		if(ParentItem == null){
			System.out.println("已是第一级");
			return null;
		}
		
		List<Item> items = ParentItem.getChild();
		for(int i=0;i<items.size();i++){
			Item it = items.get(i);
			String s = it.getNocheckbox()?"-------这一级不可以选择":"";
			System.out.println(i+": 文本:"+it.getText()+"--ID:"+it.getId()+s);
		}
		return ParentItem;
	}*/
	public static Item getParent(Item item,int str){
		Item ParentItem = item.getParentItem();
		if(ParentItem == null){
			System.out.println("已是第一级");
			return null;
		}
//		List<Item> items = parent.getChild();
//		for(int i=0;i<items.size();i++){
//			Item it = items.get(i);
//			String s = it.getNocheckbox()?"-------这一级不可以选择":"";
//			System.out.println(i+": 文本:"+it.getText()+"--ID:"+it.getId()+s);
//		}
		return ParentItem;
	}
	public static void searchPrentItem(Item allItem,Item curItem,List childList){
		List<Item> subList = allItem.getChild();
		String id = curItem.getId();
		if(subList != null && subList.size() >0){
			for(Item item:subList){
				String tempId = item.getId();
				if(id.equals(tempId)){
//					item.setParentItem(curItem);
					item.setChild(childList);
					break;
				}else{
					searchPrentItem(item,curItem,childList);
				}
			}
		}
	}
	public static Item Analytic(Item allItem,Item curItem,Item parentItem,Element element){
		Iterator<Element> its = element.elements("item").iterator();
		List<Item> childList = new ArrayList<Item>();
		
		while (its.hasNext()) {
			Element rowInfo = its.next();
			String checkBox = rowInfo.attributeValue("nocheckbox");
			Item it = new Item();
			boolean b = checkBox.equals("1")?true:false;
			it.setNocheckbox(b);
			it.setParentItem(parentItem);
			it.setType(allItem.getType());//传模板上的参数
			Iterator<Element> rowIts = rowInfo.elements("userdata").iterator();
			
			while (rowIts.hasNext()) {
				Element rowit = rowIts.next();
				String name = rowit.attributeValue("name");
				if(name.equals("id"))
					it.setId(rowit.getText());
				else if(name.equals("type"))
					it.setType(rowit.getText());
				else if(name.equals("text"))
					it.setText(rowit.getText());
				else if(name.equals("loginname"))
					it.setLoginname(rowit.getText());
			}
			childList.add(it);
		}
		if(curItem == null){
			curItem = new Item();
		}
		curItem.setChild(childList);//当前节点的子节点
		if(parentItem == null){
			allItem.setParentItem(curItem);//第一层的父节点都为null
			allItem.setChild(childList);//子节点初始化空列表
		}else{
			searchPrentItem(allItem,curItem,childList);//查找父节点
		}
//		parentItem = curItem;
		return parentItem;
	}
	
	public static void AnalyticOld(Item item,Element element){
		Iterator<Element> its = element.elements("item").iterator();
		List<Item> childList = new ArrayList<Item>();
		while (its.hasNext()) {
			Element rowInfo = its.next();
			String checkBox = rowInfo.attributeValue("nocheckbox");
			Item it = new Item();
			it.setParentItem(item);
			boolean b = checkBox.equals("1")?true:false;
			it.setNocheckbox(b);
			Iterator<Element> rowIts = rowInfo.elements("userdata").iterator();
			
			while (rowIts.hasNext()) {
				Element rowit = rowIts.next();
				String name = rowit.attributeValue("name");
				if(name.equals("id"))
					it.setId(rowit.getText());
				else if(name.equals("type"))
					it.setType(rowit.getText());
				else if(name.equals("text"))
					it.setText(rowit.getText());
				else if(name.equals("loginname"))
					it.setLoginname(rowit.getText());
				//System.out.println(rowit.getText());
			}
			
			Iterator<Element> clilditem = rowInfo.elements("item").iterator();
			if(clilditem.hasNext())
				AnalyticOld(it,rowInfo);
			childList.add(it);
		}
		item.setChild(childList);
	}
	
	
}
