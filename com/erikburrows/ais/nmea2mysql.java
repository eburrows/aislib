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
 *
 */

package com.erikburrows.ais;

import java.net.*;
import java.io.*;
import java.sql.*;
import com.mysql.jdbc.*;
import com.erikburrows.ais.Parser;
import com.erikburrows.ais.Station;
import java.sql.CallableStatement;
import java.util.Hashtable;
import java.util.Enumeration;

/**
 * AIS Gateway Program
 *
 * Connects to gpsd port, sends "r+" raw NMEA command. Then listens for NMEA strings,
 * parses them and writes them to a MySQL database.
 *
 * Usage: java com.erikburrows.aisparselib.nmea2mysql -gpsdhost <host> -gpsdport <port> -mysqlurl <url> -mysqluser <user> -mysqlpassword <password> [-debug]
 */
public class nmea2mysql {

    /**
     * Display command-line usage
     */
    public static void usage() {
	System.out.println("Usage: java com.erikburrows.aisparselib.nmea2mysql -gpsdhost <host> -gpsdport <port> -mysqlurl <url> -mysqluser <user> -mysqlpassword <password> [-debug]");

	System.exit(0);
    }

    /**
     * main() function for command-line starting
     */
    public static void main(String args[]) throws Exception {
	String gpsd_hostname = null;
	int gpsd_port = 2947;
	Socket gpsd_socket = null;
	String nmea_sentence = null;
	BufferedReader gpsdBufferedReader;
	PrintStream gpsd_output_stream;
	Boolean debug = false;
	Boolean do_avdo = false;

	String mysql_url = null;
	String mysql_user = null;
	String mysql_password = null;

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


	    } else if (args[i].equalsIgnoreCase("-mysqlurl")) {
		if (args.length > i + 1) {

		    mysql_url = args[i + 1];

		    i = i + 1;
		} else {
		    System.out.println("No mysql hostname specified.");
		}
	    } else if (args[i].equalsIgnoreCase("-mysqluser")) {
		if (args.length > i + 1) {

		    mysql_user = args[i + 1];

		    i = i + 1;
		} else {
		    System.out.println("No mysql hostname specified.");
		}
	    } else if (args[i].equalsIgnoreCase("-mysqlpassword")) {
		if (args.length > i + 1) {

		    mysql_password = args[i + 1];

		    i = i + 1;
		} else {
		    System.out.println("No mysql hostname specified.");
		}
	    } else if (args[i].equalsIgnoreCase("-avdo")) {

		do_avdo = true;

	    } else if (args[i].equalsIgnoreCase("-debug")) {
		debug = true;
	    }
	}

	if (debug)
	    System.out.println("Reading from " + gpsd_hostname + ":" + gpsd_port + ", logging to " + mysql_url);


	//Register the JDBC driver for MySQL.
	Class.forName("com.mysql.jdbc.Driver");

	java.sql.Connection con = null;

	// Continually try to connect to database, sleeping for 1 second between each attempt
	while(con == null) {
	    try {
		con = DriverManager.getConnection(mysql_url, mysql_user, mysql_password);
	    } catch (com.mysql.jdbc.exceptions.jdbc4.CommunicationsException e) {
		Thread.sleep(1000);
		System.out.println("Connection to MySQL server failed, retrying.");
	    }
	}
	
	// Continually try to connect to socket, sleeping for 1 second between each attempt
	while(gpsd_socket == null) {
	    try {
		gpsd_socket = new Socket(gpsd_hostname, gpsd_port);
	    } catch (java.net.ConnectException e) {
		Thread.sleep(1000);
		System.out.println("Connection to GPSd failed, retrying.");
	    }
	}

	// Build a PrintStream object for the GPSd socket
	gpsdBufferedReader = new BufferedReader(new InputStreamReader(gpsd_socket.getInputStream()));
	gpsd_output_stream = new PrintStream(gpsd_socket.getOutputStream());
	    
	// Put the gpsd server into "raw" NMEA mode
	gpsd_output_stream.println("r+");

	// Initialize the NMEA parser, and a Station variable for its return values
	Parser parser = new Parser();
	Station station;

	// Cache for message-type specific prepared statements
	Hashtable prepared_statements = new Hashtable();
	    
	// Prepare the insert statement for the nmea_sentences table
	java.sql.PreparedStatement nmea_insert_stmt = con.prepareStatement("insert into nmea_sentences (id, msg_type, nmea) values (?, ?, ?);");

	// Main loop
	while(true) {
	    try {
		
		// Get a line from the GPSd socket
		nmea_sentence = gpsdBufferedReader.readLine();

		// Parse only AIVDM sentences, unless the user specifies to parse AIVDO (local station)
		// messages too.
		if (nmea_sentence.startsWith("!AIVDM") || (do_avdo && nmea_sentence.startsWith("!AIVDO"))) {

		    // Parse the sentence
		    station = parser.parse(nmea_sentence);

		    // station==null if the sentence was not the last of a multi-part message,
		    // or if there was a parsing error.
		    if (station != null && station.sql_name != null) {

			if (debug)
			    System.out.println("(" + station.messageType + ", " + station.get("sourceMmsi") + ") " + nmea_sentence);
			

			// Look into the prepared statement cache, to see if we have one already prepared
			// for this table. Otherwise build up the SQL, prepare it, and stick it in the 
			// cache.
			String[] legend = station.pattern;
			if (!prepared_statements.containsKey(station.sql_name)) {

			    //
			    // Build up the SQL statement to be "prepared"
			    //

			    String sql_values = "";
			    String sql_tokens = "";
			    for(int i = 0; i < legend.length; ++i) {
				String[] parts = legend[i].split(":");
				String field_name = parts[0];
				String field_type = parts[1];
				String field_length = parts[2];
				
				if (field_length.indexOf(",") != -1) {
				    String lengthParts[] = parts[2].split(",");
				    field_length = lengthParts[1];
				}
				
				if (field_name.equals("repeat")) {
				    field_name = "rpt";
				}
				
				field_name = field_name.replace(".", "_");
				
				if (field_type.equals("sql_name")) continue;
				
				sql_values += field_name + ", ";
				sql_tokens += "?, ";
				
			    }
			    
			    sql_values = sql_values.substring(0, sql_values.length() - 2);
			    sql_tokens = sql_tokens.substring(0, sql_tokens.length() - 2);
			    
			    String sql = "insert into " + station.sql_name + "(" + sql_values + ") values (" + sql_tokens + ")";
			    
			    prepared_statements.put(station.sql_name, con.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS));
			}

			// Get the prepared insert statement for this table from the statement cache
			java.sql.PreparedStatement stmt = (java.sql.PreparedStatement)prepared_statements.get(station.sql_name);

			//
			// Set values for the prepared statement's tokens
			//
			int i;
			for(i = 0; i < legend.length; ++i) {
			    String[] parts = legend[i].split(":");
			    String field_name = parts[0];
			    String field_type = parts[1];
			    String field_length = parts[2];

			    if (field_type.equals("sql_name")) continue;

			    if (field_type.equals("int")) {
				stmt.setInt(i, (Integer)station.get(field_name));
			    } else if (field_type.equals("string")) {
				stmt.setString(i, (String)station.get(field_name));
			    } else if (field_type.equals("float")) {
				stmt.setFloat(i, (Float)station.get(field_name));
			    } else if (field_type.equals("data")) {
				stmt.setString(i, (String)station.get(field_name));
			    }
			}

			stmt.executeUpdate();

			//
			// Insert nmea_sentences into database, using the auto_increment key from the 
			// message-type specific table.
			//

			java.sql.ResultSet rs = stmt.getGeneratedKeys();
			rs.next();
			int key = rs.getInt(1);
			rs.close();

			Enumeration nmea_sentences = station.nmea_sentences.elements();
			while(nmea_sentences.hasMoreElements()) {
			    String sentence = (String)nmea_sentences.nextElement();
			    nmea_insert_stmt.setInt(1, key);
			    nmea_insert_stmt.setInt(2, station.messageType);
			    nmea_insert_stmt.setString(3, sentence);
			    nmea_insert_stmt.execute();
			}
		    }
		}
	    } catch (Exception e) {
		System.out.println("Exception: " + e);
		e.printStackTrace();
		System.out.println("Last sentence in: " + nmea_sentence);
	    }
	}
    }

}
