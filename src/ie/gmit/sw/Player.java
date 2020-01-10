package ie.gmit.sw;

public class Player extends Actor{

	private int age;
	private int clubID;
	private int agentID;
	private double valuation;
	private int status;
	private int position;
	
	public Player(int id, String name, int age, int clubID, int agentID, double valuation, int status, int position) {
		super(id, name);
		this.age = age;
		this.clubID = clubID;
		this.agentID = agentID;
		this.valuation = valuation;
		this.status = status;
		this.position = position;
	}
	
}
