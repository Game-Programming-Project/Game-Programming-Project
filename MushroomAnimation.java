import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class MushroomAnimation extends Enemy {

	private Image standImageForward;
	private Image standImageAway;

	private Animation walkAnimationUp;
	private Animation walkAnimationDown;
	private Animation blowUpAnimation;

	public MushroomAnimation(GamePanel gPanel, int mapX, int mapY, Background bg, Player p) {
		super(gPanel, mapX, mapY, bg, p);

		walkAnimationUp = new Animation(false);
		walkAnimationDown = new Animation(false);

		loadImages();
		loadWalkAnimations();

		width = 212; // 53:26
		height = 104;

		dx = 10;
		dy = 10;

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
		walkAnimationLeft = loadAnimation("images/Enemies/Level1/Mushroom/mushroomChasingLeft.png");
		walkAnimationRight = loadAnimation("images/Enemies/Level1/Mushroom/mushroomChasingRight.png");
		blowUpAnimation = loadAnimation("images/Enemies/Level1/Mushroom/mushroomSpriteBOOM.png");

		walkAnimationDown.addFrame(standImageForward, 100);

		walkAnimation = walkAnimationDown;
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
		standImageForward = ImageManager.loadImage("images/Enemies/Level1/Mushroom/mushroomStanding.png");
		standImageLeft = ImageManager.loadImage("images/Enemies/Level1/Mushroom/mushroomStandingLeft.png");
		standImageRight = ImageManager.loadImage("images/Enemies/Level1/Mushroom/mushroomStandingRight.png");
		standImage = standImageForward;
	}

	public void chasePlayer() {
		int playerX = player.getX();
		int playerY = player.getY();

		// Calculate the distance between the mushroom and the player
		double distance = Math.sqrt(Math.pow(playerX - x, 2) + Math.pow(playerY - y, 2));

		// If the player is within a certain range (e.g., 100 pixels)
		if (distance <= 300) {
			if (distance <= 50) {
				blowUp();
			}
			if (playerX - 150 > x) { // player is to the right
				mapX += dx;
				walkAnimation = walkAnimationRight;
				standImage = standImageRight;
			} else if (playerX + 100 < x) { // player is to the left
				mapX -= dx;
				walkAnimation = walkAnimationLeft;
				standImage = standImageLeft;
			}

			if (playerY - 100 > y) { // player is below
				mapY += dy;
				walkAnimation = walkAnimationLeft;
			} else if (playerY + 100 < y) { // player is above
				mapY -= dy;
				walkAnimation = walkAnimationLeft;
			}
		} else {
			// If the player is not within range, the mushroom should be standing
			// walkAnimation = null;
			walkAnimation = walkAnimationDown;
			standImage = standImageForward; // or standImageLeft, depending on the last direction

		}
	}

	// Method to trigger the blow-up animation
	private void blowUp() {
		// Replace the current animation with the blow-up animation
		walkAnimation = loadAnimation("images/Enemies/Level1/Mushroom/mushroomSpriteBOOM.png");

		// You may need to adjust the timing and other parameters for the blow-up
		// animation
		// based on your specific implementation
		// Also, you might want to handle any cleanup or other logic related to the
		// blow-up here
	}
}