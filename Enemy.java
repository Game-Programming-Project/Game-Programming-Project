import java.awt.Image;
import java.awt.Graphics2D;

public class Enemy {
	
	protected Animation walkAnimationLeft;
    protected Animation walkAnimationRight;
    protected Animation walkAnimation;

    //images for when the enemy is standing still
    protected Image standImageRight;
	protected Image standImageLeft;
	protected Image standImage;

    protected int width, height;

    protected int x, y; // these are the coordinates of the enemy on the screen
    protected int dx, dy;

    protected int mapX, mapY; // these are the coordinates of the enemy on the map coordinates
    protected GamePanel gPanel;

    protected Background bg;

    protected SoundManager soundManager;

	public Enemy(GamePanel gPanel, int mapX, int mapY, Background bg) {

        this.mapX = mapX;
        this.mapY = mapY;
        this.gPanel = gPanel;
        this.bg = bg;

        soundManager = SoundManager.getInstance();

		walkAnimation = new Animation(false);
        walkAnimationLeft = new Animation(false); 
        walkAnimationRight = new Animation(false);
	
	}

    public void loadWalkAnimation(){
        //code to load the animation to walk left and right and store them in walkAnimationLeft and walkAnimationRight
        //see loadWalkAnimation() method in player class for reference
    }

	public void start() {
		if (walkAnimation.isStillActive())
			return;
			
		walkAnimation.start();
	}

	
	public void update() {
		if (!walkAnimation.isStillActive()) //if the animation is not active, no need to update
			return;

        walkAnimation.update();
	}

    public void move(){
        //code to move the enemy, add dy and dx to mapY and mapX when moving, not x and y

    }

	public void draw(Graphics2D g2) {

        updateScreenCoordinates();

		if (!walkAnimation.isStillActive()) //if walking animation not playing then draw standing iamge instead
            g2.drawImage(standImage, x, y, width, height, null);

        if(walkAnimation.isStillActive()) // draw walking animation if it's active
		    g2.drawImage(walkAnimation.getImage(), x, y, width, height, null);
	}

    //this method is needed to ensure the entity stays where you want it to on the background map coordinates
    //the x and y that is updated are the coordinates to be drawn to the screen
    public void updateScreenCoordinates(){ //meant to be called right before the entity is drawn to the screen
        int bgX = bg.getbg1X();
        int bgY = bg.getbg1Y();

        //if bgX or bgY are negative then make them positive
        if(bgX <0)
            bgX *= -1;
        if(bgY <0)
            bgY *= -1;

        //positioning the entity on the screen relative to the background
        //this calculation makes it so that the entity stays on the map where it needs to be
        x = mapX - bgX;
        y = mapY - bgY;
    }

}