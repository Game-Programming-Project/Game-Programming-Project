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

	private SolidObjectManager soManager;

	private Boolean inRange;
	private Boolean blowingUp;
	private Long blowUpTime;

	public MushroomAnimation(GamePanel gPanel, int mapX, int mapY, Background bg, Player p, SolidObjectManager soManager) {
		super(gPanel, mapX, mapY, bg, p);
		this.soManager = soManager;

		walkAnimationUp = new Animation(false);
		walkAnimationDown = new Animation(false);

		loadImages();
		loadWalkAnimations();

		width = 212; // 53:26
		height = 104;

		dx = 0;
		dy = 0;

		inRange=false;
		blowingUp=false;
		blowUpTime=null;
	}

	public void update() {
        if (!walkAnimation.isStillActive()) // if the animation is not active, no need to update
            return;

		if(!soundManager.isStillPlaying("mushroomWalk") && walkAnimation.isStillActive() && walkAnimation!=walkAnimationDown && !blowingUp) {
			playWalkSound();
		}

        walkAnimation.update();

        if (health <= 0)
            isAlive = false;

		if(blowingUp && (System.currentTimeMillis() - blowUpTime > 1000)) {
			isAlive = false; // if 1 second pass after blow up then die
		}
    }

	public void move() {

		if(blowingUp)
			return;

		int oldMapX = mapX;
		int oldMapY = mapY;

		double distance = Math.sqrt(Math.pow(player.getX() - x, 2) + Math.pow(player.getY() - y, 2));
		if(distance<300)
			inRange=true;
		else
			inRange=false;

		if (distance <= 50) 
			blowUp();

		if(inRange)
			dx=dy=10;
		else
			dx=dy=0;
		
		
		Boolean wouldCollide = soManager.collidesWithSolid(getFutureBoundingRectangle());
        if(!wouldCollide && inRange) // only move if Mushroom won't collide with a solid and if the player is within range
            chasePlayer();
		

		if (oldMapX < mapX) { // moving right
			walkAnimation = walkAnimationRight;
			standImage = standImageRight;

		} else if (oldMapX > mapX) { // moving left
			walkAnimation = walkAnimationLeft;
			standImage = standImageLeft;

		}

		// stop animation if enemy is blocked or not moving
		if (oldMapX == mapX && oldMapY == mapY) {
			//walkAnimation=walkAnimationDown;
			walkAnimation.stop();
			standImage = standImageForward;
		}

	}

	public void chasePlayer() {
		int playerX = player.getX();
		int playerY = player.getY();

		// if (walkAnimation.isStillActive() && !soundManager.isStillPlaying("mushroomWalk")) {
		// 	playWalkSound();
		// }

		if (playerX - 50 > x) { // player is to the right
			mapX += dx;
			walkAnimation = walkAnimationRight;
			standImage = standImageRight;

		} else if (playerX + 50 < x) { // player is to the left
			mapX -= dx;
			walkAnimation = walkAnimationLeft;
			standImage = standImageLeft;
		}

		if (playerY - 50 > y) { // player is below
			mapY += dy;
			walkAnimation = walkAnimationLeft;

		} else if (playerY + 50 < y) { // player is above
			mapY -= dy;
			walkAnimation = walkAnimationLeft;
		}
	}
	
	// Method to trigger the blow-up animation
	private void blowUp() {
		if(blowingUp)
			return;
			
		blowingUp=true;
		blowUpTime = System.currentTimeMillis();
		walkAnimation = blowUpAnimation;
		playBoomSound();
	}

	public void loadWalkAnimations() {
		walkAnimationLeft = loadAnimation("images/Enemies/Level1/Mushroom/mushroomChasingLeft.png");
		walkAnimationRight = loadAnimation("images/Enemies/Level1/Mushroom/mushroomChasingRight.png");
		blowUpAnimation = loadAnimation("images/Enemies/Level1/Mushroom/mushroomSpriteBOOM.png");

		//walkAnimationDown.addFrame(standImageForward, 100);

		walkAnimation = walkAnimationLeft;
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

	private void playWalkSound() {
		if(soundManager.isStillPlaying("mushroomWalk") || blowingUp)
			return;

		soundManager.playClip("mushroomWalk", false);
	}

	private void playBoomSound() {
		if(soundManager.isStillPlaying("mushroomBoom"))
			return;

		if(soundManager.isStillPlaying("mushroomWalk"))
			soundManager.stopClip("mushroomWalk");

		soundManager.playClip("mushroomBoom", false);
		
	}
}