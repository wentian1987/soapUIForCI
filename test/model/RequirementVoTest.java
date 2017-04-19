package model;

import static org.junit.Assert.*;

import org.apache.commons.lang3.StringUtils;
import org.junit.BeforeClass;
import org.junit.Test;

public class RequirementVoTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void test() {
		RequirementVo vo = new RequirementVo();
		String mails = "1@picc;2@@@picc";
		if(!vo.validate(mails)){
			fail("失败了。");
		}
		System.out.println(StringUtils.countMatches(mails, "@"));
	}

}
