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

		clip = loadClip("sounds/Level1/background.wav"); // played from start of the Level 1
		clips.put("background", clip);

		clip = loadClip("sounds/Level1/bee.wav"); // played when a fireball is shot
		clips.put("beeSound", clip);
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

	public void stopAllClips(){
		for (Clip clip : clips.values()) {
			clip.stop();
		}
	}

	public void setVolume(String title, float volume) {
		Clip clip = getClip(title);

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