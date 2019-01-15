package dontshootthemessenger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Administrator extends PremiumUser{
	
	private String username=null;
	private String password=null;

	public Administrator() {}
	
	public Administrator(String username) {
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
	//METHOD THAT SHOWS ALL USER INFO
	public void ViewAllUserInfo(DBMethods dbm) {

		Scanner ViewAllUserInfoSC = new Scanner(System.in);
		String answer1 = null;

		String ViewAllUserQuery = "SELECT users.id AS 'User ID'," + "users.username AS 'Username',"
				+ "users.password AS 'Password'," + "users.classification AS 'User Class',"
				+ "last_account_info_update AS 'Date of Last Account Update'" + "\n"
				+ "FROM dont_shoot_the_messenger.users;";
		try {
			
				Statement stm2 = dbm.getConnection().createStatement();

				ResultSet rs1 = stm2.executeQuery(ViewAllUserQuery);

				System.out.print("\n");

				if (rs1.next() == false) {

					System.out.println("There are no users in the System!!!" + "\n" + "\n" + "Returning to Previous Menu...");
				} 
				else {
					
					//Showing All User's Info Headings in a certain format
					System.out.printf("%-11s %-15s %-15s %-20s %-30s","[User ID]","[Username]","[Password]","[User Class]","[Date of Last Account Update]");
					System.out.println("\n");

					// Do...while Loop that shows each user's info
					do {
						int User_id = rs1.getInt("User ID");
						String Username = rs1.getString("Username");
						String Password = rs1.getString("Password");
						String User_Class = rs1.getString("User Class");
						String Date_of_Last_Account_Update = rs1.getString("Date of Last Account Update");

						// Showing All User's Info
						System.out.printf("%-11d %-15s %-15s %-20s %-30s",User_id,Username,Password,User_Class,Date_of_Last_Account_Update);
						System.out.println("\n");

					} while (rs1.next());
				}
			System.out.println("Returning to Administrators Menu...");
		} catch (SQLException e) {
			// e.printStackTrace();
		}
	}
	
	
	
	//02
	//METHOD THAT CREATES A NEW USER
	public void CreateNewUser(DBMethods dbm) {

		Scanner CreateNewUserSC = new Scanner(System.in);
		String str1 = " can NOT contain one of the following characters \" ' , & % * / \\" + "\n"
				    + "and must be between 6-30 characters long:" + "\n";

		String LeaveCreateUserMenuChoice = "";

		try {
			do {
				System.out.println("\n" + "Please type the new User's username" + "\n" + "Username" + str1);
				String NewUserUsername = CreateNewUserSC.next();

				// While Loop preventing the username to have one of the following characters " ' , & % * / \
				// Or be less than 6 or more than 30 characters long
				while (NewUserUsername.contains("\"") || NewUserUsername.contains("'") || NewUserUsername.contains(",")
						|| NewUserUsername.contains("&") || NewUserUsername.contains("%")
						|| NewUserUsername.contains("*")|| NewUserUsername.contains("/") 
						|| NewUserUsername.contains("\\")|| NewUserUsername.length() < 6 
						|| NewUserUsername.length() > 30) {
					
					System.out.println("Invalid Input" + "\n" + "Please type the new User's username" + "\n" + "Username" + str1);
					
					NewUserUsername = CreateNewUserSC.next();
				}

				String CheckIfUsernameExistsQuery = "SELECT * FROM dont_shoot_the_messenger.users" + "\n"
						                          + "WHERE users.username='" + NewUserUsername + "';";

				Statement stm = dbm.getConnection().createStatement();

				ResultSet rs = stm.executeQuery(CheckIfUsernameExistsQuery);

				// Boolean that checks if the username already exists
				boolean CheckUsername = rs.next();

				// While Loop preventing that the new username does not exist in the database
				while (CheckUsername == true) {
					System.out.println("\n"+"Username already exists" + "\n" + "Please type in another Username"+"\n");
					NewUserUsername = CreateNewUserSC.next();

					CheckIfUsernameExistsQuery = "SELECT * FROM dont_shoot_the_messenger.users" + "\n"
							                   + "WHERE users.username='" + NewUserUsername + "';";

					rs = stm.executeQuery(CheckIfUsernameExistsQuery);
					CheckUsername = rs.next();
				}

				System.out.println("Username saved" + "\n");

				System.out.println("Please type the new User's password" + "\n" + "Password" + str1);
				String NewUserPassword = CreateNewUserSC.next();

				// While Loop preventing the password to have one of the following characters " ' , & % * / \
				// Or be less than 6 or more than 30 characters long
				while (NewUserPassword.contains("\"") || NewUserPassword.contains("'") || NewUserPassword.contains(",")
						|| NewUserPassword.contains("&") || NewUserPassword.contains("%")
						|| NewUserPassword.contains("*")|| NewUserPassword.contains("/")
						|| NewUserPassword.contains("\\")|| NewUserPassword.length() < 6
						|| NewUserPassword.length() > 30) {
					
					System.out.println("Invalid Input" + "\n" + "Please type the new User's password" + "\n" + "Password" + str1);
					
					NewUserPassword = CreateNewUserSC.next();
				}

				System.out.println("Password saved" + "\n");

				String NewUserClassMenu = "Please choose one of the following Classes" + "\n"
						                + "to be the new User's Class: " + "\n" 
						                + "1.User" + "\n" 
						                + "2.Advanced User" + "\n"
					              	    + "3.Premium User" + "\n" 
						                + "4.Administrator" + "\n";

				System.out.println(NewUserClassMenu);
				String NewUserClassChoice = CreateNewUserSC.next();

				// While Loop preventing wrong input
				while (!NewUserClassChoice.equals("1") && !NewUserClassChoice.equals("2")
						&& !NewUserClassChoice.equals("3") && !NewUserClassChoice.equals("4")) {
					System.out.println("Invalid Input" + "\n" + NewUserClassMenu);
					NewUserClassChoice = CreateNewUserSC.next();
				}

				String NewUserClass = "";

				switch (NewUserClassChoice) {

				// Case 1.User
				case "1":
					NewUserClass = "User";
					break;

				// Case 2.Advanced User
				case "2":
					NewUserClass = "Advanced User";
					break;

				// Case 3.Premium User
				case "3":
					NewUserClass = "Premium User";
					break;

				// Case 4.Administrator
				case "4":
					NewUserClass = "Administrator";
					break;
				}

				System.out.println("Continue Creating the New User? (y/n):");
				String ConfirmNewUserCreationChoice = CreateNewUserSC.next();

				// While Loop preventing wrong input
				while (!ConfirmNewUserCreationChoice.equalsIgnoreCase("y") && !ConfirmNewUserCreationChoice.equalsIgnoreCase("n")) {
					System.out.println("Invalid Input" + "\n" + "Continue Creating the New User? (y/n):");
					ConfirmNewUserCreationChoice = CreateNewUserSC.next();
				}

				switch (ConfirmNewUserCreationChoice) {

				// Case 1.User
				case "y":
					
					//Query that inserts the new user in the database
					String CreateNewUserQuery = "INSERT INTO `dont_shoot_the_messenger`.`users`" + "\n"
							                  + "(`username`,`password`,`classification`)" + "\n" 
							                  + "VALUES ('" + NewUserUsername + "', '"+ NewUserPassword + "', '" + NewUserClass + "');";

					dbm.executeStatement(CreateNewUserQuery);
					
					//Keeping Log of the action
					Log.appendStrToFile("Dont_Shoot_The_Messenger_Log_File.txt", getUsername()+
							            " Created a new User with Username= "+NewUserUsername+", "
							            		+ "Password= "+NewUserPassword+" and Class= "+NewUserClass+" at "+timeStamp);

					System.out.println("\n" + "Creating new User..." + "\n" + "\n" + "New " + NewUserClass + " "
							                + NewUserUsername + " was successfully created");

					break;

				// Case 2.Advanced User
				case "n":
					System.out.println("\n" + "Canceling New User Creation..." + "\n");
					break;
				}

				System.out.println("\n" + "Do you want to continue Creating other Users? (y/n):");
				LeaveCreateUserMenuChoice = CreateNewUserSC.next();
				
				while (!LeaveCreateUserMenuChoice.equalsIgnoreCase("y")
					&& !LeaveCreateUserMenuChoice.equalsIgnoreCase("n")) {
					
					System.out.println("Invalid Input" + "\n" + "Do you want to continue Creating other Users? (y/n):");
				}

			} while (LeaveCreateUserMenuChoice.equalsIgnoreCase("y"));

			System.out.println("\n" + "Exiting to Previous Menu..." + "\n");

		}

		catch (Exception e) {
			//e.printStackTrace();
		}

	}
	
	
	
	//03
	//METHOD THAT UPDATES USER INFO
	public void UpdateUserInfo(DBMethods dbm) {

		Scanner UpdateUserInfoSC = new Scanner(System.in);
		String UserUpdateContinueChoice = "";
		String str1 = " can NOT contain one of the following characters \" ' , & % * / \\" + "\n"
				    + "and must be between 6-30 characters long:" + "\n";

		try {

			do {
				System.out.println("\n" + "Please type the username of the User that you want to Update" + "\n");
				String UserUsername = UpdateUserInfoSC.next();

				// While Loop preventing the typed username to have one of the following
				// characters " '
				// in order to prevent syntax error in the Query's execution
				while (UserUsername.contains("\"") || UserUsername.contains("'")) {

					System.out.println(
							"\n" + "Username does NOT exist" + "\n" + "Please type in the correct Username" + "\n");
					
					UserUsername = UpdateUserInfoSC.next();
				}

				String CheckIfUsernameExistsQuery = "SELECT * FROM dont_shoot_the_messenger.users" + "\n"
						                          + "WHERE users.username='" + UserUsername + "';";

				Statement stm = dbm.getConnection().createStatement();

				ResultSet rs = stm.executeQuery(CheckIfUsernameExistsQuery);

				// Boolean that checks if the username already exists
				boolean CheckUsername = rs.next();

				// While Loop preventing that the new username does not exist in the database
				while (CheckUsername == false) {
					System.out.println(
							"\n" + "Username does NOT exist" + "\n" + "Please type in the correct Username" + "\n");
					UserUsername = UpdateUserInfoSC.next();

					CheckIfUsernameExistsQuery = "SELECT * FROM dont_shoot_the_messenger.users" + "\n"
							                   + "WHERE users.username='" + UserUsername + "';";

					rs = stm.executeQuery(CheckIfUsernameExistsQuery);
					CheckUsername = rs.next();
				}

				String UserUpdateMenu = "Please choose one of the following actions:" + "\n"
						              + "1.Update User's Username" + "\n" 
						              + "2.Update User's Password" + "\n"
						              + "3.Update User's Class" + "\n" 
						              + "4.Exit to previous Menu" + "\n";

				System.out.println("\n" + UserUpdateMenu);
				String UserUpdateMenuChoice = UpdateUserInfoSC.next();

				// While Loop preventing wrong input
				while (!UserUpdateMenuChoice.equals("1") && !UserUpdateMenuChoice.equals("2")
						&& !UserUpdateMenuChoice.equals("3") && !UserUpdateMenuChoice.equals("4")) {

					System.out.println("Invalid Input" + "\n" + UserUpdateMenu);
					UserUpdateMenuChoice = UpdateUserInfoSC.next();
				}

				switch (UserUpdateMenuChoice) {

				// Case 1.Update User's Username
				case "1":

					System.out.println("\n" + "Please type the User's NEW Username" + "\n" + "Username" + str1);
					String NewUsername = UpdateUserInfoSC.next();

					// While Loop preventing the username to have one of the following characters "
					// ' , & % * / \
					// Or be less than 6 or more than 30 characters long
					while (NewUsername.contains("\"") || NewUsername.contains("'") || NewUsername.contains(",")
							|| NewUsername.contains("&") || NewUsername.contains("%") || NewUsername.contains("*")
							|| NewUsername.contains("/") || NewUsername.contains("\\") || NewUsername.length() < 6
							|| NewUsername.length() > 30) {

						System.out.println("Invalid Input" + "\n" + "Please type the User's NEW Username" + "\n"
								         + "Username" + str1);

						NewUsername = UpdateUserInfoSC.next();
					}

					CheckIfUsernameExistsQuery = "SELECT * FROM dont_shoot_the_messenger.users" + "\n"
							                   + "WHERE users.username='" + NewUsername + "';";

					// stm = dbm.getConnection().createStatement();

					rs = stm.executeQuery(CheckIfUsernameExistsQuery);

					// Boolean that checks if the username already exists
					CheckUsername = rs.next();

					// While Loop preventing that the new username does not exist in the database
					while (CheckUsername == true) {
						System.out.println(
								"\n" + "Username already exists" + "\n" + "Please type in another Username" + "\n");
						NewUsername = UpdateUserInfoSC.next();

						CheckIfUsernameExistsQuery = "SELECT * FROM dont_shoot_the_messenger.users" + "\n"
								                   + "WHERE users.username='" + NewUsername + "';";

						rs = stm.executeQuery(CheckIfUsernameExistsQuery);
						CheckUsername = rs.next();
					}

					System.out.println("\n" + "Continue with Updating the Usename? (y/n):");
					String ConfirmUsernameUpdate = UpdateUserInfoSC.next();

					// While Loop preventing wrong input
					while (!ConfirmUsernameUpdate.equalsIgnoreCase("y") && !ConfirmUsernameUpdate.equalsIgnoreCase("n")) {
						System.out
								.println("\n" + "Invalid Input" + "\n" + "Continue with Updating the Usename? (y/n):");
						ConfirmUsernameUpdate = UpdateUserInfoSC.next();
					}

					switch (ConfirmUsernameUpdate) {

					// Case that the admin wants to change the Username
					case "y":

						// Query that changes the Username
						String UpdateUserUsernameQuery = "UPDATE dont_shoot_the_messenger.users" + "\n"
								                       + "SET users.username='" + NewUsername + "'" + "\n" 
								                       + "WHERE users.username='"+ UserUsername + "';";

						dbm.executeStatement(UpdateUserUsernameQuery);
						
						//Keeping Log of the action
						Log.appendStrToFile("Dont_Shoot_The_Messenger_Log_File.txt", getUsername()+
								            " Updated a User's Username from: "+UserUsername+" to: "+NewUsername+" at "+timeStamp);

						System.out.println("\n" + "Username Updated successfully..." + "\n");

						break;

					// Case that the admin does NOT want to change the Username
					case "n":
						System.out.println("\n" + "Canceling the Username Update...");

						break;
					}

					System.out.println("\n" + "Do you want to continue Updating User Info? (y/n):");
					UserUpdateContinueChoice = UpdateUserInfoSC.next();

					// While Loop preventing wrong input
					while (!UserUpdateContinueChoice.equalsIgnoreCase("y") && !UserUpdateContinueChoice.equalsIgnoreCase("n")) {
						System.out.println(
								"\n" + "Invalid Input" + "\n" + "Do you want to continue Updating User Info? (y/n):");
						UserUpdateContinueChoice = UpdateUserInfoSC.next();
					}
					break;

				// 2.Update User's Password
				case "2":

					System.out.println("\n" + "Please type the User's NEW Password" + "\n" + "Password" + str1);
					String NewPassword = UpdateUserInfoSC.next();

					// While Loop preventing the username to have one of the following characters "
					// ' , & % * / \
					// Or be less than 6 or more than 30 characters long
					while (NewPassword.contains("\"") || NewPassword.contains("'") || NewPassword.contains(",")
							|| NewPassword.contains("&") || NewPassword.contains("%") || NewPassword.contains("*")
							|| NewPassword.contains("/") || NewPassword.contains("\\") || NewPassword.length() < 6
							|| NewPassword.length() > 30) {

						System.out.println("\n" + "Invalid Input" + "\n" + "Please type the User's NEW Password" + "\n"
								                + "Password" + str1);
						
						NewPassword = UpdateUserInfoSC.next();
					}

					System.out.println("\n" + "Continue with Updating the Password? (y/n):");
					String ConfirmPassowrdUpdate = UpdateUserInfoSC.next();

					// While Loop preventing wrong input
					while (!ConfirmPassowrdUpdate.equalsIgnoreCase("y") && !ConfirmPassowrdUpdate.equalsIgnoreCase("n")) {
						System.out
								.println("\n" + "Invalid Input" + "\n" + "Continue with Updating the Password? (y/n):");
						ConfirmPassowrdUpdate = UpdateUserInfoSC.next();
					}

					switch (ConfirmPassowrdUpdate) {

					// Case that the admin wants to change the Password
					case "y":

						// Query that inserts the new user in the database
						String UpdateUserPasswordQuery = "UPDATE dont_shoot_the_messenger.users" + "\n"
								                       + "SET users.password='" + NewPassword + "'" + "\n" 
								                       + "WHERE users.username='"+ UserUsername + "';";

						dbm.executeStatement(UpdateUserPasswordQuery);
						
						//Keeping Log of the action
						Log.appendStrToFile("Dont_Shoot_The_Messenger_Log_File.txt", getUsername()+
								            " Updated the Password of "+UserUsername+" to: "+NewPassword+" at "+timeStamp);

						System.out.println("\n" + "Password Updated successfully..." + "\n");
						break;

					// Case that the admin does NOT want to change the Password
					case "n":
						System.out.println("\n" + "Canceling the Password Update...");

						break;
					}

					System.out.println("\n" + "Do you want to continue Updating User Info? (y/n):");
					UserUpdateContinueChoice = UpdateUserInfoSC.next();

					// While Loop preventing wrong input
					while (!UserUpdateContinueChoice.equalsIgnoreCase("y") && !UserUpdateContinueChoice.equalsIgnoreCase("n")) {
						System.out.println(
								"\n" + "Invalid Input" + "\n" + "Do you want to continue Updating User Info? (y/n):");
						UserUpdateContinueChoice = UpdateUserInfoSC.next();
					}
					break;

				// 3.Update User's Class
				case "3":

					String UpdateUserClassMenu = "Please choose one of the following Classes" + "\n"
							                   + "to be the new User's Class: " + "\n" 
							                   + "1.User" + "\n" 
							                   + "2.Advanced User" + "\n"
							                   + "3.Premium User" + "\n" 
							                   + "4.Administrator" + "\n";

					System.out.println("\n"+UpdateUserClassMenu);
					String NewUserClass = UpdateUserInfoSC.next();

					// While Loop preventing wrong input
					while (!NewUserClass.equals("1") && !NewUserClass.equals("2") && !NewUserClass.equals("3")
							&& !NewUserClass.equals("4")) {

						System.out.println("\n" + "Invalid Input" + "\n" + UpdateUserClassMenu);
						NewUserClass = UpdateUserInfoSC.next();
					}

					String NewUpdatedUserClass = "";

					switch (NewUserClass) {

					// Case 1.User
					case "1":
						NewUpdatedUserClass = "User";
						break;

					// Case 2.Advanced User
					case "2":
						NewUpdatedUserClass = "Advanced User";
						break;

					// Case 3.Premium User
					case "3":
						NewUpdatedUserClass = "Premium User";
						break;

					// Case 4.Administrator
					case "4":
						NewUpdatedUserClass = "Administrator";
						break;
					}

					System.out.println("\n" + "Continue with Updating the Class? (y/n):");
					String ConfirmClassUpdate = UpdateUserInfoSC.next();

					// While Loop preventing wrong input
					while (!ConfirmClassUpdate.equalsIgnoreCase("y") && !ConfirmClassUpdate.equalsIgnoreCase("n")) {
						System.out.println("\n" + "Invalid Input" + "\n" + "Continue with Updating the Class? (y/n):");
						ConfirmClassUpdate = UpdateUserInfoSC.next();
					}

					switch (ConfirmClassUpdate) {

					// Case that the admin wants to change the Class
					case "y":

						// Query that inserts the new user in the database
						String UpdateUserClassQuery = "UPDATE dont_shoot_the_messenger.users" + "\n"
								                    + "SET users.classification='" + NewUpdatedUserClass + "'" + "\n" 
								                    + "WHERE users.username='"+ UserUsername + "';";

						dbm.executeStatement(UpdateUserClassQuery);
						
						//Keeping Log of the action
						Log.appendStrToFile("Dont_Shoot_The_Messenger_Log_File.txt", getUsername()+
								            " Updated the Class of "+UserUsername+" to: "+NewUpdatedUserClass+" at "+timeStamp);

						System.out.println("\n" + "Class Updated successfully..." + "\n");
						break;

					// Case that the admin does NOT want to change the Class
					case "n":
						System.out.println("\n" + "Canceling the Class Update...");

						break;
					}

					System.out.println("\n" + "Do you want to continue Updating User Info? (y/n):");
					UserUpdateContinueChoice = UpdateUserInfoSC.next();

					// While Loop preventing wrong input
					while (!UserUpdateContinueChoice.equalsIgnoreCase("y") && !UserUpdateContinueChoice.equalsIgnoreCase("n")) {
						System.out.println(
								"\n" + "Invalid Input" + "\n" + "Do you want to continue Updating User Info? (y/n):");
						UserUpdateContinueChoice = UpdateUserInfoSC.next();
					}
					break;

				// 4.Exit to previous Menu
				case "4":
					UserUpdateContinueChoice = "n";
					System.out.println("Exiting to Previous Menu...");
					break;
				}

			} while (UserUpdateContinueChoice.equalsIgnoreCase("y"));

		} catch (Exception e) {
			// e.printStackTrace();
		}

	}
	
	
	
	//04
	//METHOD THAT DELETES USERS
	public void DeleteUser(DBMethods dbm) {

		Scanner DeleteUserSC = new Scanner(System.in);
		String UserDeleteContinueChoice = "";
		String str1 = " can NOT contain one of the following characters \" ' , & % * / \\" + "\n"
				    + "and must be between 6-30 characters long:" + "\n";

		try {

			do {
				System.out.println("\n" + "Please type the username of the User that you want to Delete" + "\n");
				String UserUsername = DeleteUserSC.next();

				// While Loop preventing the typed username to have one of the following
				// characters " '
				// in order to prevent syntax error in the Query's execution
				while (UserUsername.contains("\"") || UserUsername.contains("'")) {
					System.out.println(
							"\n" + "Username does NOT exist" + "\n" + "Please type in the correct Username" + "\n");
					UserUsername = DeleteUserSC.next();
				}

				String CheckIfUsernameExistsQuery = "SELECT * FROM dont_shoot_the_messenger.users" + "\n"
						                          + "WHERE users.username='" + UserUsername + "';";

				Statement stm = dbm.getConnection().createStatement();

				ResultSet rs = stm.executeQuery(CheckIfUsernameExistsQuery);

				// Boolean that checks if the username already exists
				boolean CheckUsername = rs.next();

				// While Loop preventing that the new username does not exist in the database
				while (CheckUsername == false) {
					System.out.println(
							"\n" + "Username does NOT exist" + "\n" + "Please type in the correct Username" + "\n");
					UserUsername = DeleteUserSC.next();

					CheckIfUsernameExistsQuery = "SELECT * FROM dont_shoot_the_messenger.users" + "\n"
							                   + "WHERE users.username='" + UserUsername + "';";

					rs = stm.executeQuery(CheckIfUsernameExistsQuery);
					CheckUsername = rs.next();
				}

				System.out.println("\n" + "Continue with Deleting the User " + UserUsername + " ? (y/n):");
				String ConfirmUserDeletion = DeleteUserSC.next();

				// While Loop preventing wrong input
				while (!ConfirmUserDeletion.equalsIgnoreCase("y") && !ConfirmUserDeletion.equalsIgnoreCase("n")) {
					System.out.println("\n" + "Invalid Input" + "\n" + "Continue with Deleting the User " + UserUsername
							+ " ? (y/n):");
					ConfirmUserDeletion = DeleteUserSC.next();
				}

				switch (ConfirmUserDeletion) {

				// Case that the admin wants to Delete the User
				case "y":

					// Query that inserts the new user in the database
					String UpdateUserClassQuery = "DELETE FROM dont_shoot_the_messenger.users" + "\n"
							                    + "WHERE users.username='" + UserUsername + "';";

					dbm.executeStatement(UpdateUserClassQuery);
					
					//Keeping Log of the action
					Log.appendStrToFile("Dont_Shoot_The_Messenger_Log_File.txt", getUsername()+
							            " Deleted the User "+UserUsername+" at "+timeStamp);

					System.out.println("\n" + "User Deleted successfully..." + "\n");
					break;

				// Case that the admin does NOT want to Delete the User
				case "n":
					System.out.println("\n" + "Canceling User Deletion...");

					break;
				}

				System.out.println("\n" + "Do you want to continue Deleting Users? (y/n):");
				UserDeleteContinueChoice = DeleteUserSC.next();

				// While Loop preventing wrong input
				while (!UserDeleteContinueChoice.equalsIgnoreCase("y") && !UserDeleteContinueChoice.equalsIgnoreCase("n")) {
					System.out
							.println("\n" + "Invalid Input" + "\n" + "Do you want to continue Deleting Users? (y/n):");
					UserDeleteContinueChoice = DeleteUserSC.next();
				}

			} while (UserDeleteContinueChoice.equalsIgnoreCase("y"));
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}
	
	
	
	//05
	//METHOD THAT VIEWS ALL MESSAGES
	public ArrayList ViewAllMessage(DBMethods dbm) {
		
		String MessageEditDeleteContinueChoice="";
		
		//Creating an arrayList that will hold the ID of every  Message
		ArrayList<String> AllMessageIDList = new ArrayList<String>();
		
		//Query that shows all the messages (hidden or not)
		String ViewAllMessagesQuery="SELECT messages.id AS 'Message id',"+"\n"+
				                    "(SELECT users.username FROM dont_shoot_the_messenger.users WHERE users.id=messages.sender_id) AS 'Sender',"+"\n"+
			   	                    "(SELECT users.username FROM dont_shoot_the_messenger.users WHERE users.id=messages.receiver_id) AS 'Receiver',"+"\n"+
				                    "messages.text AS 'Message',"+"\n"+
				                    "messages.date_time_sent AS 'Time Sent',"+"\n"+
				                    "messages.viewed AS 'Read or New',"+"\n"+
				                    "messages.sender_view AS 'Status for Sender',"+"\n"+
				                    "messages.receiver_view AS 'Status for Receiver'"+"\n"+
				                    "FROM dont_shoot_the_messenger.messages;";
		
		try {
			Statement stm10 = dbm.getConnection().createStatement();

			ResultSet rs10 = stm10.executeQuery(ViewAllMessagesQuery);
			
			//Showing All Messages Info Headings in a certain format
			System.out.printf("%-15s %-30s %-30s %-30s %-15s %-25s %-25s %-235s","[Message ID]","[Sender]","[Receiver]","[Time Sent]","[Read or New]","[Status for Sender]","[Status for Receiver]","[Message Text 250 charactes Max]");
			System.out.println("\n"+"\n");
			
			if (rs10.next() == false) {
				System.out.println("You dont have any messages!!!"+"\n"+"\n"+"Returning to Inbox Menu...");
				
			} else {
			
			do {
				String Message_id = rs10.getString("Message id");
				String Sender = rs10.getString("Sender");
				String Receiver = rs10.getString("Receiver");
				String Message = rs10.getString("Message");
				String Time_Sent = rs10.getString("Time Sent");
				String Read_or_New = rs10.getString("Read or New");
				String Status_for_Sender = rs10.getString("Status for Sender");
				String Status_for_Sender_Final="";
				String Status_for_Receiver = rs10.getString("Status for Receiver");
				String Status_for_Receiver_Final="";
				
				//Add the ID of the current message to the ArrayList
				AllMessageIDList.add(Message_id);
				
				switch(Status_for_Sender) {
				
				    case "0":
				    	Status_for_Sender_Final="Visible";
				    	break;
				    	
				    case "1":
				    	Status_for_Sender_Final="Deleted (Hidden)";
				    	break;
				    	}
				
				switch(Status_for_Receiver) {
				
			        case "0":
			        	Status_for_Receiver_Final="Visible";
			        	break;
			    	
			        case "1":
			        	Status_for_Receiver_Final="Deleted (Hidden)";
			        	break;
			        	}
				
				//Showing all the messages info in a certain format
				System.out.printf("%-15s %-30s %-30s %-30s %-15s %-25s %-25s %-235s",Message_id,Sender,Receiver,Time_Sent,Read_or_New,Status_for_Sender_Final,Status_for_Receiver_Final,Message);
				System.out.println("\n");

			} while (rs10.next());
			System.out.println("-------------End of all Messages-------------"+"\n");
			}

		} 
		catch (Exception e) {
			// e.printStackTrace();
		}
		return AllMessageIDList;
		
	}
	
	
	
	//06
	//METHOD THAT ALLOWS THE ADMIN TO
    //Case 1.UPDATE THE TEXT OF ANY MESSAGE
	//Case 2.DELETE ANY MESSAGE ENTRIRELY
	public void EditDeleteAllMessages(DBMethods dbm) {
		
		Scanner EditDeleteAllMessagesSC=new Scanner(System.in);
		ArrayList<String> ID_List = new ArrayList<String>();
		String EditOrDeleteContinueChoice="";
		String EditAnotherChoice="";
		String DeleteAnotherChoice="";
		String MessageText="";
		
		String EditDeleteMenu="Please choose one of the following actions"+"\n"+
		                      "1.View All Messages"+"\n"+
		                      "2.Edit a Message's Text"+"\n"+
		                      "3.Delete a Message"+"\n"+
		                      "4.Exit to Previous Menu";

		ID_List=ViewAllMessage(dbm);

		do {
			System.out.println("\n" + EditDeleteMenu + "\n");
			String EditOrDeleteChoice = EditDeleteAllMessagesSC.next();

			// While Loop preventing wrong input
			while (!EditOrDeleteChoice.equals("1") && !EditOrDeleteChoice.equals("2")
					&& !EditOrDeleteChoice.equals("3")&& !EditOrDeleteChoice.equals("4")) {
				System.out.println("\n" + "Invalid Input" + "\n" + EditDeleteMenu + "\n");
				EditOrDeleteChoice = EditDeleteAllMessagesSC.next();
			}

			switch (EditOrDeleteChoice) {
			
			//1.View All Messages
			case "1":
				ViewAllMessage(dbm);
				EditOrDeleteContinueChoice="y";
				break;

			//2.Edit a Message's Text
			case "2":
				do {
				System.out.println("\n" + "Please type in the id of the Sent Message you want to Edit: ");
				String Message_ID = EditDeleteAllMessagesSC.next();

				// Checking if the message id is in the ArrayList
				boolean ID_Checker = ID_List.contains(Message_ID);

				// While Loop for preventing Wrong Message ID Input
				while (ID_Checker == false) {
					System.out.println("\n" + "Invalid Message ID" + "\n"
							+ "Please type in the id of the Message you want to Edit: ");
					Message_ID = EditDeleteAllMessagesSC.next();
					ID_Checker = ID_List.contains(Message_ID);
				}
				
				System.out.println("\n"+"Please type in the new Message text (250 characters max): ");
				
				//Way to put the whole message to a string called 'MessageText'
				MessageText=EditDeleteAllMessagesSC.next();
				MessageText=MessageText+EditDeleteAllMessagesSC.nextLine();
				
				//Replacing the " and ' characters from MessageText with /
				MessageText=MessageText.replace('"','/');
				MessageText=MessageText.replaceAll("'","/");
				
				//Asking for confirmation to Edit the message
				System.out.println("\n"+"Do you want to continue Editing the message text? (y/n): ");
				String MessageEditChoise=EditDeleteAllMessagesSC.next();
				
				//While Loop for preventing Wrong Input
				while(!MessageEditChoise.equalsIgnoreCase("y")&&!MessageEditChoise.equalsIgnoreCase("n")) {
					System.out.println("\n"+"Invalid input"+"\n"+"Are you sure you want to Edit the message text? (y/n): ");
					MessageEditChoise=EditDeleteAllMessagesSC.next();
					}
				
				switch(MessageEditChoise) {
	    		
    		    //Case the User wants to Replace the message
		        case "y":
		        	
		        	//Query that changes the 'text' value to whatever the user inputs
		        	String queryReplaceMessage="UPDATE dont_shoot_the_messenger.messages"+"\n"+
                                               "SET messages.text='"+MessageText+"'"+"\n"+
    			                               "WHERE messages.id="+Message_ID+";";
		        	
					try {
						Statement stm4 = dbm.getConnection().createStatement();

						dbm.executeStatement(queryReplaceMessage);
						
						//Keeping Log of the action
						Log.appendStrToFile("Dont_Shoot_The_Messenger_Log_File.txt", getUsername()+
								            " Edited the Message with ID "+Message_ID+" changing the text to "+MessageText+" at "+timeStamp);

					} catch (Exception e) {
						//e.printStackTrace();
						}
		        	
		        	System.out.println("\n"+"Replacing message..."+"\n"+"Message Replaced"+"\n");
		        	break;
		        	
		        //Case that the user does NOT want to Replace the current message
		        case "n":
		        	System.out.println("\n"+"Skipping message Replacement..."+"\n");
		        	break;
		        	}
    		
    		//Asking whether or not the user wants to edit another message
    		System.out.println("Do you want to edit another message (y/n) ?: ");
    		EditAnotherChoice=EditDeleteAllMessagesSC.next();
    		System.out.print("\n");
    		
    		//While Loop for preventing Wrong Input
    		while(!EditAnotherChoice.equalsIgnoreCase("y")&&!EditAnotherChoice.equalsIgnoreCase("n")) {
    			System.out.println("Invalid input"+"\n"+"Do you want to edit another message (y/n) ?: ");
    			EditAnotherChoice=EditDeleteAllMessagesSC.next();
    			}
    		
			}while(EditAnotherChoice.equalsIgnoreCase("y"));
    		
    		System.out.println("\n"+"Exiting Message Editing..."+"\n"+"\n"+"Returning to Previous Menu...");
    		
    		EditOrDeleteContinueChoice="y";

				break;

			//3.Delete a Message
			case "3":
				
				do {
					System.out.println("\n" + "Please type in the id of the Sent Message you want to Delete: ");
					String Message_ID = EditDeleteAllMessagesSC.next();

					// Checking if the message id is in the ArrayList
					boolean ID_Checker = ID_List.contains(Message_ID);

					// While Loop for preventing Wrong Message ID Input
					while (ID_Checker == false) {
						System.out.println("\n" + "Invalid Message ID" + "\n"
								+ "Please type in the id of the Message you want to Delete: ");
						Message_ID = EditDeleteAllMessagesSC.next();
						ID_Checker = ID_List.contains(Message_ID);
					}
					//Asking for confirmation to Delete the message
					System.out.println("\n"+"Do you want to continue Deleting the message? (y/n): ");
					String MessageDeleteChoise=EditDeleteAllMessagesSC.next();
					
					//While Loop for preventing Wrong Input
					while(!MessageDeleteChoise.equalsIgnoreCase("y")&&!MessageDeleteChoise.equalsIgnoreCase("n")) {
						System.out.println("\n"+"Invalid input"+"\n"+"Are you sure you want to Delete the message? (y/n): ");
						MessageDeleteChoise=EditDeleteAllMessagesSC.next();
						}
					
					switch(MessageDeleteChoise) {
		    		
	    		    //Case the User wants to Replace the message
			        case "y":
			        	
			        	//Query that changes the 'text' value to whatever the user inputs
			        	String queryDeleteMessage="DELETE FROM dont_shoot_the_messenger.messages"+"\n"+
			        	                           "WHERE messages.id='"+Message_ID+"';";
	                                            
			        	
						try {
							Statement stm = dbm.getConnection().createStatement();

							dbm.executeStatement(queryDeleteMessage);
							
							//Keeping Log of the action
							Log.appendStrToFile("Dont_Shoot_The_Messenger_Log_File.txt", getUsername()+
									            " Deleted the Message with ID "+Message_ID+" at "+timeStamp);

						} 
						catch (Exception e) {
							//e.printStackTrace();
						}
			        	
			        	System.out.println("\n"+"Deleting message..."+"\n"+"Message Deleted"+"\n");
			        	break;
			        	
			        //Case that the user does NOT want to Replace the current message
			        case "n":
			        	System.out.println("\n"+"Skipping message Deletion..."+"\n");
			        	break;
			        	}
	    		
	    		//Asking whether or not the user wants to edit another message
	    		System.out.println("Do you want to Delete another message (y/n) ?: ");
	    		DeleteAnotherChoice=EditDeleteAllMessagesSC.next();
	    		System.out.print("\n");
	    		
	    		//While Loop for preventing Wrong Input
	    		while(!DeleteAnotherChoice.equalsIgnoreCase("y")&&!DeleteAnotherChoice.equalsIgnoreCase("n")) {
	    			System.out.println("Invalid input"+"\n"+"Do you want to Delete another message (y/n) ?: ");
	    			DeleteAnotherChoice=EditDeleteAllMessagesSC.next();
	    			}
	    		
				}while(DeleteAnotherChoice.equalsIgnoreCase("y"));
	    		
	    		System.out.println("\n"+"Exiting Message Deleting..."+"\n"+"\n"+"Returning to Previous Menu...");
	    		
	    		EditOrDeleteContinueChoice="y";
				break;

			//4.Exit to Previous Menu
			case "4":
				EditOrDeleteContinueChoice="n";
				break;

			}
		} while (EditOrDeleteContinueChoice.equalsIgnoreCase("y"));
		
		System.out.println("\n"+"Returning to Previous Menu..."+"\n");
		
	}

	
}
	

