import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;
import java.awt.Image;

public class Dynamite {

   private GamePanel gPanel;

   private int x;
   private int y;

   private int width;
   private int height;

   private int dx;		// increment to move along x-axis
   private int dy;		// increment to move along y-axis

   private Random random;

   //private Sheriff sheriff;
   private SoundManager soundManager;
   private Image dynamiteImage;

    public Dynamite(GamePanel p, int xPos, int yPos, Sheriff sheriff) {
        gPanel = p;
    
        width = 30;
        height = 30;

        random = new Random();

        x = xPos;
        y = yPos;

        setLocation();

        dx = 0;			
        dy = 5;			

        this.sheriff = sheriff;
        dynamiteImage = ImageManager.loadImage ("images/dynamite.png");
        soundManager = SoundManager.getInstance();
    }

    public int getY(){
        return y;
    }
    
    public void setLocation() {
        int panelWidth = gPanel.getWidth();
        x = random.nextInt (panelWidth - width);
        y = 10;
    }


    public void draw (Graphics2D g2) {

        g2.drawImage(dynamiteImage, x, y, width, height, null);

    }


    public void move() {
        if (!gPanel.isVisible ()) return;

        x = x + dx;
        y = y + dy;

        int height = gPanel.getHeight();
        boolean collision = collidesWithSheriff();
        
        if (collision){
            soundManager.playClip("explode", false);
            soundManager.playClip("lose life", false);
            gPanel.updateLives();
            setLocation();
        }

    }


    public Rectangle2D.Double getBoundingRectangle() {
        return new Rectangle2D.Double (x, y, width, height);
    }

   
    public boolean collidesWithSheriff() {
        Rectangle2D.Double dynamiteRect = getBoundingRectangle();
        Rectangle2D.Double sheriffRect = sheriff.getExplosionBoundingRectangle();
        
        return dynamiteRect.intersects(sheriffRect); 
    }

}