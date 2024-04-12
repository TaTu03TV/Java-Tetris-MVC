package models;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SoundPlayer {
    private List<String> soundFiles = new ArrayList<>();
    private List<Clip> playingClips = new ArrayList<>();

    public void addSoundFile(String filePath) {
        soundFiles.add(filePath);
    }

    public List<String> getSoundFiles() {
        return new ArrayList<>(soundFiles);
    }

    public List<Clip> getPlayingClips() {
        return new ArrayList<>(playingClips);
    }

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

    public void setVolume(float volume, int index) {
        if (index >= 0 && index < playingClips.size()) {
            Clip clip = playingClips.get(index);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(volume);
        }
    }

    public void setVolumeAll(float volume) {
        for (Clip clip : playingClips) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(volume);
        }
    }

    public void stopSound(int index) {
        Clip clip = playingClips.get(index);
        clip.stop();
    }

    public void stopAllSounds() {
        for (Clip clip : playingClips) {
            clip.stop();
        }
    }

    public void pauseSound(int index) {
        Clip clip = playingClips.get(index);
        clip.stop();
    }

    public void pauseAllSounds() {
        for (Clip clip : playingClips) {
            clip.stop();
        }
    }

    public void resumeSound(int index) {
        Clip clip = playingClips.get(index);
        clip.start();
    }

    public void resumeAllSounds() {
        for (Clip clip : playingClips) {
            clip.start();
        }
    }

    public void loopSound(int index) {
        Clip clip = playingClips.get(index);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    
}