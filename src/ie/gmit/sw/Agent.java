package ie.gmit.sw;

public class Agent extends Actor{
	private String email;
	
	public Agent(int id, String name, String email) {
		super(id, name);
		this.email = email;
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
