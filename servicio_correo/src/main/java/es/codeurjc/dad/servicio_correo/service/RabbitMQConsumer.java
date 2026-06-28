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
    private String origin;

    @Value("${mail.password}")
    private String password;

    private final String textFinal =
        "We wish you a great experience and encourage you to " +
        "post your own offers too!";

    private static final Logger log = LoggerFactory.getLogger(RabbitMQConsumer.class);

    @RabbitListener(queues="${rabbitmq.queue.name}")
    public void consume(String message){
        log.info(String.format("Received message -> %s", message));
        String[] parts = message.substring(2).split("-");
        switch(message.charAt(0)){
            // New contract:
            case 'N':
                log.info("Case N: new contract");
                sendWithGMail(parts[0], "New contract",
                            "You have successfully created a contract " + parts[1] +
                            " between you and teacher " + parts[2] +
                            " for the post \"" + parts[3] + "\"." +
                            "\n" + textFinal);
                break;
            // Cancel contract:
            case 'C':
                log.info("Case C: contract cancelled");
                sendWithGMail(parts[0], "Contract cancelled",
                            "The contract " + parts[1] +
                            " between you and teacher " + parts[2] +
                            " for the post \"" + parts[3] + "\" has been successfully cancelled." +
                            "\n" + textFinal);
                break;
            // New post:
            case 'I':
                log.info("Case I: new post");
                sendWithGMail(parts[0], "New post published",
                            "You have successfully published the post \"" + parts[1] +
                            "\" with description \"" + parts[2] + "\" " +
                            "and price " + parts[3] + " EUR." +
                            "\n" + textFinal);
                break;
            // Post deleted:
            case 'P':
                log.info("Case P: post deleted");
                sendWithGMail(parts[0], "Post deleted",
                            "You have successfully deleted the post \"" + parts[1] + "\"." +
                            "\n" + textFinal);
                break;
            // User deleted:
            case 'D':
                log.info("Case D: user deleted");
                sendWithGMail(parts[0], "Account deleted",
                            "The account \"" + parts[1] + "\" has been successfully deleted." +
                            "\n" + textFinal);
                break;
            // Registration:
            case 'S':
                log.info("Case S: user registered");
                sendWithGMail(parts[0], "Account created",
                            "You have successfully registered the account \"" + parts[1] + "\"." +
                            "\n" + textFinal);
                break;
            // New report:
            case 'R':
                log.info("Case R: new report");
                sendWithGMail(message, "New report submitted",
                            "You have submitted a report on post \"" + parts[1] +
                            "\" with reason \"" + parts[2] +
                            "\" and description \"" + parts[3] + "\"." +
                            "\n" + textFinal);
                break;
            default:
                log.info("Not a valid message type");
        }
    }

    private void sendWithGMail(String recipient, String subject, String body) {

        Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp.gmail.com");  // Google SMTP server
        props.put("mail.smtp.user", origin);
        props.put("mail.smtp.clave", password);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "587");  // Google secure SMTP port

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(origin));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", origin, password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (MessagingException me) { me.printStackTrace(); }
    }
}
