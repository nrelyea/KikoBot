import java.util.Scanner;

public class KillSwitch implements Runnable 
{ 
    public void run() 
    { 
        try
        { 
        	Scanner myObj = new Scanner(System.in);  // Create a Scanner object

            //String str = myObj.nextLine();  // Read user input
            for(int i = 0; i < 10; i++) {
            	System.out.println(i);
            	Thread.sleep(500);
            }
            
  
        } 
        catch (Exception e) 
        { 
            // Throwing an exception 
            System.out.println ("Exception is caught"); 
        } 
    } 
} 