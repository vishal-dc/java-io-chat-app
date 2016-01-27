package vdc.io.chat.commons;

import java.io.Serializable;

public class Client implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// transient private static long counter = 0;
	private long id = 0;

	public Client() {
		// id = ++counter;
	}

	public Client(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;

	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n Client Id: ").append(id);
		return sb.toString();
	}
}
