import javax.sound.sampled.AudioInputStream;		// for playing sound clips
import javax.sound.sampled.*;
import java.io.*;
import java.util.HashMap;				// for storing sound clips

public class SoundManager {				// a Singleton class
	HashMap<String, Clip> clips;

	private static SoundManager instance = null;	// keeps track of Singleton instance
	
	private SoundManager () {
		clips = new HashMap<String, Clip>();

		Clip clip1 = loadClip("sounds/break_rock.wav");
		clips.put("break_rock", clip1);

		Clip clip2 = loadClip("sounds/explosion.wav");
		clips.put("explosion", clip2);

		Clip clip3 = loadClip("sounds/game_over.wav");
		clips.put("gameover", clip3);

		Clip clip4 = loadClip("sounds/game_start.wav");
		clips.put("startgame", clip4);

		Clip clip5 = loadClip("sounds/lose_life.wav");
		clips.put("lose life", clip5);

		Clip clip6 = loadClip("sounds/pause_game.wav");
		clips.put("pause", clip6);

		Clip clip7 = loadClip("sounds/lobby.wav");
		clips.put("lobby", clip7);

		Clip clip8 = loadClip("sounds/dripping_water.wav");
		clips.put("dripping", clip8);
	}


	public static SoundManager getInstance() {	// class method to retrieve instance of Singleton
		if (instance == null)
			instance = new SoundManager();
		
		return instance;
	}		


    	public Clip loadClip (String fileName) {	// gets clip from the specified file
 		AudioInputStream audioIn;
		Clip clip = null;

		try {
    			File file = new File(fileName);
    			audioIn = AudioSystem.getAudioInputStream(file.toURI().toURL()); 
    			clip = AudioSystem.getClip();
    			clip.open(audioIn);
		}
		catch (Exception e) {
 			System.out.println ("Error opening sound files: " + e);
		}
    		return clip;
    	}


	public Clip getClip (String title) {

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

	public void setVolume (String title, float volume) {
		Clip clip = getClip(title);

		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
	
		float range = gainControl.getMaximum() - gainControl.getMinimum();
		float gain = (range * volume) + gainControl.getMinimum();

		gainControl.setValue(gain);
	}
	
}