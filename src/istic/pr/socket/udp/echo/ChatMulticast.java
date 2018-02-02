package istic.pr.socket.udp.echo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class ChatMulticast {
	public static void main(String[] args) throws IOException{
try {
			//Les messages s’échangent sur le canal 225.0.4.2
			InetAddress groupeIp = InetAddress.getByName("225.0.4.2");
			
			MulticastSocket client = new MulticastSocket(9999);
			//rejoindre le 	groupe communicant via l'adresse groupAdresse
			client.joinGroup(groupeIp);
			
			System.out.print("saisi votre nom ");
			String name = lireMessageAuClavier();
			
			String message = lireMessageAuClavier();
			
			envoyerMessage(client, "<<<"+name+" a accedé à la salle >>>>\n", groupeIp, 9999);
			
			while(!message.equals("fin")){
				
				Receiver rec = new Receiver(client);
				rec.start();
				envoyerMessage(client, name+">"+message, groupeIp, 9999);
				message = lireMessageAuClavier();
			}
			envoyerMessage(client, "<<< "+name+" a quitté la salle >>>>", groupeIp, 9999);
			client.leaveGroup(groupeIp);
			client.close();
			System.exit(0);
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static String recevoirMessage(MulticastSocket socket) throws IOException{
		//récupérer une ligne
		byte[] buffer = new byte[1024];
		DatagramPacket receive = new DatagramPacket(buffer, buffer.length);
		socket.receive(receive);
		return new String(buffer);
	}
	
	public static void envoyerMessage(MulticastSocket socket, String msg, InetAddress groupeIp, int port) throws IOException{
		//Envoyer le message vers les clients
		byte[] buffer = new byte[1024];
		buffer = msg.getBytes();
		DatagramPacket send = new DatagramPacket(buffer, buffer.length, groupeIp, port);
		socket.send(send);
		
	}

	public static String lireMessageAuClavier() throws IOException {
		Scanner sc = new Scanner(System.in);
		String msg = sc.nextLine();
		return msg;
	}
}
