import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;


public class FireBat extends Enemy {

    private Player player;

    public FireBat(GamePanel gPanel, int mapX, int mapY, Background bg, Player p) {
        super(gPanel,mapX,mapY,bg,p);
        player = p;
        
        loadImages();
        loadWalkAnimations();

        width=height=30;

        dx=7;
        dy=7;

        health = 3;
        scoreValue=22;
    }

    public void move() {
        int oldMapX = mapX;
        int oldMapY = mapY;

        if(!soundManager.isStillPlaying("batFlap"))
            soundManager.playClip("batFlap", false);

        double distance = Math.sqrt(Math.pow(player.getX() - x, 2) + Math.pow(player.getY() - y, 2));

        chasePlayer();

        if(oldMapX<mapX){ //moving right

            walkAnimation = walkAnimationRight;
            standImage = standImageRight;
        } else if (oldMapX > mapX) { // moving left
            walkAnimation = walkAnimationLeft;
            standImage = standImageLeft;
        }

    }

    public void loadWalkAnimations() {
        walkAnimationLeft = loadAnimation("images/Enemies/Level3/Bat/batWalkLeft.png");
        walkAnimationRight = loadAnimation("images/Enemies/Level3/Bat/batWalkRight.png");

        walkAnimation = walkAnimationLeft;
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

            Animation.addFrame(frameImage, 100);
        }

        return Animation;
    }

    public void loadImages() {
        standImageRight = ImageManager.loadImage("images/Enemies/Level3/Bat/batStandRight.png");
        standImageLeft = ImageManager.loadImage("images/Enemies/Level3/Bat/batStandLeft.png");

        standImage = standImageLeft;
    }
}
