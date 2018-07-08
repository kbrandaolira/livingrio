package br.com.livingrio.service;

import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import br.com.livingrio.config.ApplicationInfo;

public abstract class SendMailService implements IService<Map<String, Object>> {

	private Properties properties;

	private Session session;

	private String applicationEmail = "livingrio@livingrio.com.br";

	private String applicationEmailPassword = "$uc3$$1sc0m1ng";

	public SendMailService() {

		properties = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.port", "465");

		session = Session.getInstance(properties,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(applicationEmail,
								applicationEmailPassword);
					}
				});

	}

	public ServiceResponse execute(Map<String, Object> params) {

		// Organizando os destinatários

		String to = params.get("to") != null ? params.get("to").toString() : "";

		// Configurando a mensagem

		MimeMessage message = null;

		try {

			message = new MimeMessage(session);

			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					to));
			
			
			if(getReplyTo(params) != null){
				
				message.setReplyTo(getReplyTo(params));
			}
			
			
			
			

			message.setSubject(getTitle());

			message.setContent(getBody(params) + getFooter(), "text/html");
		} catch (MessagingException e1) {

			e1.printStackTrace();

			return new ServiceResponse(e1.getMessage(), false);

		}

		// Enviando a mensagem

		try {
			Transport.send(message);

		} catch (MessagingException e) {
			return new ServiceResponse(e.getMessage(), false);
		}

		return new ServiceResponse(true);
	}

	protected abstract String getTitle();

	protected abstract String getBody(Map<String, Object> params);
	
	protected abstract InternetAddress[] getReplyTo(Map<String, Object> params) throws AddressException;
	
	public String getFooter(){
		
		StringBuilder footer = new StringBuilder();
		
	
		footer.append("	<div dir=\"ltr\"> ");
			footer.append("<div style=\"text-align:center\">");
				footer.append("	<font face=\"arial black, sans-serif\">");
					footer.append("	<b>");
						footer.append("		<font color=\"#cccccc\" size=\"2\">");
							footer.append("		_____________________________________________________________");
							footer.append("		<br>");
						footer.append("	</font>");
						
						footer.append("	<br>");
					footer.append("</b>");
				footer.append("</font>");
			footer.append("	</div>");
			
			footer.append("	<font face=\"arial black, sans-serif\" size=\"1\">");
				footer.append("	<div style=\"text-align:center\">");
					footer.append("		<b>");
						footer.append("		<font color=\"#3d85c6\">LIVING&nbsp;</font><font color=\"#bf9000\">RIO</font>");
					footer.append("	</b>");
				footer.append("	</div>");
			footer.append("</font>");
	
			footer.append("<div style=\"font-size:12.8000001907349px;text-align:center\">");
				footer.append("	<span style=\"color:rgb(0,0,0);font-family:verdana,sans-serif;font-size:x-small\">");
					footer.append("<br>		Ajudando você a escolher o melhor bairro para viver <br>");
				footer.append("	</span>");
				footer.append(" <a href='"+ ApplicationInfo.APPLICATION_URL +"'>www.livingrio.com.br</a> ");
			footer.append("</div>");
		
		footer.append("	</div>");
		
		return footer.toString();
		
		
	}
}
