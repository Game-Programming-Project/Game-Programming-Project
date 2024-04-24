import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GrasshopperAnimation extends Enemy {

	private Image standImageForward;
	private Image standImageAway;

	private Animation walkAnimationUp;
	private Animation walkAnimationDown;

	public GrasshopperAnimation(GamePanel gPanel, int mapX, int mapY, Background bg, Player p) {
		super(gPanel, mapX, mapY, bg, p);

		walkAnimationUp = new Animation(false);
		walkAnimationDown = new Animation(false);

		loadImages();
		loadWalkAnimations();

		width = height = 60;

		dx = 5;
		dy = 5;

	}

	public void move() {
		int oldMapX = mapX;
		int oldMapY = mapY;

		chasePlayer();

		if (oldMapX < mapX) { // moving right
			walkAnimation = walkAnimationRight;
			standImage = standImageRight;
		} else if (oldMapX > mapX) { // moving left
			walkAnimation = walkAnimationLeft;
			standImage = standImageLeft;
		}

	}

	public void loadWalkAnimations() {
		walkAnimationLeft = loadAnimation("images/Enemies/Level1/Grasshopper/grasshopperSpriteLeft.png");
		walkAnimationRight = loadAnimation("images/Enemies/Level1/Grasshopper/grasshopperSpriteRight.png");

		walkAnimation = walkAnimationRight;
	}

	public Animation loadAnimation(String stripFilePath) {

		Animation Animation = new Animation(false);

		// load images from strip file
		Image stripImage = ImageManager.loadImage(stripFilePath);

		int spriteColumns = 5; // Assuming 5 columns in the sprite sheet
		int spriteRows = 6; // Assuming 2 rows in the sprite sheet

		int imageWidth = stripImage.getWidth(null) / spriteColumns;
		int imageHeight = stripImage.getHeight(null) / spriteRows;

		for (int row = 0; row < spriteRows; row++) {
			for (int col = 0; col < spriteColumns; col++) {
				BufferedImage frameImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
				Graphics2D g = (Graphics2D) frameImage.getGraphics();

				g.drawImage(stripImage,
						0, 0, imageWidth, imageHeight,
						col * imageWidth, row * imageHeight, (col * imageWidth) + imageWidth,
						(row * imageHeight) + imageHeight,
						null);

				Animation.addFrame(frameImage, 130);
			}

		}

		return Animation;
	}

	public void loadImages() {
		standImageLeft = ImageManager.loadImage("images/Enemies/Level1/Grasshopper/grasshopperStandingLeft.png");
		standImageRight = ImageManager.loadImage("images/Enemies/Level1/Grasshopper/grasshopperStandingRight.png");

		standImage = standImageForward;
	}
}