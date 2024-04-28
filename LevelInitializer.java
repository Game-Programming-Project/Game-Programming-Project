import java.util.ArrayList;
import java.util.List;

public class LevelInitializer {
	private GamePanel gamePanel;
	private SoundManager soundManager;
	private SolidObjectManager soManager;
	private List<Rock> rocks;
	private List<Enemy> enemies;
	private Background background;
	private Player player;

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
			initLevelThree();
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
		soundManager.stopAllClips();
		rocks.clear();
		enemies.clear();

		// offsetX is 360, offsetY is 80
		background = new Background(gamePanel, "images/Maps/Testing/Level1MapTest.png", 96 + player.getSpeed(), 360,
				80);
		soManager.setBg(background);
		gamePanel.setBackground(background);
		entitySpawner.setBg(background);

		soManager.initLevelOne(); // set up map boundaries
		soManager.setAllObjectsVisible(false);

		soundManager.playClip("background", true);
		//soundManager.setVolume("background", 0.7f);

		// add rocks here

		rocks.add(new Rock(gamePanel, 690, 992, background, true));
		entitySpawner.spawnRocks(15, 620, 844, 839, 1138, 75, 13, 8, 3, 1);
		entitySpawner.spawnRocks(6, 873, 1127, 850, 1106, 75, 13, 8, 3, 1);
		entitySpawner.spawnRocks(25, 1075, 1622, 682, 1225, 75, 13, 8, 3, 1);
		entitySpawner.spawnRocks(7, 1605, 1807, 914, 1062, 75, 13, 8, 3, 1);
		entitySpawner.spawnRocks(15, 1806, 2088, 790, 1299, 75, 13, 8, 3, 1);

		// add enemies under here
		entitySpawner.spawnLevelOneEnemies();
	}

	public void initLevelTwo() {
		// stop clips from previous level and clears all objects
		soundManager.stopAllClips();
		rocks.clear();
		enemies.clear();

		// background = new Background(this, xxxxxx);
		player.resetX();
		player.resetY();

		soManager.setBg(background);
		gamePanel.setBackground(background);
		entitySpawner.setBg(background);

		soManager.initLevelTwo(); // set up map boundaries
		soManager.setAllObjectsVisible(false);
	}

	public void initLevelThree() {
		soundManager.stopAllClips();
		rocks.clear();
		enemies.clear();

		// note for level 3 offsetX: -90, offsetY: 400
		background = new Background(gamePanel, "images/Maps/Testing/Level3MapTest.png", 96 + player.getSpeed(), -90, 300);

		player.resetX();
		player.resetY();

		soManager.setBg(background);
		gamePanel.setBackground(background);
		entitySpawner.setBg(background);

		soManager.initLevelThree(); // set up map boundaries
		soManager.setAllObjectsVisible(false);

		soundManager.setVolume("level3_background", 1f);
		soundManager.playClip("level3_background", true);
		

		// rocks under here
		entitySpawner.spawnLevelThreeRocks();
		// gamePanel.setRocks(entitySpawner.getRocks());
		
		// enemies under here
		entitySpawner.spawnLevelThreeEnemies();
	}
}