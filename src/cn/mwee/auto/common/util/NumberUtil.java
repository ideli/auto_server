package cn.mwee.auto.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class NumberUtil {

	public static double getDoubleNumber(double num){
		String type = "0.0";
		DecimalFormat df = new DecimalFormat(type);
		String number = df.format(num);
		return Double.parseDouble(number);
	}
	
	public static double getDoubleNumber(double num,String type){
		DecimalFormat df = new DecimalFormat(type);
		String number = df.format(num);
		return Double.parseDouble(number);
	}
	
	public static String getStringNumber(double num,String type){
		DecimalFormat df = new DecimalFormat(type);
		String number = df.format(num);
		return number;
	}
	
	public static boolean isInteger(String value){
		try{
			Integer.parseInt(value);
		}catch(NumberFormatException e){
			return false;
		}
		return true;
	}
	
	public static String getStringBankNum(String num){
		String newNum = "";
		if(num!=null && !"".equals(num)){
			int l = num.length()/4;
			for(int i=0;i<l;i++){
				newNum = newNum + num.substring(i*4,4*(i+1))+" ";
			}
			newNum = newNum +num.substring(num.length()-num.length()%4,num.length());
		}
		return newNum;
	}
	
	public static int float2int(String floatStr){
		BigDecimal bc = new BigDecimal(floatStr).multiply(new BigDecimal(100));
		bc = bc.add(new BigDecimal(0.5));
		return bc.intValue();
	}
	
	public static int integer2int(Integer val)
	{
		return val == null ? 0 : val.intValue();
	}
	
}
