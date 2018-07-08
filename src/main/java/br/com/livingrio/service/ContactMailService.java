package br.com.livingrio.service;

import java.util.Calendar;
import java.util.Map;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.springframework.stereotype.Service;

/**
 * Serviço para enviar e-mail pelo Fale Conosco
*/
@Service
public class ContactMailService extends SendMailService {

	@Override
	protected String getTitle() {
		return "[Fale Conosco] #" + String.valueOf(Calendar.getInstance().getTimeInMillis());
	}

	@Override
	protected String getBody(Map<String, Object> params) {
		return (String) params.get("body");
	}

	@Override
	protected InternetAddress[] getReplyTo(Map<String, Object> params) throws AddressException {

		String replyTo = (String) params.get("replyTo");
		
		InternetAddress[] repliesTo = new InternetAddress[1];
		
		repliesTo[0] = new InternetAddress(replyTo);
		
		return repliesTo;
	}
	
	

}
