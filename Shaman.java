import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

public class Shaman extends Enemy {

    private Image standImageForward;
    private Image standImageAway;

    private Animation walkAnimationUp;
    private Animation walkAnimationDown;

    private Player player;

    private SolidObjectManager soManager;

    public Shaman(GamePanel gPanel, int mapX, int mapY, Background bg, Player p, SolidObjectManager soManager) {
        super(gPanel,mapX,mapY,bg,p);
        player = p;
        this.soManager = soManager;

       // walkAnimationUp= new Animation(false);
       // walkAnimationDown= new Animation(false);
        
        loadImages();
        loadWalkAnimations();

        width=30;
        height=45;

        dx=4;
        dy=4;
        direction = 0;
        scoreValue = 73;
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

    public void loadWalkAnimations() {
        walkAnimationUp = loadAnimation("images/Enemies/Level3/Shaman/shamanWalkAway.png");
        walkAnimationDown = loadAnimation("images/Enemies/Level3/Shaman/shamanWalkForward.png");
        walkAnimationLeft = loadAnimation("images/Enemies/Level3/Shaman/shamanWalkLeft.png");
        walkAnimationRight = loadAnimation("images/Enemies/Level3/Shaman/shamanWalkRight.png");

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
        standImageForward = ImageManager.loadImage("images/Enemies/Level3/Shaman/Standing/shamanStandForward.png");
        standImageAway = ImageManager.loadImage("images/Enemies/Level3/Shaman/Standing/shamanStandAway.png");
        standImageRight = ImageManager.loadImage("images/Enemies/Level3/Shaman/Standing/shamanStandRight.png");
        standImageLeft = ImageManager.loadImage("images/Enemies/Level3/Shaman/Standing/shamanStandLeft.png");
        standImage = standImageForward;
    }
}
