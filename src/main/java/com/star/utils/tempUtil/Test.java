package com.star.utils.tempUtil;

import com.star.utils.SnowFlake;

public class Test {
    public static void main(String[] args) {

        //测试snowFlake算法
        SnowFlake snowFlake = new SnowFlake(2, 3);
        for(int i=0;i<10;i++){
            System.out.println(snowFlake.nextId());
        }
    }
    //测试邮箱注册
    public static void send(){
        String mail = "1101964585@qq.com"; //发送对象的邮箱
        String title = "star-art注册码";
        String content = "<div><h2>欢迎加入star-art</h2><p>您的注册码是2020</p></div>";
        MailInfo info = new MailInfo();
        info.setToAddress(mail);
        info.setSubject(title);
        info.setContent(content);
        try {
            MailSendUtil.sendHtmlMail(info);
        } catch (Exception e) {
            System.out.print("'"+title+"'的邮件发送失败！");
            e.printStackTrace();
        }
    }
}
