package vdc.io.chat.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import vdc.io.chat.commons.Client;
import vdc.io.chat.commons.TextMessage;
import vdc.io.chat.commons.utils.MessageUtils;

public class MessageReader {
	BufferedReader reader = null;
	private Client sender = null;

	// public MessageReader() {
	// // TODO Auto-generated constructor stub
	// }

	public MessageReader(InputStreamReader reader, Client sender) {
		this(new BufferedReader(reader), sender);
	}

	public MessageReader(InputStream inputStream, Client sender) {
		this(new InputStreamReader(inputStream), sender);
	}

	public MessageReader(BufferedReader bufferedReader, Client sender) {
		this.reader = bufferedReader;
		this.sender = sender;
	}

	public TextMessage getNextMessage() throws IOException {
		return MessageUtils.buildTextMessage(reader.readLine(), sender);
	}

	public TextMessage getNextMessage(long recepientId) throws IOException {
		return MessageUtils.buildTextMessage(reader.readLine(), sender, new Client(recepientId));
	}

	public TextMessage getNextMessage(Client recepient) throws IOException {
		return MessageUtils.buildTextMessage(reader.readLine(), sender, recepient);
	}

	public long getId() throws NumberFormatException, IOException {
		return Long.parseLong(reader.readLine());
	}
}
