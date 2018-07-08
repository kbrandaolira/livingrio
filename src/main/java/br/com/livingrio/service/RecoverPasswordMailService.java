package br.com.livingrio.service;

import java.util.Map;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.springframework.stereotype.Service;

import br.com.livingrio.config.ApplicationInfo;

/**
 * Servi�o para enviar e-mail de recupera��o de senha
*/
@Service
public class RecoverPasswordMailService extends SendMailService {

	@Override
	protected String getTitle() {
		// TODO Auto-generated method stub
		return  "[livingrio.com.br] Recuperar Senha";
	}

	@Override
	protected String getBody(Map<String,Object> params) {
		
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("<p><font style='color:#337AB7;font-size: 20px;'>Ol� "+ params.get("name").toString() +",</font></p>");
		
		builder.append("<p>Voc� esqueceu a sua senha? <br>");
		builder.append("N�o tem problema, estamos te informando uma nova logo abaixo: <br></p>");
		
		builder.append("<p><b>Senha: </b> " +  params.get("password").toString() +" </p>");
		
		
		builder.append("<p>Agora que j� pode logar novamente, o que acha de avaliar os <br>");
		builder.append("bairros em que morou para ajudar a nossa comunidade de usu�rios?</p>");
		
		builder.append("<p><a href='"+ ApplicationInfo.APPLICATION_URL +"'>Clique aqui</a> para acessar o nosso site.</p>");

		
		
		return builder.toString();
	}

	@Override
	protected InternetAddress[] getReplyTo(Map<String, Object> params)
			throws AddressException {
		// TODO Auto-generated method stub
		return null;
	}

	

}
