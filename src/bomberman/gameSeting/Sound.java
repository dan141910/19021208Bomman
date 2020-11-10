package bomberman.gameSeting;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {
    private final Clip _clip;
    public Sound (String path, boolean isLoop) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        // create AudioInputStream object
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(path).getAbsoluteFile());

        // create clip reference
        _clip = AudioSystem.getClip();

        // open audioInputStream to the clip
        _clip.open(audioInputStream);

        if (isLoop) _clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void play() {
        _clip.start();
    }

    public void stop(){
        _clip.stop();
        _clip.close();
    }

    public boolean isPlay() {
        return _clip.isRunning();
    }
    //========================================================================================================
    // stactic
    //========================================================================================================
    public static String sound_theme1 = "res/sound/theme1.wav";
    public static String sound_theme2 = "res/sound/theme2.wav";
    public static String sound_fire = "res/sound/fire.wav";
    public static String sound_detecBoom = "res/sound/detecBoom.wav";

}
