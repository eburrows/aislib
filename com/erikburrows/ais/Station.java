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

import com.erikburrows.ais.Trackpoint;

import java.util.Hashtable;
import java.util.Vector;
import java.util.Enumeration;

public class Station {
    public int mmsi;
    public Hashtable hashtable;
    public Vector tracklog;
    public int messageType;
    public Vector keys;
    public String sql_name = null;
    public String[] pattern = null;
    public Vector nmea_sentences;

    public Station(int mmsi, int messageType, Hashtable params) {
	this.mmsi = mmsi;
	this.messageType = messageType;

	this.hashtable = new Hashtable();
	this.tracklog = new Vector();
	this.keys = new Vector();

	this.hashtable.putAll(params);
    }

    public void update(Station update) {
	this.hashtable.putAll(update.hashtable);

	if (update.hashtable.containsKey("latitude") && update.hashtable.containsKey("longitude")) {
	    float lat = (Float)update.hashtable.get("latitude");
	    float lon = (Float)update.hashtable.get("longitude");

	    if (lon < 180 && lon > -180 && lat < 90 && lat > -90 && lat!=0 && lon!=0) {

		if (this.tracklog.size() > 0) {
		    Trackpoint lastposition = (Trackpoint)this.tracklog.lastElement();
	    
		    if (Math.abs(lastposition.latitude - lat) < 1 && Math.abs(lastposition.longitude - lon) < 1) {
			this.tracklog.add(new Trackpoint((Float)update.hashtable.get("latitude"), (Float)update.hashtable.get("longitude")));
		    }
		} else {
		    this.tracklog.add(new Trackpoint((Float)update.hashtable.get("latitude"), (Float)update.hashtable.get("longitude")));
		}
	    }
	}
    }

    public Enumeration getTracklog() {
	return this.tracklog.elements();
    }

    public Object get(String key) {
	return hashtable.get(key);
    }

    public void put(String key, Object value) {
	hashtable.put(key, value);
	keys.add(key);
    }

    public String getNavigationalStatus() {
	if (hashtable.containsKey("navigationalStatus")) {
	    String status;
	    switch ((Integer)hashtable.get("navigationalStatus")) {
	    case 0: status = "Under way using engine"; break;
	    case 1: status = "Anchored"; break;
	    case 2: status = "Not under command"; break;
	    case 3: status = "Restricted manoeuvrability"; break;
	    case 4: status = "Constricted by draught"; break;
	    case 5: status = "Moored"; break;
	    case 6: status = "Aground"; break;
	    case 7: status = "Engaged in fishing operations"; break;
	    case 8: status = "Under way sailing"; break;
	    default: status = "Unknown"; break;
  	    }

            return status;
	} else {
	    return null;
	}
    }
}
