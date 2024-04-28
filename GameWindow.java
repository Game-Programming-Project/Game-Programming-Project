import javax.swing.*; // need this for GUI objects
import java.awt.*; // need this for Layout Managers
import java.awt.event.*; // need this to respond to GUI events

public class GameWindow extends JFrame implements ActionListener, KeyListener, MouseListener {

	// declare instance variables for user interface objects

	// declare labels

	//private JLabel statusBarL;
	//private JLabel keyL;
	//private JLabel mouseL;

	// declare text fields

	//private JTextField statusBarTF;
	//private JTextField keyTF;
	//private JTextField mouseTF;

	private static final int initialTimeInSeconds = 120;		
	// Labels declaration 
	private JLabel gameL;
	private JLabel materialLabel;
	private JLabel scoreLabel;
	private JLabel timeLabel;
	private JLabel startGameLabel;
	
	// Text fields declaration 
	private JTextField materialTF;
	private JTextField scoreTF;
	private JTextField timeTF;

	// Buttons declaration 
	private JButton playB; 
	private JButton pauseB; 
	private JButton endB;
	private JButton restartB;   
	private JButton exitB; 



	// Variable declarations
	private int score; 
	private int numLives;
	private int remainingTime;
	private boolean gameOver;
	private boolean isTimerPaused;

	private Container c;
	private JPanel mainPanel;
	private GamePanel gamePanel;
	private Timer timer;

	@SuppressWarnings({ "unchecked" })
	public GameWindow() {


		//setSize(1200, 875);

		// create user interface objects
		// create labels
		//statusBarL = new JLabel("Application Status: ");
		//keyL = new JLabel("Key Pressed: ");
		//mouseL = new JLabel("Location of Mouse Click: ");
  
		// create text fields and set their colour, etc.
		//statusBarTF = new JTextField(25);
		//keyTF = new JTextField(25);
		//mouseTF = new JTextField(25);

		//statusBarTF.setEditable(false);
		//keyTF.setEditable(false);
		//mouseTF.setEditable(false);

		//statusBarTF.setBackground(Color.CYAN);
		//keyTF.setBackground(Color.YELLOW);
		//mouseTF.setBackground(Color.GREEN);

		// create buttons

		//startB = new JButton("Start Game");
		//pauseB = new JButton("Pause Game");
		//endB = new JButton("End Game");
		//startNewB = new JButton("Start New Game");
		//focusB = new JButton("Shoot Cat");
		//exitB = new JButton("Exit");

		// add listener to each button (same as the current object)

		//startB.addActionListener(this);

		setTitle ("A Game of a Miner's Exploration"); // window name
		setSize (1920, 800); // window size

		// Load image for header panel
		Image originalImage = ImageManager.loadImage("images/Game Title.png");
		int panelWidth = 800; // Adjust this to match the desired width of the panel
		int panelHeight = 100; // Adjust this to match the desired height of the panel
		Image scaledImage = originalImage.getScaledInstance(panelWidth, panelHeight, Image.SCALE_SMOOTH);
		ImageIcon gameTitleIcon = new ImageIcon(scaledImage);

		// Create the header panel 
		JPanel headerImagePanel = new JPanel();
		headerImagePanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
		headerImagePanel.setBackground(new Color(71, 78, 111)); // Set background color if needed
		headerImagePanel.add(new JLabel(gameTitleIcon)); // Add the image to the panel

		// Create the score, lives and time labels of the infoPanel
		materialLabel = new JLabel (" MATERIALS COLLECTED ");
		scoreLabel = new JLabel (" CURRENT SCORE  ");
		timeLabel = new JLabel(" TIME REMAINING ");

		// Create text fields and set properties
		materialTF = new JTextField("0");
		scoreTF = new JTextField(Integer.toString(score));
		timeTF = new JTextField(Integer.toString(remainingTime));

		materialTF.setEditable(false);
		scoreTF.setEditable(false);
		timeTF.setEditable(false);

		// Set the background color of the text fields
		materialTF.setBackground(Color.WHITE);
		scoreTF.setBackground(Color.WHITE);
		timeTF.setBackground(Color.WHITE);

		// Creating buttons 
		playB = new JButton ("Play");
		pauseB = new JButton ("Pause Game");
		endB = new JButton ("End Game");
		restartB = new JButton ("New Game"); 
		exitB = new JButton ("Exit");

		// Adding action listeners to each button
		playB.addActionListener(this);

		pauseB.addActionListener(this);
		endB.addActionListener(this);
		restartB.addActionListener(this);
		exitB.addActionListener(this);
		
		// Create mainPanel
		mainPanel = new JPanel();
		FlowLayout flowLayout = new FlowLayout();
		mainPanel.setLayout(flowLayout);

		GridLayout gridLayout;

		// create the gamePanel for game entities

		// create infoPanel

		//JPanel infoPanel = new JPanel();
		//gridLayout = new GridLayout(3, 2);
		//infoPanel.setLayout(gridLayout);
		//infoPanel.setBackground(Color.ORANGE);

		// add user interface objects to infoPanel

		//infoPanel.add(statusBarL);
		//infoPanel.add(statusBarTF);

		//infoPanel.add(keyL);
		//infoPanel.add(keyTF);

		//infoPanel.add(mouseL);
		//infoPanel.add(mouseTF);

		// create buttonPanel


		// // Create the gamePanel for game entities and set properties
		gamePanel = new GamePanel(this);
		gamePanel.setPreferredSize(new Dimension(1100, 600));
	
		// Create the start game label and add it to the panel
		startGameLabel = new JLabel("Click Play To Start Game");
		startGameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		startGameLabel.setFont(new Font("Arial", Font.BOLD, 20));
		startGameLabel.setForeground(Color.BLACK);

		
	
		// //Create the play button from an image
		// ImageIcon playIcon = new ImageIcon("images/play.png");
		// Image scaledPlayImage = playIcon.getImage().getScaledInstance(200, 80, Image.SCALE_SMOOTH);
		// playIcon = new ImageIcon(scaledPlayImage);
		// playB = new JButton(playIcon);
		// playB.setContentAreaFilled(false); // Make the button transparent
		// playB.setBorderPainted(false); // Remove border

		

		//Add startGameLabel
		// gamePanel.add(startGameLabel);
		// startGameLabel.setAlignmentX(Component.TOP_ALIGNMENT); // Center the label horizontally
		
		// // Add rigid area to create space between the label and button
		// gamePanel.add(Box.createRigidArea(new Dimension(0, 10))); // Adjust the vertical space as needed
		// startGameLabel.setVisible(false);
		// // Add playB button
		// playB.setPreferredSize(new Dimension(200, 80)); // Set button size
		// gamePanel.add(playB);
		// playB.setAlignmentX(Component.BOTTOM_ALIGNMENT); // Center the button horizontally
		// playB.setVisible(false);
		gamePanel.add(startGameLabel);
		startGameLabel.setVisible(false);
		//gamePanel.add(playB); // Add play button to the bottom
		// Set the action command for the play button
		//playB.setActionCommand("Play");
	
		// Create buttonPanel

		JPanel buttonPanel = new JPanel();
		FlowLayout f = new FlowLayout();
		buttonPanel.setLayout(f);
		buttonPanel.setPreferredSize(new Dimension(1200, 35));
		buttonPanel.setBackground(new Color(71, 78, 111));

		// Adding buttons to buttonPanel
		buttonPanel.add(playB);
		buttonPanel.add(pauseB);
		buttonPanel.add(endB);
		buttonPanel.add(restartB);
		buttonPanel.add(exitB);

		// Create infoPanel
		JPanel infoPanel = new JPanel();
		gridLayout = new GridLayout(1, 6);
		infoPanel.setLayout(gridLayout);
		infoPanel.setBackground(new Color(186, 181, 147));

		// Add user interface objects to infoPanel
		infoPanel.add (materialLabel);
		infoPanel.add (materialTF);

		infoPanel.add (scoreLabel);
		infoPanel.add (scoreTF);		

		infoPanel.add (timeLabel);
		infoPanel.add (timeTF);
		
		// Add sub-panels with GUI objects to mainPanel and set its colour
		mainPanel.add(headerImagePanel);
		mainPanel.add(buttonPanel);
		mainPanel.add(gamePanel);
		mainPanel.add(infoPanel); 
		mainPanel.setBackground(new Color(71, 78, 111));

		// Set up mainPanel to respond to keyboard and gamePanel to mouse
		gamePanel.addMouseListener(this);
		mainPanel.addKeyListener(this);
		mainPanel.setFocusable(true);
		mainPanel.requestFocusInWindow(); // Request focus when window is shown

		// Add mainPanel to window surface
		c = getContentPane();
		c.add(mainPanel);

		// Set properties of window
		setResizable(true);
		setExtendedState(JFrame.MAXIMIZED_BOTH);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null); // Center the window on the screen
		setVisible(true);

