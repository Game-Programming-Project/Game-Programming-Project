import java.awt.Image;
import java.awt.Graphics2D;
import java.util.Random;
import java.awt.geom.Rectangle2D;

public class Enemy {

    protected Animation walkAnimationLeft;
    protected Animation walkAnimationRight;
    protected Animation walkAnimation;

    // images for when the enemy is standing still
    protected Image standImageRight;
    protected Image standImageLeft;
    protected Image standImage;

    protected int width, height;

    protected int x, y; // these are the coordinates of the enemy on the screen
    protected int dx, dy;
    protected int direction;

    protected int mapX, mapY; // these are the coordinates of the enemy on the map coordinates
    protected GamePanel gPanel;

    protected Background bg;

    protected SoundManager soundManager;

    protected Player player;

    protected int aggression; // how aggressive the enemy is, how likely it is to attack the player
    protected int health;
    private int attackDamage;

    protected Boolean isAlive;

    public Enemy(GamePanel gPanel, int mapX, int mapY, Background bg, Player player) {

        this.mapX = mapX;
        this.mapY = mapY;
        this.gPanel = gPanel;
        this.bg = bg;
        this.player = player;

        aggression = 1; // always chase player by default
        health = 10;

        soundManager = SoundManager.getInstance();

        walkAnimation = new Animation(false);
        walkAnimationLeft = new Animation(false);
        walkAnimationRight = new Animation(false);

        isAlive = true;
        attackDamage=1;
    }

    public void loadWalkAnimation() {
        // code to load the animation to walk left and right and store them in
        // walkAnimationLeft and walkAnimationRight
        // see loadWalkAnimation() method in player class for reference
    }

    public void start() {
        if (walkAnimation.isStillActive())
            return;

        walkAnimation.start();
    }

    public void update() {
        if (!walkAnimation.isStillActive()) // if the animation is not active, no need to update
            return;

        walkAnimation.update();

        if (health <= 0)
            isAlive = false;
    }

    public void move() {
        // code to move the enemy, add dy and dx to mapY and mapX when moving, not x and
        // y

    }

    public void chasePlayer() {
        int playerX = player.getX();
        int playerY = player.getY();

        // this code makes the enemy target the middle of the player sprite instead of
        // the top left
        playerX += player.getWidth() / 2;
        playerY += player.getHeight() / 2;

        Random rand = new Random();
        int random = rand.nextInt(aggression);

        updateScreenCoordinates();

        if (random == 0) {
            if (playerX > x) { // player is to the right
                mapX += dx;
            }

            if (playerX < x) { // player is to the left
                mapX -= dx;
            }

            if (playerY > y) { // player is below
                mapY += dy;
            }

            if (playerY < y) { // player is above
                mapY -= dy;
            }
        }

    }

    public void draw(Graphics2D g2) {

        updateScreenCoordinates();

        if (!walkAnimation.isStillActive()) // if walking animation not playing then draw standing iamge instead
            g2.drawImage(standImage, x, y, width, height, null);

        if (walkAnimation.isStillActive()) // draw walking animation if it's active
            g2.drawImage(walkAnimation.getImage(), x, y, width, height, null);
    }

    // this method is needed to ensure the entity stays where you want it to on the
    // background map coordinates
    // the x and y that is updated are the coordinates to be drawn to the screen
    public void updateScreenCoordinates() { // meant to be called right before the entity is drawn to the screen
        int bgX = bg.getbg1X();
        int bgY = bg.getbg1Y();

        // if bgX or bgY are negative then make them positive
        if (bgX < 0)
            bgX *= -1;
        if (bgY < 0)
            bgY *= -1;

        // positioning the entity on the screen relative to the background
        // this calculation makes it so that the entity stays on the map where it needs
        // to be
        x = mapX - bgX;
        y = mapY - bgY;
    }

    public int getDX() {
        return dx;
    }

    public void setAggression(int a) {
        aggression = a;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public boolean collidesWithPlayer(Player p) {
        Rectangle2D.Double myRect = getBoundingRectangle();
        Rectangle2D.Double playerRect = p.getBoundingRectangle();

        return myRect.intersects(playerRect);
    }

    public Rectangle2D.Double getBoundingRectangle() {
        return new Rectangle2D.Double(x, y, width, height);
    }

    public int getAttackDamage(){
        return attackDamage;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health < 0) {
            health = 0;
        }
    }

    // method used in detecting if player will collide with a solid object
    public Rectangle2D.Double getFutureBoundingRectangle() {

        int futureX = x, futureY = y;

        if (direction == 1) // walking left
            futureX = x - dx;
        else if (direction == 2) // walking right
            futureX = x + dx;
        else if (direction == 4) // walking down
            futureY = y + dy;
        else if (direction == 3) // walking up
            futureY = y - dy;

        return new Rectangle2D.Double(futureX, futureY, width, height);
    }

    public Rectangle2D.Double getFutureBoundingRectangle(int direction) {

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

}