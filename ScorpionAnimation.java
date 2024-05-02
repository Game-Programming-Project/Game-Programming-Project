import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.util.Random;

public class ScorpionAnimation extends Enemy {

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

	public ScorpionAnimation(GamePanel gPanel, int mapX, int mapY, Background bg, Player p, SolidObjectManager soManager) {
		super(gPanel, mapX, mapY, bg, p);
		this.soManager = soManager;
		
		initBehavior();
		loadImages();
		loadWalkAnimations();

		width = 55; 
		height = 60;

		dx = 4;
		dy = 4;

		health = 10; 
		scoreValue = 50; 

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
		// int playerX = player.getX();
		// int playerY = player.getY();

		// // Calculate the distance between the bee and the player
		// double distance = Math.sqrt(Math.pow(playerX - x, 2) + Math.pow(playerY - y, 2));

		// // If the player is within a certain range (e.g., 100 pixels)
		// if (distance <= 250) {
		// 	if (walkAnimation.isStillActive() && !soundManager.isStillPlaying("scorpionRattle")) {
		// 		playWalkSound();
		// 	}
		// 	if (playerX > x) { // player is to the right
		// 		mapX += dx;
		// 		walkAnimation = walkAnimationRight;
		// 		standImage = standImageRight;

		// 	} else if (playerX - player.getWidth() < x) { // player is to the left
		// 		mapX -= dx;
		// 		walkAnimation = walkAnimationLeft;
		// 		standImage = standImageLeft;
		// 	}

		// 	if (playerY - player.getHeight() > y) { // player is below
		// 		mapY += dy;
		// 		walkAnimation = walkAnimationLeft;
		// 		//standImage = standImageLeft;
		// 	} else if (playerY + player.getHeight() < y) { // player is above
		// 		mapY -= dy;
		// 		walkAnimation = walkAnimationLeft;
		// 		//standImage = standImageLeft;
		// 	}
		// } else {
		// 	walkAnimation = walkAnimationRight;
		// 	standImage = standImageRight;

		//}

		int playerX = player.getX();
        int playerY = player.getY();

        //this code makes the enemy target the middle of the player sprite instead of the top left
        playerX += player.getWidth()/2;
        playerY += player.getHeight()/2;

        Random rand = new Random();
        int random = rand.nextInt(aggression);

        updateScreenCoordinates();

        if(random == 0){

            if(Math.abs(playerX - x) > 35){ // check if the difference is more than 10 pixels
                if(playerX > x){ // player is to the right
                    mapX += dx;
                }
                if(playerX < x){ // player is to the left
                    mapX -= dx;
                }
            }
            else{
                if(playerY > y){ // player is below
                    mapY += dy;
                }
    
                if(playerY < y){ // player is above
                    mapY -= dy;
                }
            }
        }
	}

	public void move() {

		int oldMapX = mapX;
        int oldMapY = mapY;

        double distance = Math.sqrt(Math.pow(player.getX() - x, 2) + Math.pow(player.getY() - y, 2));

        Boolean wouldCollide = soManager.collidesWithSolid(getFutureBoundingRectangle());
        if(!wouldCollide && distance < 400){
			playWalkSound();
			chasePlayer();
		} 
            

        if(oldMapX<mapX){ //moving right
            walkAnimation = walkAnimationRight;
            standImage = standImageRight;
        } else if (oldMapX > mapX) { // moving left
            walkAnimation = walkAnimationLeft;
            standImage = standImageLeft;
        }

        if (oldMapY < mapY) { // moving down
            walkAnimation = walkAnimationRight;
			standImage = standImageRight;
        } else if (oldMapY > mapY) { // moving up
            walkAnimation = walkAnimationLeft;
            standImage = standImageLeft;;
        }

        //stop animation if enemy is blocked or not moving
        if(oldMapX == mapX && oldMapY == mapY){
            walkAnimation.stop();
        } 

	}

	public void loadWalkAnimations() {
		walkAnimationLeft = loadAnimation("images/Enemies/Level2/Scorpion/scorpionLeft.png");
		walkAnimationRight = loadAnimation("images/Enemies/Level2/Scorpion/scorpionRight.png");

		walkAnimation = walkAnimationRight;
	}

	public Animation loadAnimation(String stripFilePath) {

		Animation Animation = new Animation(false);

        Image stripImage = ImageManager.loadImage(stripFilePath);

        int imageWidth = (int) stripImage.getWidth(null) / 8;
        int imageHeight = stripImage.getHeight(null);

        for (int i = 0; i < 8; i++) {

            BufferedImage frameImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = (Graphics2D) frameImage.getGraphics();

            g.drawImage(stripImage,
                    0, 0, imageWidth, imageHeight,
                    i * imageWidth, 0, (i * imageWidth) + imageWidth, imageHeight,
                    null);

            Animation.addFrame(frameImage, 100);
        }

        return Animation;
	}

	public void loadImages() {
		standImageLeft = ImageManager.loadImage("images/Enemies/Level2/Scorpion/scorpionStandLeft.png");
		standImageRight = ImageManager.loadImage("images/Enemies/Level2/Scorpion/scorpsionStandRight.png");
		
		standImage = standImageLeft;
	}

	private void playWalkSound() {
		soundManager.playClip("scorpionRattle", false);
	}
}