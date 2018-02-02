package istic.pr.socket.udp.echo;

import java.io.IOException;
import java.net.MulticastSocket;

public class Receiver extends Thread {
	
	private MulticastSocket socket;
	
	public Receiver(MulticastSocket socket){
		this.socket = socket;
	}

	public void run(){
		try {
			while(true){
				String msg = ChatMulticast.recevoirMessage(this.socket);
				System.out.println(msg);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
