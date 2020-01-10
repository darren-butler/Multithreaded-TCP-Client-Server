package ie.gmit.sw;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) {

		final int PORT = 10000;
		final int BACKLOG = 10;
		ServerSocket listener;
		int clientID = 0;
		Database db = new Database(); // shared object

		try {
			listener = new ServerSocket(PORT, BACKLOG);
			System.out.println("server listening for incoming connections...");

			while (true) {
				Socket connection = listener.accept();
				
				System.out.println("new connection received... spawning thread...");
				ConnectionHandler connectionHandler = new ConnectionHandler(connection, clientID, db);
				clientID++;
				
				new Thread(connectionHandler).start();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
