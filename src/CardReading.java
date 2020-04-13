import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;

public class CardReading {
	
	public CardReading() {
		
	}
	
	public String profile(Robot rob, int x, int y, int cardSize) {
		
		Rectangle captureRect = new Rectangle(x, y - cardSize / 4, 20, 20);
    	BufferedImage capture = rob.createScreenCapture(captureRect);
//    	try {
//			ImageIO.write(capture, "png", new File(x + "-" + y + "-pic.png"));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    	return (new Color(capture.getRGB(0,0))).toString();
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
}
