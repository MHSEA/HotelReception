package Reception;

import java.io.*;
import java.net.URL;
import java.util.*;
import Reception.*;

public class Reasoner {
// Main Class - Start

	public Hotel reception;
	public MainReception Myface;

	
	// The lists holding the class instances of all domain entities - Start
	public List receptionList = new ArrayList();
	public List theRoomList = new ArrayList();
	public List theCustomerList = new ArrayList();
	public List theAmenityList = new ArrayList();
	public List theBookingList = new ArrayList();
	public List theRecentThing = new ArrayList();
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
	public String URL = "";  // URL for Wordnet site
	public String URL2 = ""; // URL for Wikipedia entry

	
	// Constructor - Start
	public Reasoner(MainReception myface) {
		Myface = myface; // reference to GUI to update Tooltip-Text
	}
	// Constructor - End
	
	
	// Load library database from Library.XML File - Start
	public void initknowledge() 
		{
			JAXB_XMLParser xmlhandler = new JAXB_XMLParser();
			// Loading XML file
			File xmlfiletoload = new File("Library.xml"); 

		// Synonyms - Start
			
			// Add synonyms for Hotel - Start
			HotelSyn.add("room");
			HotelSyn.add("place");
			HotelSyn.add("hotel");
			HotelSyn.add("hotels");
			HotelSyn.add("help");
			HotelSyn.add("contact"); 
			HotelSyn.add("number");
			// Add synonyms for Hotel - End
			
			
			// Add synonyms for Room - Start
			RoomSyn.add("Room");
			RoomSyn.add("Single"); 
			RoomSyn.add("Double");
			RoomSyn.add("Twin");
			RoomSyn.add("Triple");
			RoomSyn.add("Quads");
			RoomSyn.add("Suites");
			// Add synonyms for Room - End

			
			// Add synonyms for Customer - Start		
			CustomerSyn.add("Amenity");
			CustomerSyn.add("services");
			CustomerSyn.add("service");
			CustomerSyn.add("dining");
			CustomerSyn.add("pool");
			CustomerSyn.add("excersise");
			// Add synonyms for Customer - End
			
			
			// Add synonyms for Amenity - Start
			AmenitySyn.add("pool");
			AmenitySyn.add("disco");
			AmenitySyn.add("inventor");
			// Add synonyms for Amenity - End
			
		
			// Add synonyms for Booking - Start
			BookingSyn.add(" lending");
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
				theCustomerList = reception.getCustomer();
				theAmenityList = reception.getAmenity();
				theBookingList = reception.getBooking();
				// Force it to be a List,
				receptionList.add(reception);
				System.out.println("List reading");
			}

