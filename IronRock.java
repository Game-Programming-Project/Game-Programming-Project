import java.awt.Image;

public class IronRock extends Rock {

    private Image ironRockImage;

    public IronRock(GamePanel gPanel, int mapX, int mapY, Background bg) {
        super(gPanel, mapX, mapY, bg);
        rockImage = ImageManager.loadImage("images/Rocks/ironRock.png");
        value = 2;
    }

}
