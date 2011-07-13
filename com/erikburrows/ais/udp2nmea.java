/*
 *
 *    Copyright (C) 2008  Erik G. Burrows
 *
 *    This program is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation; either version 2 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program; if not, write to the Free Software
 *    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */

package com.erikburrows.ais;

import java.net.*;
import java.io.*;
import java.util.Vector;
import java.util.Enumeration;

/**
 * AIS Gateway program
 *
 * Listens for incomming UDP packets containing AIS (or other) strings,
 * and serves those messages up to connected TCP clients.
 *
 * Connection protocol
 * 1. command arg1 arg2... = OK|ERROR: <message>
 * 2. command ...
 * 3. go
 */
public class udp2nmea {

    /**
     * Display command-line usage
     */
    public static void usage() {
	System.out.println("Usage: java com.erikburrows.ais.udp2nmea -udpport <port> -tcpport <port> [-buffer <size>]");
	System.exit(0);
    }

    /**
     * main() function for command-line starting
     */
    public static void main (String args[]) {
	int udp_port = 12345;
	int tcp_port = 12345;
	boolean debug = false;

	byte[] packet_buffer = new byte[20000];
	DatagramSocket socket;
	DatagramPacket packet;

	Vector message_buffer = new Vector();
	int message_buffer_size = 0;



	if (args.length == 0 || args[0].equals("-h") || args[0].equals("--help")) {
	    usage();
	}

	// Parse the command line
	for (int i = 0; i < args.length; ++i) {
	    if (args[i].equalsIgnoreCase("-udpport")) {
		if (args.length > i + 1) {

		    udp_port = Integer.parseInt(args[i + 1]);

		    i = i + 1;
		} else {
		    System.out.println("No UDP port specified.");
		}
	    } else if (args[i].equalsIgnoreCase("-tcpport")) {
		if (args.length > i + 1) {

		    tcp_port = Integer.parseInt(args[i + 1]);

		    i = i + 1;
		} else {
		    System.out.println("No TCP port specified.");
		}
	    } else if (args[i].equalsIgnoreCase("-buffer")) {
		if (args.length > i + 1) {

		    message_buffer_size = Integer.parseInt(args[i + 1]);

		    i = i + 1;
		} else {
		    System.out.println("No buffer size specified.");
		}
	    } else if (args[i].equalsIgnoreCase("-debug")) {
		debug = true;
	    }
	}

	if (debug)
	    System.out.println("Tunneling from UDP:" + udp_port + " to TCP:" + tcp_port);

	try {
	    socket = new DatagramSocket(udp_port);
	    packet = new DatagramPacket(packet_buffer, packet_buffer.length);

	    Accepter accepter = new Accepter(tcp_port, message_buffer);
	    new Thread(accepter).start();

	    while(true) {
		socket.receive(packet);

		String nmea_sentance = new String(packet.getData(), 0, 0, packet.getLength()).trim();

		if (debug)
		    System.out.println(nmea_sentance);

		message_buffer.add(nmea_sentance);

		if (message_buffer.size() > message_buffer_size + 5) {
		    message_buffer.remove(0);
		}

		accepter.push(nmea_sentance);
	    }

	} catch (Exception e) {
	    System.out.println(e);
	}
    }
}


/**
 * A thread to sit on the TCP server socket, waiting for new connections, 
 * and spawn client-handler threads for each connection.
 */
class Accepter implements Runnable {
    int port = 0;
    ServerSocket socket;
    Vector buffer;
    Vector clients;

    /**
     * Open a TCP server socket on the specified port number. Take a reference
     * to a buffer vector to handle caching.
     */
    public Accepter (int port, Vector buffer) throws IOException {
	this.port = port;
	this.buffer = buffer;

	socket = new ServerSocket(port);

	clients = new Vector();
    }

    /**
     * Wait for incomming connections. For each connection, start a ClientHandler
     * thread. Hand buffer reference off to client for caching.
     */
    public void run() {
	while(true) {
	    try {
		Socket clientSocket = socket.accept();

		ClientHandler client = new ClientHandler(clientSocket, buffer);
		new Thread(client).start();
		clients.add(client);	    
	
	    } catch (Exception e) {
	    }
	}
    }

    /** 
     * Take a message to send out to all client handlers.
     */
    public void push(String message) {
	for (int i = 0; i < clients.size(); ++i) {
	    try {
		ClientHandler client = (ClientHandler)clients.get(i);

		if (client.buffer.size() < 1000) { // Don't let a slow client eat up too much memory.
		    client.buffer.addElement(message);
		}
	    } catch (Exception e) {
		clients.removeElementAt(i);
	    }
	}
    }
}


/**
 * Thread class to handle each TCP client. On each connection, the buffer vector
 * passed all the way from main() gets dumped out to the client for history. From then on,
 * the main() function passes new messages to Accepter.push(), which pushes new messages onto
 * our buffer vector for client send.
 */
class ClientHandler implements Runnable {
    Socket socket;
    public Vector buffer;
    PrintStream ps;

    public ClientHandler (Socket socket, Vector cache) throws IOException {
	this.socket = socket;
	this.buffer = new Vector();

	this.ps = new PrintStream(socket.getOutputStream());

	Enumeration e = cache.elements();
	while (e.hasMoreElements()) {
	    ps.println((String)e.nextElement());
	}
    }

    public void run() {
	try {
	    while (true) {
		if (buffer.size() > 0) {
		    ps.println((String)buffer.remove(0));
		} else {
		    Thread.sleep(100);
		}
	    }
	} catch (Exception e) {
	}
    }
}
