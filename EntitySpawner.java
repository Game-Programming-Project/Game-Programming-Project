import java.util.List;
import java.util.Random;
import java.util.Random;
import java.awt.Point;
import java.util.ArrayList;

public class EntitySpawner {
	private GamePanel gamePanel;
	private SoundManager soundManager;
	private SolidObjectManager soManager;
	private ArrayList<Rock> rocks;
	private List<Enemy> enemies;
	private Background background;
	private Player player;
	private Random random;

	public EntitySpawner(GamePanel gamePanel, SoundManager soundManager, SolidObjectManager soManager, ArrayList<Rock> rocks, List<Enemy> enemies, Background background, Player player) {
		this.gamePanel = gamePanel;
		this.soundManager = soundManager;
		this.soManager = soManager;
		this.rocks = rocks;
		this.enemies = enemies;
		this.background = background;
		this.player = player;

		random = new Random();
	}

	public void spawnLevelOneEnemies() {

		for (int i = 0; i < 3; i++) { // run 3 times
			Point p = getRandomPoint(645, 829, 832, 1086);
			if (p != null) {
				enemies.add(new BeeAnimation(gamePanel, p.x, p.y, background, player));
			}

			Point p2 = getRandomPoint(1122, 1580, 720, 1128);
			if (p2 != null) {
				enemies.add(new GrasshopperAnimation(gamePanel, p2.x, p2.y, background, player));
			}
		}

		for (int i = 0; i < 2; i++) { // run 2 times
			Point p = getRandomPoint(1837, 1875, 811, 1072);
			if (p != null) {
				enemies.add(new MushroomAnimation(gamePanel, p.x, p.y, background, player, soManager));
			}
		}

	}

	public void spawnLevelTwoEnemies() {

		// Spawn 2 scorpions
		for (int i = 0; i < 3; i++) { 
			Point p = getRandomPoint(896, 1149, 834, 1543);
			if (p != null) {
				enemies.add(new ScorpionAnimation(gamePanel, p.x, p.y, background, player, soManager));
			}
		}

		// Spawn 5 spiders 
		for (int i = 0; i < 5; i++) { 
			Point p = getRandomPoint(1484, 2281, 709, 1487);
			if (p != null) {
				enemies.add(new SpiderAnimation(gamePanel, p.x, p.y, background, player, soManager));
			}
		}

		// Spawn 2 bismuths
		for (int i = 0; i < 2; i++) { 
			Point p = getRandomPoint(2619, 3139, 729, 1336);
			if (p != null) {
				enemies.add(new BismuthAnimation(gamePanel, p.x, p.y, background, player, soManager));
			}
		}

	} 	

	public void spawnLevelThreeEnemies() {

		for (int i = 0; i < 5; i++) { // run 5 times
			Point p = getRandomPoint(221, 2741, 210, 1590);
			if (p != null) {
				enemies.add(new Bomber(gamePanel, p.x, p.y, background, player, soManager));
			}
		}

		for (int i = 0; i < 4; i++) { // run 4 times
			Point p = getRandomPoint(221, 2741, 210, 1590);
			if (p != null) {
				enemies.add(new Shaman(gamePanel, p.x, p.y, background, player, soManager));
			}
		}

		for (int i = 0; i < 3; i++) { // run 3 times
			Point p = getRandomPoint(221, 2741, 210, 1590);
			if (p != null) {
				enemies.add(new RedBee(gamePanel, p.x, p.y, background, player));
			}
		} 

		for (int i=0; i < 2; i++){ // run twice
			Point p = getRandomPoint(221, 2741, 210, 1590);
			if (p != null) {
				enemies.add(new FireBat(gamePanel, p.x, p.y, background, player));
			}
		
		}

	}
	
