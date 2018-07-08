package br.com.livingrio.service;

import java.util.Map;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.springframework.stereotype.Service;

import br.com.livingrio.config.ApplicationInfo;
import br.com.livingrio.model.Question;

/**
 * Serviço para enviar e-mail quando uma pergunta tem uma nova resposta
*/
@Service
public class QuestionAnsweredMailService extends SendMailService {

	@Override
	protected String getTitle() {
		return "[livingrio.com.br] Pergunta Respondida";
	}

	@Override
	protected String getBody(Map<String, Object> params) {
		
		Question question = (Question) params.get("question");
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("<p><font style='color:#337AB7;font-size: 20px;'>Olá "+ question.getResponsible().getName() +",</font></p>");
		
		builder.append("<p><b>Sua pergunta: </b><i style='color:gray;'>"+ question.getText() +"</i> feita para o usuário "+ question.getCreatorEvaluation().getName() +" sobre a sua avaliação ao bairro de "+ question.getEvaluation().getNeighborhood().getName() +" foi respondida.</p>" );
		
		String url = ApplicationInfo.APPLICATION_URL + "profile/" + question.getResponsible().getId() + "/questions";
		
		builder.append("<p><a href='"+ url + "'>Clique aqui</a> para ver suas perguntas.</p>");

		return builder.toString() ;
		
	}

	@Override
	protected InternetAddress[] getReplyTo(Map<String, Object> params)
			throws AddressException {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
