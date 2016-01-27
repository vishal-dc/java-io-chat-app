package vdc.io.chat.client;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import vdc.io.chat.commons.Message;

public class MessageWriter {
	PrintWriter writer = null;

	public MessageWriter(OutputStream os) {

		this(new OutputStreamWriter(os));
	}

	public MessageWriter(OutputStreamWriter osw) {
		this(new PrintWriter(osw, true));
	}

	public MessageWriter(PrintWriter bw) {
		writer = bw;
	}

	public void writeMessage(Message message) {
		writer.println("Message Received: ");
		writer.println(message);
	}

	public void writeText(String text) {
		writer.println(text);

	}
}
