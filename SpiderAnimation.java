import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

/**
 * The SpiderAnimation class creates animations for a spider facing left, moving back, and moving forward.
 */
public class SpiderAnimation {
    
    Animation spiderAnimation;
    Animation backAnimation;
    Animation forwardAnimation;

    private int x;      // x position of animation
    private int y;      // y position of animation

    private int dx;     // increment to move along x-axis
    private int dy;     // increment to move along y-axis

    public SpiderAnimation() {

        spiderAnimation = new Animation(false);     // run left animation once
        backAnimation = new Animation(false);     // run back animation once
        forwardAnimation = new Animation(false);  // run forward animation once

        dx = 0;     // increment to move along x-axis
        dy = -10;   // increment to move along y-axis

        // Load images from strip files for left, back, and forward animations
        Image spiderStripImage = ImageManager.loadImage("images/Enemies/Level2/Spider/spider.png");
        Image backStripImage = ImageManager.loadImage("images/Enemies/Level2/Spider/spiderMoveBack.png");
        Image forwardStripImage = ImageManager.loadImage("images/Enemies/Level2/Spider/spiderMoveForward.png");

        // Assuming each strip image contains 4 frames
        int imageWidth = spiderStripImage.getWidth(null) / 4;
        int imageHeight = spiderStripImage.getHeight(null);

        // Load images into left animation
        loadAnimation(spiderAnimation, spiderStripImage, imageWidth, imageHeight);

        // Load images into back animation
        loadAnimation(backAnimation, backStripImage, imageWidth, imageHeight);

        // Load images into forward animation
        loadAnimation(forwardAnimation, forwardStripImage, imageWidth, imageHeight);
    }


    //Loads images from a strip file into an animation.
    private void loadAnimation(Animation animation, Image stripImage, int imageWidth, int imageHeight) {
        for (int i = 0; i < 4; i++) {
            BufferedImage frameImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = (Graphics2D) frameImage.getGraphics();
     
            g.drawImage(stripImage, 
                        0, 0, imageWidth, imageHeight,
                        i * imageWidth, 0, (i * imageWidth) + imageWidth, imageHeight,
                        null);

            animation.addFrame(frameImage, 100);
        }
    }

    public void start() {
        x = 250; // Assuming starting position
        y = 250; // Assuming starting position
        spiderAnimation.start();
        backAnimation.start();
        forwardAnimation.start();
    }

    public void update() {
        if (!spiderAnimation.isStillActive() || !backAnimation.isStillActive() || !forwardAnimation.isStillActive())
            return;

        spiderAnimation.update();
        backAnimation.update();
        forwardAnimation.update();
        x = x + dx;
        y = y + dy;
    }

    public void draw(Graphics2D g2) {
        if (!spiderAnimation.isStillActive() || !backAnimation.isStillActive() || !forwardAnimation.isStillActive())
            return;

        g2.drawImage(spiderAnimation.getImage(), x, y, 70, 50, null);
        g2.drawImage(backAnimation.getImage(), x + 100, y, 70, 50, null);
        g2.drawImage(forwardAnimation.getImage(), x + 200, y, 70, 50, null);
    }
}
