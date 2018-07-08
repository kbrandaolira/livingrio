package br.com.livingrio.service;

import java.util.Map;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.springframework.stereotype.Service;

import br.com.livingrio.config.ApplicationInfo;
import br.com.livingrio.model.Person;

/**
 * Servi�o para enviar e-mail para um novo usu�rio no sistema
*/
@Service
public class NewUserMailService extends SendMailService {

	@Override
	protected String getTitle() {
		return "[livingrio.com.br] Ajudando voc� a escolher o melhor bairro para viver";
	}

	@Override
	protected String getBody(Map<String, Object> params) {
		StringBuilder builder = new StringBuilder();
		
		Person person = (Person) params.get("person");
		
		builder.append("<p><font style='color:#337AB7;font-size: 20px;'>Seja bem-vindo "+ person.getName() +",</font></p>");
		
		builder.append("<p>Agora que o primeiro passo foi conclu�do, <br>");
		builder.append("o que acha de avaliar os bairros em que j� morou?<br>");
		builder.append("Nossa comunidade de usu�rios ficaria muito feliz com essa a��o.</p>");
		
		builder.append("<p><a href='"+ ApplicationInfo.APPLICATION_URL + "'>Clique aqui</a> para acessar o nosso site.</p>");

		return builder.toString() ;
	}

	@Override
	protected InternetAddress[] getReplyTo(Map<String, Object> params)
			throws AddressException {
		// TODO Auto-generated method stub
		return null;
	}

}
