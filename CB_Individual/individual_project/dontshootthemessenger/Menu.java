package dontshootthemessenger;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Menu {
	
	Log log=new Log();
	String timeStamp = new SimpleDateFormat("MM/dd/yyyy  hh:mm:ss").format(new Date());

	public Menu() {}
	
	
	
	//01 USER'S MENU
	public void UserMainMenu(DBMethods dbm,String username) {
		
		User usr=new User(username);
		Scanner UserMenuSC=new Scanner(System.in);
		String UserLogOut="";
		
		String UserMainMenu="\n"+"---------------MAIN MENU-------------------"+"\n"+
                                 "Please Choose One of the following Actions:"+"\n"+"\n"+
		                         "1.View Inbox"+"\n"+
				                 "2.View Sent Messages"+"\n"+
		                         "3.Send New Message"+"\n"+
				                 "4.Delete a Message"+"\n"+
		                         "5.Log Out"+"\n";
		
		System.out.println("\n" + "Welcome " + usr.getUsername()+" !!!"+ "\n" + "\n");
		
		do {
			System.out.println(UserMainMenu);
			String UserMainMenuChoice=UserMenuSC.next();
			
			while(!UserMainMenuChoice.equalsIgnoreCase("1")&&!UserMainMenuChoice.equalsIgnoreCase("2")&&
				  !UserMainMenuChoice.equalsIgnoreCase("3")&&!UserMainMenuChoice.equalsIgnoreCase("4")&&
			      !UserMainMenuChoice.equalsIgnoreCase("5")) {
				System.out.println("Invalid Input"+"\n"+UserMainMenu);
				UserMainMenuChoice=UserMenuSC.next();
			}
			
			switch(UserMainMenuChoice) {
			
			
			    //Case 1.View Inbox
			    case "1":
			    	usr.ViewInbox(dbm);
			    	UserLogOut="y";
			    	break;
			    	
			    //Case 2.View Sent Messages
			    case "2":
			    	usr.ViewSent(dbm);
			    	UserLogOut="y";
			    	break;
			    	
			    //Case 3.Send New Message
			    case "3":
			    	usr.SendNewMessage(dbm);
			    	UserLogOut="y";
			    	break;
			    	
			    //Case 4.Delete a Message
			    case "4":
			    	usr.DeleteMessages(dbm);
			    	UserLogOut="y";
			    	break;
			    	
			    //Case 5.Log Out
			    case "5":
			    	UserLogOut="n";
			    	
			    	//Keeping Log of the action
					Log.appendStrToFile("Dont_Shoot_The_Messenger_Log_File.txt", "User= "+username+" Logged OUT the system at "+timeStamp);
			    	
			    	System.out.println("\n"+"Logging out..."+"\n"+
			    	                         usr.getUsername()+" has successfully Logged Out"+"\n"+
			    	                        "Returning to Login Screen...");
			    	break;
			    	}
			}while(UserLogOut.equalsIgnoreCase("y"));
	}
	
	
	
	//02 ADVANCED USER'S MENU
	public void AdvancedUserMainMenu(DBMethods dbm,String username) {
		
		AdvancedUser advUser=new AdvancedUser(username);
		Scanner AdvancedUserMainMenuSC=new Scanner(System.in);
		String AdvancedUserLogOut="";
		
		String AdvancedUserMainMenu="\n"+"---------------MAIN MENU-------------------"+"\n"+
                                         "Please Choose One of the following Actions:"+"\n"+"\n"+
		                                 "1.View Inbox"+"\n"+
				                         "2.View Sent Messages"+"\n"+
		                                 "3.Send New Message"+"\n"+
				                         "4.Delete a Message"+"\n"+
		                                 "5.Edit a Sent Message"+"\n"+
		                                 "6.Log Out"+"\n";
		
		System.out.println("\n" + "Welcome " + advUser.getUsername()+" !!!"+ "\n" + "\n");
		
		do {
			System.out.println(AdvancedUserMainMenu);
			String AdvancedUserMainMenuChoice=AdvancedUserMainMenuSC.next();
			
			while(!AdvancedUserMainMenuChoice.equalsIgnoreCase("1")&&!AdvancedUserMainMenuChoice.equalsIgnoreCase("2")&&
				  !AdvancedUserMainMenuChoice.equalsIgnoreCase("3")&&!AdvancedUserMainMenuChoice.equalsIgnoreCase("4")&&
			      !AdvancedUserMainMenuChoice.equalsIgnoreCase("5")&&!AdvancedUserMainMenuChoice.equalsIgnoreCase("6")) {
				System.out.println("Invalid Input"+"\n"+AdvancedUserMainMenu);
				AdvancedUserMainMenuChoice=AdvancedUserMainMenuSC.next();
			}
			
			switch(AdvancedUserMainMenuChoice) {
			
			
			    //Case 1.View Inbox
			    case "1":
			    	advUser.ViewInbox(dbm);
			    	AdvancedUserLogOut="y";
			    	break;
			    	
			    //Case 2.View Sent Messages
			    case "2":
			    	advUser.ViewSent(dbm);
			    	AdvancedUserLogOut="y";
			    	break;
			    	
			    //Case 3.Send New Message
			    case "3":
			    	advUser.SendNewMessage(dbm);
			    	AdvancedUserLogOut="y";
			    	break;
			    	
			    //Case 4.Delete a Message
			    case "4":
			    	advUser.DeleteMessages(dbm);
			    	AdvancedUserLogOut="y";
			    	break;
			    	
			    //Case 5.Edit a Sent Message
			    case "5":
			    	advUser.ViewEditSent(dbm);
			    	AdvancedUserLogOut="y";
			    	break;
			    	
			    //Case 6.Log Out
			    case "6":
			    	AdvancedUserLogOut="n";
			    	
			    	//Keeping Log of the action
					Log.appendStrToFile("Dont_Shoot_The_Messenger_Log_File.txt", "User= "+username+" Logged OUT the system at "+timeStamp);
			    	
			    	System.out.println("\n"+"Logging out..."+"\n"+
			    			                advUser.getUsername()+" has successfully Logged Out"+"\n"+
			    	                        "Returning to Login Screen...");
			    	break;
			    	}
			}while(AdvancedUserLogOut.equalsIgnoreCase("y"));
	}
	
	
	
	
	//03 PREMIUM USER'S MENU
	public void PremiumUserMainMenu(DBMethods dbm,String username) {
		
		PremiumUser premUser=new PremiumUser(username);
		Scanner PremiumUserMainMenuSC=new Scanner(System.in);
		String PremiumUserLogOut="";
		
		String PremiumUserMainMenu="\n"+"---------------MAIN MENU-------------------"+"\n"+
				                        "Please Choose One of the following Actions:"+"\n"+"\n"+
		                                "1.View Inbox"+"\n"+
				                        "2.View Sent Messages"+"\n"+
		                                "3.Send New Message"+"\n"+
				                        "4.Delete a Message"+"\n"+
		                                "5.Edit a Sent Message"+"\n"+
				                        "6.Clear Delete a Sent Message"+"\n"+
		                                "7.Log Out"+"\n";
		
		System.out.println("\n" + "Welcome " + premUser.getUsername()+" !!!"+ "\n" + "\n");
		
		do {
			System.out.println(PremiumUserMainMenu);
			String PremiumUserMainMenuChoice=PremiumUserMainMenuSC.next();
			
			while(!PremiumUserMainMenuChoice.equalsIgnoreCase("1")&&!PremiumUserMainMenuChoice.equalsIgnoreCase("2")&&
				  !PremiumUserMainMenuChoice.equalsIgnoreCase("3")&&!PremiumUserMainMenuChoice.equalsIgnoreCase("4")&&
			      !PremiumUserMainMenuChoice.equalsIgnoreCase("5")&&!PremiumUserMainMenuChoice.equalsIgnoreCase("6")&&
			      !PremiumUserMainMenuChoice.equalsIgnoreCase("7")) {
				System.out.println("Invalid Input"+"\n"+PremiumUserMainMenu);
				PremiumUserMainMenuChoice=PremiumUserMainMenuSC.next();
			}
			
			switch(PremiumUserMainMenuChoice) {
			
			
			    //Case 1.View Inbox
			    case "1":
			    	premUser.ViewInbox(dbm);
			    	PremiumUserLogOut="y";
			    	break;
			    	
			    //Case 2.View Sent Messages
			    case "2":
			    	premUser.ViewSent(dbm);
			    	PremiumUserLogOut="y";
			    	break;
			    	
			    //Case 3.Send New Message
			    case "3":
			    	premUser.SendNewMessage(dbm);
			    	PremiumUserLogOut="y";
			    	break;
			    	
			    //Case 4.Delete a Message
			    case "4":
			    	premUser.DeleteMessages(dbm);
			    	PremiumUserLogOut="y";
			    	break;
			    	
			    //Case 5.Edit a Sent Message
			    case "5":
			    	premUser.ViewEditSent(dbm);
			    	PremiumUserLogOut="y";
			    	break;
			    	
			    //Case 6.Clear Delete a Sent Message
			    case "6":
			    	premUser.ViewCleanDeleteSent(dbm);
			    	PremiumUserLogOut="y";
			    	break;
			    	
			    //Case 7.Log Out
			    case "7":
			    	PremiumUserLogOut="n";
			    	
			    	//Keeping Log of the action
					Log.appendStrToFile("Dont_Shoot_The_Messenger_Log_File.txt", "User= "+username+" Logged OUT the system at "+timeStamp);
			    	
			    	System.out.println("\n"+"Logging out..."+"\n"+
			    			                premUser.getUsername()+" has successfully Logged Out"+"\n"+
			    	                        "Returning to Login Screen...");
			    	break;
			    	}
			}while(PremiumUserLogOut.equalsIgnoreCase("y"));
	}
	
	
	
	//04 ADMINISTRATOR'S MENU
	public void AdministratorMainMenu(DBMethods dbm,String username) {

		Administrator adm = new Administrator(username);
		Scanner AdministratorMainMenuSC = new Scanner(System.in);
		String AdministratorLogOut = "";

		String AdministratorMainMenu = "\n"+"----------------MAIN MENU------------------"+"\n"+
				                            "Please Choose One of the following Actions:"+"\n"+"\n"+
		                                    "1.View Inbox" + "\n" + 
				                            "2.View Sent Messages"+ "\n" + 
		                                    "3.Send New Message" + "\n" + 
				                            "4.Delete a Message" + "\n" + 
		                                    "5.Edit a Sent Message" + "\n"+
				                            "6.Clear Delete a Sent Message" + "\n" + 
		                                    "7.View All User Info" + "\n" + 
				                            "8.Create New User" + "\n"+
				                            "9.Update User Info" + "\n" + 
				                            "10.Delete a User" + "\n" + 
				                            "11.View-Edit-Delete any Message" + "\n"+
				                            "12.Log Out" + "\n";
		
		System.out.println("\n" + "Welcome " + adm.getUsername()+" !!!"+ "\n" + "\n");
        
		do {
			System.out.println(AdministratorMainMenu);
			String AdministratorMainMenuChoice = AdministratorMainMenuSC.next();

			while (!AdministratorMainMenuChoice.equalsIgnoreCase("1")
					&& !AdministratorMainMenuChoice.equalsIgnoreCase("2")
					&& !AdministratorMainMenuChoice.equalsIgnoreCase("3")
					&& !AdministratorMainMenuChoice.equalsIgnoreCase("4")
					&& !AdministratorMainMenuChoice.equalsIgnoreCase("5")
					&& !AdministratorMainMenuChoice.equalsIgnoreCase("6")
					&& !AdministratorMainMenuChoice.equalsIgnoreCase("7")
					&& !AdministratorMainMenuChoice.equalsIgnoreCase("8")
					&& !AdministratorMainMenuChoice.equalsIgnoreCase("9")
					&& !AdministratorMainMenuChoice.equalsIgnoreCase("10")
					&& !AdministratorMainMenuChoice.equalsIgnoreCase("11")
					&& !AdministratorMainMenuChoice.equalsIgnoreCase("12")) {
				System.out.println("Invalid Input" + "\n" + AdministratorMainMenu);
				AdministratorMainMenuChoice = AdministratorMainMenuSC.next();
			}

			switch (AdministratorMainMenuChoice) {

			// Case 1.View Inbox
			case "1":
				adm.ViewInbox(dbm);
				AdministratorLogOut = "y";
				break;

			// Case 2.View Sent Messages
			case "2":
				adm.ViewSent(dbm);
				AdministratorLogOut = "y";
				break;

			// Case 3.Send New Message
			case "3":
				adm.SendNewMessage(dbm);
				AdministratorLogOut = "y";
				break;

			// Case 4.Delete a Message
			case "4":
				adm.DeleteMessages(dbm);
				AdministratorLogOut = "y";
				break;

			// Case 5.Edit a Sent Message
			case "5":
				adm.ViewEditSent(dbm);
				AdministratorLogOut = "y";
				break;

			// Case 6.Clear Delete a Sent Message
			case "6":
				adm.ViewCleanDeleteSent(dbm);
				AdministratorLogOut = "y";
				break;

			// Case 7.View All User Info
			case "7":
				adm.ViewAllUserInfo(dbm);
				AdministratorLogOut = "y";
				break;

			// Case 8.Create New User
			case "8":
				adm.CreateNewUser(dbm);
				AdministratorLogOut = "y";
				break;

			// Case 9.Update User Info
			case "9":
				adm.UpdateUserInfo(dbm);
				AdministratorLogOut = "y";
				break;

			// Case 10.Delete a User
			case "10":
				adm.DeleteUser(dbm);
				AdministratorLogOut = "y";
				break;

			// Case 11.View-Edit-Delete any Message
			case "11":
				adm.EditDeleteAllMessages(dbm);
				AdministratorLogOut = "y";
				break;

			// Case 12.Log Out
			case "12":
				AdministratorLogOut = "n";
				
				
				//Keeping Log of the action
				Log.appendStrToFile("Dont_Shoot_The_Messenger_Log_File.txt", "User= "+username+" Logged OUT the system at "+timeStamp);
				
				
				System.out.println("\n" + "Logging out..." + "\n" + adm.getUsername() + " has successfully Logged Out"
						+ "\n" + "Returning to Login Screen...");
				break;
			}
		} while (AdministratorLogOut.equalsIgnoreCase("y"));
	}
	
	
	
	//05 LOGIN AND MAIN MENU
	public void LoginMainMenu(DBMethods dbm) {
		
		Scanner LoginMainMenuSC=new Scanner(System.in);
		String ExitLoginMenuChoice="";
		String Class="";
		String MainUserUrl="";
		String MainUserUsername="";
		String MainUserPassword="";
		
		String LoginMainMenu="\n"+"Welcome To DON'T SHOOT THE MESSENGER MAIL"+"\n"+
		                          "please Choose One of the following Actions:"+"\n"+"\n"+
				                  "1.Login"+"\n"+
				                  "2.Exit Program"+"\n";
		
		
		//URL Configuration (for the main user log in)
		System.out.println("Give me the URL of your server or type 'D'"+"\n"
		                  +"to load default (jdbc:mysql://localhost:3306?autoReconnect=true&useSSL=false): ");
		
		String urlOption = LoginMainMenuSC.next();
		
		if (urlOption.equalsIgnoreCase("D")) {
			MainUserUrl = "jdbc:mysql://localhost:3306?autoReconnect=true&useSSL=false";
			} 
		else {
			MainUserUrl = urlOption;
			}
		
		//USERNAME CONFIGURATION (for the main user log in)
		System.out.println("Give me the USERNAME of your server account or type 'D' to load default(root)");
		
		String usernameOption = LoginMainMenuSC.next();

		if (urlOption.equalsIgnoreCase("D")) {
			MainUserUsername = "root";
			} 
		else {
			MainUserUsername = usernameOption;
			}
		
		//PASSWORD CONFIGURATION (for the main user log in)
		System.out.println("Give me the PASSWORD of your server account or type 'D' to load default");
		
		String passwordOption = LoginMainMenuSC.next();
		
		if (urlOption.equalsIgnoreCase("D")) {
			MainUserPassword = "yF1q37$f98&!";    // yF1q37$f98&!    // Gt2!d7h3a&F85$
			} 
		else {
			MainUserPassword = passwordOption;
			}
		
		//Establishing connection
		dbm.connect(MainUserUrl,MainUserUsername,MainUserPassword);
		
		//Creating 'dont_shoot_the_messenger' DataBase
		dbm.CreateEmptyDB();
		
		//Populating 'dont_shoot_the_messenger' Database
		dbm.PopulateEmptyDB();
		
		do {
			try {
			System.out.println(LoginMainMenu);
			String LoginMenuChoice=LoginMainMenuSC.next();
			
			while(!LoginMenuChoice.equals("1")&&!LoginMenuChoice.equals("2")) {
				System.out.println("Invalid Input"+"\n"+LoginMainMenu);
				LoginMenuChoice=LoginMainMenuSC.next();
			}
			
			switch(LoginMenuChoice) {
			
			    //Case 1.Login
			    case "1":
			    	System.out.println("Please type in your Username:");
			    	String username=LoginMainMenuSC.next();
			    	
			    	//Replacing the " and ' characters in username with /
			    	//in order to prevent syntax error when executing the Query
			    	username=username.replace('"','/');
			    	username=username.replaceAll("'","/");
			    	
			    	System.out.println("\n"+"Please type in your Password:");
			    	String password=LoginMainMenuSC.next();
			    	
			    	//Replacing the " and ' characters in password with /
			    	//in order to prevent syntax error when executing the Query
			    	password=password.replace('"','/');
			    	password=password.replaceAll("'","/");
			    	
			    	String CheckIfUserExistsQuery="SELECT users.classification AS 'Class' FROM dont_shoot_the_messenger.users"+"\n"+
			    	                              "WHERE users.username='"+username+"' AND users.password='"+password+"'";
			    	
						
						Statement stm = dbm.getConnection().createStatement();

						ResultSet rs = stm.executeQuery(CheckIfUserExistsQuery);

						System.out.print("\n");

						if (rs.next() == false) {

							System.out.println("There are is no such User in the System!!!" + "\n" + "\n" + "Returning to Previous Menu...");
						} 
						else {
							
							//Keeping Log of the action
							Log.appendStrToFile("Dont_Shoot_The_Messenger_Log_File.txt", "User= "+username+" Logged in the system at "+timeStamp);
							Class = rs.getString("Class");
							
							switch(Class) {
					    	
				    	    //Case 1.User
				    	    case "User":
				    	    	UserMainMenu(dbm,username);
				    	    	break;
				    	    	
				    	    //Case 2.Advanced User
				    	    case "Advanced User":
				    	    	AdvancedUserMainMenu(dbm,username);
				    	    	break;
				    	    	
				    	    //Case 3.Premium User
				    	    case "Premium User":
				    	    	PremiumUserMainMenu(dbm,username);
				    	    	break;
				    	    	
				    	    //Case 4.Administrator
				    	    case "Administrator":
				    	    	AdministratorMainMenu(dbm,username);
				    	    	break;
				    	    	}
							
							ExitLoginMenuChoice="y";
					    	break;
							}
						ExitLoginMenuChoice="y";
						break;
				//Case 2.Exit Program
			    case "2":
			    	ExitLoginMenuChoice="n";
			    	System.out.println("Closing Software..."+"\n"+"\n"+"Thank you for using DON'T SHOOT THE MESSAGER MAIL Inc.!!!");
			    	break;
			    	}
			}
			catch(Exception e) {
			    		//e.printStackTrace();
				}
			
		}while(ExitLoginMenuChoice.equals("y"));
		
		
		
	}
	

}
