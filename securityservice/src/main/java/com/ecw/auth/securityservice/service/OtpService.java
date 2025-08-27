package com.ecw.auth.securityservice.service;

import com.ecw.auth.securityservice.entity.EmailOtp;
import com.ecw.auth.securityservice.repository.EmailOtpRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class OtpService {

    private final EmailOtpRepository emailOtpRepository;
    private final JavaMailSender mailSender;

    public OtpService(EmailOtpRepository emailOtpRepository, JavaMailSender mailSender) {
        this.emailOtpRepository = emailOtpRepository;
        this.mailSender = mailSender;
    }

    public void generateAndSendOtp(String email) {
        String otp = String.valueOf(100000 + new Random().nextInt(900000));

        EmailOtp emailOtp = emailOtpRepository.findByEmail(email)
                .orElse(new EmailOtp());
        emailOtp.setEmail(email);
        emailOtp.setOtp(otp);
        emailOtp.setCreatedAt(LocalDateTime.now());                // ✅ must set
        emailOtp.setExpiresAt(LocalDateTime.now().plusMinutes(5));
        emailOtpRepository.save(emailOtp);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your OTP Code"); // Mail Subject
        message.setText("Your OTP code is: " + otp + " (valid for 5 minutes)"); //Mail Body
        mailSender.send(message);
    }

    public boolean verifyOtp(String email, String otp) {
       return emailOtpRepository.findByEmail(email)
                .filter(e -> e.getOtp().equals(otp) && e.getExpiresAt().isAfter(LocalDateTime.now()))
                .isPresent();

    }
}