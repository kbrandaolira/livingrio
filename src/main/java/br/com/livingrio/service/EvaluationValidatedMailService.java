package br.com.livingrio.service;

import java.util.Map;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.springframework.stereotype.Service;

import br.com.livingrio.config.ApplicationInfo;

/**
 * Servi�o para enviar e-mail quando a avalia��o do usu�rio for validada
*/
@Service
public class EvaluationValidatedMailService extends SendMailService {

	@Override
	protected String getTitle() {
		// TODO Auto-generated method stub
		return  "[livingrio.com.br] Avalia��o Validada";
	}

	@Override
	protected String getBody(Map<String,Object> params) {
		StringBuilder builder = new StringBuilder();
		
		builder.append("<p><font style='color:#337AB7;font-size: 20px;'>Ol� "+ params.get("name").toString() +",</font></p>");
		
		builder.append("<p>Sua avalia��o referente ao bairro de "+ params.get("neighborhood") +" foi validada. <br>");
		builder.append("Obrigado pela colabora��o.</p>");
		
		String url = ApplicationInfo.APPLICATION_URL + "/profile/" + params.get("person_id");
		
		builder.append("<p><a href='"+ url + "'>Clique aqui</a> para ver suas avalia��es.</p>");

		return builder.toString() ;
	}

	@Override
	protected InternetAddress[] getReplyTo(Map<String, Object> params)
			throws AddressException {
		// TODO Auto-generated method stub
		return null;
	}

	

}
