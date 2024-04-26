import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class CopperRock extends Rock {
    private Image copperRockImage;

    public CopperRock(GamePanel gPanel, int mapX, int mapY, Background bg) {
        super(gPanel, mapX, mapY, bg);
        rockImage = ImageManager.loadImage("images/Rocks/copperRock.png");

    }

}
