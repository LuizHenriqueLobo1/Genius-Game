package ui;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;

public class PlaySound {

	public void playSound(String soundPath) {
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
					new File(soundPath).getAbsoluteFile()
			);
			Clip clip;
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (Exception ex) {
			System.out.println("Not found sound file");
		}
	}
	
}
