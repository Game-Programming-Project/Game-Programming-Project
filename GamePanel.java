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

public class GamePanel extends JPanel implements Runnable {

	private SoundManager soundManager;
	private boolean isRunning;
	private boolean isPaused;

	private ArrayList<Rock> rocks;
	private ArrayList<Enemy> enemies;

	private Thread gameThread;

	private BufferedImage image;

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

		isRunning = false;
		isPaused = false;
		soundManager = SoundManager.getInstance();

		image = new BufferedImage(1100, 700, BufferedImage.TYPE_INT_RGB);
	}

	public void createGameEntities() {
		//note for level 3 offsetX: -90, offsetY: 400
		background = new Background(this, "images/Maps/Testing/Level3MapTest.png", 96, -90, 400);

		soManager = new SolidObjectManager(background);
		//soManager.initLevelOne();
		soManager.initLevelThree();
		soManager.setAllObjectsVisible(false);

		player = new Player(this, 550, 350, character, soManager);

		rocks = new ArrayList<>();
		rocks.add(new Rock(this, 1550, 1321, background));
		//spawnRocks(10,470,679,1170,1401);

		enemies = new ArrayList<>();
		enemies.add(new Shaman(this, 460, 1489, background, player, soManager));
		// enemies.add(new Bomber(this, 720, 960, background, player));
		// enemies.add(new BeeAnimation(this, 720, 930, background, player));
		// enemies.add(new GrasshopperAnimation(this, 720, 990, background, player));
		// enemies.add(new MushroomAnimation(this, 720, 960, background, player));

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

			//if a player left clicks on a rock to destroy it
			if (rock.collidesWithPlayer(player) && player.justAttacked() && !rock.isDestroyed()) {

				rock.destroy();
				rock.setDestroyed(true);
				player.setJustAttacked(false);

				//setFX to start the disappearing effect once rock is destroyed
				rock.setFX(new DisappearFX(rock.getMapX(), rock.getMapY(), rock.getWidth(), rock.getHeight(), rock.getRockImageString(), background, 10));
			}

			rock.updateFX(); // update rock FX if any going on
			if(rock.isDisappearCompleted()){ // if rock is destroyed and the effect is completed
				rockIterator.remove();	// then remove it from the game since it's no longer needed
			}
		}

		Iterator<Enemy> enemyIterator = enemies.iterator();
		while (enemyIterator.hasNext()) { // loop through all enemies in the arrayList

			Enemy enemy = enemyIterator.next();

			enemy.move();

			if (enemy.getDX() != 0)
				enemy.start();
			enemy.update();


			if(enemy instanceof Shaman){ // if enemy is a Shaman

			}

		}

		// remove solid objects associated with rocks, if their rock was destroyed
		soManager.removeDestroyedRocks(); 

	}

	public void updatePlayer(int direction) {

		//get rectangle of player if they were to move in the direction
		Rectangle2D.Double futurePosition = player.getFutureBoundingRectangle(direction);

		//checking if the player would hit a solid if they moved in the direction
		Boolean wouldCollide = soManager.collidesWithSolid(futurePosition);

		//this makes the player walk through any solid object
		//wouldCollide = false; // for testing purposes, comment out when done

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

	public void spawnRocks(int num, int x1, int x2, int y1, int y2){
		for(int i = 0; i < num; i++){
			int x = (int)(Math.random() * (x2 - x1 + 1) + x1); // random x coordinate within the range
			int y = (int)(Math.random() * (y2 - y1 + 1) + y1); // random y coordinate within the range

			boolean onSolid = soManager.onSolidObject(x, y, 30, 30);
			boolean spawn = true;

			int numTries = 0;
			while(onSolid){ // if the rock is on a solid object then keep generating new coordinates until it's not on a solid object
				x = (int)(Math.random() * (x2 - x1 + 1) + x1); 
				y = (int)(Math.random() * (y2 - y1 + 1) + y1); 
				onSolid = soManager.onSolidObject(x, y, 30, 30);

				if(numTries > 1000){ // if it's tried 1000 times to find a new location then just break out of the loop
					spawn = false;
					break;
				}

				numTries++;
			}
			numTries = 0;

			if(spawn){	//spawn will be false if there are too many solid objects to spawn the rock
				Rock rock = new Rock(this, x, y, background);
				rocks.add(rock);

				//adds a solid object for each rock and associates the rock with the object
				//this is so rocks don't spawn on each other and so that players can't walk through rocks
				SolidObject s = new SolidObject(x, y, i, i, getBackground(), onSolid, background, rock);
				soManager.addSolidObject(s);
			}
		}
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