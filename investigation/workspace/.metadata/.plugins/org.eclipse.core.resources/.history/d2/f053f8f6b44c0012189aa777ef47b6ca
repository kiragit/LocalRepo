package com.example.sakuban;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class GmailSend {

	private String title = "【SKBN】";
	private String mainTxt = "";
	private String senderAddress = "kentaro.kira@gmail.com";
	private String fromAddress = "kentaro.kira@gmail.com";
	private String password = "01ken014uMoka";

	public GmailSend() {

	}

	public void sendMail() {

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com"); // SMTPサーバ名
		props.put("mail.host", "smtp.gmail.com"); // 接続するホスト名
		props.put("mail.smtp.port", "587"); // SMTPサーバポート
		props.put("mail.smtp.auth", "true"); // smtp auth
		props.put("mail.smtp.starttls.enable", "true"); // STTLS

		// セッション
		Session session = Session.getDefaultInstance(props);
		session.setDebug(true);

		MimeMessage msg = new MimeMessage(session);
		try {
			msg.setSubject(title, "utf-8");
			msg.setFrom(new InternetAddress(fromAddress));
			msg.setSender(new InternetAddress(senderAddress));
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(senderAddress));
			msg.setText(mainTxt, "utf-8");

			Transport t = session.getTransport("smtp");
			t.connect(fromAddress, password);
			t.sendMessage(msg, msg.getAllRecipients());

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public void setMainTxt(String mainTxt) {
		this.mainTxt = mainTxt;
	}

}
