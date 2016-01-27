package vdc.io.chat.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.SynchronousQueue;

import vdc.io.chat.commons.Client;
import vdc.io.chat.commons.ClientListMessage;
import vdc.io.chat.commons.Message;
import vdc.io.chat.commons.utils.MessageUtils;

public class MessageRouter {
	BlockingQueue<Message> messageQueue = new SynchronousQueue<>();

	private Map<Long, ClientHandler> clientHandlers = new ConcurrentHashMap<>();

	public void registerClientHandler(ClientHandler clientHandler) {
		clientHandlers.put(clientHandler.getId(), clientHandler);
		broadcastClientList();

	}

	private void broadcastClientList() {

		ClientListMessage msg = MessageUtils.createClientListMessage(getClientsList());
		// get lock
		for (ClientHandler clientHandler : clientHandlers.values()) {
			try {
				clientHandler.addMessage(msg);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	private Collection<Client> getClientsList() {
		ArrayList<Client> clients = new ArrayList<>();
		for (ClientHandler handler : clientHandlers.values()) {
			clients.add(handler.getClient());
		}

		return Collections.unmodifiableCollection(clients);

	}

	public void deRegisterClientHandler(ClientHandler clientHandler) {
		clientHandlers.remove(clientHandler.getId());
		broadcastClientList();
	}

	public void routeNextMessage() throws InterruptedException {
		Message message = this.getNextMessage();
		System.out.println("Message Received: " + message);
		ClientHandler client = this.resoveRecepient(message);

		if (client != null) {
			client.addMessage(message);
		} else {
			System.out.println("Client Not found!!");
			System.out.println("Message: " + message);
		}

	}

	private Message getNextMessage() throws InterruptedException {
		return this.messageQueue.take();
	}

	public ClientHandler resoveRecepient(Message message) {
		// String id = message.substring(0, message.indexOf(":"));
		Client recepient = message.getRecepient();
		if (recepient == null)
			return null;
		return clientHandlers.get(recepient.getId());
	}

	public void processMessage(Message message) throws InterruptedException {
		messageQueue.put(message);
	}

}
