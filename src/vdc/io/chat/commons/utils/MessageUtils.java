package vdc.io.chat.commons.utils;

import java.util.Collection;

import vdc.io.chat.commons.Client;
import vdc.io.chat.commons.ClientListMessage;
import vdc.io.chat.commons.Message;
import vdc.io.chat.commons.TextMessage;
import vdc.io.chat.commons.Message.Type;

public class MessageUtils {

	public static TextMessage buildTextMessage(String text, Client sender) {
		return new TextMessage(sender, createRecepient(text), text);
	}

	public static Client createRecepient(String message) {

		return new Client(getRecepientId(message));
	}

	public static long getRecepientId(String message) {
		String id = message.substring(0, message.indexOf(":"));
		return Long.parseLong(id);
	}

	public static String getText(String message) {
		return message.substring(message.indexOf(":") + 1);
	}

	public static Message createLoginMessage(Client sender) {
		return new TextMessage(sender, Type.LOGIN);
	}

	public static ClientListMessage createClientListMessage(Collection<Client> values) {
		ClientListMessage msg = new ClientListMessage();
		for (Client client : values) {
			msg.addClient(client);

		}
		return msg;
	}

	public static TextMessage buildTextMessage(String text, Client sender, Client recepient) {
		return new TextMessage(sender, recepient, text);
	}
}
