package com.starter.spring.v1.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;
    private SimpleMailMessage message = new SimpleMailMessage();

    @Value("${spring.mail.username}")
    private String FROM;

    public String sendResetPasswordMail(String toEmail, String names, String passwordResetToken) {
        try {

            message.setFrom(FROM);
            message.setTo(toEmail);
            message.setText("Dear " + names + "!\n" +
                    "\n" +
                    "You've requested to reset password to Spring template, click the link below to verify your account " +
                    "\n" +
                    "This link expires in 5 hours.\n" +
                    "\n" +
                    "https://starter-app.vercel.app/auth/forgot-password/" + passwordResetToken +
                    "\n" +
                    "If you have any questions, send us an email precieuxmugisha@gmail.com.\n" +
                    "\n" +
                    "We’re glad you’re here!\n" +
                    "\n");
            message.setSubject("Spring template password reset");
            mailSender.send(message);
            return "Message sent successfully";
        } catch (MailSendException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void sendVerificationMail(String toEmail, String names, String verificationToken) {
        message.setFrom("premugisha64@gmail.com");
        message.setTo(toEmail);
        message.setText("Dear " + names + "!\n" +
                "\n" +
                "You've requested to verify you account in Spring template, " +
                "\n" +
                "This link expires in 5 hours.\n" +
                "\n" +
                "https://starter-app.vercel.app/auth/forgot-password/" + verificationToken +
                "\n" +
                "We’re glad you’re here!\n" +
                "\n");
        message.setSubject("Spring template password reset");
        mailSender.send(message);
    }

}
