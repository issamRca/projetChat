package istic.pr.socket.tcp.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientTCP {

	public static void main(String[] args) {
		// Créer une socket Client
		try {
			Socket client = new Socket("localhost", 9999);
			
	        //créer reader et writer associés
			PrintWriter printer = creerPrinter(client);
			
			BufferedReader reader = creerReader(client);

			System.out.print("saisi ton nom ");
        	String name = lireMessageAuClavier();
        	
        	
			String message = lireMessageAuClavier();
			
    		

	    	envoyerNom(printer, name);

	        //Tant que le mot «fin» n’est pas lu sur le clavier,
			while (!message.equals("fin")) {
		        //envoyer le message au serveur
				envoyerMessage(printer, message);
		        //recevoir et afficher la réponse du serveur
				System.out.println(recevoirMessage(reader));
		        //Lire un message au clavier
				message = lireMessageAuClavier();
			}
			client.close();
			//quitter le programme
			System.exit(0);
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String lireMessageAuClavier() throws IOException {
		// lit un message au clavier
		Scanner sc = new Scanner(System.in);
		String messa = sc.nextLine();
		return messa;
	}

	public static BufferedReader creerReader(Socket socketVersunClient) throws IOException {
		// Créé un BufferedReader associé à la socket
		InputStreamReader in = new InputStreamReader(socketVersunClient.getInputStream());
		return new BufferedReader(in);
	}

	public static PrintWriter creerPrinter(Socket socketVersunClient) throws IOException {
		// créé un PrintWriter associé à la socket
		OutputStreamWriter out = new OutputStreamWriter(socketVersunClient.getOutputStream());
		return new PrintWriter(out);

	}

	public static String recevoirMessage(BufferedReader reader) throws IOException {
		// récupérer une ligne
		return reader.readLine();
	}

	public static void envoyerMessage(PrintWriter printer, String message) throws IOException {
		// Envoyer le message vers le client
		printer.println(message);
		printer.flush();
	}
	public static void envoyerNom(PrintWriter printer, String nom) throws
    IOException {
    	printer.write(""+nom+"\r");
    	printer.flush();
    }

}
