package dontshootthemessenger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class User {
	
	private String username=null;
	private String password=null;
	Log log=new Log();
	String timeStamp = new SimpleDateFormat("MM/dd/yyyy  hh:mm:ss").format(new Date());
	
	public User() {}
	
	public User(String username) {
		this.username=username;
	}
	
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	
	//01
	//METHOD THAT SHOWS RECEIVED MESSAGES 
	//Case 1.ONE BY ONE
	//Case 2.ALL TOGETHER
	//BASED ON USER'S CHOICE
	public void ShowReceivedMessages(DBMethods dbm, String query, String choice) {

		Scanner ShowReceivedMessagesSC = new Scanner(System.in);

		try {
			Statement stm2 = dbm.getConnection().createStatement();

			ResultSet rs1 = stm2.executeQuery(query);

			System.out.print("\n");

			//Message that is shown in case that there are no messages in the INBOX
			if (rs1.next() == false) {
				System.out.println("You dont have any messages!!!"+"\n"+"\n"+"Returning to Inbox Menu...");
			} else {
				String answer1 = null;

				switch (choice) {

				//Case 1.View Messages One by One
				case "1":

					//Do...while Loop that shows each message awaiting for confirmation to continue
					do {
						int Message_id = rs1.getInt("Message id");
						String Sender = rs1.getString("Sender");
						String Message = rs1.getString("Message");
						String Time_Sent = rs1.getString("Time Sent");
						//Showing the messages
						System.out.println("Sender     : " + Sender + "\n" + 
						                   "Message    : " + Message + "\n" +
								           "Time Sent  : " + Time_Sent + "\n");

						//Query that Updates the 'view' value of the current message from 'New' to 'Read'
						String query2 = "UPDATE dont_shoot_the_messenger.messages" + "\n" + 
						                "SET messages.viewed='Read'" + "\n" + 
								        "WHERE messages.id=" + Message_id + ";";

						dbm.executeStatement(query2);

						//Asking whether or not to show the next message
						System.out.println("Show next (y/n) ?: ");
						answer1 = ShowReceivedMessagesSC.next();
						System.out.print("\n");
						
						//While Loop for preventing Wrong Input
						while(!answer1.equals("y")&&!answer1.equals("n")) {
							System.out.println("Invalid input"+"\n"+"Show next (y/n) ?: ");
							answer1=ShowReceivedMessagesSC.next();
						}

					} while (rs1.next() && answer1.equalsIgnoreCase("y"));
					System.out.println("Nothing more to show" + "\n" + "Returning to Inbox Menu");
					break;

				//Case 2.View All Messages Together
				case "2":
					
					//Showing All User's Info Headings in a certain format
					System.out.printf("%-30s %-30s %-235s","[Sender]","[Time Sent]","[Message Text]");
					System.out.println("\n"+"\n");

					//Do...while Loop that shows each message Without waiting for confirmation
					do {
						int Message_id = rs1.getInt("Message id");
						String Sender = rs1.getString("Sender");
						String Message = rs1.getString("Message");
						String Time_Sent = rs1.getString("Time Sent");
						
						//Showing all the messages info in a certain format
						System.out.printf("%-30s %-30s %-235s",Sender,Time_Sent,Message);
						System.out.println("\n");
						
						//Query that Updates the 'view' value of each message from 'New' to 'Read'
						String query2 = "UPDATE dont_shoot_the_messenger.messages" + "\n" + 
						                "SET messages.viewed='Read'" + "\n" + 
								        "WHERE messages.id=" + Message_id + ";";

						dbm.executeStatement(query2);

					} while (rs1.next());
					System.out.println("Nothing more to show" + "\n" + "Returning to Inbox Menu");
					break;
				}
			}
		} catch (SQLException e) {
			//e.printStackTrace();
		}
	}
	
	
	
	//02
	//METHOD THAT SHOWS 
	//Case 1.THE USER'S NEW INBOX 
	//Case 2.ALL THE USER'S INBOX
	//BASED ON USER'S CHOICE
	public void ViewInbox(DBMethods dbm) {
		String menu1answer=null;
		String menu2answer=null;
		String ExitToMainMenuChoice=null;
		Scanner ViewInboxSC=new Scanner(System.in);
			
		
		do{
			//INBOX Menu Structure
			String menu1="\n"+"---------------INBOX MENU------------------"+"\n"+
		                      "Please select one of the following actions:"+"\n"+
			                  "1.View New Messages"+"\n"+
					          "2.View All Messages"+"\n"+
			                  "3.Exit to Main Menu"+"\n";
			System.out.println(menu1);
			menu1answer=ViewInboxSC.next();
			
			//While Loop for preventing Wrong Input
			while(!menu1answer.equals("1")&&!menu1answer.equals("2")&&!menu1answer.equals("3")) {
				System.out.println("Invalid input"+"\n"+menu1);
				menu1answer=ViewInboxSC.next();
			}
			
			//Query parts that if executed in different combinations
			//Shows 'New' or 'Read' Messages
			String queryPart1="SELECT messages.id AS 'Message id',messages.text AS 'Message',messages.date_time_sent AS 'Time Sent',(SELECT users.username FROM dont_shoot_the_messenger.users WHERE users.id=messages.sender_id) AS 'Sender'"+"\n"+
			                  "FROM dont_shoot_the_messenger.messages"+"\n"+
			                  "WHERE messages.receiver_id=("+"\n"+
		                      "SELECT users.id"+"\n"+
			                  "FROM dont_shoot_the_messenger.users"+"\n"+
			                  "WHERE users.username='"+getUsername()+"'"+"\n"+
			                  ") ";
			
			String queryPart2=" AND messages.receiver_view=0"+"\n"+
			                  "ORDER BY date_time_sent DESC;";
			
			String queryfinal=null;
			
			//Menu String that is identical for NEW or ALL MESSAGES Menu
			String menu2="Please select one of the following actions:"+"\n"+
                         "1.View Messages One by One"+"\n"+
		                 "2.View All Messages Together"+"\n"+
                         "3.Exit to Inbox Menu"+"\n";
			
			switch(menu1answer) {
			
			    //Case 1.View New Messages
			    case "1":
			    	//Combination of the above Queries that will show ONLY New Messages
			    	queryfinal=queryPart1+"AND messages.viewed='New'"+queryPart2;
			    	//New Messages Menu Structure
			    	System.out.println("\n"+"--------NEW MESSAGES INBOX MENU------------"+"\n"+menu2);
					menu2answer=ViewInboxSC.next();
					//While Loop for preventing Wrong Input
					while(!menu2answer.equals("1")&&!menu2answer.equals("2")&&!menu2answer.equals("3")) {
						System.out.println("Invalid input"+"\n"+menu1);
						menu2answer=ViewInboxSC.next();
						}
					switch(menu2answer) {
					
					    //Case 1.1.View  Messages One by One
					    case "1":
					    	ShowReceivedMessages(dbm,queryfinal,"1");
					    	ExitToMainMenuChoice="y";
					    	break;
					    	
					    //Case 1.2.View All Messages Together
					    case "2":
					    	ShowReceivedMessages(dbm,queryfinal,"2");
					    	ExitToMainMenuChoice="y";
					    	break;
					    	
					    //Case 1.3.Exit to INBOX Menu
					    case "3":
					    	System.out.println("\n"+"Returning to Inbox Menu...");
					    	ExitToMainMenuChoice="y";
					    	break;
					    	}
			    	break;
			    
			    //Case 2.View All Messages
			    case "2":
			    	//Combination of the above Queries that will show ALL Messages New and Read
			    	queryfinal=queryPart1+queryPart2;
			    	//ALL Messages Menu Structure
			    	System.out.println("\n"+"--------ALL MESSAGES INBOX MENU------------"+"\n"+menu2);
					menu2answer=ViewInboxSC.next();
					//While Loop for preventing Wrong Input
					while(!menu2answer.equals("1")&&!menu2answer.equals("2")&&!menu2answer.equals("3")) {
						System.out.println("Invalid input"+"\n"+menu1);
						menu2answer=ViewInboxSC.next();
						}
					switch(menu2answer) {
					
				        //Case 2.1.View  Messages One by One
				        case "1":
				        	ShowReceivedMessages(dbm,queryfinal,"1");
				        	ExitToMainMenuChoice="y";
				        	break;
				        	
				        //Case 2.2.View All Messages Together
				        case "2":
				        	ShowReceivedMessages(dbm,queryfinal,"2");
				        	ExitToMainMenuChoice="y";
				        	break;
				        	
				        //Case 2.3.Exit to INBOX Menu
				        case "3":
				        	System.out.println("\n"+"Returning to Inbox Menu...");
				        	ExitToMainMenuChoice="y";
				        	break;
				        	}
					break;
			    	
			    //Case 3.Exit to Main Menu
			    case "3":
			    	System.out.println("Exiting to Main Menu...");
			    	ExitToMainMenuChoice="n";
			    	break;
			    	}
			//While Loop for keeping User to the INBOX Menu Unless he/she chooses 'Case 3.Exit to Main Menu'
			}while(!ExitToMainMenuChoice.equalsIgnoreCase("n"));
		}
	
	
	
	//03
	//METHOD THAT SHOWS 
	//Case 1.USER'S SENT MESSAGES ONE BY ONE 
	//Case 2.ALL USER'S SENT MESSAGES TOGETHER 
	//BASED ON USER'S CHOICE
	public void ShowSentMessages(DBMethods dbm, String choice) {

		Scanner ShowSentMessagesSC = new Scanner(System.in);
		
		//Query that will show the Sent messages
		String queryfinal1="SELECT (SELECT users.username FROM dont_shoot_the_messenger.users WHERE users.id=messages.receiver_id) AS 'Receiver',messages.text AS 'Message',messages.date_time_sent AS 'Time Sent'"+"\n"+
			               "FROM dont_shoot_the_messenger.messages"+"\n"+
				           "WHERE messages.sender_id=("+"\n"+
				           "SELECT users.id"+"\n"+
				           "FROM dont_shoot_the_messenger.users"+"\n"+
				           "WHERE users.username='"+getUsername()+"'"+"\n"+
				           ") AND messages.sender_view=0"+"\n"+
				           "ORDER BY date_time_sent DESC;";

		try {
			Statement stm4 = dbm.getConnection().createStatement();

			ResultSet rs2 = stm4.executeQuery(queryfinal1);

			System.out.print("\n");

			//Message that is shown in case that there are no SENT messages
			if (rs2.next() == false) {
				System.out.println("You dont have any Sent messages!!!"+"\n"+"\n"+"Returning to Sent Messages Menu...");
			} else {
				String answer1 = null;

				switch (choice) {

				//Case 1.View Sent Messages One by One
				case "1":

					//Do...while Loop that shows each message awaiting for confirmation to continue
					do {
						String Receiver = rs2.getString("Receiver");
						String Message = rs2.getString("Message");
						String Time_Sent = rs2.getString("Time Sent");
						//Showing the messages
						System.out.println("Receiver   : " + Receiver + "\n" + 
						                   "Message    : " + Message + "\n" +
								           "Time Sent  : " + Time_Sent + "\n");

						//Asking whether or not to show next message
						System.out.println("Show next (y/n) ?: ");
						answer1 = ShowSentMessagesSC.next();
						System.out.print("\n");
						
						//While Loop for preventing Wrong Input
						while(!answer1.equals("y")&&!answer1.equals("n")) {
							System.out.println("Invalid input"+"\n"+"Show next (y/n) ?: ");
							answer1=ShowSentMessagesSC.next();
						}

					} while (rs2.next() && !answer1.equalsIgnoreCase("n"));
					System.out.println("Nothing more to show" + "\n" + "Returning to Sent Messages Menu...");
					break;

				//Case 2.View All Sent Messages Together
				case "2":

					//Showing All User's Info Headings in a certain format
					System.out.printf("%-30s %-30s %-235s","[Receiver]","[Time Sent]","[Message Text]");
					System.out.println("\n"+"\n");
					
					//Do...while Loop that shows each message Without waiting for confirmation
					do {
						String Receiver = rs2.getString("Receiver");
						String Message = rs2.getString("Message");
						String Time_Sent = rs2.getString("Time Sent");
						
						//Showing all the messages info in a certain format
						System.out.printf("%-30s %-30s %-235s",Receiver,Time_Sent,Message);
						System.out.println("\n");
						
					} while (rs2.next());
					System.out.println("Nothing more to show" + "\n" + "Returning to Sent Messages Menu...");
					break;
				}
			}
		} catch (SQLException e) {
			//e.printStackTrace();
		}
	}
	
	
	
	//04
	//METHOD FOR MENU THAT SHOWS 
	//Case 1.THE USER'S SENT MESSAGES ONE BY ONE
	//Case 2.ALL THE USER'S MESSAGES TOGETHER
    //BASED ON USER'S CHOICE (USING THE ABOVE METHOD)
	public void ViewSent(DBMethods dbm) {
		String menu1answer=null;
		String ExitToMainMenuChoice=null;
		Scanner ViewSentSC=new Scanner(System.in);
			
		
		do{
			//SENT MESSAGES Menu Structure
			String menu1="\n"+"-----------SENT MESSAGES MENU--------------"+"\n"+
		                      "Please select one of the following actions:"+"\n"+
		                      "1.View Sent Messages One by One"+"\n"+
				              "2.View All Sent Messages Together"+"\n"+
		                      "3.Exit to Main Menu"+"\n";
			
			System.out.println(menu1);
			menu1answer=ViewSentSC.next();
			//While Loop for preventing Wrong Input
			while(!menu1answer.equals("1")&&!menu1answer.equals("2")&&!menu1answer.equals("3")) {
				System.out.println("Invalid input"+"\n"+menu1);
				menu1answer=ViewSentSC.next();
			}
			

			switch(menu1answer) {
			
			    //Case 1.View Sent Messages One by One
			    case "1":
			    	ShowSentMessages(dbm,"1");
			    	ExitToMainMenuChoice="y";
			    	break;
			    
			    //Case 2.View All Sent Messages Together
			    case "2":
			    	ShowSentMessages(dbm,"2");
			    	ExitToMainMenuChoice="y";
					break;
			    	
			    //Case 3.Exit to Main Menu
			    case "3":
			    	System.out.println("Exiting to Main Menu...");
			    	ExitToMainMenuChoice="n";
			    	break;
			    	}
			//Do...While Loop for keeping User to the SENT MESSAGES Menu Unless he/she chooses 'Case 3.Exit to Main Menu'
			}while(!ExitToMainMenuChoice.equalsIgnoreCase("n"));
		}	



	//05
	// METHOD THAT ENBLES THE USER TO SENT MESSAGES TO OTHER USERS
	public void SendNewMessage(DBMethods dbm) {
		
		Scanner SendNewMessageSC=new Scanner(System.in);
		String ReceiverUsername=null;
		String ContinueSendMessageAnswer="";
		String MessageSendAnswer="";
		String MessageText="";
		//String ContinueSendingMessages="";
		boolean b1=true;
		
		
		try {
			
			do {
				//SEND NEW MESSAGE MENU Structure
				System.out.println("\n"+"-----------SEND NEW MESSAGE------------"+"\n"+
			                            "Please type in the receiver's username:");
				ReceiverUsername=SendNewMessageSC.next();
				
				//Query that searches the DB to find out if the username exists in the 'users' TABLE
				String query4="SELECT users.username AS 'Username'"+"\n"+
		                      "FROM dont_shoot_the_messenger.users"+"\n"+
				              "WHERE users.username='"+ReceiverUsername+"'"+"\n"+
		                      "LIMIT 1;";
				
				Statement stm4 = dbm.getConnection().createStatement();
				ResultSet rs3 = stm4.executeQuery(query4);
				
				System.out.print("\n");
				
				//Returns FALSE if the username doesn't exist
				b1=rs3.next();
				
				//Case that the username does NOT exist in the 'users' Table
				if (b1==false) {
					System.out.println("User not found"+"\n");
					
					System.out.println("Do you want to continue sending messages? (y/n): ");
					ContinueSendMessageAnswer=SendNewMessageSC.next();
					
					//While Loop for preventing Wrong Input
					while(!ContinueSendMessageAnswer.equalsIgnoreCase("y")&&!ContinueSendMessageAnswer.equalsIgnoreCase("n")) {
						System.out.println("Invalid input"+"\n"+
					                       "Do you want to continue sending messages? (y/n): ");
						ContinueSendMessageAnswer=SendNewMessageSC.next();
						}
					}
				
				//Case that the username Exists in the 'users' Table
				else {
					System.out.println("Please type in the message you want to send (250 characters max): ");
					
					//Way to put the whole message to a string called 'MessageText'
					MessageText=SendNewMessageSC.next();
					MessageText=MessageText+SendNewMessageSC.nextLine();
					
					//Replacing the " and ' characters from MessageText with /
					MessageText=MessageText.replace('"','/');
					MessageText=MessageText.replaceAll("'","/");
					
					//Asking for confirmation to send the message
					System.out.println("Do you want to continue sending the message? (y/n): ");
					MessageSendAnswer=SendNewMessageSC.next();
					
					//While Loop for preventing Wrong Input
					while(!MessageSendAnswer.equalsIgnoreCase("y")&&!MessageSendAnswer.equalsIgnoreCase("n")) {
						System.out.println("Invalid input"+"\n"+
								           "Do you want to continue sending the message? (y/n): ");
						MessageSendAnswer=SendNewMessageSC.next();
						}
					
					switch(MessageSendAnswer) {
					
					//Case that the user wants to send the message
					case "y":
						
						//Query that inserts all message data to 'messages' Table
						String query5="INSERT INTO `dont_shoot_the_messenger`.`messages`"+"\n"+
							          "(`sender_id`, `receiver_id`, `text`)"+"\n"+
							          "VALUES"+"\n"+
							          "("+"\n"+
							          "(SELECT users.id"+"\n"+
							          "FROM dont_shoot_the_messenger.users"+"\n"+
							          "WHERE users.username='"+getUsername()+"'"+"\n"+
							          "LIMIT 1),"+
							          "(SELECT users.id"+"\n"+
							          "FROM dont_shoot_the_messenger.users"+"\n"+
							          "WHERE users.username='"+ReceiverUsername+"'"+"\n"+
							          "LIMIT 1),"+
							          "'"+MessageText+"');";
						
						System.out.println("Sending Message to "+ReceiverUsername+" ..."+"\n");
						
						dbm.executeStatement(query5);
						
						System.out.print("Message Sent"+"\n");
						
						//Keeping Log of the action
						Log.appendStrToFile("Dont_Shoot_The_Messenger_Log_File.txt", getUsername()+
								            " Sent a Message To: "+ReceiverUsername+" at "+timeStamp+"  Message text: "+MessageText);
						
						break;
					
					//Case that the user does NOT want to send the message
					case "n":
						System.out.println("Canceling the sending of the message..."+"\n"+"Sent Canceled");                   
						break;
					}
					
					System.out.println("Do you want to continue sending messages? (y/n): ");
					ContinueSendMessageAnswer=SendNewMessageSC.next();
					
					//While Loop for preventing Wrong Input
					while(!ContinueSendMessageAnswer.equalsIgnoreCase("y")&&!ContinueSendMessageAnswer.equalsIgnoreCase("n")) {
						System.out.println("Invalid input"+"\n"+
					                       "Do you want to continue sending messages? (y/n): ");
						ContinueSendMessageAnswer=SendNewMessageSC.next();
						}
				}
				
				//Do...While Loop for keeping User to the SEND NEW MESSAGE Menu Unless he/she chooses 'n'
				}while(ContinueSendMessageAnswer.equals("y"));
			
			System.out.println("\n"+"Returning to Main Menu..."+"\n");
			}
		
		catch (SQLException e) {
			//e.printStackTrace();
			}
		
		}
	
	
	
	//06
	//METHOD THAT DELETES(HIDES)
	//Case 1.RECEIVED READ MESSAGES ONE BY ONE
	//Case 2.ALL RECEIVED READ MESSAGES TOGETHER
	//BASED ON USER'S CHOICE
	public void DeleteReadMessages(DBMethods dbm, String choice) {

		Scanner DeleteReadMessagesSC = new Scanner(System.in);
		
		//Query that Shows the Received Messages that are Read(NOT New)
		String queryDeleteRead="SELECT messages.id AS 'Message id',messages.text AS 'Message',messages.date_time_sent AS 'Time Sent',(SELECT users.username FROM dont_shoot_the_messenger.users WHERE users.id=messages.sender_id) AS 'Sender'"+"\n"+
                "FROM dont_shoot_the_messenger.messages"+"\n"+
                "WHERE messages.receiver_id=("+"\n"+
                "SELECT users.id"+"\n"+
                "FROM dont_shoot_the_messenger.users"+"\n"+
                "WHERE users.username='"+getUsername()+"'"+"\n"+
                ") "+"\n"+
                "AND messages.viewed='Read'"+"\n"+
                "AND messages.receiver_view=0"+"\n"+
                "ORDER BY date_time_sent DESC;";

		try {
			Statement stm2 = dbm.getConnection().createStatement();

			ResultSet rs1 = stm2.executeQuery(queryDeleteRead);

			System.out.print("\n");

			//Message that is shown in case that there are no READ messages in the INBOX
			if (rs1.next() == false) {
				System.out.println("You dont have any Read messages!!!"+"\n"+"\n"+"Returning to Delete Message Menu...");
			} else {
				String answer1 = null;

				switch (choice) {

				//Case 1.Delete Read Messages One by One
				case "1":

					//Do...while Loop that shows each received Read message
					//Asks whether to Delete (Hide) it or not
					//And waits for confirmation to continue to the next or not
					do {
						int Message_id = rs1.getInt("Message id");
						String Sender = rs1.getString("Sender");
						String Message = rs1.getString("Message");
						String Time_Sent = rs1.getString("Time Sent");
						//Showing the messages
						System.out.println("Sender     : " + Sender + "\n" + 
						                   "Message    : " + Message + "\n" +
								           "Time Sent  : " + Time_Sent + "\n");
						
						//Asking whether or not to Delete current message
						System.out.println("Delete current message (y/n) ?: ");
						String deleteAnswer=DeleteReadMessagesSC.next();
						System.out.print("\n");
						
						//While Loop for preventing Wrong Input
						while(!deleteAnswer.equals("y")&&!deleteAnswer.equals("n")) {
							System.out.println("Invalid input"+"\n"+"Delete current message (y/n) ?: ");
							deleteAnswer=DeleteReadMessagesSC.next();
						}
						
						switch (deleteAnswer){
						
						    //Case that the user wants to Delete the current message
						    case "y":
						    	//Query that changes the receiver_view value from '0' to '1'
						    	String queryDelete="UPDATE dont_shoot_the_messenger.messages"+"\n"+
						                           "SET messages.receiver_view=1"+"\n"+
						    			           "WHERE messages.id="+Message_id+";";
						    	
						    	dbm.executeStatement(queryDelete);
						    	
						    	System.out.println("\n"+"Deleting current message..."+"\n"+"Message Deleted"+"\n");
						    	
						    	//Keeping Log of the action
								Log.appendStrToFile("Dont_Shoot_The_Messenger_Log_File.txt", getUsername()+
										            " Hid a Message that "+Sender+" had sent him at "+timeStamp+"  Message text: "+Message);
						    	
						    	
						    	break;
						    	
						    //Case that the user does NOT want to Delete the current message
						    case "n":
						    	System.out.println("\n"+"Skipping Read message Deletion..."+"\n");
						    	break;
						}
						
						//Asking whether or not to show next message
						System.out.println("Show next (y/n) ?: ");
						answer1 = DeleteReadMessagesSC.next();
						System.out.print("\n");
						
						//While Loop for preventing Wrong Input
						while(!answer1.equals("y")&&!answer1.equals("n")) {
							System.out.println("Invalid input"+"\n"+"Delete current message (y/n) ?: ");
							answer1=DeleteReadMessagesSC.next();
						}

					} while (rs1.next() && !answer1.equalsIgnoreCase("n"));
					System.out.println("Nothing more to show" + "\n" + "Returning to Delete Message Menu");
					break;

				//Case 2.Delete All Received Read Messages Together
				case "2":
					
					System.out.println("\n"+"Are you sure you want to Delete all your Read messages? (y/n):");
					String DeleteAllReadChoice=DeleteReadMessagesSC.next();
					
					//While Loop for preventing Wrong Input
					while(!DeleteAllReadChoice.equals("y")&&!DeleteAllReadChoice.equals("n")) {
						System.out.println("Invalid input"+"\n"+"Are you sure you want to Delete all your Read messages? (y/n):");
						DeleteAllReadChoice=DeleteReadMessagesSC.next();
					}
					
					switch(DeleteAllReadChoice) {
					
					//Case 1.The User wants to Delete all the Read Messages
					case "y":

					//Do...while Loop that Deletes (Hides) all Received Read messages
					do {
						int Message_id = rs1.getInt("Message id");
						String Sender = rs1.getString("Sender");
						String Message = rs1.getString("Message");
						String Time_Sent = rs1.getString("Time Sent");
						
						
						String query2 = "UPDATE dont_shoot_the_messenger.messages" + "\n" + 
				                "SET messages.receiver_view=1" + "\n" + 
						        "WHERE messages.id=" + Message_id + ";";
						
						dbm.executeStatement(query2);
						
						//Keeping Log of the action
						Log.appendStrToFile("Dont_Shoot_The_Messenger_Log_File.txt", getUsername()+
						                    " Hid a Message that "+Sender+" had sent him at "+timeStamp+"  Message text: "+Message);
						} while (rs1.next());
					System.out.print("\n"+"Deleting all Read messages..."+"\n");
					System.out.print("\n"+"All Read messages Deleted Successfully" + "\n" + "Returning to Delete Message Menu..."+"\n");
					break;
					
					//Case 2.The User does NOT want to Delete all the Read Messages
					case "n":
						System.out.println("\n"+"Canceling All Read Messages Deletion..."+ "\n" + "Returning to Delete Message Menu..."+"\n");
						break;
					
					}
					break;
				}
			}
		} catch (SQLException e) {
			//e.printStackTrace();
		}
	}
	
	
	
	//07
	//METHOD THAT DELETES SENT MESSAGES
	//Case 1.ONE BY ONE
	//Case 2.ALL TOGETHER
	//BASED ON USER'S CHOICE
	public void DeleteSentMessages(DBMethods dbm, String choice) {

		Scanner DeleteSentMessagesSC = new Scanner(System.in);
		
		//Query to show all sent messages
		String queryfinal1="SELECT messages.id AS 'Message id',(SELECT users.username FROM dont_shoot_the_messenger.users WHERE users.id=messages.receiver_id) AS 'Receiver',messages.text AS 'Message',messages.date_time_sent AS 'Time Sent'"+"\n"+
			               "FROM dont_shoot_the_messenger.messages"+"\n"+
				           "WHERE messages.sender_id=("+"\n"+
				           "SELECT users.id"+"\n"+
				           "FROM dont_shoot_the_messenger.users"+"\n"+
				           "WHERE users.username='"+getUsername()+"'"+"\n"+
				           ") AND messages.sender_view=0"+"\n"+
				           "ORDER BY date_time_sent DESC;";

		try {
			Statement stm5 = dbm.getConnection().createStatement();

			ResultSet rs3 = stm5.executeQuery(queryfinal1);

			System.out.print("\n");

			//Message that is shown in case that there are no SENT messages
			if (rs3.next() == false) {
				System.out.println("You dont have any Sent messages!!!"+"\n"+"\n"+"Returning to Delete Message Menu...");
			} else {
				String answer1 = null;
				String deleteAnswer=null;
				String answerShowNext=null;

				switch (choice) {

				//Case 1.Delete(Hide) Sent Messages One by One
				case "1":

					//Do...while Loop that shows each Sent message
					//Asks whether to Delete (Hide) it or not
					//And waits for confirmation to continue to the next or not
					do {
						int Message_id=rs3.getInt("Message id");
						String Receiver = rs3.getString("Receiver");
						String Message = rs3.getString("Message");
						String Time_Sent = rs3.getString("Time Sent");
						//Showing the messages
						System.out.println("Receiver   : " + Receiver + "\n" + 
						                   "Message    : " + Message + "\n" +
								           "Time Sent  : " + Time_Sent + "\n");
						
						//Asking whether or not to Delete current message
						System.out.println("Delete current message (y/n) ?: ");
						deleteAnswer=DeleteSentMessagesSC.next();
						System.out.print("\n");
						
						//While Loop for preventing Wrong Input
						while(!deleteAnswer.equals("y")&&!deleteAnswer.equals("n")) {
							System.out.println("Invalid input"+"\n"+"Delete current message (y/n) ?: ");
							deleteAnswer=DeleteSentMessagesSC.next();
						}
						
						switch (deleteAnswer){
						
						    //Case that the user wants to Delete the current SENT message
						    case "y":
						    	
						    	//Query that changes the sender_view value from '0' to '1'
						    	String queryDelete="UPDATE dont_shoot_the_messenger.messages"+"\n"+
						                           "SET messages.sender_view=1"+"\n"+
						    			           "WHERE messages.id="+Message_id+";";
						    	
						    	dbm.executeStatement(queryDelete);
						    	
						    	//Keeping Log of the action
								Log.appendStrToFile("Dont_Shoot_The_Messenger_Log_File.txt", getUsername()+
										            " Hid a Message that he had sent to "+Receiver+" at "+timeStamp+"  Message text: "+Message);
						    	
						    	System.out.println("\n"+"Deleting current message..."+"\n"+"Message Deleted"+"\n");
						    	
						    	break;
						    	
						    //Case that the user does NOT want to Delete the current SENT message
						    case "n":
						    	System.out.println("\n"+"Skipping message Deletion..."+"\n");
						    	break;
						}

						//Asking whether or not to show next message
						System.out.println("Show next (y/n) ?: ");
						answerShowNext = DeleteSentMessagesSC.next();
						System.out.print("\n");
						
						//While Loop for preventing Wrong Input
						while(!answerShowNext.equals("y")&&!answerShowNext.equals("n")) {
							System.out.println("Invalid input"+"\n"+"Delete current message (y/n) ?: ");
							answerShowNext=DeleteSentMessagesSC.next();
						}

					} while (rs3.next() && answerShowNext.equalsIgnoreCase("y"));
					System.out.println("Nothing more to show" + "\n" + "Returning to Delete Message Menu...");
					break;

				//Case 2.Delete(Hide) All Sent Messages Together
				case "2":
					
					System.out.println("\n"+"Are you sure you want to Delete all your Sent messages? (y/n):");
					String DeleteAllSentChoice=DeleteSentMessagesSC.next();
					
					//While Loop for preventing Wrong Input
					while(!DeleteAllSentChoice.equals("y")&&!DeleteAllSentChoice.equals("n")) {
						System.out.println("Invalid input"+"\n"+"Are you sure you want to Delete all your Read messages? (y/n):");
						DeleteAllSentChoice=DeleteSentMessagesSC.next();
					}
					
					switch(DeleteAllSentChoice) {
					
					//Case 1.The User wants to Delete all the Sent Messages
					case "y":
					
					do {
						int Message_id=rs3.getInt("Message id");
						String Receiver = rs3.getString("Receiver");
						String Message = rs3.getString("Message");
						String Time_Sent = rs3.getString("Time Sent");
						
						//Query that changes the sender_view value from '0' to '1'
						String queryDeleteAll="UPDATE dont_shoot_the_messenger.messages"+"\n"+
		                                      "SET messages.sender_view=1"+"\n"+
		    			                      "WHERE messages.id="+Message_id+";";
						
						dbm.executeStatement(queryDeleteAll);
						
						//Keeping Log of the action
						Log.appendStrToFile("Dont_Shoot_The_Messenger_Log_File.txt", getUsername()+
								            " Hid a Message that he had sent to "+Receiver+" at "+timeStamp+"  Message text: "+Message);
						
					} while (rs3.next());
					
					System.out.print("\n"+"Deleting all Sent messages..."+"\n");
					System.out.print("\n"+"All Sent messages Deleted Successfully" + "\n" + "Returning to Delete Message Menu..."+"\n");
					break;
					
					//Case 2.The User does NOT want to Delete all the Read Messages
					case "n":
						System.out.println("\n"+"Canceling All Sent Messages Deletion..."+ "\n" + "Returning to Delete Message Menu..."+"\n");
						break;
					}
					break;
				}
			}
		} catch (SQLException e) {
			//e.printStackTrace();
		}
	}
	
	
	//08
	//METHOD THAT DELETES MESSAGES
	//Case 1.1.RECEIVED READ MESSAGES ONE BY ONE
	//Case 1.2.ALL RECEIVED READ MESSAGES TOGETHER
	//Case 2.1.SENT MESSAGES ONE BY ONE
	//Case 2.2.ALL SENT MESSAGES TOGETHER
	//BASED ON USER'S CHOICE
	public void DeleteMessages(DBMethods dbm) {
		String menu1answer = null;
		String menu2answer = null;
		String menu3answer = null;
		String ExitToMainMenuChoice = null;
		Scanner DeleteMessagesSC = new Scanner(System.in);

		//DELETE MESSAGE MENU Structure
		do {
			String menu1 = "\n" + "-----------DELETE MESSAGE MENU-------------" + "\n"+
					              "Please select one of the following actions:" + "\n" + 
					              "1.Delete Received Read Messages" + "\n"+
					              "2.Delete Sent Messages" + "\n" + "3.Exit to Main Menu" + "\n";
			
			System.out.println(menu1);
			menu1answer = DeleteMessagesSC.next();
			
			//While Loop for preventing Wrong Input
			while (!menu1answer.equals("1") && !menu1answer.equals("2") && !menu1answer.equals("3")) {
				System.out.println("Invalid input" + "\n" + menu1);
				menu1answer = DeleteMessagesSC.next();
			}

			//DELETE RECEIVED READ MESSAGES MENU Structure
			String menu2 = "\n"+"----DELETE RECEIVED READ MESSAGES MENU-----" + "\n"+
					            "Please select one of the following actions:" + "\n" + 
					            "1.Delete Read Messages One by One" + "\n"+
					            "2.Delete All Read Messages Together" + "\n" + 
					            "3.Exit to Delete Messages Menu" + "\n";

			//DELETE SENT MESSAGES MENU Structure
			String menu3 = "\n"+"--------DELETE SENT MESSAGES MENU----------" + "\n"+
					            "Please select one of the following actions:" + "\n" + 
					            "1.Delete Sent Messages One by One" + "\n"+
					            "2.Delete All Sent Messages Together" + "\n" + 
					            "3.Exit to Delete Messages Menu" + "\n";

			switch (menu1answer) {

			//Case 1.Delete Read Messages
			case "1":
				System.out.println(menu2);
				menu2answer = DeleteMessagesSC.next();
				while (!menu2answer.equals("1") && !menu2answer.equals("2") && !menu2answer.equals("3")) {
					System.out.println("Invalid input" + "\n" + menu1);
					menu2answer = DeleteMessagesSC.next();
				}
				switch (menu2answer) {

				//Case 1.1.Delete Received Read Messages One by One
				case "1":
					DeleteReadMessages(dbm, "1");
					ExitToMainMenuChoice = "y";
					break;

				//Case 1.2.Delete All Received Read Messages Together
				case "2":
					DeleteReadMessages(dbm, "2");
					ExitToMainMenuChoice = "y";
					break;

				//Case 1.3.Exit to Delete Messages Menu
				case "3":
					System.out.println("\n" + "Returning to Delete Message Menu...");
					ExitToMainMenuChoice = "y";
					break;
				}
				break;

			//Case 2.Delete Sent Messages
			case "2":

				System.out.println(menu3);
				menu3answer = DeleteMessagesSC.next();
				
				//While Loop for preventing Wrong Input
				while (!menu3answer.equals("1") && !menu3answer.equals("2") && !menu3answer.equals("3")) {
					System.out.println("Invalid input" + "\n" + menu3);
					menu3answer = DeleteMessagesSC.next();
				}
				switch (menu3answer) {

				//Case 2.1.Delete Sent Messages One by One
				case "1":
					DeleteSentMessages(dbm, "1");
					ExitToMainMenuChoice = "y";
					break;

				//Case 2.2.Delete All Sent Messages Together
				case "2":
					DeleteSentMessages(dbm, "2");
					ExitToMainMenuChoice = "y";
					break;

				//Case 2.3.Exit to Delete Messages Menu
				case "3":
					System.out.println("\n" + "Returning to Delete Message Menu...");
					ExitToMainMenuChoice = "y";
					break;
				}
				break;

			//Case 3.Exit to Main Menu
			case "3":
				System.out.println("Exiting to Main Menu...");
				ExitToMainMenuChoice = "n";
				break;
			}

		//Do...While Loop for keeping User to the DELETE MESSAGE MENU Unless he/she chooses 'Case 3.Exit to Main Menu'
		} while (!ExitToMainMenuChoice.equalsIgnoreCase("n"));
	}
		

}
