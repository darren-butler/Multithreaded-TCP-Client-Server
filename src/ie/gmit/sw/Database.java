package ie.gmit.sw;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import java.lang.reflect.Type;

/*
 * This Class has three ArrayLists, agents, clubs and players
 * These lists are accessed in a thread safe way by using synchronized methods
 * These lists are then backed up by converting them to JSON and storing in serverside .txt files
 * Googles GSON library was used for serializing and deserializing java lists into JSON
 */
public class Database {
	File agentsFile = new File("agents.txt");
	File clubsFile = new File("clubs.txt");
	File playersFile = new File("players.txt");

	private ArrayList<Agent> agents;
	private ArrayList<Club> clubs;
	private ArrayList<Player> players;

	public Database() {
		JsonReader jr;
		Gson gson;

		try {
			agentsFile.createNewFile(); // if file does not already exist this will create it

			if (agentsFile.length() == 0) { // if existing txt file is empty, (database is empty), initialize a new empty list
				agents = new ArrayList<>();
			} else {
				jr = new JsonReader(new FileReader(agentsFile));
				Type tempAgent = new TypeToken<ArrayList<Agent>>() { // tempAgent is used along with the data to flag the list Types
				}.getType();
				gson = new Gson();
				agents = gson.fromJson(jr, tempAgent); // assign Database list to list deserialized from txt file
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		try { // same process as above
			clubsFile.createNewFile();

			if (clubsFile.length() == 0) {
				clubs = new ArrayList<>();
			} else {
				jr = new JsonReader(new FileReader(clubsFile));
				Type tempClub = new TypeToken<ArrayList<Club>>() {
				}.getType();
				gson = new Gson();
				clubs = gson.fromJson(jr, tempClub);
			}

		} catch (Exception e) {
			e.printStackTrace();

		}

		try { // same process as above
			playersFile.createNewFile();

			if (playersFile.length() == 0) {
				players = new ArrayList<>();
			} else {
				jr = new JsonReader(new FileReader(playersFile));
				Type tempPlayer = new TypeToken<ArrayList<Player>>() {
				}.getType();
				gson = new Gson();
				players = gson.fromJson(jr, tempPlayer);
			}

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	// This method is only called by synchronized methods, so no need to synchronize it itself
	public void backupDatabase() throws FileNotFoundException { 
		Gson gson = new Gson();
		String json = gson.toJson(agents); // convert list to String 
		PrintWriter pw = new PrintWriter(new FileOutputStream(agentsFile));
		pw.print(json); // write list String to file
		pw.close();

		json = gson.toJson(clubs);
		pw = new PrintWriter(new FileOutputStream(clubsFile));
		pw.print(json);
		pw.close();

		json = gson.toJson(players);
		pw = new PrintWriter(new FileOutputStream(playersFile));
		pw.print(json);
		pw.close();
	}

	public ArrayList<Agent> getAgents() {
		return agents;
	}

	public ArrayList<Club> getClubs() {
		return clubs;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	// Adds an agent to the Java list, then backs up the json txt file
	public synchronized void addAgent(Agent agent) throws FileNotFoundException {
		agents.add(agent);
		backupDatabase();
	}

	public synchronized void addClub(Club club) throws FileNotFoundException {
		clubs.add(club);
		backupDatabase();
	}

	public synchronized void addPlayer(Player player) throws FileNotFoundException {
		players.add(player);
		backupDatabase();
	}

	public int containsAgent(int id, String name) { // if id & name of agent exist in list, return the index else -1
		for (Agent a : agents) {
			if (a.getId() == id && a.getName().equalsIgnoreCase(name)) {
				return agents.indexOf(a);
			}
		}

		return -1;
	}

	public int containsClub(int id, String name) {
		for (Club c : clubs) {
			if (c.getId() == id && c.getName().equalsIgnoreCase(name)) {
				return clubs.indexOf(c);
			}
		}

		return -1;
	}
	
	public int containsPlayer(int id) {
		for(Player p : players) {
			if(p.getId() == id);
			return players.indexOf(p);
		}
		return -1;
	}

	public int getClubByID(int id) {
		for (Club c : clubs) {
			if (c.getId() == id) {
				return clubs.indexOf(c);
			}
		}
		return -1;
	}
	
	public synchronized void updateValuation(int index, double newValuation) throws FileNotFoundException {
		players.get(index).setValuation(newValuation);
		backupDatabase();
	}
	
	public synchronized void updatePlayerStatus(int index, int newStatus) throws FileNotFoundException {
		players.get(index).setStatus(newStatus);
		backupDatabase();
	}
	
	public ArrayList<Player> searchPlayersByPosition(int position) {
		ArrayList<Player> result = new ArrayList<>();
		for(Player p : players) {
			if(p.getPosition() == position) {
				result.add(p);
			}
		}
		
		return result;
	}

}
