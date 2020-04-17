import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;

public class CardReading {
	
	Hashtable <String,BufferedImage> referenceTable = new Hashtable <String,BufferedImage>();
	
	public CardReading() {
		initCardReferences();
	}
	
	private void initCardReferences() {
		try {
			
			referenceTable.put("yellow", ImageIO.read(new File("yellow.png")));
			referenceTable.put("darkblue", ImageIO.read(new File("darkblue.png")));
			referenceTable.put("lightblue", ImageIO.read(new File("lightblue.png")));
			referenceTable.put("red", ImageIO.read(new File("red.png")));
			referenceTable.put("green", ImageIO.read(new File("green.png")));
			referenceTable.put("blue", ImageIO.read(new File("blue.png")));
			referenceTable.put("pink", ImageIO.read(new File("pink.png")));			// SOMETIMES LIGHTBLUE DETECTED AS PINK
			referenceTable.put("orange", ImageIO.read(new File("orange.png")));
			referenceTable.put("white", ImageIO.read(new File("white.png")));
			referenceTable.put("mixed", ImageIO.read(new File("mixed.png")));		// NOT REGISTERING WELL





			
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	public String profile(Robot rob, int x, int y, int cardSize) {
		
		int captureWIDTH = cardSize * 2 / 5;
		int captureHEIGHT = cardSize / 4 + cardSize * 2 / 5;
		
		Rectangle captureRect = new Rectangle(x, y - cardSize / 4, captureWIDTH, captureHEIGHT);
    	BufferedImage capture = rob.createScreenCapture(captureRect);
    	
    	
    	
    	String cardType = "";
    	// for each image in the reference table, compare the capture with it, and return it's key (the color) if it matches
    	Set<String> keys = referenceTable.keySet();
    	for(String colorKey: keys){
        	if(imagesMatch(capture,referenceTable.get(colorKey))) {
        		cardType = colorKey;
        		break;
        	}
        }
    	// otherwise if no color was matched
    	if(cardType.length() == 0) {
    		cardType = "NOMATCH";
    	}
    	
    	

    	// background color references
    	
    	Color blue = new Color(0,102,204);
    	Color red = new Color(255,0,0);
    	Color yellow = new Color(255,255,0);

    	
    	// CODE TO CHECK BACKGROUND
    	String cardBack = "";
    	
    	Color backColor = new Color(capture.getRGB(capture.getWidth() - 3,capture.getHeight() - 3));
    	System.out.println("Background color: " + backColor.getRed() + "," + backColor.getGreen() + "," + backColor.getBlue());
    	if(colorsSimilar(backColor,blue)) {
        	cardBack = "blue";
    	}
    	else if(colorsSimilar(backColor,red)) {
        	cardBack = "red";
    	}
    	else if(colorsSimilar(backColor,yellow)) {
        	cardBack = "yellow";
    	}
    	
    	
    	
    	
    	try {
			ImageIO.write(capture, "png", new File("zMatched-" + cardType + "-" + cardBack + "-" + x + "-" + y + ".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	
    	return cardType + "_" + cardBack;
		
		
	}
	
	boolean imagesMatch(BufferedImage captureIMG, BufferedImage compareIMG) {
		
		List<Point2D.Double> colorTests = new ArrayList();
		
		colorTests.add(new Point2D.Double(0.0, 0.25));	// first test
		colorTests.add(new Point2D.Double(0.0, 0.75));	// second test
		colorTests.add(new Point2D.Double(0.5, 0.25));	// third test
		colorTests.add(new Point2D.Double(0.5, 0.75));	// fourth test

		int testsPassed = 0;
		
		for(int testNum = 0; testNum < colorTests.size(); testNum ++) {
			
			Point2D.Double currentTest = colorTests.get(testNum);
			
			Color captureColor = new Color(captureIMG.getRGB(product(captureIMG.getWidth(),currentTest.x),product(captureIMG.getHeight(),currentTest.y)));
			Color compareColor = new Color(compareIMG.getRGB(product(compareIMG.getWidth(),currentTest.x),product(compareIMG.getHeight(),currentTest.y)));

			if(colorsSimilar(captureColor,compareColor)) {
				System.out.println("Test " + testNum + " passed");
				testsPassed++;
			}
			else {
				System.out.println("Test " + testNum + " failed");
			}
			
			if(testsPassed > 1) {
				return true;
			}
			
		}
		
		// if loop is exited without enough tests passed, return false
		return false;	
		
	}
	
	boolean colorsSimilar(Color test, Color base){
	    double distance = (test.getRed() - test.getRed())*(test.getRed() - test.getRed()) + 
	    				  (test.getGreen() - base.getGreen())*(test.getGreen() - base.getGreen()) + 
	    				  (test.getBlue() - base.getBlue())*(test.getBlue() - base.getBlue());
	    if(distance < 100){
	        return true;
	    }else{
	        return false;
	    }
	}
	
	// computes product of int and double
	int product(int num, double d) {
		return (int)((double)num * d);
	}
	
}
