import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;


/**
    The ScorpionAnimation class creates animations for a scorpion facing left and right.
*/
public class ScorpionAnimation {
    
    Animation leftAnimation;
    Animation rightAnimation;

    private int xLeft;      // x position of left animation
    private int yLeft;      // y position of left animation

    private int xRight;     // x position of right animation
    private int yRight;     // y position of right animation

    private int dx;         // increment to move along x-axis
    private int dy;         // increment to move along y-axis

    public ScorpionAnimation() {

        leftAnimation = new Animation(false);   // run left animation once
        rightAnimation = new Animation(false);  // run right animation once

        dx = 0;         // increment to move along x-axis
        dy = -10;       // increment to move along y-axis

        // load images from left strip file
        Image leftStripImage = ImageManager.loadImage("images/scorpionLeft.png");
        loadAnimation(leftAnimation, leftStripImage);

        // load images from right strip file
        Image rightStripImage = ImageManager.loadImage("images/scorpionRight.png");
        loadAnimation(rightAnimation, rightStripImage);
    }

    /**
     * Loads images from a strip file into an animation.
     * @param animation The animation to load images into
     * @param stripImage The strip image containing animation frames
     */
    private void loadAnimation(Animation animation, Image stripImage) {
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
    }

    public void start() {
        xLeft = 250;
        yLeft = 250;
        leftAnimation.start();

        xRight = 350; // Adjust x position for the right animation
        yRight = 250;
        rightAnimation.start();
    }

    public void update() {
        if (!leftAnimation.isStillActive() || !rightAnimation.isStillActive())
            return;

        leftAnimation.update();
        xLeft = xLeft + dx;
        yLeft = yLeft + dy;

        rightAnimation.update();
        xRight = xRight + dx;
        yRight = yRight + dy;
    }

    public void draw(Graphics2D g2) {
        if (!leftAnimation.isStillActive() || !rightAnimation.isStillActive())
            return;

        g2.drawImage(leftAnimation.getImage(), xLeft, yLeft, 70, 50, null);
        g2.drawImage(rightAnimation.getImage(), xRight, yRight, 70, 50, null);
    }
}
