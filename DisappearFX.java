import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class DisappearFX implements ImageFX {

	private int x ,y;
	private int mapX, mapY;

	private int WIDTH;
	private int HEIGHT;

	private BufferedImage spriteImage;		// image for sprite effect
	private BufferedImage copy;			// copy of image

	Graphics2D g2;

	int time, timeChange;				// to control when the image is grayed
	int alpha, alphaChange;				// alpha value (for alpha transparency byte)

	private boolean isCompleted;

	private Background bg;

	public DisappearFX (int xPos, int yPos, int width, int height, String image, Background bg) {
		time = 0;				// range is 0 to 10
		timeChange = 1;				// set to 1

		alpha = 255;				// set to 255 (fully opaque)
		alphaChange = 20;			// how to update alpha in game loop

		spriteImage = ImageManager.loadBufferedImage(image);
		copy = ImageManager.copyImage(spriteImage);		
							//  make a copy of the original image

		isCompleted = false;

		this.bg=bg;
		this.mapX=xPos;
		this.mapY=yPos;
		this.WIDTH=width;
		this.HEIGHT=height;
	}

	public DisappearFX(int xPos, int yPos, int width, int height, String image, Background bg, int alphaChange) {
		this(xPos, yPos, width, height, image, bg); 
		this.alphaChange = alphaChange; 
	}


	public void draw (Graphics2D g2) {

		if(copy==null)
			copy=ImageManager.copyImage(spriteImage);
		
		if(copy==null) return;
		
		int imWidth = copy.getWidth();
		int imHeight = copy.getHeight();

    		int [] pixels = new int[imWidth * imHeight];
    		copy.getRGB(0, 0, imWidth, imHeight, pixels, 0, imWidth);

    		int a, red, green, blue, newValue;

		for (int i=0; i<pixels.length; i++) {

			a = (pixels[i] >> 24);
			red = (pixels[i] >> 16) & 255;
			green = (pixels[i] >> 8) & 255;
			blue = pixels[i] & 255;

/*
			newValue = blue | (green << 8) | (red << 16) | (alpha << 24);
			pixels[i] = newValue;
*/

				
			if (a != 0) {
				newValue = blue | (green << 8) | (red << 16) | (alpha << 24);
				pixels[i] = newValue;
			}


		}
  
    		copy.setRGB(0, 0, imWidth, imHeight, pixels, 0, imWidth);	
		updateScreenCoordinates();
		g2.drawImage(copy, x, y, WIDTH, HEIGHT, null);

	}


	public Rectangle2D.Double getBoundingRectangle() {
		return new Rectangle2D.Double (x, y, WIDTH, HEIGHT);
	}


	public void update() {				// modify time and change the effect if necessary
	
		alpha = alpha - alphaChange;

		if(alpha<10){
			alpha=10;
			isCompleted=true;
		}
	}

	public boolean isCompleted(){
		return isCompleted;
	}

	public void setAlphaChange(int a){
		alphaChange=a;
	}

	public void updateScreenCoordinates(){ //meant to be called right before the entity is drawn to the screen
        int bgX = bg.getbg1X();
        int bgY = bg.getbg1Y();

        if(bgX <0)
            bgX *= -1;
        if(bgY <0)
            bgY *= -1;

        x = mapX - bgX;
        y = mapY - bgY;
    }

}