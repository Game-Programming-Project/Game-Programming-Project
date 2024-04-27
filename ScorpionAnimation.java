import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class ScorpionAnimation extends Enemy {


	public ScorpionAnimation(GamePanel gPanel, int mapX, int mapY, Background bg, Player p) {
		super(gPanel, mapX, mapY, bg, p);

		loadImages();
		loadWalkAnimations();

		width = height = 60;

		dx = 5;
		dy = 5;

	}

    public void move() {
        int prevMapX = mapX;
        //int prevMapY = mapY;

        double distance = Math.sqrt(Math.pow(player.getX() - x, 2) + Math.pow(player.getY() - y, 2));

        // only update the movement of scorpion if there is no collision with solid object/ player in range
        if(distance < 350) chasePlayer();

        else if(prevMapX < mapX){ //moving right
            walkAnimation = walkAnimationRight;
            standImage = standImageRight;
        } 
        else if (prevMapX > mapX) { // moving left
            walkAnimation = walkAnimationLeft;
            standImage = standImageLeft;
        }
        
    }
	
 
    // Loads images from a strip file into an animation.
    private Animation loadAnimation(String stripFilePath) {
        Animation animation = new Animation(false);
        Image stripImage = ImageManager.loadImage(stripFilePath);

         int imageWidth = stripImage.getWidth(null) / 8; // Assuming 8 frames in the strip
        int imageHeight = stripImage.getHeight(null);

        for (int i = 0; i < 8; i++) {
            BufferedImage frameImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = (Graphics2D) frameImage.getGraphics();

            g.drawImage(stripImage, 
                        0, 0, imageWidth, imageHeight,
                        i * imageWidth, 0, (i * imageWidth) + imageWidth, imageHeight,
                        null);

            animation.addFrame(frameImage, 100);
        }

        return animation;
       
    }

    public void loadWalkAnimations() {
		walkAnimationLeft = loadAnimation("images/Enemies/Level2/Scorpion/scorpionLeft.png");
		walkAnimationRight = loadAnimation("images/Enemies/Level2/Scorpion/scorpionRight.png");

		walkAnimation = walkAnimationRight;
	}
    

    public void loadImages() {
        standImageRight = ImageManager.loadImage("images/Enemies/Level2/Scorpion/scorpionStandRight.png");
        standImageLeft = ImageManager.loadImage("images/Enemies/Level2/Scorpion/scorpionStandLeft.png");

        standImage = standImageLeft;
    }

	public void chasePlayer() {
		int playerX = player.getX();
		int playerY = player.getY();

		// Calculate the distance between the scorpion and the player
		double distance = Math.sqrt(Math.pow(playerX - x, 2) + Math.pow(playerY - y, 2));

		// If the player is within a certain range (e.g., 100 pixels)
		if (distance <= 200) {
			if (walkAnimation.isStillActive() && !soundManager.isStillPlaying("grasshopperJump")) {
				playCrawlingSound();
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
			walkAnimation = null;
			standImage = standImageRight;

		}
	}

	private void playCrawlingSound() {
		soundManager.playClip("crawling", false);
	}

}