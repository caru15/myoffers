package com.example.myoffers_2;

import android.os.AsyncTask;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;

import java.util.Properties;
import javax.activation.CommandMap;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.MailcapCommandMap;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;

public class MailJob extends AsyncTask<MailJob.Mail,Void,Void> {
    private final String user;
    private final String pass;

    public MailJob(String user, String pass) {
        super();
        this.user=user;
        this.pass=pass;
    }

    @Override
    protected Void doInBackground(Mail... mails) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.user", "caru19785");
        props.put("mail.smtp.clave", "31733424");
        props.put("mail.smtp.port", "587");


        /*
        private static void enviarConGMail(String destinatario, String asunto, String cuerpo) {
            // Esto es lo que va delante de @gmail.com en tu cuenta de correo. Es el remitente también.
            private String remitente = "nomcuenta";  //Para la dirección nomcuenta@gmail.com

            Properties props = System.getProperties();
            props.put("mail.smtp.host", "smtp.gmail.com");  //El servidor SMTP de Google
            props.put("mail.smtp.user", remitente);
            props.put("mail.smtp.clave", "miClaveDeGMail");    //La clave de la cuenta
            props.put("mail.smtp.auth", "true");    //Usar autenticación mediante usuario y clave
            props.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
            props.put("mail.smtp.port", "587"); //El puerto SMTP seguro de Google

           */

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user, pass);
                    }
                });
        for (Mail mail:mails) {

            try {

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(mail.from));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(mail.to));
                message.setSubject(mail.subject);
                message.setText(mail.content);
                Transport.send(message);
            } catch (MessagingException e) {
                Log.d("MailJob", e.getMessage());
            }
        }
        return null;
    }

    public static class Mail{
        private final String subject;
        private final String content;
        private final String from;
        private final String to;

        public Mail(String from, String to, String subject, String content){
            this.subject=subject;
            this.content=content;
            this.from=from;
            this.to=to;
        }
    }
}