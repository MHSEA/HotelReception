package Reception;

import java.io.*;
import java.net.URL;
import java.util.*;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

public class Reasoner {
// Main Class - Start
	
	public Hotel reception;
	public MainReception Myface;


	// The lists holding the class instances of all domain entities - Start
	public List theHotelList = new ArrayList();
	public List theRoomList = new ArrayList();
	public List theAmenityList = new ArrayList();
	public List theRecentThing = new ArrayList();
	public List theBookingList = new ArrayList();
	public List theCustomerList = new ArrayList();
	public List help = new ArrayList();
    // The lists holding the class instances of all domain entities - End
	
	
	
	// Synonyms for the domain entities names - Start
	public Vector<String> HotelSyn = new Vector<String>();
	public Vector<String> RoomSyn = new Vector<String>();
	public Vector<String> CustomerSyn = new Vector<String>();
	public Vector<String> AmenitySyn = new Vector<String>();
	public Vector<String> BookingSyn = new Vector<String>();
	public Vector<String> RecentObjectSyn = new Vector<String>();
	public Vector<String> HelpSyn = new Vector<String>();
	// Synonyms for the domain entities names - End
	
	
	
	
	// Selects a Method To Use in Query
	public String questiontype = "";
	
	// Selects which class to list in query
	public List classtype = new ArrayList();
	
	// Selects the attributes to check in the query
	public String attributetype = "";

	// Last object dealt with
	public Object Currentitemofinterest;
	
	// Last index used
	public Integer Currentindex;
	public String tooltipstring = "";
	public String URL = "";  // URL for showing Hotel info

	
	// Constructor - Start
	public Reasoner(MainReception myface) {
		Myface = myface; // reference to GUI to update Tooltip-Text
	}
	// Constructor - End
	
	
	// Load Hotel database from DataBase.xml File - Start
	public void initknowledge() 
		{
		
			JAXB_XMLParser xmlhandler = new JAXB_XMLParser();
			// Loading XML file
			File xmlfiletoload = new File("DataBase.xml"); 
			
		// Synonyms - Start
			
			// Add synonyms for Hotel - Start
			HotelSyn.add("address");
			HotelSyn.add("place");
			HotelSyn.add("hotel");
			HotelSyn.add("hotels");
			HotelSyn.add("reception");
			// Add synonyms for Hotel - End
			
			
			
			// Add synonyms for Room - Start
			RoomSyn.add("room");
			RoomSyn.add("single");
			RoomSyn.add("for one");
			RoomSyn.add("double");
			RoomSyn.add("for two");
			RoomSyn.add("twin");
			RoomSyn.add("for three");
			RoomSyn.add("triple");
			RoomSyn.add("for four");			
			RoomSyn.add("quad");
			RoomSyn.add("suite");
			RoomSyn.add("big family");
			RoomSyn.add("apartments");
			// Add synonyms for Room - End

			
			// Add synonyms for Customer - Start		
			CustomerSyn.add("customers");
			CustomerSyn.add("customer");
			CustomerSyn.add("guests");
			// Add synonyms for Customer - End
			
			
			// Add synonyms for Amenity - Start
			AmenitySyn.add("amenity");
			AmenitySyn.add("amenities");
			AmenitySyn.add("services");
			AmenitySyn.add("service");
			AmenitySyn.add("pool");
			AmenitySyn.add("swim");
			AmenitySyn.add("swiming");
			AmenitySyn.add("disco");
			AmenitySyn.add("drink");
			AmenitySyn.add("club");
			AmenitySyn.add("dance");
			AmenitySyn.add("exercise");
			AmenitySyn.add("gym");
			AmenitySyn.add("running");
			AmenitySyn.add("spinning");
			AmenitySyn.add("meeting");
			AmenitySyn.add("business");
			AmenitySyn.add("print");
			AmenitySyn.add("internet");
			AmenitySyn.add("email");
			AmenitySyn.add("fax");
			AmenitySyn.add("Dining");
			AmenitySyn.add("food");
			AmenitySyn.add("breakfast");
			AmenitySyn.add("lunch");
			AmenitySyn.add("dinner");
			AmenitySyn.add("snack");
			AmenitySyn.add("brunch");
			// Add synonyms for Amenity - End
			
		
			// Add synonyms for Booking - Start
			BookingSyn.add("bookings");
			BookingSyn.add("booking");
			BookingSyn.add("booked");
			BookingSyn.add("reservations");
			BookingSyn.add("reservation");
			BookingSyn.add("reserve");
			// Add synonyms for Booking - End
			
			
			// Add synonyms for Recent Objects - Start
			// spaces to prevent collision with "wHERe"
			RecentObjectSyn.add(" this");
			RecentObjectSyn.add(" that");	
			RecentObjectSyn.add(" him");
			RecentObjectSyn.add(" her"); 
			RecentObjectSyn.add(" it");
			// Add synonyms for Recent Objects - End
			
		// Synonyms - End	

		try 
			{
				// Initiate input stream
				FileInputStream readthatfile = new FileInputStream(xmlfiletoload); 
				reception = xmlhandler.loadXML(readthatfile);

				// Fill the list from generated XML file
				theRoomList = reception.getRoom();
				theAmenityList = reception.getAmenity();
				theCustomerList = reception.getCustomer();
				theBookingList = reception.getBooking();
				// Force it to be a List,
				theHotelList.add(reception);
	

				


				Console.Println("Validating XML Database...\n");
			}

		catch (Exception e) 
			{
				e.printStackTrace();
				Console.Println("error in init");
			}
	}
	// Load Hotel database from DataBase.xml File - End
	
	

