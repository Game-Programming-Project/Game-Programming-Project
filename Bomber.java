import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Bomber extends Enemy {

    private Image standImageForward;
    private Image standImageAway;

    private Animation walkAnimationAway;
    private Animation walkAnimationForward;

    private Player player;

    public Bomber(GamePanel gPanel, int mapX, int mapY, Background bg, Player p) {
        super(gPanel,mapX,mapY,bg);
        player = p;

        walkAnimationAway= new Animation(false);
        walkAnimationForward= new Animation(false);
        
        loadImages();
        loadWalkAnimations();

        width=height=30;

        dx=5;
        dy=0;
    }

    public void move() {
        int oldMapX = mapX;
        int oldMapY = mapY;

        int playerX = player.getX();
        int playerY = player.getY();

        mapX+=dx;
        if(oldMapX<mapX){ //moving right

            walkAnimation = walkAnimationRight;
            standImage = standImageRight;
        } else if (oldMapX > mapX) { // moving left
            walkAnimation = walkAnimationLeft;
            standImage = standImageLeft;
        }

        mapY += dy;
        if (oldMapY < mapY) { // moving down
            walkAnimation = walkAnimationForward;
            standImage = standImageForward;
        } else if (oldMapY > mapY) { // moving up
            walkAnimation = walkAnimationAway;
            standImage = standImageAway;
        }

    }

    public void loadWalkAnimations() {
        walkAnimationAway = loadAnimation("images/Enemies/Bomber/bomberWalkAway.png");
        walkAnimationForward = loadAnimation("images/Enemies/Bomber/bomberWalkForward.png");
        walkAnimationLeft = loadAnimation("images/Enemies/Bomber/bomberWalkLeft.png");
        walkAnimationRight = loadAnimation("images/Enemies/Bomber/bomberWalkRight.png");

        walkAnimation = walkAnimationForward;
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
        standImageForward = ImageManager.loadImage("images/Enemies/Bomber/Standing/bomberStandForward.png");
        standImageAway = ImageManager.loadImage("images/Enemies/Bomber/Standing/bomberStandAway.png");
        standImageRight = ImageManager.loadImage("images/Enemies/Bomber/Standing/bomberStandRight.png");
        standImageLeft = ImageManager.loadImage("images/Enemies/Bomber/Standing/bomberStandLeft.png");
        standImage = standImageForward;
    }
}
