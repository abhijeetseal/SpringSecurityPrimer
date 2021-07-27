package com.sealab.webservice.api.resourceserver.service;

import com.sealab.webservice.api.resourceserver.entity.Activationtoken;
import com.sealab.webservice.api.resourceserver.repository.ActivationTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ActivationEmailService {

    @Autowired
    private ActivationTokenRepository activationTokenRepository;



    private JavaMailSender javaMailSender;

    @Autowired
    public ActivationEmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendEmail(SimpleMailMessage email, Activationtoken activationtoken) {
        log.info("Sending Email"+ email.getTo());
        activationTokenRepository.save(activationtoken);
        javaMailSender.send(email);
    }


    public Activationtoken findByConfirmationToken(String confirmationToken) {
        return activationTokenRepository.getByConfirmationToken(confirmationToken);
    }
}