	// Generate an Answer - Start
	public Vector<String> generateAnswer(String input)
	{
	    final ImageIcon icon = new ImageIcon("Resources//Login.png");
        
        // Method to get the time of day and return the correct greeting for Thank you Command - Start
		Calendar c = Calendar.getInstance();
		int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
        String greeting = null;
        if(timeOfDay>=1 && timeOfDay<=12){
            greeting = "Have a great day";
        } else if(timeOfDay>=12 && timeOfDay<=16){
            greeting = "Have a Wonderful Afternoon";
        } else if(timeOfDay>=16 && timeOfDay<=21){
            greeting = "Have a Good Evening";
        } else if(timeOfDay>=21 && timeOfDay<=24){
            greeting = "Good Night";
        }
        // Method to get the time of day and return the correct greeting for Thank you Command  - End
        
        
        
        // Method to get the time of day and return the correct greeting for Hi Command - Start
        String higreeting = null;
        if(timeOfDay>=1 && timeOfDay<=12){
            higreeting = "Good Morning";
        } else if(timeOfDay>=12 && timeOfDay<=16){
            higreeting = "Good Afternoon";
        } else if(timeOfDay>=16 && timeOfDay<=21){
            higreeting = "Good Evening";
        } else if(timeOfDay>=21 && timeOfDay<=24){
            higreeting = "Good Night";
        }
        // Method to get the time of day and return the correct greeting for Hi Command - End
		
        
        Vector<String> out = new Vector<String>();
		out.clear();

		questiontype = "none";

		// Check if answer was generated
		Integer Answered = 0; 
		Integer subjectcounter = 0; 
		
		// Convert input to lower case
		input = input.toLowerCase(); 
		String answer = ""; 
		

		// Convert input to lower case
		input = input.toLowerCase(); 

		// Question Type Validation - Start
		if (input.contains("how many")) {
			questiontype = "amount";
			input = input.replace("how many", "<b>how many</b>");
		}
		if (input.contains("number of")) {
			questiontype = "amount";
			input = input.replace("number of", "<b>number of</b>");
		}
		if (input.contains("amount of")) {
			questiontype = "amount";
			input = input.replace("amount of", "<b>amount of</b>");
		}
		if (input.contains("count")) {
			questiontype = "amount";
			input = input.replace("count", "<b>count</b>");
		}

		if (input.contains("what kind of")) {
			questiontype = "list";
			input = input.replace("what kind of", "<b>what kind of</b>");
		}
		if (input.contains("what")) {
			questiontype = "list";
			input = input.replace("what kind of", "<b>what kind of</b>");
		}
		if (input.contains("list all")) {
			questiontype = "list";
			input = input.replace("list all", "<b>list all</b>");
		}
		if (input.contains("diplay all")) {
			questiontype = "list";
			input = input.replace("diplay all", "<b>diplay all</b>");
		}

		if (input.contains("locate")) {
			questiontype = "checkfor";
			input = input.replace("locate", "<b>locate</b>");
		}
		if (input.contains("is there a")) {
			questiontype = "checkfor";
			input = input.replace("is there a", "<b>is there a</b>");
		}
		if (input.contains("i am searching")) {
			questiontype = "checkfor";
			input = input.replace("i am searching", "<b>i am searching</b>");
		}
		if (input.contains("i am looking for")) {
			questiontype = "checkfor";
			input = input.replace("i am looking for", "<b>i am looking for</b>");
		}
		if (input.contains("do you have") && !input.contains("how many")) {
			questiontype = "checkfor";
			input = input.replace("do you have", "<b>do you have</b>");
		}
		if (input.contains("i look for")) {
			questiontype = "checkfor";
			input = input.replace("i look for", "<b>i look for</b>");
		}
		if (input.contains("is there")) {
			questiontype = "checkfor";
			input = input.replace("is there", "<b>is there</b>");
		}
		if (input.contains("where") || input.contains("can't find") || input.contains("can i find")
				|| input.contains("way to"))
		{
			questiontype = "location";
			Console.Println("Finding Location");
		}
		// Question Type Validation - End
		
		
		
		// Commands - [Can i] - Start
		if (input.contains("can i book") 
				|| input.contains("can i reserve") 
				|| input.contains("can i book a")
				|| input.contains("am i able to") 
				|| input.contains("could i book")
				|| input.contains("how about")
				|| input.contains("i need") 
				|| input.contains("i want to book"))
		{
			questiontype = "intent";
			Console.Println("Find Room Availability");
		}
		// Commands - [Can i] - End


		
		// Commands - [Thank you] - Start
		if (input.contains("thank you") 
				|| input.contains("bye") 
				|| input.contains("thanks")
				|| input.contains("cool thank")) 
		{
			questiontype = "thanks";
			Console.Println("Thank you Message");
		}
		// Commands - [Thank you] - End

		
		// Commands - [Hi] - Start
		if (input.contains("hi") 
				|| input.contains("hello")
				|| input.contains("hey")) 
		{
			questiontype = "hi";
			Console.Println("Hello Message");
		}
		// Commands - [Hi] - End
		
		
		// Commands - [Bye] - Start
		if (input.contains("bye") 
				|| input.contains("good buy")) 
		{
			questiontype = "bye";
			Console.Println("Bye Message");
		}
		// Commands - [Bye] - End
		
		
		// Commands - [Help] - Start
		String questionmark = "?";
		if (input.contains("help")
				|| input.equalsIgnoreCase(questionmark)) 
		{
			questiontype = "Help";
			Console.Println("Help Message");
		}
		// Commands - [Help] - End
		
		
		
		// Commands - [Exit] - Start
		if (input.contains("Exit") 
				|| input.contains("exit") 
				|| input.contains("Exit")) 
		{
			questiontype = "Exit";
			Console.Println("Exit");
		}
		// Commands - [Exit] - End

		
		
		// Commands - [CLS] - Start
		if (input.contains("cls") 
				|| input.contains("clean")) {
			questiontype = "CLS";
			Console.Println("Clear Screen");
		}
		// Commands - [CLS] - End

		
		
		// Commands - [save] - Start
		String save = ("save");
		if (input.equals(save)) 
		{
			questiontype = "save";
			Console.Println("Save output");
		}
		// Commands - [save] - End
		
		
		
		
		// Check - [Command Subject] - Start
		for (int x = 0; x < RoomSyn.size(); x++) {
			if (input.contains(RoomSyn.get(x))) {
				classtype = theRoomList;
				input = input.replace(RoomSyn.get(x), "<b>" + RoomSyn.get(x) + "</b>");
				subjectcounter = 1;
				Console.Println("Class type Room recognised.");
			}
		}
		
		for (int x = 0; x < CustomerSyn.size(); x++) {
			if (input.contains(CustomerSyn.get(x))) {
				classtype = theCustomerList;
				input = input.replace(CustomerSyn.get(x), "<b>" + CustomerSyn.get(x) + "</b>");
				subjectcounter = 1;
				Console.Println("Class type Cutomer recognised.");
			}
		}
		
		for (int x = 0; x < AmenitySyn.size(); x++) {
			if (input.contains(AmenitySyn.get(x))) {
				classtype = theAmenityList;
				input = input.replace(AmenitySyn.get(x), "<b>" + AmenitySyn.get(x) + "</b>");
				subjectcounter = 1;
				Console.Println("Class type Amenity recognised.");
			}
		}
		
		for (int x = 0; x < BookingSyn.size(); x++) {
			if (input.contains(BookingSyn.get(x))) {
				classtype = theBookingList;
				input = input.replace(BookingSyn.get(x), "<b>" + BookingSyn.get(x) + "</b>");
				subjectcounter = 1;
				Console.Println("Class type Booking recognised.");
			}
		}

		if (subjectcounter == 0) {
			for (int x = 0; x < RecentObjectSyn.size(); x++) {
				if (input.contains(RecentObjectSyn.get(x))) {
					classtype = theRecentThing;
					input = input.replace(RecentObjectSyn.get(x), "<b>" + RecentObjectSyn.get(x) + "</b>");
					subjectcounter = 1;
					Console.Println("Class type recognised as" + RecentObjectSyn.get(x));
				}
			}
		}
		// Check - [Command Subject] - [End]


	
		// Check - [Command Subject (More than one Subject)] - Start
		Console.Println("subjectcounter = " + subjectcounter);

		for (int x = 0; x < HotelSyn.size(); x++) {
			if (input.contains(HotelSyn.get(x))) {
				if (subjectcounter == 0) {
					input = input.replace(HotelSyn.get(x), "<b>" + HotelSyn.get(x) + "</b>");
					classtype = theHotelList;
					Console.Println("class type Hotel recognised");
				}
			}
		}
		// Check - [Command Subject (More than one Subject)] - End

		
		
		
		// Response - [Number of Rooms, Bookings, ...] - Start
		if (questiontype == "amount" + "booked") 
		{
			Integer numberof = Count(classtype);
			answer = ("<font color=#2F4F4F  face = Roman  size = 4>Reception: There are " + numberof + " "+ classtype.get(0).getClass().getSimpleName() + "s " + "Available"+"</font>");
			Answered = 1;
		}
		// Response - [Number of Rooms, Bookings, ...] - End

		
		

		// Response - [Number of Rooms, Bookings, ...] - Start
		if (questiontype == "amount") 
		{
			Integer numberof = Count(classtype);
			answer = ("<font color=#2F4F4F  face = Roman  size = 4>Reception: There are " + numberof + " "+ classtype.get(0).getClass().getSimpleName() + "s " + "Available"+"</font>");
			Answered = 1;
		}
		// Response - [Number of Rooms, Bookings, ...] - End

		
		
		// Response - [List of Rooms, Amenities] - Start	
			if (questiontype == "list" && classtype.get(0).getClass().getSimpleName().equals("Amenity")){
				answer = "<font color=#2F4F4F  face = Roman  size = 4>Reception: We have the following " + classtype.get(0).getClass().getSimpleName() + "s:" + ListAll(classtype)+"</font>";
	    		Answered = 1;}
			
			if (questiontype == "list" && classtype.get(0).getClass().getSimpleName().equals("Room")){
				answer = "<font color=#2F4F4F  face = Roman  size = 4>Reception: We have the following " + classtype.get(0).getClass().getSimpleName() + "s:" + ListAll(classtype)+"</font>";
				Answered = 1;}
		// Response - [List of Rooms, Amenities] - End	
			
			
		
		// Response - [List of Customers, Bookings, Admin ONLY] - Start				
			if (questiontype == "list" && classtype.get(0).getClass().getSimpleName().equals("Customer")){
			    JPasswordField pwd = new JPasswordField(10);
			    JOptionPane.showConfirmDialog(null, pwd,"Restricted Area - Enter Password",JOptionPane.OK_CANCEL_OPTION,0,icon);
			    if (new String(pwd.getPassword()).equals("HRAdmin22")) {
		        	answer = "<font color=#2F4F4F  face = Roman  size = 4>Reception: We have the following " + classtype.get(0).getClass().getSimpleName() + "s:" + ListAll(classtype) +"</font>";
		    		Answered = 1;
			} else {
				JOptionPane.showMessageDialog(null,"Wrong Password!","Error",JOptionPane.WARNING_MESSAGE);
	        	Answered = 1;
				}
			}
	        
		       
			if (questiontype == "list" && classtype.get(0).getClass().getSimpleName().equals("Booking")){
			    JPasswordField pwd = new JPasswordField(10);
			    JOptionPane.showConfirmDialog(null, pwd,"Enter Password",JOptionPane.OK_CANCEL_OPTION,0,icon);
			    if (new String(pwd.getPassword()).equals("HRAdmin22")) {
		        	answer = "<font color=#2F4F4F  face = Roman  size = 4>Reception: We have the following " + classtype.get(0).getClass().getSimpleName() + "s:" + ListAll(classtype) +"</font>";
		    		Answered = 1;
			    }else {
			    	JOptionPane.showMessageDialog(null,"Wrong Password!","Error",JOptionPane.WARNING_MESSAGE);
			        Answered = 1;
					}
			}
		//Response - [List of Customers, Bookings, Admin ONLY] - End
		
		
		
		// Response - [Check] - Start
		if (questiontype == "checkfor") { // test for a certain Subject instance

			Vector<String> check = CheckFor(classtype, input);
			answer = (check.get(0));
			Answered = 1; // An answer was given
			if (check.size() > 1) {
				Currentitemofinterest = classtype.get(Integer.valueOf(check.get(1)));
				Console.Println("Classtype List = " + classtype.getClass().getSimpleName());
				Console.Println("Index in Liste = " + Integer.valueOf(check.get(1)));
				Currentindex = Integer.valueOf(check.get(1));
				theRecentThing.clear(); // Clear it before adding (changing) the
				// now recent thing
				theRecentThing.add(classtype.get(Currentindex));
			}
		}
		// Response - [Check] - End
		
		
		
		// Location Question - [Start]
		if (questiontype == "location") { 
			answer = ("<font color=#2F4F4F  face = Roman  size = 4>Reception: You can find the " + classtype.get(0).getClass().getSimpleName() + " " + Location(classtype, input) +"</font>");
			Answered = 1;
		}
		if ((questiontype == "intent" && classtype == theRoomList)
				|| (questiontype == "intent" && classtype == theRecentThing)) {

			// Can I Book the Room or Not (Book it or Not)
			answer = ("<font color=#2F4F4F  face = Roman  size = 4>Reception: " + RoomAvailable(classtype, input) + "</font>");
			Answered = 1;
		}
		// Location Question - [End]
		
		
		
		// Response - [Thank You] - Start
		if (questiontype == "thanks") {
			//This method gets username OS
			String name = System.getProperty("user.name");
			answer = ("<font color=#2F4F4F  face = Roman  size = 4>Reception: You are very welcome " + "<Strong>" + name +"</Strong> " + greeting + "." +"</font>");
			Answered = 1;}
		// Response - [Thank You] - Start
		
		
		
		// Response - [Hi] - Start
		if (questiontype == "hi") {
			//This method gets username OS
			String name = System.getProperty("user.name");
			answer = ("<font color=#2F4F4F  face = Roman  size = 4>Reception: " + higreeting +" "+ "<Strong>" + name + "</Strong>" +" How may i help you ?" + "</font>");
			Answered = 1;}
		// Response - [Hi] - Start
        
		
		
		// Response - [Bye] - Start
		if (questiontype == "bye") {
			//This method gets username OS
			String name = System.getProperty("user.name");
			answer = ("<font color=#2F4F4F  face = Roman  size = 4>Reception: " + greeting +"<Strong> "+ name +"</Strong>" +" See you soon.</Font>");
			Answered = 1;}
		// Response - [Bye] - Start
		
		
		
		// Response - [Help] - Start
		if (questiontype == "Help") {
			answer = "<br>" + "<font color=#2F4F4F  face = Roman  size = 5>You can use following commands:  </font>" 
					+ "<br>" + "<font color=#2F4F4F  face = Roman>-------------------------------------------------------------------</font>"
					+ "<br>" + "<font color=#330066  face = Roman  size = 5>Save: Save the conversation to the Desktop</font>"
					+ "<br>" + "<font color=#008000  face = Roman  size = 5>Cls: Clear the screen</font>"
					+ "<br>" + "<font color=#FF0000  face = Roman  size = 5>Exit: Quit the program</font>" 
					+ "<br>" + "<br>" 
					+ "<br>" + "<font color=#2F4F4F  face = Roman  size = 5>Also you can ask following the questions:  </font>" 
					+ "<br>" + "<font color=#2F4F4F  face = Roman>-------------------------------------------------------------------</font>"
					+ "<br>" + "<font color=#2F4F4F  face = Roman size = 4 >-Where is the hotel</font>" 
					+ "<br>" + "<font color=#2F4F4F  face = Roman  size = 4>-I am looking for a double room</font>" 
					+ "<br>" + "<font color=#2F4F4F   face = Roman  size = 4>-What kind of rooms are available</font>" 
					+ "<br>" + "<font color=#2F4F4F  face = Roman  size = 4>-Where are the room locations</font>" 
					+ "<br>" + "<font color=#2F4F4F   face = Roman  size = 4>-How many rooms are available</font>"
					+ "<br>" + "<font color=#2F4F4F  face = Roman  size = 4>-Can i book a room</font>" + "<br>";
			
			Answered = 1;
		}

		if (questiontype == "?") {
			answer = ("<font color=#2F4F4F  face = Roman  size = 4>You can type:</font> ");
			Answered = 1;
			}
		// Response - [Help] - End

		
		
		// Response - [Exit] - Start
		if (questiontype == "Exit") {
			System.exit(0);}
		// Response - [Exit] - End

		
		
		// Response - [Save] - Start
		if (questiontype == "save") {
			String desktop = System.getProperty ("user.home") + "\\Desktop\\";
			Console.saveToFile();
			Console.Confirmation("Outputhas been successfully saved to:\n" + desktop + "Reception-Output.html");
			answer = ("<font color=#2F4F4F  face = Roman  size = 4>Reception: Saved</font>");
			
			Answered = 1;
			
		}
		// Response - [Save] - End
		
		
		
		// Response - [CLS] - Start
		if ((questiontype == "CLS") || (questiontype == "CLEAR"))
		{
			
			Reception.MainReception.Info.setText(
			"<font color=#2F4F4F  face = Roman  size = 4>Background information about the conversations topic will be displayed in this window.</font>");
			Reception.MainReception.dialoghistory.removeAllElements();
			Reception.MainReception.dialoghistory.add
			("<font color=#2F4F4F  face = Roman  size = 5>>Welcome to the Hotel Reception Helpdesk, please type your question.</font> "
			+ "<font color=#2F4F4F  face = Roman  size = 5>Following services are available: Available Rooms, Bookings, Checkin and Checkouts,</font> "
			+ "Just ask me.</H3><br>"
			+ "<font color=#2F4F4F  face = Roman  size = 4>To Start, you can type help to explore more. </font><br>");
			Answered = 1;
		}
		// Response - [CLS] - End

		
		
		// Response - [Null] - [Start]
		if (Answered == 0) {
			answer = ("<font color=#e60000  face = Roman  size = 5>Reception: Sorry I didn't understand that.</font>" + "<br> " +
					  "<font color=#e60000  face = Roman  size = 5>You can type [ <Strong>Help</Strong> ] for more information and list of commands.</font>");
			}
		// Response - [Null] - [End]
		
		
		out.add(input);
		out.add(answer);
		return out;
	}
	// Generate an Answer - [End]
	


	
	// Methods to generate answers for the different kinds of Questions - Start
	public String RoomAvailable(List thelist, String input) {
		
		boolean available = true;
		String answer = "";
		Room curbook = new Room();
		String roometype = "";

		if (thelist == theRoomList) {
			int counter = 0;

			// Identify Room Type
			for (int i = 0; i < thelist.size(); i++) {
				curbook = (Room) thelist.get(i);
				if (input.contains(curbook.gettype().toLowerCase())) { 
					counter = i;
					Currentindex = counter;
					theRecentThing.clear();
					classtype = theRoomList;
					theRecentThing.add(classtype.get(Currentindex));
					roometype = curbook.gettype();
					if (input.contains(curbook.gettype().toLowerCase())) {
						input = input.replace(curbook.gettype().toLowerCase(),
								"<b>" + curbook.gettype().toLowerCase() + "</b>");
					}
					i = thelist.size() + 1; // force break
				}
			}
		}
		
		// maybe other way round or double
		if (thelist == theRecentThing && theRecentThing.get(0) != null) {

			if (theRecentThing.get(0).getClass().getSimpleName().toLowerCase().equals("room")) { 
				curbook = (Room) theRecentThing.get(0);
				roometype = curbook.gettype();
			}
		}

		
		// Check if the room is booked or not - Start
		for (int i = 0; i < theBookingList.size(); i++) {
			Booking curlend = (Booking) theBookingList.get(i);
			if (curbook.getisBooked().toLowerCase().equals(curlend.getbookingID().toLowerCase())) { 
				input = input.replace(curlend.getbookingID().toLowerCase(),
						"<b>" + curlend.getbookingID().toLowerCase() + "</b>");
				available = false;
				i = thelist.size() + 1; // force break
			}
		}
		if (available) 
		{
			answer = "<font color=#2F4F4F  face = Roman  size = 4>There are rooms available to book</font>";
		} else {
			answer = "<font color=#e60000  face = Roman  size = 4>Sorry, Mentioned room type is fully booked.</font>";
		}
		// Check if the room is booked or not - End
		
		
		
		
		URL = "https://soc.uwl.ac.uk/~21240951/Hotel_Reception/" + classtype.get(0).getClass().getSimpleName().toLowerCase() + ".html";
		Console.Println("URL = " + URL);
		tooltipstring = readwebsite(URL);
		String html = "<html>" + tooltipstring + "</html>";
		Myface.setmytooltip(html);
		Myface.setmyinfobox(URL);

		return (answer);

	}
	// Methods to generate answers for the different kinds of Questions - End
	
	
	
