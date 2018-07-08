package br.com.livingrio.service;

import java.util.Map;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.springframework.stereotype.Service;

import br.com.livingrio.config.ApplicationInfo;
import br.com.livingrio.model.Question;

/**
 * Servi�o para enviar email quando h� uma nova avalia��o para ser aprovada.
*/
@Service
public class NewEvaluationMailService extends SendMailService {

	@Override
	protected String getTitle() {
		return "[livingrio.com.br] Nova avalia��o pendente de aprova��o";
	}

	@Override
	protected String getBody(Map<String, Object> params) {
		
		Question question = (Question) params.get("question");
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("<p><font style='color:#337AB7;font-size: 20px;'>Ol�,</font></p>");
		
		builder.append("<p>Uma nova avalia��o foi enviada.</p>");
		
		String url = ApplicationInfo.APPLICATION_URL + "neighborhood/evaluations/validation";
		
		builder.append("<p><a href='"+ url + "'>Clique aqui</a> para aprovar.</p>");

		return builder.toString() ;
		
	}

	@Override
	protected InternetAddress[] getReplyTo(Map<String, Object> params)
			throws AddressException {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
