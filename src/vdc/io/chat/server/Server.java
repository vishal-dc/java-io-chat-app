package vdc.io.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	ServerSocket serverSocket = null;
	private MessageRouter messageRouter;

	public static void main(String a[]) {
		// server = null;
		try {
			Server server = new Server();
			server.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Server() throws IOException {
		this(9999);
	}

	public Server(int port) throws IOException {
		this.serverSocket = new ServerSocket(port);
		this.messageRouter = new MessageRouter();

	}

	public void start() {
		try {
			System.out.println("Starting message router...");
			MessageRouterThread router = new MessageRouterThread(this.messageRouter);
			router.start();

			System.out.println("Waiting for Client Request");
			while (true) {
				Socket socket = this.serverSocket.accept();
				System.out.println("Got a request : " + socket);

				ClientHandler clientHandler = new ClientHandler(socket, this.messageRouter);
				System.out.println("Created new client handler id: " + clientHandler.getId());

				clientHandler.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
