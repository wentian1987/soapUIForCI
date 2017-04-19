package utils.excel;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class FatherExcel {

	/**
	 * 通过列名获取值。默认headerIndex为0；
	 * 
	 * @param inputRow
	 * @param columnName
	 * @throws Exception
	 */
	public String getValueByColumenName(Row inputRow, String columnName) throws Exception {
		return getValueByColumenName( inputRow, 0,  columnName);
	}

	/**
	 * 通过列名获取值,并将值转换为正整数。默认headerIndex为0；
	 * 
	 * @param inputRow
	 * @param columnName
	 * @throws Exception
	 */
	public Integer getPostiveIntegerValueByColumenName(Row inputRow, String columnName) throws Exception {
		return getPostiveIntegerValueByColumenName( inputRow, 0, columnName);
	}

	/**
	 * 通过列名获取值。
	 * 
	 * @param inputRow
	 * @param columnName
	 * @throws Exception
	 */
	public String getValueByColumenName(Row inputRow,int headerIndex, String columnName) throws Exception {
		return ExcelTools.getCellValue(
				inputRow.getCell(ExcelTools.getColumnIndexFromSheetByHeadIndex(inputRow.getSheet(), headerIndex, columnName)));
	}

	public Double getPostiveDoubleValueByColumenName(Row inputRow, String columnName) throws Exception {
		return getPostiveDoubleValueByColumenName( inputRow, 0, columnName);
	}

	/**
	 * 通过列名获取值,并将值转换为正Double。
	 * 
	 * @param inputRow
	 * @param columnName
	 * @throws Exception
	 */
	public Double getPostiveDoubleValueByColumenName(Row inputRow,int headerIndex, String columnName) throws Exception {
		return Math.abs(Double.parseDouble(getValueByColumenName(inputRow,headerIndex, columnName)));
	}
	
	/**
	 * 通过列名获取值,并将值转换为正整数。
	 * 
	 * @param inputRow
	 * @param columnName
	 * @throws Exception
	 */
	public Integer getPostiveIntegerValueByColumenName(Row inputRow,int headerIndex, String columnName) throws Exception {
		return Math.abs((int) DataTools.myround(getValueByColumenName(inputRow,headerIndex, columnName), 0));
	}
	
	/**
	 * 在传入的inputSheet中的columnIndex列中找findKey，并返回找到的行。
	 * @param inputSheet
	 * @param columnIndex
	 * @param findKey
	 * @return
	 * @throws Exception 
	 */
	public Row findRowFromColumn(Sheet inputSheet, int columnIndex, String findKey) throws Exception{
		int rowSize = inputSheet.getPhysicalNumberOfRows();
		for(int index = 1; index < rowSize; index ++){
			Row inputRow = inputSheet.getRow(index);
			String cellValue = ExcelTools.getCellValue(inputRow.getCell(columnIndex));
			if(StringUtils.isNotEmpty(cellValue) && cellValue.equals(findKey)){
				return inputRow;
			}
		}
		
		throw new Exception("没有在sheet：" + inputSheet.getSheetName() + "中的列："
		+ columnIndex + "找到想要的值：" + findKey + "，请确定导入的文件是否正确。");
	}
}
