package istic.pr.socket.address;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class AfficheInterfaces {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String result = "";
		/*
		 * affichage de l’ensemble des interfaces de votre réseau et la/les adresses IP
		 * associées.
		 */
		try {
			Enumeration<NetworkInterface> enum_interface = NetworkInterface.getNetworkInterfaces();

			while (enum_interface.hasMoreElements()) {//
				NetworkInterface ni = enum_interface.nextElement();
				result += ni.getName() + ":\n";
				
				Enumeration<InetAddress> enum_inet = ni.getInetAddresses();
				// dans l'interface courante on recupere ses adresse ip
				while (enum_inet.hasMoreElements()) {
					InetAddress ip = enum_inet.nextElement();
					result += "->" + ip.toString() + "\n";
				}
			}

		} catch (SocketException e) {

			System.out.println("Error");
		}

		System.out.println(result);

	}

}
