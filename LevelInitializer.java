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

	public LevelInitializer(GamePanel gamePanel, SoundManager soundManager, SolidObjectManager soManager,
			List<Rock> rocks, List<Enemy> enemies, Background background, Player player) {
		this.gamePanel = gamePanel;
		this.soundManager = soundManager;
		this.soManager = soManager;
		this.rocks = rocks;
		this.enemies = enemies;
		this.background = background;
		this.player = player;
	}

	public void initLevelOne() {
		soundManager.stopAllClips();
		rocks.clear();
		enemies.clear();

		// offsetX is 360, offsetY is 80
		background = new Background(gamePanel, "images/Maps/Testing/Level1MapTest.png", 96, 360, 80);
		soManager.setBg(background);
		gamePanel.setBackground(background);

		soManager.initLevelOne(); // set up map boundaries
		soManager.setAllObjectsVisible(false);

		soundManager.playClip("background", true);
		soundManager.setVolume("background", 0.7f);

		// add rocks here

		// rocks.add(new Rock(gamePanel, 1550, 1321, background));
		gamePanel.spawnRocks(15, 620, 844, 839, 1138, 75, 13, 8, 3, 1);
		gamePanel.spawnRocks(6, 873, 1127, 850, 1106, 75, 13, 8, 3, 1);
		gamePanel.spawnRocks(25, 1075, 1622, 682, 1225, 75, 13, 8, 3, 1);
		gamePanel.spawnRocks(7, 1605, 1807, 914, 1062, 75, 13, 8, 3, 1);
		gamePanel.spawnRocks(15, 1806, 2088, 790, 1299, 75, 13, 8, 3, 1);

		// add enemies under here
		// enemies.add(new BeeAnimation(gamePanel, 620, 930, background, player));
		// enemies.add(new BeeAnimation(gamePanel, 680, 950, background, player));
		// enemies.add(new BeeAnimation(gamePanel, 780, 980, background, player));

		// enemies.add(new GrasshopperAnimation(gamePanel, 999, 900, background,
		// player));
		// enemies.add(new GrasshopperAnimation(gamePanel, 1200, 950, background,
		// player));
		// enemies.add(new GrasshopperAnimation(gamePanel, 1500, 980, background,
		// player));

		enemies.add(new MushroomAnimation(gamePanel, 1700, 900, background, player, soManager));
		enemies.add(new MushroomAnimation(gamePanel, 1900, 960, background, player, soManager));
	}

	public void initLevelTwo() {
		// stop clips from previous level and clears all objects
		soundManager.stopAllClips();
		rocks.clear();
		enemies.clear();

		// background = new Background(this, xxxxxx);
		soManager.setBg(background);
		gamePanel.setBackground(background);

		soManager.initLevelTwo(); // set up map boundaries
		soManager.setAllObjectsVisible(false);
	}

	public void initLevelThree() {
		soundManager.stopAllClips();
		rocks.clear();
		enemies.clear();

		// note for level 3 offsetX: -90, offsetY: 400
		background = new Background(gamePanel, "images/Maps/Testing/Level3MapTest.png", 96, -90, 400);
		soManager.setBg(background);
		gamePanel.setBackground(background);

		soManager.initLevelThree(); // set up map boundaries
		soManager.setAllObjectsVisible(false);

		// soundManager.playClip("background", true);
		// soundManager.setVolume("background", 0.7f);

		// rocks under here
		rocks.add(new Rock(gamePanel, 1550, 1321, background));

		// enemies under here
		enemies.add(new RedBee(gamePanel, 1425, 501, background, player));
	}
}