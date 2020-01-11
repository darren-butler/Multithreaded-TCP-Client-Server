package ie.gmit.sw;

public class Agent extends Actor{
	private String email;
	private Player player;
	
	public Agent(int id, String name, String email) {
		super(id, name);
		this.email = email;
		this.player = null;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Agent [email=" + email + "]";
	}

}
