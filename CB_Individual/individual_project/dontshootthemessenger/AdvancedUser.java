package dontshootthemessenger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class AdvancedUser extends User{
	
	private String username=null;
	private String password=null;

	public AdvancedUser() {}
	
	public AdvancedUser(String username) {
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
	//METHOD THAT SHOWS-EDITS
	//Case 1.USER'S SENT MESSAGES ONE BY ONE 
	//Case 2.ALL USER'S SENT MESSAGES TOGETHER 
	//BASED ON USER'S CHOICE
	public void ShowEditSentMessages(DBMethods dbm, String choice) {

		Scanner ShowEditSentMessagesSC = new Scanner(System.in);
		
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
						
						
						//Asking whether or not to Edit current SENT message
						System.out.println("Edit current message (y/n) ?: ");
						String EditAnswer=ShowEditSentMessagesSC.next();
						System.out.print("\n");
						
						//While Loop for preventing Wrong Input
						while(!EditAnswer.equals("y")&&!EditAnswer.equals("n")) {
							System.out.println("Invalid input"+"\n"+"Edit current message (y/n) ?: ");
							EditAnswer=ShowEditSentMessagesSC.next();
						}
						
						switch (EditAnswer){
						
						    //Case that the user wants to Edit the current message
						    case "y":
						    	
						    	System.out.println("Please type in the new text of the Sent message (250 characters max): ");
								
								//Way to put the whole edited Sent message to a string called 'MessageFinal'
						    	String MessageFinal=ShowEditSentMessagesSC.next();
						    	MessageFinal=MessageFinal+ShowEditSentMessagesSC.nextLine();
						    	
						    	//Replacing the " and ' characters from MessageFinal with /
						    	MessageFinal=MessageFinal.replace('"','/');
						    	MessageFinal=MessageFinal.replaceAll("'","/");
								
								//Asking for confirmation to replace the old Sent message with the one you typed
								System.out.println("Do you want to replace the old Sent message with the one that you just typed? (y/n): ");
								String MessageEditSendAnswer=ShowEditSentMessagesSC.next();
								
								//While Loop for preventing Wrong Input
								while(!MessageEditSendAnswer.equals("y")&&!MessageEditSendAnswer.equals("n")) {
									System.out.println("Invalid input"+"\n"+"Edit current message (y/n) ?: ");
									MessageEditSendAnswer=ShowEditSentMessagesSC.next();
								}
								
								switch(MessageEditSendAnswer) {
								
								    //Case the User wants to Replace the message
								    case "y":
								    	
								    	//Query that changes the 'text' value to whatever the user inputs
								    	String queryReplace="UPDATE dont_shoot_the_messenger.messages"+"\n"+
						                                    "SET messages.text='"+MessageFinal+"'"+"\n"+
						    			                    "WHERE messages.id="+Message_id+";";
								    	
								    	dbm.executeStatement(queryReplace);
								    	
								    	//Keeping Log of the action
										Log.appendStrToFile("Dont_Shoot_The_Messenger_Log_File.txt", getUsername()+
												            " Edited a Message that he had sent to "+Receiver+" at "+timeStamp+" New Message text: "+MessageFinal);
								    	
								    	System.out.println("\n"+"Replacing current message..."+"\n"+"Message Replaced"+"\n");
								    	break;
								    
								    //Case that the user does NOT want to Replace the current message
								    case "n":
								    	System.out.println("\n"+"Skipping Sent message Replacement..."+"\n");
								    	break;
								    	}
								break;
								
							//Case that the user does NOT wants to Edit the current message
						    case "n":
						    	System.out.println("\n"+"Skipping current Sent message Editing..."+"\n");
						    	break;
								}
						
						//Asking whether or not to show next message
						System.out.println("Show next (y/n) ?: ");
						answer1 = ShowEditSentMessagesSC.next();
						System.out.print("\n");
						
						//While Loop for preventing Wrong Input
						while(!answer1.equals("y")&&!answer1.equals("n")) {
							System.out.println("Invalid input"+"\n"+"Show next (y/n) ?: ");
							answer1=ShowEditSentMessagesSC.next();
							}
						} while (rset.next() && !answer1.equalsIgnoreCase("n"));

					System.out.println("Nothing more to show" + "\n"+"Returning to Previous Menu...");
				
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
						/*
						//Showing the messages
						System.out.println("Message id : " + Message_id + "\n"+
								           "Sender     : " + Receiver + "\n" + 
						                   "Message    : " + Message + "\n" +
								           "Time Sent  : " + Time_Sent + "\n");*/
						
						//Add the ID of the current message to the ArrayList
						MessageIDList.add(Message_id);
						

					} while (rset.next());
					
					//Asking the user whether or not he wants to edit any of Sent messages
					System.out.println("No more Sent messages to show" + "\n" + "Do you want to edit any of the Sent messages? (y/n): ");
					String answerEdit = ShowEditSentMessagesSC.next();
					System.out.print("\n");
					
					//While Loop for preventing Wrong Input
					while(!answerEdit.equals("y")&&!answerEdit.equals("n")) {
						System.out.println("Invalid input"+"\n"+"Do you want to edit any of the Sent messages? (y/n): ");
						answerEdit=ShowEditSentMessagesSC.next();
						}
					
					switch(answerEdit) {
					
					    //Case that the user wants to Edit the any of the messages
					    case "y":
					    	String answerEditAnother="";
					    	
					    	do {
					    		System.out.println("\n"+"Please type in the id of the Sent Message you want to Edit: ");
					    		String MessageID=ShowEditSentMessagesSC.next();
					    		
					    		//Checking if the message id is in the ArrayList
					    		boolean IDChecker=MessageIDList.contains(MessageID);
					    		
					    		//While Loop for preventing Wrong Message ID Input
					    		while(IDChecker==false) {
					    			System.out.println("\n"+"Invalid Message ID"+"\n"+"Please type in the id of the Sent Message you want to Edit: ");
					    			MessageID=ShowEditSentMessagesSC.next();
					    			IDChecker=MessageIDList.contains(MessageID);
					    			
					    			}
					    		
					    		System.out.println("Please type in the new text of the Sent message (250 characters max): ");
					    		
					    		//Way to put the whole edited Sent message to a string called 'MessageFinal'
					    		String MessageFinal2=ShowEditSentMessagesSC.next();
					    		MessageFinal2=MessageFinal2+ShowEditSentMessagesSC.nextLine();
					    		
					    		//Replacing the " and ' characters from MessageFinal2 with /
					    		MessageFinal2=MessageFinal2.replace('"','/');
					    		MessageFinal2=MessageFinal2.replaceAll("'","/");
					    		
					    		//Asking for confirmation to replace the old Sent message with the one you typed
					    		System.out.println("Do you want to replace the old Sent message with the one that you just typed? (y/n): ");
					    		String MessageEditSendAnswer2=ShowEditSentMessagesSC.next();
					    		
					    		//While Loop for preventing Wrong Input
					    		while(!MessageEditSendAnswer2.equals("y")&&!MessageEditSendAnswer2.equals("n")) {
					    			System.out.println("Invalid input"+"\n"+"Edit current message (y/n) ?: ");
					    			MessageEditSendAnswer2=ShowEditSentMessagesSC.next();
					    			}
					    		
					    		switch(MessageEditSendAnswer2) {
					    		
					    		    //Case the User wants to Replace the message
							        case "y":
							        	
							        	//Query that changes the 'text' value to whatever the user inputs
							        	String queryReplace2="UPDATE dont_shoot_the_messenger.messages"+"\n"+
					                                         "SET messages.text='"+MessageFinal2+"'"+"\n"+
					    			                         "WHERE messages.id="+MessageID+";";
							        	
							        	dbm.executeStatement(queryReplace2);
							        	
							        	//Keeping Log of the action
										Log.appendStrToFile("Dont_Shoot_The_Messenger_Log_File.txt", getUsername()+
												            " Edited a Message that he had sent with Message ID "+MessageID+" at "+timeStamp+" New Message text: "+MessageFinal2);
							        	
							        	System.out.println("\n"+"Replacing message..."+"\n"+"Message Replaced"+"\n");
							        	break;
							        	
							        //Case that the user does NOT want to Replace the current message
							        case "n":
							        	System.out.println("\n"+"Skipping message Replacement..."+"\n");
							        	break;
							        	}
					    		
					    		//Asking whether or not the user wants to edit another message
					    		System.out.println("Do you want to edit another message (y/n) ?: ");
					    		answerEditAnother = ShowEditSentMessagesSC.next();
					    		System.out.print("\n");
					    		
					    		//While Loop for preventing Wrong Input
					    		while(!answerEditAnother.equals("y")&&!answerEditAnother.equals("n")) {
					    			System.out.println("Invalid input"+"\n"+"Show next (y/n) ?: ");
					    			answerEditAnother=ShowEditSentMessagesSC.next();
					    			}
					    		} while (!answerEditAnother.equalsIgnoreCase("n"));
					    	System.out.println("Exiting the Sent Message Editing..." + "\n" + "Returning to Previous Menu...");
					    	break;
					    	
					    //Case that the user does NOT want to Edit the any of the messages
					    case "n":
					    	System.out.println("\n"+"Exiting the Sent Message Editing..."+"\n"+
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
	//METHOD THAT SHOWS-EDITS 
	//Case 1.USER'S SENT MESSAGES ONE BY ONE 
	//Case 2.ALL USER'S SENT MESSAGES TOGETHER 
	//BASED ON USER'S CHOICE (USING THE ABOVE METHOD)
	public void ViewEditSent(DBMethods dbm) {
		String menu1answer=null;
		String ExitToMainMenuChoice=null;
		Scanner ViewEditSentSC=new Scanner(System.in);
			
		
		do{
			//EDIT SENT MESSAGES Menu Structure
			String menuEDIT="\n"+"----------EDIT SENT MESSAGES MENU----------"+"\n"+
		                      "Please select one of the following actions:"+"\n"+
		                      "1.View-Edit Sent Messages One by One"+"\n"+
				              "2.View-Edit All Sent Messages Together"+"\n"+
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
			    	ShowEditSentMessages(dbm,"1");
			    	ExitToMainMenuChoice="y";
			    	break;
			    
			    //Case 2.View All Sent Messages Together
			    case "2":
			    	ShowEditSentMessages(dbm,"2");
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
