package vdc.io.chat.commons;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import vdc.io.chat.commons.utils.MessageConverter;
import vdc.io.chat.commons.utils.MessageUtils;

public class Protocollmpl {
	// private InputStream is;
	// private OutputStream os;
	private DataInputStream dis;
	// private BufferedInputStream bis;
	private DataOutputStream dos;
	// private BufferedOutputStream bos;

	private int BUFFER_SIZE = 8 * 1024;
	private byte incomingBuffer[] = new byte[BUFFER_SIZE];
	// private byte outgoingBuffer[] = new byte[BUFFER_SIZE];
	private Client client;

	// private Socket socket = null;
	public Protocollmpl(Client client, InputStream is, OutputStream os) throws IOException {
		// this.is = is;
		// this.os = os;
		this.dis = new DataInputStream(is);
		// this.bis = new BufferedInputStream(is);
		this.dos = new DataOutputStream(os);
		this.client = client;
		// this.bos = new BufferedOutputStream(os);
	}

	public void sendMessage(Message message) throws IOException {

		byte bytes[] = MessageConverter.marshall(message);
		int length = bytes.length;

		if (length <= BUFFER_SIZE) {
			dos.writeInt(length);
			dos.write(bytes);
			dos.flush();

		} else {
			// large object
		}

	}

	public Message receiveMessage() throws IOException, ClassNotFoundException {
		int length = dis.readInt();

		Message message = null;
		if (checkForObject(length)) {
			if (length > BUFFER_SIZE) {
				// multiple read
				// write to a temp file

				for (int i = length / BUFFER_SIZE; i >= 0; i--) {
					dis.readFully(incomingBuffer);
				}
				length = length % BUFFER_SIZE;
			}
			// read length
			dis.read(incomingBuffer, 0, length);
			message = MessageConverter.unMarshall(incomingBuffer);

		} else {
			// process stream of unknown length
			throw new RuntimeException("Unknown content length received");

		}

		return message;
	}

	private boolean checkForObject(int length) {
		return length != -1;
	}

	public void login() throws IOException, ClassNotFoundException {
		Message message = MessageUtils.createLoginMessage(this.client);
		sendMessage(message);
		Message loginReply = receiveMessage();
		this.client.setId(loginReply.getSender().getId());

	}

}
