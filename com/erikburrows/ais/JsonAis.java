package com.erikburrows.ais;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import java.util.Date;
import java.util.TimeZone;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;

import com.erikburrows.ais.Station;

import org.json.JSONObject;

public class JsonAis {

    public static String Station2JsonAis(Station station) {
	JSONObject json = new JSONObject();

	try {

	    json.put("mmsi", station.mmsi);
	    json.put("msgtype", station.messageType);
	    json.put("repeat", station.get("repeat"));
	    json.put("rxtime", calendar2timestamp(new GregorianCalendar()));

	    switch (station.messageType) {
	    case 1:
	    case 2:
	    case 3:
		switch ((int)((Integer)station.get("navigationalStatus"))) {
		case 0: json.put("status", "Under way using engine"); break;
		case 1: json.put("status", "At anchor"); break;
		case 2: json.put("status", "Not under command"); break;
		case 3: json.put("status", "Restricted manoeuverability"); break;
		case 4: json.put("status", "Constrained by her draught"); break;
		case 5: json.put("status", "Moored"); break;
		case 6: json.put("status", "Aground"); break;
		case 7: json.put("status", "Engaged in Fishing"); break;
		case 8: json.put("status", "Under way sailing"); break;
		case 9: json.put("status", "Reserved for future amendment of Navigational Status for HSC"); break;
		case 10: json.put("status", "Reserved for future amendment of Navigational Status for WIG"); break;
		case 11: json.put("status", "Reserved for future use"); break;
		case 12: json.put("status", "Reserved for future use"); break;
		case 13: json.put("status", "Reserved for future use"); break;
		case 14: json.put("status", "Reserved for future use"); break;
		case 15: json.put("status", ""); break;
		}

		json.put("turn", station.get("rateOfTurn"));
		json.put("speed", station.get("speedOverGround"));
		json.put("accuracy", station.get("positionAccuracy"));

		json.put("lon", (int)((Float)station.get("longitude") * (float)600000.0));
		json.put("lat", (int)((Float)station.get("latitude") * (float)600000.0));

		json.put("course", station.get("courseOverGround"));
		json.put("heading", station.get("trueHeading"));
		json.put("second", station.get("UTC"));

		switch ((int)((Integer)station.get("maneuver"))) {
		case 0: json.put("maneuver", "Not available"); break;
		case 1: json.put("maneuver", "No special maneuver"); break;
		case 2: json.put("maneuver", "Special maneuver"); break;
		}

		json.put("raim", station.get("raimFlag"));
		json.put("radio", station.get("communicationStatus"));
		
		break;

	    case 4:
	    case 11:
		json.put("accuracy", station.get("positionAccuracy"));
		json.put("year", station.get("utcYear"));
		json.put("month", station.get("utcMonth"));
		json.put("day", station.get("utcDay"));
		json.put("hour", station.get("utcHour"));
		json.put("minute", station.get("utcMinute"));
		json.put("second", station.get("utcSecond"));
		json.put("accuracy", station.get("positionAccuracy"));
		json.put("lon", (int)((Float)station.get("longitude") * (float)600000.0));
		json.put("lat", (int)((Float)station.get("latitude") * (float)600000.0));
		json.put("epfd", decodeEpfd((Integer)station.get("fixDeviceType")));
                json.put("raim", station.get("raimFlag"));
		json.put("radio", station.get("communicationStatus"));

		break;


	    case 5:
		json.put("ais_version", station.get("aisVersion"));
		json.put("imo_id", station.get("imoNumber"));
		json.put("callsign", station.get("callsign"));
		json.put("shipname", station.get("name"));

		json.put("shiptype", decodeShipType((Integer)station.get("shipCargoType")));

		json.put("to_bow", station.get("lengthFore"));
		json.put("to_stern", station.get("lengthAft"));
		json.put("to_port", station.get("widthPort"));
		json.put("to_starboard", station.get("widthStarboard"));

		switch ((int)((Integer)station.get("fixDeviceType"))) {
		case 0: json.put("epfd", "Undefined"); break;
		case 1: json.put("epfd", "GPS"); break;
		case 2: json.put("epfd", "GLONASS"); break;
		case 3: json.put("epfd", "Combined GPS/GLONASS"); break;
		case 4: json.put("epfd", "Loran-C"); break;
		case 5: json.put("epfd", "Chayka"); break;
		case 6: json.put("epfd", "Integrated navigation system"); break;
		case 7: json.put("epfd", "Surveyed"); break;
		case 8: json.put("epfd", "Galileo"); break;
		}

		json.put("eta_month", station.get("etaMonth"));
		json.put("eta_day", station.get("etaDay"));
		json.put("eta_hour", station.get("etaHour"));
		json.put("eta_minute", station.get("etaMinute"));

		json.put("draught", station.get("draught"));
		json.put("destination", station.get("destination"));
		json.put("dte", station.get("dte"));

		break;

	    case 6:
		json.put("sequence", station.get("sequence"));
		json.put("dest_mmsi", station.get("destinationMmsi"));
		json.put("retransmit", station.get("retransmit"));
		json.put("application_id", station.get("applicationId"));
		json.put("data", station.get("data"));

		break;

	    case 7:
	    case 13:
		json.put("sequence", station.get("sequence"));

		json.put("mmsi1", station.get("destinationMmsi1"));
		json.put("sequence1", station.get("sequenceNumber1"));

		if (station.hashtable.containsKey("destinationMmsi2")) {
		    json.put("mmsi1", station.get("destinationMmsi2"));
		    json.put("sequence1", station.get("sequenceNumber2"));
		}

		if (station.hashtable.containsKey("destinationMmsi\3")) {
		    json.put("mmsi1", station.get("destinationMmsi3"));
		    json.put("sequence1", station.get("sequenceNumber3"));
		}

		if (station.hashtable.containsKey("destinationMmsi4")) {
		    json.put("mmsi1", station.get("destinationMmsi4"));
		    json.put("sequence1", station.get("sequenceNumber4"));
		}

		break;

	    case 8:
		json.put("application_id", station.get("applicationID"));
		json.put("sequence", station.get("data"));

		break;

	    case 9:

		json.put("alt", station.get("altitude"));
		json.put("speed", station.get("speedOverGround"));
		json.put("accuracy", station.get("positionAccuracy"));
		json.put("lon", (int)((Float)station.get("longitude") * (float)600000.0));
		json.put("lat", (int)((Float)station.get("latitude") * (float)600000.0));
		json.put("course", station.get("courseOverGround"));
		json.put("second", station.get("UTC"));
		json.put("regional", station.get("regionalApplication"));
		json.put("dte", station.get("dte"));
		json.put("assigned", station.get("assignedModeFlag"));
		json.put("raim", station.get("raimFlag"));
		json.put("radio", station.get("communicationStatus"));

		break;

	    case 10:
		json.put("dest_mmsi", station.get("destinationMmsi"));
		break;

	    case 12:
		json.put("dest_mmsi", station.get("destinationMmsi"));
		json.put("retransmit", station.get("retransmitFlag"));
		json.put("text", station.get("text"));
		break;

	    case 14:
		json.put("text", station.get("text"));
		break;

	    case 15:
		// FIXME
		break;

	    case 16:
		json.put("mmsi1", station.get("destinationMmsi1"));
		json.put("offset1", station.get("offset1"));
		json.put("increment1", station.get("increment1"));
		json.put("mmsi2", station.get("destinationMmsi2"));
		json.put("offset2", station.get("offset2"));
		json.put("increment2", station.get("increment2"));
		break;

	    case 17:
		json.put("data", station.get("data"));
		json.put("lon", (int)((Float)station.get("longitude") * (float)600000.0));
		json.put("lat", (int)((Float)station.get("latitude") * (float)600000.0));
		break;

	    case 18:
		json.put("speed", station.get("speedOverGround"));
		json.put("accuracy", station.get("positionAccuracy"));
		json.put("lon", (int)((Float)station.get("longitude") * (float)600000.0));
		json.put("lat", (int)((Float)station.get("latitude") * (float)600000.0));
		json.put("course", station.get("courseOverGround"));
		json.put("second", station.get("UTC"));
		json.put("heading", station.get("trueHeading"));
		json.put("cs", station.get("csFlag"));
		json.put("display", station.get("displayFlag"));
		json.put("dsc", station.get("dscFlag"));
		json.put("band", station.get("bandFlag"));
		json.put("msg22", station.get("msg22Flag"));
		json.put("raim", station.get("raimFlag"));
		json.put("radio", station.get("communicationStatus"));
		json.put("assigned", station.get("assignedMode"));

		break;

	    case 19:
		json.put("speed", station.get("speedOverGround"));
		json.put("accuracy", station.get("positionAccuracy"));
		json.put("lon", (int)((Float)station.get("longitude") * (float)600000.0));
		json.put("lat", (int)((Float)station.get("latitude") * (float)600000.0));
		json.put("course", station.get("courseOverGround"));
		json.put("heading", station.get("trueHeading"));
		json.put("second", station.get("UTC"));
		json.put("shipname", station.get("name"));
		json.put("shiptype", decodeShipType((Integer)station.get("shipCargoType")));
		json.put("to_bow", station.get("lengthFore"));
		json.put("to_stern", station.get("lengthAft"));
		json.put("to_port", station.get("widthPort"));
		json.put("to_starboard", station.get("widthStarboard"));
		json.put("epfd", decodeEpfd((Integer)station.get("fixDeviceType")));
		json.put("raim", station.get("raimFlag"));
		json.put("dte", station.get("dte"));
		json.put("assigned", station.get("assignedMode"));
			 
		break;

	    case 20:
		json.put("offset1", station.get("offset1"));
		json.put("number1", station.get("number1"));
		json.put("timeout1", station.get("timeout1"));
		json.put("increment1", station.get("increment1"));
		json.put("offset2", station.get("offset2"));
		json.put("number2", station.get("number2"));
		json.put("timeout2", station.get("timeout2"));
		json.put("increment2", station.get("increment2"));
		json.put("offset3", station.get("offset3"));
		json.put("number3", station.get("number3"));
		json.put("timeout3", station.get("timeout3"));
		json.put("increment3", station.get("increment3"));
		json.put("offset4", station.get("offset4"));
		json.put("number4", station.get("number4"));
		json.put("timeout4", station.get("timeout4"));
		json.put("increment4", station.get("increment4"));

		break;

	    case 21:
		json.put("type", decodeAidToNavigationType((Integer)station.get("typeOfAid")));
		json.put("name", station.get("name"));
		json.put("lon", (int)((Float)station.get("longitude") * (float)600000.0));
		json.put("lat", (int)((Float)station.get("latitude") * (float)600000.0));
		json.put("accuracy", station.get("positionAccuracy"));
		json.put("to_bow", station.get("lengthFore"));
		json.put("to_stern", station.get("lengthAft"));
		json.put("to_port", station.get("widthPort"));
		json.put("to_starboard", station.get("widthStarboard"));
		json.put("epfd", decodeEpfd((Integer)station.get("fixDeviceType")));
		json.put("second", station.get("UTC"));
		json.put("off_position", station.get("offPosition"));
		json.put("raim", station.get("raimFlag"));
		json.put("virtual_aid", station.get("virtual"));

		break;

	    }


	    return json.toString();
	} catch (Exception e) {
	    System.out.println("Exception " + e);
	    e.printStackTrace();
	    return null;
	}
    }