	// Response Web Area - [Type of Rooms] - Start
	public Integer Count(List thelist) { 

		
		URL = "https://soc.uwl.ac.uk/~21240951/Hotel_Reception/" + classtype.get(0).getClass().getSimpleName().toLowerCase() + ".html";
		Console.Println("URL = " + URL);
		tooltipstring = readwebsite(URL);
		String html = "<html>" + tooltipstring + "</html>";
		Myface.setmytooltip(html);
		Myface.setmyinfobox(URL);

		return thelist.size();
	}
	// Response Web Area - [Type of Rooms] - End

	
	
	// Response - "What kind of..." - Start
	public String ListAll(List thelist) {

		String listemall = "<ul>";
		if (thelist == theRoomList) {
			for (int i = 0; i < thelist.size(); i++) {
				Room curbook = (Room) thelist.get(i);
				listemall = listemall + "<li>" + (curbook.gettype() + "</li>");
			}
		}
		if (thelist == theCustomerList) {
			for (int i = 0; i < thelist.size(); i++) {
				Customer curmem = (Customer) thelist.get(i);
				listemall = listemall + "<li>"
						+ (curmem.getfirsName() + " " + curmem.getlastName() + "</li>");
			}
		}
		if (thelist == theAmenityList) {
			for (int i = 0; i < thelist.size(); i++) {
				Amenity curcat = (Amenity) thelist.get(i);
				listemall = listemall + "<li>" + (curcat.getName() + "</li>");
			}
		}
		if (thelist == theBookingList) {
			for (int i = 0; i < thelist.size(); i++) {
				Booking curlend = (Booking) thelist.get(i);
				listemall = listemall + "<li>" + (curlend.getbookingID() + "</li>");
			}
		}
		listemall += "</ul>";

	
		URL = "https://soc.uwl.ac.uk/~21240951/Hotel_Reception/" + classtype.get(0).getClass().getSimpleName().toLowerCase() + ".html";
		Console.Println("URL = " + URL);
		tooltipstring = readwebsite(URL);
		String html = "<html>" + tooltipstring + "</html>";
		Myface.setmytooltip(html);
		Myface.setmyinfobox(URL);
		return listemall;
	}
	// Response - "What kind of..." - End
	
	
	
