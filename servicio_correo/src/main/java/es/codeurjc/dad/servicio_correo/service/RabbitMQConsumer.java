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

    private final String textFinal=
        "Te deseamos una buena estancia y te animamos a "+
        "que añadas tu también tus propios anuncios!";

    private static final Logger log = LoggerFactory.getLogger(RabbitMQConsumer.class);

    @RabbitListener(queues="${rabbitmq.queue.name}")
    public void consume(String message){
        log.info(String.format("Recived message -> %s", message));
        String[] mCutted=message.substring(2).split("-");
        switch(message.charAt(0)){
            //Nuevo contrato:
            case 'N':
                log.info("Caso N");
                enviarConGMail(mCutted[0],"Nuevo contrato",
                            "Has creado con éxito el contrato "+mCutted[1]+
                            ", entre tu y el profesor "+mCutted[2]+" del post "+
                            "con titulo "+mCutted[3]+".\n"+textFinal);
                break;
            //Borrar Contrato:
            case 'C':
                log.info("Caso C");
                enviarConGMail(mCutted[0],"Contrato borrado",
                            "Se ha borrado con éxito el contrato "+mCutted[1]+
                            ", entre tu y el profesor "+mCutted[2]+" del post "+
                            "con titulo "+mCutted[3]+".\n"+textFinal);
                break;
            //Nuevo Post:
            case 'I':
                log.info("Caso I");
                enviarConGMail(mCutted[0],"Nuevo post",
                            "Has creado con éxito el post con titulo "+
                            mCutted[1]+", descripcion \""+mCutted[2]+"\" "+
                            "y precio de "+mCutted[3]+" euros.\n"+textFinal);
                break;
            //Borrar Post:
            case 'P':
                log.info("Caso P");
                enviarConGMail(mCutted[0],"Post borrado",
                            "Has borrado con éxito el post con titulo "+mCutted[1]+".\n"+textFinal);
                break;
            //Borrar Usuario:
            case 'D':
                log.info("Caso D");
                enviarConGMail(mCutted[0],"Usuario borrado",
                            "Has borrado con éxito el usuario llamado "+mCutted[1]+".\n"+textFinal);
                break;
            //Registro:
            case 'S':
                log.info("Caso S");
                enviarConGMail(mCutted[0],"Usuario creado",
                            "Has logrado registrarte adecuadamente el usuario"+
                            " con nombre "+mCutted[1]+".\n"+textFinal);
                break;
            //Nuevo reporte:
            case 'R':
                log.info("Caso R");
                enviarConGMail(message,"Nuevo reporte",
                            "Has hecho un reporte al post "+mCutted[1]+
                            ", con motivo \""+mCutted[2]+"\" y descripcion \""+
                            mCutted[3]+"\".\n"+textFinal);
                break;
            default:
                log.info("Not valid message");
        }
    }

    private void enviarConGMail(String destinatario, String asunto, String cuerpo) {
        
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
            message.setFrom(new InternetAddress(origin));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));   //Se podrían añadir varios de la misma manera
            message.setSubject(asunto);
            message.setText(cuerpo);
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com",origin,password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (MessagingException me) {me.printStackTrace();}
    }
}