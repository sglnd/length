package com.stand.lnd;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class StandTest {

	private BufferedReader bufferedreader;

	// 复数对应单数形式
	static HashMap<String, String> mapWordsToWord = new HashMap<String, String>();

	// 某单位对应转换多少m
	HashMap<String, String> map = new HashMap<String, String>();

	// 需要计算的表达式
	List<String> list = new ArrayList<String>();


	public StandTest() {
		mapWordsToWord.put("miles", "mile");
		mapWordsToWord.put("yards", "yard");
		mapWordsToWord.put("inches", "inch");
		mapWordsToWord.put("feet", "foot");
		mapWordsToWord.put("faths", "fath");
		mapWordsToWord.put("furlongs", "furlong");
	}

	// 计算只有-的表示式的值
	public float getfValue(String str) {
		
		if(str.contains("-")){
			String ayyay[] = str.trim().split("-");
	
			float tmpfl = getValue(ayyay[0]);
	
			for (int i = 1; i < ayyay.length; i++) {
				tmpfl = tmpfl - getValue(ayyay[i]);
			}
			return tmpfl;
		}else{
			return getValue(str);
		}

	}
	
	// 将表达式中的复数转换为单数形式，适合计算
	public String formatListCont(String listCont) {

		Set<String> setmap = mapWordsToWord.keySet();
		for (String s : setmap) {
			if (listCont.contains(s)) {
				listCont = listCont.replace(s, mapWordsToWord.get(s));
			}
		}
		return listCont;
	}

	// 换算成m的值
	public float getValue(String str) {

		String array[] = str.trim().split(" ");
		String valtmp = map.get(array[1]);

		Float flval1 = Float.parseFloat(array[0]);
		Float flval2 = Float.parseFloat(valtmp);

		return flval1 * flval2;

	}

	// 计算表达式
	public float calcuExpr(String tmpExp) {
		float result = 0;
		if (tmpExp.contains("+")) {
			String ayyay[] = tmpExp.split("\\+");
			for (int i = 0; i < ayyay.length; i++) {
				if (ayyay[i].contains("\\-")) {
					result = result + getfValue(ayyay[i]);
				} else {
					result += getValue(tmpExp);
				}
			}
		} else {
			result = getValue(tmpExp);
		}

		return result;
	}
	
	//遍历list，求得所有表达式的值

	public void calAllExp(){

		NumberFormat numFor = NumberFormat.getNumberInstance();
		numFor.setMaximumFractionDigits(2);
		
		for(int i=0;i<list.size();i++){
			System.out.println(numFor.format(calcuExpr(formatListCont(list.get(i))))+" m");
		}
	}
	
	// 分析输入文件
	public void relaseFile() {
		String filePath = System.getProperty("user.dir");
		String tmpLine = "";
		try {
			FileReader file = new FileReader(filePath + "\\input.txt");
			bufferedreader = new BufferedReader(file);
			while (tmpLine != null) {
				tmpLine = bufferedreader.readLine();
				if (tmpLine != null && tmpLine.contains("=")) {
					String tmpAyyay[] = tmpLine.split("=");
					String tmpAyyay2[] = tmpAyyay[0].trim().split(" ");
					String tmpAyyay3[] = tmpAyyay[1].trim().split(" ");
					map.put(tmpAyyay2[1], tmpAyyay3[0]);
				} else if (tmpLine != null && !tmpLine.trim().equals("")) {
					list.add(tmpLine);
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("请将输入文件input.txt放置在" + filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		StandTest st = new StandTest();
		System.out.println("sglnd@126.com\n");
		st.relaseFile();
		st.calAllExp();

	}
}