	// Response - "Do you have..." - Start
	public Vector<String> CheckFor(List thelist, String input) {

		Vector<String> yesorno = new Vector<String>();
		if (classtype.isEmpty()) {
			yesorno.add("<font color=#e60000  face = Roman  size = 4>Sorry I didn't understand that.</font>" + "<br> "
					+ "<font color=#e60000  face = Roman  size = 4>You can type [ <Strong>Help</Strong> ] for more information and list of commands.</font>");
		} else {
			yesorno.add("<font color=#2F4F4F  face = Roman  size = 4>Requested Room is not available.</font> " + classtype.get(0).getClass().getSimpleName());
		}

		Integer counter = 0;
		if (thelist == theRoomList) {
			for (int i = 0; i < thelist.size(); i++) {
				Room curbook = (Room) thelist.get(i);
				if (input.contains(curbook.gettype().toLowerCase()))
						{
					counter = i;
					yesorno.set(0, "<font color=#2F4F4F  face = Roman  size = 4>Requested Room is Available.<font color=#2F4F4F  face = Roman  size = 5>"); 
					yesorno.add(counter.toString());
					i = thelist.size() + 1; // force break
				}
			}
		}
		if (thelist == theCustomerList) {
			for (int i = 0; i < thelist.size(); i++) {
				Customer curmem = (Customer) thelist.get(i);
				if (input.contains(curmem.getfirsName().toLowerCase())
						|| input.contains(curmem.getlastName().toLowerCase())
						|| input.contains(curmem.getCity().toLowerCase())) {
					counter = i;
					yesorno.set(0, "<font color=#2F4F4F  face = Roman  size = 4>Yes we do have such a Customer</font>");
					yesorno.add(counter.toString());
					i = thelist.size() + 1;
				}
			}
		}
		if (thelist == theAmenityList) {
			for (int i = 0; i < thelist.size(); i++) {
				Amenity curcat = (Amenity) thelist.get(i);
				if (input.contains(curcat.getName().toLowerCase())) {
					counter = i;
					yesorno.set(0, "<font color=#2F4F4F  face = Roman  size = 4>Yes we have such a Amenity</font>");
					yesorno.add(counter.toString());
					i = thelist.size() + 1;
				}
			}
		}
		if (thelist == theBookingList) {
			for (int i = 0; i < thelist.size(); i++) {
				Booking curlend = (Booking) thelist.get(i);
				if (input.contains(curlend.getbookingID().toLowerCase())
						|| input.contains(curlend.getcustomerID().toLowerCase())) {
					counter = i;
					yesorno.set(0, "<font color=#2F4F4F  face = Roman  size = 4>Yes we have such a Booking</font>");
					yesorno.add(counter.toString());
					i = thelist.size() + 1;
				}
			}
		}
		if (classtype.isEmpty()) {
			Console.Println("<font color=#2F4F4F  face = Roman  size = 4>Not class type given.</font>");
		} else {
			URL = "https://soc.uwl.ac.uk/~21240951/Hotel_Reception/" + classtype.get(0).getClass().getSimpleName().toLowerCase() + ".html";
			Console.Println("URL = " + URL);
			tooltipstring = readwebsite(URL);
			String html = "<html>" + tooltipstring + "</html>";
			Myface.setmytooltip(html);
			Myface.setmyinfobox(URL);
		}
		return yesorno;
	}
	// Response - "Do you have..." - End
	
	
	
