package es.codeurjc.dad.servicio_correo.service;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumer {

    @Value("${mail.origin}")
    private static String origin;

    @Value("${mail.password}")
    private String password;

    private static final Logger log = LoggerFactory.getLogger(RabbitMQConsumer.class);

    @RabbitListener(queues="${rabbitmq.queue.name}")
    public void consume(String message){
        log.info(String.format("Recived message -> %s", message));
        String[] mCutted=message.substring(1).split("-");
        //Contraseña: omzxpsrdfltkeged
        switch(message.charAt(0)){
            //Nuevo contrato:
            case 'N':

                break;
            //Borrar Contrato:
            case 'C':
                break;
            //Nuevo Post:
            case 'I':
                break;
            //Borrar Post:
            case 'P':
                break;
            //Borrar Usuario:
            case 'D':
                break;
            //Registro:
            case 'S':
                break;
            //Nuevo reporte:
            case 'R':
                break;
            default:
                log.info("Not valid message");
        }
    }

    private static void enviarConGMail(String destinatario, String asunto, String cuerpo) {
        
        Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp.gmail.com");  //El servidor SMTP de Google
        props.put("mail.smtp.user", origin);
        props.put("mail.smtp.clave", password);    //La clave de la cuenta
        props.put("mail.smtp.auth", "true");    //Usar autenticación mediante usuario y clave
        props.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
        props.put("mail.smtp.port", "587"); //El puerto SMTP seguro de Google
    
        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);
    
        try {
            message.setFrom(new InternetAddress(remitente));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));   //Se podrían añadir varios de la misma manera
            message.setSubject(asunto);
            message.setText(cuerpo);
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", remitente, claveemail);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (MessagingException me) {
            me.printStackTrace();   //Si se produce un error
        }
    }
}