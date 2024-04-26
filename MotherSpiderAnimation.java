import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

/**
    The MotherSpiderAnimation class creates an animation from a vertical strip file.
*/
public class MotherSpiderAnimation {
    
    Animation animation;

    private int x;      // x position of animation
    private int y;      // y position of animation

    private int width;
    private int height;

    private int dy;     // increment to move along y-axis

    public MotherSpiderAnimation() {

        animation = new Animation(false);   // run animation once

        dy = 1;     // increment to move downward along y-axis

        // load images from vertical strip file
        Image stripImage = ImageManager.loadImage("images/motherSpider.png");

        int imageWidth = stripImage.getWidth(null);
        int imageHeight = stripImage.getHeight(null) / 3; // Assuming 3 frames in the strip

        for (int i = 0; i < 3; i++) {
            BufferedImage frameImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = (Graphics2D) frameImage.getGraphics();
     
            g.drawImage(stripImage, 
                        0, 0, imageWidth, imageHeight,
                        0, i * imageHeight, imageWidth, (i * imageHeight) + imageHeight,
                        null);

            animation.addFrame(frameImage, 100);
        }
    }

    public void start() {
        x = 250; // Assuming starting position
        y = 5;   // Start at the top
        animation.start();
    }

    public void update() {
        if (!animation.isStillActive())
            return;

        animation.update();

        // Update y position to simulate falling
        y += dy;
    }

    public void draw(Graphics2D g2) {
        if (!animation.isStillActive())
            return;

        g2.drawImage(animation.getImage(), x, y, 70, 50, null);
    }
}
