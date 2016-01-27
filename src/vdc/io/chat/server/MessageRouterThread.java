package vdc.io.chat.server;

public class MessageRouterThread implements Runnable {
	MessageRouter router = null;

	public MessageRouterThread(MessageRouter messageRouter) {
		this.router = messageRouter;
	}

	public void start() {
		Thread thread = new Thread(this);
		thread.setName("Message Router Thread:");
		thread.start();
	}

	@Override
	public void run() {
		while (true) {
			try {

				router.routeNextMessage();

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
