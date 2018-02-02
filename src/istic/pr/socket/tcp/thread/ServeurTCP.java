package istic.pr.socket.tcp.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class ServeurTCP {

	public static void main(String[] args) {
		// Traitement des exceptions

		try {
			// Attente des connexions sur le port 9999
			ServerSocket socket = new ServerSocket(9999);
			System.out.println("Écoute sur le  port : 9999\n");
			//créé un pool de 4 thread max
			Executor service = Executors.newFixedThreadPool(4);

			System.out.println("******Serveur Start******** :\n");

			while (true) {// Dans une boucle, pour chaque socket clientes, appeler traiterSocketCliente

				Socket client = socket.accept();
				System.out.println(client.toString());
				System.out.println("__________");
				service.execute(new TraiteUnClient(client));
			}

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("ERROR SERVER");
		}

	}

	public static void traiterSocketCliente(Socket socketVersunClient) throws IOException {
		try {
			// Créer printer et reader
			BufferedReader reader = creerReader(socketVersunClient);
			PrintWriter writer = creerPrinter(socketVersunClient);
			// Tant qu'il y a un message à lire via recevoirMessage
			
			String nom = avoirNom(reader);
			//si le nom est un nombre ou bien il contient un null on signale au client que le nom et invalide 
			if(nom.matches("\\d*")||nom == null)
			envoyerMessage(writer, "Mauvais nom utilisateur");
			
			String message = recevoirMessage(reader);
			while (message != null) {
				//System.out.println("Client ==>" + message + "\n");
				System.out.println(nom+">"+message+"\n");
				envoyerMessage(writer, nom+">"+message);

				// Envoyer message au client via EnvoyerMessage
				//envoyerMessage(writer, "Server==>" + message);
				message = recevoirMessage(reader);
			}
		} catch (Exception e) {
			System.out.println("Arret du serveur");
		}
	}

	public static String avoirNom(BufferedReader reader) throws IOException {
		return reader.readLine();
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

}