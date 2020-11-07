package bomberman;

import bomberman.GUI.Frame;
import bomberman.GUI.menu.Menu;
import bomberman.gameSeting.Configuration;
import bomberman.gameSeting.Keyboard;

import javax.swing.*;

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
        //add menu
        add(new Menu( getWidth() / 10    , getHeight(), gameController));
        //add gameframe
        add(new Frame(getWidth() / 10 * 9, getHeight(), gameController));

        addKeyListener(input);
        requestFocus();
        setVisible(true);
        // start game
        gameController.start();
    }

}
