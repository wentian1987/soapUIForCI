package model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequirementVo {
	final Logger logger = LoggerFactory.getLogger(this.getClass());
	List<String> mailList = new ArrayList<String>();
	String requirementNum = null;
	String requirementName = null;
	String requirementDesc = null;
	String onlineStatus = null;
	/**
	 * 测试用例编写情况
	 */
	String testcaseStatue = null;

	public RequirementVo() {
	}

	public RequirementVo(String requirementNum, String requirementName, String requirementDesc, 
			String onlineStatus,String testcaseStatue, String mails) {
		super();
		this.requirementNum = requirementNum;
		this.requirementName = requirementName;
		this.requirementDesc = requirementDesc;
		this.onlineStatus = onlineStatus;
		this.testcaseStatue = testcaseStatue;
		if (this.validate(mails)) {
			convertMails(mails);
		} else {
			logger.error("邮件格式出现了问题，请检查。mails=" + mails);
		}
	}

	public String getTestcaseStatue() {
		return testcaseStatue;
	}

	public void setTestcaseStatue(String testcaseStatue) {
		this.testcaseStatue = testcaseStatue;
	}

	private void convertMails(String mails) {
		String[] mailArray = mails.split(";");
		for (String mail : mailArray) {
			mailList.add(mail);
		}
	}

	public boolean validate(String mails) {
		if (StringUtils.countMatches(mails, "@") == StringUtils.countMatches(mails, ";") + 1 ) {
			return true;
		} else {
			return false;
		}
	}

	public List<String> getMailList() {
		return mailList;
	}

	public void setMailList(List<String> mailList) {
		this.mailList = mailList;
	}

	public String getOnlineStatus() {
		return onlineStatus;
	}

	public void setOnlineStatus(String onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

	public String getRequirementNum() {
		return requirementNum;
	}

	public void setRequirementNum(String requirementNum) {
		this.requirementNum = requirementNum;
	}

	public String getRequirementName() {
		return requirementName;
	}

	public void setRequirementName(String requirementName) {
		this.requirementName = requirementName;
	}

	public String getRequirementDesc() {
		return requirementDesc;
	}

	public void setRequirementDesc(String requirementDesc) {
		this.requirementDesc = requirementDesc;
	}
	public String toString(){
		String temp =  requirementNum+  requirementName+  requirementDesc+  onlineStatus;
		logger.info(temp);
		return temp;
	}
}
