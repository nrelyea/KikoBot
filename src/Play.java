import java.awt.AWTException;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;

public class Play implements Runnable 
{ 
    public void run() 
    { 
        try
        { 
		
    		Point CENTER = new Point(690,510); //CENTER OF SCREEN IF CHROME ZOOM IS 175% AND TOP OF GAME WINDOW TOUCHING BOOKMARKS BAR
    		int BOARDSIZE = 820; // size of play area
    		
    		
    		
			Robot rob = new Robot();
			click(rob,CENTER);
			Thread.sleep(300);
			

    		
			
    		int WIDTH = 3;
    		int HEIGHT = 3;
			
    		Point[][] coordMatrix = generateCoordMatrix(CENTER, BOARDSIZE, HEIGHT, WIDTH); 
    		
    		
    		play(rob, coordMatrix, HEIGHT, WIDTH, BOARDSIZE / WIDTH);
    		
    		
			// re-focus on console
			rob.mouseMove(-1000, 925);
			rob.mousePress(InputEvent.BUTTON1_MASK);
			rob.mouseRelease(InputEvent.BUTTON1_MASK);
            
  
        } 
        catch (Exception e) { System.out.println ("Play terminated"); } 
    } 
    
    void play(Robot rob, Point[][] coords, int h, int w, int cardSize) throws InterruptedException {
    	
		Hashtable<String, Point> matchTable = new Hashtable<String, Point>();
		
		int clickCount = 0;
		
		int clickNum = 1;
		String lastCard = "";
		
		for(int i = 0; i < w; i++) {
			for(int j = 0; j < h; j++) {
				
				if(h == w && i == j && (h%2) == 1 && (w%2) == 1 && j == w / 2) {
					System.out.println("Skipping point (" + i + "," + j + ")");
				}
				else {
					System.out.println("Click " + clickNum + ": (" + i + "," + j + ")");
					click(rob, coords[i][j]);
					Thread.sleep(275);
					String cardRead = readCard(rob, coords[i][j].x, coords[i][j].y, cardSize);
					System.out.println("Card color: " + cardRead);
					
					Point rtnPoint = matchTable.put(cardRead,new Point(coords[i][j].x,coords[i][j].y));
					clickCount++;
					if(rtnPoint != null) {					
						if(clickNum == 1) {
							System.out.println("Click 2: at (" + rtnPoint.x + "," + rtnPoint.y + ") to match (" + i + "," + j + ")");
							Thread.sleep(200);
							click(rob, rtnPoint);
							clickCount++;
						}
						else {
							if(cardRead.equals(lastCard)) {
								System.out.println("instant match!");
							}
							else {
								System.out.println("Click 1: at (" + rtnPoint.x + "," + rtnPoint.y + ") to match:");
								Thread.sleep(200);
								click(rob, rtnPoint);
								System.out.println("Click 2: at (" + i + "," + j + ")");
								Thread.sleep(200);
								click(rob, coords[i][j]);
								clickCount += 2;							
							}	
							clickNum = 1;
						}
						Thread.sleep(300);
					}
					else {
						if(clickNum == 1) {
							clickNum = 2;
						}
						else {
							clickNum = 1;
						}
					}
					lastCard = cardRead;
					Thread.sleep(300);
				}						
			}
		}
		
		System.out.println("\nTotal clicks: " + clickCount);
		
	}
    
    String readCard(Robot rob, int x, int y, int cardSize) {
    	
    	//Rectangle captureRect = new Rectangle(x - cardSize / 2, y - cardSize / 2, cardSize, cardSize);
    	Rectangle captureRect = new Rectangle(x - 10, y - cardSize / 4 - 10, 20, 20);
    	BufferedImage capture = rob.createScreenCapture(captureRect);
    	try {
			ImageIO.write(capture, "png", new File("pic.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return (new Color(capture.getRGB(10, 10))).toString();
    	
    	
	}

	static void click(Robot r, Point pt) throws InterruptedException {
    	r.mouseMove(pt.x, pt.y);
		r.mousePress(InputEvent.BUTTON1_MASK);
		r.mouseRelease(InputEvent.BUTTON1_MASK);
    }
    
    static Point[][] generateCoordMatrix(Point center, int boardSize, int rows, int columns) throws AWTException, InterruptedException {

		Point[][] coords = new Point[columns][rows];
		int cardSize = boardSize / columns;
		Point firstCardPos = new Point(center.x - (boardSize / 2) + (cardSize / 2), center.y - ((cardSize * rows) / 2) + (cardSize / 2));
		
		
		for(int i = 0; i < columns; i++) {
			for(int j = 0; j < rows; j++) {
				coords[i][j] = new Point(firstCardPos.x + (i * cardSize),firstCardPos.y + (j * cardSize));
				System.out.println(coords[i][j].toString());
				
			}
		}
				
		return coords;
	}
} 