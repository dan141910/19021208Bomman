package bomberman;

import bomberman.GUI.Board;
import bomberman.entities.Entities;
import bomberman.entities.dynamicEntities.Player;
import bomberman.gameSeting.Configuration;
import bomberman.gameSeting.Keyboard;
import bomberman.graphics.Map;
import bomberman.graphics.Render;

import java.awt.*;

/**
 * Control date game playing.
 */
public class GameController extends Canvas implements Render {
    private final Board _board; // i4 about boardgame (number mod, map,...)
    private final Keyboard _input;

    //Time in the level screen in seconds
    private boolean _running = true; // run is true other false;
    private final Player _player;

    /**
     * Khoi tao.
     * @param input keyboard.
     */
    public GameController(Keyboard input) {
        _input = input;
        _board = new Board();
        Map.initMap (_board);
        _player = _board.getPlayer();
        _player.set_input(input);
        setSize(Configuration.game_width / 10 * 9, Configuration.game_height);
    }

    public void start() {
        _running = true;

        long  lastTime = System.nanoTime();
        final double ns = 1000000000.0 / 60.0; //nanosecond, 60 frames per second
        double delta = 0;

        while(isRunning()) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1) {
                update();
                delta--;
            }

            if(!isRunning()) {
                //pause game
                System.out.println("Game pause!");
            } else {
                // rungame
                render(this.getGraphics());
            }
        }
    }

    @Override
    public void update() {
        _input.update();
        _board.update();
    }

    @Override
    public void render(Graphics g) {
        if (System.nanoTime() % 10000 == 0) {
            g.setColor(new Color(0xCB4F954A, true));
            g.fillRect(0, 0, Configuration.game_width / 10 * 9, Configuration.game_height);
        }
        _board.render(g);
    }

    /*
        |--------------------------------------------------------------------------
        | Getters & Setters
        |--------------------------------------------------------------------------
         */
    public void run() {
        _running = true;
    }
    public void stop() {
        _running = false;
    }

    public boolean isRunning() {
        return _running;
    }
}
