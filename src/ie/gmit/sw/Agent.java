package ie.gmit.sw;

public class Agent extends Actor{
	private String email;

	public Agent(int id, String name, String email) {
		super(id, name);
		this.email = email;
	}

}
