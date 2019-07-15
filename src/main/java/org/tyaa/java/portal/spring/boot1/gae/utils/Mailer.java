/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tyaa.java.portal.spring.boot1.gae.utils;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author gachechega
 */
public class Mailer {

    public static void sendPlainMsg(
            String _messageString,
             String _subjectString,
             String _fromAddressString,
             String _fromNameString,
             String _toAddressString,
             String _toNameString
    ) throws MessagingException, UnsupportedEncodingException {

        Properties props = new Properties();
        props.setProperty("mail.mime.charset", "UTF-8");
        Session session = Session.getDefaultInstance(props, null);

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(_fromAddressString, _fromNameString));
        msg.addRecipient(Message.RecipientType.TO, new InternetAddress(_toAddressString, _toNameString));
        msg.setSubject(_subjectString);
        msg.setText(_messageString);
        Transport.send(msg);
    }
}
