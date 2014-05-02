package za.qns.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import org.apache.commons.mail.EmailException;

import za.qns.logic.Contact;
import za.qns.logic.sender;
import za.qns.storage.contactReader;

import com.hexidec.ekit.*;

public class gui {
	JFrame desktop;
	
	//Menu stuff
	JMenuBar bar;
	JMenu file;
	JMenu email;
	JMenu help;
	
	JMenuItem load;
	JMenuItem add;
	JMenuItem login;
	JMenuItem exit;
	
	JMenuItem New;
	JMenuItem send;
	
	JMenuItem about;
	JMenuItem author;
	
	//Look stuff
	JPanel menu;
	JPanel top;
	JPanel middle;
	JPanel footer;
	
	//Content stuff
	JTextField subject;
	JEditorPane message;
	JProgressBar progress;
	JButton sendMsg;
	
	EkitCore ekit;
	JScrollPane scroll;
	
	Contact cont;
	contactReader dblist;
	sender emailer;
	
	public gui(contactReader readDB, Contact contacts, sender email) {
		dblist = readDB;
		cont = contacts;
		emailer = email;
		ui();
	}


	private void ui() {
		desktop = new JFrame();
		desktop.setSize(800, 600);
		desktop.setLayout(new BorderLayout());
		bar =  new JMenuBar();
		desktop.setJMenuBar(bar);
		file = new JMenu("File");
		email = new JMenu("Email");
		help = new JMenu("Help");
		
		load = new JMenuItem("Load");
		add = new JMenuItem("Add Contact");
		login = new JMenuItem("Login");
		exit = new JMenuItem("Exit");
		
		New = new JMenuItem("New");
		send = new JMenuItem("Send");
		
		about = new JMenuItem("About");
		author = new JMenuItem("Author");
		
		file.add(load);
		file.add(add);
		file.add(login);
		file.add(exit);
		
		email.add(New);
		email.add(send);
		
		help.add(about);
		help.add(author);
		
		bar.add(file);
		bar.add(email);
		bar.add(help);
		
		
		
		top = new JPanel();
		top.setLayout(new BorderLayout());
		
		subject = new JTextField(50);
		top.add("West",new JLabel("Subject:"));
		top.add(subject);
		
		middle = new JPanel();
		middle.setLayout(new BorderLayout());
		middle.add("North",new JLabel("Message:"));
		message = new JEditorPane();
		message.setBorder(new EtchedBorder());
		message.setContentType("text/html");
		
		JPanel editor = new JPanel();
		editor.setLayout(new FlowLayout());
		ekit = new EkitCore();
		scroll = new JScrollPane();
		menu = new JPanel();
		menu.setLayout(new GridLayout(2,2));
		menu.add(ekit.getToolBarMain(true));
		menu.add(editor);
		editor.add(ekit.getToolBarFormat(true));
		editor.add(ekit.getToolBarStyles(true));
		scroll.add(ekit);
		
		scroll.setVisible(true);
		middle.add("North",menu);
		//middle.add("North",editor);
		middle.add(ekit);
		
		footer = new JPanel();
		footer.setLayout(new BorderLayout());
		sendMsg = new JButton("Send");
		footer.add("West",sendMsg);
		progress = new JProgressBar();
		progress.setSize(400, 30);
		progress.setMaximum(100);
		//progress.setValue(33);
		footer.add(progress);
		
		desktop.add("North",top);
		desktop.add(middle);
		desktop.add("South",footer);
		desktop.setLocationRelativeTo(null);
		desktop.setVisible(true);
		
		ekit.disable();
		middle.disable();
		sendMsg.disable();
		subject.disable();
	
		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				final JFrame tempContact = new JFrame();
				JPanel addPlatform = new JPanel();
				addPlatform.setLayout(new GridLayout(3,2));
				final JTextField names = new JTextField(10);
				final JTextField emails = new JTextField(10);
				addPlatform.add(new JLabel("Name:"));
				addPlatform.add(names);
				addPlatform.add(new JLabel("Email:"));
				addPlatform.add(emails);
				
				JButton saveMe = new JButton("Add");
				JButton cancelMe = new JButton("Cancel");
				addPlatform.add(saveMe);
				addPlatform.add(cancelMe);
				tempContact.add(addPlatform);
				tempContact.setSize(400, 100);
				tempContact.setLocationRelativeTo(null);
				tempContact.setVisible(true);
				
				saveMe.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						Contact C = new Contact();
						C.setName(names.getText());
						C.setEmail(emails.getText());
						emailer.add(C);
						new messagin().displayMessage("Contact added");
						tempContact.dispose();
					}
					
				});
				cancelMe.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						tempContact.dispose();
					}
					
				});
				
			}
			
		});
	
	exit.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			//displayMessage("Thank you!");
			desktop.dispose();
			}
		});
	New.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			subject.setText(null);
			message.setText(null);
			ekit.setDocumentText(null);
			}
	});
	sendMsg.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			while(progress.getValue() == 100) 
				ekit.disable();
			//progress.setValue(progress.getValue() + 33);
			try {
				progress.setValue(20);
				emailer.setProgress(progress);
				emailer.sendEmail(ekit.getTextPane(), subject.getText());
			} catch (EmailException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}});
	load.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			dblist.connect();
			emailer.setList(dblist.getSendList());
		}
		
	});
	
	login.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			final JFrame tempContact = new JFrame();
			JPanel addPlatform = new JPanel();
			addPlatform.setLayout(new GridLayout(5,2));
			final JTextField names = new JTextField(10);
			final JTextField emails = new JTextField(10);
			final JTextField port = new JTextField(10);
			final JTextField host = new JTextField(10);
			addPlatform.add(new JLabel("User Name:"));
			addPlatform.add(names);
			addPlatform.add(new JLabel("Password:"));
			addPlatform.add(emails);
			addPlatform.add(new JLabel("Host"));
			addPlatform.add(host);
			addPlatform.add(new JLabel("Port"));
			addPlatform.add(port);
			host.setText("smtp.gmail.com");
			port.setText("587");
			
			JButton saveMe = new JButton("Login");
			JButton cancelMe = new JButton("Cancel");
			addPlatform.add(saveMe);
			addPlatform.add(cancelMe);
			tempContact.add(addPlatform);
			tempContact.setSize(400, 180);
			tempContact.setLocationRelativeTo(null);
			tempContact.setVisible(true);
			
			saveMe.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					emailer.setLogin(host.getText(), Integer.valueOf(port.getText()), names.getText(), emails.getText());
					new messagin().displayMessage("Thank you!");
					ekit.enable();
					middle.enable();
					sendMsg.enable();
					subject.enable();
					tempContact.dispose();
				}
				
			});
			cancelMe.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					tempContact.dispose();
				}
				
			});
			
		}
		
	}); 

	}
	
	
	public void setTitle(String T) {
		desktop.setTitle(T);
	}
	
	public int getProgress() {
		return progress.getValue();
	}
	
	public void setProgress(int n) {
		progress.setValue(n);
	}
}



