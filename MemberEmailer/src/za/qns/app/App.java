package za.qns.app;

import za.qns.logic.Contact;
import za.qns.logic.sender;
import za.qns.storage.contactReader;
import za.qns.ui.*;

public class App {

	public static void main(String[] args) {
		Contact contacts = new Contact();
		sender emailer = new sender();
		contactReader readDB = new contactReader();
		
		gui app = new gui(readDB, contacts, emailer);
		
		app.setTitle("Email App");
	}

}
