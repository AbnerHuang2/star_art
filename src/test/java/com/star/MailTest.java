package com.star;

import com.star.service.MailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class MailTest {

    @Autowired
    private MailService mailService;


    String to = "1101964585@qq.com";

    @Test
    void contextLoads() {
        String subject = "Springboot 发送简单文本邮件";
        String content = "<h2>Hi~</h2><p>第一封 Springboot Text 邮件</p>";
        mailService.sendSimpleTextMail(to, subject, content);
    }

    @Test
    public void sendHtmlMailTest() throws MessagingException {
        String subject = "Springboot 发送 HTML 邮件";
        String content = "<h2>Hi~</h2><p>第一封 Springboot HTML 邮件</p>";
        mailService.sendHtmlMail(to, subject, content);
    }

    @Test
    public void sendAttachmentTest() throws MessagingException {
        String subject = "Springboot 发送 HTML 附件邮件";
        String content = "<h2>Hi~</h2><p>第一封 Springboot HTML 附件邮件</p>";
        String filePath = "pom.xml";
        mailService.sendAttachmentMail(to, subject, content, filePath);
    }

    @Test
    public void sendImgTest() throws MessagingException {
        String subject = "Springboot 发送 HTML 图片邮件";
        String content =
                "<h2>Hi~</h2><p>第一封 Springboot HTML 图片邮件</p><br/><img style='width:300px;height:300px' src='cid:img01' />";
        String imgPath = "img/1.jpg";
        Map<String, String> imgMap = new HashMap<>();
        imgMap.put("img01", imgPath);
        //imgMap.put("img02", imgPath);
        mailService.sendImgMail(to, subject, content, imgMap);
    }

    @Test
    public void sendTemplateMailTest() throws MessagingException {
        String subject = "Springboot 发送 模版邮件";
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("username", "Darcy");
        paramMap.put("checkCode", "2020");
        mailService.sendTemplateMail(to, subject, paramMap, "check");
    }

}

