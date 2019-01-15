package dontshootthemessenger;

public class DSTMMain {

	public static void main(String[] args) {
		
		Menu menu=new Menu();
		DBMethods dbm=new DBMethods();
		
		//Don't Shoot The Messenger Login Menu + ALL
		menu.LoginMainMenu(dbm);
		
	}

}
