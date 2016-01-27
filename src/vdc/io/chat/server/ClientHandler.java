package vdc.io.chat.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

import vdc.io.chat.commons.Client;
import vdc.io.chat.commons.Message;
import vdc.io.chat.commons.Protocollmpl;

public class ClientHandler {
	private Socket socket = null;
	private static long idCounter = 0;
	private BlockingQueue<Message> incomingMessages = new SynchronousQueue<>();
	// private BlockingQueue<String> outgoingMessages= new SynchronousQueue<>();
	private MessageRouter router;
	// representing the client on server side
	private Client client;
	private Protocollmpl protocol;

	public void addMessage(Message message) throws InterruptedException {
		incomingMessages.put(message);
	}

	public ClientHandler(Socket clientSocket, MessageRouter router) throws IOException {
		this(clientSocket.getInputStream(), clientSocket.getOutputStream(), router);
		socket = clientSocket;
	}

	public ClientHandler(InputStream is, OutputStream os, MessageRouter router) throws IOException {
		client = new Client(++idCounter);
		this.router = router;
		protocol = new Protocollmpl(client, is, os);

	}

	public void start() throws IOException {
		
		// reader
		new Thread(new ClientReader()).start();

		// writer
		new Thread(new ClientWriter()).start();
		
	}

	class ClientReader implements Runnable {
		public void run() {
			try {
				Message message = null;
				while ((message = protocol.receiveMessage()) != null) {
					// message = bf.readLine();
					Client recepient = message.getRecepient();
					if (recepient == null && message.getType().equals(Message.Type.LOGIN)) {
						message.getSender().setId(ClientHandler.this.client.getId());
						incomingMessages.add(message);
						router.registerClientHandler(ClientHandler.this);
					} else {
						if (message != null) {

							try {
								router.processMessage(message);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}

			} catch (IOException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				router.deRegisterClientHandler(ClientHandler.this);
			}
		}
	}

	class ClientWriter implements Runnable {
		public void run() {
			try {
				Message message = null;
				while (true) {
					try {
						message = incomingMessages.take();
						protocol.sendMessage(message);
						// pw.println(message);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				router.deRegisterClientHandler(ClientHandler.this);
			}
		}
	}

	public long getId() {
		return this.client.getId();
	}

	public Client getClient() {
		return this.client;
	}

}