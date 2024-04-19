import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * The StripAnimation class creates an animation from a strip file.
 * 108 327
 * 
 */
public class BeeAnimation {

	Animation animation;

	private int x; // x position of animation
	private int y; // y position of animation
	private int mapX, mapY; // x and y position of map

	private int width;
	private int height;

	private int dx; // increment to move along x-axis
	private int dy; // increment to move along y-axis

	public BeeAnimation() {
		animation = new Animation(true); // run animation once

		width = 50;
		height = 50;

		dx = 0; // increment to move along x-axis
		dy = 0; // increment to move along y-axis

		// load images from strip file
		Image stripImage = ImageManager.loadImage("images/beeSprite.png");

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

				animation.addFrame(frameImage, 200);
			}
		}
	}

	public void start() {
		x = 150;
		y = 150;
		animation.start();
	}

	public void update() {
		if (!animation.isStillActive())
			return;

		animation.update();
		x = x + dx;
		y = y + dy;
	}

	public void draw(Graphics2D g2) {
		if (!animation.isStillActive())
			return;

		g2.drawImage(animation.getImage(), x, y, width, height, null);
	}

}
