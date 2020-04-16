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

import javax.imageio.ImageIO;

public class CardReading {
	
	Hashtable <String,BufferedImage> referenceTable = new Hashtable <String,BufferedImage>();
	
	public CardReading() {
		initCardReferences();
	}
	
	private void initCardReferences() {
		try {
			//BufferedImage yellowIMG = ImageIO.read(new File("strawberry.jpg"));
			referenceTable.put("yellow", ImageIO.read(new File("yellow.png")));
			referenceTable.put("darkblue", ImageIO.read(new File("darkblue.png")));
			

			
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	public String profile(Robot rob, int x, int y, int cardSize) {
		
		int captureWIDTH = cardSize * 2 / 5;
		int captureHEIGHT = cardSize / 4 + cardSize * 2 / 5;
		
		Rectangle captureRect = new Rectangle(x, y - cardSize / 4, captureWIDTH, captureHEIGHT);
    	BufferedImage capture = rob.createScreenCapture(captureRect);
    	
    	//imagesMatch(capture,referenceTable.get("yellow"));
    	
//    	try {
//			ImageIO.write(capture, "png", new File(x + "-" + y + "-pic.png"));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    	
    	if(imagesMatch(capture,referenceTable.get("yellow"))) {
    		return("yellow");
    	}
    	else if(imagesMatch(capture,referenceTable.get("darkblue"))) {
    		return("darkblue");
    	}
    	return("NO_MATCH");
    	
    	
    	
    	//return (new Color(capture.getRGB(0,0))).toString();
		
		
		
		
		
		
		
		
		
		
		
		
		
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
			
		}
		
		if(testsPassed > 2) {
			return true;
		}
		else {
			return false;
		}
		
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
