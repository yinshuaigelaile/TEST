package com.lianwei.store.test;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

public class TestMyMailSend {
	public static void sendMail(String email, String emailMsg)
			throws AddressException, MessagingException {
		// 1.创建一个程序与邮件服务器会话对象 Session

		Properties props = new Properties();
//		//设置发送的协议
//		props.setProperty("mail.transport.protocol", "SMTP");
//		
//		//设置发送邮件的服务器
//		props.setProperty("mail.host", "smtp.qq.com");
//		props.setProperty("mail.smtp.auth", "true");// 指定验证为true
		

		// 创建验证器
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				//设置发送人的账号和密码
				return new PasswordAuthentication("admin@store.com", "admin");
//				return new PasswordAuthentication("2632855426@qq.com", "fclbohkzswhadifa");
			}
		};

		Session session = Session.getInstance(props, auth);
		session.setDebug(true);

		// 2.创建�?个Message，它相当于是邮件内容
		Message message = new MimeMessage(session);

		//设置发送者
//		message.setFrom(new InternetAddress("2632855426@qq.com"));
		message.setFrom(new InternetAddress("admin@store.com"));

		//设置发送方式与接收者
		message.setRecipient(RecipientType.TO, new InternetAddress(email)); 

		//设置邮件主题
//		message.setSubject("用户激活");
		message.setSubject("送给小辣鸡的一封信");
		// message.setText("这是�?封激活邮件，�?<a href='#'>点击</a>");

//		String url="http://localhost:48080/store/UserServlet?method=active&code="+emailMsg;
//		String content="<h1>来自购物天堂的激活邮件，激活请点击以下链接!</h1><h3><a href='"+url+"'>"+url+"</a></h3>";
		String content = "<h1>"+emailMsg+"</h1>";
		//设置邮件内容
		message.setContent(content, "text/html;charset=utf-8");

		// 3.创建 Transport用于将邮件发�?
		for(int i=0;i<10;i++) {
			Transport.send(message);
		}
		
	}
	
	public static void main(String[] args) throws AddressException, MessagingException {
		sendMail("aaa@store.com", "小辣鸡太优秀了，没办法");
//		sendMail("2506330750@qq.com", "小辣鸡美丽、善良、大方、优雅、文静、脱俗、纯洁、开朗、贤淑活泼、率直、可爱、天真、端庄、温柔、贤惠、多才、俊俏，温柔、体贴、撒娇、任性、独立、爱美、温柔 善良 贤惠");
	}
}
