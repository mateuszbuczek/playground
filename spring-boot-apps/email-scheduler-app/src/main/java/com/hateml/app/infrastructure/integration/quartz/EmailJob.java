package com.hateml.app.infrastructure.integration.quartz;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
@RequiredArgsConstructor
public class EmailJob extends QuartzJobBean {

    private final JavaMailSender mailSender;
    private final MailProperties mailProperties;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap dataMap = jobExecutionContext.getMergedJobDataMap();
        String subject = dataMap.getString("subject");
        String body = dataMap.getString("body");
        String email = dataMap.getString("email");

        sendEmail(mailProperties.getUsername(), email, subject, body);
    }

    private void sendEmail(String from, String to, String subject, String body) {
        try {
            log.info("Sending email tp {}", to);

            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper messageHelper = new MimeMessageHelper(message, StandardCharsets.UTF_8.toString());
            messageHelper.setSubject(subject);
            messageHelper.setText(body, true);
            messageHelper.setFrom(from);
            messageHelper.setTo(to);

            mailSender.send(message);
        } catch (MessagingException ex) {
            log.error("Failed to send email");
        }
    }
}
