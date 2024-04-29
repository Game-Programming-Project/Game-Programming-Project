import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class TintFX implements ImageFX {

    private static final int WIDTH = 1100;      // width of the image
    private static final int HEIGHT = 500;     // height of the image
    private static final int YPOS = 0;       // vertical position of the image
    private static final int XPOS = 0;       // vertical position of the image

    private GamePanel panel;

    private int x;
    private int y;

    private BufferedImage spriteImage;         // image for sprite effect
    private BufferedImage copy;                // copy of image

    Graphics2D g2;

    int tint, tintChange;                       // to alter the brightness of the image

    public TintFX(GamePanel p) {
        panel = p;

        x = XPOS;
        y = YPOS;

        tint = -100;                             // lower values darken the image
        tintChange = 1;                          // increase of tint in each update

        spriteImage = ImageManager.loadBufferedImage("images/MineEntrance.png");
    }

    private int truncate(int colourValue) {     // keeps colourValue within [0..255] range
        if (colourValue > 255)
            return 255;

        if (colourValue < 0)
            return 0;

        return colourValue;
    }

    private int applyTint(int pixel) {
        int alpha, red, green, blue;
        int newPixel;

        alpha = (pixel >> 24) & 255;
        red = (pixel >> 16) & 255;
        green = (pixel >> 8) & 255;
        blue = pixel & 255;

        // Decrease the value of the red component based on the value of tint
        red = truncate(red + tint);

        // Check the boundaries for 8-bit red component [0..255]

        newPixel = blue | (green << 8) | (red << 16) | (alpha << 24);

        return newPixel;
    }

    public void draw(Graphics2D g2) {
        copy = ImageManager.copyImage(spriteImage);
        // make copy of image for brightness effect

        int imWidth = copy.getWidth();
        int imHeight = copy.getHeight();

        int[] pixels = new int[imWidth * imHeight];
        copy.getRGB(0, 0, imWidth, imHeight, pixels, 0, imWidth);

        int alpha, red, green, blue;

        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = applyTint(pixels[i]);
        }

        copy.setRGB(0, 0, imWidth, imHeight, pixels, 0, imWidth);

        g2.drawImage(copy, x, y, WIDTH, HEIGHT, null);

    }

    public Rectangle2D.Double getBoundingRectangle() {
        return new Rectangle2D.Double(x, y, WIDTH, HEIGHT);
    }

    public void update() {                      // modify brightness (-255 to 255)

        tint = tint + tintChange;

        if (tint > 255) {
            tint = 0;
        }
    }
}
