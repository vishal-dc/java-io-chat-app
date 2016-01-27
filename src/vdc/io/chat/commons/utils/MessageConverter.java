package vdc.io.chat.commons.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import vdc.io.chat.commons.Message;

public class MessageConverter {

	public static byte[] marshall(Message message) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		ObjectOutputStream objectOs = new ObjectOutputStream(bos);

		objectOs.writeObject(message);
		objectOs.flush();
		return bos.toByteArray();

	}

	public static Message unMarshall(byte[] bytes) throws IOException, ClassNotFoundException {
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		ObjectInputStream objectIs = new ObjectInputStream(bis);

		return (Message) objectIs.readObject();

	}

//	public static void main(String a[]) throws ClassNotFoundException {
//		MessageSerializer ow;
//		try {
//			ByteArrayOutputStream bos = new ByteArrayOutputStream();
//			ow = new MessageSerializer(bos);
//
//			TextMessage msg = new TextMessage(new Client(10), new Client(11), "Hello!!");
//			System.out.println(msg);
//
//			ow.marshall(msg);
//			bos.close();
//			System.out.println(bos);
//			byte[] bytes = bos.toByteArray();
//			ObjectInputStream oid = new ObjectInputStream(new ByteArrayInputStream(bytes));
//			TextMessage msg2 = (TextMessage) oid.readObject();
//			System.out.println(msg2);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
}
