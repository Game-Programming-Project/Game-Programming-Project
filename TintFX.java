import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class TintFX implements ImageFX {

	private int x;
	private int y;

	private int WIDTH;
	private int HEIGHT;

	private BufferedImage spriteImage;		// image for sprite effect
	private BufferedImage copy;			// copy of image

	private boolean completed;

	Graphics2D g2;

	int tint, tintChange;				// to alter the brightness of the image

	public TintFX (int xPos, int yPos, int width, int height, String imagePath) {

		x = xPos;
		y = yPos;

		WIDTH = width;
		HEIGHT = height;

		tint = 0;				// range is 0 to 255; negative values darken the
							// image and positive values brighten the image
 
		tintChange = 255;				// increase of tint in each update

		spriteImage = ImageManager.loadBufferedImage(imagePath);

		completed=false;
	}


	private int truncate (int colourValue) {	// keeps colourValue within [0..255] range
		if (colourValue > 255)
			return 255;

		if (colourValue < 0)
			return 0;

		return colourValue;
	}


	private int applyTint (int pixel) {

    	int alpha, red, green, blue;
		int newPixel;
		
		alpha = (pixel >> 24) & 255;
		red = (pixel >> 16) & 255;
		green = (pixel >> 8) & 255;
		blue = pixel & 255;

		// Increase the value of the red component based on the value of tint

		red = red + tint;

		// Check the boundaries for 8-bit red component [0..255]

		red = truncate (red);
		
		newPixel = blue | (green << 8) | (red << 16) | (alpha << 24);

		return newPixel;
	}


	public void draw (Graphics2D g2) {

		if(copy==null)
			copy=ImageManager.copyImage(spriteImage); // make copy of image for brightness effect
		
		if(copy==null) return;
		
		int imWidth = copy.getWidth();
		int imHeight = copy.getHeight();

    		int [] pixels = new int[imWidth * imHeight];
    		copy.getRGB(0, 0, imWidth, imHeight, pixels, 0, imWidth);

		for (int i=0; i<pixels.length; i++) {
			pixels[i] = applyTint(pixels[i]);
		}

    		copy.setRGB(0, 0, imWidth, imHeight, pixels, 0, imWidth);	

		g2.drawImage(copy, x, y, WIDTH, HEIGHT, null);

	}


	public Rectangle2D.Double getBoundingRectangle() {
		return new Rectangle2D.Double (x, y, WIDTH, HEIGHT);
	}


	public void update() {				// modify brightness (-255 to 255)
	
		tint = tint + tintChange;

		if (tint > 255) {
			completed=true;
			tint=0;
		}		
	}

	public boolean isCompleted(){
		return completed;
	}

	public int getTint(){
		return tint;
	}
}