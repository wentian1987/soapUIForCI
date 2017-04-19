package utils.excel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;

public class ExcelTools {

	/**
	 * 
	 * @param inputSheet
	 *            需要查找的sheet
	 * @param columnIndex
	 *            查找的列。
	 * @param findKey
	 *            需要查找的value
	 * @param findPattern
	 * @return
	 * @throws Exception
	 */
	public static int getIndexFromSheetByIndex(Sheet inputSheet, int columnIndex, String findKey, int findPattern)
			throws Exception {
		Double findKeyValueD = Double.MIN_VALUE;
		Double cellValueD = Double.MIN_VALUE;
		int length = inputSheet.getPhysicalNumberOfRows();
		for (int index = 2; index < length; index++) {
			Row row = inputSheet.getRow(index);
			String cellValue = ExcelTools.getCellValue(row.getCell(columnIndex));
			if (findPattern == 1) {
				String AxisDirValue = ExcelTools.getCellValue(row.getCell(1));
				if (StringUtils.isEmpty(AxisDirValue) || !"Z".equals(AxisDirValue)) {
					continue;
				}

				cellValueD = Double.parseDouble(cellValue);
				findKeyValueD = Double.parseDouble(findKey);
				if (findKeyValueD <= cellValueD) {
					return index;
				}
			}
			if (findKey.contains(".")) {
				findKey = DataTools.deleteDou(findKey);
			}
			if (cellValue.contains(".")) {
				cellValue = DataTools.deleteDou(cellValue);
			}
			if (!StringUtils.isEmpty(cellValue) && cellValue.equals(findKey)) {
				return index;
			}
		}
		throw new Exception("没有在" + inputSheet.getSheetName() + "找到对应的数据" + findKey);
	}

	public static List<Integer> getIndexsFromSheetByIndex(Sheet inputSheet, int columnIndex, String findKey)
			throws Exception {
		List<Integer> indexs = new ArrayList<Integer>();
		System.out.println("inputSheet.getPhysicalNumberOfRows() ==" + inputSheet.getPhysicalNumberOfRows());
		for (int index = 2; index < inputSheet.getPhysicalNumberOfRows(); index++) {
			Row row = inputSheet.getRow(index);
			String cellValue = ExcelTools.getCellValue(row.getCell(columnIndex));
			if (!StringUtils.isEmpty(cellValue) && cellValue.equals(findKey)) {
				indexs.add(index);
			}
		}
		return indexs;
	}

	public static List<Integer> getIndexsFromSheetByColumnNames(Sheet inputSheet, String[] headerColumnNames, String[] findKeys) throws Exception {
		return getIndexsFromSheetByColumnNames(inputSheet, 0, headerColumnNames, findKeys);
	}

	public static Row getRowFromSheetByColumnNames(Sheet inputSheet,int inputHeaderIndex, 
			String[] headerColumnNames, String[] findKeys) throws Exception{
		List<Integer> list = getIndexsFromSheetByColumnNames( inputSheet, inputHeaderIndex, 
				headerColumnNames, findKeys);
		if(list.size() != 1){
			return null;
		}
		
		return inputSheet.getRow(list.get(0));
	}
	public static List<Integer> getIndexsFromSheetByColumnNames(Sheet inputSheet,int inputHeaderIndex, 
			String[] headerColumnNames, String[] findKeys)
			throws Exception {
		List<Integer> indexs = new ArrayList<Integer>();
		Map<Integer,String> inputColumnMap = new HashMap<Integer, String>();
		Map<Integer,String> findKeyMap = new HashMap<Integer, String>();
		Row row = inputSheet.getRow(inputHeaderIndex);
		int tempIndex = 0;
		for(String columnName:headerColumnNames){
			int inputColumnLength = row.getPhysicalNumberOfCells();
			for(int index = 0; index < inputColumnLength; index ++){
				String cellValue = ExcelTools.getCellValue(row.getCell(index));
				if(!StringUtils.isEmpty(cellValue) && cellValue.equals(columnName)){
					inputColumnMap.put(index, columnName);
					findKeyMap.put(index, findKeys[tempIndex ++]);
					break;
				}
			}
		}
		int endIndex =  inputSheet.getPhysicalNumberOfRows();
		for (int index = 1; index < endIndex; index++) {
			row = inputSheet.getRow(index);
			Boolean isMatch = true;
			if(row == null){
				break;
			}
			for(Integer inputColumnIndex: inputColumnMap.keySet()){
				String cellValue = ExcelTools.getCellValue(row.getCell(inputColumnIndex));
				String tempKey = findKeyMap.get(inputColumnIndex);
				if (StringUtils.isEmpty(cellValue)) {
					isMatch = false;
					break;
				}
				if(tempKey.endsWith("%")){
					if( !cellValue.startsWith(tempKey.replaceAll("%", ""))){
						isMatch = false;
						break;
					}
				}else if(!cellValue.equals(tempKey)){
					isMatch = false;
					break;
				}
			}
			if (isMatch) {
				indexs.add(index);
			}
		}
		return indexs;
	}

