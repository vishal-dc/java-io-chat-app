package vdc.io.chat.client;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import vdc.io.chat.commons.Client;
import vdc.io.chat.commons.Message;
import vdc.io.chat.commons.Protocollmpl;

public class ChatClient implements Closeable {

	private Socket socket = null;
	private MessageReader messageReader = null;
	private MessageWriter messageWriter = null;
	private Client clientId = null;

	private Protocollmpl protocol;

	public static void main(String[] args) throws ClassNotFoundException {
		try (ChatClient client = new ChatClient()) {
			client.start();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	class ClientReader implements Runnable {
		public void run() {
			try {
				Message message = null;
				while ((message = protocol.receiveMessage()) != null) {
					// message = bf.readLine();
					messageWriter.writeMessage(message);

					// if (message.equals("Bye")) {
					// return;
					// }
				}

			} catch (IOException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException(e);

			} finally {
			}
		}
	}

	class ClientWriter implements Runnable {
		public void run() {
			try {
				Message message = null;
				long recepientId = 0;
				while (true) {
					while (recepientId == 0) {
						try {
							messageWriter.writeText("Recepient Id: ");
							recepientId = messageReader.getId();
						} catch (NumberFormatException e) {

						}
					}
					messageWriter.writeText("Message to Id:" + recepientId + ": ");
					message = messageReader.getNextMessage(recepientId);
					protocol.sendMessage(message);
					recepientId = 0;
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException(e);
			} finally {
			}
		}
	}

	private void initConnection() throws UnknownHostException, IOException, ClassNotFoundException {
		socket = new Socket(InetAddress.getLocalHost(), 9999);
		protocol = new Protocollmpl(this.clientId, socket.getInputStream(), socket.getOutputStream());

		try {
			protocol.login();
			messageWriter.writeText("Client logged in with ID: " + this.clientId.getId());

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		}

	}

	public void start() throws UnknownHostException, IOException, ClassNotFoundException {
		initConnection();
		// reader
		new Thread(new ClientReader(), "ClientReader").start();

		// writer
		new Thread(new ClientWriter(), "ClientWriter").start();

	}

	public ChatClient() throws UnknownHostException, IOException {
		clientId = new Client();
		messageReader = new ConsoleMessageReader(this.clientId);
		messageWriter = new ConsoleMessageWriter();

	}

	@Override
	public void close() throws IOException {
		// socket.close();

	}

}
