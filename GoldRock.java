import java.awt.Image;

public class GoldRock extends Rock {
    private Image goldRockImage;

    public GoldRock(GamePanel gPanel, int mapX, int mapY, Background bg) {
        super(gPanel, mapX, mapY, bg);
        rockImage = ImageManager.loadImage("images/Rocks/goldRock.png");
        materialValue = 3;
        scoreValue = 30;
    }

}
