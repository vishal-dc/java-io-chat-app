package vdc.io.chat.commons;

import java.util.ArrayList;
import java.util.List;

public class ClientListMessage extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ClientListMessage() {
		super(null, null, Message.Type.CLIENT_LIST);
	}

	List<Client> clients = new ArrayList<>();

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n Client List: ").append(this.clients);
		return sb.toString();
	}

	public void addClient(Client client) {
		this.clients.add(client);
	}

}
