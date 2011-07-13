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
 *    Modified: John Griffiths, CSC
 *    Dates:
 *           Feb  4 2009
 *
 */

package com.erikburrows.ais;

import java.net.*;
import java.io.*;
import java.util.Hashtable;
import com.erikburrows.ais.Parser;
import com.erikburrows.ais.Station;
import com.erikburrows.ais.JsonAis;

public class TypeFilter {

    /**
     * Display command-line usage
     */
    public static void usage() {
	System.out.println("Usage: java com.erikburrows.aisparselib.nmea2udp -gpsdhost <host> -gpsdport <port> [-debug] [-avdo] [-json]");

	System.exit(0);
    }

    /**
     * main() function for command-line starting
     */
    public static void main(String args[]) {
	String gpsd_hostname = null;
	int gpsd_port = 0;
	Socket gpsd_socket = null;
	String nmea_sentance = null;
	BufferedReader gpsdBufferedReader;
	PrintStream gpsd_output_stream;
	Boolean debug = false;
	Boolean do_avdo = false;
	Parser parser = new Parser();
	Station station;
	Hashtable outputStreams = new Hashtable();
	Boolean json = false;
	
	if (args.length == 0 || args[0].equals("-h") || args[0].equals("--help")) {
	    usage();
	}

	// Parse the command line
	for (int i = 0; i < args.length; ++i) {
	    if (args[i].equalsIgnoreCase("-gpsdhost")) {
		if (args.length > i + 1) {

		    gpsd_hostname = args[i + 1];

		    i = i + 1;
		} else {
		    System.out.println("No GPSd hostname specified.");
		}
	    } else if (args[i].equalsIgnoreCase("-gpsdport")) {
		if (args.length > i + 1) {

		    gpsd_port = Integer.parseInt(args[i + 1]);

		    i = i + 1;
		} else {
		    System.out.println("No GPSd port specified.");
		}
	    } else if (args[i].equalsIgnoreCase("-avdo")) {

		do_avdo = true;

	    } else if (args[i].equalsIgnoreCase("-debug")) {
		debug = true;

	    } else if (args[i].equalsIgnoreCase("-json")) {
		json = true;
	    }
	}

	if (debug)
	    System.out.println("Connecting to " + gpsd_hostname + ":" + gpsd_port);

	try {
	    gpsd_socket = new Socket(gpsd_hostname, gpsd_port);
	    gpsdBufferedReader = new BufferedReader(new InputStreamReader(gpsd_socket.getInputStream()));
	    gpsd_output_stream = new PrintStream(gpsd_socket.getOutputStream());
	    
	    // Put the gpsd server into "raw" NMEA mode
	    gpsd_output_stream.println("r+");
	    
	    while(true) {
		nmea_sentance = gpsdBufferedReader.readLine();

		if (nmea_sentance.startsWith("!AIVDM") || (do_avdo && nmea_sentance.startsWith("!AIVDO"))) {
		
		    if (debug)
			System.out.println("NMEA: " + nmea_sentance);

		    station = parser.parse(nmea_sentance);

		    if (station != null) { // Null is returned for partially-received messages. It's OK.
			
			if (!outputStreams.containsKey(station.messageType)) {
			    outputStreams.put(station.messageType, new FileOutputStream(new File(station.messageType + ".log"), true));
			}

			if (json) {
			    String jsontxt = JsonAis.Station2JsonAis(station) + "\n";
			    ((FileOutputStream)outputStreams.get(station.messageType)).write(jsontxt.getBytes());
			    if (debug)
				System.out.println("JSON: " + jsontxt);
			} else {	
			    ((FileOutputStream)outputStreams.get(station.messageType)).write((nmea_sentance + "\r\n").getBytes());
			}
		    }

		}
	    }
	} catch (Exception e) {
	    System.out.println("Exception: " + e);
	}
    }
}
