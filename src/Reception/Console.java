package Reception;

import java.awt.Component;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import Reception.MainReception;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Console {

	// Method For Confirm Dialog - Start
	public static void Confirmation(String message)
		{
	    Component Confirmation = null;
	    final ImageIcon icon = new ImageIcon("Resources//icon.png");
	    JOptionPane.showMessageDialog(Confirmation, message, "Hotel Reception",JOptionPane.QUESTION_MESSAGE, icon);
		}
		
	// Method For Confirm Dialog - End
	
	
	
	// Method For Print Feature - Start
	public static void Print(String msg)
		{
			System.out.print(msg);
		}
	// Method For Print Feature - End	
	
	
	
	// Method For Print Feature - Start
	public static void Println(String msg)
		{
			System.out.println(msg);
		}
	// Method For Print Feature - End	

	
	
	// Method For Save Feature - Start
	public static void saveToFile() 
	{
	String desktop = System.getProperty ("user.home") + "//Desktop//";
	File filepath = new File (desktop + "Reception-Output.html");
	
	FileWriter out = null;
	try {
		out = new FileWriter(filepath);
	} catch (IOException e1) {
		e1.printStackTrace();
	}
    try {
		out.write(MainReception.Output.getText());
	} catch (IOException e1) {
		e1.printStackTrace();
	}
    try {
		out.close();
	} catch (IOException e1) {
		e1.printStackTrace();
	}
	}
	// Method For Save Feature - End

	
	
	// Method For Menu Help -> About - Start
	public static void about() 
	{
    final ImageIcon icon = new ImageIcon("Resources//icon.png");
    JOptionPane.showMessageDialog(null,
    "Hotel Reception" + "\n" +
    "Knowledge Based Systems" + "\n" +
    "University of West London" + "\n" +
    "\nMehdi Amerinia" +
    "\nMoafaq Jamal Ashshareef" +
    "\nKrishnadas charankatbaiju" +
    "\nSoheil Emadi" + "\n",
    "About",
    JOptionPane.QUESTION_MESSAGE, icon);
	}
	// Method For Menu Help -> About - End	
	
}


