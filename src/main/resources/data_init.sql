
--
-- Create test users
INSERT INTO SOPRAFS21.USERS(ID, EMAIL, NAME, PROFILE_PICTUREURL, PROVIDER, PROVIDER_UID, STATUS, TOKEN, LAST_USER_LOCATION)
VALUES (1, 'mark@twen.de', 'Mark', 'SomeURL', 'GoogleProvider', 'SOME_GOOGLE_ID', 'ONLINE', 'DUMMYTOKEN',  'point(8.451911 47.371412)');
INSERT INTO SOPRAFS21.USERS(ID, EMAIL, NAME, PROFILE_PICTUREURL, PROVIDER, PROVIDER_UID, STATUS, TOKEN, LAST_USER_LOCATION)
VALUES (2, 'mark2@twen.de', 'Mark2', 'SomeURL', 'GoogleProvider', 'SOME_GOOGLE_IDj', 'ONLINE', 'DUMMYTOKENj', 'point(8.457969 47.364950 )');
INSERT INTO SOPRAFS21.USERS(ID, EMAIL, NAME, PROFILE_PICTUREURL, PROVIDER, PROVIDER_UID, STATUS, TOKEN, LAST_USER_LOCATION)
VALUES (3, 'mark3@twen.de', 'Mark3', 'SomeURL', 'GoogleProvider', 'SOME_GOOGLE_IDl', 'ONLINE', 'DUMMYTOKENh', 'point(8.456369 47.396379)');
INSERT INTO SOPRAFS21.USERS(ID, EMAIL, NAME, PROFILE_PICTUREURL, PROVIDER, PROVIDER_UID, STATUS, TOKEN)
VALUES (4, 'mark4@twen.de', 'Mark4', 'SomeURL', 'GoogleProvider', 'SOME_GOOGLE_IDle', 'ONLINE', 'DUMMYTOKENeh');
-- Create conversation
INSERT INTO SOPRAFS21.CONVERSATIONS(ID, participant1_id, PARTICIPANT2_ID)
VALUES (1, 1, 2);
-- Create test messages
INSERT INTO SOPRAFS21.MESSAGES(ID, MESSAGE, TIME_STAMP, UNREAD, RECEIVER_ID, SENDER_ID, CONVERSATION_ID)
VALUES (1, 'privet', TO_TIMESTAMP( '2013-03-05', 'yyyy-mm-dd' ), true, 1, 2, 1);
INSERT INTO SOPRAFS21.MESSAGES(ID, MESSAGE, TIME_STAMP, UNREAD, RECEIVER_ID, SENDER_ID, CONVERSATION_ID)
VALUES (2, 'poka', TO_TIMESTAMP( '2013-03-06', 'yyyy-mm-dd' ), true, 1, 2, 1);
INSERT INTO SOPRAFS21.MESSAGES(ID, MESSAGE, TIME_STAMP, UNREAD, RECEIVER_ID, SENDER_ID, CONVERSATION_ID)
VALUES (3, 'privet kak dela', TO_TIMESTAMP( '2013-03-04', 'yyyy-mm-dd' ), true, 2, 1, 1);
INSERT INTO SOPRAFS21.MESSAGES(ID, MESSAGE, TIME_STAMP, UNREAD, RECEIVER_ID, SENDER_ID, CONVERSATION_ID)
VALUES (4, 'dosvidania', TO_TIMESTAMP( '2013-03-11', 'yyyy-mm-dd' ), true, 2, 1, 1);
INSERT INTO SOPRAFS21.MESSAGES(ID, MESSAGE, TIME_STAMP, UNREAD, RECEIVER_ID, SENDER_ID, CONVERSATION_ID)
VALUES (5, 'dosvidaniaagain', TO_TIMESTAMP( '2013-03-03', 'yyyy-mm-dd' ), true, 2, 1, 1);
--Create test park
INSERT INTO SOPRAFS21.PARKS(ID, COORDINATE, CREATOR_ID)
VALUES (1, 'point(10.451911 50.371412)', 3);

-- Create Path
INSERT INTO SOPRAFS21.PATHS(ID,ROUTE,DISTANCE,CREATOR_ID,DESCRIPTION)
VALUES (1,'LINESTRING(8.451911 47.371412, 8.457969 47.364950)',1.0,1,'Path1');
INSERT INTO SOPRAFS21.PATHS(ID,ROUTE,DISTANCE,CREATOR_ID,DESCRIPTION)
VALUES (2,'LINESTRING(8.561912 48.371413, 8.457968 47.364951)',1.1,1,'Path4');
INSERT INTO SOPRAFS21.PATHS(ID,ROUTE,DISTANCE,CREATOR_ID,DESCRIPTION)
VALUES (3,'LINESTRING(8.571912 48.381413, 8.457968 47.364951)',1.2,3,'Path3');
INSERT INTO SOPRAFS21.PATHS(ID,ROUTE,DISTANCE,CREATOR_ID,DESCRIPTION)
VALUES (4,'LINESTRING(8.451912 47.371413, 8.451922 47.371422,  8.457968 47.364951)',1.3,3,'Path4');