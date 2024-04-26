import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

/**
    The BismuthAnimation class creates an animation from a strip file.
*/
public class BismuthAnimation {
    
    Animation animation;

    private int x;      // x position of animation
    private int y;      // y position of animation

    private int width;
    private int height;

    private int dx;     // increment to move along x-axis
    private int dy;     // increment to move along y-axis

    public BismuthAnimation() {

        animation = new Animation(false);   // run animation once

        dx = 0;     // increment to move along x-axis
        dy = -10;   // increment to move along y-axis

        // load images from strip file
        Image stripImage = ImageManager.loadImage("images/Enemies/Level2/Bismuth/bismuth.png");

        int imageWidth = stripImage.getWidth(null) / 10; // Assuming 10 frames in the strip
        int imageHeight = stripImage.getHeight(null);

        for (int i = 0; i < 10; i++) {
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
        x = 250;
        y = 250;
        animation.start();
    }

    public void update() {
        if (!animation.isStillActive())
            return;

        animation.update();
        x = x + dx;
        y = y + dy;
    }

    public void draw(Graphics2D g2) {
        if (!animation.isStillActive())
            return;

        g2.drawImage(animation.getImage(), x, y, 70, 50, null);
    }
}