package ie.gmit.sw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Database {
	private File dataFile = new File("Data.txt");

	//private ArrayList<Player> players;
	private ArrayList<Club> clubs;
	private ArrayList<Agent> agents;

	private ArrayList<Integer> testInts = new ArrayList<Integer>();

	public Database() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(dataFile));

			String str;
			while ((str = br.readLine()) != null) {
				testInts.add(Integer.parseInt(str));
			}

			br.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public ArrayList<Integer> getTestInts() {
		return testInts;
	}
	
	public synchronized void addPlayer() {
		//TODO
	}
	
	public synchronized void updatePlayerValuation() {
		
	}
	
	public synchronized void updatePlayerStatus() {
		
	}
	
	public synchronized Player getPlayer(int playerID) {
		return null;
	}
	
//	public synchronized Player getPlayer(int status) {
//		return null;
//	}
	
	public synchronized void buyPlayer() {
		
	}

}
