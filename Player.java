import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.HashSet;

public class Player {

	private Animation walkAnimationRight;
	private Animation walkAnimationLeft;
	private Animation walkAnimation;

	private Animation attackAnimationRight;
	private Animation attackAnimationLeft;
	private Animation attackAnimation;

	private int x, y;

	private int width, height;
	private int attackWidth, attackHeight;

	private GamePanel panel;

	private int dx;
	private int dy;

	private Image standImageRight;
	private Image standImageLeft;
	private Image standImage;

	private String characterType;

	private HashSet directions;

	private Boolean attacking;
	private Boolean justAttacked;
	private Boolean attackRegistered;

	private SolidObjectManager soManager;
	private SolidObject solidObject;

	private int centerX;
	private int centerY;

	private int attackDamage;
	private int health;

	public Player(GamePanel p, int xPos, int yPos, String cType, SolidObjectManager soManager) {

		directions = new HashSet<>(3);

		walkAnimationRight = new Animation(false);
		walkAnimationLeft = new Animation(false);
		walkAnimation = new Animation(false);

		attackAnimationRight = new Animation(false);
		attackAnimationLeft = new Animation(false);
		attackAnimation = new Animation(false);

		characterType = cType;

		panel = p;

		setDimensions();

		this.soManager = soManager;

		x = centerX = xPos;
		y = centerY = yPos;

		dx = 8;
		dy = 8;

		// load images from strip files
		loadImages();
		loadAllAnimations();

		attacking = false;
		justAttacked = false;
		attackRegistered = false;
		solidObject = null;

		attackDamage = 5;
		health = 10;
	}

	public void start() {
		if (walkAnimation.isStillActive())
			return;

		// System.out.println("start");
		walkAnimation.start();
	}

	public void update() {

		if (!walkAnimation.isStillActive() && !attackAnimation.isStillActive()) { // if animations are not active no
																					// need to update
			if (attacking)
				justAttacked = true; // just finished attacking so set to true

			attacking = false;
			return;
		}

		// set to false every time update is called cause didn't just finish attacking
		justAttacked = false; // need this or else it will falsely think it just finished attacking

		if (attackAnimation.isStillActive()) { // if attack animation is active update it
			attacking = true;
			attackAnimation.update();
			return;
		}

		walkAnimation.update();

	}

	public boolean attack() {
		if (attackAnimation.isStillActive()) // if an attack is currently going on return, else start the animation
			return false; // indicates that attack did not go off
		else
			attacking = true;

		
		attackAnimation.start();
		attackRegistered = false;

		return true; // attack went off
	}

	public int move(int direction) {

		if(soManager!=null)
			solidObject = collidesWithSolid(); // if the player collides with a solid object (map boundary) this will be
											// !=null

		if (!walkAnimation.isStillActive())
			return 0;

		if (attackAnimation.isStillActive()) // dont move if attacking
			return 0;

		walkAnimation.update();

		if (!panel.isVisible())
			return 0;

		if (direction == 1) { // left
			int oldX = x;
			standImage = standImageLeft;
			walkAnimation = walkAnimationLeft;
			attackAnimation = attackAnimationLeft;

			if (directions.contains(Integer.valueOf(1))) { // can move left
				x = x - dx;
				if (x < 33)
					x = 33;

				if (x <= centerX && oldX > centerX) { // check if was moving left and reaches centre
					x = centerX;// x = 190; // reposition at centre
					directions.remove(Integer.valueOf(1)); // stop moving left
					directions.remove(Integer.valueOf(2)); // and right
					return 10; // background can start scrolling left and right
				}

				else if (x < 190)
					return -1; // don't move background left
			}

			else
				return 1; // bat can't move left, let background scroll
		} else if (direction == 2) { // right
			int oldX = x;
			standImage = standImageRight;
			walkAnimation = walkAnimationRight;
			attackAnimation = attackAnimationRight;

			if (directions.contains(Integer.valueOf(1))) { // can move right
				x = x + dx; // move right
				if (x + width > panel.getWidth() - 33)
					x = panel.getWidth() - width - 33;

				if (x >= centerX && oldX < centerX) { // check if was moving left and reaches centre
					x = centerX;// x = 190; // reposition at centre
					directions.remove(Integer.valueOf(1)); // stop moving left
					directions.remove(Integer.valueOf(2)); // and right
					return 10; // background can start scrolling left and right
				} else if (x > 190)
					return -2; // don't move background right
			}

			else
				return 2; // bat can't move right, let background scroll
		} else if (direction == 3) { // up
			int oldY = y;

			if (directions.contains(Integer.valueOf(3))) { // can move up
				y = y - dy; // move up
				if (y < 82)
					y = 82;

				if (y <= centerY && oldY > centerY) { // check if was moving up and reaches centre
					y = centerY;// y = 180; // reposition at centre
					directions.remove(Integer.valueOf(3)); // stop moving up
					directions.remove(Integer.valueOf(4)); // and down
					return 30; // background can start scrolling up and down
				}

				else if (y < 180)
					return -3; // don't move background up
			} else
				return 3; // bat can't move up, let background scroll
		} else if (direction == 4) { // down
			int oldY = y;
			if (directions.contains(Integer.valueOf(3))) {
				y = y + dy; // move down
				if (y + height > panel.getHeight() - 33)
					y = panel.getHeight() - height - 33;

				if (y >= centerY && oldY < centerY) { // check if was moving up and reaches centre
					y = centerY;// y = 180; // reposition at centre
					directions.remove(Integer.valueOf(3)); // stop moving up
					directions.remove(Integer.valueOf(4)); // and down
					return 30; // background can start scrolling up and down
				}

				else if (y > 180)
					return -4; // don't move background down
			} else
				return 4; // bat can't move down, let background scroll
		}

		return 0;

	}

