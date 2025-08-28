package org.example.awrms.service.impl;

import org.example.awrms.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;


    @Override
    public void sendDoctorRegistrationEmail(String toEmail, String doctorName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Doctor Registration Successful");
        message.setText("Dear " + doctorName + ",\n\nYour registration as a doctor is successful!\n\nBest Regards,\nHelaSuwaya Team");
        mailSender.send(message);
    }
}
