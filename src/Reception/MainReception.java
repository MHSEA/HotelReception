package Reception;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import java.net.URL;

import javax.swing.text.html.HTMLEditorKit;

import sun.security.action.GetBooleanSecurityPropertyAction;

public class MainReception {
	
	public static Vector<String> dialoghistory = new Vector<String>();
	
	public static JTextField Input;
	public static JEditorPane Output,Info;
	public static JScrollPane Scroll, ScrollInfo;
	public JPanel Inframe, Outframe, Buttonframe;
	public JLabel Inputlabel;
	public String dialogout = "";
	public String displaytext = "";
	public static String question = "";
	public static String answer = "";
	

	Reasoner myReasoner;
	
	public MainReception() {               // Constructor for an Instance of SimpleGUI
		
		myReasoner = new Reasoner(this);         // Instantiate a "brain", reference this GUI to it										                      		
		myReasoner.initknowledge();              // fill "the brain" with knowledge
		
		Input = new JTextField(80);	
		Inputlabel = new JLabel("Question");
		Input.setBorder(null);
		
		
		Output = new JEditorPane("text/html","<b>Initial text</b>");
		Output.setEditable(false);                 // no one should be able to write in the display	
		Output.setToolTipText("<html>Your dialog with the machine.</html>");
		
		Scroll = new JScrollPane(Output);          									                
		Scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		Scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		Scroll.setBorder(BorderFactory.createTitledBorder("Conversation:"));
		Scroll.getViewport().setPreferredSize(new Dimension(500,600));
		Scroll.getViewport().setOpaque(false);
		Scroll.setBorder(null);
		
		Info = new JEditorPane("text/html","html string");	    
		Info.setEditable(false);
		Info.setEditorKit(new HTMLEditorKit());
		Info.setText("<font face=\"Verdana\">Background information about the conversations topic will be displayed in this window.");
		
		
	    ScrollInfo = new JScrollPane(Info);   									                
		ScrollInfo.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		ScrollInfo.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		ScrollInfo.setBorder(BorderFactory.createTitledBorder("Extended Information:"));
	    ScrollInfo.getViewport().setPreferredSize(new Dimension(500,600));
	    ScrollInfo.getViewport().setOpaque(false);	
	    ScrollInfo.setBorder(null);
		
	    Inframe = new JPanel();                             // Frame for the Inputelements
		Outframe = new JPanel();                            // Frame for the Outputelements
					
		Inframe.add(Inputlabel);                            // adding the elements to the JPanels
		Inframe.add(Input);
		
		Outframe.add(Scroll);
		Outframe.add(ScrollInfo);		

		// Main Frame
		JFrame Main = new JFrame("Hotel Reception");   
		
		

		      //create a menu bar
		      final JMenuBar menuBar = new JMenuBar();

		      // Create Menu - Start
		      JMenu fileMenu = new JMenu("File"); 
		      JMenu helpMenu = new JMenu("Help");
		      JMenu linkMenu = new JMenu("Link");
		      // Create Menu - End
		      
		      
		      
		      // Create Menu Items - Start
		      JMenuItem saveMenuItem = new JMenuItem("Save");
		      saveMenuItem.setActionCommand("Save");

		      JMenuItem exitMenuItem = new JMenuItem("Exit");
		      exitMenuItem.setActionCommand("Exit");

		      JMenuItem aboutMenuItem = new JMenuItem("About");
		      aboutMenuItem.setActionCommand("About");
		      
		      JMenuItem githubMenuItem = new JMenuItem("GitHub");
		      githubMenuItem.setActionCommand("GitHub");
		      // Create Menu Items - End
		      
		      
		      
		    // Menu Item Action Listeners - Start

		      
		      saveMenuItem.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					String desktop = System.getProperty ("user.home") + "\\Desktop\\";
					Console.saveToFile();
					Console.Confirmation("Outputhas been successfully saved to:\n" + desktop + "Reception-Output.html");					
					
				}
			});
		      
		      
		      // Menu Bar - Help -> About - Start
		      aboutMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Console.about();
				}
		      });
		      // Menu Bar - Help -> About - End
		      

		      
		      
		      // Menu Bar - File -> Exit - Start
		      exitMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
					
				}
			});
		      // Menu Bar - File -> Exit - End
		      
		      
		      
		      // Menu Bat - Links -> GitHub - Start
		      githubMenuItem.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
					    Desktop.getDesktop().browse(new URL("https://github.com/MHSEA/HotelReception").toURI());
					} catch (Exception e1) {}
					
				}
			});
		      // Menu Bat - Links -> GitHub - Start
		      
		      

		      // Menu Item Action Listeners - End
		     

		      // Add Menu Items to File Menu - Start
		      fileMenu.add(saveMenuItem);
		      fileMenu.addSeparator();
		      fileMenu.add(exitMenuItem);
		      // Add Menu Items to File Menu - End
		      
		      
		      
		      // Add Menu Items to Link Menu - Start
		      linkMenu.add(githubMenuItem);
		      // Add Menu Items to Link Menu - End
		      
		      
		      
		      // Add Menu Items to Help Menu - Start
		      helpMenu.add(aboutMenuItem);
		      // Add Menu Items to Help Menu - End		      
		      

		      
		      // Add Menus to Menu Bar - Start
		      menuBar.add(fileMenu);     
		      menuBar.add(linkMenu);
		      menuBar.add(helpMenu);  
		      // Add Menus to Menu Bar - End
   
		   

	      
		try {
			
			// Setting GUI Background Image
    		Main.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("Resources//BG.jpg")))));
    		// Setting GUI Window Icon 
    		Main.setIconImage(ImageIO.read(new File("Resources//Icon.png")));
    		// Initial Menu Bar to Main Frame
    		Main.setJMenuBar(menuBar);
		    

    	} catch (IOException e) {
    		e.printStackTrace();
    	}
		Main.getContentPane().setLayout(new FlowLayout());
		Main.getContentPane().add(Outframe);
		Main.getContentPane().add(Inframe);
		
				
		Main.addWindowListener(new WindowAdapter() {        // implementing listeners we need
			public void windowClosing(WindowEvent e) {      // What to do if Window is closed
				System.exit(0);                             // Kill program
			}
		});
				
		Input.addActionListener(new ActionListener() {      // Action if "Enter" is pressed
			public void actionPerformed(ActionEvent e) {    // while "Input" has the Focus
				questionasked();
				Input.setText("");
			}
		});

		Main.pack();
		Main.setSize(1300, 800);
		Main.setVisible(true);                               // Don't forget
		
		dialoghistory.add("<H2><font face=\"Verdana\">Welcome to the Hotel Reception Helpdesk, please type your question.</H2> " +
				          "<H3><font face=\"Verdana\">Following services are available: Available Rooms, Bookings, Checkin and Checkouts, " +
					      "Just ask me.</H3><br>" +
				          "<H3><font face=\"Verdana\">To Start, you can type help to explore more.</H3><br>");
		Output.setText(dialoghistory.firstElement());
		Input.requestFocusInWindow();
	}                                                        // Constructor done

	// Validating the reasoner's Database - Start
	public void checkbrain() {
		String validateCustomer = myReasoner.validateCustomer();           
		Console.Print(validateCustomer);                    
		
		String validateroom = myReasoner.validateRoom();       
		Console.Print(validateroom);
		
		String validatebookings = myReasoner.validateBookings();   
		Console.Print(validatebookings);
		
		String validateAmenities = myReasoner.validateAmenities();  
		Console.Print(validateAmenities);
		
	}
	// Validating the reasoner's Database - End
	public void questionasked() {                            // log questions in a String vector
		question = Input.getText();
		generateanswer();
	}

	public void generateanswer() {                           // generate an answer

		Vector<String> answers = new Vector<String>();
		answers = myReasoner.generateAnswer(question);
	
		displaytext = "<font color=\"red\" face=\"Verdana\">You: " + answers.get(0)+
				       "</font>"+"<br><font COLOR=\"green\" face=\"Verdana\">" +
				       answers.get(1) + "</font><br>" + "<br>";
		
		dialoghistory.add(displaytext);                      // write the string to show in the
										                     // dialoghistory
	
		String dialogdisplay = "";
		
		for(int i=0; i<dialoghistory.size(); i++){
			dialogdisplay=dialogdisplay+dialoghistory.get(i);
		}
		
		Output.setText(dialogdisplay);                       // write the dialoghistory to output
		displaytext = "";                                    // clear the "long string" for the next answer
		
	}

	public void setmytooltip(String inputstring) {

		Info.setToolTipText(inputstring);
		System.out.println("Done setting the Tooltiptext");

	}
	
	public void setmyinfobox(String inputstring) {

		 try {
		       Info.setPage(inputstring);
		     }
		    catch (IOException e) {
		       Info.setContentType("text/html");
		       Info.setText("<html>Could not load"+inputstring);
		     } 
		System.out.println("Done setting the Tooltiptext");

	}

	public static void main(String[] args) {                 // main Method (starts when class/instance is called)
		MainReception mygui = new MainReception();
		mygui.checkbrain();                                  // check if brain is there and knowledge loaded
		
	}
}
