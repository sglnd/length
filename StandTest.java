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

	// ������Ӧ������ʽ
	static HashMap<String, String> mapWordsToWord = new HashMap<String, String>();

	// ĳ��λ��Ӧת������m
	HashMap<String, String> map = new HashMap<String, String>();

	// ��Ҫ����ı��ʽ
	List<String> list = new ArrayList<String>();


	public StandTest() {
		mapWordsToWord.put("miles", "mile");
		mapWordsToWord.put("yards", "yard");
		mapWordsToWord.put("inches", "inch");
		mapWordsToWord.put("feet", "foot");
		mapWordsToWord.put("faths", "fath");
		mapWordsToWord.put("furlongs", "furlong");
	}

	// ����ֻ��-�ı�ʾʽ��ֵ
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
	
	// �����ʽ�еĸ���ת��Ϊ������ʽ���ʺϼ���
	public String formatListCont(String listCont) {

		Set<String> setmap = mapWordsToWord.keySet();
		for (String s : setmap) {
			if (listCont.contains(s)) {
				listCont = listCont.replace(s, mapWordsToWord.get(s));
			}
		}
		return listCont;
	}

	// �����m��ֵ
	public float getValue(String str) {

		String array[] = str.trim().split(" ");
		String valtmp = map.get(array[1]);

		Float flval1 = Float.parseFloat(array[0]);
		Float flval2 = Float.parseFloat(valtmp);

		return flval1 * flval2;

	}

	// ������ʽ
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
	
	//����list��������б��ʽ��ֵ

	public void calAllExp(){

		NumberFormat numFor = NumberFormat.getNumberInstance();
		numFor.setMaximumFractionDigits(2);
		
		for(int i=0;i<list.size();i++){
			System.out.println(numFor.format(calcuExpr(formatListCont(list.get(i))))+" m");
		}
	}
	
	// ���������ļ�
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
			System.out.println("�뽫�����ļ�input.txt������" + filePath);
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
