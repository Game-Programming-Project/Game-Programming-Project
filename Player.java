import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Player {

    Animation walkAnimationRight;
	Animation walkAnimationLeft;
	Animation walkAnimation;

    Animation attackAnimationRight;
    Animation attackAnimationLeft;
	Animation attackAnimation;

    private int x;		// x position of animation
	private int y;		// y position of animation

	private int width;
	private int height;

    private GamePanel panel;

	private int dx;		// increment to move along x-axis
	private int dy;		// increment to move along y-axis

	private Image standImageRight;
	private Image standImageLeft;
	private Image standImage;

    private String characterType;
    
    public Player(GamePanel p, int xPos, int yPos, String cType) {

		walkAnimationRight = new Animation(false);	
		walkAnimationLeft = new Animation(false);	
		walkAnimation = new Animation(false);

        attackAnimationRight = new Animation(false);;
        attackAnimationLeft = new Animation(false);;
		attackAnimation = new Animation(false);

        characterType = cType;

        panel = p;

        width=height=48;
        
        x = xPos;		// set x position
        y = yPos;		// set y position

        dx = 5;		// increment to move along x-axis
        dy = 5;	// increment to move along y-axis

		// load images from strip files
		loadImages();
		loadAnimations();

		

		
	}

    public void start() {
		if (walkAnimation.isStillActive())
			return;
			
		System.out.println("start");
		walkAnimation.start();
	}

	
	public void update() {

		if(!walkAnimation.isStillActive() && !attackAnimation.isStillActive()) // if animations are not active no need to update
			return;

		if(attackAnimation.isStillActive()){ // if attack animation is active update it
			System.out.println("attack update");
			attackAnimation.update();
			return;
		}

		walkAnimation.update();
		
	}

    public void attack() {
		if (attackAnimation.isStillActive()) // if an attack is currently going on return, else start the animation
			return;

		System.out.println("attack");
		attackAnimation.start();
	}

    public void move(int direction) {
		if (!walkAnimation.isStillActive())
			return;
        
        if(attackAnimation.isStillActive()) //dont move if attacking
            return;

            walkAnimation.update();
		
        if (direction == 1) {
			x = x - dx;
	     
			standImage = standImageLeft;
			walkAnimation = walkAnimationLeft;
            attackAnimation = attackAnimationLeft;          
			if (x < -30)			// move to right of GamePanel
				x = 380;
		}
		else 
		if (direction == 2) {
			x = x + dx;
           
		    standImage = standImageRight;
		    walkAnimation = walkAnimationRight;
            attackAnimation = attackAnimationRight;
			if (x > 380)			// move to left of GamePanel
				x = -30;
		}
		else
		if (direction == 3) {
			y = y - dy;
			if (y < 0)			// move to bottom of GamePanel
				y = 0;
		}
		else
		if (direction == 4) {
			y = y + dy;
			
		}
	}


	public void draw(Graphics2D g2) {
		
		if(!walkAnimation.isStillActive() && !attackAnimation.isStillActive())
			g2.drawImage(standImage, x, y, width, height, null);

		if(attackAnimation.isStillActive())
			g2.drawImage(attackAnimation.getImage(), x, y, width, height, null);

		if (walkAnimation.isStillActive())
			g2.drawImage(walkAnimation.getImage(), x, y, width, height, null);
	}


    private void loadAttackAnimation(){
        // loading animation for attacking
		Image stripImage = ImageManager.loadImage("images/Player/"+characterType+"/"+characterType+"_attackRight.png");

		int imageWidth = (int) stripImage.getWidth(null) / 6;
		int imageHeight = stripImage.getHeight(null);

		for (int i=0; i<6; i++) {

			BufferedImage frameImage = new BufferedImage (imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = (Graphics2D) frameImage.getGraphics();
     
			g.drawImage(stripImage, 
					0, 0, imageWidth, imageHeight,
					i*imageWidth, 0, (i*imageWidth)+imageWidth, imageHeight,
					null);

            attackAnimationRight.addFrame(frameImage, 100);
		}


        stripImage = ImageManager.loadImage("images/Player/"+characterType+"/"+characterType+"_attackLeft.png");

        imageWidth = (int) stripImage.getWidth(null) / 6;
		imageHeight = stripImage.getHeight(null);

        for (int i=0; i<6; i++) {

			BufferedImage frameImage = new BufferedImage (imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = (Graphics2D) frameImage.getGraphics();
     
			g.drawImage(stripImage, 
					0, 0, imageWidth, imageHeight,
					i*imageWidth, 0, (i*imageWidth)+imageWidth, imageHeight,
					null);

            attackAnimationLeft.addFrame(frameImage, 100);
		}

        attackAnimation = attackAnimationRight;
    }

    private void loadWalkAnimation() {
        Image stripImage = ImageManager.loadImage("images/Player/"+characterType+"/"+characterType+"_walkRight.png");

		int imageWidth = (int) stripImage.getWidth(null) / 6;
		int imageHeight = stripImage.getHeight(null);

		for (int i=0; i<6; i++) {

			BufferedImage frameImage = new BufferedImage (imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = (Graphics2D) frameImage.getGraphics();
     
			g.drawImage(stripImage, 
					0, 0, imageWidth, imageHeight,
					i*imageWidth, 0, (i*imageWidth)+imageWidth, imageHeight,
					null);

            walkAnimationRight.addFrame(frameImage, 70);
		}

		stripImage = ImageManager.loadImage("images/Player/"+characterType+"/"+characterType+"_walkLeft.png");

		imageWidth = (int) stripImage.getWidth(null) / 6;
		imageHeight = stripImage.getHeight(null);

		for (int i=0; i<6; i++) {

			BufferedImage frameImage = new BufferedImage (imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = (Graphics2D) frameImage.getGraphics();
     
			g.drawImage(stripImage, 
					0, 0, imageWidth, imageHeight,
					i*imageWidth, 0, (i*imageWidth)+imageWidth, imageHeight,
					null);

            walkAnimationLeft.addFrame(frameImage, 70);
		}

        walkAnimation = walkAnimationRight;
    }

    private void loadAnimations(){
        loadWalkAnimation();
        loadAttackAnimation();
    
    }

    private void loadImages(){
        standImageRight = ImageManager.loadImage("images/Player/"+characterType+"/"+characterType+"_standRight.png");
		standImageLeft = ImageManager.loadImage("images/Player/"+characterType+"/"+characterType+"_standLeft.png");
		standImage=standImageRight;
    }

    public Rectangle2D.Double getBoundingRectangle() {

		return new Rectangle2D.Double (x, y, width, height);
	}
}
