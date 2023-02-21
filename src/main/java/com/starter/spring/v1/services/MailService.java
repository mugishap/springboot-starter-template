package com.starter.spring.v1.services;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;
    private SimpleMailMessage message = new SimpleMailMessage();

    public void sendResetPasswordMail(String toEmail, String names, String passwordResetToken) {
        message.setFrom("premugisha64@gmail.com");
        message.setTo(toEmail);
        message.setText("Dear " + names + "!\n" +
                "\n" +
                "You've requested to reset password to Spring template, " +
                "your verification code is " + passwordResetToken + ". \n" +
                "\n" +
                "This code expires in 5 hours.\n" +
                "\n" +
                "If you have any questions, send us an email precieux@support.com.\n" +
                "\n" +
                "We’re glad you’re here!\n" +
                "\n");
        message.setSubject("Spring template password reset");
        mailSender.send(message);
    }

    public void sendVerificationMail(String toEmail, String names, String passwordResetToken) {
        message.setFrom("premugisha64@gmail.com");
        message.setTo(toEmail);
        message.setText("Dear " + names + "!\n" +
                "\n" +
                "You've requested to reset password to Spring template, " +
                "your verification code is " + passwordResetToken + ". \n" +
                "\n" +
                "This code expires in 5 hours.\n" +
                "\n" +
                "If you have any questions, send us an email precieux@support.com.\n" +
                "\n" +
                "We’re glad you’re here!\n" +
                "\n");
        message.setSubject("Spring template password reset");
        mailSender.send(message);
    }

}
