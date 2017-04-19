import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCaseRunner;
import com.eviware.soapui.model.testsuite.TestRunner.Status;
import com.eviware.soapui.model.support.PropertiesMap;
import com.eviware.soapui.model.testsuite.TestCase;
import com.eviware.soapui.model.testsuite.TestProperty;
import com.eviware.soapui.model.testsuite.TestSuite;
import com.eviware.soapui.tools.SoapUITestCaseRunner;

import model.TestCaseVo;
import wentian.data.DB;

public class Main {

	final Logger logger = LoggerFactory.getLogger(this.getClass());
	private WsdlProject project;
	private static String wsdlPath = "resource/Sample-SOAP-Project-soapui-project.xml";
	private List<TestCaseVo> testcaseList = new ArrayList<TestCaseVo>();

	@BeforeClass
	public static void initSoapuiLogs() {
		System.setProperty("soapui.logroot", "target/test-output/soapui/logs/");
	}

	@Before
	public void initTestSuite() throws Exception {
		project = new WsdlProject(wsdlPath);
		project.setPropertyValue("Env", "test");
	}

	@Test
	public void testSoapUIRunner() {
		try {
			List<TestSuite> suiteList = project.getTestSuiteList();
			String reportStr = null;
			WsdlTestCaseRunner runner;
			List<String> sqls = new ArrayList<String>();
			String sql = "insert into SoapUITestrun(operate_Date,operate_User ) "
					+ " values(sysdate, 'wentian_service')";
			logger.info(sql);
			Long runid = DB.updateSqlGetId(sql);
			boolean testRunStatus = true;
			for (int i = 0; i < suiteList.size(); i++) {
				String suiteName = suiteList.get(i).getName();
				logger.info("\nTest Suite: " + suiteName);
				sql = "insert into SoapUITestsuite(name, operate_Date,operate_User,testrun_id ) " + " values('"
						+ StringEscapeUtils.escapeSql(suiteName) + "',sysdate, 'wentian_service'," + runid + ")";
				logger.info(sql);
				long suiteid = DB.updateSqlGetId(sql);
				List<TestCase> caseList = suiteList.get(i).getTestCaseList();
				boolean testSuiteStatus = true;
				for (int j = 0; j < caseList.size(); j++) {
					TestCase testcase = caseList.get(j);
					runner = project.getTestSuiteByName(suiteName).getTestCaseByName(testcase.getName())
							.run(new PropertiesMap(), false);

					String requirenmentNum = validateTasecaseProperties(testcase.getProperties());

					TestCaseVo testcaseVo = new TestCaseVo(testcase.getName(), runner.getStatus(), runner.getReason(),
							runner.getTimeTaken(), "wentian test", suiteid);
					testcaseList.add(testcaseVo);
					if(runner.getStatus() != Status.FINISHED){
						testSuiteStatus = false;
						testRunStatus = false;
					}
					logger.info("自己的时间：" + runner.getTimeTaken());
					logger.info(testcaseVo.getInsertSql());
					sqls.add(testcaseVo.getInsertSql());
					if (!StringUtils.isEmpty(requirenmentNum)) {
						logger.info("需求号 == " + requirenmentNum);
					}
				}

				String tempSql = "update SoapUITestsuite s set s.status_State='" + (testSuiteStatus?"SUCCESS":"FAILED")
						+ "' where s.id = '" + suiteid
						+ "'";
				sqls.add(tempSql);
			}

			String tempsqlRun = "update SoapUITestrun s set s.status_State='" + (testRunStatus?"SUCCESS":"FAILED")
					+ "' where s.id = '" + runid
					+ "'";
			sqls.add(tempsqlRun);

			DB.executeSqls(sqls);
			logger.info(reportStr);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("testSoapUIRunner error");
		}
	}

	@Test
	public void loadTestData() {
		for(int i = 0 ; i < 15; i ++){
			this.testSoapUIRunner();
		}
	}

	@Test
	public void testSoapUIRunnerWsdl() {
		try {
			SoapUITestCaseRunner soapUITestCaseRunner = new SoapUITestCaseRunner();
			soapUITestCaseRunner.setProjectFile(wsdlPath);
			soapUITestCaseRunner.setPrintReport(true);
			soapUITestCaseRunner.setJUnitReport(true);
			soapUITestCaseRunner.run();
		} catch (Exception e) {
			System.out.println("wentian output");
			fail("wentian test Not yet implemented");
		}
	}

	/**
	 * 校验testcase的properties的值是否正确。
	 * 
	 * @param properties
	 * @return
	 */
	private String validateTasecaseProperties(Map<String, TestProperty> properties) {
		if (properties != null && properties.size() > 0 && properties.containsKey("num")) {
			return properties.get("num").getValue();
		}
		return null;
	}

	public static void main() {
	}

}
