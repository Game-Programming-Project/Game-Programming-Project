import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class Rock {
    private Image rockImage;
    private int width, height;
    private int x, y;

    private int mapX, mapY;
    private GamePanel gPanel;

    public Rock (GamePanel gPanel, int mapX, int mapY) {

        this.mapX = mapX;
        this.mapY = mapY;
        this.gPanel = gPanel;

        width = height = 30;

        rockImage = ImageManager.loadImage("images/Rocks/Rock_1.png");

    }

    public void draw(Graphics2D g2, Background bg) {

        int bgX = bg.getbg1X();
        int bgY = bg.getbg1Y();

        //if bgX or bgY are negative then make them positive
        if(bgX <0)
            bgX *= -1;
        if(bgY <0)
            bgY *= -1;

        //positioning the rock on the screen relative to the background
        //this calculation makes it so that the rock stays on the map where it needs to be
        int screenX = mapX - bgX;
        int screenY = mapY - bgY;
        
        g2.drawImage(rockImage, screenX, screenY, width, height, null);

    }


}
