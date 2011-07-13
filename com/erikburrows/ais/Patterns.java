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

import java.util.*;

public class Patterns {
    public Hashtable patterns = new Hashtable();

    public Patterns() {
	// 1: Dynamic Info
	patterns.put(
		 "^" + i2b(1,6) + ".*", new String[] {
		     "msg1_dynamic_info:sql_name:0",
		     "messageType:int:6",
		     "repeat:int:2",
		     "sourceMmsi:int:30",
		     "navigationalStatus:int:4",
		     "rateOfTurn:int:8",
		     "speedOverGround:int:10",
		     "positionAccuracy:int:1",
		     "longitude:float:28:600000",
		     "latitude:float:27:600000",
		     "courseOverGround:float:12:10",
		     "trueHeading:int:9",
		     "utc:int:6",
		     "maneuver:int:2",
		     "spare:int:3",
		     "raimFlag:int:1",
		     "communicationStatus:int:19"
		 });

	// 2: Dynamic Info
	patterns.put(
		 "^" + i2b(2,6) + ".*", new String[] {
		     "msg2_dynamic_info:sql_name:0",
		     "messageType:int:6",
		     "repeat:int:2",
		     "sourceMmsi:int:30",
		     "navigationalStatus:int:4",
		     "rateOfTurn:int:8",
		     "speedOverGround:int:10",
		     "positionAccuracy:int:1",
		     "longitude:float:28:600000",
		     "latitude:float:27:600000",
		     "courseOverGround:float:12:10",
		     "trueHeading:int:9",
		     "utc:int:6",
		     "maneuver:int:2",
		     "spare:int:3",
		     "raimFlag:int:1",
		     "communicationStatus:int:19"
		 });

	// 3: Dynamic Info
	patterns.put(
		 "^" + i2b(3,6) + ".*", new String[] {
		     "msg3_dynamic_info:sql_name:0",
		     "messageType:int:6",
		     "repeat:int:2",
		     "sourceMmsi:int:30",
		     "navigationalStatus:int:4",
		     "rateOfTurn:int:8",
		     "speedOverGround:int:10",
		     "positionAccuracy:int:1",
		     "longitude:float:28:600000",
		     "latitude:float:27:600000",
		     "courseOverGround:float:12:10",
		     "trueHeading:int:9",
		     "utc:int:6",
		     "maneuver:int:2",
		     "spare:int:3",
		     "raimFlag:int:1",
		     "communicationStatus:int:19"
		 });

	// 4: Station Info
	patterns.put(
		 "^" + i2b(4,6) + ".*", new String[] {
		     "msg4_station_info:sql_name:0",
		     "messageType:int:6",
		     "repeat:int:2",
		     "sourceMmsi:int:30",
		     "utcYear:int:14",
		     "utcMonth:int:4",
		     "utcDay:int:5",
		     "utcHour:int:5",
		     "utcMinute:int:6",
		     "utcSecond:int:6",
		     "positionAccuracy:int:1",
		     "longitude:float:28:600000",
		     "latitude:float:27:600000",
		     "fixDeviceType:int:4",
		     "spare:int:10",
		     "raimFlag:int:1",
		     "communicationStatus:int:19"
		 });

	// 5: Static Info
	patterns.put(
		 "^" + i2b(5,6) + ".*", new String[] {
		     "msg5_static_info:sql_name:0",
		     "messageType:int:6",
		     "repeat:int:2",
		     "sourceMmsi:int:30",
		     "aisVersion:int:2",
		     "imoNumber:int:30",
		     "callSign:string:42",
		     "name:string:120",
		     "shipCargoType:int:8",
		     "lengthFore:int:9",
		     "lengthAft:int:9",
		     "widthPort:int:6",
		     "widthStarboard:int:6",
		     "fixDeviceType:int:4",
		     "etaMonth:int:4",
		     "etaDay:int:5",
		     "etaHour:int:5",
		     "etaMinute:int:6",
		     "draught:int:8",
		     "destination:string:120",
		     "dte:int:1",
		     "spare:int:1"
		 });


	// 6: Addressed binary message
	patterns.put( 
		 "^" + i2b(6,6) + ".*", new String[] {
		     "msg6_addressed_binary_message:sql_name:0",
		     "messageType:int:6",
		     "repeat:int:2",
		     "sourceMmsi:int:30",
		     "sequence:int:2",
		     "destinationMmsi:int:30",
		     "retransmit:int:1",
		     "spare:int:1",
		     "applicationId:int:16",
		     "data:data:0,920"
		 });


	// 7: Binary Acknowlege with 4 destinations
	patterns.put( 
		 "^" + i2b(7,6) + ".{162}", new String[] {
		     "msg7_binary_ack_4_destinations:sql_name:0",
		     "messageType:int:6",
		     "repeat:int:2",
		     "sourceMmsi:int:30",
		     "spare:int:2",
		     "destinationMmsi1:int:30",
		     "sequenceNumber1:int:2",
		     "destinationMmsi2:int:30",
		     "sequenceNumber2:int:2",
		     "destinationMmsi3:int:30",
		     "sequenceNumber3:int:2",
		     "destinationMmsi4:int:30",
		     "sequenceNumber4:int:2"
		 });


	// 7: Binary Acknowlege with 3 destinations
	patterns.put( 
		 "^" + i2b(7,6) + ".{130}", new String[] {
		     "msg7_binary_ack_3_destinations:sql_name:0",
		     "messageType:int:6",
		     "repeat:int:2",
		     "sourceMmsi:int:30",
		     "spare:int:2",
		     "destinationMmsi1:int:30",
		     "sequenceNumber1:int:2",
		     "destinationMmsi2:int:30",
		     "sequenceNumber2:int:2",
		     "destinationMmsi3:int:30",
		     "sequenceNumber3:int:2"
		 });


	// 7: Binary Acknowlege with 2 destinations
	patterns.put( 
		 "^" + i2b(7,6) + ".{98}", new String[] {
		     "msg7_binary_ack_2_destinations:sql_name:0",
		     "messageType:int:6",
		     "repeat:int:2",
		     "sourceMmsi:int:30",
		     "spare:int:2",
		     "destinationMmsi1:int:30",
		     "sequenceNumber1:int:2",
		     "destinationMmsi2:int:30",
		     "sequenceNumber2:int:2"
		 });


	// 7: Binary Acknowledge with 1 destination
	patterns.put( 
		 "^" + i2b(7,6) + ".{66}", new String[] {
		     "msg7_binary_ack_1_destination:sql_name:0",
		     "messageType:int:6",
		     "repeat:int:2",
		     "sourceMmsi:int:30",
		     "spare:int:2",
		     "destinationMmsi1:int:30",
		     "sequenceNumber1:int:2"
		 });

	// 8: Binary broadcast
	patterns.put( 
		 "^" + i2b(8,6) + ".*", new String[] {
		     "msg8_binary_broadcast:sql_name:0",
		     "messageType:int:6",
		     "repeat:int:2",
		     "sourceMmsi:int:30",
		     "spare:int:2",
		     "applicationID:int:16",
		     "data:data:0,952"
		 });


	// 9: SAR Aircraft
	patterns.put( 
		 "^" + i2b(9,6) + ".*", new String[] {
		     "msg9_sar_aircraft:sql_name:0",
		     "messageType:int:6",
		     "repeat:int:2",
		     "sourceMmsi:int:30",
		     "altitude:int:12",
		     "speedOverGround:int:10",
		     "positionAccuracy:int:1",
		     "longitude:float:28:600000",
		     "latitude:float:27:600000",
		     "courseOverGround:float:12:10",
		     "utc:int:6",
		     "regionalApplication:int:8",
		     "dte:int:1",
		     "spare:int:3",
		     "assignedModeFlag:int:1",
		     "raimFlag:int:1",
		     "communicationStateSelectorFlag:int:1",
		     "communicationStatus:int:19"
		 });


	// 10: UTC Date Inqury
	patterns.put( 
		 "^" + i2b(10,6) + ".*", new String[] {
		     "msg9_utc_date_inquiry:sql_name:0",
		     "messageType:int:6",
		     "repeat:int:2",
		     "sourceMmsi:int:30",
		     "spare:int:2",
		     "destinationMmsi:int:30",
		     "spare2:int:2"
		 });


	// 11: Station Info
	patterns.put(
		 "^" + i2b(11,6) + ".*", new String[] {
		     "msg11_station_info:sql_name:0",
		     "messageType:int:6",
		     "repeat:int:2",
		     "sourceMmsi:int:30",
		     "utcYear:int:14",
		     "utcMonth:int:4",
		     "utcDay:int:5",
		     "utcHour:int:5",
		     "utcMinute:int:6",
		     "utcSecond:int:6",
		     "positionAccuracy:int:1",
		     "longitude:float:28:600000",
		     "latitude:float:27:600000",
		     "fixDeviceType:int:4",
		     "spare:int:10",
		     "raimFlag:int:1",
		     "communicationStatus:int:19"
		 });


	// 12: Addressed safety related message
	patterns.put(
		 "^" + i2b(12,6) + ".*", new String[] {
		     "msg12_addressed_safety_message:sql_name:0",
		     "messageType:int:6",
		     "repeat:int:2",
		     "sourceMmsi:int:30",
		     "sequence:int:2",
		     "destinationMmsi:int:30",
		     "retransmitFlag:int:1",
		     "spare:int:1",
		     "text:string:0,936"
		 });


	// 13: Safety Acknowlege with 4 destinations
	patterns.put( 
		 "^" + i2b(13,6) + ".{162}", new String[] {
		     "msg13_safety_ack_4_destinations:sql_name:0",
		     "messageType:int:6",
		     "repeat:int:2",
		     "sourceMmsi:int:30",
		     "spare:int:2",
		     "destinationMmsi1:int:30",
		     "sequenceNumber1:int:2",
		     "destinationMmsi2:int:30",
		     "sequenceNumber2:int:2",
		     "destinationMmsi3:int:30",
		     "sequenceNumber3:int:2",
		     "destinationMmsi4:int:30",
		     "sequenceNumber4:int:2"
		 });

	// 13: Safety Acknowlege with 3 destinations
	patterns.put( 
		 "^" + i2b(13,6) + ".{130}", new String[] {
		     "msg13_safety_ack_3_destinations:sql_name:0",
		     "messageType:int:6",
		     "repeat:int:2",
		     "sourceMmsi:int:30",
		     "spare:int:2",
		     "destinationMmsi1:int:30",
		     "sequenceNumber1:int:2",
		     "destinationMmsi2:int:30",
		     "sequenceNumber2:int:2",
		     "destinationMmsi3:int:30",
		     "sequenceNumber3:int:2"
		 });


	// 13: Safety Acknowlege with 2 destinations
	patterns.put( 
		 "^" + i2b(13,6) + ".{98}", new String[] {
		     "msg13_safety_ack_2_destinations:sql_name:0",
		     "messageType:int:6",
		     "repeat:int:2",
		     "sourceMmsi:int:30",
		     "spare:int:2",
		     "destinationMmsi1:int:30",
		     "sequenceNumber1:int:2",
		     "destinationMmsi2:int:30",
		     "sequenceNumber2:int:2"
		 });


	// 13: Safety Acknowledge with 1 destination
	patterns.put( 
		 "^" + i2b(13,6) + ".{66}", new String[] {
		     "msg13_safety_ack_1_destination:sql_name:0",
		     "messageType:int:6",
		     "repeat:int:2",
		     "sourceMmsi:int:30",
		     "spare:int:2",
		     "destinationMmsi1:int:30",
		     "sequenceNumber1:int:2"
		 });

	// 14: Safety related broadcast message
	patterns.put(
		 "^" + i2b(14,6) + ".*", new String[] {
		     "msg14_safety_broadcast:sql_name:0",
		     "messageType:int:6",
		     "repeat:int:2",
		     "sourceMmsi:int:30",
		     "spare:int:2",
		     "text:string:0,936"
		 });


	// 15: Interrogation, pattern 4, 3
	patterns.put(
		 "^" + i2b(15,6) + ".{160}", new String[] {
		     "msg15_interrogation_4_3:sql_name:0",
		     "messageType:int:6",
		     "repeat:int:2",
		     "sourceMmsi:int:30",
		     "spare:int:2",
		     "destinationMmsi1:int:30",
		     "messageId1_1:int:6",
		     "slotOffset1_1:int:12",
		     "spare2:int:2",
		     "messageId1_2:int:6",
		     "slotOffset1_2:int:12",
		     "spare3:int:2",
		     "destinationMmsi2:int:30",
		     "messageId2_1:int:6",
		     "slotOffset2_1:int:12",
		     "spare4:int:2"
		 });

	// 15: Interrogation, pattern 2
	patterns.put(
		 "^" + i2b(15,6) + ".{108}", new String[] {
		     "msg15_interrogation_2:sql_name:0",
		     "messageType:int:6",
		     "repeat:int:2",
		     "sourceMmsi:int:30",
		     "spare:int:2",
		     "destinationMmsi1:int:30",
		     "messageId1_1:int:6",
		     "slotOffset1_1:int:12",
		     "spare2:int:2",
		     "messageId1_2:int:6",
		     "slotOffset1_2:int:12"
		 });

	// 15: Interrogation, pattern 1
	patterns.put(
		 "^" + i2b(15,6) + ".*", new String[] {
		     "msg15_interrogation_1:sql_name:0",
		     "messageType:int:6",
		     "repeat:int:2",
		     "sourceMmsi:int:30",
		     "spare:int:2",
		     "destinationMmsi1:int:30",
		     "messageId1_1:int:6",
		     "slotOffset1_1:int:12"
		 });

	// 16: Assignment Mode Command, 2 destinations
	patterns.put(
		 "^" + i2b(16,6) + ".{138}", new String[] {
		     "msg16_assignment_mode_command_2_dest:sql_name:0",
		     "messageType:int:6",
		     "repeat:int:2",
		     "sourceMmsi:int:30",
		     "spare:int:2",
		     "destinationMmsi1:int:30",
		     "offset1:int:12",
		     "increment1:int:10",
		     "destinationMmsi2:int:30",
		     "offset2:int:12",
		     "increment2:int:10"
		 });

	// 16: Assignment Mode Command, 1 destination
	patterns.put(
		 "^" + i2b(16,6) + ".{90}", new String[] {
		     "msg16_assignment_mode_command_1_dest:sql_name:0",
		     "messageType:int:6",
		     "repeat:int:2",
		     "sourceMmsi:int:30",
		     "spare:int:2",
		     "destinationMmsi1:int:30",
		     "offset1:int:12",
		     "increment1:int:10",
		     "spare2:int:4"
		 });


	// 17: DGNSS Broadcast binary message
	patterns.put(
		 "^" + i2b(17,6) + ".*", new String[] {
		     "msg17_dgnss_broadcast:sql_name:0",
		     "messageType:int:6",
		     "repeat:int:2",
		     "sourceMmsi:int:30",
		     "spare:int:2",
		     "longitude:float:18:600",
		     "latitude:float:17:600",
		     "spare2:int:5",
		     "data:data:0,736"
		 });


	// 18: Class B position info
	patterns.put(
		 "^" + i2b(18,6) + ".*", new String[] {
		     "msg18_class_b_position:sql_name:0",
		     "messageType:int:6",
		     "repeat:int:2",
		     "sourceMmsi:int:30",
		     "localApplicationData:int:8",
		     "speedOverGround:float:10:10",
		     "positionAccuracy:int:1",
		     "longitude:float:28:600000",
		     "latitude:float:27:600000",
		     "courseOverGround:float:12:10",
		     "trueHeading:int:9",
		     "timestamp:int:6",
		     "regionalApplicationData:int:2",
		     "csFlag:int:1",
		     "displayFlag:int:1",
		     "dscFlag:int:1",
		     "bandFlag:int:1",
		     "msg22Flag:int:1",
		     "assignedMode:int:1",
		     "raimFlag:int:1",
		     "communicationStatusSelectFlag:int:1",
		     "communicationStatus:int:19"
		 });


	// 19: Class B extended info
	patterns.put(
		 "^" + i2b(19,6) + ".*", new String[] {
		     "msg19_class_b_extended_info:sql_name:0",
		     "messageType:int:6",
		     "repeat:int:2",
		     "sourceMmsi:int:30",
		     "localApplicationData:int:8",
		     "speedOverGround:float:10:10",
		     "positionAccuracy:int:1",
		     "longitude:float:28:600000",
		     "latitude:float:27:600000",
		     "courseOverGround:float:12:10",
		     "trueHeading:int:9",
		     "timestamp:int:6",
		     "regionalApplicationData:int:4",
		     "name:string:120",
		     "shipCargoType:int:8",
		     "lengthFore:int:9",
		     "lengthAft:int:9",
		     "widthPort:int:6",
		     "widthStarboard:int:6",
		     "fixDeviceType:int:4",
		     "raimFlag:int:1",
		     "dte:int:1",
		     "assignedMode:int:1",
		     "spare:int:4"
		 });


	// 20: data link management with 4 slots
	patterns.put(
		 "^" + i2b(20,6) + ".{154}.*", new String[] {
		     "msg20_data_link_management_4_slots:sql_name:0",
		     "messageType:int:6",
		     "repeat:int:2",
		     "sourceMmsi:int:30",
		     "spare:int:2",
		     "offset1:int:12",
		     "slots1:int:4",
		     "timeout1:int:3",
		     "increment1:int:11",
		     "offset2:int:12",
		     "slots2:int:4",
		     "timeout2:int:3",
		     "increment2:int:11",
		     "offset3:int:12",
		     "slots3:int:4",
		     "timeout3:int:3",
		     "increment3:int:11",
		     "offset4:int:12",
		     "slots4:int:4",
		     "timeout4:int:3",
		     "increment4:int:11"
		 });


	// 20: data link management with 3 slots
	patterns.put(
		 "^" + i2b(20,6) + ".{124}.*", new String[] {
		     "msg20_data_link_management_3_slots:sql_name:0",
		     "messageType:int:6",
		     "repeat:int:2",
		     "sourceMmsi:int:30",
		     "spare:int:2",
		     "offset1:int:12",
		     "slots1:int:4",
		     "timeout1:int:3",
		     "increment1:int:11",
		     "offset2:int:12",
		     "slots2:int:4",
		     "timeout2:int:3",
		     "increment2:int:11",
		     "offset3:int:12",
		     "slots3:int:4",
		     "timeout3:int:3",
		     "increment3:int:11"
		 });


	// 20: data link management with 2 slots
	patterns.put(
		 "^" + i2b(20,6) + ".{94}.*", new String[] {
		     "msg20_data_link_management_2_slots:sql_name:0",
		     "messageType:int:6",
		     "repeat:int:2",
		     "sourceMmsi:int:30",
		     "spare:int:2",
		     "offset1:int:12",
		     "slots1:int:4",
		     "timeout1:int:3",
		     "increment1:int:11",
		     "offset2:int:12",
		     "slots2:int:4",
		     "timeout2:int:3",
		     "increment2:int:11"
		 });


	// 20: data link management with 1 slot
	patterns.put(
		 "^" + i2b(20,6) + ".{64}.*", new String[] {
		     "msg20_data_link_management_1_slot:sql_name:0",
		     "messageType:int:6",
		     "repeat:int:2",
		     "sourceMmsi:int:30",
		     "spare:int:2",
		     "offset1:int:12",
		     "slots1:int:4",
		     "timeout1:int:3",
		     "increment1:int:11"
		 });


	// 21: Aids-to-Nav Report
	patterns.put(
		 "^" + i2b(21,6) + ".*", new String[] {
		     "msg21_aids_to_nav_report:sql_name:0",
		     "messageType:int:6",
		     "repeat:int:2",
		     "sourceMmsi:int:30",
		     "typeOfAid:int:5",
		     "name:string:120",
		     "positionAccuracy:int:1",
		     "longitude:float:28:600000",
		     "latitude:float:27:600000",
		     "lengthFore:int:9",
		     "lengthAft:int:9",
		     "widthPort:int:6",
		     "widthStarboard:int:6",
		     "fixDeviceType:int:4",
		     "timestamp:int:6",
		     "offPosition:int:1",
		     "localApplicationData:int:8",
		     "raimFlag:int:1",
		     "virtual:int:1",
		     "assignedMode:int:1",
		     "spare:int:1",
		     "nameExtension:string:0,84"
		 });


	// 22: Channel Management
	patterns.put(
		 "^" + i2b(22,6) + ".*", new String[] {
		     "msg22_channel_management:sql_name:0",
		     "messageType:int:6",
		     "repeat:int:2",
		     "sourceMmsi:int:30",
		     "spare:int:2",
		     "channelA:int:12",
		     "channelB:int:12",
		     "transmitReceiveMode:int:4",
		     "power:int:1",
		     "longitude1:float:18:600",
		     "latitude1:float:17:600",
		     "longitude2:float:18:600",
		     "latitude2:float:17:600",
		     "addressOrBroadcast:int:1",
		     "channelABW:int:1",
		     "channelBBW:int:1",
		     "transmissionZoneSite:int:3",
		     "spare2:int:23"		  
		 });


	// 24: Class B static info type A
	patterns.put(
		 "^" + i2b(24,6) + ".{2}.{30}00.*", new String[] {
		     "msg24_class_b_static_info_a:sql_name:0",
		     "messageType:int:6",
		     "repeat:int:2",
		     "sourceMmsi:int:30",
		     "partNumber:int:2",
		     "name:string:120"
		 });

	// 24: Class B static info type B
	patterns.put(
		 "^" + i2b(24,6) + ".{2}.{30}01.*", new String[] {
		     "msg24_class_b_static_info_b:sql_name:0",
		     "messageType:int:6",
		     "repeat:int:2",
		     "sourceMmsi:int:30",
		     "partNumber:int:2",
		     "shipCargoType:int:8",
		     "vendorId:string:42",
		     "callSign:string:42",
		     "lengthFore:int:9",
		     "lengthAft:int:9",
		     "widthPort:int:6",
		     "widthStarboard:int:6",
		     "spare:int:6"
		 });
    }


