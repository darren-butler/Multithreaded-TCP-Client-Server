package ie.gmit.sw;

public class Player extends Actor{

	private int age;
	private int clubID;
	private int agentID;
	private double valuation;
	private int status;
	private int position;
	
	public Player(int id, String name) {
		super(id, name);
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getClubID() {
		return clubID;
	}

	public void setClubID(int clubID) {
		this.clubID = clubID;
	}

	public int getAgentID() {
		return agentID;
	}

	public void setAgentID(int agentID) {
		this.agentID = agentID;
	}

	public double getValuation() {
		return valuation;
	}

	public void setValuation(double valuation) {
		this.valuation = valuation;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
	
	

}
