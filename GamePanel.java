import javax.swing.JPanel;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class GamePanel extends JPanel implements Runnable {

	private SoundManager soundManager;
	private boolean isRunning;
	private boolean isPaused;

	private ArrayList<Rock> rocks;
	private ArrayList<Enemy> enemies;

	private Thread gameThread;

	//private BufferedImage image;
	private SolidObjectManager soManager;

	private BufferedImage image1;
	private BufferedImage image2;
	private Image backgroundImage;

	private Player player;

	private boolean characterSelected;
	private CharacterSelection charSelect;
	private String character;

	private Background background;

	private GrasshopperAnimation animGrasshopper;
	private MushroomAnimation animMushroom;
	private GameWindow window;


	private LevelInitializer levelInitializer;

	private HealthDisplay healthDisplay;

	public GamePanel(GameWindow w) {

		this.window = w; 

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

		isRunning = false;
		isPaused = false;
		soundManager = SoundManager.getInstance();

		soManager = new SolidObjectManager();
		healthDisplay = new HealthDisplay(10, 10); // position it at the top left corner

		//backgroundImage = ImageManager.loadImage("images/landing.jpg");
    	//image = new BufferedImage(1200, 500, BufferedImage.TYPE_INT_RGB);

		image1 = new BufferedImage(1100, 600, BufferedImage.TYPE_INT_RGB);
		//image2 = new BufferedImage(1100, 600, BufferedImage.TYPE_INT_RGB);

	}

	public void createGameEntities() {

		player = new Player(this, 550, 350, character, soManager);
		rocks = new ArrayList<>();

		enemies = new ArrayList<>();

		levelInitializer = new LevelInitializer(this, soundManager, soManager, rocks, enemies, background, player);
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

			// if a player left clicks on a rock to destroy it
			if (rock.collidesWithPlayer(player) && player.justAttacked() && !rock.isDestroyed()) {

				rock.destroy();
				rock.setDestroyed(true);
				player.setJustAttacked(false);

				// setFX to start the disappearing effect once rock is destroyed
				rock.setFX(new DisappearFX(rock.getMapX(), rock.getMapY(), rock.getWidth(), rock.getHeight(),
						rock.getRockImageString(), background, 10));
			}

			rock.updateFX(); // update rock FX if any going on
			if (rock.isDisappearCompleted()) { // if rock is destroyed and the effect is completed
				rockIterator.remove(); // then remove it from the game since it's no longer needed
			}
		}

		Iterator<Enemy> enemyIterator = enemies.iterator();
		while (enemyIterator.hasNext()) { // loop through all enemies in the arrayList

			Enemy enemy = enemyIterator.next();

			enemy.move();

			if (enemy.getDX() != 0)
				enemy.start();
			enemy.update();

			//if the enemy collides with player and the player has attacked, then the enemy takes damage
			if (enemy.collidesWithPlayer(player) && player.attackRegistered() && enemy.isAlive()) {
				System.out.println("enemy HIT for " + player.getAttackDamage() + " damage");
				enemy.takeDamage(player.getAttackDamage());

				if(!player.isInvincible())
					player.takeDamage(enemy.getAttackDamage());
			}

			// if enemy is dead then remove it from the game
			if (!enemy.isAlive()) {
				enemyIterator.remove();
			}
		}

		// remove solid objects associated with rocks, if their rock was destroyed
		soManager.removeDestroyedRocks();
	}

	public void updatePlayer(int direction) {

		// get rectangle of player if they were to move in the direction
		Rectangle2D.Double futurePosition = player.getFutureBoundingRectangle(direction);

		// checking if the player would hit a solid if they moved in the direction
		Boolean wouldCollide = soManager.collidesWithSolid(futurePosition);

		// this makes the player walk through any solid object
		// wouldCollide = false; // for testing purposes, comment out when done

		if (player != null && !isPaused) {
			if (direction != 99) {

				if (!wouldCollide) { // if would not collide in the next move then move
					player.start();
					player.move(direction);
				}
			}

			if (direction == 99) { // direction of 99 means click on screen to attack
				player.attack();
			}
		}

		if (background != null && player != null && !isPaused && direction != 99) {
			if (!wouldCollide) { // if wouldn't collide with solid then move in the direction
				int batMovement = background.move(direction); // check whether the bat can start/stop moving in a new direction

				player.setDirections(batMovement);
				background.setDirections(player.move(direction)); // check if the bat is centred so the background can move
			}
		}
	}

	public void gameRender() {

		// draw the game objects on the image
		Graphics2D imageContext = (Graphics2D) image1.getGraphics();

		background.draw(imageContext);
		//imageContext1.drawImage(backgroundImage, 0, 0, null);	// draw the background image

		//Graphics2D imageContext = (Graphics2D) image2.getGraphics();

		if (background != null)
			background.draw(imageContext);

		if (soManager != null)
			soManager.draw(imageContext);

		if(rocks !=null){
			for (int i=0; i<rocks.size(); i++)
				rocks.get(i).draw(imageContext);
		}

		if (enemies != null) {
			for (int i = 0; i < enemies.size(); i++)
				enemies.get(i).draw(imageContext);
		}

		if (player != null)
			player.draw(imageContext);

		if (healthDisplay != null)
			healthDisplay.draw(imageContext, player.getHealth(), getWidth());

		Graphics2D g2 = (Graphics2D) getGraphics(); // get the graphics context for the panel

		//g2.drawImage(image1, 0, 0, 1100, 600, null);
		g2.drawImage(image1, 0, 0, 1100, 600, null);

		//imageContext1.dispose();
		imageContext.dispose();
		g2.dispose();
	}

	public void startGame() { // initialise and start the game thread

		if (gameThread == null && characterSelected) {
			createGameEntities();
			levelInitializer.initLevelOne();
			gameThread = new Thread(this);
			gameThread.start();

		}

	}

	public void startNewGame() { // initialise and start a new game thread

		isPaused = false;

		if (gameThread == null || !isRunning) {
			soundManager.playClip("start", false);
			// soundManager.playClip ("background", true);
			createGameEntities();
			levelInitializer.initLevelThree();
			gameThread = new Thread(this);
			gameThread.start();
		}
	}

	public void setCharacterSelected(boolean characterSelected){
		if(!characterSelected){
			window.setStartGameComponentsVisible(false);
		}
		else{
			window.setStartGameComponentsVisible(true);
		}
		
	}
	// method sets which character the player will be using
	public void setCharacter(String character) {
		this.character = character;
		System.out.println("Character selected: " + this.character);
		// Remove the SelectCharacter panel
		remove(charSelect);

		characterSelected = true;
		setCharacterSelected(characterSelected);
		// Redraw the GamePanel
		revalidate();
		repaint();
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

	public void spawnRocks(int num, int x1, int x2, int y1, int y2) {
		for (int i = 0; i < num; i++) {
			int x = (int) (Math.random() * (x2 - x1 + 1) + x1); // random x coordinate within the range
			int y = (int) (Math.random() * (y2 - y1 + 1) + y1); // random y coordinate within the range

			boolean onSolid = soManager.onSolidObject(x, y, 30, 30);
			boolean spawn = true;

			int numTries = 0;
			while (onSolid) { // if the rock is on a solid object then keep generating new coordinates until
								// it's not on a solid object
				x = (int) (Math.random() * (x2 - x1 + 1) + x1);
				y = (int) (Math.random() * (y2 - y1 + 1) + y1);
				onSolid = soManager.onSolidObject(x, y, 30, 30);

				if (numTries > 1000) { // if it's tried 1000 times to find a new location then just break out of the
										// loop
					spawn = false;
					break;
				}

				numTries++;
			}
			numTries = 0;

			if (spawn) { // spawn will be false if there are too many solid objects to spawn the rock
				Rock rock;
				int randomValue = new Random().nextInt(100); // Generate a random number between 0 and 99

				if (randomValue < 75) { // 75% chance
					rock = new Rock(this, x, y, background);
				} else if (randomValue < 88) { // 13% chance
					rock = new CopperRock(this, x, y, background);
				} else if (randomValue < 96) { // 8% chance
					rock = new IronRock(this, x, y, background);
				} else if (randomValue < 99) { // 3% chance
					rock = new GoldRock(this, x, y, background);
				} else { // 1% chance
					rock = new DiamondRock(this, x, y, background);
				}

				rocks.add(rock);

				// adds a solid object for each rock and associates the rock with the object
				// this is so rocks don't spawn on each other and so that players can't walk
				// through rocks
				SolidObject s = new SolidObject(x, y, i, i, getBackground(), onSolid, background, rock);
				soManager.addSolidObject(s);
			}
		}
	}

	public void setBackground(Background bg) {
		background = bg;
	}

}