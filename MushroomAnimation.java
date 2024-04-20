import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class MushroomAnimation extends Enemy {

	private Image standImageForward;
	private Image standImageAway;

	private Animation walkAnimationAway;
	private Animation walkAnimationForward;

	public MushroomAnimation(GamePanel gPanel, int mapX, int mapY, Background bg, Player p) {
		super(gPanel, mapX, mapY, bg, p);

		walkAnimationAway = new Animation(false);
		walkAnimationForward = new Animation(false);

		loadImages();
		loadWalkAnimations();

		width = 212; //53:26
		height = 104;

		dx = 0;
		dy = 0;

	}

	public void move() {
		int oldMapX = mapX;
		int oldMapY = mapY;

		mapX += dx;
		if (oldMapX < mapX) { // moving right
			walkAnimation = walkAnimationRight;
			standImage = standImageRight;
		} else if (oldMapX > mapX) { // moving left
			walkAnimation = walkAnimationLeft;
			standImage = standImageLeft;
		}

		mapY += dy;
		if (oldMapY < mapY) { // moving down
			walkAnimation = walkAnimationForward;
			standImage = standImageForward;
		} else if (oldMapY > mapY) { // moving up
			walkAnimation = walkAnimationAway;
			standImage = standImageAway;
		}

	}

	public void loadWalkAnimations() {
		walkAnimationLeft = loadAnimation("images/Enemies/Level1/Mushroom/mushroomChasingLeft.png");
		walkAnimationRight = loadAnimation("images/Enemies/Level1/Mushroom/mushroomChasingRight.png");

		walkAnimation = walkAnimationRight;
	}

	public Animation loadAnimation(String stripFilePath) {

		Animation Animation = new Animation(false);

		// load images from strip file
		Image stripImage = ImageManager.loadImage(stripFilePath);

		int spriteColumns = 5; // Assuming 5 columns in the sprite sheet
		int spriteRows = 2; // Assuming 2 rows in the sprite sheet

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

				Animation.addFrame(frameImage, 100);
			}

		}

		return Animation;
	}

	public void loadImages() {
		standImage = ImageManager.loadImage("images/Enemies/Level1/Mushroom/mushroomStanding.png");

		standImageLeft = ImageManager.loadImage("images/Enemies/Level1/Mushroom/mushroomStandingLeft.png");
		standImageRight = ImageManager.loadImage("images/Enemies/Level1/Mushroom/mushroomStandingRight.png");

	}
}