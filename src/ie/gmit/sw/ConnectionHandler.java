package ie.gmit.sw;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectionHandler implements Runnable {

	private Socket connection;
	private int socketID;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private String str;
	private int option = 0;
	private int toggle = 0;
	private int id = 0;
	private String name = null;
	private Agent userAgent = null;
	private Club userClub = null;
	private boolean isLoggedIn = false;

	private Database db = null;

	public ConnectionHandler(Socket connection, int socketID, Database db) {
		this.connection = connection;
		this.socketID = socketID;
		this.db = db;
	}

	
	@Override
	public void run() {

		try {
			out = new ObjectOutputStream(connection.getOutputStream());
			out.flush();
			in = new ObjectInputStream(connection.getInputStream());

			System.out.println("Connection " + socketID + " from IP: " + connection.getInetAddress());

			do {
				do {
					str = "1.Register\n2.Login\n>";
					send(str);

					str = (String) in.readObject();
					option = Integer.parseInt(str);

				} while (option != 1 && option != 2); // keep user locked in this loop until they input either 1 or 2

				switch (option) {
				case 1:// Register
					toggle = 0; // init toggle - toggle is used to determine whether the user is registering as a new agent or club
					send("\tRegistration Menu\n\tUser type (1-Agent/2-Club): ");
					toggle = Integer.parseInt((String) in.readObject());
					
					send("\tID: "); // TODO auto generate from database?
					id = Integer.parseInt((String) in.readObject());

					send("\tName: ");
					name = (String) in.readObject();

					send("\tEmail: ");
					String email = (String) in.readObject();
					
					if (toggle == 1 && db.containsAgent(id, name) == -1) { // toggle - 1: new agent registration
						Agent agent = new Agent(id, name, email); // TODO: check if id/name are unique before adding to database																	// database
						db.addAgent(agent);
					} else if (toggle == 2 && db.containsClub(id, name) == -1) { // toggle - 2: new club registration
						send("\tFunds: ");
						double funds = Double.parseDouble((String) in.readObject());
						Club club = new Club(id, name, email, funds);
						db.addClub(club);
					} else {
						send("ERROR - ID/NAME TAKEN - Input ANYKEY+ENTER to return");
						String dump = (String) in.readObject();
					}
					break;
				case 2:// Login
					toggle = 0;
					send("\tLogin Menu\n\tUser type (1-Agent/2-Club): ");
					toggle = Integer.parseInt((String) in.readObject());

					send("\tID: ");
					id = Integer.parseInt((String) in.readObject());
					send("\tName: ");
					name = (String) in.readObject();
					
					if (toggle == 1 && db.containsAgent(id, name) > -1) { // if logging in as agent, search agent arraylist for input name & id to verify login
						isLoggedIn = true;
						userAgent = db.getAgents().get(db.containsAgent(id, name));
					} else if (toggle == 2 && db.containsClub(id, name) > -1) { // if logging in as club search clubs arralist for input name & id to verify login
						isLoggedIn = true;
						userClub =  db.getClubs().get(db.containsClub(id, name));
					}
					break;
				}
			} while (!isLoggedIn); // do/while - loops until correct login credentials input

			if (userAgent != null) { // if - successfully logged in as agent
				do {
					send("\t\tWelcome " + userAgent.getName() + "\n\t\t1.Add Player\n\t\t2.Update Player Valuation\n\t\t3.Update Player Status\n\t\t>");
					option = Integer.parseInt((String)in.readObject());
				}while(option != 1 && option != 2 && option != 3);

				switch(option) {
				case 1:
					send("\t\tAdd Player Menu\n\t\t\tName: ");
					name = (String) in.readObject();
					send("\t\t\tID: ");
					id = Integer.parseInt((String)in.readObject());
					Player player = new Player(id, name);
					send("\t\t\tAge: ");
					player.setAge(Integer.parseInt((String) in.readObject()));
					send("\t\t\tClub ID: ");
					player.setClubID(Integer.parseInt((String) in.readObject()));
					player.setAgentID(userAgent.getId()); // set the player's agent ID to the agent logged in who is adding the player?
//					send("\t\t\tAgent ID: ");
//					player.setAgentID(Integer.parseInt((String) in.readObject()));
					send("\t\t\tValuation: ");
					player.setValuation(Double.parseDouble((String)in.readObject()));
					send("\t\t\tStatus (1.For Sale 2.Sold 3.Sale Suspended):");
					player.setStatus(Integer.parseInt((String)in.readObject()));
					send("\t\t\tPosition (1.Goalkeeper 2.Defender 3.Midfield 4.Attacker):");
					player.setPosition(Integer.parseInt((String)in.readObject()));
					
					userAgent.setPlayer(player);
					id = db.getClubByID(player.getClubID());
					
					db.getClubs().get(player.getClubID()).getPlayers().add(player); // overengineered
					db.backupDatabase();
					break;
				case 2:
					break;
				case 3:
					break;
				}
				
				
				
				
				
				
				
				
				
				
				
				

			} else if (userClub != null) { // or successfully logged in as club
				send("\t\tWelcome " + userClub.getName() + "!");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void send(String str) throws IOException {
		out.writeObject(str);
		out.flush();
	}

}