		// Setting the initial values 
		score = 0;
		gameOver = false; 

		// Set the default values of the score, lives and time text fields in the infoPanel
		scoreTF.setText(" 0 ");
		timeTF.setText(" 00:00 ");
	}

	// implement single method in ActionListener interface
	public void actionPerformed(ActionEvent e) {

		String command = e.getActionCommand();

		if (command.equals("Play")) {
			gamePanel.startGame();
			setStartGameComponentsVisible(true);
		}

		if (command.equals("Pause Game")) {
			gamePanel.pauseGame();
			isTimerPaused = false;
			pauseB.setText ("Resume Game");
		}

		if (command.equals("Resume Game")) {
			gamePanel.pauseGame();
			isTimerPaused = true;
			pauseB.setText ("Pause Game");
		}
		
		if (command.equals(endB.getText())) {
			gamePanel.endGame();
			remainingTime = 0;
		}

		if (command.equals(restartB.getText())){
			pauseB.setText ("Pause Game");
			gamePanel.startNewGame();
		}

		// if (command.equals(focusB.getText())){
		// 	//gamePanel.shootCat();
		// }

		if (command.equals(exitB.getText()))
			System.exit(0);

		mainPanel.requestFocus();
	}

	// implement methods in KeyListener interface
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();

		if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) {
			gamePanel.updatePlayer(1);
		}

		if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
			gamePanel.updatePlayer(2);
		}

		if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
			gamePanel.updatePlayer(3);
		}

		if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
			gamePanel.updatePlayer(4);
		}

		if(keyCode == KeyEvent.VK_B){
			gamePanel.bombBismuth();
		}
	}

	public void keyReleased(KeyEvent e) {

	}

	public void keyTyped(KeyEvent e) {

	}

	// implement methods in MouseListener interface

	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();

		// 99 means attack, so clicking makes player swing weapon
		gamePanel.updatePlayer(99);

		if (SwingUtilities.isLeftMouseButton(e)) {
			// 99 means attack, so clicking makes player swing weapon
			gamePanel.updatePlayer(99);

        } else if (SwingUtilities.isRightMouseButton(e)) {
			gamePanel.updatePlayer(88);
        }

	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {

	}

	// Method to set the visibility of the start game label 
    public void setStartGameComponentsVisible(boolean isVisible) {
        startGameLabel.setVisible(isVisible);
    }

	public void updateScore(int score) {
		scoreTF.setText(String.valueOf(score));
	}

	public void updateMaterials(int materials) {
		materialTF.setText(String.valueOf(materials));
	}

}