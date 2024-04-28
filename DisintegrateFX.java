import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class DisintegrateFX implements ImageFX {
    private int x ,y;
	private int mapX, mapY;
	private int WIDTH ;		
	private int HEIGHT;		
    private int time, timeChange;				// to control when the image is grayed

	private BufferedImage spriteImage;		// image for sprite effect
	private BufferedImage copy;			// copy of image

	Graphics2D g2;

    private boolean isDisintegrated;
    private Background bg;

	public DisintegrateFX (int xPos, int yPos, int width, int height, String image, Background bg) {
		this.bg=bg;
		this.mapX=xPos;
		this.mapY=yPos;
		this.WIDTH=width;
		this.HEIGHT=height;


        spriteImage = ImageManager.loadBufferedImage(image);
		copy = ImageManager.copyImage(spriteImage);		
							//  make a copy of the original image

		isDisintegrated = false;

		time = 0;				// range is 0 to 70
		timeChange = 1;				// how to increment time in game loop

	}


  	public void eraseImageParts(BufferedImage im, int interval) {

    		int imWidth = im.getWidth();
    		int imHeight = im.getHeight();

    		int [] pixels = new int[imWidth * imHeight];
    		im.getRGB(0, 0, imWidth, imHeight, pixels, 0, imWidth);

		for (int i = 0; i < pixels.length; i = i + interval) {
      			pixels[i] = 0;    // make transparent (or black if no alpha)
		}
  
    		im.setRGB(0, 0, imWidth, imHeight, pixels, 0, imWidth);
            updateScreenCoordinates();
  	}


	public void draw (Graphics2D g2) {

		if (time == 10)
			eraseImageParts(copy, 11);
		else
		if (time == 20)
			eraseImageParts(copy, 7);
		else
		if (time == 30)
			eraseImageParts(copy, 5);
		else
		if (time == 40)
			eraseImageParts(copy, 3);
		else
		if (time == 50)
			eraseImageParts(copy, 2);
		else
		if (time == 60)
			eraseImageParts(copy, 1);
		else
		if (time == 70)
			copy = ImageManager.copyImage(spriteImage);

		g2.drawImage(copy, x, y, WIDTH, HEIGHT, null);

	}


	public Rectangle2D.Double getBoundingRectangle() {
		return new Rectangle2D.Double (x, y, WIDTH, HEIGHT);
	}


	public void update() {				// modify time
	
		time = time + timeChange;

		if (time > 70){
            time = 70;
            isDisintegrated = true;
        }		
			

	}

    public boolean isDisintegrated(){
        return isDisintegrated;
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