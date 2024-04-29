import java.util.ArrayList;
import java.util.List;
import java.awt.Point;

public class LevelInitializer {
	private GamePanel gamePanel;
	private SoundManager soundManager;
	private SolidObjectManager soManager;
	private List<Rock> rocks;
	private List<Enemy> enemies;
	private Background background;
	private Player player;
	private EntitySpawner entitySpawner;

	public LevelInitializer(GamePanel gamePanel, SoundManager soundManager, SolidObjectManager soManager,
			ArrayList<Rock> rocks, List<Enemy> enemies, Background background, Player player,
			EntitySpawner entitySpawner) {
		this.gamePanel = gamePanel;
		this.soundManager = soundManager;
		this.soManager = soManager;
		this.rocks = rocks;
		this.enemies = enemies;
		this.background = background;
		this.player = player;

		this.entitySpawner = entitySpawner;
	}

	public void initNextLevel(int currentLevel) {

		if (currentLevel == 1) {
			gamePanel.setCurrentLevel(2);
			initLevelTwo();
		} else if (currentLevel == 2) {
			gamePanel.setCurrentLevel(3);
			initLevelThree();
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

		background = new Background(gamePanel, "images/Maps/Testing/Level2MapTest1.png", 96, 690, -300);

		player.resetX();
		player.resetY();

		soManager.setBg(background);
		gamePanel.setBackground(background);
		entitySpawner.setBg(background);

		soManager.initLevelTwo(); // set up map boundaries
		soManager.setAllObjectsVisible(false);

		// Add level 2 background sound
		soundManager.playClip("background2", true);

		// Spawns the ladder randomly
		Point p = entitySpawner.getRandomPoint(2684, 3070, 1018, 1484);
		rocks.add(new Rock(gamePanel, p.x, p.y, background, true));

		// spawn rocks under here
		entitySpawner.spawnLevelTwoRocks();

		// add enemies under here
		entitySpawner.spawnLevelTwoEnemies();

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

		int numEnemies = enemies.size();
		gamePanel.setNumEnemies(numEnemies);
		System.out.println("numEnemies: " + numEnemies);
	}
}