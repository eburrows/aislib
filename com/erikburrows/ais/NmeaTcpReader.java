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

//
// Connect to a TCP port, read AIS NMEA sentances, decode them, and 
// send them to a consumer.
//

package com.erikburrows.ais;

import java.net.*;
import java.io.*;

import java.util.Hashtable;
import java.util.Enumeration;

import com.erikburrows.ais.Station;
import com.erikburrows.ais.Parser;

public class NmeaTcpReader implements Runnable {
    String host;
    int port = 0;
    Socket gpsd_socket = null;
    String nmea_sentance;
    BufferedReader gpsd_input_stream;
    PrintStream gpsd_output_stream;
    Parser parser;
    Station station;
    AISConsumer consumer;

    public NmeaTcpReader (String host, int port, AISConsumer consumer) {
	this.host = host;
	this.port = port;
	this.consumer = consumer;

	this.parser = new Parser();
    }

    public void run() {

	System.out.println("NmeaTcpReader connecting to " + host + ":" + port);

	try {
	    gpsd_socket = new Socket(host, port);

	    gpsd_input_stream = new BufferedReader(new InputStreamReader(gpsd_socket.getInputStream()));

	    gpsd_output_stream = new PrintStream(gpsd_socket.getOutputStream());

	    // Put the gpsd server into "raw" NMEA mode
	    gpsd_output_stream.println("r+");

	    while(true) {
		nmea_sentance = gpsd_input_stream.readLine();

		if (nmea_sentance.startsWith("!AIVDM") || nmea_sentance.startsWith("!AIVDO")) {

		    try {
			station = this.parser.parse(nmea_sentance);

			if (nmea_sentance.startsWith("!AIVDO")) {
			    this.consumer.setMyMMSI(station.mmsi);
			}

			
			if (station != null) {
			    this.consumer.receiveAISStation(station);
			}
		    } catch (Exception e) {
			System.out.println("Error parsing: " + nmea_sentance + "\n" + e);
		    }
		}
	    }
	} catch (Exception e) {
	    System.out.println("NmeaTcpReader reader error, restarting " + e);
	    try {
		Thread.sleep(1000);
	    } catch (Exception e2) {
	    }
	    new Thread(new NmeaTcpReader(this.host, this.port, this.consumer)).start();
	}
    }

    public static void main (String args[]) {
	String host = args[0];
	int port = Integer.parseInt(args[1]);
	
	Consumer consumer = new Consumer();
	NmeaTcpReader reader = new NmeaTcpReader(host, port, consumer);
	new Thread(reader).start();

    }
}

class Consumer implements AISConsumer {
    public void receiveAISStation(Station station) {
	String out = "";

	out += "msgType=" + station.messageType;
	out += " mmsi=" + station.mmsi;

	Enumeration keys = station.hashtable.keys();
	while (keys.hasMoreElements()) {
	    String key = (String)keys.nextElement();
	    out += " " + key + "=" + station.hashtable.get(key);
	}

	System.out.println(out);
    }

    public void setMyMMSI(int mmsi) {
    }
}
