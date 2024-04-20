import javax.swing.JPanel;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.geom.Rectangle2D;


public class GamePanel extends JPanel implements Runnable {

	private static int NUM_ALIENS = 3;

	private SoundManager soundManager;
	private Alien[] aliens;
	private boolean alienDropped;
	private boolean isRunning;
	private boolean isPaused;

	private ArrayList<Rock> rocks;
	private ArrayList<Enemy> enemies;

	private Thread gameThread;

	private BufferedImage image;
	// private Image backgroundImage;
	private SolidObjectManager soManager;

	private Player player;

	private boolean characterSelected;
	private CharacterSelection charSelect;
	private String character;

	private Background background;

	private GrasshopperAnimation animGrasshopper;
	private MushroomAnimation animMushroom;

	public GamePanel() {

		characterSelected = false;

		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		charSelect = new CharacterSelection(this);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;

		add(charSelect, gbc);

		aliens = null;
		alienDropped = false;
		isRunning = false;
		isPaused = false;
		soundManager = SoundManager.getInstance();

		// backgroundImage = ImageManager.loadImage ("images/Background.jpg");

		image = new BufferedImage(1100, 700, BufferedImage.TYPE_INT_RGB);
	}

	public void createGameEntities() {

		background = new Background(this, "images/Level1MapTest.png", 96, 360, 80);

		soManager = new SolidObjectManager(background);
		soManager.initLevelOne();
		soManager.setAllObjectsVisible(false);
    
    	player = new Player(this, 550, 350, character, soManager);

		rocks = new ArrayList<>();
		rocks.add(new Rock(this, 823, 960, background));
		// rocks.add(new Rock(this, 87, 134, background));

		enemies = new ArrayList<>();
		enemies.add(new Bomber(this, 720, 960, background, player));
		enemies.add(new BeeAnimation(this, 720, 930, background));
		enemies.add(new GrasshopperAnimation(this, 720, 990, background));
		enemies.add(new MushroomAnimation(this, 720, 960, background));

	}

	public void run() {
		try {
			isRunning = true;
			while (isRunning) {
				if (!isPaused)
					gameUpdate();
				gameRender();
				Thread.sleep(50);
			}
		} catch (InterruptedException e) {
		}
	}

	public void gameUpdate() {

		if (player != null)
			player.update(); // needed for animations to run

		// iterator is needed to avoid ConcurrentModificationException
		Iterator<Rock> rockIterator = rocks.iterator();
		while (rockIterator.hasNext()) { // loop through all rocks in the arrayList

			Rock rock = rockIterator.next();

			if (rock.collidesWithPlayer(player) && player.justAttacked() && !rock.isDestroyed()) {

				rock.destroy();
				rock.setDestroyed(true);
				rockIterator.remove(); // Use iterator's remove method to remove the destroyed rock from the list
				player.setJustAttacked(false);
			}

		}

		Iterator<Enemy> enemyIterator = enemies.iterator();
		while (enemyIterator.hasNext()) { // loop through all enemies in the arrayList

			Enemy enemy = enemyIterator.next();
			enemy.move();

			if (enemy.getDX() != 0)
				enemy.start();
			enemy.update();

		}

	}

	public void updatePlayer(int direction) {

		Rectangle2D.Double futurePosition = player.getFutureBoundingRectangle(direction);

		Boolean wouldCollide = soManager.collidesWithSolid(futurePosition); 
		System.out.println("Would collide: " + wouldCollide);

		if (player != null && !isPaused) {
			if (direction != 99) { //if not colliding with a solid then move

				if(!wouldCollide){ // if would not collide in the next move then move
					player.start();
					player.move(direction);
				}
			}

			if (direction == 99) { // direction of 99 means click on screen to attack
				player.attack();
			}
		}

		if (background != null && player != null && !isPaused && direction!=99) {
			if(!wouldCollide){ //if wouldn't collide with solid then move in the direction
				int batMovement = background.move(direction); // check whether the bat can start/stop moving in a new
																// direction

				player.setDirections(batMovement);
				background.setDirections(player.move(direction)); // check if the bat is centred so the background can move
			}
		}
	}

	public void gameRender() {

		// draw the game objects on the image

		Graphics2D imageContext = (Graphics2D) image.getGraphics();

		background.draw(imageContext);

		if (soManager != null) {
			soManager.draw(imageContext);
		}


		if (rocks != null) {
			for (int i = 0; i < rocks.size(); i++)
				rocks.get(i).draw(imageContext);
		}

		if (enemies != null) {
			for (int i = 0; i < enemies.size(); i++)
				enemies.get(i).draw(imageContext);
		}

		if (player != null) {
			player.draw(imageContext);
		}

		Graphics2D g2 = (Graphics2D) getGraphics(); // get the graphics context for the panel

		g2.drawImage(image, 0, 0, 1100, 700, null);

		imageContext.dispose();
		g2.dispose();
	}

	public void startGame() { // initialise and start the game thread

		if (gameThread == null && characterSelected) {
			// soundManager.playClip ("background", true);
			createGameEntities();
			gameThread = new Thread(this);
			gameThread.start();

		}

	}

	public void startNewGame() { // initialise and start a new game thread

		isPaused = false;

		if (gameThread == null || !isRunning) {
			// soundManager.playClip ("background", true);
			createGameEntities();
			gameThread = new Thread(this);
			gameThread.start();

		}
	}

	public void pauseGame() { // pause the game (don't update game entities)
		if (isRunning) {
			if (isPaused)
				isPaused = false;
			else
				isPaused = true;
		}
	}

	public void endGame() { // end the game thread
		isRunning = false;
		// soundManager.stopClip ("background");
	}

	public void shootCat() {
		// animation3.start();
	}

	// method sets which character the player will be using
	public void setCharacter(String character) {
		this.character = character;
		System.out.println("Character selected: " + this.character);
		// Remove the SelectCharacter panel
		remove(charSelect);

		characterSelected = true;
		// Redraw the GamePanel
		revalidate();
		repaint();
	}
}