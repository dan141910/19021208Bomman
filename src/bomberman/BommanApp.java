package bomberman;

import bomberman.GUI.Frame;
import bomberman.GUI.menu.InfoPanel;
import bomberman.gameSeting.Configuration;
import bomberman.gameSeting.Keyboard;
import bomberman.gameSeting.Sound;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.IOException;

public class BommanApp extends JFrame{
    GameController gameController;
    Keyboard input = new Keyboard();
    public BommanApp() {
        setTitle( Configuration.game_title);
        setResizable(false);
        setLayout( null );
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Configuration.game_width, Configuration.game_height);
        setLocationRelativeTo(null);
        //gameController
        gameController = new GameController(input);
        //add InfoPanel
        add(new InfoPanel(getWidth() / 10    , getHeight()));
        //add GameFrame
        add(new Frame(getWidth() / 10 * 9, getHeight(), gameController));
        //add key
        addKeyListener(input);
        requestFocus();
        setVisible(true);

        // start game
        Sound theme = null;
        try {
            theme = new Sound(Sound.sound_theme1, true);
            theme.play();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
       gameController.openingGame();
       gameController.start();


    }

}
