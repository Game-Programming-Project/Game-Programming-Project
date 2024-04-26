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

    public LevelInitializer(GamePanel gamePanel, SoundManager soundManager, SolidObjectManager soManager, List<Rock> rocks, List<Enemy> enemies, Background background, Player player) {
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

		background = new Background(gamePanel, "images/Maps/Testing/Level1MapTest.png", 96, 360, 80);
		soManager.setBg(background);
        gamePanel.setBackground(background);

		soManager.initLevelOne(); // set up map boundaries
		soManager.setAllObjectsVisible(false);

		soundManager.playClip("background", true);
		soundManager.setVolume("background", 0.7f);
		
		//add rocks here 
		rocks.add(new Rock(gamePanel, 1550, 1321, background));

		//add enemies under here
		enemies.add(new BeeAnimation(gamePanel, 620, 930, background, player));
		enemies.add(new BeeAnimation(gamePanel, 680, 950, background, player));
		enemies.add(new BeeAnimation(gamePanel, 780, 980, background, player));

		enemies.add(new GrasshopperAnimation(gamePanel, 999, 900, background, player));
		enemies.add(new GrasshopperAnimation(gamePanel, 1200, 950, background, player));
		enemies.add(new GrasshopperAnimation(gamePanel, 1500, 980, background, player));

		enemies.add(new MushroomAnimation(gamePanel, 1700, 900, background, player));
		enemies.add(new MushroomAnimation(gamePanel, 1900, 960, background, player));
    }

    public void initLevelTwo() {
        //stop clips from previous level and clears all objects
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

		//note for level 3 offsetX: -90, offsetY: 400
		background = new Background(gamePanel, "images/Maps/Testing/Level3MapTest.png", 96, -90, 400);
		soManager.setBg(background);
        gamePanel.setBackground(background);

		soManager.initLevelThree(); // set up map boundaries
		soManager.setAllObjectsVisible(false);

		// soundManager.playClip("background", true);
		// soundManager.setVolume("background", 0.7f);

		//rocks under here
		rocks.add(new Rock(gamePanel, 1550, 1321, background));

		//enemies under here
		enemies.add(new Shaman(gamePanel, 460, 1489, background, player, soManager));
		enemies.add(new Bomber(gamePanel, 1425, 501, background, player, soManager));
		enemies.add(new FireBat(gamePanel, 1425, 501, background, player));
		enemies.add(new TinyBee(gamePanel, 1425, 501, background, player));
		enemies.add(new RedBee(gamePanel, 1425, 501, background, player));
    }
}