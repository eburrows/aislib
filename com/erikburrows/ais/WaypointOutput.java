package com.erikburrows.ais;

import java.text.DecimalFormat;
import java.util.*;

import com.erikburrows.ais.Station;


/*
WPL - Waypoint Location

	1       2 3        4 5    6
        |       | |        | |    |
 $--WPL,llll.ll,a,yyyyy.yy,a,c--c*hh<CR><LF>

 Field Number: 
  1) Latitude
  2) N or S (North or South)
  3) Longitude
  4) E or W (East or West)
  5) Waypoint name
  6) Checksum     
*/

/*


    $GPGLL,5300.97914,N,00259.98174,E,125926,A*28

In this sentence the checksum is the character representation of the hexadecimal value 28. The string that the checksum is calculated over is

    GPGLL,5300.97914,N,00259.98174,E,125926,A

To calculate the checksum you parse all characters between $ and * from the NMEA sentence into a new string.  In the examples below the name of this new string is stringToCalculateTheChecksumOver. Then just XOR the first character with the next character, until the end of the string.


var checksum = 0;
for(var i = 0; i < stringToCalculateTheChecksumOver.length; i++) {
  checksum = checksum ^ stringToCalculateTheChecksumOver.charCodeAt(i);
}
*/


public class WaypointOutput implements AISConsumer {

    public Hashtable inventory = new Hashtable();

    public WaypointOutput () {}
    public void setMyMMSI(int mmsi) {}



    public void receiveAISStation(Station station) {
	Station old = (Station)inventory.get(station.mmsi);

	if (old == null) {
	    inventory.put((Integer)station.mmsi, station);
	    old = station;
	} else {
	    old.update(station);
	}

	if (old.hashtable.containsKey("name") && old.hashtable.containsKey("latitude")) {

	    DecimalFormat formatter = new DecimalFormat("#.00");

	    float lat = (Float)old.hashtable.get("latitude");
	    float lon = (Float)old.hashtable.get("longitude");

	    String latNS = Math.abs(lat) == lat ? "N" : "S";
	    String lonEW = Math.abs(lon) == lon ? "E" : "W";

	    String sentence = "GPWPL," + formatter.format(Math.abs(lat * 60)) + "," + latNS + "," + formatter.format(Math.abs(lon * 60)) + "," + lonEW + "," + ((String)old.hashtable.get("name")).trim();

	    sentence = "$" + sentence + "*" + checksum(sentence) + "\r\n";

	    try {
		System.out.write(sentence.getBytes());
	    } catch (java.io.IOException e) {
	    }


	}
    }

    public int checksum(String str) {
	int checksum = 0;
	for (int i = 0; i < str.length(); i++) {
	    checksum = checksum ^ str.charAt(i);
	}

	return checksum;
    }

    public static void main (String args[]) {
	String host = args[0];
	int port = Integer.parseInt(args[1]);
	
	AISConsumer consumer = new WaypointOutput();
	NmeaTcpReader reader = new NmeaTcpReader(host, port, consumer);
	new Thread(reader).start();

    }

}