package bomberman;

import bomberman.GUI.Board;
import bomberman.GUI.menu.InfoPanel;
import bomberman.entities.dynamicEntities.Player;
import bomberman.gameSeting.Configuration;
import bomberman.gameSeting.Keyboard;
import bomberman.graphics.Map;
import bomberman.graphics.Render;

import javax.swing.*;
import java.awt.*;

/**
 * Control date game playing.
 */
public class GameController extends Canvas implements Render {
    private  Board _board; // i4 about boardGame (number mod, map,...)
    private final Keyboard _input;

    //Time in the level screen in seconds
    private boolean _running; // run is true other false;
    private boolean _pause; // run is true other false;
    private Player _player;

    /**
     * Khoi tao.
     * @param input keyboard.
     */
    public GameController(Keyboard input) {
        _input = input;
        _board = new Board();
        _player = _board.getPlayer();
        _player.set_input(input);
        setSize(Configuration.game_width / 10 * 9, Configuration.game_height);

    }

    public void start() {
        run();
        long lastTime = System.nanoTime();
        final double ns = 1000000000.0 / 60.0; //nanosecond, 60 frames per second
        double delta = 0;
        while (isRunning()) {
            long now = System.nanoTime();
            delta += ( now - lastTime ) / ns;
            lastTime = now;
            while (delta >= 1) {
                update();
                delta--;
            }

            if (isPause()) {
                InfoPanel.notice("PAUSE GAME!");
            } else {
                // run Game
                render(this.getGraphics());
            }
        }

    }

    @Override
    public void update() {
        _input.update();
        _board.update();
        //update info to panel
        InfoPanel.update(_player.getHp(), _player.getPoint(),
                         _player.getNumBoom(), _player.get_dameRange(), _player.getSpeed());
        if (_player.isRemoved()) {
            endGame();
        }
        if (_board.isPassGame()) {
            nextGame(++Map._level);
        }
    }

    @Override
    public void render(Graphics g) {
        if (System.nanoTime() % 10000 == 0) {
            g.setColor(new Color(0xCB4F954A, true));
            g.fillRect(0, 0, Configuration.game_width / 10 * 9, Configuration.game_height);
        }
        _board.render(g);
    }

    //=============================================================================================================
    //Level Game
    //=============================================================================================================

    public void openingGame() {

        int cf = JOptionPane.showConfirmDialog(new JFrame(), "Chơi không?");
        if (cf == 0) run();
        else {
            JOptionPane.showMessageDialog(new JFrame(), "thế mở ăn lone à?");
            System.exit(-1);
        }
    }
    public void endGame() {
        stop();
        JOptionPane.showConfirmDialog(new JFrame(), "Game over ngu vcl");
        System.exit(1);
    }

    public void nextGame(int level) {
        stop();
        if (level > 5) wingame();
        int cf = JOptionPane.showConfirmDialog(new JFrame(), "Bạn có muốn tiếp tục level:" + level + "?");
        if (cf != 0) {
            endGame();
        } else {
            _board = new Board();
            Player nextLevelPlayer = _board.getPlayer();
            nextLevelPlayer.setPoint(_player.getPoint());
            nextLevelPlayer.set_input(_input);
            _player = nextLevelPlayer;
            start();
        }
    }

    public void wingame() {
        JOptionPane.showConfirmDialog(new JFrame(), "Thắng rồi đấy con zai!!!");
        System.exit(1);
    }

    //=============================================================================================================
    //getter and setter
    //=============================================================================================================

    public void run() {
        _running = true;
        _pause = false;
    }
    public void stop() {
        _running = false;
    }

    public void pause() {
        _pause = true;
    }

    public boolean isRunning() {
        return _running;
    }

    public boolean isPause() {return  _pause;}
}
