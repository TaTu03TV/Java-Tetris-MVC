package models;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to play sounds in the game
 * 
 * This class is used to play sounds in the game. It contains methods to add sound files, play sounds, set the volume of sounds, stop sounds, pause sounds, resume sounds, and loop sounds.
 * 
 * @version 1.0
 * @since 2024-04-14
 */
public class SoundPlayer {
    private List<String> soundFiles = new ArrayList<>();
    private List<Clip> playingClips = new ArrayList<>();

    /**
     * Add a sound file to the list of sound files
     */
    public void addSoundFile(String filePath) {
        soundFiles.add(filePath);
    }

    /**
     * Get the list of sound files
     * 
     * @return List of sound files
     */
    public List<String> getSoundFiles() {
        return new ArrayList<>(soundFiles);
    }

    /**
     * Get the list of playing clips
     * 
     * @return List of playing clips
     */
    public List<Clip> getPlayingClips() {
        return new ArrayList<>(playingClips);
    }

    /**
     * Play a sound
     * 
     * @param index Index of the sound file to play
     */
    public void playSound(int index) {
        try {
            URL url = getClass().getResource(soundFiles.get(index));
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            playingClips.add(clip);
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set the volume of a sound
     * 
     * @param volume Volume to set
     * @param index Index of the sound file
     */
    public void setVolume(float volume, int index) {
        if (index >= 0 && index < playingClips.size()) {
            Clip clip = playingClips.get(index);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(volume);
        }
    }

    /**
     * Set the volume of all sounds
     * 
     * @param volume Volume to set
     */
    public void setVolumeAll(float volume) {
        for (Clip clip : playingClips) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(volume);
        }
    }

    /**
     * Stop a sound
     * 
     * @param index Index of the sound file to stop
     */
    public void stopSound(int index) {
        Clip clip = playingClips.get(index);
        clip.stop();
    }

    /**
     * Stop all sounds
     */
    public void stopAllSounds() {
        for (Clip clip : playingClips) {
            clip.stop();
        }
    }

    /**
     * Pause a sound
     * 
     * @param index Index of the sound file to pause
     */
    public void pauseSound(int index) {
        Clip clip = playingClips.get(index);
        clip.stop();
    }

    /**
     * Pause all sounds
     */
    public void pauseAllSounds() {
        for (Clip clip : playingClips) {
            clip.stop();
        }
    }

    /**
     * Resume a sound
     * 
     * @param index Index of the sound file to resume
     */
    public void resumeSound(int index) {
        Clip clip = playingClips.get(index);
        clip.start();
    }

    /**
     * Resume all sounds
     */
    public void resumeAllSounds() {
        for (Clip clip : playingClips) {
            clip.start();
        }
    }

    /**
     * Loop a sound
     * 
     * @param index Index of the sound file to loop
     */
    public void loopSound(int index) {
        Clip clip = playingClips.get(index);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    
}