	public void spawnLevelTwoRocks() {
		spawnRocks(10, 888, 1251, 576, 922, 70, 12, 20, 15, 4);
		spawnRocks(6, 706, 911, 806, 996, 70, 12, 20, 15, 4);
		spawnRocks(8, 970, 1146, 1058, 1325, 70, 12, 20, 15, 4);
		spawnRocks(12, 831, 1385, 1359, 1538, 70, 12, 20, 15, 4);
		spawnRocks(12, 1515, 2122, 916, 1251, 70, 12, 20, 15, 4);
		spawnRocks(10, 1660, 2227, 638, 874, 70, 12, 20, 15, 4);
		spawnRocks(6, 1771, 2060, 1271, 1589, 70, 12, 20, 15, 4);
		spawnRocks(4, 2310, 2429, 1121, 1310, 70, 12, 20, 15, 4);
		spawnRocks(8, 2296, 2653, 859, 1035, 70, 12, 20, 15, 4);
		spawnRocks(20, 2633, 3130, 726, 1333, 70, 12, 20, 15, 4);
		spawnRocks(10, 2679, 3076, 1342, 1552, 70, 12, 20, 15, 4);

	}

	public void spawnLevelThreeRocks() {
		spawnRocks(25, 1061, 1943, 1357, 1571, 50, 15, 20, 10, 5); // below spawn
		spawnRocks(15, 1708, 2027, 910, 1355, 50, 15, 20, 10, 5); // right of spawn
		spawnRocks(15, 1105, 1351, 1010, 1362, 50, 15, 20, 10, 5); // left of spawn
		spawnRocks(8, 1426, 1561, 367, 974, 50, 15, 20, 10, 5); // middle corridor above nose
		spawnRocks(15, 1923, 2606, 656, 834, 50, 15, 20, 10, 5);// top right straight
		spawnRocks(6, 2391, 2600, 506, 656, 50, 15, 20, 10, 5); // top right corner
		spawnRocks(10, 2021, 2689, 1172, 1581, 50, 15, 20, 10, 5); // bottom right corner
		spawnRocks(7, 1555, 1820, 401, 640, 50, 15, 20, 10, 5); // top right of nose
		spawnRocks(10, 1165, 1426, 376, 632, 50, 15, 20, 10, 5); // top left of nose
		spawnRocks(13, 247, 963, 1159, 1549, 50, 15, 20, 10, 5); // bottom left corner
		spawnRocks(7, 904, 1030, 587, 1156, 50, 15, 20, 10, 5); // left corridor
		spawnRocks(10, 430, 905, 591, 774, 50, 15, 20, 10, 5); // top left straight
		spawnRocks(5, 500, 679, 435, 593, 50, 15, 20, 10, 5); // top left corner
	}

	public void spawnRocks(int num, int x1, int x2, int y1, int y2, double basicProbability, double copperProbability,
			double ironProbability, double goldProbability, double diamondProbability) {
		for (int i = 0; i < num; i++) {
			Point p = getRandomPoint(x1, x2, y1, y2);

			int x = p.x;
			int y = p.y;

			if (p != null) { // if p is null then a new point to spawn couldn't be found
				Rock rock;
				int randomValue = new Random().nextInt(100); // Generate a random number between 0 and 99

				if (randomValue < basicProbability) { // 75% chance
					rock = new Rock(gamePanel, x, y, background);
				} else if (randomValue < basicProbability + copperProbability) { // 13% chance
					rock = new CopperRock(gamePanel, x, y, background);
				} else if (randomValue < basicProbability + copperProbability + ironProbability) { // 8% chance
					rock = new IronRock(gamePanel, x, y, background);
				} else if (randomValue < basicProbability + copperProbability + ironProbability + goldProbability) { // 3%
																														// chance
					rock = new GoldRock(gamePanel, x, y, background);
				} else { // 1% chance
					rock = new DiamondRock(gamePanel, x, y, background);
				}

				// 10% chance to have a fruit
				if (new Random().nextInt(100) < 10) {
					rock.setHasFruit(true);
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

	public Point getRandomPoint(int x1, int x2, int y1, int y2) {

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

		if (spawn)
			return new Point(x, y);
		else
			return null;
	}

	public void setBg(Background bg) {
		background = bg;
	}

	public ArrayList<Rock> getRocks(){
		return rocks;
	}
}
