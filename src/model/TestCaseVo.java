package model;

import org.apache.commons.lang.StringEscapeUtils;

import com.eviware.soapui.model.testsuite.TestRunner.Status;

//import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCase;
//import com.eviware.soapui.model.testsuite.TestCase;
public class TestCaseVo {
	String name;
	com.eviware.soapui.model.testsuite.TestRunner.Status status;
	String statusState;
	String Reason;
	Long duration;
	String operateUser;
	String operateDate;
	Long suiteId;
	
	public TestCaseVo(String name, com.eviware.soapui.model.testsuite.TestRunner.Status status,
			String reason, Long duration, String operateUser,Long suiteId) {
		super();
		this.name = name;
		this.status = status;
		this.Reason = reason;
		this.duration = duration;
		this.operateUser = operateUser;
		this.suiteId = suiteId;
	}
	public String toString(){
		return "\n\tTestCase: " + getName() + "\t执行状态: " + getStatusState()
		+ "\tReason: " + getReason() + "\t运行时间（毫秒）: " + duration;
	}
	public String getInsertSql(){
		String sql = "insert into SoapUITestCase (name, status_State, Reason, duration,"
				+ "operate_Date,operate_User,testsuite_id ) values( '"
		+ StringEscapeUtils.escapeSql(getName()) + "', '" + StringEscapeUtils.escapeSql(getStatusState())
		+ "','" + ((getReason()==null)?"":getReason()) + "','" + duration + 
		"',sysdate, 'wentian_service'," + getSuiteId() 
						+")";
		return sql;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getReason() {
		return Reason;
	}
	public void setReason(String reason) {
		Reason = reason;
	}
	public com.eviware.soapui.model.testsuite.TestRunner.Status getStatus() {
		return status;
	}
	public void setStatus(com.eviware.soapui.model.testsuite.TestRunner.Status status) {
		this.status = status;
	}
	public Long getDuration() {
		return duration;
	}
	public void setDuration(Long duration) {
		this.duration = duration;
	}
	
	public String getOperateUser() {
		return operateUser;
	}
	public void setOperateUser(String operateUser) {
		this.operateUser = operateUser;
	}
	public String getOperateDate() {
		return operateDate;
	}
	public void setOperateDate(String operateDate) {
		this.operateDate = operateDate;
	}
	public Long getSuiteId() {
		return suiteId;
	}
	public void setSuiteId(Long suiteId) {
		this.suiteId = suiteId;
	}
	public void setStatusState(String statusState) {
		this.statusState = statusState;
	}
	public String getStatusState() {
		String statusState = null;
		if(status == Status.FAILED){
			statusState = "FAILED";
		}
		if(status == Status.CANCELED){
			statusState = "CANCELED";
		}
		if(status == Status.FINISHED){
			statusState = "FINISHED";
		}
		if(status == Status.WARNING){
			statusState = "WARNING";
		}
		if(status == Status.RUNNING){
			statusState = "RUNNING";
		}
		return statusState;
	}
	
	
}
