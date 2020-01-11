package ie.gmit.sw;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {

	private Socket connection;
	private String ip;
	private int port;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Scanner console;
	private String message;

	public Client() {
		console = new Scanner(System.in);

		System.out.println("IP: ");
		ip = console.next();
		System.out.println("Port: ");
		port = console.nextInt();
		// TODO remove hardcode
//		ip = "127.0.0.1";
//		port = 10000;

	}

	public static void main(String[] args) {

		Client client = new Client();
		client.runApplication();

	}

	public void runApplication() {

		try {
			connection = new Socket(ip, port);

			out = new ObjectOutputStream(connection.getOutputStream());
			out.flush();
			in = new ObjectInputStream(connection.getInputStream());
			System.out.println("client/server connection established...");

			do {
				message = (String) in.readObject();
				System.out.print(message);
				message = console.next();
				send(message);

			} while (true);

		} catch (Exception e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
		}

	}

	private void send(String str) throws IOException {
		out.writeObject(str);
		out.flush();
	}
}
