package vdc.io.chat.commons;

public class TextMessage extends Message {

	private String text;

	public TextMessage(Client sender, Client recepient, String message) {
		this(sender, recepient, Type.NORMAL, message);
	}

	/**
	 * @param sender
	 * @param recepient
	 * @param type
	 */
	public TextMessage(Client sender, Client recepient, Type type, String message) {
		super(sender, recepient, type);
		this.text = message;

	}

	public TextMessage(Client sender, Type type) {
		this(sender, null, type, null);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getText() {
		return this.text;
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
		sb.append(super.toString()).append("\nMessage: ").append(this.getText());
		return sb.toString();
	}

}
