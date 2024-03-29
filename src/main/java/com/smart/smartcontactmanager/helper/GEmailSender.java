package com.smart.smartcontactmanager.helper;

import jakarta.mail.*;
import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

import java.io.File;
import java.util.Properties;

public class GEmailSender {

    public boolean sendEmail(String to , String from , String subject, String text){
        boolean flag =false;

        Properties properties=new Properties();
        properties.put("mail.smtp.auth",true);
        properties.put("mail.smtp.starttls.enable",true);
        properties.put("mail.smtp.port","587");
        properties.put("mail.smtp.host","smtp.gmail.com");
        String username= "vijayyadavworks";
        String password="kzsrsinrvrayjgaw";
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username,password);
            }
        });

        try{
            Message message=new MimeMessage(session);
            message.setRecipient(Message.RecipientType.TO,new InternetAddress(to));
            message.setFrom(new InternetAddress(from));
            message.setSubject(subject);
            message.setText(text);
            Transport.send(message);
            flag=true;
        }catch(Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean sendEmailAttachment(String to,String from,String subject,String text,File file){

        boolean flag =false;

        Properties properties=new Properties();
        properties.put("mail.smtp.auth",true);
        properties.put("mail.smtp.starttls.enable",true);
        properties.put("mail.smtp.port","587");
        properties.put("mail.smtp.host","smtp.gmail.com");
        String username= "vijayyadavworks";
        String password="kzsrsinrvrayjgaw";
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username,password);
            }
        });

        try{
            Message message=new MimeMessage(session);
            message.setRecipient(Message.RecipientType.TO,new InternetAddress(to));
            message.setFrom(new InternetAddress(from));
            message.setSubject(subject);

            MimeBodyPart part1=new MimeBodyPart();
            part1.setText(text);
            MimeBodyPart part2=new MimeBodyPart();
            part2.attachFile(file);

            MimeMultipart mimeMultipart=new MimeMultipart();
            mimeMultipart.addBodyPart(part1);
            mimeMultipart.addBodyPart(part2);

            message.setContent(mimeMultipart);
            Transport.send(message);
            flag=true;
        }catch(Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
}