	/**
	 * 检查表头是否符合要求。
	 * 
	 * @param row
	 * @throws Exception
	 */
	public static boolean validateHeader(Row row, String headerString) throws Exception {
		String[] headerNames = headerString.split("	");

		for (int index = 0; index < headerNames.length; index++) {
			Cell cell = row.getCell(index);
			if (!headerNames[index].equals(getCellValue(cell))) {
				throw new Exception(row.getSheet().getSheetName() + "表头不符合预设的格式：" + headerString + "\n" + (index+1)
						+ "列。期望：" + headerNames[index] + ",结果是：" + getCellValue(cell));
					// return false;
			}
		}
		return true;
	}

	public static String getCellValue(Cell cell) {
		if(cell == null){
			return "0";
		}
		String returnStr = "0"; 
		// System.out.println("celltype=" + cell.getCellType());
		if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
			return String.valueOf(cell.getBooleanCellValue());
		} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			returnStr = String.valueOf(cell.getNumericCellValue()).trim();
			if(returnStr == null || "".equals(returnStr)){
				returnStr = "0";
			}
		} else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			return String.valueOf(cell.getStringCellValue()).trim();
		} else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {

			return String.valueOf(cell.getNumericCellValue());
		} else {
			return String.valueOf(cell.getStringCellValue()).trim();

		}
		
		return returnStr;
	}

	public static String getSheetFromInput(List<String> sheetNames, String[] keys) throws Exception {
		String sheetName = null;
		for (String temp : sheetNames) {
			boolean find = true;
			for (String key : keys) {
				if (!temp.contains(key)) {
					find = false;
					break;
				}
			}
			if (find) {
				sheetName = temp;
			}
		}
		if (sheetName == null) {
			sheetName = "";
			for(String key : keys){
				sheetName += key + " "; 
			}
			throw new Exception("发生错误了，没有在导入文件中找到相应的sheet，sheet名字是:" + sheetName
					+ "请检查导入的文件是否正确。");
		}
		return sheetName;
	}

	public static void initAutosize(Sheet sheet) {
		for (int index = 0; index < 20; index++) {
			sheet.autoSizeColumn(index);
		}
	}

	public static boolean testNullRow(Row fromRow) {
		if ("力输入区".equals(fromRow.getSheet().getSheetName())) {
			String cell5 = ExcelTools.getCellValue(fromRow.getCell(8));
			String cell14 = ExcelTools.getCellValue(fromRow.getCell(9));
			if ("0.0".equals(cell14) && "0.0".equals(cell5)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 没有指定的rowIndex行则创建，如果存在则获取。
	 * 
	 * @param outputSheet1
	 * @param rowIndex
	 * @return
	 */
	public static Row getRowFromSheet(Sheet outputSheet1, int rowIndex) {
		Row outputRow = null;
		if (outputSheet1.getPhysicalNumberOfRows() <= rowIndex ) {
			outputRow = outputSheet1.createRow(rowIndex);
		} else {
			outputRow = outputSheet1.getRow(rowIndex);
		}
		return outputRow;
	}

	public static int getColumnIndexFromSheetByHeadIndex(Sheet inputSheet1, int rowIndex, String headerKey)
			throws Exception {
		Row inputRow = inputSheet1.getRow(rowIndex);
		for (int columnIndex = 0; columnIndex < inputRow.getPhysicalNumberOfCells(); columnIndex++) {
			Cell cell = inputRow.getCell(columnIndex);
			if (headerKey.equals(ExcelTools.getCellValue(cell))) {
				return columnIndex;
			}
		}
		throw new Exception("在" + inputSheet1.getSheetName() + "中寻找标题" + headerKey + "时发生错误。");
	}

	public static void setRengeStyle(Sheet outputSheet, CellRangeAddress region) {
		// Set the border and border colors.
		final short borderMediumDashed = CellStyle.BORDER_MEDIUM;
		RegionUtil.setBorderBottom(borderMediumDashed, region, outputSheet, outputSheet.getWorkbook());
		RegionUtil.setBorderTop(borderMediumDashed, region, outputSheet, outputSheet.getWorkbook());
		RegionUtil.setBorderLeft(borderMediumDashed, region, outputSheet, outputSheet.getWorkbook());
		RegionUtil.setBorderRight(borderMediumDashed, region, outputSheet, outputSheet.getWorkbook());
	}

	public static Boolean validateHeaderContainColumn(Row inputRow, String[] shouldContainColumnName) throws Exception {
		if(shouldContainColumnName == null || shouldContainColumnName.length == 0){
			return true;
		}
		if(inputRow == null){
			throw new Exception("sheet 中没有内容。");
		}
		for (int index = 0; index < shouldContainColumnName.length; index++) {
			String headerString = shouldContainColumnName[index];
			boolean isFind = false;
			for(int inputIndex = 0; inputIndex < inputRow.getPhysicalNumberOfCells(); inputIndex ++){
				Cell cell = inputRow.getCell(inputIndex);
				if(headerString.equals(getCellValue(cell))){
					isFind = true;
					break;
				}
			}
			if (!isFind) {
				throw new Exception(inputRow.getSheet().getSheetName() + "表头不包含预定义的列：" + headerString + "，请检查导入的文件是否符合要求。");
			}
		}
		return true;
	}

}
