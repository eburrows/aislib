drop table if exists nmea_sentences;
create table nmea_sentences (id int, msg_type int, nmea varchar(128));
drop table if exists msg11_station_info;
create table msg11_station_info (ts timestamp default now(), id int auto_increment primary key, 
messageType int, rpt int, sourceMmsi int unsigned, utcYear int, utcMonth int, utcDay int, utcHour int, utcMinute int, utcSecond int, positionAccuracy int, longitude float, latitude float, fixDeviceType int, spare int, raimFlag int, communicationStatus int unsigned
);
drop table if exists msg16_assignment_mode_command_2_dest;
create table msg16_assignment_mode_command_2_dest (ts timestamp default now(), id int auto_increment primary key, 
messageType int, rpt int, sourceMmsi int unsigned, spare int, destinationMmsi1 int unsigned, offset1 int, increment1 int, destinationMmsi2 int unsigned, offset2 int, increment2 int
);
drop table if exists msg17_dgnss_broadcast;
create table msg17_dgnss_broadcast (ts timestamp default now(), id int auto_increment primary key, 
messageType int, rpt int, sourceMmsi int unsigned, spare int, longitude float, latitude float, spare2 int, data varchar(736)
);
drop table if exists msg1_dynamic_info;
create table msg1_dynamic_info (ts timestamp default now(), id int auto_increment primary key, 
messageType int, rpt int, sourceMmsi int unsigned, navigationalStatus int, rateOfTurn int, speedOverGround int, positionAccuracy int, longitude float, latitude float, courseOverGround float, trueHeading int, UTC int, maneuver int, spare int, raimFlag int, communicationStatus int unsigned
);
drop table if exists msg9_utc_date_inquiry;
create table msg9_utc_date_inquiry (ts timestamp default now(), id int auto_increment primary key, 
messageType int, rpt int, sourceMmsi int unsigned, spare int, destinationMmsi int unsigned, spare2 int
);
drop table if exists msg20_data_link_management_1_slot;
create table msg20_data_link_management_1_slot (ts timestamp default now(), id int auto_increment primary key, 
messageType int, rpt int, sourceMmsi int unsigned, spare int, offset1 int, slots1 int, timeout1 int, increment1 int
);
drop table if exists msg16_assignment_mode_command_1_dest;
create table msg16_assignment_mode_command_1_dest (ts timestamp default now(), id int auto_increment primary key, 
messageType int, rpt int, sourceMmsi int unsigned, spare int, destinationMmsi1 int unsigned, offset1 int, increment1 int, spare2 int
);
drop table if exists msg20_data_link_management_2_slots;
create table msg20_data_link_management_2_slots (ts timestamp default now(), id int auto_increment primary key, 
messageType int, rpt int, sourceMmsi int unsigned, spare int, offset1 int, slots1 int, timeout1 int, increment1 int, offset2 int, slots2 int, timeout2 int, increment2 int
);
drop table if exists msg22_channel_management;
create table msg22_channel_management (ts timestamp default now(), id int auto_increment primary key, 
messageType int, rpt int, sourceMmsi int unsigned, spare int, channelA int, channelB int, transmitReceiveMode int, power int, longitude1 float, latitude1 float, longitude2 float, latitude2 float, addressOrBroadcast int, channelABW int, channelBBW int, transmissionZoneSite int, spare2 int unsigned
);
drop table if exists msg6_addressed_binary_message;
create table msg6_addressed_binary_message (ts timestamp default now(), id int auto_increment primary key, 
messageType int, rpt int, sourceMmsi int unsigned, sequence int, destinationMmsi int unsigned, retransmit int, spare int, applicationId int unsigned, data varchar(920)
);
drop table if exists msg13_safety_ack_2_destinations;
create table msg13_safety_ack_2_destinations (ts timestamp default now(), id int auto_increment primary key, 
messageType int, rpt int, sourceMmsi int unsigned, spare int, destinationMmsi1 int unsigned, sequenceNumber1 int, destinationMmsi2 int unsigned, sequenceNumber2 int
);
drop table if exists msg13_safety_ack_1_destination;
create table msg13_safety_ack_1_destination (ts timestamp default now(), id int auto_increment primary key, 
messageType int, rpt int, sourceMmsi int unsigned, spare int, destinationMmsi1 int unsigned, sequenceNumber1 int
);
drop table if exists msg20_data_link_management_3_slots;
create table msg20_data_link_management_3_slots (ts timestamp default now(), id int auto_increment primary key, 
messageType int, rpt int, sourceMmsi int unsigned, spare int, offset1 int, slots1 int, timeout1 int, increment1 int, offset2 int, slots2 int, timeout2 int, increment2 int, offset3 int, slots3 int, timeout3 int, increment3 int
);
drop table if exists msg12_addressed_safety_message;
create table msg12_addressed_safety_message (ts timestamp default now(), id int auto_increment primary key, 
messageType int, rpt int, sourceMmsi int unsigned, sequence int, destinationMmsi int unsigned, retransmitFlag int, spare int, text varchar(156)
);
drop table if exists msg19_class_b_extended_info;
create table msg19_class_b_extended_info (ts timestamp default now(), id int auto_increment primary key, 
messageType int, rpt int, sourceMmsi int unsigned, localApplicationData int, speedOverGround float, positionAccuracy int, longitude float, latitude float, courseOverGround float, trueHeading int, timestamp int, regionalApplicationData int, name varchar(20), shipCargoType int, lengthFore int, lengthAft int, widthPort int, widthStarboard int, fixDeviceType int, raimFlag int, dte int, assignedMode int, spare int
);
drop table if exists msg3_dynamic_info;
create table msg3_dynamic_info (ts timestamp default now(), id int auto_increment primary key, 
messageType int, rpt int, sourceMmsi int unsigned, navigationalStatus int, rateOfTurn int, speedOverGround int, positionAccuracy int, longitude float, latitude float, courseOverGround float, trueHeading int, UTC int, maneuver int, spare int, raimFlag int, communicationStatus int unsigned
);
drop table if exists msg24_class_b_static_info_b;
create table msg24_class_b_static_info_b (ts timestamp default now(), id int auto_increment primary key, 
messageType int, rpt int, sourceMmsi int unsigned, partNumber int, shipCargoType int, vendorId varchar(7), callSign varchar(7), lengthFore int, lengthAft int, widthPort int, widthStarboard int, spare int
);
drop table if exists msg20_data_link_management_4_slots;
create table msg20_data_link_management_4_slots (ts timestamp default now(), id int auto_increment primary key, 
messageType int, rpt int, sourceMmsi int unsigned, spare int, offset1 int, slots1 int, timeout1 int, increment1 int, offset2 int, slots2 int, timeout2 int, increment2 int, offset3 int, slots3 int, timeout3 int, increment3 int, offset4 int, slots4 int, timeout4 int, increment4 int
);
drop table if exists msg9_sar_aircraft;
create table msg9_sar_aircraft (ts timestamp default now(), id int auto_increment primary key, 
messageType int, rpt int, sourceMmsi int unsigned, altitude int, speedOverGround int, positionAccuracy int, longitude float, latitude float, courseOverGround float, UTC int, regionalApplication int, dte int, spare int, assignedModeFlag int, raimFlag int, communicationStateSelectorFlag int, communicationStatus int unsigned
);
drop table if exists msg18_class_b_position;
create table msg18_class_b_position (ts timestamp default now(), id int auto_increment primary key, 
messageType int, rpt int, sourceMmsi int unsigned, localApplicationData int, speedOverGround float, positionAccuracy int, longitude float, latitude float, courseOverGround float, trueHeading int, timestamp int, regionalApplicationData int, csFlag int, displayFlag int, dscFlag int, bandFlag int, msg22Flag int, assignedMode int, raimFlag int, communicationStatusSelectFlag int, communicationStatus int unsigned
);
drop table if exists msg2_dynamic_info;
create table msg2_dynamic_info (ts timestamp default now(), id int auto_increment primary key, 
messageType int, rpt int, sourceMmsi int unsigned, navigationalStatus int, rateOfTurn int, speedOverGround int, positionAccuracy int, longitude float, latitude float, courseOverGround float, trueHeading int, UTC int, maneuver int, spare int, raimFlag int, communicationStatus int unsigned
);
drop table if exists msg15_interrogation_4_3;
create table msg15_interrogation_4_3 (ts timestamp default now(), id int auto_increment primary key, 
messageType int, rpt int, sourceMmsi int unsigned, spare int, destinationMmsi1 int unsigned, messageId1_1 int, slotOffset1_1 int, spare2 int, messageId1_2 int, slotOffset1_2 int, spare3 int, destinationMmsi2 int unsigned, messageId2_1 int, slotOffset2_1 int, spare4 int
);
drop table if exists msg24_class_b_static_info_a;
create table msg24_class_b_static_info_a (ts timestamp default now(), id int auto_increment primary key, 
messageType int, rpt int, sourceMmsi int unsigned, partNumber int, name varchar(20)
);
drop table if exists msg15_interrogation_2;
create table msg15_interrogation_2 (ts timestamp default now(), id int auto_increment primary key, 
messageType int, rpt int, sourceMmsi int unsigned, spare int, destinationMmsi1 int unsigned, messageId1_1 int, slotOffset1_1 int, spare2 int, messageId1_2 int, slotOffset1_2 int
);
drop table if exists msg8_binary_broadcast;
create table msg8_binary_broadcast (ts timestamp default now(), id int auto_increment primary key, 
messageType int, rpt int, sourceMmsi int unsigned, spare int, applicationID int unsigned, data varchar(952)
);
drop table if exists msg15_interrogation_1;
create table msg15_interrogation_1 (ts timestamp default now(), id int auto_increment primary key, 
messageType int, rpt int, sourceMmsi int unsigned, spare int, destinationMmsi1 int unsigned, messageId1_1 int, slotOffset1_1 int
);
drop table if exists msg7_binary_ack_3_destinations;
create table msg7_binary_ack_3_destinations (ts timestamp default now(), id int auto_increment primary key, 
messageType int, rpt int, sourceMmsi int unsigned, spare int, destinationMmsi1 int unsigned, sequenceNumber1 int, destinationMmsi2 int unsigned, sequenceNumber2 int, destinationMmsi3 int unsigned, sequenceNumber3 int
);
drop table if exists msg7_binary_ack_4_destinations;
create table msg7_binary_ack_4_destinations (ts timestamp default now(), id int auto_increment primary key, 
messageType int, rpt int, sourceMmsi int unsigned, spare int, destinationMmsi1 int unsigned, sequenceNumber1 int, destinationMmsi2 int unsigned, sequenceNumber2 int, destinationMmsi3 int unsigned, sequenceNumber3 int, destinationMmsi4 int unsigned, sequenceNumber4 int
);
drop table if exists msg21_aids_to_nav_report;
create table msg21_aids_to_nav_report (ts timestamp default now(), id int auto_increment primary key, 
messageType int, rpt int, sourceMmsi int unsigned, typeOfAid int, name varchar(20), positionAccuracy int, longitude float, latitude float, lengthFore int, lengthAft int, widthPort int, widthStarboard int, fixDeviceType int, timestamp int, offPosition int, localApplicationData int, raimFlag int, virtual int, assignedMode int, spare int, nameExtension varchar(14)
);
drop table if exists msg5_static_info;
create table msg5_static_info (ts timestamp default now(), id int auto_increment primary key, 
messageType int, rpt int, sourceMmsi int unsigned, aisVersion int, imoNumber int unsigned, callSign varchar(7), name varchar(20), shipCargoType int, lengthFore int, lengthAft int, widthPort int, widthStarboard int, fixDeviceType int, etaMonth int, etaDay int, etaHour int, etaMinute int, draught int, destination varchar(20), dte int, spare int
);
drop table if exists msg14_safety_broadcast;
create table msg14_safety_broadcast (ts timestamp default now(), id int auto_increment primary key, 
messageType int, rpt int, sourceMmsi int unsigned, spare int, text varchar(156)
);
drop table if exists msg13_safety_ack_4_destinations;
create table msg13_safety_ack_4_destinations (ts timestamp default now(), id int auto_increment primary key, 
messageType int, rpt int, sourceMmsi int unsigned, spare int, destinationMmsi1 int unsigned, sequenceNumber1 int, destinationMmsi2 int unsigned, sequenceNumber2 int, destinationMmsi3 int unsigned, sequenceNumber3 int, destinationMmsi4 int unsigned, sequenceNumber4 int
);
drop table if exists msg13_safety_ack_3_destinations;
create table msg13_safety_ack_3_destinations (ts timestamp default now(), id int auto_increment primary key, 
messageType int, rpt int, sourceMmsi int unsigned, spare int, destinationMmsi1 int unsigned, sequenceNumber1 int, destinationMmsi2 int unsigned, sequenceNumber2 int, destinationMmsi3 int unsigned, sequenceNumber3 int
);
drop table if exists msg4_station_info;
create table msg4_station_info (ts timestamp default now(), id int auto_increment primary key, 
messageType int, rpt int, sourceMmsi int unsigned, utcYear int, utcMonth int, utcDay int, utcHour int, utcMinute int, utcSecond int, positionAccuracy int, longitude float, latitude float, fixDeviceType int, spare int, raimFlag int, communicationStatus int unsigned
);
drop table if exists msg7_binary_ack_2_destinations;
create table msg7_binary_ack_2_destinations (ts timestamp default now(), id int auto_increment primary key, 
messageType int, rpt int, sourceMmsi int unsigned, spare int, destinationMmsi1 int unsigned, sequenceNumber1 int, destinationMmsi2 int unsigned, sequenceNumber2 int
);
drop table if exists msg7_binary_ack_1_destination;
create table msg7_binary_ack_1_destination (ts timestamp default now(), id int auto_increment primary key, 
messageType int, rpt int, sourceMmsi int unsigned, spare int, destinationMmsi1 int unsigned, sequenceNumber1 int
);