	// Method to retrieve location information - Start
	public String Location(List classtypelist, String input) {

		List thelist = classtypelist;
		String location = "";

		// if a pronomial was used "it", "them" etc: Reference to the recent input
		if (thelist == theRecentThing && theRecentThing.get(0) != null) {
			if (theRecentThing.get(0).getClass().getSimpleName().toLowerCase().equals("room")) { 
				Room curbook = (Room) theRecentThing.get(0); 
				location = (curbook.getLocation() + " "); 
			}
			if (theRecentThing.get(0).getClass().getSimpleName().toLowerCase().equals("customer")) {
				Customer curmem = (Customer) theRecentThing.get(0); 
				location = (curmem.getCity() + " " + curmem.getStreet() + " " + curmem.getHousenumber()); 
			}
			if (theRecentThing.get(0).getClass().getSimpleName().toLowerCase().equals("amenity")) 
				{
					Amenity curcat = (Amenity) theRecentThing.get(0);
					location = (curcat.getLocation() + " ");
				}
			if (theRecentThing.get(0).getClass().getSimpleName().toLowerCase().equals("hotel")) { 
			
				location = (reception.getCity() + " " + reception.getStreet() + reception
						.getHousenumber());
			}
		}

		// if a direct noun was used (Room, Customer, etc)
		else {
			if (thelist == theRoomList) {
				int counter = 0;
				for (int i = 0; i < thelist.size(); i++) {
					Room curbook = (Room) thelist.get(i);
					if (input.contains(curbook.gettype().toLowerCase()))
						counter = i;
						location = (curbook.getLocation() + " ");
						Currentindex = counter;
						theRecentThing.clear();
						classtype = theRoomList;
						theRecentThing.add(classtype.get(Currentindex));
						i = thelist.size() + 1; // force break
					}
				}
			}
			if (thelist == theCustomerList) {
				int counter = 0;
				for (int i = 0; i < thelist.size(); i++) {
					Customer curmember = (Customer) thelist.get(i);
					if (input.contains(curmember.getfirsName().toLowerCase())
							|| input.contains(curmember.getlastName().toLowerCase())
							|| input.contains(curmember.getcustomerID().toLowerCase())) { 
						counter = i;
						location = (curmember.getCity() + " ");
						Currentindex = counter;
						theRecentThing.clear();
						classtype = theCustomerList;
						theRecentThing.add(classtype.get(Currentindex));
						i = thelist.size() + 1; // force break
					}
				}
			}
			if (thelist == theAmenityList) {
				int counter = 0;
				for (int i = 0; i < thelist.size(); i++) {
					Amenity curcatalog = (Amenity) thelist.get(i);
					if (input.contains(curcatalog.getName().toLowerCase())) { 
						counter = i;
						location = (curcatalog.getLocation() + " ");
						Currentindex = counter;
						theRecentThing.clear();
						classtype = theAmenityList;
						theRecentThing.add(classtype.get(Currentindex));
						i = thelist.size() + 1; // force break
					}
				}
			}
			
			
			// Print Hotel Address - Start
			if (thelist == theHotelList) {
				location = (" at" + "<br>" + reception.getHousenumber() +""+ reception.getStreet() + reception.getCity() + "" +reception
						.getPostcode() + "<br>" + reception.getTel());
			}
			// Print Hotel Address - End
			
			
		
		URL = "https://soc.uwl.ac.uk/~21240951/Hotel_Reception/" + classtype.get(0).getClass().getSimpleName().toLowerCase() + ".html";
		Console.Println("URL = " + URL);
		tooltipstring = readwebsite(URL);
		String html = "<html>" + tooltipstring + "</html>";
		Myface.setmytooltip(html);
		Myface.setmyinfobox(URL);

		return location;
	}
	// Method to retrieve location information - End
	
	
	
