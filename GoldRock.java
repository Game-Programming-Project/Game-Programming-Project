import java.awt.Image;

public class GoldRock extends Rock {
    private Image goldRockImage;

    public GoldRock(GamePanel gPanel, int mapX, int mapY, Background bg) {
        super(gPanel, mapX, mapY, bg);
        rockImage = ImageManager.loadImage("images/Rocks/goldRock.png");
        value = 3;
    }

}
