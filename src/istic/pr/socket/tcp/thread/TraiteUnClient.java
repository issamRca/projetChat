package istic.pr.socket.tcp.thread;

import java.io.IOException;
import java.net.Socket;

public class TraiteUnClient implements Runnable {

	private Socket socketClient;
	//constructeur
	public TraiteUnClient(Socket s) {
		socketClient = s;
	}

	@Override
	//méthode exécutée lorsqu'on démarre le Thread
	public void run() {
		// TODO Auto-generated method stub
		try {
			ServeurTCP.traiterSocketCliente(socketClient);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}