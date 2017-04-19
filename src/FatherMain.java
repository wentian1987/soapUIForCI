import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.MailVo;
import model.RequirementVo;
import utils.excel.FatherExcel;

public class FatherMain {

	private String excelPath = "resource/resource.xlsx";
	private XSSFWorkbook inputWorkbook = null;
	private List<RequirementVo> requirementList = new ArrayList<RequirementVo>();
	private List<MailVo> mailList = new ArrayList<MailVo>();

	final Logger logger = LoggerFactory.getLogger(this.getClass());
	public FatherMain() {
		super();
	}

	@Before
	public void initExcel() {
		try {
			File inputFile = new File(excelPath);
			OPCPackage pkg = OPCPackage.open(inputFile);
			inputWorkbook = new XSSFWorkbook(pkg);
			pkg.close();

			operateExcelData();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
			logger.error("wentian test InvalidFormatException");
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("wentian test IOException");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("wentian test Exception");
		}
	}

	private void operateExcelData() throws Exception {
		Sheet requirementSheet = inputWorkbook.getSheet("需求列表");
		requirementList = convertRequirement(requirementSheet);
		Sheet mailSheet = inputWorkbook.getSheet("邮件列表");
		mailList = convertMail(mailSheet);
	}

	private List<MailVo> convertMail(Sheet mailSheet) throws Exception {
		int endIndex = mailSheet.getLastRowNum();
		List<MailVo> outputList = new ArrayList<MailVo>();
		FatherExcel fatherExcel = new FatherExcel();
		for (int rowIndex = 1; rowIndex <= endIndex; rowIndex++) {
			Row mailRow = mailSheet.getRow(rowIndex);
			String mail = fatherExcel.getValueByColumenName(mailRow, "邮件地址");
			String position = fatherExcel.getValueByColumenName(mailRow, " 岗位");
			outputList.add(new MailVo(mail, position));
		}
		return outputList;
	}

	private List<RequirementVo> convertRequirement(Sheet requirementSheet) throws Exception {
		int endIndex = requirementSheet.getLastRowNum();
		List<RequirementVo> outputList = new ArrayList<RequirementVo>();
		FatherExcel fatherExcel = new FatherExcel();
		for (int rowIndex = 1; rowIndex <= endIndex; rowIndex++) {
			Row requirementRow = requirementSheet.getRow(rowIndex);
			String requirementNum = fatherExcel.getValueByColumenName(requirementRow, "需求号");
			String requirementName = fatherExcel.getValueByColumenName(requirementRow, "需求名称");
			String requirementDesc = fatherExcel.getValueByColumenName(requirementRow, "需求描述");
			String onlineStatus = fatherExcel.getValueByColumenName(requirementRow, "上线状态");
			String mails = fatherExcel.getValueByColumenName(requirementRow, "需求负责人邮件（多个数值时分号隔开）");
			String testcaseStatue = fatherExcel.getValueByColumenName(requirementRow, "测试用例编写情况");
			outputList.add(new RequirementVo(requirementNum, requirementName, requirementDesc, onlineStatus,
					testcaseStatue, mails));
		}
		return outputList;
	}
}