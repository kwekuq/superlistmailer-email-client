package za.qns.logic;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Properties;

import javax.print.DocFlavor.URL;
import javax.swing.JProgressBar;
import javax.swing.JTextPane;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import za.qns.storage.*;
import za.qns.ui.gui;
import za.qns.ui.messagin;
public class sender {
	private ArrayList<Contact> list = new ArrayList<Contact>();
	private HtmlEmail email = new HtmlEmail();
	private JProgressBar bar;
	public void sendEmail(JTextPane S, String sub) throws EmailException {
		email.setHostName("smtp.gmail.com");
		email.setSmtpPort(587);
		email.setSSL(true);
		email.setAuthentication("technical.mcv@gmail.com", "Thlonolofatso2208");
		email.setFrom("kwekuq@gmail.com", "Kweku Quansah");
		email.setSubject(sub);
		email.setTextMsg("Your email client does not support HTML messages");
		DecimalFormat form = new DecimalFormat("##");
		email.setHtmlMsg(S.getText());
		for(int i = 0; i < list.size(); i++) {
			Contact temp = list.get(i);
			email.addBcc(temp.getEmail());
			
			bar.setValue(Integer.valueOf(form.format((calc() * (i + 1)))));
			bar.repaint();
		}
		email.send();
			
			
			bar.setValue(100);
			bar.repaint();
			new messagin().displayMessage("Emails sent");
	}
	
	public double calc() {
		return 100 / list.size();
	}
	
	public void add(Contact C) {
		list.add(C);
	}
	
	public ArrayList<Contact> getList() {
		return list;
	}
	
	public void setList(ArrayList<Contact> C) {
		list = C;
	}
	
	public void setProgress(JProgressBar prog) {
		bar = prog;
		bar.setValue(0);
	}
}
