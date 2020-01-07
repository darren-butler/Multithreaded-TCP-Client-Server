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
	private String message;
	private int result;

	public ConnectionHandler(Socket connection, int socketID) {
		this.connection = connection;
		this.socketID = socketID;
	}

	@Override
	public void run() {

		try {
			out = new ObjectOutputStream(connection.getOutputStream());
			out.flush();
			in = new ObjectInputStream(connection.getInputStream());

			System.out.println("Connection " + socketID + " from IP: " + connection.getInetAddress());

			do {
				send("SHALL WE PLAY A GAME?\n1.Chess\n2.Global Thermonuclear War");
				message = (String) in.readObject();
				System.out.println(message);

			} while (true); // client response is invalid

			// switch based on client option

		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void send(String str) throws IOException {
		out.writeObject(str);
		out.flush();
		//System.out.println("client>" + str);
	}

}
