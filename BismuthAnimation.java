import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Random;

public class BismuthAnimation extends Enemy {

    private final int minX = 2207;
    private final int maxX = 3024;
    private final int minY = 843;
    private final int maxY = 1249;
    private final int aggression = 10; 

    private Animation walkAnimationLeft;
    private Animation walkAnimationRight;

    private Player player;
    private SolidObjectManager soManager;

    // Constructor
    public BismuthAnimation(GamePanel gPanel, int mapX, int mapY, Background bg, Player p, SolidObjectManager soManager) {
        super(gPanel, mapX, mapY, bg, p);
        this.player = p;
        this.soManager = soManager;

        dx = 5;
        dy = 5;

        width = 40;
        height = 55;

        loadImages();
        loadWalkAnimations();
    }


    public void move() {
        int oldMapX = mapX;
        int oldMapY = mapY;

        double distance = Math.sqrt(Math.pow(player.getX() - x, 2) + Math.pow(player.getY() - y, 2));

        Boolean wouldCollide = soManager.collidesWithSolid(getFutureBoundingRectangle());

        if (!wouldCollide && distance < 300) {
            blockPlayer(); // Invoke blockPlayer() method to block the player's path
        }

        else if (oldMapX < mapX) {
            walkAnimation = walkAnimationRight;
            standImage = standImageRight;
        } 
        else if (oldMapX > mapX) {
            walkAnimation = walkAnimationLeft;
            standImage = standImageLeft;
        }

        if (oldMapX == mapX && oldMapY == mapY) {
            walkAnimation.stop();
        }
    }


    public void loadWalkAnimations() {
        walkAnimationLeft = loadAnimation("images/Enemies/Level2/Bismuth/bismuthLeft.png");
        walkAnimationRight = loadAnimation("images/Enemies/Level2/Bismuth/bismuthRight.png");

        walkAnimation = walkAnimationLeft;
    }

    
    public void loadImages() {
        standImageRight = ImageManager.loadImage("images/Enemies/Level2/Bismuth/bismuthStandRight.png");
        standImageLeft = ImageManager.loadImage("images/Enemies/Level2/Bismuth/bismuthStandLeft.png");

        standImage = standImageLeft;
    }


    public Animation loadAnimation(String stripFilePath) {
        Animation animation = new Animation(false);
        Image stripImage = ImageManager.loadImage(stripFilePath);
        int imageWidth = (int) stripImage.getWidth(null) / 10;
        int imageHeight = stripImage.getHeight(null);

        for (int i = 0; i < 10; i++) {
            BufferedImage frameImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = (Graphics2D) frameImage.getGraphics();

            g.drawImage(stripImage,
                    0, 0, imageWidth, imageHeight,
                    i * imageWidth, 0, (i * imageWidth) + imageWidth, imageHeight,
                    null);

            animation.addFrame(frameImage, 200);
        }

        return animation;
    }


    private void blockPlayer() {
        int playerX = player.getX() + player.getWidth() / 2;
        int playerY = player.getY() + player.getHeight() / 2;
    
        Random rand = new Random();
        int random = rand.nextInt(aggression);
    
        updateScreenCoordinates();
    
        if (random == 0) {
            int distanceX = Math.abs(playerX - x);
            int distanceY = Math.abs(playerY - y);
    
            if (distanceX > 35) { // Check if the difference is more than 10 pixels
                if (playerX > x && mapX < maxX) { // Player is to the right and within maxX
                    mapX += dx;
                }
                if (playerX < x && mapX > minX) { // Player is to the left and within minX
                    mapX -= dx;
                }
            } else {
                if (distanceY > 35) { // Check if the difference is more than 10 pixels
                    if (playerY > y && mapY < maxY) { // Player is below and within maxY
                        mapY += dy;
                    }
                    if (playerY < y && mapY > minY) { // Player is above and within minY
                        mapY -= dy;
                    }
                }
            }
        }
    }

    public int getCurrentXPosition() {
        return mapX; 
    }
    
}
