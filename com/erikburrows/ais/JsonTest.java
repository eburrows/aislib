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

public class JsonTest {

    /**
     * Display command-line usage
     */
    public static void usage() {
	System.out.println("Usage: java com.erikburrows.aisparselib.JsonTest -in <file> -out <file> [-debug]");

	System.exit(0);
    }

    /**
     * main() function for command-line starting
     */
    public static void main(String args[]) {
	String inFilename = null;
	String outFilename = null;
	BufferedReader infile = null;
	FileOutputStream outfile = null;
	String nmea_sentance = null;
	Boolean debug = false;
	Parser parser = new Parser();
	Station station;
	String json = null;

	if (args.length == 0 || args[0].equals("-h") || args[0].equals("--help")) {
	    usage();
	}

	// Parse the command line
	for (int i = 0; i < args.length; ++i) {
	    if (args[i].equalsIgnoreCase("-in")) {
		if (args.length > i + 1) {

		    inFilename = args[i + 1];

		    i = i + 1;
		} else {
		    System.out.println("No input file specified.");
		}
	    } else if (args[i].equalsIgnoreCase("-out")) {
		if (args.length > i + 1) {

		    outFilename = args[i + 1];

		    i = i + 1;
		} else {
		    System.out.println("No output file specified.");
		}
	    } else if (args[i].equalsIgnoreCase("-debug")) {
		debug = true;
	    }
	}

	try {

	    infile = new BufferedReader(new FileReader(inFilename));
	    outfile = new FileOutputStream(new File(outFilename));

	    while(true) {
		nmea_sentance = infile.readLine();
		
		if (nmea_sentance == null)
		    break;

		outfile.write((nmea_sentance + "\n").getBytes());

		station = parser.parse(nmea_sentance);

		if (station != null) { // Null is returned for partially-received messages. It's OK.
			
		    json = JsonAis.Station2JsonAis(station);

		    if (debug)
			System.out.println("JSON: " + json);

		    outfile.write((json + "\n").getBytes());

		}
	    }
	} catch (Exception e) {
	    System.out.println("Exception: " + e);
	    e.printStackTrace();
	}
    }
}
