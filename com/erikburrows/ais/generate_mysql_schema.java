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

import java.util.Enumeration;
import com.erikburrows.ais.Parser;

public class generate_mysql_schema {
    public static void main(String args[]) throws Exception {
	Parser parser = new Parser();

	System.out.println("drop table if exists nmea_sentences;");
	System.out.println("create table nmea_sentences (id int, msg_type int, nmea varchar(128));");

	Enumeration patterns = parser.patterns.elements();

	while(patterns.hasMoreElements()) {
	    String[] pattern = (String[])patterns.nextElement();

	    String[] parts;

	    parts = pattern[0].split(":");

	    if (parts[1].equals("sql_name")) {
		System.out.println("drop table if exists " + parts[0] + ";");
		System.out.println("create table " + parts[0] + " (ts timestamp default now(), id int auto_increment primary key, ");

		for(int i = 0; i < pattern.length; ++i) {
		    parts = pattern[i].split(":");
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

		    if (field_type.equals("string")) {
			pattern[i] = field_name + " varchar(" + (Integer.parseInt(field_length) / 6) + ")";

		    } else if (field_type.equals("data")) {
			pattern[i] = field_name + " varchar(" + Integer.parseInt(field_length) + ")";

		    } else if (field_type.equals("int")) {

			if (Integer.parseInt(field_length) < 16) {
			    pattern[i] = field_name + " int";
			} else {
			    pattern[i] = field_name + " int unsigned";
			}

		    } else if (field_type.equals("float")) {
			pattern[i] = field_name + " float";

		    } else {
			pattern[i] = null;
		    }		    
		}

		System.out.println(join(pattern, ", "));

		System.out.println(");");
	    }
	}
    }

    public static String join(String[] parts, String delim) {
	String out = "";

	for(int i = 0; i < parts.length; ++i) {
	    if (parts[i] != null) {
		out += parts[i];

		if (i < parts.length - 1) {
		    out += delim;
		}
	    }
	}
	
	return out;
    }
}
