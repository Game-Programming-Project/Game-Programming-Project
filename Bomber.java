import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

public class Bomber extends Enemy {

    private Image standImageForward;
    private Image standImageAway;

    private Animation walkAnimationUp;
    private Animation walkAnimationDown;

    private Player player;

    private SolidObjectManager soManager;

    public Bomber(GamePanel gPanel, int mapX, int mapY, Background bg, Player p, SolidObjectManager soManager) {
        super(gPanel,mapX,mapY,bg,p);
        player = p;
        this.soManager = soManager;

        walkAnimationUp= new Animation(false);
        walkAnimationDown= new Animation(false);
        
        loadImages();
        loadWalkAnimations();

        width=height=30;

        dx=5;
        dy=3;
    }

    public void move() {
        int oldMapX = mapX;
        int oldMapY = mapY;

        double distance = Math.sqrt(Math.pow(player.getX() - x, 2) + Math.pow(player.getY() - y, 2));

        Boolean wouldCollide = soManager.collidesWithSolid(getFutureBoundingRectangle());
        if(!wouldCollide && distance < 400) // only move if Shaman won't collide with a solid and if the player is within range
            chasePlayer();

        if(oldMapX<mapX){ //moving right

            walkAnimation = walkAnimationRight;
            standImage = standImageRight;
        } else if (oldMapX > mapX) { // moving left
            walkAnimation = walkAnimationLeft;
            standImage = standImageLeft;
        }

        if (oldMapY < mapY) { // moving down
            walkAnimation = walkAnimationDown;
            standImage = standImageForward;
        } else if (oldMapY > mapY) { // moving up
            walkAnimation = walkAnimationUp;
            standImage = standImageAway;
        }

        //stop animation if enemy is blocked or not moving
        if(oldMapX == mapX && oldMapY == mapY){
            walkAnimation.stop();
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

    public void loadWalkAnimations() {
        walkAnimationUp = loadAnimation("images/Enemies/Level3/Bomber/bomberWalkAway.png");
        walkAnimationDown = loadAnimation("images/Enemies/Level3/Bomber/bomberWalkForward.png");
        walkAnimationLeft = loadAnimation("images/Enemies/Level3/Bomber/bomberWalkLeft.png");
        walkAnimationRight = loadAnimation("images/Enemies/Level3/Bomber/bomberWalkRight.png");

        walkAnimation = walkAnimationDown;
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
        standImageForward = ImageManager.loadImage("images/Enemies/Level3/Bomber/Standing/bomberStandForward.png");
        standImageAway = ImageManager.loadImage("images/Enemies/Level3/Bomber/Standing/bomberStandAway.png");
        standImageRight = ImageManager.loadImage("images/Enemies/Level3/Bomber/Standing/bomberStandRight.png");
        standImageLeft = ImageManager.loadImage("images/Enemies/Level3/Bomber/Standing/bomberStandLeft.png");
        standImage = standImageForward;
    }
}
