import javax.sound.sampled.*;
import java.io.*;
import java.util.HashMap; // for storing sound clips

public class SoundManager { // a Singleton class
	HashMap<String, Clip> clips;

	private static SoundManager instance = null; // keeps track of Singleton instance

	private float volume;

	private SoundManager() {

		clips = new HashMap<String, Clip>();

		Clip clip = loadClip("sounds/break_rock.wav");
		clips.put("break_rock", clip);

		clip = loadClip("sounds/Level2/explosion.wav");
		clips.put("explosion", clip);

		clip = loadClip("sounds/game_over.wav");
		clips.put("gameover", clip);

		clip = loadClip("sounds/game_start.wav");
		clips.put("startgame", clip);

		clip = loadClip("sounds/Level1/background.wav"); // played from start of the Level 1
		clips.put("background", clip);

		clip = loadClip("sounds/Level1/bee.wav"); // played when a fireball is shot
		clips.put("beeSound", clip);

		clip = loadClip("sounds/Level1/mushroomWalk.wav"); // played when a mushroom walks
		clips.put("mushroomWalk", clip);

		clip = loadClip("sounds/Level1/mushroomBoom.wav"); // played when a mushroom explodes
		clips.put("mushroomBoom", clip);

		clip = loadClip("sounds/Level1/grasshopperJump.wav"); // played when a grasshopper jumps
		clips.put("grasshopperJump", clip);

		clip = loadClip("sounds/lose_life.wav");
		clips.put("lose life", clip);

		clip = loadClip("sounds/pause_game.wav");
		clips.put("pause", clip);

		clip = loadClip("sounds/lobby.wav");
		clips.put("lobby", clip);

		clip = loadClip("sounds/game_start.wav");
		clips.put("start", clip);

		clip = loadClip("sounds/Level2/dripping_water.wav");
		clips.put("dripping", clip);

		clip = loadClip("sounds/enemyHit.wav");
		clips.put("enemyHit", clip);

		clip = loadClip("sounds/ladderDown.wav");
		clips.put("ladderDown", clip);

		clip = loadClip("sounds/player/munch.wav");
		clips.put("munch", clip);

		clip = loadClip("sounds/Level3/bomberHit.wav");
		clips.put("bomberHit", clip);

		clip = loadClip("sounds/Player/damage.wav");
		clips.put("damagePlayer", clip);

		clip = loadClip("sounds/Level3/level3_background.wav");
		clips.put("level3_background", clip);

		clip = loadClip("sounds/Level3/batFlap.wav");
		clips.put("batFlap", clip);

		clip = loadClip("sounds/Level2/crawling.wav");
		clips.put("crawling", clip);

		
		clip = loadClip("sounds/Level2/background2.wav");
		clips.put("background2", clip);

			
		clip = loadClip("sounds/Level2/explosion.wav");
		clips.put("explosion", clip);

			
		clip = loadClip("sounds/Level2/scorpion.wav");
		clips.put("scorpionRattle", clip);

		clip = loadClip("sounds/Level2/bismuthRoar.wav");
		clips.put("roar", clip);

	}

	public static SoundManager getInstance() { // class method to retrieve instance of Singleton
		if (instance == null)
			instance = new SoundManager();

		return instance;
	}

	public Clip loadClip(String fileName) { // gets clip from the specified file
		AudioInputStream audioIn;
		Clip clip = null;

		try {
			File file = new File(fileName);
			audioIn = AudioSystem.getAudioInputStream(file.toURI().toURL());
			clip = AudioSystem.getClip();
			clip.open(audioIn);
		} catch (Exception e) {
			System.out.println("Error opening sound files: " + e);
		}
		return clip;
	}

	public Clip getClip(String title) {

		return clips.get(title);
	}

	public void playClip(String title, boolean looping) {
		Clip clip = getClip(title);
		if (clip != null) {
			clip.setFramePosition(0);
			if (looping)
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			else
				clip.start();
		}
	}

	public void stopClip(String title) {
		Clip clip = getClip(title);
		if (clip != null) {
			clip.stop();
		}
	}

	public void stopAllClips() {
		for (Clip clip : clips.values()) {
			if(clip!=null)
				clip.stop();
		}
	}

	public void setVolume(String title, float volume) {
		Clip clip = getClip(title);

		if(clip==null)
			return;

		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

		float range = gainControl.getMaximum() - gainControl.getMinimum();
		float gain = (range * volume) + gainControl.getMinimum();

		gainControl.setValue(gain);
	}

	public boolean isStillPlaying(String title) {
		Clip clip = getClip(title);
		return clip.isRunning();
	}

}