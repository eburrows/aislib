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

import java.util.Hashtable;
import java.util.Vector;
import java.util.Enumeration;

import com.erikburrows.ais.Patterns;
import com.erikburrows.ais.Station;
import com.erikburrows.ais.NmeaTcpReader;
import com.erikburrows.ais.AISConsumer;

public class Parser {

    private void debug(String msg) {
	if (1 == 0) {
	    System.out.println(msg);
	}
    }

    Hashtable patterns;
    Hashtable buf = new Hashtable();
    Vector nmea_buffer = new Vector();

    public Parser() {
	Patterns p = new Patterns();
	patterns = p.patterns;
    }

    public boolean syntaxCheck(String nmea) {
	// !AIVDM,1,1,,A,15NUWl0000GRm5VCDk2r=GTN0D1B,0*52
	return nmea.matches("^!AIVD[M,O],[0-9]+,[0-9]+,[0-9]*,[A,B,1,2]{0,1},[0-9,a-z,A-Z,\\:,\\;,\\<,\\>,\\=,\\?,\\@,\\`]{1,82},[0-9]\\*[0-9,A-F]{2}");
    }

    public Station parse(String nmea_sentance) {
	if (syntaxCheck(nmea_sentance)) {
	    if (checksum(nmea_sentance)) {

		debug("IN: " + nmea_sentance);

		String[] splits = nmea_sentance.split(",");

		int message_max = Integer.parseInt(splits[1]);
		int message_num = Integer.parseInt(splits[2]);
		// splits[3] = Sequence Identifier, may be empty-string or 0-9
		// splits[4] = Channel: A/B
		String bitstring = binaryToBitString(splits[5]);

		debug("Bitstring: " + bitstring);
		debug("  Length: " + bitstring.length());

		//int mmsi = Integer.parseInt(bitstring.substring(8, 38), 2);
		int mmsi = 1;


		if (message_num < message_max) {
		    // Add to the buffer
		    buf.put(mmsi + "." + message_num, bitstring);
		    nmea_buffer.add(nmea_sentance);
		}

		if (message_num == message_max && message_num > 1) {
		    for (int i = message_num - 1; i >= 1; --i) {
			String prefix = (String)buf.remove(mmsi + "." + i);

			if (prefix == null) {
			    //System.out.println("Got last message part, but not previous parts.");
			    return null;
			}

			bitstring = prefix + bitstring;
		    }
		}

		if (message_num == message_max) {

		    Station message = null;

		    try {

			Enumeration keys = patterns.keys();
			while (keys.hasMoreElements()) {
			    String pattern = (String)keys.nextElement();
			    String legend[] = (String[])patterns.get(pattern);
			    
			    debug("Checking pattern '" + pattern + "' against: " + bitstring);
			    
			    if (bitstring.matches(pattern)) {
				debug("Matched");
				message = decode(nmea_sentance, bitstring, legend);
				break;
			    }
			}
			
			if (message == null) {
			    debug("No pattern matches: " + nmea_sentance);
			    debug("  Bitstring: " + bitstring);
			    debug("  Length: " + bitstring.length());
			    debug("  Type: " + Integer.parseInt(bitstring.substring(0, 6), 2));
			} else {
			    message.nmea_sentences = new Vector(nmea_buffer);
			    message.nmea_sentences.add(nmea_sentance);
			    nmea_buffer = new Vector();
			}

			return message;
		    } catch (Exception e) {
			// decode should have caught this, and displayed error information.
			return null;
		    }
		} else {
		    debug("Not last message.");
		    return null;
		}
	    } else {
		debug("Bad checksum");
		return null;
	    }
	} else {
	    debug("NMEA sentence failed syntax check: " + nmea_sentance);
	    return null;
	}
    }


    boolean checksum(String sentance) {
	char sum = sentance.charAt(sentance.indexOf("!") + 1);

	for (int i = sentance.indexOf("!") + 2; i < sentance.indexOf("*"); ++i) {
	    sum = (char)((int)sum ^ (int)sentance.charAt(i));
	}

	int idx = sentance.indexOf("*");

	/*
	if (sentance.charAt(idx+1) == '!')
	    return false;

	if (sentance.charAt(idx+2) == '!')
	    return false;
	*/

	return sum == Integer.parseInt(sentance.substring(idx +1, idx + 3), 16);
    }


    float decodeSignedFloat(String binary) {
	double ret = (double)Integer.parseInt(binary, 2);

	if (binary.charAt(0) == '1')
	    ret = ret - java.lang.Math.pow(2, binary.length());

	return (float)ret;
    }


    String decodeString(String binary) {
	String ret = "";

	for (int i = 0; i < binary.length() / 6; ++i) {
	    int c = Integer.parseInt(binary.substring(i * 6, i * 6 + 6), 2);

	    if (c < 32 && c >= 0) //convert to 6-bit ASCII - control chars to uppercase latins
		c = c + 64;

	    if (c != 64)
		ret = ret + (char)c;
	}

	return ret;
    }


