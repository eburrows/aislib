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
 * Program to compare AIS NMEA -> JSON-ais regression test files. Two files of the format
 * specified below. The NMEA strings are used as a key, and JSON strings are compared 
 * on a per key/value basis. Keys need not be specified in the same order. Differing keys
 * and differing values are reported.
 *
 * Test file format:
 * !AIVDM,1,1,,A,15RTgt0PAso;90TKcjM8h6g208CQ,0*4A
 * {"msgtype":1,"repeat":0,"mmsi":371798000,"status":0,"turn":-127,"speed":123,"accuracy":1,"lon":-74037230,"lat":29028980,"course":2240,"heading":215,"second":33,"regional":0,"radio":0}
 * !AIVDM,1,1,,A,16SteH0P00Jt63hHaa6SagvJ087r,0*42
 * {"msgtype":1,"repeat":0,"mmsi":440348000,"status":0,"turn":-128,"speed":0,"accuracy":0,"lon":-42454920,"lat":25848090,"course":934,"heading":511,"second":13,"regional":0,"radio":0}
 * !AIVDM,1,1,,B,25Cjtd0Oj;Jp7ilG7=UkKBoB0<06,0*60
 * {"msgtype":2,"repeat":0,"mmsi":356302000,"status":0,"turn":127,"speed":139,"accuracy":0,"lon":-42975686,"lat":24235415,"course":877,"heading":91,"second":41,"regional":0,"radio":0}
 * !AIVDM,1,1,,A,38Id705000rRVJhE7cl9n;160000,0*40
 * {"msgtype":3,"repeat":0,"mmsi":563808000,"status":5,"turn":0,"speed":0,"accuracy":1,"lon":-45796520,"lat":22146000,"course":2520,"heading":352,"second":35,"regional":0,"radio":0}
 *
 */


package com.erikburrows.ais;

import java.io.*;
import java.util.Hashtable;
import java.util.Enumeration;
import java.util.Iterator;

import org.json.JSONObject;
import org.json.JSONException;

public class CompareTestFiles {

    /**
     * Display command-line usage
     */
    public static void usage() {
	System.out.println("Usage: java com.erikburrows.ais.CompareTestFiles [-debug] -file1 <file 1> -file2 <file 2>");

	System.exit(0);
    }

    /**
     * main() function for command-line starting
     */
    public static void main(String args[]) {
	String filename1 = null;
	String filename2 = null;
	BufferedReader file1reader = null;
	BufferedReader file2reader = null;
	Hashtable file1hashtable = new Hashtable();
	Hashtable file2hashtable = new Hashtable();
	Boolean debug = false;
	
	if (args.length == 0 || args[0].equals("-h") || args[0].equals("--help")) {
	    usage();
	}

	// Parse the command line
	for (int i = 0; i < args.length; ++i) {
	    if (args[i].equalsIgnoreCase("-file1")) {
		if (args.length > i + 1) {

		    filename1 = args[i + 1];

		    i = i + 1;
		} else {
		    System.out.println("No file 1 specified.");
		}
	    } else if (args[i].equalsIgnoreCase("-file2")) {
		if (args.length > i + 1) {

		    filename2 = args[i + 1];

		    i = i + 1;
		} else {
		    System.out.println("No file 2 specified.");
		}
	    } else if (args[i].equalsIgnoreCase("-debug")) {
		debug = true;
	    }
	}

	if (debug)
	    System.out.println("Comparing " + filename1 + " to " + filename2);

	readTestFile(file1hashtable, filename1);
	readTestFile(file2hashtable, filename2);

	// Check for missing keys

	Enumeration file1keys = file1hashtable.keys();
	while(file1keys.hasMoreElements()) {
	    String key = (String)file1keys.nextElement();
	    if (! file2hashtable.containsKey(key)) {
		System.out.println("File " + filename2 + " is missing nmea sentence " + key);
	    }
	}

	Enumeration file2keys = file2hashtable.keys();
	while(file2keys.hasMoreElements()) {
	    String key = (String)file2keys.nextElement();
	    if (! file1hashtable.containsKey(key)) {
		System.out.println("File " + filename1 + " is missing nmea sentence " + key);
	    }
	}

	// Check key values
	file1keys = file1hashtable.keys();
	while(file1keys.hasMoreElements()) {
	    String nmea = (String)file1keys.nextElement();

	    if (!file2hashtable.containsKey(nmea)) 
		continue;

	    JSONObject file1json = (JSONObject)file1hashtable.get(nmea);
	    JSONObject file2json = (JSONObject)file2hashtable.get(nmea);

	    // Check for missing keys in the JSON objects
	    Iterator json1keys = file1json.keys();
	    while(json1keys.hasNext()) {
		String key = (String)json1keys.next();
		if (!file2json.has(key))
		    System.out.println("File " + filename2 + " for nmea " + nmea + " is missing JSON key " + key);
	    }

	    Iterator json2keys = file2json.keys();
	    while(json2keys.hasNext()) {
		String key = (String)json2keys.next();
		if (!file1json.has(key))
		    System.out.println("File " + filename1 + " for nmea " + nmea + " is missing JSON key " + key);
	    }

	    // Check for same values:
	    json1keys = file1json.keys();
	    while(json1keys.hasNext()) {
		String key = (String)json1keys.next();
		String val1, val2;

		if (! file2json.has(key))
		    continue;

		try {
		    val1 = file1json.getString(key);
		} catch (JSONException e) {
		    System.out.println("Error retrieving: file1:" + nmea + ":" + key);
		    continue;
		}

		try {
		    val2 = file2json.getString(key);
		} catch (JSONException e) {
		    System.out.println("Error retrieving: file2:" + nmea + ":" + key);
		    continue;
		}

		if (! val1.equals(val2)) {
		    System.out.println("Differing values: " + nmea + ":" + key + ": " + filename1 + "=" + val1 + " " + filename2 + "=" + val2);
		}
	    }
	}
    }

    static void readTestFile(Hashtable ht, String filename) {
	BufferedReader reader = null;

	try {
	    reader = new BufferedReader(new FileReader(filename));

	    while(true) {

		String nmea = "";
		String jsontxt = "";

		while(true) {
		    String in = reader.readLine();

		    if (in == null) {
			break;
		    }

		    if (in.substring(0,1).equals("{")) {
			jsontxt = in;
			break;
		    } else {
			nmea = nmea + in;
		    }
		}

		if (nmea == null || jsontxt == null)
		    break;

		try {
		    ht.put(nmea, new JSONObject(jsontxt));
		} catch (JSONException e) {
		    System.out.println("Could not parse " + nmea + ": " + jsontxt);
		    System.exit(1);
		}
	    }

	} catch (FileNotFoundException e) {
	    System.out.println("Could not open file: " + filename);
	    System.exit(1);
	} catch (IOException e) {
	    try {
		reader.close();
	    } catch (IOException e2) {
	    }
	}
    }
}
