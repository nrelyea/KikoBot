import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;

public class Play implements Runnable 
{ 
    public void run() 
    { 
        try
        { 
    		int WIDTH = 4;
    		int HEIGHT = 4;
    		

    		
    		Point CENTER = new Point(690,500); //CENTER OF SCREEN IF CHROME ZOOM IS 175% AND TOP OF GAME WINDOW TOUCHING BOOKMARKS BAR
    		int SIZE = 820; // size of play area
    		
    		Point[][] coordMatrix = generateCoordMatrix(CENTER, SIZE, 4, 4); 
            
  
        } 
        catch (Exception e) 
        { 
            // Throwing an exception 
            System.out.println ("Play terminated"); 
        } 
    } 
    
    static Point[][] generateCoordMatrix(Point center, int boardSize, int rows, int columns) throws AWTException, InterruptedException {

		Point[][] coords = new Point[columns][rows];
		int cardSize = boardSize / columns;
		Point firstCardPos = new Point(center.x - (boardSize / 2) + (cardSize / 2), center.y - ((cardSize * rows) / 2) + (cardSize / 2));
		
		
		for(int i = 0; i < columns; i++) {
			for(int j = 0; j < rows; j++) {
				coords[i][j] = new Point(firstCardPos.x + (i * cardSize),firstCardPos.y + (j * cardSize));
				
				Robot rob = new Robot();
				rob.mouseMove(coords[i][j].x, coords[i][j].y);
				Thread.sleep(500);
				
			}
		}
				
		return null;
	}
} 