    private static String calendar2timestamp(Calendar cal) {
	int msInMin = 60000;    
	int minInHr = 60;    
	Date date = new Date();    
	int Hours, Minutes;    
	
	DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG );  
	TimeZone zone = dateFormat.getTimeZone();  

	Minutes =zone.getOffset( date.getTime() ) / msInMin;    
	Hours = Minutes / minInHr;    

	zone = zone.getTimeZone( "GMT Time" +(Hours>=0?"+":"")+Hours+":"+ Minutes);

	dateFormat.setTimeZone( zone );    

	return String.format("%1$TY:%1$Tm:%1TdT%1$TTZ", dateFormat.getCalendar());

    }

    private static String eta2timestamp(int month, int day, int hour, int minute) {
	GregorianCalendar cal = new GregorianCalendar();
	cal.set(Calendar.MONTH, month);
	cal.set(Calendar.DAY_OF_MONTH, day);
	cal.set(Calendar.HOUR_OF_DAY, hour);
	cal.set(Calendar.MINUTE, minute);

	int msInMin = 60000;    
	int minInHr = 60;    
	Date date = new Date();    
	int Hours, Minutes;    
	
	DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG );  
	TimeZone zone = dateFormat.getTimeZone();  

	Minutes =zone.getOffset( date.getTime() ) / msInMin;    
	Hours = Minutes / minInHr;    

	zone = zone.getTimeZone( "GMT Time" +(Hours>=0?"+":"")+Hours+":"+ Minutes);

	dateFormat.setTimeZone( zone );    

	return String.format("%1$TY:%1$Tm:%1TdT%1$TTZ", dateFormat.getCalendar());
    }

    private static String utcValues2timestamp(int year, int month, int day, int hour, int minute) {
	GregorianCalendar cal = new GregorianCalendar();
	cal.set(Calendar.YEAR, year);
	cal.set(Calendar.MONTH, month);
	cal.set(Calendar.DAY_OF_MONTH, day);
	cal.set(Calendar.HOUR_OF_DAY, hour);
	cal.set(Calendar.MINUTE, minute);

	return String.format("%1$TY:%1$Tm:%1TdT%1$TTZ", cal);
    }

    public static String decodeEpfd(int epfd) {
	switch (epfd) {
	    case 0: return("Undefined");
	    case 1: return("GPS");
	    case 2: return("GLONASS");
	    case 3: return("Combined GPS/GLONASS");
	    case 4: return("Loran-C");
	    case 5: return("Chayka");
	    case 6: return("Integrated navigation system");
	    case 7: return("Surveyed");
	    case 8: return("Galileo");
	    default: return("");
	}
    }

    public static String decodeAidToNavigationType(int t) {
	switch (t) {
	    case 0: return("Default");
	    case 1: return("Reference point");
	    case 2: return("RACON");
	    case 3: return("Fixed structure off shore");
	    case 4: return("Spare");
	    case 5: return("Light, without sectors");
	    case 6: return("Light, with sectors");
	    case 7: return("Leading Light Front");
	    case 8: return("Leading Light Rear");
	    case 9: return("Beacon, Cardinal N");
	    case 10: return("Beacon, Cardinal E");
	    case 11: return("Beacon, Cardinal S");
	    case 12: return("Beacon, Cardinal W");
	    case 13: return("Beacon, Port hand");
	    case 14: return("Beacon, Starboard hand");
	    case 15: return("Beacon, Preferred Channel port hand");
	    case 16: return("Beacon, Preferred Channel starboard hand");
	    case 17: return("Beacon, Isolated danger");
	    case 18: return("Beacon, Safe water");
	    case 19: return("Beacon, Special mark");
	    case 20: return("Cardinal Mark N");
	    case 21: return("Cardinal Mark E");
	    case 22: return("Cardinal Mark S");
	    case 23: return("Cardinal Mark W");
	    case 24: return("Port hand Mark");
	    case 25: return("Starboard hand Mark");
	    case 26: return("Preferred Channel Port hand");
	    case 27: return("Preferred Channel Starboard hand");
	    case 28: return("Isolated danger");
	    case 29: return("Safe Water");
	    case 30: return("Special Mark");
	    case 31: return("Light Vessel / LANBY / Rigs");
	    default: return("");
	}
    }

    public static String decodeShipType(int shipType) {
	switch (shipType) {
	case 0: return("Not available");
	case 1:
	case 2:
	case 3:
	case 4:
	case 5:
	case 6:
	case 7:
	case 8:
	case 9:
	case 10:
	case 11:
	case 12:
	case 13:
	case 14:
	case 15:
	case 16:
	case 17:
	case 18:
	case 19: return("Reserved for future use");
	case 20: return("Wing in ground (WIG), all ships of this type");
	case 21: return("Wing in ground (WIG), Hazardous category A");
	case 22: return("Wing in ground (WIG), Hazardous category B");
	case 23: return("Wing in ground (WIG), Hazardous category C");
	case 24: return("Wing in ground (WIG), Hazardous category D");
	case 25: return("Wing in ground (WIG), Reserved for future use");
	case 26: return("Wing in ground (WIG), Reserved for future use");
	case 27: return("Wing in ground (WIG), Reserved for future use");
	case 28: return("Wing in ground (WIG), Reserved for future use");
	case 29: return("Wing in ground (WIG), Reserved for future use");
	case 30: return("Fishing");
	case 31: return("Towing");
	case 32: return("Towing: length exceeds 200m or breadth exceeds 25m");
	case 33: return("Dredging or underwater ops");
	case 34: return("Diving ops ");
	case 35: return("Military ops ");
	case 36: return("Sailing");
	case 37: return("Pleasure Craft");
	case 38: return("Reserved");
	case 39: return("Reserved");
	case 40: return("High speed craft (HSC), all ships of this type");
	case 41: return("High speed craft (HSC), Hazardous category A");
	case 42: return("High speed craft (HSC), Hazardous category B");
	case 43: return("High speed craft (HSC), Hazardous category C");
	case 44: return("High speed craft (HSC), Hazardous category D");
	case 45: return("High speed craft (HSC), Reserved for future use");
	case 46: return("High speed craft (HSC), Reserved for future use");
	case 47: return("High speed craft (HSC), Reserved for future use");
	case 48: return("High speed craft (HSC), Reserved for future use");
	case 49: return("High speed craft (HSC), No additional information");
	case 50: return("Pilot Vessel");
	case 51: return("Search and Rescue vessel");
	case 52: return("Tug");
	case 53: return("Port Tender");
	case 54: return("Anti-pollution equipment");
	case 55: return("Law Enforcement");
	case 56: return("Spare - Local Vessel");
	case 57: return("Spare - Local Vessel");
	case 58: return("Medical Transport");
	case 59: return("Ship according to RR Resolution No. 18");
	case 60: return("Passenger, all ships of this type");
	case 61: return("Passenger, Hazardous category A");
	case 62: return("Passenger, Hazardous category B");
	case 63: return("Passenger, Hazardous category C");
	case 64: return("Passenger, Hazardous category D");
	case 65: return("Passenger, Reserved for future use");
	case 66: return("Passenger, Reserved for future use");
	case 67: return("Passenger, Reserved for future use");
	case 68: return("Passenger, Reserved for future use");
	case 69: return("Passenger, No additional information");
	case 70: return("Cargo, all ships of this type");
	case 71: return("Cargo, Hazardous category A");
	case 72: return("Cargo, Hazardous category B");
	case 73: return("Cargo, Hazardous category C");
	case 74: return("Cargo, Hazardous category D");
	case 75: return("Cargo, Reserved for future use");
	case 76: return("Cargo, Reserved for future use");
	case 77: return("Cargo, Reserved for future use");
	case 78: return("Cargo, Reserved for future use");
	case 79: return("Cargo, No additional information");
	case 80: return("Tanker, all ships of this type");
	case 81: return("Tanker, Hazardous category A");
	case 82: return("Tanker, Hazardous category B");
	case 83: return("Tanker, Hazardous category C");
	case 84: return("Tanker, Hazardous category D");
	case 85: return("Tanker, Reserved for future use");
	case 86: return("Tanker, Reserved for future use");
	case 87: return("Tanker, Reserved for future use");
	case 88: return("Tanker, Reserved for future use");
	case 89: return("Tanker, No additional information");
	case 90: return("Other Type, all ships of this type");
	case 91: return("Other Type, Hazardous category A");
	case 92: return("Other Type, Hazardous category B");
	case 93: return("Other Type, Hazardous category C");
	case 94: return("Other Type, Hazardous category D");
	case 95: return("Other Type, Reserved for future use ");
	case 96: return("Other Type, Reserved for future use ");
	case 97: return("Other Type, Reserved for future use ");
	case 98: return("Other Type, Reserved for future use ");
	case 99: return("Other Type, no additional information");
	default: return("");
	}
    }

}
