package dontshootthemessenger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class DBMethods {
	
	Connection connection;
	DriverManager dm;
	Statement stm;

	
	public DBMethods() {}
	
	
	
	public Connection getConnection() {
		return connection;
	}



	public void setConnection(Connection connection) {
		this.connection = connection;
	}



	//METHOD TO ESTABLISH CONNECTION WITH THE DATABASE
	public void connect(String DBurl, String username, String password) {
		
		try {
			connection = DriverManager.getConnection(DBurl, username, password);
			System.out.println("Established connection with DataBase");
			}
		
		catch (SQLException e) {
			e.printStackTrace();//<---Tells what exception occurred during the run of the program
			System.out.println("There was a problem with the Database connection");// <---message to print when the
			}                                                                     //           sql connection fails
		
		System.out.println("Connection signature: "+connection);
		}
	
	
	
	//METHOD THAT EXCECUTES A QUERY STATEMENT
	public int executeStatement(String sql) {

			try {
				stm = connection.createStatement();
				return stm.executeUpdate(sql);
				}

			catch (SQLException e) {
				e.printStackTrace();
				System.out.println("There was a problem with the Query Statement");// <---message to print when
				}                                                                  // the query was not executed

			return 1;// <-----having the return statement show '1' if the Query was executed successfully
			}
	
	
	
	//METHOD THAT CREATES THE D.S.T.M. DATABASE (EMPTY)
	public void CreateEmptyDB() {
		
		
		System.out.println("\n"+"Creating 'dont_shoot_the_messenger' Database...");
		
		//Creating an empty Database named 'dont_shoot_the_messenger' if it doesn't exist
		executeStatement("CREATE DATABASE IF NOT EXISTS `dont_shoot_the_messenger`;");
		
		System.out.println("\n"+"Creating 'users' Table...");
		
		//Creating an empty table 'users' in the empty Database if it doesn't exist
		executeStatement("CREATE TABLE IF NOT EXISTS `dont_shoot_the_messenger`.`users`"+"\n"+
				         "("+"\n"+
				         "`id` INT NOT NULL AUTO_INCREMENT,"+"\n"+
				         "`username` VARCHAR(30) NOT NULL,"+"\n"+
				         "`password` VARCHAR(30) NOT NULL,"+"\n"+
				         "`classification` VARCHAR(25) NOT NULL,"+"\n"+
				         "`last_account_info_update` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,"+"\n"+
				         "PRIMARY KEY (`id`),"+"\n"+
				         "UNIQUE INDEX `username_UNIQUE` (`username` ASC)"+"\n"+
				         ")"+"\n"+
				         "ENGINE = InnoDB"+"\n"+
				         "DEFAULT CHARACTER SET = utf8;");
                         
		System.out.println("\n"+"Creating 'messages' Table...");
		
		//Creating an empty table 'messages' in the empty Database if it doesn't exist
		executeStatement("CREATE TABLE IF NOT EXISTS `dont_shoot_the_messenger`.`messages`"+"\n"+
				         "("+"\n"+ 
				         "`id` INT(11) NOT NULL AUTO_INCREMENT,"+"\n"+
				         "`sender_id` INT(11) NOT NULL,"+"\n"+
				         "`receiver_id` INT(11) NOT NULL,"+"\n"+
				         "`text` VARCHAR(250) NOT NULL,"+"\n"+
				         "`date_time_sent` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,"+"\n"+
				         "`viewed` VARCHAR(15) CHARACTER SET 'utf8' COLLATE 'utf8_bin' NOT NULL DEFAULT 'New',"+"\n"+
				         "`sender_view` VARCHAR(15) CHARACTER SET 'utf8' COLLATE 'utf8_bin' NOT NULL DEFAULT '0',"+"\n"+
				         "`receiver_view` VARCHAR(15) CHARACTER SET 'utf8' COLLATE 'utf8_bin' NOT NULL DEFAULT '0',"+"\n"+
				         "PRIMARY KEY (`id`)"+"\n"+
				         ")"+"\n"+
				         "ENGINE = InnoDB"+"\n"+
				         "DEFAULT CHARACTER SET = utf8;");
				
		System.out.println("\n"+"Database dont_shoot_the_messenger was succesfully created");
		}
	
	
	
	//METHOD THAT POPULATES THE 'user' TABLE
	public void PopulateEmptyDB() {
	
		System.out.println("\n"+"Populating 'users' Table...");
		
		//Populating the 'users' Table
		executeStatement("INSERT IGNORE INTO `dont_shoot_the_messenger`.`users`"+"\n"+
		                 "(`username`, `password`, `classification`)"+"\n"+
	                     "VALUES ('admin', 'admin', 'Administrator'),"+"\n"+
	                     "('Nikos87!', 'Nikos2018!', 'Premium User')," +"\n"+
			             "('Giorgos97!', 'Giorgos2018!', 'Premium User')," +"\n"+
			             "('Xaris61!', 'Xaris2018!', 'Advanced User')," +"\n"+
			             "('Ritsa2Ep!', 'Ritsa2018!', 'Advanced User')," +"\n"+
			             "('Giannis91!', 'Giannis2018!', 'User'),"+"\n"+
			             "('user', 'test', 'User');");
		
		System.out.println("\n"+"Populating 'messages' Table...");
		
		executeStatement("INSERT IGNORE INTO `dont_shoot_the_messenger`.`messages`"+"\n"+
		                 "(`id`,`sender_id`, `receiver_id`, `text`)"+"\n"+
				         "VALUES ('1','1', '2', 'Hi Nikos87! I\\'m admin'),"+"\n"+
				         "('2','1', '3', 'Hi Giorgos97! I\\'m admin'),"+"\n"+
		                 "('3','1', '4', 'Hi Xaris61! I\\'m admin'),"+"\n"+
				         "('4','1', '5', 'Hi Ritsa2Ep! I\\'m admin'),"+"\n"+
		                 "('5','1', '6', 'Hi Giannis91! I\\'m admin'),"+"\n"+
				         "('6','2', '1', 'Hi admin I\\'m Nikos87!'),"+"\n"+
		                 "('7','2', '3', 'Hi Giorgos97! I\\'m Nikos87!'),"+"\n"+
				         "('8','2', '4', 'Hi Xaris61! I\\'m Nikos87!'),"+"\n"+
		                 "('9','2', '5', 'Hi Ritsa2Ep! I\\'m Nikos87!'),"+"\n"+
				         "('10','2', '6', 'Hi Giannis91! I\\'m Nikos87!'),"+"\n"+
		                 "('11','3', '1', 'Hi admin I\\'m Giorgos97!'),"+"\n"+
				         "('12','3', '2', 'Hi Nikos87! I\\'m Giorgos97!'),"+"\n"+
		                 "('13','3', '4', 'Hi Xaris61! I\\'m Giorgos97!'),"+"\n"+
				         "('14','3', '5', 'Hi Ritsa2Ep! I\\'m Giorgos97!'),"+"\n"+
		                 "('15','3', '6', 'Hi Giannis91! I\\'m Giorgos97!'),"+"\n"+
				         "('16','4', '1', 'Hi admin I\\'m  Xaris61!'),"+"\n"+
		                 "('17','4', '2', 'Hi Nikos87! I\\'m Xaris61!'),"+"\n"+
				         "('18','4', '3', 'Hi Giorgos97! I\\'m Xaris61!'),"+"\n"+
		                 "('19','4', '5', 'Hi Ritsa2Ep! I\\'m Xaris61!'),"+"\n"+
				         "('20','4', '6', 'Hi Giannis91! I\\'m Xaris61!'),"+"\n"+
		                 "('21','5', '1', 'Hi admin I\\'m Ritsa2Ep!'),"+"\n"+
				         "('22','5', '2', 'Hi Nikos87! I\\'m Ritsa2Ep!'),"+"\n"+
		                 "('23','5', '3', 'Hi Giorgos97! I\\'m Ritsa2Ep!'),"+"\n"+
				         "('24','5', '4', 'Hi Xaris61! I\\'m Ritsa2Ep!'),"+"\n"+
		                 "('25','5', '6', 'Hi Giannis91! I\\'m Ritsa2Ep!'),"+"\n"+
				         "('26','6', '1', 'Hi admin I\\'m Giannis91!'),"+"\n"+
		                 "('27','6', '2', 'Hi Nikos87! I\\'m Giannis91!'),"+"\n"+
				         "('28','6', '3', 'Hi Giorgos97! I\\'m Giannis91!'),"+"\n"+
		                 "('29','6', '4', 'Hi Xaris61! I\\'m Giannis91!'),"+"\n"+
				         "('30','6', '5', 'Hi Ritsa2Ep! I\\'m Giannis91!');");
		
		System.out.println("\n"+"Database dont_shoot_the_messenger Tables were succesfully populated");
		}
}