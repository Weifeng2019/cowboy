package com.cow.cowboy.mail;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.List;

//参考链接：https://blog.csdn.net/ljk126wy/article/details/83239398

@Service
public class MailService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JavaMailSender sender;

    @Value("${spring.mail.username}")
    private String formMail;

    public void sendSimpleMail(String toMail,String subject,String content) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(formMail);
        simpleMailMessage.setTo(toMail);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(content);
        try {
            sender.send(simpleMailMessage);
            logger.info("发送给"+toMail+"简单邮件已经发送。 subject："+subject);
        }catch (Exception e){
            logger.info("发送给"+toMail+"send mail error subject："+subject);
            e.printStackTrace();
        }
    }

    //邮箱
    public void sendHtmlMail(String toMail,String subject,String content) {
        MimeMessage mimeMessage = sender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
            mimeMessageHelper.setTo(toMail);
            mimeMessageHelper.setFrom(formMail);
            mimeMessageHelper.setText(content,true);
            mimeMessageHelper.setSubject(subject);
            sender.send(mimeMessage);
            logger.info("发送给"+toMail+"html邮件已经发送。 subject："+subject);
        } catch (MessagingException e) {
            logger.info("发送给"+toMail+"html send mail error subject："+subject);
            e.printStackTrace();
        }
    }


    /**
     * 发送静态资源（一般是图片）的邮件
     * @param to
     * @param subject
     * @param content 邮件内容，需要包括一个静态资源的id，比如：<img src=\"cid:image\" >
     * @param resourceist 静态资源list
     */
    public void sendInlineResourceMail(String to, String subject, String content, List<InlineResource> resourceist){
        MimeMessage message = sender.createMimeMessage();

        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(formMail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            for (InlineResource inlineResource : resourceist) {
                FileSystemResource res = new FileSystemResource(new File(inlineResource.getPath()));
                helper.addInline(inlineResource.getCid(),res);
            }
            sender.send(message);
            logger.info("嵌入静态资源的邮件已经发送。");
        } catch (MessagingException e) {
            logger.error("发送嵌入静态资源的邮件时发生异常！", e);
        }
    }

}