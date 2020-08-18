package com.star.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.text.Normalizer;
import java.util.Map;

/**
 * @author James
 * @create 2019-04-01 10:14
 * @desc
 **/
public interface MailService {
    public void sendSimpleTextMail(String to, String subject, String content);
    public void sendHtmlMail(String to, String subject, String content) throws MessagingException;
    public void sendAttachmentMail(String to, String subject, String content, String... fileArr)
            throws MessagingException;
    public void sendImgMail(String to, String subject, String content, Map<String, String> imgMap)
            throws MessagingException;
    public void sendTemplateMail(String to, String subject, Map<String, Object> paramMap, String template)
            throws MessagingException;
}