	// Validation - Number of Rooms - Start
	public String validateRoom() { 
		String answer = "";
		Console.Println("Room List = " + theRoomList.size() + " Items");
		for (int i = 0; i < theRoomList.size(); i++) {
			Room curbook = (Room) theRoomList.get(i); 
		}
		return answer;
	}
	// Validation - Number of Rooms - End
	
	
	// Validation - Number of Customers - Start
	public String validateCustomer() { 
		String answer = "";
		Console.Println("Customer List = " + theCustomerList.size() + " Items");
		for (int i = 0; i < theCustomerList.size(); i++) {
			Customer curbook = (Customer) theCustomerList.get(i); 
		}
		return answer;
	}
	// Validation - Number of Customers - End
	
	
	// Validation - Number of Bookings - Start
	public String validateBookings() { 
		String answer = "";
		Console.Println("Booking List = " + theBookingList.size() + " Items");
		for (int i = 0; i < theBookingList.size(); i++) {
			Booking curbook = (Booking) theBookingList.get(i); 
		}
		return answer;
	}
	// Validation - Number of Bookings - End
	
	
	// Validation - Number of Amenities - Start
	public String validateAmenities() { 
		String answer = "";
		Console.Println("Amenity List = " + theAmenityList.size() + " Items");
		for (int i = 0; i < theAmenityList.size(); i++) {
			Amenity curbook = (Amenity) theAmenityList.get(i); 
		}
		return answer;
	}
	// Validation - Number of Amenities - End
	
	
	// Reading the web sites - Start
	public static String readwebsite(String url) {
		String webtext = "";
		try {
			BufferedReader readit = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
			String lineread = readit.readLine();
			Console.Println("<font color=#2F4F4F  face = Roman  size = 5>Reader okay</font>");
			while (lineread != null) {
				webtext = webtext + lineread;
				lineread = readit.readLine();
			}
			// Check if website still has this structure
			webtext = webtext.substring(webtext.indexOf("<ul>"), webtext.indexOf("</ul>"));
			webtext = "<table width=\"700\"><tr><td>" + webtext + "</ul></td></tr></table>";

		} catch (Exception e) {
			webtext = "<font color=#2F4F4F  face = Roman  size = 5>Not yet</font>";
			Console.Println("<font color=#2F4F4F  face = Roman  size = 5>Error connecting to wordnet</font>");
		}
		return webtext;
	}
	// Reading the web sites - End

}
// Main Class - End