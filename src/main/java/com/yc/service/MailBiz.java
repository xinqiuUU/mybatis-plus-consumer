package com.yc.service;

import com.google.gson.Gson;
import com.yc.model.MessageBean;
import com.yc.model.RedisEmail;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Log
public class MailBiz {

    @Autowired  // 注入JavaMailSender
    private JavaMailSender MailSender;

    @Value("${spring.mail.properties.mail.smtp.from}")
    public String fromemail;

//    @Autowired
//    private RedisTemplate redisTemplate;
    //返回前端的数据
    List<RedisEmail> results = new ArrayList<>();

    @Autowired
    private WebSocketServer webSocketServer;

    @Async  // 异步发送邮件
    public void send(String to, String subject, String text , MessageBean mb) {
//        SimpleMailMessage message = new SimpleMailMessage(); //不包括附件

        MimeMessage mm = MailSender.createMimeMessage();  //可以包括附件
        try{
            // 邮件内容                                        true 表示可以加附件
            MimeMessageHelper message = new MimeMessageHelper(mm, true,"UTF-8");
            message.setFrom(fromemail);// 发件人
            message.setTo(to);// 收件人
            message.setSubject(subject);// 邮件主题
            message.setText(text , true);// 邮件内容 一定要有 true 代表当成html代码
            MailSender.send( mm );// 发送邮件
            long score = System.currentTimeMillis()/1000;
//            opsForZSet().add(K key, V value, double score): 向有序集合添加值
            // 有序集合的key  发送者     收件人    主题
//            String value = fromemail+" "+to+" "+subject+":"+mb.getOpType() +" "+score;
            Date date = new Date();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String sendTime = df.format(date);
            RedisEmail email = new RedisEmail(fromemail,to,subject+":"+mb.getOpType(),sendTime);
            Gson go = new Gson();
//            String key = String.valueOf(mb.getAccount().getAccountid());

//            redisTemplate.opsForZSet().add(key,go.toJson(email) ,score);
            results.add(email);

            webSocketServer.send( go.toJson(results) ); // 发送websocket消息 通知前端需要刷新了 即前端需要去redis数据库中读取消息了
        }catch (Exception e){
            e.printStackTrace();
            log.info("邮件发送失败:"+e.getMessage());
        }

    }

}