	public void setDirections(int direction) {
		if (direction == 1 && directions.contains(Integer.valueOf(1))) // already moved left so can move right (back to centre)
			directions.add(Integer.valueOf(2));
			
		else if (direction == 2 && directions.contains(Integer.valueOf(2))) // already moved right so can move left (back to centre)
			directions.add(Integer.valueOf(1));

		else if (direction == 3 && directions.contains(Integer.valueOf(3))) // already moved up so can move down (back to centre)
			directions.add(Integer.valueOf(4));
		else if (direction == 4 && directions.contains(Integer.valueOf(4))) // already moved down so can move up (back to centre)

		directions.add(Integer.valueOf(3));

		if (direction > 0) { // new direction the bat can move in
			directions.add(Integer.valueOf(direction));
			System.out.println("In bat added: " + direction);
		}

	}

	public void draw(Graphics2D g2) {

		if (!walkAnimation.isStillActive() && !attackAnimation.isStillActive()) // if animations are not active then
																				// draw standing image instead
			g2.drawImage(standImage, x, y, width, height, null);

		// if(attackAnimation.isStillActive())
		// g2.drawImage(attackAnimation.getImage(), x, y, width, height, null);

		if (attackAnimation.isStillActive())
			g2.drawImage(attackAnimation.getImage(), x, y, attackWidth, attackHeight, null);

		if (walkAnimation.isStillActive())
			g2.drawImage(walkAnimation.getImage(), x, y, width, height, null);
	}

	private void loadAttackAnimations() { // loading animation for attacking
		attackAnimationRight = loadAnimation(
				"images/Player/" + characterType + "/" + characterType + "_attackRight.png");
		attackAnimationLeft = loadAnimation("images/Player/" + characterType + "/" + characterType + "_attackLeft.png");

		// attackAnimationRight =
		// loadAnimation("images/Player/"+characterType+"/"+characterType+"_attackRightTest.png");
		// attackAnimationLeft =
		// loadAnimation("images/Player/"+characterType+"/"+characterType+"_attackLeftTest.png");

        attackAnimation = attackAnimationRight;
    }

    private void loadWalkAnimations() {
		walkAnimationRight = loadAnimation("images/Player/"+characterType+"/"+characterType+"_walkRight.png");
		walkAnimationLeft = loadAnimation("images/Player/"+characterType+"/"+characterType+"_walkLeft.png");
	
        walkAnimation = walkAnimationRight;
    }

	private void loadAllAnimations() {
		loadWalkAnimations();
		loadAttackAnimations();
	}

	private Animation loadAnimation(String stripFilePath) {

		Animation Animation = new Animation(false);

		Image stripImage = ImageManager.loadImage(stripFilePath);

		int imageWidth = (int) stripImage.getWidth(null) / 6;
		int imageHeight = stripImage.getHeight(null);

		for (int i = 0; i < 6; i++) {

			BufferedImage frameImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = (Graphics2D) frameImage.getGraphics();

			g.drawImage(stripImage,
					0, 0, imageWidth, imageHeight,
					i * imageWidth, 0, (i * imageWidth) + imageWidth, imageHeight,
					null);

			Animation.addFrame(frameImage, 70);
		}

		return Animation;
	}

    private void loadImages(){
        standImageRight = ImageManager.loadImage("images/Player/"+characterType+"/"+characterType+"_standRight.png");
		standImageLeft = ImageManager.loadImage("images/Player/"+characterType+"/"+characterType+"_standLeft.png");

		standImage=standImageRight;
    }

	private void setDimensions() {
		// player 1 is 27x38 walking and standing
		// and 38x38 attacking
		if (characterType == "1") {
			width = 57;
			height = 80;

			attackWidth = 76;
			attackHeight = 80;
		}

		// player 2 is 32x38 walking and standing
		// and 38x38 attacking
		if (characterType == "2") {
			width = 68;
			height = 80;

			attackWidth = 76;
			attackHeight = 80;
		}

		// player 3 is 27x38 walking and standing
		// and 33x38 attacking
		if (characterType == "3") {
			width = 57;
			height = 80;

			attackWidth = 66;
			attackHeight = 80;
		}
	}

    public Rectangle2D.Double getBoundingRectangle() {
		int offset = 10; // used to make player range bigger, needed for collision detection

		// if(characterType=="1" || characterType=="3")
		// 	offset = 10;
		// else
		// 	offset = 0;

		return new Rectangle2D.Double(x, y, width + offset, height);
	}

	//method used in detecting if player will collide with a solid object
	public Rectangle2D.Double getFutureBoundingRectangle(int direction){

		int futureX = x, futureY = y;

		if (direction == 1)
			futureX = x - dx;
		else if (direction == 2)
			futureX = x + dx;
		else if (direction == 3)
			futureY = y - dy;
		else if (direction == 4)
			futureY = y + dy;

		return new Rectangle2D.Double(futureX, futureY, width, height);
	}

	public SolidObject collidesWithSolid() {
		Rectangle2D.Double myRect = getBoundingRectangle();
		return soManager.collidesWith(myRect);
	}

	public void setAttackDamage(int a){
		attackDamage = a;
	}

	public void setJustAttacked(boolean a) {
		justAttacked = a;
	}

	public boolean attackRegistered() {
		if (!attackRegistered) {
            attackRegistered = true;
            return true;
        }

		return false;
	}

	public boolean justAttacked() {
		return justAttacked;
	}

	public boolean isAttacking() {
		return attacking;
	}

	public int getAttackDamage() {
		return attackDamage;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth(){
		return width;
	}

	public int getHeight(){
		return height;
	}

	public int getHealth(){
		return health;
	}
}