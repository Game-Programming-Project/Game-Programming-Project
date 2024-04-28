import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.HashSet;

public class Chest {
    private Image chestClosedImage, chestOpenImage;

    private int closedWidth, closedHeight;
    private int openWidth, openHeight;
    private int x, y;
    private int mapX, mapY;
    private GamePanel gPanel;
    private Background bg;
    private SoundManager soundManager;
    private boolean isOpen, visible;

    public Chest(GamePanel gPanel, int x, int y, Background bg) {
        this.x = x;
        this.y = y;
        this.gPanel = gPanel;
        this.bg = bg;

        int bgX = bg.getbg1X();
        int bgY = bg.getbg1Y();

        if (bgX < 0)
            bgX *= -1;
        if (bgY < 0)
            bgY *= -1;

        mapX = x + bgX;
        mapY = y + bgY;

        //closed image is 16x21
        closedWidth = 40;
        closedHeight = 53;

        //open image is 16x28
        openWidth = 40;
        openHeight = 70;

        soundManager = SoundManager.getInstance();

        chestClosedImage = ImageManager.loadImage("images/Chest/closedChest.png");
        chestOpenImage = ImageManager.loadImage("images/Chest/openChest.png");

        isOpen = false;
        visible = true;
    }

    public void draw(Graphics2D g2) {

        if(!visible)
            return;

        updateScreenCoordinates();

        if (!isOpen) 
            g2.drawImage(chestClosedImage, x, y, closedWidth, closedHeight, null);
        else 
            g2.drawImage(chestOpenImage, x, y, openWidth, openHeight, null);
        
    }

    public void updateScreenCoordinates() { 
        int bgX = bg.getbg1X();
        int bgY = bg.getbg1Y();

        if (bgX < 0)
            bgX *= -1;
        if (bgY < 0)
            bgY *= -1;

        x = mapX - bgX;
        y = mapY - bgY;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setVisible(boolean v) {
        visible = v;
    }

    public boolean isVisible() {
        return visible;
    }

    public Rectangle2D.Double getBoundingRectangle() {
        return new Rectangle2D.Double(x, y, closedWidth, closedHeight);
    }

    public boolean collidesWithPlayer(Player p) {
        Rectangle2D.Double myRect = getBoundingRectangle();
        Rectangle2D.Double playerRect = p.getBoundingRectangle();

        return myRect.intersects(playerRect);
    }
}
