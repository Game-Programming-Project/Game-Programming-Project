import javax.swing.JPanel;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Iterator;

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

		image = new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);
	}

	public void createGameEntities() {

		background = new Background(this, "images/Level1MapTest.png", 96, 220, 30);

		player = new Player(this, 190, 180, character);

		rocks = new ArrayList<>();
		rocks.add(new Rock(this, 823, 400, background));
		//rocks.add(new Rock(this, 87, 134, background));

		enemies = new ArrayList<>();
		enemies.add(new Bomber(this, 87, 134, background, player));

		animGrasshopper = new GrasshopperAnimation();
		animMushroom = new MushroomAnimation();
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
		animGrasshopper.update();
		animMushroom.update();
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
			enemy.start();
			enemy.update();

			//if enemy is a beeAnimation then call the status() method
		}

	}

	public void updatePlayer(int direction) {

		if (player != null && !isPaused) {

			if (direction != 99) {
				player.start();
				player.move(direction);
				// System.out.println("walk.update(direction) called "+direction);
			}

			if (direction == 99) { // direction of 99 means click on screen to attack
				player.attack();
			}
		}

		if (background != null && player != null) {
			int batMovement = background.move(direction); // check whether the bat can start/stop moving in a new
															// direction

			player.setDirections(batMovement);
			background.setDirections(player.move(direction)); // check if the bat is centred so the background can move
		}

	}

	public void gameRender() {

		// draw the game objects on the image

		Graphics2D imageContext = (Graphics2D) image.getGraphics();

		background.draw(imageContext);

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

		g2.drawImage(image, 0, 0, 400, 400, null);

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

		if (animGrasshopper != null) {
			animGrasshopper.start();
		}

		if (animMushroom != null) {
			animMushroom.start();
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