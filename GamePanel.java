import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.geom.Rectangle2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class GamePanel extends JPanel implements Runnable {

	private static final int initialTimeInSeconds = 900;	

	private boolean isRunning;
	private boolean isPaused;
	private boolean characterSelected;
	private boolean isGameOver;

	private ArrayList<Rock> rocks;
	private ArrayList<Enemy> enemies;

	private Thread gameThread;

	private BufferedImage image;

	private SoundManager soundManager;
	private SolidObjectManager soManager;

	private LevelInitializer levelInitializer;
	private EntitySpawner entitySpawner;

	private Player player;
	private Timer timer;

	private int remainingTime;
	
	private CharacterSelection charSelect;
	private String character;

	private Background background;

	private GameWindow window;

	private HealthDisplay healthDisplay;

	private int currentLevel;

	private List<Enemy> tempEnemies;
	private Chest chest;

	private int numEnemies;


	public GamePanel(GameWindow w) {

		this.window = w;
		characterSelected = false;

		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		this.setBackground(Color.DARK_GRAY);
		
		charSelect = new CharacterSelection(this);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;

		add(charSelect, gbc);

		
		isRunning = false;
		isPaused = false;
		isGameOver = false;
		chest = null;


		soundManager = SoundManager.getInstance();

		currentLevel = 1;
		numEnemies = -1;

		image = new BufferedImage(1100, 500, BufferedImage.TYPE_INT_RGB);
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

		levelInitializer = new LevelInitializer(this, soundManager, soManager, rocks, enemies, background, player,
				entitySpawner);
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
			if (rock.isDisappearCompleted() || rock.isFruitEaten()) { // if rock is destroyed and the effect is
																		// completed
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

				// System.out.println("enemy HIT for " + player.getAttackDamage() + " damage");
				enemy.takeDamage(player.getAttackDamage());

				if (!player.isInvincible())
					player.takeDamage(enemy.getAttackDamage());
			}

			// if enemy collides with player damage player
			if (enemy.collidesWithPlayer(player) && enemy.isAlive()) {

				if (!player.isInvincible()) {
					player.takeDamage(enemy.getAttackDamage());

				}
			}

			if ((enemy instanceof RedBee) && !enemy.isAlive()) {
				// spawn 3 TinyBees when red bee dies
				Point p = new Point(enemy.getMapX(), enemy.getMapY());
				tempEnemies.add(new TinyBee(this, p.x + 30, p.y, background, player));
				tempEnemies.add(new TinyBee(this, p.x - 30, p.y, background, player));
				tempEnemies.add(new TinyBee(this, p.x, p.y - 30, background, player));
			}

			// if enemy is dead then remove it from the game
			if (!enemy.isAlive()) {
				player.addScore(enemy.getScoreValue());
				enemyIterator.remove();

				System.out.println("NumEnemies " + numEnemies);
				if (numEnemies != -1 && !(enemy instanceof TinyBee))
					numEnemies--;
			}
		}
		enemies.addAll(tempEnemies);
		tempEnemies.clear();

		// remove solid objects associated with rocks, if their rock was destroyed
		soManager.removeDestroyedRocks();

		if (numEnemies !=-1 && numEnemies < 1 && chest == null) {
			chest = new Chest(this, player.getX() - player.getHeight(), player.getY(), background);
			soundManager.playClip("chestSpawn", false);
			System.out.println("chestSpawned");
		}

		window.updateScore(player.getScore());
		window.updateMaterials(player.getMaterials());
	}

	public void updatePlayer(int direction) {

		// get rectangle of player if they were to move in the direction
		Rectangle2D.Double futurePosition = player.getFutureBoundingRectangle(direction);

		// checking if the player would hit a solid if they moved in the direction
		Boolean wouldCollide = soManager.collidesWithSolid(futurePosition);

		// this makes the player walk through any solid object
		// wouldCollide = false; // for testing purposes, comment out when done

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
				System.out.println("CURRENT LEVEL "+currentLevel);
				levelInitializer.initNextLevel(currentLevel);
				System.out.println("CURRENT LEVEL AFTER "+currentLevel);
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

			if (direction == 88 && playerOnChest()) {
				if (!chest.isOpen()) {
					soundManager.stopClip("level3_background");
					soundManager.playClip("victory", false);
					chest.setOpen(true);
					player.addScore(chest.getScoreValue());
				}
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

		if(!isGameOver){
			if (isPaused) {
				printGamePaused(imageContext);
			} 
			else{
	
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
	
			if (chest != null)
				chest.draw(imageContext);
	
			if (player != null)
				player.draw(imageContext);
	
			if (healthDisplay != null)
				healthDisplay.draw(imageContext, player.getHealth(), getWidth());
			}

		}
		else if (isGameOver) {
			displayScoreBoard(imageContext);
		}

		
		Graphics2D g2 = (Graphics2D) getGraphics(); // get the graphics context for the panel
		g2.drawImage(image, 0, 0, 1100, 500, null);

		imageContext.dispose();
		g2.dispose();
	}

	public void startGame() { // initialise and start the game thread
		isGameOver = false;
		if (gameThread == null && characterSelected) {
			soundManager.playClip("start", false);
			createGameEntities();
			levelInitializer.initLevelOne();
			gameThread = new Thread(this);
			gameThread.start();
			startTimer();

		}

	}


	public void startNewGame() { // initialise and start a new game thread
		isGameOver = false;
		isPaused = false;
	
		soundManager.stopClip("gameover"); // Stop game over sound
		Graphics2D g2d = (Graphics2D) getGraphics();
		g2d.clearRect(0, 0, getWidth(), getHeight()); // Clear any residual drawings
	
		if (gameThread == null || !isRunning) {
			soundManager.playClip("start", false);
			int level = getCurrentLevel();
	
			if (level == 1) {
				soundManager.playClip("background", true);
			} else if (level == 2) {
				soundManager.playClip("background2", true);
			} else if (level == 3) {
				soundManager.playClip("level3_background", true);
			}
			createGameEntities();
			levelInitializer.initLevelTwo();
	
			gameThread = new Thread(this);
			gameThread.start();
	
			startTimer();
		} else {
			if (isPaused) {
				// Stop the pause music
				soundManager.stopClip("pause");
				int level = getCurrentLevel();
	
				// Play the appropriate background music based on the current level
				if (level == 1) {
					soundManager.playClip("background", true);
				} else if (level == 2) {
					soundManager.playClip("background2", true);
				} else if (level == 3) {
					soundManager.playClip("level3_background", true);
				}
	
				// Resume the timer
				startOrResumeTimer();
				isPaused = false;
			}
		}
	}
	

	public void pauseGame() { // pause the game (don't update game entities)
		if (isRunning) {
			if (isPaused){
				startOrResumeTimer();
				soundManager.stopClip("pause"); 
				int level = getCurrentLevel();

				if(level == 1){
					soundManager.playClip("background", true);
				}
				else if(level == 2){
					soundManager.playClip("background2", true);
				}
				else if(level == 3){
					soundManager.playClip("level3_background", true);
				}
				isPaused = false;
			}
				
			else{
				printGamePaused((Graphics2D) image.getGraphics());
                soundManager.stopAllClips();
                soundManager.playClip("pause", true); 
				pauseTimer();
                isPaused = true;
			}
				
		}
	}

	public void endGame() { // end the game thread
		isRunning = false;
		// soundManager.stopClip ("background");
		isGameOver = true;
        isRunning = false;

		pauseTimer();
		displayGameOver();

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

	public int getCurrentLevel() {
		return currentLevel;
	}

	public void setCurrentLevel(int level) {
		currentLevel = level;
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


	public void setNumEnemies(int numEnemies) {
		this.numEnemies = numEnemies;
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

	public boolean playerOnChest() {
		if (chest != null && chest.collidesWithPlayer(player))
			return true;
		return false;
	}

	public void displayScoreBoard(Graphics2D g2d) {
		if(isGameOver == true){
			int panelWidth = getWidth();
			int panelHeight = getHeight();

			Image woodFrame = ImageManager.loadImage("images/scoreboard.png");
			int frameWidth = 300; // Adjust width as needed
        	int frameHeight = 250; // Adjust height as needed

			int frameX = (panelWidth - frameWidth) / 2; // Calculate X position for centering
			int frameY = (panelHeight - frameHeight) / 2; // Calculate Y position for centering

			g2d.drawImage(woodFrame, frameX, frameY, frameWidth, frameHeight, null); // Draw the wood frame
		}
	}

	
    // Prints "Game Paused" message
    public void printGamePaused(Graphics2D g2) {
        g2.setColor(Color.WHITE); // Set the color to white
        g2.setFont(new Font("Arial", Font.BOLD, 42)); // Set the font and size

        String message = "GAME PAUSED";
        FontMetrics fontMetrics = g2.getFontMetrics();
        int stringWidth = fontMetrics.stringWidth(message);
        int x = (getWidth() - stringWidth) / 2; // Calculate x-coordinate for center alignment
        int y = getHeight() / 2; // Center vertically

        g2.drawString(message, x, y); // Draw the message at the calculated position
    }

	// Method to start or resume the timer
	public void startOrResumeTimer() {
		if (timer == null) {
			// Timer not yet initialized, start from initial time
			startTimer(); // Start timer with initial time of 120 seconds (2 minutes)
		} else {
			// Timer already initialized, resume from remaining time
			resumeTimer(remainingTime);
		}
	}

	// Method to pause the timer
	public void pauseTimer() {
		if (timer != null) {
			timer.cancel(); // Cancel the timer task
		}
	}

	// Method to start the timer with the specified initial time
	public void startTimer() {
		timer = new Timer(); // Initialize new Timer object
		int delay = 500; // Adjust delay as needed
		remainingTime = initialTimeInSeconds;
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if (remainingTime == 0 || player.getHealth() == 0) {
					window.timeTF.setText(String.format("%02d:%02d", 0, 0));
					timer.cancel(); // Stop the timer when time reaches 0
					endGame();
					remainingTime = 900;
					isGameOver = true;
					checkGameOver();
				} else {
					int minutes = remainingTime / 60;
					int seconds = remainingTime % 60;
					window.timeTF.setText(String.format("%02d:%02d", minutes, seconds));
					remainingTime--;
				}
			}
		}, delay, delay);
	}


	// Method to resume the timer with the specified remaining time
	private void resumeTimer(int remainingTimeInSeconds) {
		timer = new Timer(); // Initialize new Timer object
		int delay = 500; // Adjust delay as needed
		remainingTime = remainingTimeInSeconds;
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if (remainingTime == 0 || player.getHealth() == 0) {
					window.timeTF.setText(String.format("%02d:%02d", 0, 0));
					timer.cancel(); // Stop the timer when time reaches 0
					endGame();
					remainingTime = 900;
					isGameOver = true;
					checkGameOver();
				} else {
					int minutes = remainingTime / 60;
					int seconds = remainingTime % 60;
					window.timeTF.setText(String.format("%02d:%02d", minutes, seconds));
					remainingTime--;
				}
			}
		}, delay, delay);
	}

	public boolean checkGameOver() {
		if (player.getHealth() == 0 || remainingTime == 0) {
			isGameOver = true;
		}
		else
			isGameOver = false;

		return isGameOver;
	}


	// Method to display the game over screen details
	public void displayGameOver(){
		if(checkGameOver() == true){
			soundManager.stopAllClips();
			soundManager.playClip("gameover", false);
			Graphics g = this.getGraphics();
			Graphics2D g2 = (Graphics2D) g;
	
			// Set rendering hints for smoother edges
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	
			// Set font properties for "Game Over!" message
			Font gameOverFont = new Font("Arial", Font.BOLD, 36);
			g2.setFont(gameOverFont);
			g2.setColor(Color.RED);
	
			// Get the size of the panel
			Dimension panelSize = this.getSize();
	
			// Calculate the position to center the "Game Over!" message
			FontMetrics metrics = g2.getFontMetrics(gameOverFont);
			int x = (panelSize.width - metrics.stringWidth("Game Over!")) / 2;
			int y = (panelSize.height - metrics.getHeight()) / 2 + metrics.getAscent();

			// Calculate positions for score and lives
			int scoreX = ((panelSize.width - metrics.stringWidth("SCORE " + player.getScore())) / 2) + 45;
			int scoreY = y + metrics.getHeight() - 10; // Offset from "Game Over!" text
	
			int livesX = ((panelSize.width - metrics.stringWidth("LIVES " + player.getHealth())) / 2) + 32;
			int livesY = scoreY + metrics.getHeight() - 15; // Offset from score text
	
		
			// Draw the "Game Over!" text
			g2.drawString("Game Over!", x, y);

			// Set font properties for score
			Font scoreFont = new Font("Helvetica", Font.BOLD, 20);
			g2.setFont(scoreFont);
			g2.setColor(Color.WHITE);
	
			// Draw score and lives
			g2.drawString("Score " + player.getScore(), scoreX, scoreY);
			g2.drawString("Health " + player.getHealth(), livesX, livesY);
	
			// Dispose the graphics object
			g.dispose();
	
		}
		
	}

	public void resetGame(){
		window.materialTF.setText(" 0 ");
		window.scoreTF.setText(" 0 ");
		window.timeTF.setText(" 15:00 ");
	}

}