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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Control date game playing.
 */
public class GameController extends Canvas implements Render {
    private  Board _board; // i4 about boardGame (number mod, map,...)
    private final Keyboard _input;
    private String user;

    //Time in the level screen in seconds
    private boolean _running; // run is true other false;
    private Player _player;

    /**
     * Initial.
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

            if (isRunning()) render(this.getGraphics());
        }

    }

    @Override
    public void update() {
        _input.update();
        _board.update();
        //update info to panel
        InfoPanel.update(_player.getHp(), _player.getPoint(), _player.getNumBoom(), _player.get_dameRange(), _player.getSpeed());
        if (_player.isRemoved()) { endGame();}
        if (_board.isPassGame()) { nextGame(++Map._level);}
    }

    @Override
    public void render(Graphics g) {
        if (System.nanoTime() % 20000 == 0) {
            g.setColor(new Color(0xCB4F954A, true));
            g.fillRect(0, 0, Configuration.game_width / 10 * 9, Configuration.game_height);
        }
        _board.render(g);
    }

    //=============================================================================================================
    //Scene Game
    //=============================================================================================================

    public boolean openingGame() {
        user = JOptionPane.showInputDialog(new JFrame(), "Nhập tên của bạn");
        if (user == null || user.equals("")) {
            JOptionPane.showMessageDialog(new JFrame(), "thôi nghỉ nhé!");
            System.exit(-1);
            return false;
        } else return true;
    }

    public void endGame() {
        stop();
        int x = JOptionPane.showConfirmDialog(new JFrame(), "Game over! \n Your point: " + _player.getPoint());
        if (x < 3) showHighPoint();
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
        int x = JOptionPane.showConfirmDialog(new JFrame(), "U win game! \n Your point:" + _player.getPoint() + " point!!!");
        if (x < 3) showHighPoint();
    }

    public void showHighPoint() {
        JFrame pointBoard = new JFrame("High Point");
        pointBoard.setLayout(new GridLayout(12,1));
        Label x;
        JButton btn = new JButton("Update");

        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader( new File("res/highpoint.csv")));
            String tmp;
            String[] values;
            while ((tmp = bufferedReader.readLine()) != null ) {
                values = tmp.split(",");
                x = new Label("   " + values[0] + " : " + values[1]);
                pointBoard.add(x);
            }
            x = new Label("   " + this.user + " : " + this._player.getPoint());

            x.setBackground(new Color(0x9E4545));
            pointBoard.add(x);

        } catch (Exception e) {
            System.out.println("high point board error");
            System.exit(-1);
        }

        btn.setSize(50, 30);
        btn.addActionListener(e -> {
            JOptionPane.showMessageDialog(new JFrame(),"Soạn tin nhắn \" NIHAOMA \" tới 0982482398 để sủ dụng tính năng");
            // save point here
        });

        pointBoard.add(btn);
        pointBoard.setVisible(true);
        pointBoard.setSize(300, 400);
        pointBoard.setLocation(500, 200);
        pointBoard.setResizable(false);
        pointBoard.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                pointBoard.dispose();
                System.exit(1);
            }
        });
    }

    //=============================================================================================================
    //getter and setter
    //=============================================================================================================

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
