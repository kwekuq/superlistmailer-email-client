package za.qns.storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JFileChooser;

import za.qns.logic.Contact;
import za.qns.ui.gui;
import za.qns.ui.messagin;

public class contactReader {
	private ArrayList<Contact> list = new ArrayList<Contact>();
	
	private Connection con;
	private Statement st;
	private ResultSet result;
	
	private void addContacts() {
		String sql = "Select * FROM contacts";
		try {
			result = st.executeQuery(sql);
			while(result.next()) {
				Contact C = new Contact();
				C.setName(result.getString("Name"));
				C.setEmail(result.getString("Email"));
				list.add(C);
				//new messagin().displayMessage(D)
				//new gui(null,null,null).setTitle("Loading....");
				//new gui().setProgress(new gui().getProgress() + 1);
			}
		} catch (SQLException e) {
			new messagin().displayMessage(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void connect() {
		String location = "";
		JFileChooser db = new JFileChooser();
		if(db.showOpenDialog(null)== JFileChooser.APPROVE_OPTION) {
			location = db.getSelectedFile().getAbsolutePath();
		}
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		} catch (Exception e) {
			//new gui().displayMessage(e.getMessage());
		}
		try {
			//Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

			   /* String url = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ="+fileName;
			    con = DriverManager.getConnection(url,"","");*/

		/*		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			    String url = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ="+fileName;
			    con = DriverManager.getConnection(url,"","");*/

			
			
			String myDB = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=" + location;
			con = DriverManager.getConnection(myDB,"","");
			st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			addContacts();
		} catch (Exception e) {
			new messagin().displayMessage(e.getMessage());
		}
	}
	
	public ArrayList<Contact> getSendList() {
		return list;
	}
}
