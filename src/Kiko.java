import java.awt.AWTException;
import java.awt.Color;
import java.awt.Point;
import java.awt.Robot;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Random;

public class Kiko {

	public static void main(String[] args) throws AWTException, InterruptedException{
		
		int WIDTH = 4;
		int HEIGHT = 4;
		
		//int[][] cardMatrix = fillCards(WIDTH,HEIGHT);
		//printMatrix(cardMatrix);
		//System.out.println("");
		
		//play(cardMatrix, WIDTH, HEIGHT);
		
		Point CENTER = new Point(690,500); //CENTER OF SCREEN IF CHROME ZOOM IS 175% AND TOP OF GAME WINDOW TOUCHING BOOKMARKS BAR
		int SIZE = 820; // size of play area
		
		
		Point[][] coordMatrix = generateCoordMatrix(CENTER, SIZE, 5, 5);
		
		
		
		
		System.out.println("COMPLETE");
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
		
		
		
		
		//Robot rob = new Robot();
		//rob.mouseMove(firstCardPos.x, firstCardPos.y);
		
		
		
		return null;
	}

	static void play(int[][] cardMatrix, int w, int h) {
		Hashtable<Integer, Point> matchTable = new Hashtable<Integer, Point>();
		
		int clickCount = 0;
		
		int clickNum = 1;
		int lastCard = -1;
		
		for(int i = 0; i < w; i++) {
			for(int j = 0; j < h; j++) {
				System.out.println("Click " + clickNum + ": (" + i + "," + j + ")");
				int cardRead = cardMatrix[i][j];
				Point rtnPoint = matchTable.put(cardRead,new Point(i,j));
				clickCount++;
				if(rtnPoint != null) {					
					if(clickNum == 1) {
						System.out.println("Click 2: at (" + rtnPoint.x + "," + rtnPoint.y + ") to match (" + i + "," + j + ")");
						clickCount++;
					}
					else {
						if(cardRead == lastCard) {
							System.out.println("instant match!");
						}
						else {
							System.out.println("Click 1: at (" + rtnPoint.x + "," + rtnPoint.y + ") to match:");
							System.out.println("Click 2: at (" + i + "," + j + ")");
							clickCount += 2;							
						}	
						clickNum = 1;
					}
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
			}
		}
		
		System.out.println("\nTotal clicks: " + clickCount);
		
	}

	static int[][] fillCards(int w, int h) {
		
		int[][] cards = new int[w][h];
		for(int[] row: cards) {
			Arrays.fill(row, -1);
		}
		
		int matchCount = (w * h) / 2;
		
		for(int i = 0; i < matchCount; i++) {

			for(int j = 0; j < 2; j++) {
				while(true) {
					Random rand = new Random();
					int chosenX = rand.nextInt(w);
					int chosenY = rand.nextInt(h);
					if(cards[chosenX][chosenY] == -1) {
						cards[chosenX][chosenY] = i;
						break;
					}
				}
			}			
		}
		
		return cards;
	}
	
	static void printMatrix(int[][] matrix) {
		for(int[] row: matrix) {
			System.out.println(Arrays.toString(row));
		}
	}
	
	

}
