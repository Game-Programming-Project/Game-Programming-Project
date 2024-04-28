import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.util.Random;

public class SpiderAnimation extends Enemy {

	private Image standImageForward;
	private Image standImageAway;

	private Animation walkAnimationAway;
	private Animation walkAnimationForward;

	// Constants for different behavior types
	private static final int NORMAL = 0;
	private static final int AGGRESSIVE = 1;
	private static final int PASSIVE = 2;

	// Probability of each behavior type (sum should be 100)
	private static final int NORMAL_PROBABILITY = 60; // Higher chance of normal behavior
	private static final int AGGRESSIVE_PROBABILITY = 20;
	private static final int PASSIVE_PROBABILITY = 20;

	// Random object for generating random behavior
	private static final Random random = new Random();

    private SolidObjectManager soManager;  

	public SpiderAnimation(GamePanel gPanel, int mapX, int mapY, Background bg, Player p, SolidObjectManager soManager) {
		super(gPanel, mapX, mapY, bg, p);
        this.soManager = soManager;

		walkAnimationAway = new Animation(false);
		walkAnimationForward = new Animation(false);
		initBehavior();

		loadImages();
		loadWalkAnimations();

		width = height = 50;

		dx = 2;
		dy = 2;

		health = 20; 
		scoreValue = 60; 

	}


	private void initBehavior() {
		int behavior = selectBehavior();
		setBehavior(behavior);
	}


	private int selectBehavior() {
		int randomNumber = random.nextInt(100); // Generate random number between 0 and 99

		if (randomNumber < NORMAL_PROBABILITY) {
			return NORMAL;
		} else if (randomNumber < NORMAL_PROBABILITY + AGGRESSIVE_PROBABILITY) {
			return AGGRESSIVE;
		} else {
			return PASSIVE;
		}
	}

	// Method to set behavior based on behavior type
	private void setBehavior(int behavior) {
		switch (behavior) {
			case NORMAL:
				// Set normal behavior
				break;
			case AGGRESSIVE:
				// Set aggressive behavior
				break;
			case PASSIVE:
				// Set passive behavior
				break;
			default:
				// Default behavior
				break;
		}
	}

	public void chasePlayer() {
		int playerX = player.getX();
		int playerY = player.getY();

		// Calculate the distance between the bee and the player
		double distance = Math.sqrt(Math.pow(playerX - x, 2) + Math.pow(playerY - y, 2));

		// If the player is within a certain range (e.g., 100 pixels)
		if (distance <= 200) {
			if (walkAnimation.isStillActive() && !soundManager.isStillPlaying("crawling")) {
				playWalkSound();
			}
			if (playerX > x) { // player is to the right
				mapX += dx;
				walkAnimation = walkAnimationRight;
				standImage = standImageRight;
			
			} else if (playerX - player.getWidth() < x) { // player is to the left
				mapX -= dx;
				walkAnimation = walkAnimationLeft;
				standImage = standImageLeft;
				
			}

			if (playerY - player.getHeight() > y) { // player is below
				mapY += dy;
				walkAnimation = walkAnimationLeft;
				
			} else if (playerY + player.getHeight() < y) { // player is above
				mapY -= dy;
				walkAnimation = walkAnimationLeft;
			
			}
		} else {
			walkAnimation = walkAnimationRight;
			standImage = standImageRight;

		}
	}

	public void move() {
		int oldMapX = mapX;
		int oldMapY = mapY;

		Boolean wouldCollide = soManager.collidesWithSolid(getFutureBoundingRectangle());
        if(!wouldCollide)
            chasePlayer();

		if (oldMapX < mapX) { // moving right
			walkAnimation = walkAnimationRight;
			standImage = standImageRight;
			playWalkSound();
		} else if (oldMapX > mapX) { // moving left
			walkAnimation = walkAnimationLeft;
			standImage = standImageLeft;
			playWalkSound();
		}

	}

	public void loadWalkAnimations() {
		walkAnimationLeft = loadAnimation("images/Enemies/Level2/Spider/spiderWalkLeft.png");
		walkAnimationRight = loadAnimation("images/Enemies/Level2/Spider/spiderWalkRight.png");

		walkAnimation = walkAnimationRight;
	}

	public Animation loadAnimation(String stripFilePath) {

		Animation Animation = new Animation(false);

        Image stripImage = ImageManager.loadImage(stripFilePath);

        int imageWidth = (int) stripImage.getWidth(null) / 4;
        int imageHeight = stripImage.getHeight(null);

        for (int i = 0; i < 4; i++) {

            BufferedImage frameImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = (Graphics2D) frameImage.getGraphics();

            g.drawImage(stripImage,
                    0, 0, imageWidth, imageHeight,
                    i * imageWidth, 0, (i * imageWidth) + imageWidth, imageHeight,
                    null);

            Animation.addFrame(frameImage, 200);
        }

        return Animation;
	}

	public void loadImages() {
		standImageLeft = ImageManager.loadImage("images/Enemies/Level2/Spider/Standing/spiderStandLeft.png");
		standImageRight = ImageManager.loadImage("images/Enemies/Level2/Spider/Standing/spiderStandRight.png");

		standImage = standImageForward;
	}

	private void playWalkSound() {
		soundManager.playClip("crawling", false);
	}
}