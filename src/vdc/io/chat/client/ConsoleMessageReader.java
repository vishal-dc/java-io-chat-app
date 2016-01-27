package vdc.io.chat.client;

import vdc.io.chat.commons.Client;

public class ConsoleMessageReader extends MessageReader {

	public ConsoleMessageReader(Client sender) {
		super(System.in, sender);
	}

	
	
}
