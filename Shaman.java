import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

public class Shaman extends Enemy {

    private Image standImageForward;
    private Image standImageAway;

    private Animation walkAnimationAway;
    private Animation walkAnimationForward;

    private Player player;

    public Shaman(GamePanel gPanel, int mapX, int mapY, Background bg, Player p) {
        super(gPanel,mapX,mapY,bg,p);
        player = p;

        walkAnimationAway= new Animation(false);
        walkAnimationForward= new Animation(false);
        
        loadImages();
        loadWalkAnimations();

        width=30;
        height=45;

        dx=4;
        dy=4;
    }

    public void move() {
        int oldMapX = mapX;
        int oldMapY = mapY;

        chasePlayer();

        if(oldMapX<mapX){ //moving right

            walkAnimation = walkAnimationRight;
            standImage = standImageRight;
        } else if (oldMapX > mapX) { // moving left
            walkAnimation = walkAnimationLeft;
            standImage = standImageLeft;
        }


        if (oldMapY < mapY) { // moving down
            walkAnimation = walkAnimationForward;
            standImage = standImageForward;
        } else if (oldMapY > mapY) { // moving up
            walkAnimation = walkAnimationAway;
            standImage = standImageAway;
        }

    }

    public void chasePlayer() {
        int playerX = player.getX();
        int playerY = player.getY();

        Random rand = new Random();
        int random = rand.nextInt(aggression);

        updateScreenCoordinates();

        if(random == 0){

            if(Math.abs(playerX - x) > 10){ // check if the difference is more than 10 pixels
                if(playerX > x){ // player is to the right
                    mapX += dx;
                }
                if(playerX < x){ // player is to the left
                    mapX -= dx;
                }
            }
            else{
                System.out.println("Shaman x equal player x");
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
        walkAnimationAway = loadAnimation("images/Enemies/Level3/Shaman/shamanWalkAway.png");
        walkAnimationForward = loadAnimation("images/Enemies/Level3/Shaman/shamanWalkForward.png");
        walkAnimationLeft = loadAnimation("images/Enemies/Level3/Shaman/shamanWalkLeft.png");
        walkAnimationRight = loadAnimation("images/Enemies/Level3/Shaman/shamanWalkRight.png");

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
        standImageForward = ImageManager.loadImage("images/Enemies/Level3/Shaman/Standing/shamanStandForward.png");
        standImageAway = ImageManager.loadImage("images/Enemies/Level3/Shaman/Standing/shamanStandAway.png");
        standImageRight = ImageManager.loadImage("images/Enemies/Level3/Shaman/Standing/shamanStandRight.png");
        standImageLeft = ImageManager.loadImage("images/Enemies/Level3/Shaman/Standing/shamanStandLeft.png");
        standImage = standImageForward;
    }
}