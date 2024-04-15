import javax.swing.JPanel;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class GamePanel extends JPanel implements Runnable {
   
	private static int NUM_ALIENS = 3;

	private SoundManager soundManager;
	private Alien[] aliens;
	private boolean alienDropped;
	private boolean isRunning;
	private boolean isPaused;

	private Thread gameThread;

	private BufferedImage image;
 	private Image backgroundImage;

	private Player player;

	private CharacterSelection charSelect;
	private String character;

	public GamePanel () {

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

		backgroundImage = ImageManager.loadImage ("images/Background.jpg");

		image = new BufferedImage (400, 400, BufferedImage.TYPE_INT_RGB);
	}


	public void createGameEntities() {
		player = new Player(this, 50, 350, character);

		aliens = new Alien [3];
		aliens[0] = new Alien (this, 275, 10, player);
		aliens[1] = new Alien (this, 150, 10, player);
		aliens[2] = new Alien (this, 330, 10, player);
	
	}


	public void run () {
		try {
			isRunning = true;
			while (isRunning) {
				if (!isPaused)
					gameUpdate();
				gameRender();
				Thread.sleep (50);	
			}
		}
		catch(InterruptedException e) {}
	}


	public void gameUpdate() {

		for (int i=0; i<NUM_ALIENS; i++) {
			aliens[i].move();
		}

		player.update();
	}


	public void updatePlayer (int direction) {

		if (player != null && !isPaused) {
			
			if(direction!=99){
				player.start();
				player.move(direction);
				System.out.println("walk.update(direction) called "+direction);
			}

			if(direction==99)
				player.attack();
		}

	}


	public void gameRender() {

		// draw the game objects on the image

		Graphics2D imageContext = (Graphics2D) image.getGraphics();

		imageContext.drawImage(backgroundImage, 0, 0, null);	// draw the background image


		if (player != null) {
			player.draw(imageContext);
		}

		if (aliens != null) {
			for (int i=0; i<NUM_ALIENS; i++)
				aliens[i].draw(imageContext);
       	}

		Graphics2D g2 = (Graphics2D) getGraphics();	// get the graphics context for the panel
		g2.drawImage(image, 0, 0, 400, 400, null);

		imageContext.dispose();
		g2.dispose();
	}


	public void startGame() {				// initialise and start the game thread 

		if (gameThread == null) {
			//soundManager.playClip ("background", true);
			createGameEntities();
			gameThread = new Thread (this);			
			gameThread.start();

		}

	}


	public void startNewGame() {				// initialise and start a new game thread 

		isPaused = false;

		if (gameThread == null || !isRunning) {
			//soundManager.playClip ("background", true);
			createGameEntities();
			gameThread = new Thread (this);			
			gameThread.start();

		}
	}


	public void pauseGame() {				// pause the game (don't update game entities)
		if (isRunning) {
			if (isPaused)
				isPaused = false;
			else
				isPaused = true;
		}
	}


	public void endGame() {					// end the game thread
		isRunning = false;
		//soundManager.stopClip ("background");
	}


	public void shootCat() {
		//animation3.start();
	}

	
	//method sets which character the player will be using
	public void setCharacter(String character) {
        this.character = character;
		System.out.println("Character selected: " + this.character);
        // Remove the SelectCharacter panel
        remove(charSelect);

        // Redraw the GamePanel
        revalidate();
        repaint();
    }
}