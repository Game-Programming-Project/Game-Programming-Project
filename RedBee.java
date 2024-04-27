import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.util.Random;

public class RedBee extends BeeAnimation{
    
    public RedBee(GamePanel gPanel, int mapX, int mapY, Background bg, Player p) {
		super(gPanel, mapX, mapY, bg, p);

        walkAnimationLeft.setLoop(true);
        walkAnimationRight.setLoop(true);

		loadImages();
		loadWalkAnimations();

        //bee image is 132 x 144
		width = 50;
        height = 55;

		dx = 2;
		dy = 2;

	}

    public void update() {
        if (!walkAnimation.isStillActive()) // if the animation is not active, no need to update
            return;

        walkAnimation.update();

        if (health <= 0){
            isAlive = false;
        }   
    }

    public void move() {
        int oldMapX = mapX;
        int oldMapY = mapY;

        double distance = Math.sqrt(Math.pow(player.getX() - x, 2) + Math.pow(player.getY() - y, 2));

        if(distance < 400) // only move if Bee won't collide with a solid and if the player is within range
            chasePlayer();

        if(oldMapX<mapX){ //moving right
            walkAnimation = walkAnimationRight;
            standImage = standImageRight;
        } else if (oldMapX > mapX) { // moving left
            walkAnimation = walkAnimationLeft;
            standImage = standImageLeft;
        }

    }

    public void chasePlayer() {
        int playerX = player.getX();
        int playerY = player.getY();

        //this code makes the enemy target the middle of the player sprite instead of the top left
        playerX += player.getWidth()/2;
        playerY += player.getHeight()/2;

        Random rand = new Random();
        int random = rand.nextInt(aggression);

        updateScreenCoordinates();

        if(random == 0){

            // check if the difference is more than 10 pixels
            if(Math.abs(playerY - y) > 50){ // this makes it so that the enemy does not just target the top left of the player, but the entire height of the player
                if(playerY > y){ // player is below
                    mapY += dy;
                }
    
                if(playerY < y){ // player is above
                    mapY -= dy;
                }
            }
            else if(Math.abs(playerX - x) > 20){
                if(playerX > x){ // player is to the right
                    mapX += dx;
                }
                if(playerX < x){ // player is to the left
                    mapX -= dx;
                }
            }
        }
    }

    public void draw(Graphics2D g2) {

        updateScreenCoordinates();

        // Always draw the stand image
        g2.drawImage(standImage, x, y, null);
    
        // If the animation is ongoing, draw it on top of the stand image
        if (walkAnimation != null && walkAnimation.isStillActive()) {
            g2.drawImage(walkAnimation.getImage(), x, y, width, height, null);
        }
    }

    public void loadWalkAnimations() {
		walkAnimationLeft = loadAnimation("images/Enemies/Level3/RedBee/redBeeWalkLeft.png");
		walkAnimationRight = loadAnimation("images/Enemies/Level3/RedBee/redBeeWalkRight.png");

		walkAnimation = walkAnimationRight;
	}

	public void loadImages() {
		standImageLeft = ImageManager.loadImage("images/Enemies/Level3/RedBee/redBeeStandingLeft.png");
		standImageRight = ImageManager.loadImage("images/Enemies/Level3/RedBee/redBeeStandingRight.png");

		standImage = standImageRight;
	}

}