		catch (Exception e) 
			{
				e.printStackTrace();
				System.out.println("error in init");
			}
	}
	// Load library database from Library.XML File - End
	
	
	
	// Generate an Answer - Start
	public Vector<String> generateAnswer(String input)
	{
		Vector<String> out = new Vector<String>();
		out.clear();

		questiontype = "none";
		
		// Check if answer was generated
		Integer Answered = 0; 
		Integer subjectcounter = 0; 
		
		// Convert input to lower case
		input = input.toLowerCase(); 
		String answer = ""; 

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
			System.out.println("Find Location");
		}
		// Question Type Validation - End
		
		
		
		// Commands - [Can i] - Start
		if (input.contains("can i book") 
				|| input.contains("can i reserve") 
				|| input.contains("can i book a")
				|| input.contains("am i able to") 
				|| input.contains("could i book") 
				|| input.contains("i want to book"))
		{
			questiontype = "intent";
			System.out.println("Find BookAvailability");
		}
		// Commands - [Can i] - End


		
		// Commands - [Thank you] - Start
		if (input.contains("thank you") 
				|| input.contains("bye") 
				|| input.contains("thanks")
				|| input.contains("cool thank")) 
		{
			questiontype = "farewell";
			System.out.println("farewell");
		}
		// Commands - [Thank you] - End

		
		
		// Commands - [Help] - Start
		String questionmark = "?";
		if (input.contains("Help") 
				|| input.equalsIgnoreCase(questionmark)) 
		{
			questiontype = "Help";
			System.out.println("Help");
		}
		// Commands - [Help] - End
		
		
		
		// Commands - [Exit] - Start
		if (input.contains("Exit") 
				|| input.contains("exit") 
				|| input.contains("Exit")) 
		{
			questiontype = "Exit";
			System.out.println("Exit");
		}
		// Commands - [Exit] - End

		
		
		// Commands - [CLS] - Start
		if (input.contains("cls") 
				|| input.contains("Clean") 
				|| input.contains("clean")) {
			questiontype = "CLS";
			System.out.println("CLS");
		}
		// Commands - [CLS] - End

		
		
		// Check - [Command Subject] - Start
		for (int x = 0; x < RoomSyn.size(); x++) {
			if (input.contains(RoomSyn.get(x))) {
				classtype = theRoomList;
				input = input.replace(RoomSyn.get(x), "<b>" + RoomSyn.get(x) + "</b>");
				subjectcounter = 1;
				System.out.println("Class type Room recognised.");
			}
		}
		
		for (int x = 0; x < CustomerSyn.size(); x++) {
			if (input.contains(CustomerSyn.get(x))) {
				classtype = theCustomerList;
				input = input.replace(CustomerSyn.get(x), "<b>" + CustomerSyn.get(x) + "</b>");
				subjectcounter = 1;
				System.out.println("Class type Cutomer recognised.");
			}
		}
		
		for (int x = 0; x < AmenitySyn.size(); x++) {
			if (input.contains(AmenitySyn.get(x))) {
				classtype = theAmenityList;
				input = input.replace(AmenitySyn.get(x), "<b>" + AmenitySyn.get(x) + "</b>");
				subjectcounter = 1;
				System.out.println("Class type Amenity recognised.");
			}
		}
		
		for (int x = 0; x < BookingSyn.size(); x++) {
			if (input.contains(BookingSyn.get(x))) {
				classtype = theBookingList;
				input = input.replace(BookingSyn.get(x), "<b>" + BookingSyn.get(x) + "</b>");
				subjectcounter = 1;
				System.out.println("Class type Booking recognised.");
			}
		}

		if (subjectcounter == 0) {
			for (int x = 0; x < RecentObjectSyn.size(); x++) {
				if (input.contains(RecentObjectSyn.get(x))) {
					classtype = theRecentThing;
					input = input.replace(RecentObjectSyn.get(x), "<b>" + RecentObjectSyn.get(x) + "</b>");
					subjectcounter = 1;
					System.out.println("Class type recognised as" + RecentObjectSyn.get(x));
				}
			}
		}
		// Check - [Command Subject] - [End]


	
		// Check - [Command Subject (More than one Subject)] - Start
		System.out.println("subjectcounter = " + subjectcounter);

		for (int x = 0; x < HotelSyn.size(); x++) {
			if (input.contains(HotelSyn.get(x))) {
				if (subjectcounter == 0) {
					input = input.replace(HotelSyn.get(x), "<b>" + HotelSyn.get(x) + "</b>");
					classtype = receptionList;
					System.out.println("class type Library recognised");
				}
			}
		}
		// Check - [Command Subject (More than one Subject)] - End



		// Response - [Number of Rooms] - Start
		if (questiontype == "amount") 
		{
			Integer numberof = Count(classtype);
			answer = ("There are " + numberof + " Rooms Available");
			Answered = 1;
		}
		// Response - [Number of Rooms] - End

		
		
		// Response - [List of Rooms] - Start
		if (questiontype == "list") {
			answer = ("You asked for the listing of all " + classtype.get(0).getClass().getSimpleName() + "s. <br>"
					+ "We have the following " + classtype.get(0).getClass().getSimpleName() + "s:"
					+ ListAll(classtype));
			Answered = 1;
		}
		// Response - [List of Rooms] - End
		
		
		
		// Response - [Check] - Start
		if (questiontype == "checkfor") { // test for a certain Subject instance

			Vector<String> check = CheckFor(classtype, input);
			answer = (check.get(0));
			Answered = 1; // An answer was given
			if (check.size() > 1) {
				Currentitemofinterest = classtype.get(Integer.valueOf(check.get(1)));
				System.out.println("Classtype List = " + classtype.getClass().getSimpleName());
				System.out.println("Index in Liste = " + Integer.valueOf(check.get(1)));
				Currentindex = Integer.valueOf(check.get(1));
				theRecentThing.clear(); // Clear it before adding (changing) the
				// now recent thing
				theRecentThing.add(classtype.get(Currentindex));
			}
		}
		// Response - [Check] - End
		
		
		
		// Location Question - [Start]
		if (questiontype == "location") { 
			answer = ("You can find the " + classtype.get(0).getClass().getSimpleName() + " " + "at "
					+ Location(classtype, input));
			Answered = 1;
		}
		if ((questiontype == "intent" && classtype == theRoomList)
				|| (questiontype == "intent" && classtype == theRecentThing)) {

			// Can I Book the Room or Not (Book it or Not)
			answer = ("Reception: " + RoomAvailable(classtype, input));
			Answered = 1;
		}
		// Location Question - [End]
		
		
		
		// Response - [Thank You] - Start
		if (questiontype == "farewell") {
			String name = System.getenv("USERNAME");
			answer = ("You are very welcome" + name);
			Answered = 1;}
		// Response - [Thank You] - Start
		
		
		
		// Response - [Help] - Start
		if (questiontype == "Help") {
			answer = "<br>" + "You can use following commands:  " + "<br>" + "---------------------------------------"
					+ "<br>" + "Exit: Quit the program" + "<br>" + "CLS: Clear the screen" + "<br>" + "<br>" + "<br>"
					+ "Also you can ask following questions:  " + "<br>" + "---------------------------------------"
					+ "<br>" + "- where is the hotel" + "<br>" + "- I am looking for a double room" + "<br>"
					+ "- What kind of rooms are available" + "<br>" + "- Where are the room locations" + "<br>"
					+ "- How many rooms are available" + "<br>" + "- Can i book a room" + "<br>";
			Answered = 1;
		}

		if (questiontype == "?") {
			answer = ("You can type: ");
			Answered = 1;
			}
		// Response - [Help] - End

		
		
		// Response - [Exit] - Start
		if (questiontype == "Exit") {
			System.exit(0);}
		// Response - [Exit] - End

		
		
		// Response - [CLS] - Start
		if ((questiontype == "CLS") || (questiontype == "CLEAN")){
			Reception.MainReception.Info.setText(
			"<font face=\"Verdana\">Background information about the conversations topic will be displayed in this window.");
			Reception.MainReception.dialoghistory.removeAllElements();
			Reception.MainReception.dialoghistory.add
			("<H2><font face=\"Verdana\">Welcome to the Hotel Reception Helpdesk, please type your question.</H2> "
			+ "<H3><font face=\"Verdana\">Following services are available: Available Rooms, Bookings, Checkin and Checkouts, "
			+ "Just ask me.</H3><br>"
			+ "<H3><font face=\"Verdana\">To Start, you can type help to explore more. </H3><br>");
			Answered = 1;}
		// Response - [CLS] - End

		
		// Response - [Null] - [Start]
		if (Answered == 0) {
			answer = ("Sorry I didn't understand that." + "<br> " +
					  "You can type [ Help ] for more information and list of commands.");
			}
		// Response - [Null] - [End]
		
		
		out.add(input);
		out.add(answer);
		return out;
	}
	// Generate an Answer - [End]
	
	
	
	// Methods to generate answers for the different kinds of Questions - Start
	// Answer a question of the "Is a book or "it (meaning a book) available ?"
	// kind
	public String RoomAvailable(List thelist, String input) {
		
		boolean available = true;
		String answer = "";
		Room curbook = new Room();
		String roometype = "";

		if (thelist == theRoomList) { // This is a candidate for a name change
			int counter = 0;

			// Identify Room Type
			for (int i = 0; i < thelist.size(); i++) {
				curbook = (Room) thelist.get(i);
				if (input.contains(curbook.getTitle().toLowerCase())) { 
					counter = i;
					Currentindex = counter;
					theRecentThing.clear();
					classtype = theRoomList;
					theRecentThing.add(classtype.get(Currentindex));
					roometype = curbook.getTitle();
					if (input.contains(curbook.getTitle().toLowerCase())) {
						input = input.replace(curbook.getTitle().toLowerCase(),
								"<b>" + curbook.getTitle().toLowerCase() + "</b>");
					}
					i = thelist.size() + 1; // force break
				}
			}
		}
		
		// maybe other way round or double
		if (thelist == theRecentThing && theRecentThing.get(0) != null) {

			if (theRecentThing.get(0).getClass().getSimpleName().toLowerCase().equals("room")) { 
				curbook = (Room) theRecentThing.get(0);
				roometype = curbook.getTitle();
			}
		}

		// check all lendings if they contain the books ISBN
		for (int i = 0; i < theBookingList.size(); i++) {
			Booking curlend = (Booking) theBookingList.get(i);
			if (curbook.getisBooked().toLowerCase().equals(curlend.getbookingID().toLowerCase())) { 
				input = input.replace(curlend.getbookingID().toLowerCase(),
						"<b>" + curlend.getbookingID().toLowerCase() + "</b>");
				available = false;
				i = thelist.size() + 1; // force break
			}
		}

		if (available) {
			answer = "There are rooms available to book";
		} else {
			answer = "Sorry that type of rooms are fully booked";
		}

		URL = "http:// wordnetweb.princeton.edu/perl/webwn?o2=&o0=1&o8=1&o1=1&o7=&o5=&o9=&o6=&o3=&o4=&s="
				+ classtype.get(0).getClass().getSimpleName().toLowerCase();
		URL2 = "http:// en.wikipedia.org/wiki/" + roometype;
		System.out.println("URL = " + URL);
		tooltipstring = readwebsite(URL);
		String html = "<html>" + tooltipstring + "</html>";
		Myface.setmytooltip(html);
		Myface.setmyinfobox(URL2);

		return (answer);

	}
	// Methods to generate answers for the different kinds of Questions - End
	
	
	
	// Response Web Area - [Number of Rooms] - Start
	public Integer Count(List thelist) { 

		// URL = "http:// moafaq.com/HotelRec/room.html";
		// URL2 = "http:// moafaq.com/HotelRec/room.html";
		URL = "http:// osm.org/go/euu4KvUh--?relation=65606";
		URL2 = "http:// osm.org/go/euu4KvUh--?relation=65606";

		System.out.println("URL = " + URL);
		tooltipstring = readwebsite(URL);
		String html = "<html>" + tooltipstring + "</html>";
		Myface.setmytooltip(html);
		Myface.setmyinfobox(URL2);

		return thelist.size();
	}
	// Response Web Area - [Number of Rooms] - End

	
	
	// Response - "What kind of..." - Start
	public String ListAll(List thelist) {

		String listemall = "<ul>";
		if (thelist == theRoomList) {
			for (int i = 0; i < thelist.size(); i++) {
				Room curbook = (Room) thelist.get(i);
				listemall = listemall + "<li>" + (curbook.getTitle() + "</li>");
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

		URL = "http:// wordnetweb.princeton.edu/perl/webwn?o2=&o0=1&o8=1&o1=1&o7=&o5=&o9=&o6=&o3=&o4=&s="
				+ classtype.get(0).getClass().getSimpleName().toLowerCase();
		URL2 = "http:// en.wikipedia.org/wiki/" + classtype.get(0).getClass().getSimpleName().toLowerCase();
		System.out.println("URL = " + URL);
		tooltipstring = readwebsite(URL);
		String html = "<html>" + tooltipstring + "</html>";
		Myface.setmytooltip(html);
		Myface.setmyinfobox(URL2);
		return listemall;
	}
	// Response - "What kind of..." - End
	
	
	
	// Response - "Do you have..." - Start
	public Vector<String> CheckFor(List thelist, String input) {

		Vector<String> yesorno = new Vector<String>();
		if (classtype.isEmpty()) {
			yesorno.add("Sorry I didn't understand that." + "<br> "
					+ "You can type [ Help ] for more information and list of commands.");
		} else {
			yesorno.add("No we don't have such a " + classtype.get(0).getClass().getSimpleName());
		}

		Integer counter = 0;
		if (thelist == theRoomList) {
			for (int i = 0; i < thelist.size(); i++) {
				Room curbook = (Room) thelist.get(i);
				if (input.contains(curbook.getTitle().toLowerCase()))
						{
					counter = i;
					yesorno.set(0, "Requested Room is Available."); 
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
					yesorno.set(0, "Yes we do have such a Amenitiy");
					yesorno.add(counter.toString());
					i = thelist.size() + 1;
				}
			}
		}
		if (thelist == theAmenityList) {
			for (int i = 0; i < thelist.size(); i++) {
				Amenity curcat = (Amenity) thelist.get(i);
				if (input.contains(curcat.getName().toLowerCase())
						|| input.contains(curcat.getUrl().toLowerCase())) {
					counter = i;
					yesorno.set(0, "Yes we have such a room");
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
					yesorno.set(0, "Yes we have such a Lending");
					yesorno.add(counter.toString());
					i = thelist.size() + 1;
				}
			}
		}
		if (classtype.isEmpty()) {
			System.out.println("Not class type given.");
		} else {
			URL = "http:// wordnetweb.princeton.edu/perl/webwn?o2=&o0=1&o8=1&o1=1&o7=&o5=&o9=&o6=&o3=&o4=&s="
					+ classtype.get(0).getClass().getSimpleName().toLowerCase();
			URL2 = "http:// en.wikipedia.org/wiki/" + classtype.get(0).getClass().getSimpleName().toLowerCase();
			System.out.println("URL = " + URL);
			tooltipstring = readwebsite(URL);
			String html = "<html>" + tooltipstring + "</html>";
			Myface.setmytooltip(html);
			Myface.setmyinfobox(URL2);
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
			if (theRecentThing.get(0).getClass().getSimpleName().toLowerCase().equals("member")) {
				Customer curmem = (Customer) theRecentThing.get(0); 
				location = (curmem.getCity() + " " + curmem.getStreet() + " " + curmem.getHousenumber()); 
			}
			if (theRecentThing.get(0).getClass().getSimpleName().toLowerCase().equals("catalog")) 
				{
					Amenity curcat = (Amenity) theRecentThing.get(0);
					location = (curcat.getLocation() + " ");
				}
			if (theRecentThing.get(0).getClass().getSimpleName().toLowerCase().equals("library")) { 
			
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
					if (input.contains(curbook.getTitle().toLowerCase()))
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
					if (input.contains(curcatalog.getName().toLowerCase())
							|| input.contains(curcatalog.getUrl().toLowerCase())) { 
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

			if (thelist == receptionList) {
				location = (reception.getCity() + " " + reception.getStreet() + reception
						.getHousenumber());
			}
		
		URL = "http:// wordnetweb.princeton.edu/perl/webwn?o2=&o0=1&o8=1&o1=1&o7=&o5=&o9=&o6=&o3=&o4=&s="
				+ classtype.get(0).getClass().getSimpleName().toLowerCase();
		URL2 = "http:// en.wikipedia.org/wiki/" + classtype.get(0).getClass().getSimpleName().toLowerCase();
		System.out.println("URL = " + URL);
		tooltipstring = readwebsite(URL);
		String html = "<html>" + tooltipstring + "</html>";
		Myface.setmytooltip(html);
		Myface.setmyinfobox(URL2);

		return location;
	}
	// Method to retrieve location information - End
	
	
	
	// Validation - Number of Rooms - Start
	public String testit() { 
		String answer = "";
		System.out.println("room List = " + theRoomList.size());
		for (int i = 0; i < theRoomList.size(); i++) {
			Room curbook = (Room) theRoomList.get(i); 
		}
		return answer;
	}
	// Validation - Number of Rooms - End
	
	
	
	// Reading the web sites - Start
	public String readwebsite(String url) {
		String webtext = "";
		try {
			BufferedReader readit = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
			String lineread = readit.readLine();
			System.out.println("Reader okay");
			while (lineread != null) {
				webtext = webtext + lineread;
				lineread = readit.readLine();
			}
			// Check if website still has this structure
			webtext = webtext.substring(webtext.indexOf("<ul>"), webtext.indexOf("</ul>"));
			webtext = "<table width=\"700\"><tr><td>" + webtext + "</ul></td></tr></table>";

		} catch (Exception e) {
			webtext = "Not yet";
			System.out.println("Error connecting to wordnet");
		}
		return webtext;
	}
	// Reading the web sites - End
	
	
}
// Main Class - End