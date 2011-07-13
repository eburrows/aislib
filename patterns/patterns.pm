
package Patterns;

%patterns = (
    (
     regex => "^" . i2b(1,6) . ".*",
     name => "msg1_dynamic_info",
     fields = [
	 "messageType" => ( type => int, bits => 6),
	 "repeat => ( type => int, bits => 2),
		     "sourceMmsi:int:30",
		     "navigationalStatus:int:4",
		     "rateOfTurn:int:8",
		     "speedOverGround:int:10",
		     "positionAccuracy:int:1",
		     "longitude:float:28:600000",
		     "latitude:float:27:600000",
		     "courseOverGround:float:12:10",
		     "trueHeading:int:9",
		     "UTC:int:6",
		     "maneuver:int:2",
		     "spare:int:3",
		     "raimFlag:int:1",
		     "communicationStatus:int:19"
     ]
    )
);

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
		     "UTC:int:6",
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
		     "UTC:int:6",
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
		     "UTC:int:6",
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
		     "UTC:int:6",
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
		     "messageId1.1:int:6",
		     "slotOffset1.1:int:12",
		     "spare2:int:2",
		     "messageId1.2:int:6",
		     "slotOffset1.2:int:12",
		     "spare3:int:2",
		     "destinationMmsi2:int:30",
		     "messageId2.1:int:6",
		     "slotOffset2.1:int:12",
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
		     "messageId1.1:int:6",
		     "slotOffset1.1:int:12",
		     "spare2:int:2",
		     "messageId1.2:int:6",
		     "slotOffset1.2:int:12"
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
		     "messageId1.1:int:6",
		     "slotOffset1.1:int:12"
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
