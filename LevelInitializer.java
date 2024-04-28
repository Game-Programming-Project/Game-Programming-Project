import java.util.ArrayList;
import java.util.List;
import java.awt.Point;
import java.util.Random;


public class LevelInitializer {
	private GamePanel gamePanel;
	private SoundManager soundManager;
	private SolidObjectManager soManager;
	private List<Rock> rocks;
	private List<Enemy> enemies;
	private Background background;
	private Player player;
	private Random random;

	private EntitySpawner entitySpawner;

	public LevelInitializer(GamePanel gamePanel, SoundManager soundManager, SolidObjectManager soManager, ArrayList<Rock> rocks, List<Enemy> enemies, Background background, Player player, EntitySpawner entitySpawner) {
		this.gamePanel = gamePanel;
		this.soundManager = soundManager;
		this.soManager = soManager;
		this.rocks = rocks;
		this.enemies = enemies;
		this.background = background;
		this.player = player;

		this.entitySpawner = entitySpawner;
	}

	public String initNextLevel(String currentLevel) {
		if (currentLevel.equals("1")) {
			initLevelTwo();
			currentLevel = "2";
			return "2";
		} else if (currentLevel.equals("2")) {
			initLevelThree();
			currentLevel = "3";
			return "3";
		} else if (currentLevel.equals("3")) {
			return "win";
		} else {
			return "Error";
		}
	}

	public void initLevelOne() {
		soundManager.stopClip("lobby");
		soundManager.playClip("start", false);

		rocks.clear();
		enemies.clear();

		// offsetX is 360, offsetY is 80
		background = new Background(gamePanel, "images/Maps/Testing/Level1MapTest.png", 96 + player.getSpeed(), 390,
				-30);

		soManager.setBg(background);
		gamePanel.setBackground(background);
		entitySpawner.setBg(background);

		soManager.initLevelOne(); // set up map boundaries
		soManager.setAllObjectsVisible(false);

		soundManager.playClip("background", true);

		// add rocks here
		Point p = entitySpawner.getRandomPoint(1946, 2083, 798, 1137);
		rocks.add(new Rock(gamePanel, p.x, p.y, background, true));
		entitySpawner.spawnRocks(15, 633, 847, 808, 1138, 75, 13, 8, 3, 1); // left area
		entitySpawner.spawnRocks(6, 873, 1127, 850, 1106, 75, 13, 8, 3, 1); // left corridor
		entitySpawner.spawnRocks(25, 1075, 1622, 682, 1225, 75, 13, 8, 3, 1); // middle area
		entitySpawner.spawnRocks(7, 1605, 1807, 914, 1062, 75, 13, 8, 3, 1); // right corridor
		entitySpawner.spawnRocks(15, 1806, 2088, 790, 1299, 75, 13, 8, 3, 1); // right area

		// add enemies under here
		entitySpawner.spawnLevelOneEnemies();
	}

	public void initLevelTwo() {
		// stop clips from previous level and clears all objects
		soundManager.stopAllClips();
		rocks.clear();
		enemies.clear();

		background = new Background(gamePanel, "images/Maps/Testing/Level2MapTest.png", 96, 300, 50);
    
    	player.resetX();
		player.resetY();
    
		soManager.setBg(background);
		gamePanel.setBackground(background);
		entitySpawner.setBg(background);

		soManager.initLevelTwo(); // set up map boundaries
		soManager.setAllObjectsVisible(false);
		
		// Add level 2 background sound
		soundManager.setVolume("background2", 1.0f);
		soundManager.playClip("background2", true);

		entitySpawner.spawnRocks(6, 662, 1018, 182, 546, 65, 8, 20, 5, 2);
		entitySpawner.spawnRocks(15, 384, 1298, 937, 1210, 65, 8, 20, 5, 2);
		entitySpawner.spawnRocks(8, 1107, 1892, 31, 576, 65, 8, 20, 5, 2);
		entitySpawner.spawnRocks(12, 1283, 1853, 942, 1182, 65, 8, 20, 5, 2);
		entitySpawner.spawnRocks(12, 1979, 2789, 318, 581, 65, 8, 20, 5, 2);
		entitySpawner.spawnRocks(10, 2090, 2803, 586, 969, 65, 8, 20, 5, 2);
		entitySpawner.spawnRocks(12, 2241, 2766, 940, 1156, 65, 8, 20, 5, 2);

		// add enemies under here
		enemies.add(new ScorpionAnimation(gamePanel, 620, 930, background, player, soManager));

		enemies.add(new SpiderAnimation(gamePanel, 1320, 809, background, player, soManager));
		enemies.add(new SpiderAnimation(gamePanel, 1526, 1010, background, player, soManager));
		enemies.add(new SpiderAnimation(gamePanel, 1627, 566, background, player, soManager));

		enemies.add(new MotherSpiderAnimation(gamePanel, 1583, 300, background, player, soManager));

		enemies.add(new BismuthAnimation(gamePanel, 2437, 900, background, player, soManager));

	}

	public void initLevelThree() {
		soundManager.stopAllClips();
		rocks.clear();
		enemies.clear();

		// note for level 3 offsetX: -90, offsetY: 400
		background = new Background(gamePanel, "images/Maps/Testing/Level3MapTest.png", 96 + player.getSpeed(), -90,
				300);

		player.resetX();
		player.resetY();

		soManager.setBg(background);
		gamePanel.setBackground(background);
		entitySpawner.setBg(background);

		soManager.initLevelThree(); // set up map boundaries
		soManager.setAllObjectsVisible(false);

		soundManager.setVolume("level3_background", 0.7f);
		soundManager.playClip("level3_background", true);

		// rocks under here
		entitySpawner.spawnLevelThreeRocks();

		// enemies under here
		entitySpawner.spawnLevelThreeEnemies();
	}
}