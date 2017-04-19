import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;
import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.config.TransportStrategy;

public class MailTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void testMail() {
		Email email = new EmailBuilder().from("wentian", "wentian1@126.com").
				to("me", "wentian1@126.com").subject("test wentian send!")
				.text("test wentian send!").build();
		
		new Mailer("smtp.126.com", 587, "wentian1", "wentian1987",
				TransportStrategy.SMTP_SSL).sendMail(email);
		fail("Not yet implemented");
	}
}
