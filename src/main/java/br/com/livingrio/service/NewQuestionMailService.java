package br.com.livingrio.service;

import java.util.Map;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.springframework.stereotype.Service;

import br.com.livingrio.config.ApplicationInfo;
import br.com.livingrio.model.Question;

/**
 * Servi�o para enviar email quando h� uma nova pergunta para um usu�rio
*/
@Service
public class NewQuestionMailService extends SendMailService {

	@Override
	protected String getTitle() {
		return "[livingrio.com.br] Pergunta Recebida";
	}

	@Override
	protected String getBody(Map<String, Object> params) {
		
		Question question = (Question) params.get("question");
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("<p><font style='color:#337AB7;font-size: 20px;'>Ol� "+ question.getCreatorEvaluation().getName() +",</font></p>");
		
		builder.append("<p>Voc� recebeu uma pergunta referente a sua avalia��o feita para o bairro de " + question.getEvaluation().getNeighborhood().getName() + ".</p>");
		
		String url = ApplicationInfo.APPLICATION_URL + "profile/" + question.getResponsible().getId() + "/questions/answer";
		
		builder.append("<p><a href='"+ url + "'>Clique aqui</a> para responder.</p>");

		return builder.toString() ;
		
	}

	@Override
	protected InternetAddress[] getReplyTo(Map<String, Object> params)
			throws AddressException {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
