package vdc.io.chat.commons;

import java.io.Serializable;

public abstract class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// private static long counter = 0;

	private Client sender = null;

	private Client recepient = null;
	private long id = 0;

	private Type type = Type.NORMAL;

	public enum Type {
		LOGIN,

		BROADCAST,

		NORMAL,
		
		CLIENT_LIST

	}

	public Message(Client sender, Client recepient) {
		this.sender = sender;
		this.recepient = recepient;
		// this.id = ++counter;
	}

	public Message(Client sender, Client recepient, Type type) {
		this(sender, recepient);
		this.type = type;
	}

	public Message(Client sender, Type type) {
		this(sender, null, type);
	}

	public Client getSender() {
		return sender;
	}

	public Client getRecepient() {
		return recepient;
	}

	public long getId() {
		return id;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public void setid(long id) {
		this.id = id;
	}

	// private void writeObject(ObjectOutputStream os) throws IOException {
	//
	// os.defaultWriteObject();
	//
	// }
	//
	// private void readObject(ObjectInputStream is) throws
	// ClassNotFoundException, IOException {
	// is.defaultReadObject();
	// }
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Sender: ").append(sender.toString()).append("\nRecepient: ").append(recepient.toString())
				.append("\nType: ").append(type);

		return sb.toString();
	}

}
