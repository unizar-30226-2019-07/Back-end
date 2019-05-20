package selit.mail;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

/**
 * Clase para correos electronicos.
 */
public class MailMail
{
	/** Emisor del correo */
	private MailSender mailSender;
	
	/**
	 * Cambia el emisor del correo a mailSender.
	 * @param mailSender Nuevo emisor del correo.
	 */
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	/**
	 * Envia un correo electronico.
	 * @param from Emisor del coreo.
	 * @param to Destinatario del correo.
	 * @param subject Sujeto del correo.
	 * @param msg Contenido del correo.
	 */
	public void sendMail(String from, String to, String subject, String msg) {

		SimpleMailMessage message = new SimpleMailMessage();
		
		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(msg);
		mailSender.send(message);	
	}
}