    public void erlang_printer() {

	System.out.println("%");
	System.out.println("% Generated from \"Aislib\" Patterns class, see: http://www.erikburrows.com/index.php?node=AISlib");
	System.out.println("% Copyright Erik G. Burrows, 2010. Licensed under GPL version 2.");
	System.out.println("%");

	System.out.println("-define(AisPatterns, [");

	Enumeration keys = patterns.keys();

	while(keys.hasMoreElements()) {
	    String regex = (String)keys.nextElement();
	    String[] fields = (String[])patterns.get(regex);
	    String name = "";
	    
	    for(int i = 0; i < fields.length; ++i) {
		String[] parts = fields[i].split(":");
		
		String fieldName = parts[0];
		String fieldType = parts[1];
		
		if (fieldType.equals("sql_name"))
		    name = fieldName;
	    }
	    
	    System.out.println("  [");
	    System.out.println("    {pattern, \"" + regex + "\"},");
	    System.out.println("    {name,\"" + name + "\"},");
	    System.out.println("    {fields, [");

	    for(int i = 0; i < fields.length; ++i) {
		String[] parts = fields[i].split(":");
		
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
		
		if (fieldType.equals("sql_name"))
		    continue;

		System.out.print("      {" + fieldName + ", " + fieldType + ", [");

		System.out.print("{min_length, " + fieldMinLength + "},");
		System.out.print("{max_length, " + fieldMaxLength + "}");

		if (fieldType.equals("float"))
		    System.out.print(",{divisor, " + divisor + "}");

		if (i == fields.length -1) {
		    System.out.println("]}");
		} else {
		    System.out.println("]},");
		}
	    }

	    System.out.println("    ]}");

	    if (keys.hasMoreElements()) {
		System.out.println("  ],");
	    } else {
		System.out.println("  ]");
	    }
	}

	System.out.println("]).");
    }

