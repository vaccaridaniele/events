package org.e2d.smartfarm.util;

import static org.junit.Assert.fail;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.e2d.smartfarm.pojo.SfErrorsLog;
import org.junit.Test;

public class EmailUtilTest {

	@Test
	public void testSendGmail() {
		fail("Not yet implemented");
	}

	@Test
	public void testSendEmail() throws AddressException, MessagingException {
		EmailUtil emailUtil = new EmailUtil();
		SfErrorsLog errorLog = new SfErrorsLog();
		errorLog.setJson(
				"[{'act':'2','last':'2','imei':'357976060525128','ddate':'110416','dtime':191700,'qual':1,'nrsat':11,'hdop':8,'pdop':15,'lon':1034451,'lat':4531269,'alt':722,'vel':0,'ang':17750,'vers':3,'t1b':0,'t1':123,'t2b':0,'t2':2,'t3b':0,'t3':0}]");
		errorLog.setEmail("vaccaridaniele@gmail.com");
		errorLog.setDescription("Dati errori da datalogger");
		errorLog.setDate(new Date());
		emailUtil.sendEmail(errorLog.getEmail(), errorLog.getDescription(),
				errorLog.getJson() + "<br>" + errorLog.getHttpStatus());
	}
}