    String binaryToBitString(String binary) {
	String bitString = "";

	for (int i = 0; i < binary.length(); ++i) {
	    int byteValue = (int)binary.charAt(i);

	    if (byteValue >= 48 && byteValue < 88) {    //40 chars 0 - W
                byteValue -= 48;
	    } else if (byteValue >= 96 && byteValue < 121) { //24 chars `,a - w
		byteValue -= 56;
	    } else {
                byteValue = 0;    //ignore any ather chars
	    }

	    for (int j = Integer.toBinaryString(byteValue).length(); j < 6; ++j)
		bitString = bitString + "0";

	    bitString = bitString + Integer.toBinaryString(byteValue);
	}

	return bitString;
    }

    Station decode(String nmea_sentance, String bitstring, String legend[]) throws Exception {
	Vector keylist = new Vector();
	Hashtable ret = new Hashtable();
	int index = 0;
	String sql_name = null;

	try {

	    for (int i = 0; i < legend.length; ++i) {
		String parts[] = legend[i].split(":");
		String fieldName = parts[0];
		String fieldType = parts[1];
		int fieldMinLength = 0;
		int fieldMaxLength = 0;
		int divisor = 0;
		
		if (parts[2].indexOf(",") != -1) {
		    String lengthParts[] = parts[2].split(",");
		    fieldMinLength = Integer.parseInt(lengthParts[0]);
		    fieldMaxLength = Integer.parseInt(lengthParts[1]);
		} else {
		    fieldMinLength = Integer.parseInt(parts[2]);
		    fieldMaxLength = fieldMinLength;
		}
		
		if (parts.length >= 4) {
		    divisor = Integer.parseInt(parts[3]);
		}
		
		debug("Extracting: " + fieldName + " type=" + fieldType + " minLength=" + fieldMinLength + " maxLength=" + fieldMaxLength + " divisor=" + divisor);
		
		debug("  len=" + bitstring.length() + " index=" + index);
		
		if (fieldMinLength == 0 && bitstring.length() == index) {
		    continue;
		}

		keylist.add(fieldName);
		
		if (fieldType.equals("int")) {
		    debug("  bitstring=" + bitstring.substring(index, index + fieldMinLength));
		    ret.put(fieldName, Integer.parseInt(bitstring.substring(index, index + fieldMinLength), 2));
		    index += fieldMinLength;		
		    
		} else if (fieldType.equals("string")) {
		    debug("  bitstring=" + bitstring.substring(index, index + fieldMinLength));
		    ret.put(fieldName, decodeString(bitstring.substring(index, index + fieldMinLength)));
		    index += fieldMinLength;
		    
		} else if (fieldType.equals("float")) {
		    debug("  bitstring=" + bitstring.substring(index, index + fieldMinLength));
		    ret.put(fieldName, new Float(decodeSignedFloat(bitstring.substring(index, index + fieldMinLength)) / (float)divisor));
		    index += fieldMinLength;			
		    
		} else if (fieldType.equals("data")) {
		    if (fieldMinLength == fieldMaxLength) {
			debug("  bitstring=" + bitstring.substring(index, index + fieldMaxLength));
			ret.put(fieldName, bitstring.substring(index, index + fieldMaxLength));
			index += fieldMaxLength;
		    } else {
			debug("  bitstring=" + bitstring.substring(index));
			ret.put(fieldName, bitstring.substring(index));
		    }
		} else if (fieldType.equals("sql_name")) {
		    sql_name = fieldName;
		} else {
		    debug("Unknown type specified: " + fieldType);
		    return null;
		}
		
		debug("  Extracted: " + ret.get(fieldName));
		debug("  Remainder: " + bitstring.substring(index));

	    }

	    String debugMsg = "";
	    Enumeration keys = ret.keys();
	    while(keys.hasMoreElements()) {
		String key = (String)keys.nextElement();
		debugMsg += key + "=" + ret.get(key) + " ";
	    }

	    //System.out.println(debugMsg);

	    Station station = new Station((Integer)ret.get("sourceMmsi"), (Integer)ret.get("messageType"), ret);
	    station.sql_name = sql_name;
	    station.pattern = legend;
	    station.keys = keylist;

	    return station;

	} catch (Exception e) {
	    debug("Error parsing: " + nmea_sentance + ", " + e);
	    debug("   Bitstring: " + bitstring);

	    Enumeration keys = ret.keys();
	    while(keys.hasMoreElements()) {
		String key = (String)keys.nextElement();
		debug("    '" + key + "' = '" + ret.get(key) + "'");
	    }
	    
	    throw(e);
	}
    }


    public static void main(String args[]) {
	Parser parser = new Parser();
	Station station;

	station = parser.parse("!AIVDM,1,1,,A,15NUWl0000GRm5VCDk2r=GTN0D1B,0*52");

	if (station == null) {
	    System.out.println("Null return");
	} else {
	    Enumeration keys = station.hashtable.keys();
	    while(keys.hasMoreElements()) {
		String key = (String)keys.nextElement();
		System.out.println(key + ": " + station.hashtable.get(key));
	    }
	}
    }

}
