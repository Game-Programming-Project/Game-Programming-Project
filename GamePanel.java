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
import java.util.List;
import java.awt.geom.Rectangle2D;
import java.util.Random;
import java.awt.Point;

public class GamePanel extends JPanel implements Runnable {

	private boolean isRunning;
	private boolean isPaused;

	private ArrayList<Rock> rocks;
	private ArrayList<Enemy> enemies;

	private Thread gameThread;

	private BufferedImage image;

	private SoundManager soundManager;
	private SolidObjectManager soManager;

	private LevelInitializer levelInitializer;
	private EntitySpawner entitySpawner;

	private Player player;

	private boolean characterSelected;
	private CharacterSelection charSelect;
	private String character;

	private Background background;

	private GrasshopperAnimation animGrasshopper;
	private MushroomAnimation animMushroom;

	private GameWindow window;

	private HealthDisplay healthDisplay;

	private String currentLevel;

	private List<Enemy> tempEnemies;

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

		currentLevel = "1";

		image = new BufferedImage(1100, 700, BufferedImage.TYPE_INT_RGB);
		soManager = new SolidObjectManager();
		healthDisplay = new HealthDisplay(10, 10); // position it at the top left corner
	}

	public void createGameEntities() {

		player = new Player(this, 550, 350, character, soManager);
		rocks = new ArrayList<>();
		enemies = new ArrayList<>();
		tempEnemies = new ArrayList<>();
		healthDisplay.setPlayer(player);
		healthDisplay.setMaxHealth(player.getHealth());

		entitySpawner = new EntitySpawner(this, soundManager, soManager, rocks, enemies, background, player);

		levelInitializer = new LevelInitializer(this, soundManager, soManager, rocks, enemies, background, player, entitySpawner);
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

				player.addMaterials(rock.getMaterialValue());
				player.addScore(rock.getScoreValue());

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

			// if the player attacked while enemy collides then dmg enemy
			if (enemy.collidesWithPlayer(player) && player.attackRegistered() && enemy.isAlive()) {

				//System.out.println("enemy HIT for " + player.getAttackDamage() + " damage");
				enemy.takeDamage(player.getAttackDamage());

				if (!player.isInvincible())
					player.takeDamage(enemy.getAttackDamage());
			}

			// if enemy collides with player damage player
			if (enemy.collidesWithPlayer(player) && enemy.isAlive()) {

				if (!player.isInvincible())
					player.takeDamage(enemy.getAttackDamage());
			}
			
			if((enemy instanceof RedBee) && !enemy.isAlive()){
				//spawn 3 TinyBees when red bee dies
				Point p = new Point(enemy.getMapX(), enemy.getMapY());
				tempEnemies.add(new TinyBee(this, p.x+30, p.y, background, player));
				tempEnemies.add(new TinyBee(this, p.x-30, p.y, background, player));
				tempEnemies.add(new TinyBee(this, p.x, p.y-30, background, player));
			}

			// if enemy is dead then remove it from the game
			if (!enemy.isAlive()) {
				player.addScore(enemy.getScoreValue());
				enemyIterator.remove();
			}
		}
		enemies.addAll(tempEnemies);
		tempEnemies.clear();

		// remove solid objects associated with rocks, if their rock was destroyed
		soManager.removeDestroyedRocks();
		window.updateScore(player.getScore());
	}

	public void updatePlayer(int direction) {

		// get rectangle of player if they were to move in the direction
		Rectangle2D.Double futurePosition = player.getFutureBoundingRectangle(direction);

		// checking if the player would hit a solid if they moved in the direction
		Boolean wouldCollide = soManager.collidesWithSolid(futurePosition);

		// this makes the player walk through any solid object
		wouldCollide = false; // for testing purposes, comment out when done

		if (player != null && !isPaused) {
			if (direction != 99 & direction != 88) {

				if (!wouldCollide) { // if would not collide in the next move then move
					player.start();
					player.move(direction);
				}
			}

			if (direction == 99) { // direction of 99 means click on screen to attack
				player.attack();
			}

			// right click on ladder
			if (direction == 88 && playerOnLadder()) {

				// send player to next level if right click on ladder
				currentLevel = levelInitializer.initNextLevel(currentLevel);

				if (!soundManager.isStillPlaying("ladderDown"))
					soundManager.playClip("ladderDown", false);
			}

			// right click on fruit
			if (direction == 88 && playerOnFruit() != null) {
				Rock r = playerOnFruit();

				player.heal(2);

				if (!soundManager.isStillPlaying("munch"))
					soundManager.playClip("munch", false);

				r.setFruitEaten(true);
			}
		}

		if (background != null && player != null && !isPaused && direction != 99 && direction != 88) {
			if (!wouldCollide) { // if wouldn't collide with solid then move in the direction
				int batMovement = background.move(direction); // check whether the bat can start/stop moving in a new
																// direction

				player.setDirections(batMovement);
				background.setDirections(player.move(direction)); // check if the bat is centred so the background can
																	// move
			}
		}
	}

	public void gameRender() {

		// draw the game objects on the image
		Graphics2D imageContext = (Graphics2D) image.getGraphics();

		if (background != null)
			background.draw(imageContext);

		if (soManager != null)
			soManager.draw(imageContext);

		if (rocks != null) {
			for (int i = 0; i < rocks.size(); i++)
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

		g2.drawImage(image, 0, 0, 1100, 700, null);

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
			// soundManager.playClip ("background", true);
			createGameEntities();
			levelInitializer.initLevelOne();
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

	public boolean playerOnLadder() {
		for (Rock rock : rocks) {
			if (rock.hasLadder() && rock.collidesWithPlayer(player)) {
				return true;
			}
		}
		return false;
	}

	public Rock playerOnFruit() {
		for (Rock rock : rocks) {
			if (rock.hasFruit() && rock.collidesWithPlayer(player)) {
				return rock;
			}
		}
		return null;

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

	public void setBackground(Background bg) {
		background = bg;
	}

	public String getCurrentLevel() {
		return currentLevel;
	}

	public void addEnemy(Enemy e) {
		enemies.add(e);
	}

	public void addRock(Rock r) {
		rocks.add(r);
	}

	public void setRocks(ArrayList<Rock> rocks) {
		this.rocks = rocks;
	}
}