    public void mysql_printer() {
	System.out.println("drop table if exists nmea_sentences;");
	System.out.println("create table nmea_sentences (id int, msg_type int, nmea varchar(128));");

	Enumeration keys = patterns.elements();

	while(keys.hasMoreElements()) {
	    String[] pattern = (String[])keys.nextElement();

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

    public void postgresql_printer() {
	System.out.println("drop sequence if exists ais_seq cascade;");
	System.out.println("create sequence ais_seq;");

	System.out.println("create table ais_raw (id bigint default nextval('ais_seq'), msg_type int, nmea text);");

	Enumeration keys = patterns.elements();

	while(keys.hasMoreElements()) {
	    String[] pattern = (String[])keys.nextElement();

	    String[] parts;

	    parts = pattern[0].split(":");

	    if (parts[1].equals("sql_name")) {
		boolean has_position = false;

		String table_name = parts[0];

		System.out.println("drop table " + table_name + ";");
		System.out.println("create table " + table_name + " (ts timestamp default now(), id bigint default currval('ais_seq'), ");

		for(int i = 0; i < pattern.length; ++i) {
		    parts = pattern[i].split(":");
		    String field_name = parts[0];
		    String field_type = parts[1];
		    String field_length = parts[2];

		    if (field_length.indexOf(",") != -1) {
			String lengthParts[] = parts[2].split(",");
			field_length = lengthParts[1];
		    }

		    if (field_name.equals("latitude"))
			has_position = true;

		    if (field_type.equals("string")) {
			pattern[i] = field_name + " text";

		    } else if (field_type.equals("data")) {
			pattern[i] = field_name + " text";

		    } else if (field_type.equals("int")) {

			if (Integer.parseInt(field_length) < 16) {
			    pattern[i] = field_name + " int";
			} else {
			    pattern[i] = field_name + " int";
			}

		    } else if (field_type.equals("float")) {
			pattern[i] = field_name + " float";

		    } else {
			pattern[i] = null;
		    }		    
		}

 		System.out.print("    " + join(pattern, ",\n    "));

		System.out.println(",\n    PRIMARY KEY (id));");

		if (has_position) {
		    System.out.println("SELECT AddGeometryColumn('', '" + table_name + "', 'position', -1, 'POINT', 2 );");
		    System.out.println("CREATE INDEX " + table_name + "_geom_idx ON " + table_name + " USING GIST (position GIST_GEOMETRY_OPS);");
		}

		System.out.println("CLUSTER " + table_name + "_pkey ON " + table_name + ";");

		System.out.println("");
	    }
	}
    }

    String i2b(int in, int len) {
	String ret = Integer.toBinaryString(in);

	for (int i = ret.length(); i < len; ++i) {
	    ret = "0" + ret;
	}

	return ret;
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

    public static void main(String args[]) {
	Patterns p = new Patterns();

	if (args.length == 1 && args[0].equals("erlang")) {
	    p.erlang_printer();
	} else if (args.length == 1 && args[0].equals("mysql")) {
	    p.mysql_printer();
	} else if (args.length == 1 && args[0].equals("postgresql")) {
	    p.postgresql_printer();
	} else {
	    System.out.println("Usage: java com.erikburrows.ais.Patterns <erlang|mysql|postgresql>");
	    System.exit(1);
	}
    }
}
