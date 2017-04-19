package utils.excel;

import java.math.BigDecimal;

public class DataTools {
	
	public static double parseDouble(String s) {
		if(s == null || "".equals(s.trim()))
			return 0;
		else
			return Double.parseDouble(s);
	}
	public static double myround(double num, int cutPoint) {
		BigDecimal b = new BigDecimal(num);
		num = b.setScale(cutPoint, BigDecimal.ROUND_HALF_UP).doubleValue();
		return num;
	}
	public static double myround(String cellValue, int cutPoint) {
		return myround(Double.parseDouble(cellValue), cutPoint);
	}
	/**
	 * 将3000065.0 转换为3000065
	 * @param cellValue
	 * @return
	 */
	public static String deleteDou(String cellValue) {
		if (cellValue.contains(".")) {
			cellValue =  cellValue.replace(cellValue.substring(cellValue.indexOf("."), cellValue.length()), "");
		}
		return cellValue;
	}

	public static Double tranformScienToString(String data) {
		BigDecimal bd = new BigDecimal(data);
		// System.out.println(bd.toPlainString());

		return Double.parseDouble(bd + "");
	}

	public static String cutToPointTwo(String data) {
		java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
		return df.format(data);
	}

	public static Double cutToPointTwo(Double data) {
		java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
		return Double.parseDouble(df.format(data));
	}

	public static String cutToPointFour(String data) {
		java.text.DecimalFormat df = new java.text.DecimalFormat("#.0000");
		return df.format(data);
	}

	public static String cutToPointFour(Double data) {
		java.text.DecimalFormat df = new java.text.DecimalFormat("#.0000");
		return df.format(data);
	}

	public static String cutToPointSix(String data) {
		java.text.DecimalFormat df = new java.text.DecimalFormat("#.000000");
		return df.format(data);
	}

	public static Double tranform(String up, String down) {
		if (down == null) {
			return null;
		}
		down = down.replace("1/", "");
		// System.out.println("down=" + down);
		BigDecimal b1 = new BigDecimal(up);
		BigDecimal b2 = new BigDecimal(down);
		BigDecimal b3 = b1.divide(b2, 6, BigDecimal.ROUND_HALF_UP);
		return Double.parseDouble(b3 + "");
	}

	public static String convertNumToChinese(String num) {
		String numChina = null;
		switch (num) {
		case "1":
			numChina = "一层";
			break;

		case "2":
			numChina = "二层";
			break;

		case "3":
			numChina = "三层";
			break;

		case "4":
			numChina = "四层";
			break;

		case "5":
			numChina = "五层";
			break;

		case "6":
			numChina = "六层";
			break;

		case "7":
			numChina = "七层";
			break;

		case "8":
			numChina = "八层";
			break;

		case "9":
			numChina = "九层";
			break;

		case "10":
			numChina = "十层";
			break;

		case "11":
			numChina = "十一层";
			break;

		case "12":
			numChina = "十二层";
			break;

		case "13":
			numChina = "十三层";
			break;

		case "14":
			numChina = "十四层";
			break;

		case "15":
			numChina = "十五层";
			break;

		case "16":
			numChina = "十六层";
			break;

		case "17":
			numChina = "十七层";
			break;

		case "18":
			numChina = "八层";
			break;

		case "19":
			numChina = "九层";
			break;

		case "20":
			numChina = "十层";
			break;

		case "21":
			numChina = "二十一层";
			break;

		case "22":
			numChina = "二十二层";
			break;

		case "23":
			numChina = "二十三层";
			break;

		case "24":
			numChina = "二十四层";
			break;

		case "25":
			numChina = "二十五层";
			break;

		case "26":
			numChina = "二十六层";
			break;

		case "27":
			numChina = "二十七层";
			break;

		case "28":
			numChina = "二十八层";
			break;

		case "29":
			numChina = "二十九层";
			break;

		case "30":
			numChina = "三十层";
			break;

		default:
			break;
		}
		return numChina;
	}

}
