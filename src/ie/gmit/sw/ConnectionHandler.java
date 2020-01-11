package ie.gmit.sw;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

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
	private double valuation = 0;
	private int index = -1;

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
					send("\t\tWelcome " + userAgent.getName() + "\n\t\t1.Add Player\n\t\t2.Update Player Valuation\n\t\t3.Update Player Status\n\t\tCtrl+C - Quit\n\t\t>");
					option = Integer.parseInt((String)in.readObject());
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
						send("\t\t\tValuation: ");
						player.setValuation(Double.parseDouble((String)in.readObject()));
						send("\t\t\tStatus (1.For Sale 2.Sold 3.Sale Suspended):");
						player.setStatus(Integer.parseInt((String)in.readObject()));
						send("\t\t\tPosition (1.Goalkeeper 2.Defender 3.Midfield 4.Attacker):");
						player.setPosition(Integer.parseInt((String)in.readObject()));					
						db.addPlayer(player);
						break;
					case 2:
						send("\t\t\tPlayer ID: ");
						id = Integer.parseInt((String)in.readObject());
						index = db.containsPlayer(id);
						send("\t\t\tNew Valuation: ");
						valuation = Double.parseDouble((String)in.readObject());
						db.updateValuation(index, valuation);
						break;
					case 3:
						send("\t\t\tPlayer ID: ");
						id = Integer.parseInt((String)in.readObject());
						index = db.containsPlayer(id);
						send("\t\t\tStatus (1.For Sale 2.Sold 3.Sale Suspended):");
						id = Integer.parseInt((String)in.readObject());
						db.updatePlayerStatus(index, id); // int id being reused to hold the status input by user
						break;
					}
				}while(option != 0);
			} else if (userClub != null) { // or successfully logged in as club
				do {
					send("\t\tWelcome " + userClub.getName() + "\n\t\t1.Search players by position\n\t\t2.Show for sale players"
							+ "\n\t\t3.Suspend or resume sale\n\t\t4.Purchase Player\n\t\tCtrl+C to quit\n\t\t>");
					option = Integer.parseInt((String)in.readObject());
					switch(option) {
					case 1:
						int position = 0;
						send("\t\t\tPosition to search by (1.Goalkeeper 2.Defender 3.Midfield 4.Attacker): ");
						position = Integer.parseInt((String)in.readObject());
						ArrayList<Player> resultSet = db.searchPlayersByPosition(position);
						String results = "";
						for(Player p : resultSet) {
							results += p.getName() + " ";
						}
						send(results);
						break;
					}
					
					
					
					
				}while(option != 0);
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
