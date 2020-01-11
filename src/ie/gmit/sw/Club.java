package ie.gmit.sw;

public class Club extends Actor {
	private String email;
	private double funds;

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

}
