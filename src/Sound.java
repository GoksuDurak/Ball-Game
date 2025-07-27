import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import javax.sound.sampled.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;

public class Sound {
    private Player player;
    public boolean exit = false;
    private Clip clipJump;
    private Clip clipExit;
    private Game game;
    private boolean soundActivated = false;
    public Sound(Game game) {
        this.game = game;
    }
    public void mp3() {
        try {
            FileInputStream file = new FileInputStream("test.mp3");
            BufferedInputStream bis = new BufferedInputStream(file);
            player = new Player(bis);
        } catch (FileNotFoundException | JavaLayerException e) {
            e.printStackTrace();
        }
    }
    public void jumpSound() {
        File soundFile = new File("jump.wav");
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);
            clipJump = AudioSystem.getClip();
            clipJump.open(ais);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        Thread t1 = new Thread(() -> {
            clipJump.start();
        });
        t1.start();
        try {
            t1.join();   // t2 bitene kadar bekle
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void exitSound() {
        File soundFileExit = new File("blipSelect.wav");
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundFileExit);
            clipExit = AudioSystem.getClip();
            clipExit.open(ais);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        Thread t1 = new Thread(() -> {
            clipExit.start();
        });
        t1.start();
        try {
            t1.join();   // t2 bitene kadar bekle
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void start() throws JavaLayerException, InterruptedException {

    }
}
