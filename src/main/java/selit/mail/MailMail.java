package selit.mail;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import javax.mail.internet.MimeMessage;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * Clase para correos electronicos.
 */
public class MailMail
{
	/** Emisor del correo */
	private JavaMailSender mailSender;
	
	/**
	 * Cambia el emisor del correo a mailSender.
	 * @param mailSender Nuevo emisor del correo.
	 */
	public void setMailSender(JavaMailSender mailSender) {
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
	
	
	public void sendMail2(String from, String to, String subject, String personal, String anuncios, String subastas, String pujas, String valoraciones, String wishesA, String wishesS) {
		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText("Esto son tus datos");
			InputStream is= new ByteArrayInputStream(personal.getBytes(StandardCharsets.UTF_8));
			helper.addAttachment("personal.json",new ByteArrayResource(IOUtils.toByteArray(is)));
			if (!anuncios.isEmpty()) {
				is= new ByteArrayInputStream(anuncios.getBytes(StandardCharsets.UTF_8));
				helper.addAttachment("anuncios.json",new ByteArrayResource(IOUtils.toByteArray(is)));
			}
			if (!subastas.isEmpty()) {
				is= new ByteArrayInputStream(subastas.getBytes(StandardCharsets.UTF_8));
				helper.addAttachment("subastas.json",new ByteArrayResource(IOUtils.toByteArray(is)));
			}
			if (!pujas.isEmpty()) {
				is= new ByteArrayInputStream(pujas.getBytes(StandardCharsets.UTF_8));
				helper.addAttachment("pujas.json",new ByteArrayResource(IOUtils.toByteArray(is)));
			}
			if (!valoraciones.isEmpty()) {
				is= new ByteArrayInputStream(valoraciones.getBytes(StandardCharsets.UTF_8));
				helper.addAttachment("valoraciones.json",new ByteArrayResource(IOUtils.toByteArray(is)));
			}
			if (!wishesA.isEmpty()) {
				is= new ByteArrayInputStream(wishesA.getBytes(StandardCharsets.UTF_8));
				helper.addAttachment("anunciosDeseados.json",new ByteArrayResource(IOUtils.toByteArray(is)));
			}
			if (!wishesS.isEmpty()) {
				is= new ByteArrayInputStream(wishesS.getBytes(StandardCharsets.UTF_8));
				helper.addAttachment("SubastasDeseadas.json",new ByteArrayResource(IOUtils.toByteArray(is)));
			}
			mailSender.send(message);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}