import java.util.List;
import java.util.Random;
import java.util.Random;
import java.awt.Point;

public class EntitySpawner {
    private GamePanel gamePanel;
	private SoundManager soundManager;
	private SolidObjectManager soManager;
	private List<Rock> rocks;
	private List<Enemy> enemies;
	private Background background;
	private Player player;
	private Random random;

	public EntitySpawner(GamePanel gamePanel, SoundManager soundManager, SolidObjectManager soManager,
			List<Rock> rocks, List<Enemy> enemies, Background background, Player player) {
		this.gamePanel = gamePanel;
		this.soundManager = soundManager;
		this.soManager = soManager;
		this.rocks = rocks;
		this.enemies = enemies;
		this.background = background;
		this.player = player;

		random = new Random();
	}

	public void spawnLevelOneEnemies(){

		for(int i=0;i<3;i++){ // run 3 times
			Point p = getRandomPoint(645, 829, 832, 1086);
			if (p != null) {
				enemies.add(new BeeAnimation(gamePanel, p.x, p.y, background, player));
			}

			Point p2 = getRandomPoint(1122, 1580, 720, 1128);
			if (p2 != null) {
				enemies.add(new GrasshopperAnimation(gamePanel, p2.x, p2.y, background, player));
			}
		}

		for(int i=0;i<2;i++){ // run 2 times
			Point p = getRandomPoint(1841, 2063, 809, 1146);
			if (p != null) {
				enemies.add(new MushroomAnimation(gamePanel, p.x, p.y, background, player, soManager));
			}
		}
	
	}

	public void spawnLevelTwo(){

	}

	public void spawnLevelThree(){

	}

    public void spawnRocks(int num, int x1, int x2, int y1, int y2, double basicProbability, double copperProbability, double ironProbability, double goldProbability, double diamondProbability) {
		for (int i = 0; i < num; i++) {
			Point p = getRandomPoint(x1, x2, y1, y2);

			int x = p.x;
			int y = p.y;

			if (p!=null) { // if p is null then a new point to spawn couldn't be found
				Rock rock;
				int randomValue = new Random().nextInt(100); // Generate a random number between 0 and 99

				if (randomValue < basicProbability) { // 75% chance
					rock = new Rock(gamePanel, x, y, background);
				} else if (randomValue < basicProbability+copperProbability) { // 13% chance
					rock = new CopperRock(gamePanel, x, y, background);
				} else if (randomValue < basicProbability+copperProbability+ironProbability) { // 8% chance
					rock = new IronRock(gamePanel, x, y, background);
				} else if (randomValue < basicProbability+copperProbability+ironProbability+goldProbability) { // 3% chance
					rock = new GoldRock(gamePanel, x, y, background);
				} else { // 1% chance
					rock = new DiamondRock(gamePanel, x, y, background);
				}

				rocks.add(rock);

				// adds a solid object for each rock and associates the rock with the object
				// this is so rocks don't spawn on each other and so that players can't walk
				// through rocks
				SolidObject s = new SolidObject(x, y, i, i, gamePanel.getBackground(), false, background, rock);
				soManager.addSolidObject(s);
			}
		}
	}

	public Point getRandomPoint(int x1, int x2, int y1, int y2){

		int width = 30;
		int height = 30;

		int x = (int) (Math.random() * (x2 - x1 + 1) + x1); // random x coordinate within the range
		int y = (int) (Math.random() * (y2 - y1 + 1) + y1); // random y coordinate within the range

		boolean onSolid = soManager.onSolidObject(x, y, width, height);
		boolean spawn = true;

		int numTries = 0;
		while (onSolid) { // if point is on a solid object then keep generating new coordinates until
							// it's not on a solid object
			x = (int) (Math.random() * (x2 - x1 + 1) + x1);
			y = (int) (Math.random() * (y2 - y1 + 1) + y1);
			onSolid = soManager.onSolidObject(x, y, width, height);

			if (numTries > 1000) { // if it's tried 1000 times to find a new location then just break 
				spawn = false;
				break;
			}

			numTries++;
		}
		numTries = 0;

		if(spawn)
			return new Point(x, y);
		else
			return null;
	}

	public void setBg(Background bg) {
		background = bg;
	}
}
