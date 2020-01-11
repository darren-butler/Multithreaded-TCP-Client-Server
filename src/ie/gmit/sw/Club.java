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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public double getFunds() {
		return funds;
	}

	public void setFunds(double funds) {
		this.funds = funds;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}


}
