import java.awt.Image;

public class DiamondRock extends Rock {
    private Image diamondRockImage;

    public DiamondRock(GamePanel gPanel, int mapX, int mapY, Background bg) {
        super(gPanel, mapX, mapY, bg);
        rockImage = ImageManager.loadImage("images/Rocks/diamondRock.png");
        value=4;
    }

}
