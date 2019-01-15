package dontshootthemessenger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class PremiumUser extends AdvancedUser{

	private String username=null;
	private String password=null;
	
	public PremiumUser() {}
	
	public PremiumUser(String username) {
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
	//METHOD THAT SHOWS-CLEAN DELETES 
	//Case 1.USER'S SENT MESSAGES ONE BY ONE 
	//Case 2.ALL USER'S SENT MESSAGES TOGETHER 
	//BASED ON USER'S CHOICE
	public void ShowCleanDeleteSentMessages(DBMethods dbm, String choice) {

		Scanner ShowCleanDeleteSentMessagesSC = new Scanner(System.in);
		
		//Query that will show the Sent messages
		String queryfinal="SELECT messages.id AS 'Message id',(SELECT users.username FROM dont_shoot_the_messenger.users WHERE users.id=messages.receiver_id) AS 'Receiver',messages.text AS 'Message',messages.date_time_sent AS 'Time Sent'"+"\n"+
			               "FROM dont_shoot_the_messenger.messages"+"\n"+
				           "WHERE messages.sender_id=("+"\n"+
				           "SELECT users.id"+"\n"+
				           "FROM dont_shoot_the_messenger.users"+"\n"+
				           "WHERE users.username='"+getUsername()+"'"+"\n"+
				           ") AND messages.sender_view=0"+"\n"+
				           "ORDER BY date_time_sent DESC;";

		try {
			Statement stm4 = dbm.getConnection().createStatement();

			ResultSet rset = stm4.executeQuery(queryfinal);

			System.out.print("\n");

			//Message that is shown in case that there are no SENT messages
			if (rset.next() == false) {
				System.out.println("You dont have any Sent messages!!!"+"\n"+"\n"+"Returning to Previous Menu...");
			} else {
				String answer1 = null;

				switch (choice) {

				//Case 1.View Sent Messages One by One
				case "1":

					//Do...while Loop that shows each message awaiting for confirmation to continue
					do {
						int Message_id = rset.getInt("Message id");
						String Receiver = rset.getString("Receiver");
						String Message = rset.getString("Message");
						String Time_Sent = rset.getString("Time Sent");
						//Showing the messages
						System.out.println("Receiver   : " + Receiver + "\n" + 
						                   "Message    : " + Message + "\n" +
								           "Time Sent  : " + Time_Sent + "\n");
						
						
						//Asking whether or not to Delete current SENT message
						System.out.println("Delete current message (y/n) ?: ");
						String DeleteAnswer=ShowCleanDeleteSentMessagesSC.next();
						System.out.print("\n");
						
						//While Loop for preventing Wrong Input
						while(!DeleteAnswer.equals("y")&&!DeleteAnswer.equals("n")) {
							System.out.println("Invalid input"+"\n"+"Delete current message (y/n) ?: ");
							DeleteAnswer=ShowCleanDeleteSentMessagesSC.next();
						}
						
						switch (DeleteAnswer){
						
						    //Case that the user wants to Delete the current message
						    case "y":
						    	
						    	//Query that Deletes the current message
						    	String queryDelete="DELETE FROM dont_shoot_the_messenger.messages"+"\n"+
								    			    "WHERE messages.id="+Message_id+";";
						    	
						    	dbm.executeStatement(queryDelete);
						    	
						    	//Keeping Log of the action
								Log.appendStrToFile("Dont_Shoot_The_Messenger_Log_File.txt", getUsername()+
										            " Deleted a Message that he had sent to "+Receiver+" at "+timeStamp+" Deleted Message text: "+Message);
						    	
						    	System.out.println("\n"+"Deleting current message..."+"\n"+"Message Deleted"+"\n");
						    	break;
						    	
						    //Case that the user does NOT want to Delete the current message
							case "n":
								
								System.out.println("\n"+"Skipping current Sent message Deletion..."+"\n");
								break;
								}

						
						//Asking whether or not to show next message
						System.out.println("Show next (y/n) ?: ");
						answer1 = ShowCleanDeleteSentMessagesSC.next();
						System.out.print("\n");
						
						//While Loop for preventing Wrong Input
						while(!answer1.equals("y")&&!answer1.equals("n")) {
							System.out.println("Invalid input"+"\n"+"Show next (y/n) ?: ");
							answer1=ShowCleanDeleteSentMessagesSC.next();
							}
						} while (rset.next() && !answer1.equalsIgnoreCase("n"));
					System.out.println("Exiting Sent Message Deletion" + "\n" + "Returning to Previous Menu...");
					break;

				//Case 2.View All Sent Messages Together
				case "2":
					
					//Creating an arrayList that will hold the ID of every Sent Message
					ArrayList<String> MessageIDList = new ArrayList<String>();
					
					//Showing All User's Info Headings in a certain format
					System.out.printf("%-15s %-30s %-30s %-235s","[Message ID]","[Receiver]","[Time Sent]","[Message Text]");
					System.out.println("\n"+"\n");

					//Do...while Loop that shows each Sent message Without waiting for confirmation
					do {
						String Message_id = rset.getString("Message id");
						String Receiver = rset.getString("Receiver");
						String Message = rset.getString("Message");
						String Time_Sent = rset.getString("Time Sent");
						
						//Showing all the messages info in a certain format
						System.out.printf("%-15s %-30s %-30s %-235s",Message_id,Receiver,Time_Sent,Message);
						System.out.println("\n");
						
						//Add the ID of the current message to the ArrayList
						MessageIDList.add(Message_id);
						

					} while (rset.next());
					
					//Asking the user whether or not he wants to delete any of Sent messages
					System.out.println("No more Sent messages to show" + "\n" + "Do you want to delete any of the Sent messages? (y/n): ");
					String answerDeletion = ShowCleanDeleteSentMessagesSC.next();
					System.out.print("\n");
					
					//While Loop for preventing Wrong Input
					while(!answerDeletion.equals("y")&&!answerDeletion.equals("n")) {
						System.out.println("Invalid input"+"\n"+"Do you want to delete any of the Sent messages? (y/n): ");
						answerDeletion=ShowCleanDeleteSentMessagesSC.next();
						}
					
					switch(answerDeletion) {
					
					    //Case that the user wants to Delete any of the messages
					    case "y":
					    	String answerDeleteAnother="";
					    	
					    	do {
					    		System.out.println("\n"+"Please type in the id of the Sent Message you want to Delete: ");
					    		String MessageID=ShowCleanDeleteSentMessagesSC.next();
					    		
					    		//Checking if the message id is in the ArrayList
					    		boolean IDChecker=MessageIDList.contains(MessageID);
					    		
					    		//While Loop for preventing Wrong Message ID Input
					    		while(IDChecker==false) {
					    			System.out.println("\n"+"Invalid Message ID"+"\n"+"Please type in the id of the Sent Message you want to Delete: ");
					    			MessageID=ShowCleanDeleteSentMessagesSC.next();
					    			IDChecker=MessageIDList.contains(MessageID);
					    			
					    			}
					    		
					    		//Asking for confirmation to Delete the Sent message
					    		System.out.println("Do you want to Delete the Sent message? (y/n): ");
					    		String MessageDeleteSendAnswer2=ShowCleanDeleteSentMessagesSC.next();
					    		
					    		//While Loop for preventing Wrong Input
					    		while(!MessageDeleteSendAnswer2.equals("y")&&!MessageDeleteSendAnswer2.equals("n")) {
					    			System.out.println("Invalid input"+"\n"+"Do you want to Delete the Sent message? (y/n): ");
					    			MessageDeleteSendAnswer2=ShowCleanDeleteSentMessagesSC.next();
					    			}
					    		
					    		switch(MessageDeleteSendAnswer2) {
					    		
					    		    //Case the User wants to Delete the message
							        case "y":
							        	
							        	//Query that Deletes the current message
								    	String queryDelete2="DELETE FROM dont_shoot_the_messenger.messages"+"\n"+
										    			    "WHERE messages.id="+MessageID+";";
								    	
								    	dbm.executeStatement(queryDelete2);
								    	
								    	//Keeping Log of the action
										Log.appendStrToFile("Dont_Shoot_The_Messenger_Log_File.txt", getUsername()+
												            " Deleted a Message with Message ID "+MessageID+" at "+timeStamp);
								    	
								    	
								    	System.out.println("\n"+"Deleting message..."+"\n"+"Message Deleted"+"\n");
								    	break;
								    	
							        //Case that the user does NOT want to Delete the message
							        case "n":
							        	System.out.println("\n"+"Skipping message Deletion..."+"\n");
							        	break;
							        	}
					    		
					    		//Asking whether or not the user wants to Delete another message
					    		System.out.println("Do you want to Delete another message (y/n) ?: ");
					    		answerDeleteAnother = ShowCleanDeleteSentMessagesSC.next();
					    		System.out.print("\n");
					    		
					    		//While Loop for preventing Wrong Input
					    		while(!answerDeleteAnother.equals("y")&&!answerDeleteAnother.equals("n")) {
					    			System.out.println("Invalid input"+"\n"+"Show next (y/n) ?: ");
					    			answerDeleteAnother=ShowCleanDeleteSentMessagesSC.next();
					    			}
					    		} while (!answerDeleteAnother.equalsIgnoreCase("n"));
					    	System.out.println("Exiting the Sent Message Deletion" + "\n" + "Returning to Previous Menu...");
					    	break;
					    	
					    //Case that the user does NOT want to Delete the any of the messages
					    case "n":
					    	System.out.println("\n"+"Exiting the Sent Message Deletion..."+"\n"+
					    			           "Returning to Previous Menu...");
					    	break;
					}
					break;
				}
			}
		} catch (SQLException e) {
			//e.printStackTrace();
		}
	}

	
	
	//02
	//METHOD FOR MENU THAT SHOWS-CLEAN DELETES
	//Case 1.USER'S SENT MESSAGES ONE BY ONE 
	//Case 2.ALL USER'S SENT MESSAGES TOGETHER 
	//BASED ON USER'S CHOICE (USING THE ABOVE METHOD)
	public void ViewCleanDeleteSent(DBMethods dbm) {
		String menu1answer=null;
		String ExitToMainMenuChoice=null;
		Scanner ViewEditSentSC=new Scanner(System.in);
			
		
		do{
			//EDIT SENT MESSAGES Menu Structure
			String menuEDIT="\n"+"------CLEAN DELETE SENT MESSAGES MENU--------"+"\n"+
		                      "Please select one of the following actions:"+"\n"+
		                      "1.View and Clean-Delete Sent Messages One by One"+"\n"+
				              "2.View and Clean-Delete All Sent Messages Together"+"\n"+
		                      "3.Exit to Main Menu"+"\n";
			
			System.out.println(menuEDIT);
			menu1answer=ViewEditSentSC.next();
			//While Loop for preventing Wrong Input
			while(!menu1answer.equals("1")&&!menu1answer.equals("2")&&!menu1answer.equals("3")) {
				System.out.println("Invalid input"+"\n"+menuEDIT);
				menu1answer=ViewEditSentSC.next();
			}
			

			switch(menu1answer) {
			
			    //Case 1.View Sent Messages One by One
			    case "1":
			    	ShowCleanDeleteSentMessages(dbm,"1");
			    	ExitToMainMenuChoice="y";
			    	break;
			    
			    //Case 2.View All Sent Messages Together
			    case "2":
			    	ShowCleanDeleteSentMessages(dbm,"2");
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
	

}
