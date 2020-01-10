package ie.gmit.sw;

import java.util.ArrayList;

public class Club extends Actor {
	private String email;
	private double funds;
	private ArrayList<Player> players; // populate this by getting it from database on construction?
	
	public Club(int id, String name, String email, double funds) {
		super(id, name);
		this.email = email;
		this.funds = funds;
	}


}
